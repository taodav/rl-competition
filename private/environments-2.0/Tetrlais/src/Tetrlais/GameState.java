/* Tetris Domain
 * Copyright (C) 2007, Brian Tanner brian@tannerpages.com (http://brian.tannerpages.com/)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. */
package Tetrlais;

import java.util.Random;
import java.util.Vector;

import rlVizLib.utilities.UtilityShop;
import rlglue.types.Observation;

public class GameState {

    static final int defaultWidth = 10;
    static final int defaultHeight = 20;
    /*Action values*/
    static final int LEFT = 0; /*Action value for a move left*/

    static final int RIGHT = 1; /*Action value for a move right*/

    static final int CW = 2; /*Action value for a clockwise rotation*/

    static final int CCW = 3; /*Action value for a counter clockwise rotation*/

    static final int NONE = 4; /*The no-action Action*/

    static final int FALL = 5; /* fall down */

    private Random randomGenerator = new Random();

    public GameState() {
        this(defaultWidth, defaultHeight, new Vector<TetrlaisPiece>(), null, 1.0d);
    }

    public static void main(String[] args) {
        GameState g = new GameState();
    }

    public GameState(int width, int height, Vector<TetrlaisPiece> possibleBlocks, double[] pieceWeights, double rewardExponent) {
        this.worldWidth = width;
        this.worldHeight = height;
        this.possibleBlocks = possibleBlocks;
        worldState = new int[width * height];

        this.pieceWeights = pieceWeights;
        this.rewardExponent = rewardExponent;
        if (this.pieceWeights == null) {
            this.pieceWeights = new double[possibleBlocks.size()];
            for (int i = 0; i < this.pieceWeights.length; i++) {
                this.pieceWeights[i] = 1.0d;
            }
        }
        reset();
    }

    public void reset() {
        currentX = worldWidth / 2 - 1;
        currentY = 0;
        score = 0;
        for (int i = 0; i < worldState.length; i++) {
            worldState[i] = 0;
        }
        currentRotation = 0;
        is_game_over = false;
    }

    public Observation get_observation() {
        //eget observation with only the state space
        try {
            int[] worldObservation = new int[worldState.length];

            for (int i = 0; i < worldObservation.length; i++) {
                worldObservation[i] = worldState[i];
            }

            writeCurrentBlock(worldObservation);
            Observation o = new Observation(worldObservation.length + possibleBlocks.size() + 2, 0);
            for (int i = 0; i < worldObservation.length; i++) {
                if (worldObservation[i] == 0) {
                    o.intArray[i] = 0;
                } else {
                    o.intArray[i] = 1;
                }
            }
            for (int j = 0; j < possibleBlocks.size(); ++j) {
                o.intArray[worldObservation.length + j] = 0;
            }
            //Set the bit vector value for which block is currently following
            o.intArray[worldObservation.length + currentBlockId] = 1;

            o.intArray[o.intArray.length - 2] = getHeight();
            o.intArray[o.intArray.length - 1] = getWidth();
            return o;

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: ArrayIndexOutOfBoundsException in GameState::get_observation");
            System.err.println("Error: The Exception was: " + e);
            Thread.dumpStack();
            System.err.println("Current X is: " + currentX + " Current Y is: " + currentY + " Rotation is: " + currentRotation + " blockId: " + currentBlockId);
            System.err.println("Not realy sure what to do, so crashing.  Sorry.");
            System.exit(1);
            //Can never happen
            return null;
        }
    }

