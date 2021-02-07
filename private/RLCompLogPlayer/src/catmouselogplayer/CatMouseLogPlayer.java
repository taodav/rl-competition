/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catmouselogplayer;

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
public class CatMouseLogPlayer {

    String filename = "";
    String envName = "";
    BufferedReader fileRead = null;
    private String currentEpisodeString = null;
    private String teamName = "";
    StringTokenizer theTokenizer = null;
    StringTokenizer currentEpisodeTokenizer = null;
    double mouseX = 0.0d;
    double mouseY = 0.0d;
    double catX = 0.0d;
    double catY = 0.0d;
    int currentAction = 0;
    double currentReward = 0.0d;
    int parsePos = 0;
    int mdpNum = 0;
    int curSteps=0;
    int maxSteps=0;
    private int episodeNum=0;
    private int frequency=25;
    Vector<Integer> stepsVector = new Vector<Integer>();

    public CatMouseLogPlayer(String filename) {
        try {
            this.filename = filename;
            boolean found = false;
            this.fileRead = new BufferedReader(new FileReader(new File(this.filename)));
            while (fileRead.ready() && !found) {
                envName = fileRead.readLine();
                if (envName.startsWith("Username")) {
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

            Logger.getLogger(CatMouseLogPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected String getTeamName() {
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
            Logger.getLogger(CatMouseLogPlayer.class.getName()).log(Level.SEVERE, null, ex);
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
                } else {
                    return null;
                }
            } catch (IOException ex) {
                System.err.println("There was a problem reading log: " + ex);
                Logger.getLogger(CatMouseLogPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String theReturnString = "";
        theTokenizer = new StringTokenizer(currentEpisodeString, ":");

        while (theTokenizer.hasMoreTokens()) {
            theReturnString = theTokenizer.nextToken();
        }

        StringTokenizer nextEpisodeTokenizer = new StringTokenizer(theReturnString, "_");
        //pull off the garbage
        if (nextEpisodeTokenizer.hasMoreTokens()) {
            //nextEpisodeTokenizer.nextToken(); // 2       
        }
        return nextEpisodeTokenizer;

    }

    //Returns true if more steps are available (log not exhausted)
    public boolean updateStep() {
        curSteps++;
        if (this.currentEpisodeTokenizer == null) {
            currentEpisodeTokenizer = getNextEpisodeLog();
            if (currentEpisodeTokenizer == null) {
                return false;
            }
            parseFirstStepFromCurrentEpisodeTokenizer();
            return true;
        }

        if (!this.currentEpisodeTokenizer.hasMoreTokens()) {
            //this.gameState = overLayBlock(currentStepInt);
            currentEpisodeTokenizer = getNextEpisodeLog();
            if (currentEpisodeTokenizer == null) {
                return false;
            }
            //check to see if we actually have enough tokens to parse a full
            //episodes.
            if(currentEpisodeTokenizer.countTokens() < 5)
                return false;
            
            parseFirstStepFromCurrentEpisodeTokenizer();
            return true;
        }
        //Parse as many tokens as possible
        while (parseOneStepFromCurrentEpisodeTokenizer());
        return true;

    }

    public double getReward(){
        return this.currentReward;
    }
    
    public double getCatX() {
        return this.catX;
    }

    public double getCatY() {
        return this.catY;
    }

    public double getMouseX() {
        return this.mouseX;
    }

    public double getMouseY() {
        return this.mouseY;
    }

    private boolean parseFirstStepFromCurrentEpisodeTokenizer() {
        //eat 4_d_
        
        String thisToken=currentEpisodeTokenizer.nextToken();
        //If this is false then we just ate the _d!
        if(thisToken.equals("4"))
            currentEpisodeTokenizer.nextToken();

        mouseX = Double.parseDouble(currentEpisodeTokenizer.nextToken());
        //eat d_
       currentEpisodeTokenizer.nextToken();
        mouseY = Double.parseDouble(currentEpisodeTokenizer.nextToken());
        //No D
        catX = Double.parseDouble(currentEpisodeTokenizer.nextToken());
        //eat d_
        currentEpisodeTokenizer.nextToken();
        catY = Double.parseDouble(currentEpisodeTokenizer.nextToken());
        return false;
    }

    //Parsing one element out of a tuple (time step) from the log file
    //There is some global logic that keeps track of what to do with the next variable
    //Returns true if the current tuple has more tokens
    private boolean parseOneStepFromCurrentEpisodeTokenizer() {
        boolean thisStepHasMore = true;

        //First part of the agent_start and agent_step are the same
        parseFirstStepFromCurrentEpisodeTokenizer();
        
        //Eat the a_ and action
        String nextValueToken=currentEpisodeTokenizer.nextToken();
        currentAction=getInt(nextValueToken);


        //Eat the r_ and reward
        nextValueToken=currentEpisodeTokenizer.nextToken();
        currentReward+=getDouble(nextValueToken);

        //Eat the inGoal=_ and whether we are in goal
        currentEpisodeTokenizer.nextToken();
//        currentEpisodeTokenizer.nextToken();

        return false;
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
