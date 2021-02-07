/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rlcommunity.competition.proving;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import org.rlcommunity.competition.phoneHomeInterface.AuthToken;
import org.rlcommunity.competition.phoneHomeInterface.MDPDetails;
import org.rlcommunity.competition.phoneHomeInterface.RLEvent;
import rlVizLib.messaging.environment.EpisodeSummaryRequest;
import rlVizLib.messaging.environment.EpisodeSummaryResponse;
import java.io.IOException;

/**
 *
 * @author btanner
 */

/*
 * This lifeTime of the status tracker should be 1 experiment run, through all of the MDPs
 */
public class StatusTracker {

    private int stepsPerMDP = 1;
    private int numMDPs = 1;
    private int currentMDP = 0;
    private long constructTime;
    private long MDPStartTime;
    private long MDPEndTime;
    private long totalRunTime = 0;
    private int stepsThisMDP;
    double averageTimePerStep = 0.0d;
    double averageOverheadPerMDP = 0.0d;
    long mdpRunTimes = 0;
    int totalRunSteps = 0;
    private long EpisodeStartTime;
    private long EpisodeEndTime;
    private long totalEpisodeSteps;
    private long totalEpisodeMilliseconds = 0;
    private double totalReturnOverallForResultsWindow = 0.0d;
    private int totalEpisodesOverallForResultsWindow = 0;
    public long lastResultsSendTime = 0;
    Controller theController = null;
    ResultsFileWriter theResultsWriter = null;
    EnvironmentLogWriter theLogWriter = null;
    AuthToken theAuthToken = null;
    RLEvent theEvent = null;
    int MDPLogFrequency = 1;

    StatusTracker(Controller theController, AuthToken validAuthToken, RLEvent theEvent) {
        this.theController = theController;
        constructTime = System.currentTimeMillis();
        this.theAuthToken = validAuthToken;
        this.theEvent = theEvent;
        this.totalRunTime = 0;
    }

    public void notifyOfMDPLogFrequency(int MDPLogFrequency) {
        this.MDPLogFrequency = MDPLogFrequency;
    }

    //For now we're only going to log the first MDP
    public void startMDPTesting(MDPDetails theMDP) {
        try {
            theResultsWriter = new ResultsFileWriter(theAuthToken, theEvent, theMDP);
            //Add more cool stats here
            String[] props = {"user.name", "java.vm.version", "os.arch", "os.name", "os.version", "sun.cpu.endian", "user.country"};
            for (String thisProp : props) {
                theResultsWriter.appendHeaderTuple(thisProp + ":" + System.getProperty(thisProp));
            }
            //Get the IP address
            try {
                InetAddress addr = InetAddress.getLocalHost();
                // Get IP Address
                byte[] ipAddr = addr.getAddress();
                // Get hostname
                String hostname = addr.getHostName();
                String ipString = "";
                for (int i = 0; i < ipAddr.length; i++) {
                    if (i > 0) {
                        ipString += ".";
                    }
                    ipString += ipAddr[i] & 0xFF;
                }
                theResultsWriter.appendHeaderTuple("ipAddress:" + ipString);
                theResultsWriter.appendHeaderTuple("hostname:" + hostname);
                theResultsWriter.appendHeaderTuple("TimeToSendLastResultsFile:" + lastResultsSendTime);

            } catch (UnknownHostException e) {
            }
            theResultsWriter.appendHeaderTuple("date:" + new Date().toString());

            theLogWriter = new EnvironmentLogWriter(theResultsWriter.getDirLocation(), theMDP.getMDPId());
        } catch (IOException ex) {
            String errorMessage = "Problem writing to results file\n\n";
            errorMessage += ex.getMessage() + "\n\n";
            errorMessage += "Please fix this problem and restart the proving application.";
            theController.dieWithErrorMessage(errorMessage);
        }
    }

    public void endMDPTesting(int totalEpisodes, double totalReturn, int totalSteps) {
        try {
            totalReturnOverallForResultsWindow += totalReturn;
            totalEpisodesOverallForResultsWindow += totalEpisodes;

            theResultsWriter.addSummaryStat("Episodes:" + totalEpisodes);
            theResultsWriter.addSummaryStat("Return:" + totalReturn);
            theResultsWriter.addSummaryStat("Steps:" + totalSteps);

            long millisecondsSinceStart = System.currentTimeMillis() - MDPStartTime;
            theResultsWriter.addSummaryStat("TimeInMilliseconds:" + millisecondsSinceStart);

            theLogWriter.closeFile();
            theResultsWriter.addLog(theLogWriter);
            theResultsWriter.closeFile();
            theResultsWriter.prepForDelivery();

            DebugLogger.log("StatusTracker", "Episodes: " + totalEpisodes);
            DebugLogger.log("StatusTracker", "Return: " + totalReturn);
            DebugLogger.log("StatusTracker", "Steps: " + totalSteps);
            DebugLogger.log("StatusTracker", "TimeInMilliseconds: " + millisecondsSinceStart);

        } catch (IOException ex) {
            String errorMessage = "Problem writing to results file\n\n";
            errorMessage += ex.getMessage() + "\n\n";
            errorMessage += "Please fix this problem and restart the proving application.";
            theController.dieWithErrorMessage(errorMessage);
        }

    }

