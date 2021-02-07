/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gridworldlogplayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author mradkie
 */
public class GridworldLogPlayer {
String filename = "";
    String envName = "";
    BufferedReader fileRead = null;
    private String currentEpisodeString = null;
    private String teamName = "";
    StringTokenizer theTokenizer = null;
    StringTokenizer currentEpisodeTokenizer = null;
    double currentPos = 0.0d;
    double currentVel = 0.0d;
    int currentAction = 0;
    double currentReward = 0.0d;

    boolean parsePos = true;
    int mdpNum = 0;
    int maxSteps=0;
    int curSteps=0;
    private int episodeNum=0;
    private int frequency=25;
    
    Vector<Integer> stepsVector = new Vector<Integer>();
    public GridworldLogPlayer(String filename) {
        try {
            this.filename = filename;
            boolean found = false;
            this.fileRead = new BufferedReader(new FileReader(new File(this.filename)));
            while (fileRead.ready() && !found) {
                envName = fileRead.readLine();
                                if( envName.startsWith("Username")){
                    this.teamName = envName.substring(9);
                }
                if (envName.startsWith("EventName")) {
                    //in log file, the format is EventName:Tetris , we just want 'Tetris'
                    this.envName = envName.substring(10, envName.length());
                }
                if (envName.startsWith("MDP")) {
                    mdpNum = Integer.parseInt(envName.substring(4, envName.length()));
                    found = true;
                }
            }
        } catch (IOException ex) {
            System.err.println("File " + this.filename + " not found, pwd is: " + System.getProperty("user.dir"));

            Logger.getLogger(GridworldLogPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    protected String getTeamName(){
        return this.teamName;
    }

    // make the reader point to the right place, and return the last string read,
    //which should correspond to the first log summary
    private String preProcessReader() {
        String nextLine = "";
        try {
            if (!fileRead.ready()) {
                fileRead.close();
                System.exit(0);
            }
            StringTokenizer tempTokenizer;
            String currentToken = "";
            
            while (fileRead.ready()) {
                nextLine = fileRead.readLine();
                if (nextLine.contains("theLog")) {
                    return nextLine;
                }
                tempTokenizer = new StringTokenizer(nextLine, " ");
                //iterate through the tokens, each line *should* have all 3 of these tokens
                while (tempTokenizer.hasMoreTokens()) {
                    currentToken = tempTokenizer.nextToken();
                    if (currentToken.startsWith("Steps")) {
                        stepsVector.add(new Integer(Integer.parseInt(currentToken.substring(6))));
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(GridworldLogPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected void cleanup() throws Throwable {
        fileRead.close();
    }

    public StringTokenizer getNextEpisodeLog() {
        curSteps=0;
        if (this.currentEpisodeString == null) {
            this.currentEpisodeString = preProcessReader();
        } else {
            try {
                if (this.fileRead.ready()) {
                    this.currentEpisodeString = this.fileRead.readLine();
                    episodeNum++;
                }else{
                    return null;
                }
            } catch (IOException ex) {
                System.err.println("There was a problem reading log: "+ex);
                Logger.getLogger(GridworldLogPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String theReturnString = "";
        theTokenizer = new StringTokenizer(currentEpisodeString, ":");

        while (theTokenizer.hasMoreTokens()) {
            theReturnString = theTokenizer.nextToken();
        }
        
        StringTokenizer nextEpisodeTokenizer = new StringTokenizer(theReturnString, "_");
        //pull off the garbage
        if (nextEpisodeTokenizer.hasMoreTokens()) nextEpisodeTokenizer.nextToken(); // 2       

        return nextEpisodeTokenizer;

    }

    //Returns true if more steps are available (log not exhausted)
    public boolean updateStep() {
        curSteps++;
        if (this.currentEpisodeTokenizer == null) {
            currentEpisodeTokenizer=getNextEpisodeLog();
            if(currentEpisodeTokenizer==null)
                return false;
        }

        if (!this.currentEpisodeTokenizer.hasMoreTokens()) {
            //this.gameState = overLayBlock(currentStepInt);
            currentEpisodeTokenizer=getNextEpisodeLog();
            if(currentEpisodeTokenizer==null)
                return false;
        }
        //Parse as many tokens as possible
        while (parseToken());
        return true;
        
    }

    public double getPos() {
        return this.currentPos;
    }

    public double getVel() {
        return this.currentVel;
    }

    //Parsing one element out of a tuple (time step) from the log file
    //There is some global logic that keeps track of what to do with the next variable
    //Returns true if the current tuple has more tokens
    private boolean parseToken() {
        boolean thisStepHasMore=true;

        if (this.currentEpisodeTokenizer.hasMoreTokens()) {
            String thisToken = this.currentEpisodeTokenizer.nextToken();
            //parse the double into the right place
            if (thisToken.startsWith("d")) {
                if (this.parsePos) {
                    this.currentPos = Double.parseDouble(currentEpisodeTokenizer.nextToken());
                } else {
                    this.currentVel = Double.parseDouble(currentEpisodeTokenizer.nextToken());
                }
                this.parsePos = !this.parsePos;
            } else if (thisToken.startsWith("a")) {
            //this.currentAction = getInt(currentEpisodeTokenizer.nextToken());
            } else if (thisToken.startsWith("r")) {
                this.currentReward += getDouble(thisToken);

            //This condition stops us at the end of the episode
            } else if (thisToken.startsWith("inGoal")) {
                thisStepHasMore = false;
            }
        }else{
            //No more tokens, stop parsing!
            thisStepHasMore=false;
        }
        return thisStepHasMore;
    }
    public double getReward(){
        return this.currentReward;
    }
    public double getDouble(String input) {
        StringTokenizer tempTokenizer = new StringTokenizer(input, "=");
        String tempToken = "";
        while (tempTokenizer.hasMoreTokens()) {
            tempToken = tempTokenizer.nextToken();
        }

        return Double.parseDouble(tempToken);
    }

    public int getInt(String input) {
        StringTokenizer tempTokenizer = new StringTokenizer(input, "=");
        String tempToken = "";
        while (tempTokenizer.hasMoreTokens()) {
            tempToken = tempTokenizer.nextToken();
        }

        return Integer.parseInt(tempToken);
    }
    public int getCurSteps(){
        return this.curSteps;
    }
    public int getMaxSteps(){
        int index = episodeNum*frequency;
        if(index >= stepsVector.size())
            return stepsVector.lastElement();
        return stepsVector.elementAt(index);
    }
}
