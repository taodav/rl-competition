package SamplePolyathlon.ProblemTypeD.MainPackage;

import java.util.Random;


import java.util.Vector;
import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.ProvidesEpisodeSummariesInterface;
import rlVizLib.utilities.logging.EpisodeLogger;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Random_seed_key;
import rlglue.types.Reward_observation;
import rlglue.types.State_key;

/**
 * Problem type D is a bandit problem with gaussian rewards.
 * @author btanner
 */
public abstract class AbstractPolyProblemTypeD extends EnvironmentBase implements ProvidesEpisodeSummariesInterface {

    protected PolyProblemTypeDState theState = new PolyProblemTypeDState();
    protected rlVizLib.utilities.logging.EpisodeLogger theEpisodeLogger;
    protected Random ourRandom = new Random();
    /*Constructor Business*/

    public AbstractPolyProblemTypeD() {
        this(getDefaultParameters());
    }

    public AbstractPolyProblemTypeD(ParameterHolder p) {
        super();
    }

    //This method creates the object that can be used to easily set different problem parameters
    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        return p;
    }

    /*Beginning of RL-GLUE methods*/
    public String env_init() {
        theEpisodeLogger = new EpisodeLogger();
        return SamplePolyathlon.SamplePolyathlon.genericTaskSpec();
    }

    public Observation env_start() {
        Observation theObservation = makeObservation();
        theEpisodeLogger.clear();
        theEpisodeLogger.appendLogString(theState.stringSerialize());
        return theObservation;
    }

    public Reward_observation env_step(Action a) {

        if ((a.intArray[0] < 0) || (a.intArray[0] > 3)) {
            System.out.printf("Invalid action %d, selecting an action randomly\n", a.intArray[0]);
            a.intArray[0] = (int) ourRandom.nextDouble() * 4;
        }


        double r = theState.sampleReward(a.intArray[0]);
        Reward_observation ro = new Reward_observation();
        ro.r = r;
        ro.o = makeObservation();

        ro.terminal = 1;
        theEpisodeLogger.appendLogString(theState.stringSerialize() + "_a=" + a.intArray[0] + "_r=" + ro.r + "_terminal=" + ro.terminal);
        return ro;
    }

    public void env_cleanup() {
    }

    public String env_message(String theMessage) {
        EnvironmentMessages theMessageObject;
        try {
            theMessageObject = EnvironmentMessageParser.parseMessage(theMessage);
        } catch (Exception e) {
            System.err.println("Someone sent AbstractPolyProblemTypeD a message that wasn't RL-Viz compatible");
            return "I only respond to RL-Viz messages!";
        }

        if (theMessageObject.canHandleAutomatically(this)) {
            return theMessageObject.handleAutomatically(this);
        }

//        System.out.println("We need some code written in Env Message for AbstractPolyProblemTypeD!"+theMessageObject.getClass());
//        Thread.dumpStack();

        return null;
    }

    public Random_seed_key env_get_random_seed() {
        System.err.println("IN AbstractPolyProblemTypeD we don't support Random_seed_key");
        return null;
    }

    public State_key env_get_state() {
        System.err.println("IN AbstractPolyProblemTypeD we don't support env_get_state");
        return null;
    }

    public void env_set_random_seed(Random_seed_key key) {
        System.err.println("IN AbstractPolyProblemTypeD we don't support env_set_random_seed");
    }

    public void env_set_state(State_key key) {
        System.err.println("IN AbstractPolyProblemTypeD we don't support env_set_state");
    }
    /*End of RL-Glue Methods*/

    /*Beginning of RL-VIZ Methods*/
    @Override
    protected Observation makeObservation() {
        Observation obs = new Observation(0, 4);
        obs.doubleArray[0] = 0.0d;
        obs.doubleArray[1] = 0.0d;
        obs.doubleArray[2] = 0.0d;
        obs.doubleArray[3] = 0.0d;
        return obs;
    }
    
    
        //Pretty sure this is deprecated
    public Vector<String> getEpisodeSummary() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getEpisodeSummary(long charToStartOn, int charsToSend) {
        return theEpisodeLogger.getLogSubString(charToStartOn, charsToSend);
    }

}
