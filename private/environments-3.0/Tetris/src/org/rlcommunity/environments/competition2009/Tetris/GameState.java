// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameState.java

package org.rlcommunity.environments.competition2009.Tetris;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import org.rlcommunity.rlglue.codec.types.Observation;
import rlVizLib.utilities.UtilityShop;

// Referenced classes of package org.rlcommunity.environments.competition2009.Tetris:
//            TetrlaisPiece, SimulatedTetrisGame

public class GameState
{

    public GameState()
    {
        this(10, 20, new Vector<TetrlaisPiece>(), null, 1.0D, 0.0D, 0, 0.0D);
    }

    public static void main(String args[])
    {
        GameState g = new GameState();
    }

    public GameState(int width, int height, Vector<TetrlaisPiece> possibleBlocks, double pieceWeights[], double rewardExponent, double evilness, int opponentType, double benchmarkScore)
    {
        randomGenerator = new Random();
        blockMobile = true;
        currentRotation = 0;
        this.pieceWeights = null;
        this.possibleBlocks = null;
        worldWidth = width;
        worldHeight = height;
        this.possibleBlocks = possibleBlocks;
        worldState = new int[width * height];
        this.pieceWeights = pieceWeights;
        this.rewardExponent = rewardExponent;
        this.evilness = evilness;
        evilAgentType = opponentType;
        this.benchmarkScore = benchmarkScore;
        if(benchmarkScore == 0.0D)
            scalingFactor = 1.0D;
        else
            scalingFactor = 1000D / benchmarkScore;
        if(this.pieceWeights == null)
        {
            this.pieceWeights = new double[possibleBlocks.size()];
            for(int i = 0; i < this.pieceWeights.length; i++)
                this.pieceWeights[i] = 1.0D;

        }
        reset();
    }

    public void reset()
    {
        currentX = worldWidth / 2 - 1;
        currentY = 0;
        score = 0.0D;
        for(int i = 0; i < worldState.length; i++)
            worldState[i] = 0;

        currentRotation = 0;
        is_game_over = false;
    }

    public Observation get_observation()
    {
        try
        {
            int worldObservation[] = new int[worldState.length];
            for(int i = 0; i < worldObservation.length; i++)
                worldObservation[i] = worldState[i];

            writeCurrentBlock(worldObservation);
            Observation o = new Observation(worldObservation.length + possibleBlocks.size() + 2, 0);
            for(int i = 0; i < worldObservation.length; i++)
                if(worldObservation[i] == 0)
                    o.intArray[i] = 0;
                else
                    o.intArray[i] = 1;

            for(int j = 0; j < possibleBlocks.size(); j++)
                o.intArray[worldObservation.length + j] = 0;

            o.intArray[worldObservation.length + currentBlockId] = 1;
            o.intArray[o.intArray.length - 2] = getHeight();
            o.intArray[o.intArray.length - 1] = getWidth();
            return o;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.err.println("Error: ArrayIndexOutOfBoundsException in GameState::get_observation");
            System.err.println((new StringBuilder()).append("Error: The Exception was: ").append(e).toString());
            Thread.dumpStack();
            System.err.println((new StringBuilder()).append("Current X is: ").append(currentX).append(" Current Y is: ").append(currentY).append(" Rotation is: ").append(currentRotation).append(" blockId: ").append(currentBlockId).toString());
            System.err.println("Not realy sure what to do, so crashing.  Sorry.");
            System.exit(1);
            return null;
        }
    }

    int packStateToInteger(int theAction, int terminal, boolean episodeStart)
    {
        int packedInteger = 0;
        int nextOffset = 0;
        int thisSize = 3;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(currentBlockId, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        thisSize = 2;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(currentRotation, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        thisSize = 6;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(currentX + 5, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        thisSize = 6;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(currentY + 5, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        thisSize = 3;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(theAction, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        thisSize = 1;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(terminal, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        int newEpisode = 0;
        if(episodeStart)
            newEpisode = 1;
        thisSize = 1;
        packedInteger = UtilityShop.putSomeBitsFromIntIntoInt(newEpisode, packedInteger, thisSize, nextOffset);
        nextOffset += thisSize;
        return packedInteger;
    }

    protected static int unpackBlockId(int packedInteger)
    {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 3, 0);
    }

    protected static int unpackCurrentRotation(int packedInteger)
    {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 2, 3);
    }

    protected static int unpackCurrentX(int packedInteger)
    {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 6, 5) - 5;
    }

    protected static int unpackCurrentY(int packedInteger)
    {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 6, 11) - 5;
    }

    protected static int unpackCurrentAction(int packedInteger)
    {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 3, 17);
    }

    protected static int unpackTerminal(int packedInteger)
    {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 1, 20);
    }

