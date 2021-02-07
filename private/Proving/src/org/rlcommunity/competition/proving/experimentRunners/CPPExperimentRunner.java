/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rlcommunity.competition.proving.experimentRunners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rlcommunity.competition.proving.*;
import org.rlcommunity.competition.phoneHomeInterface.MDPDetails;
import org.rlcommunity.competition.phoneHomeInterface.RLEvent;
import org.rlcommunity.rlglue.codec.RLGlue;
import rlVizLib.general.ParameterHolder;

import rlVizLib.messaging.environment.EnvReceiveRunTimeParametersRequest;

/**
 *
 * @author btanner
 */
class CPPExperimentRunner extends AbstractExperimentRunner {

    private Process rtsProcess = null;
    private externalProcessRunner theRLGlueProcessRunner;
    private externalProcessRunner theRTSProcessRunner;

    public CPPExperimentRunner(RLEvent theEvent, Controller theController) {
        super(theEvent, theController);
    }

    public void runMDP(MDPDetails theMDP) {
        BufferedReader r = null;
        try {
            theController.getStatusTracker().startMDPTesting(theMDP);
            double totalReturn = 0.0d;
            String theParameterFileName = theMDP.getJarFileName();
            String relativePathParameterFileName = "../system/proving/" + theParameterFileName;
            r = new BufferedReader(new FileReader(new File(relativePathParameterFileName)));
            String paramHolderString = r.readLine();
            r.close();

            //Send the string to RTS
            ParameterHolder P = new ParameterHolder(paramHolderString);
            EnvReceiveRunTimeParametersRequest.Execute(P);

            int totalStepsAllowed = theMDP.getTotalSteps();
            int stepsRemaining = totalStepsAllowed;

            theController.getStatusTracker().notifyOfStepsPerMDP(totalStepsAllowed);




            RLGlue.RL_init();


            int totalSteps = 0;
            int totalEpisodes = 0;
            //theController.getView().episodeProgressBar.setMinimum(0);
            //theController.getView().episodeProgressBar.setMaximum(totalStepsAllowed);
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

            theController.getStatusTracker().endMDPTesting(totalEpisodes, totalReturn, totalSteps);

            //clean up the environment and end the program
            RLGlue.RL_cleanup();
        } catch (Exception ex) {
            Logger.getLogger(CPPExperimentRunner.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (r != null) {
                    r.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(CPPExperimentRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void stopRTS() {
        if (theRTSProcessRunner == null) {
            return;
        }
        try {
            theRTSProcessRunner.stopProcess();
            theRTSProcessRunner.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(CPPExperimentRunner.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void startRTS() {
        theRTSProcessRunner = new rtsProcessRunner();
        theRTSProcessRunner.startExternalProcess();
    }

    private void startGlue() {
        theRLGlueProcessRunner = new rlGlueProcessRunner();
        theRLGlueProcessRunner.startExternalProcess();
    }

    private void stopGlue() {
        if (theRLGlueProcessRunner == null) {
            return;
        }
        try {
            theRLGlueProcessRunner.stopProcess();
            theRLGlueProcessRunner.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(CPPExperimentRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setup() {
        startGlue();
        startRTS();
    }

    public void teardown() {
        System.err.println("Calling teardown");
        stopRTS();
        stopGlue();
    }
}
