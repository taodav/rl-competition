/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mdpparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mradkie
 */
public abstract class abstractResult {

    public double[] episodeReturns;
    private int[] episodeSteps;
    private String key = "";
    private String teamName = "";
    private int mdpNum;
    private int eventNum;
    private int numEpisodes;


    public abstractResult() {
        // so i can call super()... probably dont need to do this but for now this works
    }
    //copy constructor, useful for creating a specific subclass from the generic superclass.
    public abstractResult(abstractResult genericResult) {
        this.episodeReturns = genericResult.episodeReturns.clone();
        this.episodeSteps = genericResult.episodeSteps.clone();
        this.key = genericResult.key;
        this.teamName = genericResult.teamName;
        this.mdpNum = genericResult.mdpNum;
        this.eventNum = genericResult.eventNum;
        this.numEpisodes = genericResult.numEpisodes;
    }

    public abstract double getScoreAt(double percent);
    public abstract int getMaxSteps();
    public void writeToFile(FileWriter theFile) {
        
        String source = "Event:" + this.eventNum + "_MDP:" + this.mdpNum + "_Team:" + this.teamName + "_Score:" + this.getScoreAt(100) + "\n";
        try {
            theFile.write(source);
            theFile.flush();
        } catch (IOException ex) {
            Logger.getLogger(abstractResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String getResultString(){
        String source = "Event:" + this.eventNum + "_MDP:" + this.mdpNum + "_Team:" + this.teamName + "_Score:" + this.getScoreAt(100) + "\n";
        return source;
    }

    public void parseRawFile(String pathToResultFile) {
        parseFile(new File(pathToResultFile));
    }

    public void parseRawFile(File theResultsFile) {
        parseFile(theResultsFile);
    }

    private void parseFile(File resultFile) {
        //initialize variables
        mdpNum = 0;
        eventNum = 0;
        numEpisodes = 0;

        try {
            boolean doneHeader = false;
            BufferedReader fileRead = new BufferedReader(new FileReader(resultFile));
            String nextLine = "";
            //read in the preamble info
            while (fileRead.ready() && !doneHeader) {
                nextLine = fileRead.readLine();
                if (nextLine.startsWith("Username")) {
                    this.teamName = nextLine.substring(9);
                }
                if (nextLine.startsWith("Event:")) {
                    //in log file, the format is EventName:Tetris , we just want 'Tetris'
                    this.eventNum = Integer.parseInt(nextLine.substring(6, nextLine.length()));
                }
                if (nextLine.startsWith("MDP")) {
                    mdpNum = Integer.parseInt(nextLine.substring(4, nextLine.length()));
                }
                if (nextLine.startsWith("Key")) {
                    this.key = nextLine.substring(4);
                }
                if (nextLine.startsWith("#####")) {
                    doneHeader = true;
                }
            }
            boolean doneEpisodes = false;
            StringTokenizer theTokenizer;
            String currentToken = "";
            this.numEpisodes = 0;
            Vector<Double> returnVector = new Vector<Double>();
            Vector<Integer> stepsVector = new Vector<Integer>();
            while (fileRead.ready() && !doneEpisodes) {
                nextLine = fileRead.readLine();
                //check stopping condition
                if (nextLine.startsWith("#####")) {
                    doneEpisodes = true;
                    continue;
                }
                //fields are separated by " " i believe
                theTokenizer = new StringTokenizer(nextLine, " ");
                //iterate through the tokens, each line *should* have all 3 of these tokens
                while (theTokenizer.hasMoreTokens()) {
                    currentToken = theTokenizer.nextToken();
                    if (currentToken.startsWith("Episode")) {
                        this.numEpisodes++;
                    }
                    if (currentToken.startsWith("Return")) {
                        returnVector.add(new Double(Double.parseDouble(currentToken.substring(7))));
                    }
                    if (currentToken.startsWith("Steps")) {
                        stepsVector.add(new Integer(Integer.parseInt(currentToken.substring(6))));
                    }
                }
            }
            //should be done parsing the episode  information
            //copy the vectors to arrays, so they are easier to deal with
            this.episodeReturns = new double[returnVector.size()];
            this.episodeSteps = new int[stepsVector.size()];
            int index = 0;
            for (Double thedouble : returnVector) {
                this.episodeReturns[index] = thedouble.doubleValue();
                index++;
            }
            index = 0;
            for (Integer theint : stepsVector) {
                this.episodeSteps[index] = theint.intValue();
                index++;
            }

        } catch (IOException ex) {
            System.err.println("File " + resultFile.getName() + " not found full path is: " + resultFile.getAbsolutePath());

            Logger.getLogger(abstractResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getRewardAt(int index) {
        assert(index<episodeReturns.length);
        return this.episodeReturns[index];
    }

    public int getStepCountAt(int index) {
        assert(index<episodeSteps.length);
        return episodeSteps[index];
    }

    public String getKey() {
        return this.key;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public int getMDPNum() {
        return this.mdpNum;
    }

    public int getEventNum() {
        return this.eventNum;
    }

    /**
     * Returns the number of episodes for the mdp parsed. This is also the size
     * of the return and stepcount arrays!
     * 
     * @return numEpisodes integer number of episodes
     */
    public int getNumEpisodes() {
        return this.numEpisodes;
    }
}
