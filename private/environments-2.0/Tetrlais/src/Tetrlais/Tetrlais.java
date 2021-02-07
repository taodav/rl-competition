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

import java.util.Vector;

import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.general.RLVizVersion;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.HasAVisualizerInterface;
import rlVizLib.messaging.interfaces.RLVizVersionResponseInterface;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Random_seed_key;
import rlglue.types.Reward_observation;
import rlglue.types.State_key;
import Tetrlais.messages.TetrlaisStateResponse;
import rlVizLib.general.hasVersionDetails;
import rlVizLib.messaging.interfaces.ProvidesEpisodeSummariesInterface;
import rlVizLib.utilities.UtilityShop;


public abstract class Tetrlais extends EnvironmentBase implements RLVizVersionResponseInterface, HasAVisualizerInterface, ProvidesEpisodeSummariesInterface {

    private int currentScore = 0;
    protected GameState gameState = null;
    static final int terminalScore = 0;
    Vector<GameState> savedStates = new Vector<GameState>();

    //This will be an entire summary of this episode
        //For now, this is going to be rough, we'll fix it up later

    rlVizLib.utilities.logging.EpisodeLogger theEpisodeLogger;
    protected boolean recordLogs = false;
    protected boolean allowSaveLoadState = true;
    protected boolean allowSaveLoadSeed = true;

    public Tetrlais() {
        this(getDefaultParameters());
    }

    public Tetrlais(ParameterHolder p) {
        super();


        int width = GameState.defaultWidth;
        int height = GameState.defaultHeight;
        double rewardExponent = 1.0d;
        Vector<TetrlaisPiece> possibleBlocks = new Vector<TetrlaisPiece>();
        //Add all blocks
        possibleBlocks.add(TetrlaisPiece.makeLine());
        possibleBlocks.add(TetrlaisPiece.makeSquare());
        possibleBlocks.add(TetrlaisPiece.makeTri());
        possibleBlocks.add(TetrlaisPiece.makeSShape());
        possibleBlocks.add(TetrlaisPiece.makeZShape());
        possibleBlocks.add(TetrlaisPiece.makeLShape());
        possibleBlocks.add(TetrlaisPiece.makeJShape());

        double[] pieceProbs = new double[7];
        for (int i = 0; i < 7; i++) {
            pieceProbs[i] = 1.0d;
        }

        if (p != null) {
            if (!p.isNull()) {
                width = p.getIntegerParam("Width");
                height = p.getIntegerParam("Height");

                pieceProbs[0] = p.getDoubleParam("LongBlockWeight");
                pieceProbs[1] = p.getDoubleParam("SquareBlockWeight");
                pieceProbs[2] = p.getDoubleParam("TriBlockWeight");
                pieceProbs[3] = p.getDoubleParam("SBlockWeight");
                pieceProbs[4] = p.getDoubleParam("ZBlockWeight");
                pieceProbs[5] = p.getDoubleParam("LBlockWeight");
                pieceProbs[6] = p.getDoubleParam("JBlockWeight");

                rewardExponent = p.getDoubleParam("rewardExponent");

            }
        }
        gameState = new GameState(width, height, possibleBlocks, pieceProbs, rewardExponent);
        gameState.verifyParameters();
    }

    //This method creates the object that can be used to easily set different problem parameters

    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        rlVizLib.utilities.UtilityShop.setVersionDetails(p, new DetailsProvider());

        p.addIntegerParam("Width", GameState.defaultWidth);
        p.addIntegerParam("Height", GameState.defaultHeight);


        p.addDoubleParam("LongBlockWeight", 1.0d);
        p.addDoubleParam("SquareBlockWeight", 1.0);
        p.addDoubleParam("TriBlockWeight", 1.0);
        p.addDoubleParam("SBlockWeight", 1.0);
        p.addDoubleParam("ZBlockWeight", 1.0);
        p.addDoubleParam("LBlockWeight", 1.0);
        p.addDoubleParam("JBlockWeight", 1.0);

        p.addDoubleParam("rewardExponent", 1.0);


