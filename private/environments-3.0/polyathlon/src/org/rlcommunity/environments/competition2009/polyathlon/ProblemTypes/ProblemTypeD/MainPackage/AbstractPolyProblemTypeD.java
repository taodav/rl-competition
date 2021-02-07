package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeD.MainPackage;

import java.net.URL;
import java.util.Random;


import java.util.Vector;
import org.rlcommunity.environments.competition2009.polyathlon.TrainingPolyathlon;
import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.ProvidesEpisodeSummariesInterface;
import rlVizLib.utilities.logging.EpisodeLogger;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;
import org.rlcommunity.rlglue.codec.types.Reward_observation_terminal;
import rlVizLib.messaging.interfaces.HasImageInterface;

/**
 * Problem type D is a bandit problem with gaussian rewards.
 * @author btanner
 */
public abstract class AbstractPolyProblemTypeD extends EnvironmentBase implements ProvidesEpisodeSummariesInterface, HasImageInterface {

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
        String taskSpec = TrainingPolyathlon.makeTaskSpec();
        return taskSpec;
    }

    public Observation env_start() {
        Observation theObservation = makeObservation();
        theEpisodeLogger.clear();
        theEpisodeLogger.appendLogString(theState.stringSerialize());
        return theObservation;
    }

    public Reward_observation_terminal env_step(Action a) {

        if ((a.intArray[0] < 0) || (a.intArray[0] > 5)) {
            System.out.printf("Invalid action %d, selecting an action randomly\n", a.intArray[0]);
            a.intArray[0] = (int) ourRandom.nextDouble() * 4;
        }


        double r = theState.sampleReward(a.intArray[0]);
        Reward_observation_terminal ro = new Reward_observation_terminal();
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

    /*End of RL-Glue Methods*/

    /*Beginning of RL-VIZ Methods*/
    @Override
    protected Observation makeObservation() {
        Observation obs = new Observation(0, 6);
        obs.doubleArray[0] = 0.0d;
        obs.doubleArray[1] = 0.0d;
        obs.doubleArray[2] = 0.0d;
        obs.doubleArray[3] = 0.0d;
        obs.doubleArray[4] = 0.0d;
        obs.doubleArray[5] = 0.0d;
        return obs;
    }

    //Pretty sure this is deprecated
    public Vector<String> getEpisodeSummary() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getEpisodeSummary(long charToStartOn, int charsToSend) {
        return theEpisodeLogger.getLogSubString(charToStartOn, charsToSend);
    }

    public URL getImageURL() {
        URL imageURL = TrainingPolyathlon.class.getResource("/images/poly.png");
        return imageURL;
    }
}
