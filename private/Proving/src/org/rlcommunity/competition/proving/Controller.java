/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rlcommunity.competition.proving;

import org.rlcommunity.competition.proving.experimentRunners.ExperimentRunningFactory;
import org.rlcommunity.competition.proving.experimentRunners.AbstractExperimentRunner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rlcommunity.competition.phoneHomeInterface.AuthToken;
import org.rlcommunity.competition.phoneHomeInterface.PhoneHomeConnection;
import org.rlcommunity.competition.phoneHomeInterface.PhoneHomeStub;
import org.rlcommunity.competition.phoneHomeInterface.PhoneHomeConnectionInterface;
import org.rlcommunity.competition.phoneHomeInterface.RLEvent;
import org.rlcommunity.competition.phoneHomeInterface.MDPDetails;
import org.rlcommunity.competition.phoneHomeInterface.ResultDetails;
import org.rlcommunity.competition.phoneHomeInterface.exceptions.connectionError;
import org.rlcommunity.competition.phoneHomeInterface.exceptions.invalidEventException;
import org.rlcommunity.competition.phoneHomeInterface.exceptions.invalidKeyException;
import org.rlcommunity.competition.phoneHomeInterface.exceptions.noKeysAvailableException;
import org.rlcommunity.competition.phoneHomeInterface.exceptions.noMDPsAvailableException;
import org.rlcommunity.competition.phoneHomeInterface.exceptions.notAuthenticatedException;
import org.rlcommunity.competition.proving.experimentRunners.ProcessStartException;
import org.rlcommunity.competition.proving.experimentRunners.externalProcessRunner;

/**
 *
 * @author btanner
 */
public class Controller {

    doStuffThread theDoStuffThread;
    ViewInterface theView = null;
    boolean allowed[] = null;
    PhoneHomeConnectionInterface theConnection = null;
    RLEvent theEvent = null;
    AuthToken validAuthToken = null;
    private StatusTracker theStatusTracker;
    private boolean useOfflineStubInsteadOfNetworkConnection = false;

    public StatusTracker getStatusTracker() {
        return theStatusTracker;
    }

    Controller(ViewInterface theView) {
        this.theView = theView;

        String offlineStubPropertyValue = System.getProperty("offlineStub");


        if (offlineStubPropertyValue != null && offlineStubPropertyValue.equals("true")) {
            useOfflineStubInsteadOfNetworkConnection = true;
        }

        if (useOfflineStubInsteadOfNetworkConnection) {
            theConnection = new PhoneHomeStub(this);
            System.out.println("DEBUG MODE :: Using stub");
        } else {
            theConnection = new PhoneHomeConnection(this);
        }

        allowed = new boolean[RLEvent.numEvents()];
        theDoStuffThread = new doStuffThread(this);
        theDoStuffThread.start();
    }

    public ViewInterface getView() {
        return theView;
    }

    void eventSelected(RLEvent whichEvent) {
        theEvent = whichEvent;
        theView.setVisibilities(allowed, (validAuthToken != null));
    }

    public void makeLoginEvent(String username, String password) {
        theDoStuffThread.username = username;
        theDoStuffThread.password = password;
        theDoStuffThread.setJob("login");
    }

    public String decorateMessage(String theMessage) {
        theMessage = "------------------------------------------\n" + theMessage;
        theMessage += "\n------------------------------------------\n";
        theMessage += "\nWhen you click ok, the application will close.";
        return theMessage;
    }

    private void login(String userName, String password) {
        theView.logMessage("Authenticating...");
        AuthToken theAuthToken = new AuthToken(userName, password);
        
        boolean authenticated = theConnection.isAuthenticationValid(theAuthToken);
        if (authenticated) {
            validAuthToken = theAuthToken;
        } else {
            validAuthToken = null;
        }

        if (validAuthToken != null) {
            checkWhatsAllowed();
            theView.setVisibilities(allowed, (validAuthToken != null));
            theView.logMessage("authenticated\n");
        } else {
            theView.logMessage("NOT authenticated\n");
        }

    }

    public void makeLogoutEvent() {
        theDoStuffThread.setJob("logout");
    }

    private void logout() {
        validAuthToken = null;

        theView.logMessage("Logging out...");
        checkWhatsAllowed();
        theView.setVisibilities(allowed, (validAuthToken != null));
        theView.logMessage("complete\n");
    }

    public void makeRunExperimentEvent() {
        theDoStuffThread.setJob("runExperiment");
    }

