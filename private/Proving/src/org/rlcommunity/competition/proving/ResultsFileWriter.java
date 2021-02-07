/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rlcommunity.competition.proving;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rlcommunity.competition.phoneHomeInterface.AuthToken;
import org.rlcommunity.competition.phoneHomeInterface.MDPDetails;
import org.rlcommunity.competition.phoneHomeInterface.RLEvent;

/**
 *
 * @author btanner
 */
public class ResultsFileWriter {

    final private String pathToResults = "../system/proving/results";
    FileWriter theWriter = null;
    String theFileLocation = null;
    String theDirectory = null;
    boolean summaryStatsStarted = false;
    boolean summaryStatsCompleted = false;
    boolean headerCompleted = false;
    private boolean episodeStatsStarted = false;
    private boolean episodeStatsCompleted = false;


    void appendHeaderTuple(String theTuple) throws IOException {
        if (headerCompleted) {
            System.err.println("Tried to added HeaderTuple:" + theTuple + " but header already closed");
            return;
        }
        theWriter.write(theTuple + "\n");
    }

    public ResultsFileWriter(AuthToken validAuthToken, RLEvent theEvent, MDPDetails theMDP) throws IOException {
        String fileName = theMDP.getMDPId() + ".results";
        theDirectory = theEvent + "/" + theMDP.getKey();

        theFileLocation = pathToResults + "/" + theDirectory + "/" + fileName;

        File theDir = new File(pathToResults + "/" + theDirectory + "/");
        boolean result = theDir.mkdirs();
        File theFile = new File(theFileLocation);
        theWriter = new FileWriter(theFile);
        theWriter.write("Username:" + validAuthToken.getUsername() + "\n");
        theWriter.write("Event:" + theEvent.id() + "\n");
        theWriter.write("EventName:" + theEvent + "\n");
        theWriter.write("MDP:" + theMDP.getMDPId() + "\n");
        theWriter.write("Key:" + theMDP.getKey() + "\n");

    }

      void endHeaderIfNecessary() throws IOException {
        if (!headerCompleted) {
            headerCompleted = true;
            theWriter.write("#####\n");
        }
    }
  void startEpisodeStatsIfNecessary() throws IOException {
        if (!episodeStatsStarted) {
            endHeaderIfNecessary();
            episodeStatsStarted = true;
        }
    }

    void endEpisodeStatsIfNecessary() throws IOException {
        if (!episodeStatsCompleted) {
            startEpisodeStatsIfNecessary();
            episodeStatsCompleted = true;
            theWriter.write("#####\n");
        }

    }

    void addEpisodeStats(int whichEpisodeNumber, double thisEpisodeReturn, int thisEpisodeStepCount) throws IOException {
        startEpisodeStatsIfNecessary();
        theWriter.write("EpisodeNumber:" + whichEpisodeNumber + " Return:" + thisEpisodeReturn + " Steps:" + thisEpisodeStepCount + "\n");
        theWriter.flush();
    }

    private void startSummaryStatsIfNecessary() throws IOException {

        if (!summaryStatsStarted) {
            endEpisodeStatsIfNecessary();
            summaryStatsStarted = true;
        }
    }

    private void endSummaryStatsIfNecessary() throws IOException {
        if (!summaryStatsCompleted) {
        	startSummaryStatsIfNecessary();
          summaryStatsCompleted = true;
          theWriter.write("\n");
        }
    }

    public void addSummaryStat(String pair) throws IOException {
        startSummaryStatsIfNecessary();
        theWriter.write(pair + " ");
    }

    public void closeFile() throws IOException {
        endSummaryStatsIfNecessary();
        theWriter.write("-- File Closed Normally --");
        theWriter.close();
    }

    public String getFileLocation() {
        return theFileLocation;
    }

    public String getDirLocation() {
        return theDirectory;
    }

    void prepForDelivery() {
        FileGZipper.doCompressFile(theFileLocation);
        //Delete original file on exit (this is so I can easily check it by not closing the app)
        File f=new File(theFileLocation);
        f.deleteOnExit();
        theFileLocation += ".gz";
    }

    void addLog(EnvironmentLogWriter theLogWriter) throws IOException {
        endSummaryStatsIfNecessary();
        theWriter.write("#####\n");
        BufferedReader in = new BufferedReader(new FileReader(theLogWriter.getFileLocation()));
        char[] buffer=new char[1000000];
        while (in.ready()) {
            int amountRead=in.read(buffer);
            theWriter.write(buffer, 0, amountRead);
        }
        in.close();
        File f=new File(theLogWriter.getFileLocation());
        f.deleteOnExit();
    }
}