        return p;
    }

    /*Base RL-Glue Functions*/
    public String env_init() {
        if(recordLogs)theEpisodeLogger=new rlVizLib.utilities.logging.EpisodeLogger();
        /* initialize the environment, construct a task_spec to pass on. The tetris environment
         * has 200 binary observation variables and 1 action variable which ranges between 0 and 4. These are all
         * integer values. */
        /*NOTE: THE GAME STATE WIDTH AND HEIGHT MUST MULTIPLY TO EQUAL THE NUMBER OF OBSERVATION VARIABLES*/
        //original code: only the statespace
        int boardSize = gameState.getHeight() * gameState.getWidth();
        int numPieces = gameState.possibleBlocks.size();
        int boardSizeObservations = 2;
        int intObsCount = boardSize + numPieces + boardSizeObservations;
        
        //adding the plus 2 because we are sending the weidth and height also
        String task_spec = "2.0:e:" + (boardSize + numPieces + 2) + "_[";
        for (int i = 0; i < intObsCount - 1; i++) {
            task_spec = task_spec + "i,";
        }

        //Add 2 more for the num Rows and num Cols
        task_spec = task_spec + " i]";
        for (int i = 0; i < boardSize + numPieces; i++) {
            task_spec = task_spec + "_[0,1]";
        }
        //Added what the width and height are
        task_spec = task_spec + "_[" + gameState.getHeight() + "," + gameState.getHeight() + "]_[" + gameState.getWidth() + "," + gameState.getWidth() + "]";
        task_spec = task_spec + ":1_[i]_[0,5]:[0,1]";



        return task_spec;
    }

    public Observation env_start() {
        if (recordLogs)theEpisodeLogger.clear();
        gameState.reset();
        gameState.spawn_block();
        gameState.blockMobile = true;
        currentScore = 0;


        if (recordLogs) {
            int stateSummary=gameState.packStateToInteger(0,0,true);
            theEpisodeLogger.appendLogString(""+stateSummary);
        }

        //this one will get you the observation of the state space observation
        Observation o = gameState.get_observation();
        return o;
    }

    public Reward_observation env_step(Action actionObject) {

        int theAction = 0;
        
        try{
            theAction=actionObject.intArray[0];
        }catch(ArrayIndexOutOfBoundsException e){
            System.err.println("Error: Action was expected to have 1 dimension but got ArrayIndexOutOfBoundsException when trying to get element 0:"+e);
            System.err.println("Error: Choosing action 0");
            theAction=0;
        }

        if (theAction > 5 || theAction < 0) {
            System.err.println("Invalid action selected in Tetrlais: " + theAction);
            theAction = gameState.getRandom().nextInt(5);
        }

        if (gameState.blockMobile) {
            gameState.take_action(theAction);
            gameState.update();
        } else {
            gameState.spawn_block();
        }



        Reward_observation ro = new Reward_observation();

        ro.terminal = 1;
        ro.o = gameState.get_observation();

        if (!gameState.gameOver()) {
            ro.terminal = 0;
            ro.r = gameState.get_score() - currentScore;
            currentScore = gameState.get_score();
        } else {
            ro.r = Tetrlais.terminalScore;
            currentScore = 0;
        }

        if (recordLogs) {
            int stateSummary=gameState.packStateToInteger(theAction, ro.terminal,false);
            theEpisodeLogger.appendLogString("_"+stateSummary);
        }

        return ro;
    }

    public void env_cleanup() {
        savedStates.clear();
    }

    public String env_message(String theMessage) {
        EnvironmentMessages theMessageObject;
        try {
            theMessageObject = EnvironmentMessageParser.parseMessage(theMessage);
        } catch (Exception e) {
            System.err.println("Someone sent Tetrlais a message that wasn't RL-Viz compatible");
            return "I only respond to RL-Viz messages!";
        }

        
        if (theMessageObject.canHandleAutomatically(this)) {
            return theMessageObject.handleAutomatically(this);
        }

        if (theMessageObject.getTheMessageType() == rlVizLib.messaging.environment.EnvMessageType.kEnvCustom.id()) {

            String theCustomType = theMessageObject.getPayLoad();

            if (theCustomType.equals("GETTETRLAISSTATE")) {
                //It is a request for the state
                TetrlaisStateResponse theResponseObject = new TetrlaisStateResponse(currentScore, gameState.getWidth(), gameState.getHeight(), gameState.getNumberedStateSnapShot(), gameState.getCurrentPiece());
                return theResponseObject.makeStringResponse();
            }
            System.out.println("We need some code written in Env Message for Tetrlais.. unknown custom message type received");
            Thread.dumpStack();

            return null;
        }

        System.out.println("We need some code written in Env Message for  Tetrlais!");
        Thread.dumpStack();

        return null;
    }

    public State_key env_get_state() {
        if (allowSaveLoadState) {
            savedStates.add(new GameState(gameState));
            State_key k = new State_key(1, 0);
            k.intArray[0] = savedStates.size() - 1;
            return k;
        }
        System.err.println("env_get_state() called in: " + getClass() + " but it is disabled");
        return null;
    }

    public void env_set_state(State_key k) {
        if (allowSaveLoadState) {
            int theIndex = k.intArray[0];

            if (savedStates == null || theIndex >= savedStates.size()) {
                System.err.println("Could not set state to index:" + theIndex + ", that's higher than saved size");
                return;
            }
            GameState oldState = savedStates.get(theIndex);
            this.gameState = new GameState(oldState);
            return;
        }
        System.err.println("env_set_state() called in: " + getClass() + " but it is disabled");
    }

    //
