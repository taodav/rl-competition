/*
 * 
 */
package org.rlcommunity.competition.proving.experimentRunners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rlcommunity.competition.phoneHomeInterface.MDPDetails;
import org.rlcommunity.competition.phoneHomeInterface.RLEvent;
import org.rlcommunity.competition.proving.Controller;

/**
 *
 * @author mradkie
 */
class ScriptExperimentRunner extends AbstractExperimentRunner {

    private String relativePathtoFile = "";
    private String argumentString = "";
    private String theScriptFileName="keepaway_RLGlue.sh";
    private String theLogFileName = "keepawaylog.txt";

    public ScriptExperimentRunner(RLEvent theEvent, Controller theController, String relativePath) {
        super(theEvent, theController);
        this.relativePathtoFile = relativePath;
    }

    public void runMDP(MDPDetails theMDP) {
        BufferedReader r = null;
        try {
            theController.getStatusTracker().startMDPTesting(theMDP);

            String theParameterFileName = theMDP.getJarFileName();
            int numEpisodes = theMDP.getTotalSteps();
            argumentString = numEpisodes + " ";
            String pathToParamFile = "../system/proving/" + theParameterFileName;
            String paramString = "";
            r = new BufferedReader(new FileReader(new File(pathToParamFile)));
            while(r.ready()){
                paramString = r.readLine();
                if(!paramString.startsWith("#") && paramString.length() > 0)
                    argumentString += paramString + " ";
            }
            r.close();

            String relativePathParameterFileName = relativePathtoFile + theScriptFileName;
            relativePathParameterFileName.replaceAll("/",File.pathSeparator );

            try {
                relativePathParameterFileName += " " + argumentString + theLogFileName;
                System.out.println("/bin/bash" + " -c " + relativePathParameterFileName);
                String[] commandString = {"/bin/bash", "-c", relativePathParameterFileName};
                Runtime.getRuntime().exec(commandString).waitFor();
            } catch (Exception ex) {
                System.out.println("Error :: Exception Running " + relativePathParameterFileName + " :: " + ex);
            }
            //parse the keepaway log into our log file
            getResults();
        } catch (IOException ex) {
            Logger.getLogger(ScriptExperimentRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getResults() {
        // hack to get it working for right now
        theController.getStatusTracker().notifyOfMDPLogFrequency(Integer.MAX_VALUE);
        try {
            BufferedReader r = null;

            String pathToParamFile = "../system/proving/" + theLogFileName;
            r = new BufferedReader(new FileReader(new File(pathToParamFile)));
            String episodeString ="";
            
            int episodeNum = 0;
            double episodeReward = 0.0;
            double cumulativeReward = 0.0;
            int episodeDuration = 0;
            int totalSteps = 0;

            while (r.ready()) {
                episodeNum++;
                episodeString = r.readLine();
                if (!episodeString.startsWith("#")) {
                    //break the string up
                    StringTokenizer theTokenizer = new StringTokenizer(episodeString, "\t");
                    try{
                        episodeReward = Double.parseDouble(theTokenizer.nextToken());
                    }catch(NumberFormatException e){
                        episodeReward = 0.0;
                    }
                    try{
                        episodeDuration = Integer.parseInt(theTokenizer.nextToken());
                    }catch(NumberFormatException e){
                        episodeDuration = 0;
                    }
                       cumulativeReward += episodeReward;
                    totalSteps += episodeDuration;
                    theController.getStatusTracker().endEpisode(episodeNum, episodeReward, episodeDuration);
                }
            }
            theController.getStatusTracker().endMDPTesting(episodeNum, cumulativeReward, totalSteps);
            r.close();
        } catch (IOException ex) {
            Logger.getLogger(ScriptExperimentRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Not needed 
     */
    @Override
    public void setup() {

    }

    public void teardown() {

    }
}
