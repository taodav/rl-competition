/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helicopterlogplayer;

/**
 *
 * @author mradkie
 */
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
public class HelicopterLogPlayer {

    private String filename = "";
    private String envName = "";
    private BufferedReader fileRead = null;
    private String currentEpisodeString = null;
    private StringTokenizer theTokenizer = null;
    private StringTokenizer currentEpisodeTokenizer = null;
    private boolean inGoal = false;
    private String teamName = "";
    public int mdpNum = 0;
    private char curDim = 'x';
    private int xCount = 0;
    private int yCount = 0;
    private int zCount = 0;
    private double currentReward=0;
    private int episodeNum=0;
    
    private int countTokens=0;
    private int frequency=200;
    Vector<Integer> stepsVector = new Vector<Integer>();
    // 13 dimensinal observation: x1,x2,x3,x4,y1,y2,y3,y4,z1,z2,z3,z4,w
    private double observations[];

    // upper bounds on values state variables can take on (required by rl_glue to be put into a string at environment initialization)
    static double MAX_VEL = 5.0; // m/s
    static double MAX_POS = 20.0;
    static double MAX_RATE = 2 * 3.1415 * 2;
    static double MAX_QUAT = 1.0;
    static double mins[] = {-MAX_VEL, -MAX_VEL, -MAX_VEL, -MAX_POS, -MAX_POS, -MAX_POS, -MAX_RATE, -MAX_RATE, -MAX_RATE, -MAX_QUAT, -MAX_QUAT, -MAX_QUAT, -MAX_QUAT};
    static double maxs[] = {MAX_VEL, MAX_VEL, MAX_VEL, MAX_POS, MAX_POS, MAX_POS, MAX_RATE, MAX_RATE, MAX_RATE, MAX_QUAT, MAX_QUAT, MAX_QUAT, MAX_QUAT};

    int maxSteps=0;
    int curSteps=0;
    
    public HelicopterLogPlayer(String filename) {
        super();
        observations = new double[13];

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

            Logger.getLogger(HelicopterLogPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    protected String getTeamName(){
        return this.teamName;
    }

    protected void cleanup() throws Throwable {
        fileRead.close();
    }

    public double getMinAt(int index) {
        return mins[index];
    }

    public double getMaxAt(int index) {
        return maxs[index];
    }
    
    public double[] getObservation(){
        return this.observations;
    }
    // make the reader point to the right place, and return the last string read,
    //which should correspond to the first log summary
    private String preProcessReader() {
        String nextLine = "";
        try {
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
            Logger.getLogger(HelicopterLogPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public StringTokenizer getNextEpisodeLog() {
        curSteps=0;
        if (this.currentEpisodeString == null) {
            this.currentEpisodeString = preProcessReader();
        } else {
            try {
                if (this.fileRead.ready()) {
                    this.currentEpisodeString = this.fileRead.readLine();
                } else {
                    return null;
                }
            } catch (IOException ex) {
                System.err.println("There was a problem reading log: " + ex);
                Logger.getLogger(HelicopterLogPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String theReturnString = "";
        theTokenizer = new StringTokenizer(currentEpisodeString, ":");

        while (theTokenizer.hasMoreTokens()) {
            theReturnString = theTokenizer.nextToken();
        }

        StringTokenizer nextEpisodeTokenizer = new StringTokenizer(theReturnString, "_");
        //pull off the garbage       

        return nextEpisodeTokenizer;

    }

    public boolean updateStep() {
        curSteps++;
        try{
        if (this.currentEpisodeTokenizer == null) {
            currentEpisodeTokenizer = getNextEpisodeLog();
            if (currentEpisodeTokenizer == null) {
                return false;
            }
        }

        if (!this.currentEpisodeTokenizer.hasMoreTokens()) {
            episodeNum++;
            //this.gameState = overLayBlock(currentStepInt);
            currentEpisodeTokenizer = getNextEpisodeLog();
            if (currentEpisodeTokenizer == null) {
                return false;
            }
        }
        }catch(StringIndexOutOfBoundsException ex){
            return false;
        }
        while (parseToken());

        return true;

    }

    private boolean parseToken() {
        boolean thisStepHasMore = true;
        
        if (this.currentEpisodeTokenizer.hasMoreTokens()) {
            String thisToken;
            try{
                thisToken = this.currentEpisodeTokenizer.nextToken();
            }catch(StringIndexOutOfBoundsException ex){
                System.out.println("caught an exception, wtf");
                return false;
            }
            String theNumber = "";

            
            if(thisToken != null && thisToken.length() == 0) return true;
            //parse the double into the right place
            if (thisToken.length() > 0 && thisToken.length() < 2) {// its the _x_ token
                this.curDim = thisToken.charAt(0);
                return true;
            }

            // check if terminal
            if (thisToken.contains("terminal")) {
                this.inGoal = true;
                return false;
            }
            if (thisToken.startsWith("r")){
                currentReward+=Double.parseDouble(thisToken.substring(2));
            }

            char tempDim = thisToken.charAt(thisToken.length() - 1); // grab last char from token
            theNumber = thisToken.substring(0, thisToken.length() - 1);

            switch (this.curDim) {
                case 'x':
                    switch (xCount) {
                        case 0:
                            observations[0] = doubleParser(theNumber);
                            break;
                        case 1:
                            observations[1] = doubleParser(theNumber);
                            break;

                        case 2:
                            observations[2] = doubleParser(theNumber);
                            break;

                        case 3:
                            observations[3] = doubleParser(theNumber);
                            break;
                    }

                    xCount = ++xCount % 4;
                    break;
                case 'y':
                    switch (yCount) {
                        case 0:
                            observations[4] = doubleParser(theNumber);
                            break;
                        case 1:
                            observations[5] = doubleParser(theNumber);
                            break;

                        case 2:
                            observations[6] = doubleParser(theNumber);
                            break;

                        case 3:
                            observations[7] = doubleParser(theNumber);
                            break;
                    }
                    yCount = ++yCount % 4;
                    break;
                case 'z':
                    switch (zCount) {
                        case 0:
                            observations[8] = doubleParser(theNumber);
                            break;
                        case 1:
                            observations[9] = doubleParser(theNumber);
                            break;

                        case 2:
                            observations[10] = doubleParser(theNumber);
                            break;

                        case 3:
                            observations[11] = doubleParser(theNumber);
                            break;
                    }
                    zCount = ++zCount % 4;
                    break;
                case 'w':
                    observations[12] = doubleParser(thisToken);
                    tempDim = 'n';
            }

            this.curDim = tempDim;
        } else {
            thisStepHasMore = false;
        }

        return thisStepHasMore;
    }

    private double doubleParser(String input) {
        if (input == null || input.length() < 1) {
            return 0.0d;
        } // perhaps dont want to return a valid number? not sure
        double theReturn = 0.0d;
        try {
            theReturn = Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            doubleParser(input.substring(0, input.length() - 1));//cut off the last char and try again
        }
        return theReturn;
    }
    public double getReward(){
        return this.currentReward;
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