    int packStateToInteger(int theAction, int terminal, boolean episodeStart) {
        int packedInteger = 0;
        //Things to pack are:
        //1) Block ID - 3 bits
        int nextOffset = 0;

        int thisSize = 3;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(currentBlockId, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;

        //2) currentRotation - 2 bits
        thisSize = 2;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(currentRotation, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        //Total = 5

        //3) currentX - 6 bits
        thisSize = 6;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(currentX + 5, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        //Total = 11

        //4) curretY - 6 bits
        thisSize = 6;
        //Y can be negative, can't have that!
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(currentY + 5, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        //Total = 17

        //5) action - 3 bits
        thisSize = 3;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(theAction, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        //Total = 20

        //6) teriminal - 1 bit
        thisSize = 1;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(terminal, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        //Total = 21

        int newEpisode = 0;
        if (episodeStart) {
            newEpisode = 1;
        }
        //6) newEpisode - 1 bit
        thisSize = 1;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(newEpisode, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        //Total = 21


        return packedInteger;
    }

    static protected int unpackBlockId(int packedInteger) {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 3, 0);
    }

    static protected int unpackCurrentRotation(int packedInteger) {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 2, 3);
    }

    static protected int unpackCurrentX(int packedInteger) {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 6, 5) - 5;
    }

    static protected int unpackCurrentY(int packedInteger) {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 6, 11) - 5;
    }

    static protected int unpackCurrentAction(int packedInteger) {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 3, 17);
    }

    static protected int unpackTerminal(int packedInteger) {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 1, 20);
    }

    static protected int unpackNewEpisode(int packedInteger) {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 1, 21);
    }

    void stringSerialize(StringBuilder b) {
        b.append("_mobile_");
        b.append(blockMobile);
        b.append("_b_");
        b.append(currentBlockId);
        b.append("_r_");
        b.append(currentRotation);
        b.append("_x_");
        b.append(currentX);
        b.append("_y_");
        b.append(currentY);
        b.append("_s_");
        b.append(score);
//       b.append("_state_");
//       for(int i=0;i<worldState.length;i++)b.append(worldState[i]);
    }

    @Deprecated
    String stringSerialize() {
        StringBuilder b = new StringBuilder();
        stringSerialize(b);
        return b.toString();
    }

    private void writeCurrentBlock(int[] game_world) {
        int[][] thisPiece = possibleBlocks.get(currentBlockId).getShape(currentRotation);

        for (int y = 0; y < thisPiece[0].length; ++y) {
            for (int x = 0; x < thisPiece.length; ++x) {
                if (thisPiece[x][y] != 0) {
                    //Writing currentBlockId +1 because blocks are 0 indexed, and we want spots to be
                    //0 if they are clear, and >0 if they are not.
                    int linearIndex = calculateLinearArrayPosition(currentX + x, currentY + y);
                    game_world[linearIndex] = currentBlockId + 1;
                }
            }
        }

    }

    public boolean gameOver() {
        return is_game_over;
    }

    /* This code applies the action, but doesn't do the default fall of 1 square */
    public void take_action(int theAction) {

        if (theAction > 5 || theAction < 0) {
            System.err.println("Invalid action selected in Tetrlais: " + theAction);
            //Random >0 < 6
            theAction = randomGenerator.nextInt(6);
        }

        int nextRotation = currentRotation;
        int nextX = currentX;
        int nextY = currentY;

        switch (theAction) {
            case CW:
                nextRotation = (currentRotation + 1) % 4;
                break;
            case CCW:
                nextRotation = (currentRotation - 1);
                if (nextRotation < 0) {
                    nextRotation = 3;
                }
                break;
            case LEFT:
                nextX = currentX - 1;
                break;
            case RIGHT:
                nextX = currentX + 1;
                break;
            case FALL:
                nextY = currentY;

                boolean isInBounds = true;
                boolean isColliding = false;

                //Fall until you hit something then back up once
                while (isInBounds && !isColliding) {
                    nextY++;
                    isInBounds = inBounds(nextX, nextY, nextRotation);
                    if (isInBounds) {
                        isColliding = colliding(nextX, nextY, nextRotation);
                    }
                }
                nextY--;
            default:
                break;
        }
        //Check if the resulting position is legal. If so, accept it.
        //Otherwise, don't change anything
        if (inBounds(nextX, nextY, nextRotation)) {
            if (!colliding(nextX, nextY, nextRotation)) {
                currentRotation = nextRotation;
                currentX = nextX;
                currentY = nextY;
            }
        }

    }

    /**
     * Calculate the learn array position from (x,y) components based on 
     * worldWidth.
     * Package level access so we can use it in tests.
     * @param x
     * @param y
     * @return
     */
    int calculateLinearArrayPosition(int x, int y) {
        return y * worldWidth + x;
    }