    private void runExperiment() {
        System.out.println("The Experiment is: " + theEvent.name());
        assert (validAuthToken != null);
        AbstractExperimentRunner theExp = null;

        try {
            theExp = ExperimentRunningFactory.makeExperimentRunner(theEvent, this);
            theExp.setup();
            String currentKey = theConnection.makeNewKey(validAuthToken, theEvent);
            checkWhatsAllowed();
            theView.disableAll();
            theStatusTracker = new StatusTracker(this, validAuthToken, theEvent);

            int MDPRemaining = theConnection.MDPSRemaining(validAuthToken, currentKey);
            theStatusTracker.notifyOfMDPs(MDPRemaining);
            while (MDPRemaining > 0) {
                theStatusTracker.startMDP();
                MDPDetails theMDP = theConnection.getNextMDP(validAuthToken, currentKey);
                theStatusTracker.notifyOfMDPLogFrequency(theMDP.getLogRatio());

                theView.logMessage("Loading...");
                theExp.runMDP(theMDP);

                ResultDetails theResults = new ResultDetails(theStatusTracker.getResultsFileLocation(), theMDP);

                theView.logMessage("Sending...");
                Long beforeSendTime = System.currentTimeMillis();
                theConnection.sendResults(validAuthToken, currentKey, theResults);
                Long totalResultsSendTime = System.currentTimeMillis() - beforeSendTime;
                theStatusTracker.lastResultsSendTime = totalResultsSendTime;
                theView.logMessage("done .\n");

                theStatusTracker.endMDP();
                MDPRemaining = theConnection.MDPSRemaining(validAuthToken, currentKey);

            }
            //Don't Tear Down unless there is a crash, because force killing 
            //RL-Glue means the agent won't finish nicely.
//            theExp.teardown();
            externalProcessRunner.killActiveProcesses();
            if (!theEvent.name().startsWith("Testing")) {
                theView.dieWithResultsMessage(theStatusTracker.getResultSummarString());
            }
        } catch (invalidKeyException ex) {
            System.err.println("Invalid key exception in Controller::runExperiment");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            checkWhatsAllowed();
            theView.setVisibilities(allowed, (validAuthToken != null));
        } catch (notAuthenticatedException ex) {
            System.err.println("notAuthenticatedException exception in Controller::runExperiment");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            checkWhatsAllowed();
            theView.setVisibilities(allowed, (validAuthToken != null));
        } catch (invalidEventException ex) {
            System.err.println("invalidEventException exception in Controller::runExperiment");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (noKeysAvailableException ex) {
            System.err.println("noKeysAvailableException exception in Controller::runExperiment");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            checkWhatsAllowed();
            theView.setVisibilities(allowed, (validAuthToken != null));
        } catch (noMDPsAvailableException ex) {
            System.err.println("noMDPsAvailableException exception in Controller::runExperiment");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            checkWhatsAllowed();
            theView.setVisibilities(allowed, (validAuthToken != null));
        } catch (ProcessStartException ex) {
            //We should kill all started processes here then
            if (theExp != null) {
                theExp.teardown();
            }

            String errorMessage = "Problem running or starting experiment\n\n";
            errorMessage += ex.getMessage() + "\n\n";
            errorMessage += "Please fix this problem and restart the proving application.";
            externalProcessRunner.killActiveProcesses();
            dieWithErrorMessage(errorMessage);
        }
    }

    void stop() {
        theDoStuffThread.setJob("stop");

    //theView.bRun.setEnabled(true);
    }

    public void updateView(String currentTime, String estimateTimeRemaining, int stepsPerMDP, int numMDPs, int stepsThisMDP, int totalRunSteps) {
        theView.updateView(currentTime, estimateTimeRemaining, stepsPerMDP, numMDPs, stepsThisMDP, totalRunSteps);
    }

    void displayKeysAvailable() {
        if (validAuthToken == null) {
            //theView.setDefaultButtonNames();
            return;
        }
        try {
            for (RLEvent theCurrentEvent : RLEvent.values()) {
                int num = theConnection.howManyKeysAvailable(validAuthToken, theCurrentEvent);
                theView.setNumberRunsAvailable(theCurrentEvent, num);
            }
        } catch (invalidEventException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (notAuthenticatedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logMessage(String theMessage) {
        theView.logMessage(theMessage);
    }

    public void logError(String theError) {
        theView.logError(theError);
        System.err.println("Error: "+theError);
    }

    public void dieWithErrorMessage(String theError) {
        logError(theError);
        externalProcessRunner.killActiveProcesses();
        System.exit(1);
    }

    void checkWhatsAllowed() {
        //update the text to show the keys available
        displayKeysAvailable();
        if (validAuthToken == null) {
            for (int i = 0; i < RLEvent.numEvents(); i++) {
                allowed[i] = false;
            }

        } else {
            try {
                int i=0;
                for (RLEvent currentEvent : RLEvent.values()) {
                    int keysAvailable = 0;
                    if (validAuthToken != null) {
                        keysAvailable = theConnection.howManyKeysAvailable(validAuthToken, currentEvent);
                    }
                    allowed[i] = keysAvailable > 0;
                    i++;
                }
            } catch (invalidEventException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (notAuthenticatedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void someEventButtonPressed() {
    }

    public void quitProvingApp() {
        System.exit(0);
    }

    //Inner class
    class doStuffThread extends Thread {

        Controller theController = null;
        String theJob = null;
        public String username;
        public String password;

        synchronized void setJob(String newJob) {
            if (theJob != null) {
                System.err.println("Tried to set job to: " + newJob + " but it was already: " + theJob);
            }
            theJob = newJob;
        }

        private synchronized String getJob() {
            return theJob;
        }

        private synchronized void clearJob() {
            theJob = null;
        }

        doStuffThread(Controller theController) {
            this.theController = theController;
        }

        @Override
        public void run() {
            while (true) {
                String whatToDo = getJob();
                if (whatToDo != null) {

                    try {
                        if (whatToDo.equals("login")) {
                            theController.login(username, password);
                        }
                        if (whatToDo.equals("logout")) {
                            theController.logout();
                        }
                        if (whatToDo.equals("runExperiment")) {
                            theController.runExperiment();
                        }

                    } catch (connectionError e) {
                        theController.dieWithErrorMessage("Problem Connecting to the Competition Server: \n\n" + e.getMessage());
                    }
                    clearJob();

                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(doStuffThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }
}
    
    

