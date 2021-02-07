package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeE.MainPackage;

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
 * Problem type D is a random-state deterministic bandit problem
 * IE: Action affects reward but not the state
 * @author btanner
 */
public abstract class AbstractPolyProblemTypeE extends EnvironmentBase implements ProvidesEpisodeSummariesInterface, HasImageInterface {

    protected PolyProblemTypeEState theState = new PolyProblemTypeEState();
    protected rlVizLib.utilities.logging.EpisodeLogger theEpisodeLogger;
    protected Random ourRandom = new Random();
    /*Constructor Business*/

    public AbstractPolyProblemTypeE() {
        this(getDefaultParameters());
    }

    public AbstractPolyProblemTypeE(ParameterHolder p) {
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
        //The Polyathlon wrapper will do the task spec.
        return TrainingPolyathlon.makeTaskSpec();
    }

    public Observation env_start() {
        theState.generateNextState();
        Observation theObservation = makeObservation();
        theEpisodeLogger.clear();
        theEpisodeLogger.appendLogString(theState.stringSerialize());
        return theObservation;
    }

    abstract public double generateReward(Observation o, int a);

    public Reward_observation_terminal env_step(Action a) {
        theState.generateNextState();

        if ((a.intArray[0] < 0) || (a.intArray[0] > 5)) {
            System.out.printf("Invalid action %d, selecting an action randomly\n", a.intArray[0]);
            a.intArray[0] = (int) ourRandom.nextDouble() * 5;
        }


        Reward_observation_terminal ro = new Reward_observation_terminal();
        ro.o = makeObservation();


        double r = generateReward(ro.o, a.intArray[0]);

        ro.r = r;

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
            System.err.println("Someone sent AbstractPolyProblemTypeE a message that wasn't RL-Viz compatible");
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
        return theState.makeObservation();
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
