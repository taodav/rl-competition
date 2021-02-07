package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeH.MainPackage;

import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeG.Messages.CartpoleTrackResponse;
import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeG.Visualizer.CartPoleVisualizer;
import org.rlcommunity.environments.competition2009.polyathlon.TrainingPolyathlon;
import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.HasAVisualizerInterface;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;

import org.rlcommunity.rlglue.codec.types.Reward_observation_terminal;

/**
 * This uses the cart-pole physics but is a swing-up task with negative reward
 * until the pole is balanced.
 * This is based on David Finton's code from:
 * http://pages.cs.wisc.edu/~finton/poledriver.html which in turn is credited to
 * The Barto, Sutton, and Anderson cart-pole simulation. 
 * Available (not in 2008) by anonymous ftp from ftp.gte.com, as 
 * /pub/reinforcement-learning/pole.c.
 * Update (May 2009): the original pole.c is available from the UMass RL Repository
 *    Umass RLR: http://www-anw.cs.umass.edu/rlr/domains.html
 *    pole.c in cpole.tar at: http://www-anw.cs.umass.edu/rlr/distcode/cpole.tar
 * @author btanner
 */
public abstract class AbstractPolyProblemTypeH extends EnvironmentBase implements HasAVisualizerInterface {

    final static double GRAVITY = 9.8;
    final static double MASSCART = 1.0;
    final static double MASSPOLE = 0.1;
    final static double TOTAL_MASS = (MASSPOLE + MASSCART);
    final static double LENGTH = 0.5;	  /* actually half the pole's length */

    final static double POLEMASS_LENGTH = (MASSPOLE * LENGTH);
    final static double FORCE_MAG = 10.0;
    final static double TAU = 0.02;	  /* seconds between state updates */

    final static double FOURTHIRDS = 4.0d / 3.0d;
    final static double DEFAULTLEFTCARTBOUND = -2.4;
    final static double DEFAULTRIGHTCARTBOUND = 2.4;
    final static double DEFAULTLEFTANGLEBOUND = -Math.toRadians(12.0d);
    final static double DEFAULTRIGHTANGLEBOUND = Math.toRadians(12.0d);
    double leftCartBound;
    double rightCartBound;
    double leftAngleBound = Math.PI;
    double rightAngleBound = -Math.PI;    //State variables
    double x;			/* cart position, meters */

    double x_dot;			/* cart velocity */

    double theta;			/* pole angle, radians */

    double theta_dot;		/* pole angular velocity */


    public AbstractPolyProblemTypeH() {
        this(getDefaultParameters());
    }

    public AbstractPolyProblemTypeH(ParameterHolder p) {
        super();
        if (p != null) {
            if (!p.isNull()) {
                this.leftCartBound = p.getDoubleParam("leftCart");
                rightCartBound = p.getDoubleParam("rightCart");

            }
        }
    }

    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();

        p.addDoubleParam("Terminal Left Cart Position", DEFAULTLEFTCARTBOUND);
        p.addDoubleParam("Terminal Right Cart Position", DEFAULTRIGHTCARTBOUND);

