package tetrislogplayer;

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
public class TetrisLogPlayer {

    String filename = "";
    String envName = "";
    private int boardWidth = 0;
    private int boardHeight = 0;
    private int[][] gameState = null;
    BufferedReader fileRead = null;
    private String currentEpisodeString = null;
    private int currentStepInt = 0;
    private int prevStepInt = 0;
    StringTokenizer theTokenizer = null;
    StringTokenizer currentEpisodeTokenizer = null;
    Vector<TetrisBlock> possibleBlocks = null;
    int mdpNum = 0;
    private String teamName = "";
    private boolean endOfFile = false;
    private double currentReward = 0;
    private double rewardExponent = 1;
    int maxSteps=0;
    int curSteps=0;
    Vector<Integer> stepsVector = new Vector<Integer>();
    private int episodeNum=0;
    private int frequency=100;
    
    public double getReward() {
        return currentReward;
    }

    public TetrisLogPlayer(String filename) {
        try {
            this.filename = filename;

            possibleBlocks = new Vector<TetrisBlock>();
            possibleBlocks.add(TetrisBlock.makeLine());
            possibleBlocks.add(TetrisBlock.makeSquare());
            possibleBlocks.add(TetrisBlock.makeTri());
            possibleBlocks.add(TetrisBlock.makeSShape());
            possibleBlocks.add(TetrisBlock.makeZShape());
            possibleBlocks.add(TetrisBlock.makeLShape());
            possibleBlocks.add(TetrisBlock.makeJShape());

            gameState = new int[boardWidth][boardHeight];

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

            Logger.getLogger(TetrisLogPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        setBoardDim();

        this.gameState = new int[boardWidth][boardHeight];
    }

    protected String getTeamName() {
        return this.teamName;
    }

    protected void cleanup() throws Throwable {
        fileRead.close();
    }

    public boolean endOfFile() {
        return this.endOfFile;
    }

    private void setBoardDim() {
        switch (mdpNum) {
            case 0:
                boardWidth = 11;
                boardHeight = 23;
                break;
            case 1:
                boardWidth = 10;
                boardHeight = 22;
                break;
            case 2:
                boardWidth = 8;
                boardHeight = 19;
                break;
            case 3:
                boardWidth = 8;
                boardHeight = 24;
                break;
            case 4:
                boardWidth = 9;
                boardHeight = 19;
                break;
            case 5:
                boardWidth = 8;
                boardHeight = 18;
                break;
            case 6:
                boardWidth = 12;
                boardHeight = 25;
                break;
            case 7:
//                boardWidth = 0;
//                boardHeight = 0;
                break;
            case 8:
                boardWidth = 9;
                boardHeight = 24;
                break;
            case 9:
                boardWidth = 8;
                boardHeight = 23;
                break;
//            case 10:
//                boardWidth = 0;
//                boardHeight = 0;
//                break;
//            case 11:
//                boardWidth = 0;
//                boardHeight = 0;
//                break;
            case 50:
                boardWidth = 11;
                boardHeight = 22;
                rewardExponent = 1.200156d;
                break;
            case 51:
                boardWidth = 12;
                boardHeight = 26;
                rewardExponent = 1.996852d;
                break;
            case 52:
                boardWidth = 6;
                boardHeight = 19;
                rewardExponent = 1.700876d;
                break;
            case 53:
                boardWidth = 6;
                boardHeight = 20;
                rewardExponent = 1.082389d;
                break;
            case 54:
                boardWidth = 8;
                boardHeight = 20;
                rewardExponent = 1.004850d;
                break;
            case 55:
                boardWidth = 11;
                boardHeight = 25;
                rewardExponent = 1.518196d;
                break;
            case 56:
                boardWidth = 10;
                boardHeight = 24;
                rewardExponent = 1.401838d;
                break;
            case 57:
                boardWidth = 7;
                boardHeight = 18;
                rewardExponent = 1.450916d;
                break;
            case 58:
                boardWidth = 7;
                boardHeight = 17;
                rewardExponent = 1.436526d;
                break;
            case 59:
                boardWidth = 12;
                boardHeight = 23;
                rewardExponent = 1.381198d;
                break;
            case 60:
                boardWidth = 11;
                boardHeight = 24;
                break;
            case 61:
                boardWidth = 11;
                boardHeight = 22;
                break;
            case 62:
                boardWidth = 11;
                boardHeight = 23;
                break;
            case 63:
                boardWidth = 6;
                boardHeight = 22;
                break;
            case 64:
                boardWidth = 7;
                boardHeight = 20;
                break;
            case 65:
                boardWidth = 7;
                boardHeight = 20;
                break;
            default:
                boardWidth = 100;
                boardHeight = 100;
                break;
        }
    }

    public int getBoardHeight() {
        return this.boardHeight;
    }

    public int getBoardWidth() {
        return this.boardWidth;
    }

    private void zeroBoard() {
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                gameState[i][j] = 0;
            }
        }
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
            Logger.getLogger(TetrisLogPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void getNextEpisodeLog() {
        curSteps=0;
        zeroBoard();
        if (this.currentEpisodeString == null) {
            this.currentEpisodeString = preProcessReader();
        } else {
            try {
                if (this.fileRead.ready()) {
                    this.currentEpisodeString = this.fileRead.readLine();
                    episodeNum++;
                }
            } catch (IOException ex) {
                System.err.println("There was a problem reading log");
                Logger.getLogger(TetrisLogPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String theReturnString = "";
        theTokenizer = new StringTokenizer(currentEpisodeString, ":");
        while (theTokenizer.hasMoreTokens()) {
            theReturnString = theTokenizer.nextToken();
        }
        this.currentEpisodeTokenizer = new StringTokenizer(theReturnString, "_");
    }

    public int[][] updateStep() {
        curSteps++;
        if (this.currentEpisodeTokenizer == null) {
            getNextEpisodeLog();
            if (this.currentEpisodeTokenizer == null) {
                this.endOfFile = true;
                return this.gameState;
            }

        }

        if (!this.currentEpisodeTokenizer.hasMoreTokens()) {
            //this.gameState = overLayBlock(currentStepInt);
            getNextEpisodeLog();
            if (this.currentEpisodeTokenizer == null) {
                this.endOfFile = true;
                return this.gameState;
            }
        }

        if (currentEpisodeTokenizer.hasMoreTokens()) {
            prevStepInt = currentStepInt;
            String temp = "";
            if ((temp = currentEpisodeTokenizer.nextToken()) != null) {
                try {
                    currentStepInt = Integer.parseInt(temp);
                } catch (NumberFormatException ex) {
                    System.err.println("Broke on:" + ex);
                    this.endOfFile = true;
                }
            }
        }

        if (unpackCurrentY(currentStepInt) < unpackCurrentY(prevStepInt)) {
            writeBlock(prevStepInt);
            checkIfRowAndScore();
        }
        return overLayBlock(currentStepInt);
    }

    void checkIfRowAndScore() {
        int numRowsCleared = 0;
        //Start at the bottom, work way up
        for (int y = boardHeight - 1; y >= 0; --y) {
            if (isRow(y)) {
                removeRow(y);
                numRowsCleared += 1;
                y += 1;
            }
        }
        currentReward += java.lang.Math.pow(numRowsCleared + 0.1, rewardExponent);
    }

    /**
     * Check if a row has been completed at height y.
     * Short circuits, returns false whenever we hit an unfilled spot.
     * @param y
     * @return
     */
    boolean isRow(int y) {
        for (int x = 0; x < boardWidth; ++x) {
            if (gameState[x][y] == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Dec 13/07.  Radkie + Tanner found 2 bugs here.
     * Bug 1: Top row never gets updated when removing lower rows. So, if there are 
     * pieces in the top row, and we clear something, they will float there.
     * @param y
     */
    void removeRow(int y) {
        if (!isRow(y)) {
            System.err.println("In GameState.java remove_row you have tried to remove a row which is not complete. Failed to remove row");
            return;
        }

        for (int x = 0; x < boardWidth; ++x) {
            gameState[x][y] = 0;
        }

        //Copy each row down one (except the top)
        for (int ty = y; ty > 0; --ty) {
            for (int x = 0; x < boardWidth; ++x) {
                gameState[x][ty] = gameState[x][ty - 1];

            }
        }
        //Clear the top row
        for (int x = 0; x < boardWidth; ++x) {
            gameState[x][0] = 0;
        }

    }

    public int[][] getCurrentState() {
        return this.gameState;
    }

    public void writeBlock(int currentStep) {
        int currentBlockId = unpackBlockId(currentStep);
        int currentRotation = unpackCurrentRotation(currentStep);
        int[][] thisPiece = possibleBlocks.get(currentBlockId).getShape(currentRotation);

        for (int y = 0; y < thisPiece[0].length; ++y) {
            for (int x = 0; x < thisPiece.length; ++x) {
                if (thisPiece[x][y] != 0) {
                    int xindex = unpackCurrentX(currentStep) + x;
                    int yindex = unpackCurrentY(currentStep) + y;
                    gameState[xindex][yindex] = currentBlockId + 1;
                }
            }
        }
    }

    public int[][] overLayBlock(int currentStep) {
        int currentBlockId = unpackBlockId(currentStep);
        int currentRotation = unpackCurrentRotation(currentStep);
        int[][] thisPiece = possibleBlocks.get(currentBlockId).getShape(currentRotation);

        int[][] gamestateCopy = new int[boardWidth][boardHeight];
        for (int i = 0; i < boardWidth; i++) {
            gamestateCopy[i] = gameState[i].clone();
        //          for(int j=0; j<boardHeight; j++){
//                gamestateCopy[i][j]=gameState[i][j];
        //        }
        }
        for (int y = 0; y < thisPiece[0].length; ++y) {
            for (int x = 0; x < thisPiece.length; ++x) {
                if (thisPiece[x][y] != 0) {
                    int xindex = unpackCurrentX(currentStep) + x;
                    int yindex = unpackCurrentY(currentStep) + y;
                    gamestateCopy[xindex][yindex] = currentBlockId + 1;
                }
            }
        }
        return gamestateCopy;
    }

    static protected int unpackBlockId(int packedInteger) {
        return extractSomeBitsAsIntFromInt(packedInteger, 3, 0);
    }

    static protected int unpackCurrentRotation(int packedInteger) {
        return extractSomeBitsAsIntFromInt(packedInteger, 2, 3);
    }

    static protected int unpackCurrentX(int packedInteger) {
        return extractSomeBitsAsIntFromInt(packedInteger, 6, 5) - 5;
    }

    static protected int unpackCurrentY(int packedInteger) {
        return extractSomeBitsAsIntFromInt(packedInteger, 6, 11) - 5;
    }

    static protected int unpackCurrentAction(int packedInteger) {
        return extractSomeBitsAsIntFromInt(packedInteger, 3, 17);
    }

    static protected int unpackTerminal(int packedInteger) {
        return extractSomeBitsAsIntFromInt(packedInteger, 1, 20);
    }

    static protected int unpackNewEpisode(int packedInteger) {
        return extractSomeBitsAsIntFromInt(packedInteger, 1, 21);
    }

    public static int extractSomeBitsAsIntFromInt(int B, int amount,
            int offset) {
        int A = 0;
        if (B < 0) {
            throw new IllegalArgumentException("B should be non-negative");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("amount should be non-negative");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("offset should be non-negative");
        }
        if (amount + offset >= 32) {
            throw new IllegalArgumentException("amount + offset should not be larger than 31");
        }
        if (amount == 0) {
            return A;
        }
        // shift down B
        B = B >> offset;
        // mask off higher values from B
        int mask = (1 << amount) - 1;
        B = B & mask;
        // zero out section in A
        A = A & (~mask);
        // or B with A
        A = A | B;
        return A;
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
