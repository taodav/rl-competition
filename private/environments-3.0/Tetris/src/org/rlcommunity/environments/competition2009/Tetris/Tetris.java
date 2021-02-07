// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tetris.java

package org.rlcommunity.environments.competition2009.Tetris;

import java.io.PrintStream;
import java.net.URL;
import java.util.Random;
import java.util.Vector;
import org.rlcommunity.environments.competition2009.Tetris.messages.TetrisStateResponse;
import org.rlcommunity.environments.competition2009.Tetris.visualizer.TetrisVisualizer;
import org.rlcommunity.rlglue.codec.taskspec.TaskSpec;
import org.rlcommunity.rlglue.codec.taskspec.TaskSpecVRLGLUE3;
import org.rlcommunity.rlglue.codec.taskspec.ranges.DoubleRange;
import org.rlcommunity.rlglue.codec.taskspec.ranges.IntRange;
import org.rlcommunity.rlglue.codec.types.*;
import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.general.RLVizVersion;
import rlVizLib.messaging.environment.*;
import rlVizLib.messaging.environmentShell.TaskSpecPayload;
import rlVizLib.messaging.interfaces.*;
import rlVizLib.utilities.UtilityShop;
import rlVizLib.utilities.logging.EpisodeLogger;

// Referenced classes of package org.rlcommunity.environments.competition2009.Tetris:
//            GameState, DetailsProvider, TetrlaisPiece