        p.setAlias("leftCart", "Terminal Left Cart Position");
        p.setAlias("rightCart", "Terminal Right Cart Position");
        return p;
    }


    /*RL GLUE METHODS*/
    public String env_init() {
        x = 0.0f;
        x_dot = 0.0f;
        theta = 0.0f;
        theta_dot = 0.0f;

        String taskSpec = TrainingPolyathlon.makeTaskSpec();
        return taskSpec;
    }

    public Observation env_start() {
        x = 0.0f;
        x_dot = 0.0f;
        theta = Math.PI;
        theta_dot = 0.0f;
        return makeObservation();
    }

    public Reward_observation_terminal env_step(Action action) {
        double xacc;
        double thetaacc;
        double force;
        double costheta;
        double sintheta;
        double temp;

        if (action.intArray[0] > 0) {
            force = FORCE_MAG;
        } else {
            force = -FORCE_MAG;
        }

        costheta = Math.cos(theta);
        sintheta = Math.sin(theta);

        temp = (force + POLEMASS_LENGTH * theta_dot * theta_dot * sintheta) / TOTAL_MASS;

        thetaacc = (GRAVITY * sintheta - costheta * temp) / (LENGTH * (FOURTHIRDS - MASSPOLE * costheta * costheta / TOTAL_MASS));

        xacc = temp - POLEMASS_LENGTH * thetaacc * costheta / TOTAL_MASS;

        /*** Update the four state variables, using Euler's method. ***/
        x += TAU * x_dot;
        x_dot += TAU * xacc;
        theta += TAU * theta_dot;
        theta_dot += TAU * thetaacc;

        if (theta_dot > 9.0d) {
            theta_dot = 9.0d;
        }
        if (theta_dot < -9.0d) {
            theta_dot = -9.0d;
        }

        if (x_dot > 6.0d) {
            x_dot = 6.0d;
        }
        if (x_dot < -6.0d) {
            x_dot = -6.0d;
        }

        /**These probably never happen because the pole would crash **/
        while (theta > Math.PI) {
            theta -= 2.0d * Math.PI;
        }
        while (theta < -Math.PI) {
            theta += 2.0d * Math.PI;
        }


        double reward = -.001d;
        int terminal = 0;

        if (inFailure()) {
            reward = -1.0d;
            terminal = 1;
        }

        if (inSuccess()) {
            reward = 1.0d;
            terminal = 1;
        }

        return new Reward_observation_terminal(reward, makeObservation(), terminal);
    }

    public void env_cleanup() {
    }

    public String env_message(String theMessage) {
        EnvironmentMessages theMessageObject;
        try {
            theMessageObject = EnvironmentMessageParser.parseMessage(theMessage);
        } catch (NotAnRLVizMessageException e) {
            System.err.println("Someone sent " + this.getClass().getName() + " a message that wasn't RL-Viz compatible");
            return "I only respond to RL-Viz messages!";
        }

        if (theMessageObject.canHandleAutomatically(this)) {
            return theMessageObject.handleAutomatically(this);
        }

//		If it wasn't handled automatically, maybe its a custom Mountain Car Message
        if (theMessageObject.getTheMessageType() == rlVizLib.messaging.environment.EnvMessageType.kEnvCustom.id()) {

            String theCustomType = theMessageObject.getPayLoad();


            if (theCustomType.equals("GETCARTPOLETRACK")) {
                //It is a request for the state
                CartpoleTrackResponse theResponseObject = new CartpoleTrackResponse(leftCartBound, rightCartBound, leftAngleBound, rightAngleBound);
                return theResponseObject.makeStringResponse();
            }
        }
        return null;
    }

    /*END OF RL_GLUE FUNCTIONS*/

    /*RL-VIZ Requirements*/
    @Override
    protected Observation makeObservation() {
        Observation returnObs = new Observation(0, 4);
        returnObs.doubleArray[0] = x;
        returnObs.doubleArray[1] = x_dot;
        returnObs.doubleArray[2] = theta;
        returnObs.doubleArray[3] = theta_dot;

        return returnObs;
    }

    /*END OF RL-VIZ REQUIREMENTS*/
    /*CART POLE SPECIFIC FUNCTIONS*/
    private boolean inFailure() {
        if (x < leftCartBound || x > rightCartBound) {
            return true;
        } /* to signal failure */
        return false;

    }

    private boolean inSuccess() {
        if (theta < Math.PI / 4.0d && theta > -Math.PI / 4.0d && theta_dot < 1.0d && theta_dot > -1.0d) {
            return true;
        }
        return false;
    }

    public double getLeftCartBound() {
        return this.leftCartBound;
    }

    public double getRightCartBound() {
        return this.rightCartBound;
    }

    public double getRightAngleBound() {
        return this.rightAngleBound;
    }

    public double getLeftAngleBound() {
        return this.leftAngleBound;
    }

    public String getVisualizerClassName() {
        return CartPoleVisualizer.class.getName();
    }
//    private String makeTaskSpec() {
//
//        double xMin = leftCartBound;
//        double xMax = rightCartBound;
//
//        //Dots are guesses
//        double xDotMin = -6.0d;
//        double xDotMax = 6.0d;
//        double thetaMin = leftAngleBound;
//        double thetaMax = rightAngleBound;
//        double thetaDotMin = -6.0d;
//        double thetaDotMax = 6.0d;
//
//        TaskSpecVRLGLUE3 theTaskSpecObject = new TaskSpecVRLGLUE3();
//        theTaskSpecObject.setEpisodic();
//        theTaskSpecObject.setDiscountFactor(1.0d);
//        theTaskSpecObject.addContinuousObservation(new DoubleRange(xMin, xMax));
//        theTaskSpecObject.addContinuousObservation(new DoubleRange(xDotMin, xDotMax));
//        theTaskSpecObject.addContinuousObservation(new DoubleRange(thetaMin, thetaMax));
//        theTaskSpecObject.addContinuousObservation(new DoubleRange(thetaDotMin, thetaDotMax));
//        theTaskSpecObject.addDiscreteAction(new IntRange(0, 1));
//        theTaskSpecObject.setRewardRange(new DoubleRange(-1, 0));
//        theTaskSpecObject.setExtra("EnvName:CartPole");
//
//        String newTaskSpecString = theTaskSpecObject.toTaskSpec();
//        TaskSpec.checkTaskSpec(newTaskSpecString);
//
//        return newTaskSpecString;
//    }
}



