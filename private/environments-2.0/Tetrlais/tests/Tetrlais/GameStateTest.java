/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tetrlais;

import TPMDP0.TPMDP0;
import TPMDP1.TPMDP1;
import java.util.Random;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rlglue.types.Action;
import static org.junit.Assert.*;

/**
 *
 * @author btanner
 */
public class GameStateTest {

    GameState g1;
    GameState g2;
    GameState g3;

    public GameStateTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setupTest() {
        g1 = new GameState();
        g2 = new GameState();
        g3 = new GameState();

        g1.currentBlockId = 3;
        g1.currentRotation = 2;
        g1.currentX = 20;
        g1.currentY = 11;

        g2.currentBlockId = 6;
        g2.currentRotation = 3;
        g2.currentX = 37;
        g2.currentY = 0;

        g3.currentBlockId = 0;
        g3.currentRotation = 0;
        g3.currentX = 0;
        g3.currentY = 0;

    }

    @Test
    public void negativeYTest() {
        setupTest();
        int a = 3;
        int terminal = 0;

        g1.currentY = -2;
        int packedInt = g1.packStateToInteger(a, terminal, false);

        assertEquals(GameState.unpackBlockId(packedInt), g1.currentBlockId);
        assertEquals(GameState.unpackCurrentRotation(packedInt), g1.currentRotation);
        assertEquals(GameState.unpackCurrentX(packedInt), g1.currentX);
        assertEquals(GameState.unpackCurrentY(packedInt), g1.currentY);
        assertEquals(GameState.unpackCurrentAction(packedInt), a);
        assertEquals(GameState.unpackTerminal(packedInt), terminal);
        assertEquals(GameState.unpackNewEpisode(packedInt), 0);

    }

    @Test
    public void packStateToInteger() {
        setupTest();
        int a = 2;
        int terminal = 1;
        int packedInt = g1.packStateToInteger(a, terminal, false);

        assertEquals(GameState.unpackBlockId(packedInt), g1.currentBlockId);
        assertEquals(GameState.unpackCurrentRotation(packedInt), g1.currentRotation);
        assertEquals(GameState.unpackCurrentX(packedInt), g1.currentX);
        assertEquals(GameState.unpackCurrentY(packedInt), g1.currentY);
        assertEquals(GameState.unpackCurrentAction(packedInt), a);
        assertEquals(GameState.unpackTerminal(packedInt), terminal);
        assertEquals(GameState.unpackNewEpisode(packedInt), 0);

        a = 0;
        terminal = 0;
        packedInt = g2.packStateToInteger(a, terminal, false);

        assertEquals(GameState.unpackBlockId(packedInt), g2.currentBlockId);
        assertEquals(GameState.unpackCurrentRotation(packedInt), g2.currentRotation);
        assertEquals(GameState.unpackCurrentX(packedInt), g2.currentX);
        assertEquals(GameState.unpackCurrentY(packedInt), g2.currentY);
        assertEquals(GameState.unpackCurrentAction(packedInt), a);
        assertEquals(GameState.unpackTerminal(packedInt), terminal);
        assertEquals(GameState.unpackNewEpisode(packedInt), 0);

        a = 0;
        terminal = 0;
        packedInt = g3.packStateToInteger(a, terminal, true);

        assertEquals(GameState.unpackBlockId(packedInt), g3.currentBlockId);
        assertEquals(GameState.unpackCurrentRotation(packedInt), g3.currentRotation);
        assertEquals(GameState.unpackCurrentX(packedInt), g3.currentX);
        assertEquals(GameState.unpackCurrentY(packedInt), g3.currentY);
        assertEquals(GameState.unpackCurrentAction(packedInt), a);
        assertEquals(GameState.unpackTerminal(packedInt), terminal);
        assertEquals(GameState.unpackNewEpisode(packedInt), 1);

    }

    @Test
    public void testFirstProvingMDPPackMethodStart() {
        Tetrlais someProblem = new TPMDP0();
        someProblem.env_init();
        someProblem.env_start();
    }

    @Test
    public void testFirstProvingMDP100Episodes() {
        Random R = new Random();
        Tetrlais someProblem = new TPMDP0();
        someProblem.env_init();
        someProblem.env_start();

        boolean terminated = false;
        int maxEpisodes = 100;

        for (int i = 0; i < maxEpisodes; i++) {
            terminated = false;
            while (!terminated) {
                Action a = new Action(1, 0);
                a.intArray[0] = R.nextInt(5);

                terminated = someProblem.env_step(a).terminal == 1;
            }

        }
    }


