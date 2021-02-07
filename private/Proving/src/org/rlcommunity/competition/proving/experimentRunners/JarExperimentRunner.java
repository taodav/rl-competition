/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.proving.experimentRunners;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.rlcommunity.competition.proving.*;
import org.rlcommunity.competition.phoneHomeInterface.MDPDetails;
import org.rlcommunity.competition.phoneHomeInterface.RLEvent;
import org.rlcommunity.rlglue.codec.RLGlue;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.environmentShell.EnvShellListRequest;
import rlVizLib.messaging.environmentShell.EnvShellListResponse;
import rlVizLib.messaging.environmentShell.EnvShellLoadRequest;
import rlVizLib.messaging.environmentShell.EnvShellRefreshRequest;
import rlVizLib.messaging.environmentShell.EnvShellUnLoadRequest;

/**
 *
 * @author btanner
 */
public class JarExperimentRunner extends AbstractExperimentRunner{
        private boolean currentlyLoaded = false;

        externalProcessRunner theEnvShellProcessRunner=null;
        externalProcessRunner theRLGlueProcessRunner=null;
        
    private void unload() {
        EnvShellUnLoadRequest.Execute();
    }

    private void load(String envNameString, ParameterHolder theParams) {
        if (currentlyLoaded) {
            unload();
        }

        EnvShellLoadRequest.Execute(envNameString, theParams);
        currentlyLoaded = true;
    }

    private ParameterHolder preload(String envNameString) {
        EnvShellListResponse ListResponse = EnvShellListRequest.Execute();
        int thisEnvIndex = ListResponse.getTheEnvList().indexOf(envNameString);
        if(thisEnvIndex == -1)
            theController.dieWithErrorMessage("Error :: Loading Jars\nCouldn't find the request jar:"+ envNameString+"\nThe List was: "+ListResponse.getTheEnvList());
        ParameterHolder p = ListResponse.getTheParamList().get(thisEnvIndex);
        return p;
    }

    private void loadEnvironment(String envName) {
        EnvShellRefreshRequest.Execute();
        ParameterHolder theParams = preload(envName);
        load(envName, theParams);
    }

    public void runMDP(MDPDetails theMDP) {
        theController.getStatusTracker().startMDPTesting(theMDP);

        double totalReturn = 0.0d;

        String theEnvJarName = theMDP.getJarFileName();
        //Cut off the last 4 letters (.jar)
        String envClassName = theMDP.getMDPClassName();
        //Add the EnvironmentShell suffix
        envClassName += " - Java";

        int totalStepsAllowed = theMDP.getTotalSteps();
        int stepsRemaining = totalStepsAllowed;

        theController.getStatusTracker().notifyOfStepsPerMDP(totalStepsAllowed);

        loadEnvironment(envClassName);
        
        RLGlue.RL_init();
        
        theController.logMessage("Running...");

        int totalSteps = 0;//counter for the total number of steps taken to finish all episodes
        int totalEpisodes = 0;
//        theController.getView().episodeProgressBar.setMinimum(0);
//       theController.getView().episodeProgressBar.setMaximum(totalStepsAllowed);
        while (stepsRemaining > 0) {
            theController.getStatusTracker().startEpisode();
            RLGlue.RL_episode(stepsRemaining);
            int thisStepCount = RLGlue.RL_num_steps();
            double thisReturn = RLGlue.RL_return();

            theController.getStatusTracker().endEpisode(totalEpisodes, thisReturn, thisStepCount);


            stepsRemaining -= thisStepCount;
            //Not useful
            totalSteps += thisStepCount;
            totalEpisodes++;

            totalReturn += RLGlue.RL_return();
        }

        theController.logMessage("Recording...");
        theController.getStatusTracker().endMDPTesting(totalEpisodes, totalReturn, totalSteps);

        //clean up the environment and end the program
        RLGlue.RL_cleanup();
        
    }
        public JarExperimentRunner(RLEvent theEvent, Controller theController) {
            super(theEvent,theController);
        }
        
        private void stopEnvShell(){
            if(theEnvShellProcessRunner==null)return;
        try {
            theEnvShellProcessRunner.stopProcess();
            theEnvShellProcessRunner.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(JarExperimentRunner.class.getName()).log(Level.SEVERE, null, ex);
        }

        }

    private void startEnvShell(){
       theEnvShellProcessRunner=new envShellProcessRunner();
       theEnvShellProcessRunner.startExternalProcess();
    }
    private void startGlue(){
            theRLGlueProcessRunner=new rlGlueProcessRunner();
            theRLGlueProcessRunner.startExternalProcess();
    }
    
    private void stopGlue(){
            if(theRLGlueProcessRunner==null)return;
        try {
            theRLGlueProcessRunner.stopProcess();
            theRLGlueProcessRunner.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(JarExperimentRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setup() {
            startGlue();
            startEnvShell();
    }
    

    public void teardown() {
        //Not actually going to do this because then it doesn't close gracefully...
        stopGlue();
        stopEnvShell();
    }
    
    public static void main(String[] args){
        try {
            JarExperimentRunner J = new JarExperimentRunner(RLEvent.Acrobot, null);
            J.setup();
        } catch (ProcessStartException ex) {
            System.err.println("WHAM!  Caught the exception");
        }
    }

}