    public String getResultSummarString() {
        String returnString = "";
        returnString += "Proving Run Summary\n";
        returnString += "************************\n\n";
        returnString += "Event: " + theEvent.name() + "\n\n";
        returnString += "Total Number of Episodes: " + totalEpisodesOverallForResultsWindow + "\n";
        returnString += "Total Return: " + totalReturnOverallForResultsWindow + "\n";
        returnString += "Total Time taken for Proving run: " + makeTimeString(mdpRunTimes) + "\n";

        return returnString;
    }

    private String makeTimeString(long timeInMillis) {
        long extraMilliseconds = timeInMillis % 1000;
        long totalSeconds = timeInMillis / 1000;
        long totalMinutes = totalSeconds / 60;
        long totalHours = totalMinutes / 60;

        long extraSeconds = totalSeconds % 60;
        long extraMinutes = totalMinutes % 60;

        String timeString = "";
        if (totalHours > 0) {
            timeString += totalHours + " hours, ";
        }
        if (extraMinutes > 0) {
            if (extraMinutes < 10) {
                timeString += " ";
            }
            timeString += extraMinutes + " minutes, ";
        }

        if (extraSeconds < 10) {
            timeString += " ";
        }
        timeString += extraSeconds + " seconds";
        //timeString += " and " + extraMilliseconds + " milliseconds";
        return timeString;
    }

    private String updateCurrentTime() {
        long nowTime = System.currentTimeMillis();
        long totalTime = nowTime - constructTime;
        String timeString = makeTimeString(totalTime);
        return timeString;
    }

    private String updateEstimatedTime() {
        int stepsLeft = numMDPs * stepsPerMDP - totalRunSteps;
        int mdpsLeft = numMDPs - currentMDP;

        long timeRemaining = (long) (stepsLeft * averageTimePerStep + mdpsLeft * averageOverheadPerMDP);
        String timeString = makeTimeString(timeRemaining);
        return timeString;
    }

    public void notifyOfStepsPerMDP(int stepsPerMDP) {
        this.stepsPerMDP = stepsPerMDP;
        updateWidgets();
    }

    public void notifyOfMDPs(int totalMDPs) {
        this.numMDPs = totalMDPs;
    }

    private void updateWidgets() {
        theController.updateView(updateCurrentTime(), updateEstimatedTime(), stepsPerMDP, numMDPs, stepsThisMDP, totalRunSteps);
       
        //updateEpisodeProgressBar();
        //updateMDPProgressBar();
        //updateCurrentTime();
        //updateEstimatedTime();
    }

    public void startEpisode() {
        EpisodeStartTime = System.currentTimeMillis();
    }

    public void endEpisode(int episodeNumber, double thisReturn, int thisStepCount) {
        try {
            theResultsWriter.addEpisodeStats(episodeNumber, thisReturn, thisStepCount);
        } catch (IOException ex) {
            String errorMessage = "Problem writing to results file\n\n";
            errorMessage += ex.getMessage() + "\n\n";
            errorMessage += "Please fix this problem and restart the proving application.";
            theController.dieWithErrorMessage(errorMessage);
        }

        //We don't log every episode anymore
        boolean shouldLog = (episodeNumber % MDPLogFrequency) == 0;

        if (shouldLog) {
            //Get the log from the environment
            EpisodeSummaryResponse r = EpisodeSummaryRequest.Execute();
            theLogWriter.addLog(episodeNumber, r.getLogFile());
        }

        EpisodeEndTime = System.currentTimeMillis();
        stepsThisMDP += thisStepCount;
        totalRunSteps += thisStepCount;

        totalEpisodeMilliseconds += EpisodeEndTime - EpisodeStartTime;
        averageTimePerStep = (float) totalEpisodeMilliseconds / (float) totalRunSteps;
        updateWidgets();
    }

    public String getResultsFileLocation() {
        return theResultsWriter.getFileLocation();
    }

    public void startMDP() {
        MDPStartTime = System.currentTimeMillis();
        currentMDP++;
        stepsThisMDP = 0;
    }

    public void endMDP() {
        MDPEndTime = System.currentTimeMillis();
        mdpRunTimes += MDPEndTime - MDPStartTime;

        long totalOverhead = mdpRunTimes - totalEpisodeMilliseconds;
        averageOverheadPerMDP = (float) totalOverhead / (float) currentMDP;

    }
}