    /**
     * Check if any filled part of the 5x5 block array is either out of bounds
     * or overlapping with something in wordState
     * @param checkX X location of the left side of the 5x5 block array
     * @param checkY Y location of the top of the 5x5 block array
     * @param checkOrientation Orientation of the block to check
     * @return
     */
    private boolean colliding(int checkX, int checkY, int checkOrientation) {
        int[][] thePiece = possibleBlocks.get(currentBlockId).getShape(checkOrientation);
        try {

            for (int y = 0; y < thePiece[0].length; ++y) {
                for (int x = 0; x < thePiece.length; ++x) {
                    if (thePiece[x][y] != 0) {
                        //First check if a filled in piece of the block is out of bounds!
                        //if the height of this square is negative or the X of 
                        //this square is negative, then we're "colliding" with the wall
                        if (checkY + y < 0 || checkX + x < 0) {
                            return true;
                        }

                        //if the height of this square is more than the board size or the X of 
                        //this square is more than the board size, then we're "colliding" with the wall
                        if (checkY + y >= worldHeight || checkX + x >= worldWidth) {
                            return true;
                        }

                        //Otherwise check if it hits another piece
                        int linearArrayIndex = calculateLinearArrayPosition(checkX + x, checkY + y);
                        if (worldState[linearArrayIndex] != 0) {
                            return true;
                        }
                    }
                }
            }
            return false;

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: ArrayIndexOutOfBoundsException in GameState::colliding called with params: " + checkX + " , " + checkY + ", " + checkOrientation);
            System.err.println("Error: The Exception was: " + e);
            Thread.dumpStack();
            System.err.println("Returning true from colliding to help save from error");
            System.err.println("Setting is_game_over to true to hopefully help us to recover from this problem");
            is_game_over = true;
            return true;
        }
    }