    protected static int unpackNewEpisode(int packedInteger)
    {
        return UtilityShop.extractSomeBitsAsIntFromInt(packedInteger, 1, 21);
    }

    void stringSerialize(StringBuilder b)
    {
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
    }

    /**
     * @deprecated Method stringSerialize is deprecated
     */

    String stringSerialize()
    {
        StringBuilder b = new StringBuilder();
        stringSerialize(b);
        return b.toString();
    }

    private void writeCurrentBlock(int game_world[])
    {
        int thisPiece[][] = (possibleBlocks.get(currentBlockId)).getShape(currentRotation);
        for(int y = 0; y < thisPiece[0].length; y++)
        {
            for(int x = 0; x < thisPiece.length; x++)
                if(thisPiece[x][y] != 0)
                {
                    int linearIndex = calculateLinearArrayPosition(currentX + x, currentY + y);
                    game_world[linearIndex] = currentBlockId + 1;
                }

        }

    }

    public boolean gameOver()
    {
        return is_game_over;
    }

    public void take_action(int theAction)
    {
        if(theAction > 5 || theAction < 0)
        {
            System.err.println((new StringBuilder()).append("Invalid action selected in Tetrlais: ").append(theAction).toString());
            theAction = randomGenerator.nextInt(6);
        }
        int nextRotation = currentRotation;
        int nextX = currentX;
        int nextY = currentY;
        switch(theAction)
        {
        case 2: // '\002'
            nextRotation = (currentRotation + 1) % 4;
            break;

        case 3: // '\003'
            nextRotation = currentRotation - 1;
            if(nextRotation < 0)
                nextRotation = 3;
            break;

        case 0: // '\0'
            nextX = currentX - 1;
            break;

        case 1: // '\001'
            nextX = currentX + 1;
            break;

        case 5: // '\005'
            nextY = currentY;
            boolean isInBounds = true;
            boolean isColliding = false;
            do
            {
                if(!isInBounds || isColliding)
                    break;
                nextY++;
                isInBounds = inBounds(nextX, nextY, nextRotation);
                if(isInBounds)
                    isColliding = colliding(nextX, nextY, nextRotation);
            } while(true);
            nextY--;
            break;
        }
        if(inBounds(nextX, nextY, nextRotation) && !colliding(nextX, nextY, nextRotation))
        {
            currentRotation = nextRotation;
            currentX = nextX;
            currentY = nextY;
        }
    }