//This has a side effect, it changes the random order.
//

    public Random_seed_key env_get_random_seed() {
        if (allowSaveLoadSeed) {
            Random_seed_key k = new Random_seed_key(2, 0);
            long newSeed = gameState.getRandom().nextLong();
            gameState.getRandom().setSeed(newSeed);
            k.intArray[0] = UtilityShop.LongHighBitsToInt(newSeed);
            k.intArray[1] = UtilityShop.LongLowBitsToInt(newSeed);
            return k;
        }
        System.err.println("env_get_random_seed() called in: " + getClass() + " but it is disabled");
        return null;

    }

    public void env_set_random_seed(Random_seed_key k) {
        if (allowSaveLoadSeed) {
            long storedSeed = UtilityShop.intsToLong(k.intArray[0], k.intArray[1]);
            gameState.getRandom().setSeed(storedSeed);
            return;
        }
        System.err.println("env_set_random_seed() called in: " + getClass() + " but it is disabled");
    }

    /*End of Base RL-Glue Functions */
    /*RL-Viz Methods*/
    @Override
    protected Observation makeObservation() {
        return gameState.get_observation();
    }

    /*End of RL-Viz Methods*/
    public RLVizVersion getTheVersionISupport() {
        return new RLVizVersion(1, 0);
    }

    public String getVisualizerClassName() {
        return "visualizers.Tetrlais.TetrlaisVisualizer";
    }
    /*Tetris Helper Functions*/

/*    public static void main(String[] args) {
        Tetrlais T = new Tetrlais(); 
        T.recordLogs=true;
        T.env_init();
        T.env_start();
        T.env_step(new Action(1,0));
        T.env_step(new Action(1,0));
        T.env_step(new Action(1,0));
        T.env_step(new Action(1,0));
        T.env_step(new Action(1,0));
        T.env_step(new Action(1,0));

        Vector<String> theLog=T.getEpisodeSummary();
        int totalChars=0;
        for (String theSummary : theLog) {
            totalChars+=theSummary.length();
        }
        System.out.println("String length total: "+totalChars+": over: "+theLog.size()+" steps");

        for(int i=0;i<100000000;i++){
            try {
                String newString=new String(theLog.get(1));
                totalChars+=newString.length();
                theLog.add(newString);
                if((i+1)%1000==0)
        System.out.println("String length so far: "+totalChars+": over: "+theLog.size()+" steps");
                } catch (Throwable e) {
                    System.out.println("String length total: "+totalChars+": over: "+theLog.size()+" steps");
                    System.out.println(e);
                    System.exit(1);
        }
}
    }*/

    /**
     * @deprecated
     * @return
     */
    public Vector<String> getEpisodeSummary() {
return null;
    }
    public String getEpisodeSummary(long charToStartOn, int charsToSend) {
        if (!recordLogs) {
            System.err.println("Someone asked for episode logs on: " + getClass() + " but logging is disabled!");
        }
        return theEpisodeLogger.getLogSubString(charToStartOn, charsToSend);
    }

}



class DetailsProvider implements hasVersionDetails {

    public String getName() {
        return "Tetris 1.0";
    }

    public String getShortName() {
        return "Tetris";
    }

    public String getAuthors() {
        return "Brian Tanner, Leah Hackman, Matt Radkie, Andrew Butcher";
    }

    public String getInfoUrl() {
        return "http://rl-library.googlecode.com";
    }

    public String getDescription() {
        return "Tetris problem from the reinforcement learning library.";
    }
}