public abstract class Tetris extends EnvironmentBase
    implements HasAVisualizerInterface, ProvidesEpisodeSummariesInterface, HasImageInterface
{

    public Tetris()
    {
        this(getDefaultParameters());
    }

    public Tetris(ParameterHolder p)
    {
        currentScore = 0.0D;
        gameState = null;
        savedStates = new Vector();
        recordLogs = false;
        allowSaveLoadState = true;
        allowSaveLoadSeed = true;
        nsteps = 0;
        int width = 10;
        int height = 20;
        double rewardExponent = 1.0D;
        double evilness = 0.0D;
        int evilAgentType = 0;
        double benchmarkScore = 0.0D;
        Vector<TetrlaisPiece> possibleBlocks = new Vector<TetrlaisPiece>();
        possibleBlocks.add(TetrlaisPiece.makeLine());
        possibleBlocks.add(TetrlaisPiece.makeSquare());
        possibleBlocks.add(TetrlaisPiece.makeTri());
        possibleBlocks.add(TetrlaisPiece.makeSShape());
        possibleBlocks.add(TetrlaisPiece.makeZShape());
        possibleBlocks.add(TetrlaisPiece.makeLShape());
        possibleBlocks.add(TetrlaisPiece.makeJShape());
        double pieceProbs[] = new double[7];
        for(int i = 0; i < 7; i++)
            pieceProbs[i] = 1.0D;

        if(p != null && !p.isNull())
        {
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
            evilness = p.getDoubleParam("Evilness");
            evilAgentType = p.getIntegerParam("EvilAgentType");
            benchmarkScore = p.getDoubleParam("BenchmarkScore");
        }
        gameState = new GameState(width, height, possibleBlocks, pieceProbs, rewardExponent, evilness, evilAgentType, benchmarkScore);
        gameState.verifyParameters();
    }

    public static ParameterHolder getDefaultParameters()
    {
        ParameterHolder p = new ParameterHolder();
        UtilityShop.setVersionDetails(p, new DetailsProvider());
        p.addIntegerParam("Width", Integer.valueOf(10));
        p.addIntegerParam("Height", Integer.valueOf(20));
        p.addDoubleParam("LongBlockWeight", Double.valueOf(1.0D));
        p.addDoubleParam("SquareBlockWeight", Double.valueOf(1.0D));
        p.addDoubleParam("TriBlockWeight", Double.valueOf(1.0D));
        p.addDoubleParam("SBlockWeight", Double.valueOf(1.0D));
        p.addDoubleParam("ZBlockWeight", Double.valueOf(1.0D));
        p.addDoubleParam("LBlockWeight", Double.valueOf(1.0D));
        p.addDoubleParam("JBlockWeight", Double.valueOf(1.0D));
        p.addDoubleParam("rewardExponent", Double.valueOf(1.0D));
        p.addDoubleParam("Evilness", Double.valueOf(0.0D));
        p.addIntegerParam("EvilAgentType", Integer.valueOf(0));
        p.addDoubleParam("BenchmarkScore", Double.valueOf(0.0D));
        return p;
    }

    public URL getImageURL()
    {
        URL imageURL = Tetris.class.getResource("/images/tetris.png");
        return imageURL;
    }

    public TaskSpecPayload getTaskSpecPayload(ParameterHolder P)
    {
        String taskSpec = makeTaskSpec();
        return new TaskSpecPayload(taskSpec, false, "");
    }

    public String env_init()
    {
        nsteps = 0;
        if(recordLogs)
            theEpisodeLogger = new EpisodeLogger();
        int boardSize = gameState.getHeight() * gameState.getWidth();
        int numPieces = gameState.possibleBlocks.size();
        int boardSizeObservations = 2;
        int intObsCount = boardSize + numPieces + boardSizeObservations;
        String task_spec = makeTaskSpec();
        return task_spec;
    }

    public Observation env_start()
    {
        if(recordLogs)
            theEpisodeLogger.clear();
        gameState.reset();
        gameState.spawn_block();
        gameState.blockMobile = true;
        currentScore = 0.0D;
        if(recordLogs)
        {
            int stateSummary = gameState.packStateToInteger(0, 0, true);
            theEpisodeLogger.appendLogString((new StringBuilder()).append("").append(stateSummary).toString());
        }
        Observation o = gameState.get_observation();
        return o;
    }

    public Reward_observation_terminal env_step(Action actionObject)
    {
        nsteps++;
        int theAction = 0;
        try
        {
            theAction = actionObject.intArray[0];
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.err.println((new StringBuilder()).append("Error: Action was expected to have 1 dimension but got ArrayIndexOutOfBoundsException when trying to get element 0:").append(e).toString());
            System.err.println("Error: Choosing action 0");
            theAction = 0;
        }
        if(theAction > 5 || theAction < 0)
        {
            System.err.println((new StringBuilder()).append("Invalid action selected in Tetrlais: ").append(theAction).toString());
            theAction = gameState.getRandom().nextInt(5);
        }
        if(gameState.blockMobile)
        {
            gameState.take_action(theAction);
            gameState.update();
        } else
        {
            gameState.spawn_block();
        }
        Reward_observation_terminal ro = new Reward_observation_terminal();
        ro.terminal = 1;
        ro.o = gameState.get_observation();
        if(!gameState.gameOver())
        {
            ro.terminal = 0;
            ro.r = gameState.get_score() - currentScore;
            currentScore = gameState.get_score();
        } else
        {
            ro.r = 0.0D;
            currentScore = 0.0D;
        }
        if(recordLogs)
        {
            int stateSummary = gameState.packStateToInteger(theAction, ro.terminal, false);
            theEpisodeLogger.appendLogString((new StringBuilder()).append("_").append(stateSummary).toString());
        }
        return ro;
    }

    public void env_cleanup()
    {
        savedStates.clear();
    }

    public String env_message(String theMessage) {
        EnvironmentMessages theMessageObject;
        try {
            theMessageObject = EnvironmentMessageParser.parseMessage(theMessage);
        } catch (Exception e) {
            System.err.println("Someone sent Tetris a message that wasn't RL-Viz compatible");
            return "I only respond to RL-Viz messages!";
        }


        if (theMessageObject.canHandleAutomatically(this)) {
            return theMessageObject.handleAutomatically(this);
        }

        if (theMessageObject.getTheMessageType() == rlVizLib.messaging.environment.EnvMessageType.kEnvCustom.id()) {

            String theCustomType = theMessageObject.getPayLoad();

            if (theCustomType.equals("GETTETRLAISSTATE")) {
                //It is a request for the state
                TetrisStateResponse theResponseObject = new TetrisStateResponse((int)currentScore, gameState.getWidth(), gameState.getHeight(), gameState.getNumberedStateSnapShot(), gameState.getCurrentPiece());
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

    protected Observation makeObservation()
    {
        return gameState.get_observation();
    }

    public RLVizVersion getTheVersionISupport()
    {
        return new RLVizVersion(1, 0);
    }

    public String getVisualizerClassName()
    {
        return TetrisVisualizer.class.getName();
    }

    /**
     * @deprecated Method getEpisodeSummary is deprecated
     */

    public Vector<String> getEpisodeSummary()
    {
        return null;
    }

    public String getEpisodeSummary(long charToStartOn, int charsToSend)
    {
        if(!recordLogs)
            System.err.println((new StringBuilder()).append("Someone asked for episode logs on: ").append(getClass()).append(" but logging is disabled!").toString());
        return theEpisodeLogger.getLogSubString(charToStartOn, charsToSend);
    }

    private String makeTaskSpec() {
        int boardSize = gameState.getHeight() * gameState.getWidth();
        int numPieces = gameState.possibleBlocks.size();
        int boardSizeObservations = 2;
        int intObsCount = boardSize + numPieces + boardSizeObservations;

        TaskSpecVRLGLUE3 theTaskSpecObject = new TaskSpecVRLGLUE3();
        theTaskSpecObject.setEpisodic();
        theTaskSpecObject.setDiscountFactor(1.0d);
        //First add the binary variables for the board
        theTaskSpecObject.addDiscreteObservation(new IntRange(0, 1, boardSize));
        //Now the binary features to tell what piece is falling
        theTaskSpecObject.addDiscreteObservation(new IntRange(0, 1, numPieces));
        //Now the actual board size in the observation. The reason this was here is/was because
        //there was no way to add meta-data to the task spec before.
        //First height
        theTaskSpecObject.addDiscreteObservation(new IntRange(gameState.getHeight(), gameState.getHeight()));
        //Then width
        theTaskSpecObject.addDiscreteObservation(new IntRange(gameState.getWidth(), gameState.getWidth()));

        theTaskSpecObject.addDiscreteAction(new IntRange(0, 5));
        //This is actually a lie... the rewards aren't in that range.
        theTaskSpecObject.setRewardRange(new DoubleRange(0, 1));

        //This is a better way to tell the rows and cols
        theTaskSpecObject.setExtra("EnvName:Tetris HEIGHT:" + gameState.getHeight() + " WIDTH:" + gameState.getWidth() + " Revision: " + this.getClass().getPackage().getImplementationVersion());

        String taskSpecString = theTaskSpecObject.toTaskSpec();

        TaskSpec.checkTaskSpec(taskSpecString);
        return taskSpecString;
    }

    private double currentScore;
    protected GameState gameState;
    static final int terminalScore = 0;
    Vector savedStates;
    EpisodeLogger theEpisodeLogger;
    protected boolean recordLogs;
    protected boolean allowSaveLoadState;
    protected boolean allowSaveLoadSeed;
    int nsteps;
}