    @Test
    public void testTopRowClearedWhenRemovingALine() {
        Tetrlais someProblem = new TPMDP0();
        someProblem.env_init();
        someProblem.env_start();

        GameState theGameState = someProblem.gameState;
        //Fill up everything except the top row
        for (int x = 0; x < theGameState.worldWidth; x++) {
            for (int y = 1; y < theGameState.worldHeight; y++) {
                int linearIndex = theGameState.calculateLinearArrayPosition(x, y);
                theGameState.worldState[linearIndex] = 1;
            }
        }
        //Set 1 piece in the top row on
        int linearIndex = theGameState.calculateLinearArrayPosition(2, 0);
        theGameState.worldState[linearIndex] = 1;
//        theGameState.printState();
        //Remove a row
        theGameState.removeRow(1);
//        theGameState.printState();

        for (int x = 0; x < theGameState.worldWidth; x++) {
            linearIndex = theGameState.calculateLinearArrayPosition(x, 0);
            assertEquals(theGameState.worldState[linearIndex], 0);
        }
    }

    @Test
    public void testRemoveTopRow() {
        Random R = new Random();
        Tetrlais someProblem = new TPMDP0();
        someProblem.env_init();
        someProblem.env_start();

        GameState theGameState = someProblem.gameState;
        //Fill up everything randomly
        for (int x = 0; x < theGameState.worldWidth; x++) {
            for (int y = 0; y < theGameState.worldHeight; y++) {
                int linearIndex = theGameState.calculateLinearArrayPosition(x, y);
                theGameState.worldState[linearIndex] = R.nextInt(2);
            }
        }
        //Fill the top row
        for (int x = 0; x < theGameState.worldWidth; x++) {
            int linearIndex = theGameState.calculateLinearArrayPosition(x, 0);
            theGameState.worldState[linearIndex] = 1;
        }

//        theGameState.printState();
        //Remove a row
        theGameState.checkIfRowAndScore();
//        theGameState.printState();

        for (int x = 0; x < theGameState.worldWidth; x++) {
            int linearIndex = theGameState.calculateLinearArrayPosition(x, 0);
            assertEquals(theGameState.worldState[linearIndex], 0);
        }
    }
    
    @Test
    public void testRemoveSecondTopRowWhen2LinesAtTop() {
        Random R = new Random();
        Tetrlais someProblem = new TPMDP0();
        someProblem.env_init();
        someProblem.env_start();

        GameState theGameState = someProblem.gameState;
        //Fill up everything randomly
        for (int x = 0; x < theGameState.worldWidth; x++) {
            for (int y = 0; y < theGameState.worldHeight; y++) {
                int linearIndex = theGameState.calculateLinearArrayPosition(x, y);
                theGameState.worldState[linearIndex] = R.nextInt(2);
            }
        }
        //Fill the top row
        for (int x = 0; x < theGameState.worldWidth; x++) {
            int linearIndex = theGameState.calculateLinearArrayPosition(x, 0);
            theGameState.worldState[linearIndex] = 1;
            linearIndex = theGameState.calculateLinearArrayPosition(x, 1);
            theGameState.worldState[linearIndex] = 1;
        }

//        theGameState.printState();
        //Remove a row
        theGameState.checkIfRowAndScore();
//        theGameState.printState();

        for (int x = 0; x < theGameState.worldWidth; x++) {
            int linearIndex = theGameState.calculateLinearArrayPosition(x, 0);
            assertEquals(theGameState.worldState[linearIndex], 0);
            linearIndex = theGameState.calculateLinearArrayPosition(x, 1);
            assertEquals(theGameState.worldState[linearIndex], 0);
        }

    }

    @Test
    public void testWhatHappensWithFullBoard() {
        Tetrlais someProblem = new TPMDP0();
        someProblem.env_init();
        someProblem.env_start();

        GameState theGameState = someProblem.gameState;
        //Fill up everything except the top row
        for (int x = 0; x < theGameState.worldWidth; x++) {
            for (int y = 0; y < theGameState.worldHeight; y++) {
                int linearIndex = theGameState.calculateLinearArrayPosition(x, y);
                theGameState.worldState[linearIndex] = 1;
            }
        }
//        theGameState.printState();
        //Remove a row
        theGameState.checkIfRowAndScore();
//        theGameState.printState();

        for (int x = 0; x < theGameState.worldWidth; x++) {
            for (int y = 0; y < theGameState.worldHeight; y++) {
                int linearIndex = theGameState.calculateLinearArrayPosition(x, y);
                assertEquals(theGameState.worldState[linearIndex], 0);
            }
        }
    }
}