    int calculateLinearArrayPosition(int x, int y)
    {
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

//    private boolean colliding(int checkX, int checkY, int checkOrientation)
//    {
//        int thePiece[][] = ((TetrlaisPiece)possibleBlocks.get(currentBlockId)).getShape(checkOrientation);
//        int y = 0;
//_L4:
//        int x;
//        if(y >= thePiece[0].length)
//            break MISSING_BLOCK_LABEL_133;
//        x = 0;
//_L3:
//        if(x >= thePiece.length)
//            break MISSING_BLOCK_LABEL_127;
//        if(thePiece[x][y] == 0) goto _L2; else goto _L1
//_L1:
//        if(checkY + y < 0 || checkX + x < 0)
//            return true;
//        int linearArrayIndex;
//        try
//        {
//            if(checkY + y >= worldHeight || checkX + x >= worldWidth)
//                return true;
//        }
//        catch(ArrayIndexOutOfBoundsException e)
//        {
//            System.err.println((new StringBuilder()).append("Error: ArrayIndexOutOfBoundsException in GameState::colliding called with params: ").append(checkX).append(" , ").append(checkY).append(", ").append(checkOrientation).toString());
//            System.err.println((new StringBuilder()).append("Error: The Exception was: ").append(e).toString());
//            Thread.dumpStack();
//            System.err.println("Returning true from colliding to help save from error");
//            System.err.println("Setting is_game_over to true to hopefully help us to recover from this problem");
//            is_game_over = true;
//            return true;
//        }
//        linearArrayIndex = calculateLinearArrayPosition(checkX + x, checkY + y);
//        if(worldState[linearArrayIndex] != 0)
//            return true;
//_L2:
//        x++;
//          goto _L3
//        y++;
//          goto _L4
//        return false;
//    }
//
//    private boolean collidingCheckOnlySpotsInBounds(int checkX, int checkY, int checkOrientation)
//    {
//        int thePiece[][] = ((TetrlaisPiece)possibleBlocks.get(currentBlockId)).getShape(checkOrientation);
//        int y = 0;
//_L2:
//        int x;
//        if(y >= thePiece[0].length)
//            break MISSING_BLOCK_LABEL_129;
//        x = 0;
//_L1:
//        if(x >= thePiece.length)
//            break MISSING_BLOCK_LABEL_123;
//        if(thePiece[x][y] != 0 && checkX + x >= 0 && checkX + x < worldWidth && checkY + y >= 0 && checkY + y < worldHeight)
//        {
//            int linearArrayIndex = calculateLinearArrayPosition(checkX + x, checkY + y);
//            if(worldState[linearArrayIndex] != 0)
//                return true;
//        }
//        x++;
//          goto _L1
//        y++;
//          goto _L2
//        return false;
//        ArrayIndexOutOfBoundsException e;
//        e;
//        System.err.println((new StringBuilder()).append("Error: ArrayIndexOutOfBoundsException in GameState::collidingCheckOnlySpotsInBounds called with params: ").append(checkX).append(" , ").append(checkY).append(", ").append(checkOrientation).toString());
//        System.err.println((new StringBuilder()).append("Error: The Exception was: ").append(e).toString());
//        Thread.dumpStack();
//        System.err.println("Returning true from colliding to help save from error");
//        System.err.println("Setting is_game_over to true to hopefully help us to recover from this problem");
//        is_game_over = true;
//        return true;
//    }

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

//     private boolean inBounds(int checkX, int checkY, int checkOrientation)
//    {
//        int thePiece[][];
//        int y;
//        thePiece = ((TetrlaisPiece)possibleBlocks.get(currentBlockId)).getShape(checkOrientation);
//        y = 0;
//_L2:
//        int x;
//        if(y >= thePiece[0].length)
//            break MISSING_BLOCK_LABEL_105;
//        x = 0;
//_L1:
//        if(x >= thePiece.length)
//            break MISSING_BLOCK_LABEL_99;
//        if(thePiece[x][y] != 0 && (checkX + x < 0 || checkX + x >= worldWidth || checkY + y < 0 || checkY + y >= worldHeight))
//            return false;
//        x++;
//          goto _L1
//        y++;
//          goto _L2
//        return true;
//        ArrayIndexOutOfBoundsException e;
//        e;
//        System.err.println((new StringBuilder()).append("Error: ArrayIndexOutOfBoundsException in GameState::inBounds called with params: ").append(checkX).append(" , ").append(checkY).append(", ").append(checkOrientation).toString());
//        System.err.println((new StringBuilder()).append("Error: The Exception was: ").append(e).toString());
//        Thread.dumpStack();
//        System.err.println("Returning false from inBounds to help save from error.  Not sure if that's wise.");
//        System.err.println("Setting is_game_over to true to hopefully help us to recover from this problem");
//        is_game_over = true;
//        return false;
//    }

    private boolean nextInBounds()
    {
        return inBounds(currentX, currentY + 1, currentRotation);
    }

    private boolean nextColliding()
    {
        return colliding(currentX, currentY + 1, currentRotation);
    }

    public void update()
    {
        if(!inBounds(currentX, currentY, currentRotation))
            System.err.println("In GameState.Java the Current Position of the board is Out Of Bounds... Consistency Check Failed");
        boolean onSomething = false;
        if(!nextInBounds())
            onSomething = true;
        if(!onSomething && nextColliding())
            onSomething = true;
        if(onSomething)
        {
            blockMobile = false;
            writeCurrentBlock(worldState);
            checkIfRowAndScore();
        } else
        {
            currentY++;
        }
    }

    public double[] try_blocks()
    {
        int PieceToCe[] = {
            0, 6, 3, 5, 4, 1, 2
        };
        double value[] = new double[pieceWeights.length];
        double sortedvalue[] = new double[pieceWeights.length];
        double ranks[] = new double[pieceWeights.length];
        if(testgame == null)
        {
            testgame = new SimulatedTetrisGame(getWidth(), getHeight(), 150);
            testgame.setValues(evilAgentType);
        }
        for(int i = 0; i < getWidth(); i++)
        {
            for(int j = 0; j < getHeight(); j++)
                testgame.board[i + 3][j + 3] = worldState[j * getWidth() + i];

        }

        testgame.UpdateSkyline();
        for(int piece = 0; piece < 7; piece++)
        {
            int cePiece = PieceToCe[piece];
            value[piece] = testgame.getBestValue(cePiece);
        }


        System.arraycopy(value, 0, sortedvalue, 0,value.length);
        Arrays.sort(sortedvalue);
        for(int i = 0; i < sortedvalue.length; i++)
        {
            int j;
            for(j = 0; j < value.length && sortedvalue[i] != value[j]; j++);
            ranks[j] = i;
            sortedvalue[i] -= 1.0000000000000001E-005D;
        }

        return ranks;
    }

    public void spawn_block()
    {
        blockMobile = true;
        double ranks[] = try_blocks();
        double pieceWeights[] = new double[this.pieceWeights.length];
        System.arraycopy(this.pieceWeights, 0, pieceWeights, 0, this.pieceWeights.length);
        for(int i = 0; i < ranks.length; i++)
        {
            double x = ((ranks[i] - 3D) / 3D) * evilness;
            pieceWeights[i] *= 1.0D - x;
        }

        currentBlockId = 0;
        double total = 0.0D;
        for(int i = 0; i < pieceWeights.length; i++)
            total += pieceWeights[i];

        for(double r = randomGenerator.nextDouble() * total; pieceWeights[currentBlockId] < r && currentBlockId < 6; currentBlockId++)
            r -= pieceWeights[currentBlockId];

        currentRotation = 0;
        currentX = worldWidth / 2 - 2;
        currentY = -4;
        boolean hitOnWayIn = false;
        for(; !inBounds(currentX, currentY, currentRotation); currentY++)
            hitOnWayIn = collidingCheckOnlySpotsInBounds(currentX, currentY, currentRotation);

        is_game_over = colliding(currentX, currentY, currentRotation) || hitOnWayIn;
        if(is_game_over)
            blockMobile = false;
    }

    void checkIfRowAndScore()
    {
        int numRowsCleared = 0;
        for(int y = worldHeight - 1; y >= 0; y--)
            if(isRow(y))
            {
                removeRow(y);
                numRowsCleared++;
                y++;
            }

        score += (double)(int)Math.pow((double)numRowsCleared + 0.10000000000000001D, rewardExponent) * scalingFactor;
    }

    boolean isRow(int y)
    {
        for(int x = 0; x < worldWidth; x++)
        {
            int linearIndex = calculateLinearArrayPosition(x, y);
            if(worldState[linearIndex] == 0)
                return false;
        }

        return true;
    }

    void removeRow(int y)
    {
        if(!isRow(y))
        {
            System.err.println("In GameState.java remove_row you have tried to remove a row which is not complete. Failed to remove row");
            return;
        }
        for(int x = 0; x < worldWidth; x++)
        {
            int linearIndex = calculateLinearArrayPosition(x, y);
            worldState[linearIndex] = 0;
        }

        for(int ty = y; ty > 0; ty--)
        {
            for(int x = 0; x < worldWidth; x++)
            {
                int linearIndexTarget = calculateLinearArrayPosition(x, ty);
                int linearIndexSource = calculateLinearArrayPosition(x, ty - 1);
                worldState[linearIndexTarget] = worldState[linearIndexSource];
            }

        }

        for(int x = 0; x < worldWidth; x++)
        {
            int linearIndex = calculateLinearArrayPosition(x, 0);
            worldState[linearIndex] = 0;
        }

    }

    public double get_score()
    {
        return score;
    }

    public int getWidth()
    {
        return worldWidth;
    }

    public int getHeight()
    {
        return worldHeight;
    }

    public int[] getNumberedStateSnapShot()
    {
        int numberedStateCopy[] = new int[worldState.length];
        for(int i = 0; i < worldState.length; i++)
            numberedStateCopy[i] = worldState[i];

        writeCurrentBlock(numberedStateCopy);
        return numberedStateCopy;
    }

    public int getCurrentPiece()
    {
        return currentBlockId;
    }

    public void printState()
    {
        int index = 0;
        for(int i = 0; i < worldHeight - 1; i++)
        {
            for(int j = 0; j < worldWidth; j++)
                System.out.print(worldState[i * worldWidth + j]);

            System.out.print("\n");
        }

        System.out.println("-------------");
    }

    public Random getRandom()
    {
        return randomGenerator;
    }

    public void verifyParameters()
    {
        if(worldHeight < 5)
        {
            System.err.println("Height must be greater than or equal to 5, it has been set to 5");
            worldHeight = 5;
        }
        if(worldWidth < 5)
        {
            System.err.println("Width must be greater than or equal to 5, it has been set to 5");
            worldWidth = 5;
        }
        if(possibleBlocks.isEmpty())
        {
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

    public GameState(GameState stateToCopy)
    {
        randomGenerator = new Random();
        blockMobile = true;
        currentRotation = 0;
        pieceWeights = null;
        possibleBlocks = null;
        blockMobile = stateToCopy.blockMobile;
        currentBlockId = stateToCopy.currentBlockId;
        currentRotation = stateToCopy.currentRotation;
        currentX = stateToCopy.currentX;
        currentY = stateToCopy.currentY;
        score = stateToCopy.score;
        is_game_over = stateToCopy.is_game_over;
        worldWidth = stateToCopy.worldWidth;
        worldHeight = stateToCopy.worldHeight;
        pieceWeights = new double[stateToCopy.pieceWeights.length];
        for(int i = 0; i < pieceWeights.length; i++)
            pieceWeights[i] = stateToCopy.pieceWeights[i];

        rewardExponent = stateToCopy.rewardExponent;
        evilness = stateToCopy.evilness;
        evilAgentType = stateToCopy.evilAgentType;
        benchmarkScore = stateToCopy.benchmarkScore;
        scalingFactor = stateToCopy.scalingFactor;
        worldState = new int[stateToCopy.worldState.length];
        for(int i = 0; i < worldState.length; i++)
            worldState[i] = stateToCopy.worldState[i];

        possibleBlocks = new Vector<TetrlaisPiece>();
        TetrlaisPiece thisPiece;
        for(Iterator i$ = stateToCopy.possibleBlocks.iterator(); i$.hasNext(); possibleBlocks.add(thisPiece))
            thisPiece = (TetrlaisPiece)i$.next();

    }

    static final int defaultWidth = 10;
    static final int defaultHeight = 20;
    static final int LEFT = 0;
    static final int RIGHT = 1;
    static final int CW = 2;
    static final int CCW = 3;
    static final int NONE = 4;
    static final int FALL = 5;
    private Random randomGenerator;
    SimulatedTetrisGame testgame;
    public boolean blockMobile;
    public int currentBlockId;
    public int currentRotation;
    public int currentX;
    public int currentY;
    public double score;
    public boolean is_game_over;
    public int worldWidth;
    public int worldHeight;
    public int worldState[];
    private double pieceWeights[];
    private double rewardExponent;
    private double evilness;
    private int evilAgentType;
    private double benchmarkScore;
    private double scalingFactor;
    Vector<TetrlaisPiece> possibleBlocks;
}