    private boolean collidingCheckOnlySpotsInBounds(int checkX, int checkY, int checkOrientation) {
        int[][] thePiece = possibleBlocks.get(currentBlockId).getShape(checkOrientation);
        try {

            for (int y = 0; y < thePiece[0].length; ++y) {
                for (int x = 0; x < thePiece.length; ++x) {
                    if (thePiece[x][y] != 0) {

                        //This checks to see if x and y are in bounds
                        if ((checkX + x >= 0 && checkX + x < worldWidth && checkY + y >= 0 && checkY + y < worldHeight)) {
                            //This array location is in bounds  
                            //Check if it hits another piece
                            int linearArrayIndex = calculateLinearArrayPosition(checkX + x, checkY + y);
                            if (worldState[linearArrayIndex] != 0) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: ArrayIndexOutOfBoundsException in GameState::collidingCheckOnlySpotsInBounds called with params: " + checkX + " , " + checkY + ", " + checkOrientation);
            System.err.println("Error: The Exception was: " + e);
            Thread.dumpStack();
            System.err.println("Returning true from colliding to help save from error");
            System.err.println("Setting is_game_over to true to hopefully help us to recover from this problem");
            is_game_over = true;
            return true;
        }
    }

    /**
     * This function checks every filled part of the 5x5 block array and sees if
     * that piece is in bounds if the entire block is sitting at (checkX,checkY)
     * on the board.
     * @param checkX X location of the left side of the 5x5 block array
     * @param checkY Y location of the top of the 5x5 block array
     * @param checkOrientation Orientation of the block to check
     * @return
     */
    private boolean inBounds(int checkX, int checkY, int checkOrientation) {
        try {
            int[][] thePiece = possibleBlocks.get(currentBlockId).getShape(checkOrientation);

            for (int y = 0; y < thePiece[0].length; ++y) {
                for (int x = 0; x < thePiece.length; ++x) {
                    if (thePiece[x][y] != 0) {
                        //if ! (thisX is non-negative AND thisX is less than width
                        // AND thisY is non-negative AND thisY is less than height)
                        //Through demorgan's law is
                        //if thisX is negative OR thisX is too big or 
                        //thisY is negative OR this Y is too big
                        if (!(checkX + x >= 0 && checkX + x < worldWidth && checkY + y >= 0 && checkY + y < worldHeight)) {
                            return false;
                        }
                    }
                }
            }

            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: ArrayIndexOutOfBoundsException in GameState::inBounds called with params: " + checkX + " , " + checkY + ", " + checkOrientation);
            System.err.println("Error: The Exception was: " + e);
            Thread.dumpStack();
            System.err.println("Returning false from inBounds to help save from error.  Not sure if that's wise.");
            System.err.println("Setting is_game_over to true to hopefully help us to recover from this problem");
            is_game_over = true;
            return false;
        }

    }

    private boolean nextInBounds() {
        return inBounds(currentX, currentY + 1, currentRotation);
    }

    private boolean nextColliding() {
        return colliding(currentX, currentY + 1, currentRotation);
    }

    /*Ok, at this point, they've just taken their action.  We now need to make them fall 1 spot, and check if the game is over, etc */
    public void update() {
        // Sanity check.  The game piece should always be in bounds.
        if (!inBounds(currentX, currentY, currentRotation)) {
            System.err.println("In GameState.Java the Current Position of the board is Out Of Bounds... Consistency Check Failed");
        }

        //Need to be careful here because can't check nextColliding if not in bounds

        //onSomething means we're basically done with this piece
        boolean onSomething = false;
        if (!nextInBounds()) {
            onSomething = true;
        }
        if (!onSomething) {
            if (nextColliding()) {
                onSomething = true;
            }
        }

        if (onSomething) {
            blockMobile = false;
            writeCurrentBlock(worldState);
            checkIfRowAndScore();
        } else {
            //fall
            currentY += 1;
        }
    }

    public void spawn_block() {
        blockMobile = true;

        currentBlockId = 0;
        //Start Shivaram (Brian updated)
        double total = 0;
        for (int i = 0; i < pieceWeights.length; i++) {
            total += pieceWeights[i];
        }

        //Pick a random number between 0 and total
        double r = randomGenerator.nextDouble() * total;

        //Checking a cumulative prob function to see when to stop
        while (pieceWeights[currentBlockId] < r && (currentBlockId < 6)) {
            r -= pieceWeights[currentBlockId];
            currentBlockId++;
        }
        //End Shivaram
        //If none above matched, currentBlockId will be 6

        currentRotation = 0;
        currentX = (worldWidth / 2) - 2;
        currentY = -4;
//        while (colliding(currentX, currentY, currentRotation) && currentY < 0) {
//Colliding checks both bounds and piece/piece collisions.  We really only want the piece to be falling
//If the filled parts of the 5x5 piece are out of bounds.. IE... we want to stop falling when its all on the screen
        boolean hitOnWayIn = false;
        while (!inBounds(currentX, currentY, currentRotation)) {
            //We know its not in bounds, and we're bringing it in.  Let's see if it would have hit anything...
            hitOnWayIn = collidingCheckOnlySpotsInBounds(currentX, currentY, currentRotation);
            currentY++;
        }
        is_game_over = colliding(currentX, currentY, currentRotation) || hitOnWayIn;
        if (is_game_over) {
            blockMobile = false;
        }
    }

//Shivaram's code
    void checkIfRowAndScore() {
        int numRowsCleared = 0;

        //Start at the bottom, work way up
        for (int y = worldHeight - 1; y >= 0; --y) {
            if (isRow(y)) {
                removeRow(y);
                numRowsCleared += 1;
                y += 1;
            }
        }

        //Shivaram :numerical error may occur without 0.1 addition.
        //Brian : would casting numRowsCleared to double fix this? Weird?
        score += java.lang.Math.pow(numRowsCleared + 0.1, rewardExponent);
    }
//End Shivaram
    /**
     * Check if a row has been completed at height y.
     * Short circuits, returns false whenever we hit an unfilled spot.
     * @param y
     * @return
     */
    boolean isRow(int y) {
        for (int x = 0; x < worldWidth; ++x) {
            int linearIndex = calculateLinearArrayPosition(x, y);
            if (worldState[linearIndex] == 0) {
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

        for (int x = 0; x < worldWidth; ++x) {
            int linearIndex = calculateLinearArrayPosition(x, y);
            worldState[linearIndex] = 0;
        }

        //Copy each row down one (except the top)
        for (int ty = y; ty > 0; --ty) {
            for (int x = 0; x < worldWidth; ++x) {
                int linearIndexTarget = calculateLinearArrayPosition(x, ty);
                int linearIndexSource = calculateLinearArrayPosition(x, ty - 1);
                worldState[linearIndexTarget] = worldState[linearIndexSource];
            }
        }

        //Clear the top row
        for (int x = 0; x < worldWidth; ++x) {
            int linearIndex = calculateLinearArrayPosition(x, 0);
            worldState[linearIndex] = 0;
        }

    }

    public int get_score() {
        return score;
    }

    public int getWidth() {
        return worldWidth;
    }

    public int getHeight() {
        return worldHeight;
    }

    public int[] getNumberedStateSnapShot() {
        int[] numberedStateCopy = new int[worldState.length];
        for (int i = 0; i < worldState.length; i++) {
            numberedStateCopy[i] = worldState[i];
        }
        writeCurrentBlock(numberedStateCopy);
        return numberedStateCopy;

    }
//    public int[] getWorldState() {
//        return worldObservation;
//    }
    public int getCurrentPiece() {
        return currentBlockId;
    }

    public void printState() {
        int index = 0;
        for (int i = 0; i < worldHeight - 1; i++) {
            for (int j = 0; j < worldWidth; j++) {
                System.out.print(worldState[i * worldWidth + j]);
            }
            System.out.print("\n");
        }
        System.out.println("-------------");


    }

    public Random getRandom() {
        return randomGenerator;
    }

    public void verifyParameters() {
        if (worldHeight < 5) {
            System.err.println("Height must be greater than or equal to 5, it has been set to 5");
            worldHeight = 5;
        }
        if (worldWidth < 5) {
            System.err.println("Width must be greater than or equal to 5, it has been set to 5");
            worldWidth = 5;
        }
        if (possibleBlocks.isEmpty()) {
            System.err.println("Cant play Tetris with no blocks... all blocks will be used!");
            possibleBlocks.add(TetrlaisPiece.makeLine());
            possibleBlocks.add(TetrlaisPiece.makeSquare());
            possibleBlocks.add(TetrlaisPiece.makeTri());
            possibleBlocks.add(TetrlaisPiece.makeSShape());
            possibleBlocks.add(TetrlaisPiece.makeZShape());
            possibleBlocks.add(TetrlaisPiece.makeLShape());
            possibleBlocks.add(TetrlaisPiece.makeJShape());
        }
    }
    /*End of Tetris Helper Functions*/
    public boolean blockMobile = true;
    public int currentBlockId;/*which block we're using in the block table*/

    public int currentRotation = 0;
    public int currentX;/* where the falling block is currently*/

    public int currentY;
    public int score;/* what is the current_score*/

    public boolean is_game_over;/*have we reached the end state yet*/

    public int worldWidth;/*how wide our board is*/

    public int worldHeight;/*how tall our board is*/

    public int[] worldState;/*what the world looks like without the current block*/

//    public int[] worldObservation;/*what the world looks like with the current block*/
    private double[] pieceWeights = null;
    private double rewardExponent;
    //	/*Hold all the possible bricks that can fall*/
    Vector<TetrlaisPiece> possibleBlocks = null;

    public GameState(GameState stateToCopy) {
        this.blockMobile = stateToCopy.blockMobile;
        this.currentBlockId = stateToCopy.currentBlockId;
        this.currentRotation = stateToCopy.currentRotation;
        this.currentX = stateToCopy.currentX;
        this.currentY = stateToCopy.currentY;
        this.score = stateToCopy.score;
        this.is_game_over = stateToCopy.is_game_over;
        this.worldWidth = stateToCopy.worldWidth;
        this.worldHeight = stateToCopy.worldHeight;
        this.pieceWeights = new double[stateToCopy.pieceWeights.length];
        for (int i = 0; i < this.pieceWeights.length; i++) {
            this.pieceWeights[i] = stateToCopy.pieceWeights[i];
        }

        this.rewardExponent = stateToCopy.rewardExponent;
        this.worldState = new int[stateToCopy.worldState.length];
        for (int i = 0; i < this.worldState.length; i++) {
            this.worldState[i] = stateToCopy.worldState[i];
        }

        this.possibleBlocks = new Vector<TetrlaisPiece>();
        //hopefully nobody modifies the pieces as they go
        for (TetrlaisPiece thisPiece : stateToCopy.possibleBlocks) {
            this.possibleBlocks.add(thisPiece);
        }

    }
}

