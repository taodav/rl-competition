package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeC.MainPackage;

import java.net.URL;
import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeB.Messages.CGWPositionResponse;
import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeC.Messages.ProblemTypeCStateResponse;
import java.util.Random;


import org.rlcommunity.environments.competition2009.polyathlon.TrainingPolyathlon;
import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.HasAVisualizerInterface;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;
import org.rlcommunity.rlglue.codec.types.Reward_observation_terminal;
import rlVizLib.messaging.interfaces.HasImageInterface;

public abstract class AbstractPolyProblemTypeC extends EnvironmentBase implements HasAVisualizerInterface,HasImageInterface {
    /*STATIC CONSTANTS*/

    final static int stateSize = 4;
    final static int numActions = 3;
    protected PolyProblemTypeCState theState = new PolyProblemTypeCState();
    private int maxSteps = 1000000;
    private int currentNumSteps;
    private int seed = 0;
    private Random ourRandomNumber = new Random(seed);
    boolean setRandomStarts; //if true then do random starts, else, start at static position
    private int numEpisodes = 0;
    private int numSteps = 0;
    private int totalNumSteps = 0;

    /*Constructor Business*/
    public AbstractPolyProblemTypeC() {
        this(getDefaultParameters());
    }

    public AbstractPolyProblemTypeC(ParameterHolder p) {
        super();
    }

    //This method creates the object that can be used to easily set different problem parameters
    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        return p;
    }

    /*Beginning of RL-GLUE methods*/
    public String env_init() {

        numEpisodes = 0;
        numSteps = 0;
        totalNumSteps = 0;
        String taskSpec=TrainingPolyathlon.makeTaskSpec();
        return taskSpec;
    }

    public Observation env_start() {
        numEpisodes++;
        totalNumSteps++;
        numSteps = 0;
        if (setRandomStarts) {
            theState.set_initial_position_random(ourRandomNumber);
        } else {
            theState.set_initial_position_at_bottom();
        }

        currentNumSteps = 0;

        return makeObservation();
    }

    public Reward_observation_terminal env_step(Action a) {
        totalNumSteps++;
        numSteps++;
        if ((a.intArray[0] < 0) || (a.intArray[0] > 2)) {
            System.out.printf("Invalid action %d, selecting an action randomly\n", a.intArray[0]);
            a.intArray[0] = (int) ourRandomNumber.nextDouble() * 3;
        }
        double torque = a.intArray[0] - 1.0;

        theState.applyTorque(torque);

        currentNumSteps++;

        Reward_observation_terminal ro = new Reward_observation_terminal();
        ro.r = -1;
        ro.o = makeObservation();

        ro.terminal = 0;
        if (theState.inGoalRegion() || (currentNumSteps > maxSteps)) {
            ro.terminal = 1;
            ro.r=0;
        }
        return ro;
    }

    public void env_cleanup() {
    }

    public String env_message(String theMessage) {
        EnvironmentMessages theMessageObject;
        try {
            theMessageObject = EnvironmentMessageParser.parseMessage(theMessage);
        } catch (Exception e) {
            System.err.println("Someone sent a message that wasn't RL-Viz compatible");
            return "I only respond to RL-Viz messages!";
        }

        if (theMessageObject.canHandleAutomatically(this)) {
            return theMessageObject.handleAutomatically(this);
        }

        if (theMessageObject.getTheMessageType() == rlVizLib.messaging.environment.EnvMessageType.kEnvCustom.id()) {

            String theCustomType = theMessageObject.getPayLoad();

            if (theCustomType.equals("GETSTATE")) {
                //It is a request for the state
                ProblemTypeCStateResponse theResponseObject = new ProblemTypeCStateResponse(theState.getTheta1(), theState.getTheta2());
                return theResponseObject.makeStringResponse();
            }



            System.out.println("We need some code written in Env Message for Acrobot.. unknown custom message type received" + theMessage);
            Thread.dumpStack();

            return null;
        }

        System.out.println("We need some code written in Env Message for  Acrobot!");
        Thread.dumpStack();

        return null;
    }

    /*End of RL-Glue Methods*/
    /*Beginning of RL-VIZ Methods*/
    @Override
    protected Observation makeObservation() {
        Observation obs = new Observation(0, 4);
        obs.doubleArray[0] = theState.getTheta1();
        obs.doubleArray[1] = theState.getTheta2();
        obs.doubleArray[2] = theState.getTheta1Dot();
        obs.doubleArray[3] = theState.getTheta2Dot();
        return obs;
    }

    public URL getImageURL() {
        URL imageURL = TrainingPolyathlon.class.getResource("/images/poly.png");
        return imageURL;
    }


    /*End of RL-VIZ Methods*/
    public String getVisualizerClassName() {
        return "SamplePolyathlon.ProblemTypeC.Visualizer.AbstractPolyProblemTypeCVisualizer";
    }
}
