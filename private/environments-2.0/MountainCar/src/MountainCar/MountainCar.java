/* Mountain Car Domain
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
package MountainCar;

import java.util.StringTokenizer;
import java.util.Vector;

import mcMessages.MCGoalResponse;
import mcMessages.MCHeightResponse;
import mcMessages.MCStateResponse;
import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.general.RLVizVersion;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.HasAVisualizerInterface;
import rlVizLib.messaging.interfaces.RLVizVersionResponseInterface;
import rlVizLib.messaging.interfaces.getEnvMaxMinsInterface;
import rlVizLib.messaging.interfaces.getEnvObsForStateInterface;
import rlVizLib.utilities.TaskSpecObject;
import rlglue.environment.Environment;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Random_seed_key;
import rlglue.types.Reward_observation;
import rlglue.types.State_key;
import java.util.Random;

/*
 * July 2007
 * This is the Java Version MountainCar Domain from the RL-Library.  
 * Brian Tanner ported it from the Existing RL-Library to Java.
 * I found it here: http://rlai.cs.ualberta.ca/RLR/environment.html
 * 
 * The methods in here are sorted by importance in terms of what's important to know about for playing with the dynamics of the system.
 */
import rlVizLib.general.hasVersionDetails;
import rlVizLib.messaging.interfaces.ProvidesEpisodeSummariesInterface;
import rlVizLib.messaging.interfaces.RLVizVersionResponseInterface;
import rlVizLib.utilities.UtilityShop;
import rlVizLib.utilities.random.myBeta;


public abstract class MountainCar extends EnvironmentBase implements getEnvMaxMinsInterface,
        getEnvObsForStateInterface,
        RLVizVersionResponseInterface,
        HasAVisualizerInterface,
        ProvidesEpisodeSummariesInterface {

    static final int numActions = 3;
    protected MountainCarState theState = null;
    protected Vector<MountainCarState> savedStates = null;


    rlVizLib.utilities.logging.EpisodeLogger theEpisodeLogger;
    protected boolean recordLogs = false;
    public boolean allowSaveLoadState = true;
    public boolean allowSaveLoadSeed = true;

    //Problem parameters have been moved to MountainCar State

    private Random randomGenerator = new Random();
    private myBeta betaGenerator = new myBeta(5, 2, randomGenerator);

    private Random getRandom() {
        return randomGenerator;
    }
    double reportedMinPosition;
    double reportedMaxPosition;
    double reportedMinVelocity;
    double reportedMaxVelocity;

    public String env_init() {
        if(recordLogs)theEpisodeLogger=new rlVizLib.utilities.logging.EpisodeLogger();
        savedStates = new Vector<MountainCarState>();
        //This should be like a final static member or something, or maybe it should be configurable... dunno
        int taskSpecVersion = 2;

        return taskSpecVersion + ":e:2_[f,f]_[" + reportedMinPosition + "," + reportedMaxPosition + "]_[" + reportedMinVelocity + "," + reportedMaxVelocity + "]:1_[i]_[0,2]:[-1,0]";
    }

    public Observation env_start() {
        if (recordLogs)theEpisodeLogger.clear();

        if (theState.randomStarts) {
            double fullRandPosition = (randomGenerator.nextDouble() * (theState.maxPosition + Math.abs((theState.minPosition))) - Math.abs(theState.minPosition));
            //Want to actually start in a smaller bowl
            theState.position = theState.defaultInitPosition + fullRandPosition / 3.0d;
            //Want inital velocity = 0.0d;
            theState.velocity = theState.defaultInitVelocity;
        } else {
            theState.position = theState.defaultInitPosition;
            theState.velocity = theState.defaultInitVelocity;
        }

        if (recordLogs) {
            theEpisodeLogger.appendLogString(theState.stringSerialize());
        }
        return makeObservation();
    }

    //	The constants of this height function could easily be parameterized

    public Reward_observation env_step(Action theAction) {

        int a = theAction.intArray[0];

        if (a > 2 || a < 0) {
            System.err.println("Invalid action selected in mountainCar: " + a);
            a = randomGenerator.nextInt(3);
        }

        theState.update(a);
        if (recordLogs) {
            theEpisodeLogger.appendLogString(theState.stringSerialize() + "_a=" + a + "_r=" + theState.getReward() + "_inGoal=" + theState.inGoalRegion());
        }

        return makeRewardObservation(theState.getReward(), theState.inGoalRegion());
    }


    //This method creates the object that can be used to easily set different problem parameters

    public static ParameterHolder getDefaultParameters() {

        ParameterHolder p = new ParameterHolder();

        rlVizLib.utilities.UtilityShop.setVersionDetails(p, new DetailsProvider());
        p.addBooleanParam("useCompetitionMC", false);
        p.addBooleanParam("randomStartStates", true);

        //Remember to force everything to be in bounds in makeObservation

        p.addDoubleParam("scaleP", 1.0d);//should be between 0 and 1.0, probably not too close to 0
        p.addDoubleParam("scaleV", 1.0d);//should be between 0 and 1.0, probably not too close to 0



        p.addDoubleParam("pOffset", 0.0d); //should be between -1.0 and 1.0, remember to adjust task spec
        p.addDoubleParam("vOffset", 0.0d); //should be between -1.0 and 1.0, remember to adjust task spec


        p.addDoubleParam("pNoiseDivider", 20.0d); //should be between 5.0 and 20.0
        p.addDoubleParam("vNoiseDivider", 20.0d); //should be between 5.0 and 20.0

        p.addDoubleParam("AccelBiasMean", 1.0d); //should be between .8 and whatever larger, say 2.0

        return p;
    }
    public boolean useCompetitionMC = true;

    public MountainCar(ParameterHolder p) {
        this(p, false);
    }

    public MountainCar(ParameterHolder p, boolean useCompetitionMode) {
        super();
        this.useCompetitionMC = useCompetitionMode;
        theState = new MountainCarState(randomGenerator, betaGenerator);
        if (p != null) {
            if (!p.isNull()) {
                theState.randomStarts = p.getBooleanParam("randomStartStates");
                theState.scaleP = p.getDoubleParam("scaleP");
                theState.scaleV = p.getDoubleParam("scaleV");
                theState.pOffset = p.getDoubleParam("pOffset");
                theState.vOffset = p.getDoubleParam("vOffset");
                theState.pNoiseDivider = p.getDoubleParam("pNoiseDivider");
                theState.vNoiseDivider = p.getDoubleParam("vNoiseDivider");
                theState.AccelBiasMean = p.getDoubleParam("AccelBiasMean");
            }
        }
        setupRangesAndStuff();
    }

    private void setupRangesAndStuff() {
        //Double ReportedMinPosition should be 
            //actualMinPosition - minOffset - maxPositionNoise
        double posRange = theState.maxPosition - theState.minPosition;
        //The beta will be in [-.5,.5], and multiply that by the PosRange and divide by something in [5,20]
        double maxPosNoise = (posRange / 5.0) * .5d;

        //These won't vary with the parameters
        if (useCompetitionMC) {
            reportedMinPosition = theState.minPosition - 1.0d - maxPosNoise;
            reportedMaxPosition = theState.maxPosition + 1.0d + maxPosNoise;
        } else {
            reportedMinPosition = theState.minPosition;
            reportedMaxPosition = theState.maxPosition;
        }
        double velRange = theState.maxVelocity - theState.minVelocity;
        //The beta will be in [-.5,.5], and multiply that by the PosRange and divide by something in [5,20]
        double maxVelNoise = (velRange / 5.0) * .5d;

        //These won't vary with the parameters
        if (useCompetitionMC) {
            reportedMinVelocity = theState.minVelocity - 1.0d - maxVelNoise;
            reportedMaxVelocity = theState.minVelocity + 1.0d + maxVelNoise;
        } else {
            reportedMinVelocity = theState.minVelocity;
            reportedMaxVelocity = theState.maxVelocity;
        }

    }

    public String env_message(String theMessage) {
        EnvironmentMessages theMessageObject;
        try {
            theMessageObject = EnvironmentMessageParser.parseMessage(theMessage);
        } catch (NotAnRLVizMessageException e) {
            System.err.println("Someone sent mountain Car a message that wasn't RL-Viz compatible");
            return "I only respond to RL-Viz messages!";
        }

        if (theMessageObject.canHandleAutomatically(this)) {
            String theResponseString = theMessageObject.handleAutomatically(this);
            return theResponseString;
        }

        //		If it wasn't handled automatically, maybe its a custom Mountain Car Message
        if (theMessageObject.getTheMessageType() == rlVizLib.messaging.environment.EnvMessageType.kEnvCustom.id()) {

            String theCustomType = theMessageObject.getPayLoad();

            if (theCustomType.equals("GETMCSTATE")) {
                //It is a request for the state
                double position = theState.position;
                double velocity = theState.velocity;
                double height = this.getHeight();
                double deltaheight = theState.getHeightAtPosition(position + .05);
                MCStateResponse theResponseObject = new MCStateResponse(position, velocity, height, deltaheight);
                return theResponseObject.makeStringResponse();
            }

            if (theCustomType.startsWith("GETHEIGHTS")) {
                Vector<Double> theHeights = new Vector<Double>();

                StringTokenizer theTokenizer = new StringTokenizer(theCustomType, ":");
                //throw away the first token
                theTokenizer.nextToken();

                int numQueries = Integer.parseInt(theTokenizer.nextToken());
                for (int i = 0; i < numQueries; i++) {
                    double thisPoint = Double.parseDouble(theTokenizer.nextToken());
                    theHeights.add(theState.getHeightAtPosition(thisPoint));
                }

                MCHeightResponse theResponseObject = new MCHeightResponse(theHeights);
                return theResponseObject.makeStringResponse();
            }

            if (theCustomType.startsWith("GETMCGOAL")) {
                MCGoalResponse theResponseObject = new MCGoalResponse(theState.goalPosition);
                return theResponseObject.makeStringResponse();
            }

        }
        System.err.println("We need some code written in Env Message for MountainCar.. unknown request received: " + theMessage);
        Thread.dumpStack();
        return null;
    }

    private double makeNoise(double minValue, double maxValue) {
        double noise = betaGenerator.sampleDouble() - .5;
        double varRange = maxValue - minValue;
        noise *= varRange;
        return noise;
    }

    @Override
	protected Observation makeObservation() {
        final boolean debugThis = false;

        double noiseP = makeNoise(theState.maxPosition, theState.minPosition);
        double noiseV = makeNoise(theState.maxVelocity, theState.minVelocity);

        noiseP /= theState.pNoiseDivider;
        noiseV /= theState.vNoiseDivider;

        Observation currentObs = new Observation(0, 2);

        double obsPos = theState.position + theState.pOffset + noiseP;
        if (obsPos < reportedMinPosition) {
            obsPos = reportedMinPosition;
        }
        if (obsPos > reportedMaxPosition) {
            obsPos = reportedMaxPosition;
        }

        double obsVel = theState.velocity + theState.vOffset + noiseV;

        if (obsVel < reportedMinVelocity) {
            if (debugThis) {
                System.out.println("\t " + obsVel + " < " + reportedMinVelocity + " so setting to: " + reportedMinVelocity);
            }
            obsVel = reportedMinVelocity;
        }
        if (obsVel > reportedMaxVelocity) {
            if (debugThis) {
                System.out.println("\t " + obsVel + " > " + reportedMaxVelocity + " so setting to: " + reportedMaxVelocity);
            }
            obsVel = reportedMaxVelocity;
        }

        currentObs.doubleArray[0] = obsPos;
        currentObs.doubleArray[1] = obsVel;

        if (debugThis) {
            System.out.printf("Observed Pos  Truncated = %.3f \t Raw= %.3f <-- %.3f + %.3f + %.3f\n", obsPos, (theState.position + theState.pOffset + noiseP), theState.position, theState.pOffset, noiseP);
        }
        if (debugThis) {
            System.out.printf("Observed Velo Truncated=%.3f \t Raw= %.3f <-- %.3f+ %.3f + %.3f\n", obsVel, (theState.velocity + theState.vOffset + noiseV), theState.velocity, theState.vOffset, noiseV);
        }

        return currentObs;
    }

    public MountainCar(boolean useCompetitionMode) {
        this(getDefaultParameters(), useCompetitionMode);
    }

    public MountainCar() {
        this(getDefaultParameters(), false);
    }

    public void env_cleanup() {
        if (savedStates != null) {
            savedStates.clear();
        }
    }

    //
//This has a side effect, it changes the random order.
//

    public Random_seed_key env_get_random_seed() {
        if (allowSaveLoadSeed) {
            Random_seed_key k = new Random_seed_key(2, 0);
            long newSeed = getRandom().nextLong();
            getRandom().setSeed(newSeed);
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
            getRandom().setSeed(storedSeed);
            return;
        }
        System.err.println("env_set_random_seed() called in: " + getClass() + " but it is disabled");
    }

    public State_key env_get_state() {
        if (allowSaveLoadState) {
            savedStates.add(new MountainCarState(theState));
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
            MountainCarState oldState = savedStates.get(k.intArray[0]);
            this.theState = new MountainCarState(oldState);
            return;
        }
        System.err.println("env_set_state() called in: " + getClass() + " but it is disabled");

    }

    public double getMaxValueForQuerableVariable(int dimension) {
        if (dimension == 0) {
            return reportedMaxPosition;
        } else {
            return reportedMaxVelocity;
        }
    }

    public double getMinValueForQuerableVariable(int dimension) {
        if (dimension == 0) {
            return reportedMinPosition;
        } else {
            return reportedMinVelocity;
        }
    }


    //This is really easy in mountainCar because you observe exactly the state
        //Oops, that's not true anymore, we have noise and offsets...

    public Observation getObservationForState(Observation theState) {
        return theState;
    }

    public int getNumVars() {
        return 2;
    }

/*    public static void main(String args[]) {
        Environment mcEnv = new MountainCar();
        String taskSpec = mcEnv.env_init();
        TaskSpecObject parsedTaskSpec = new TaskSpecObject(taskSpec);
        System.out.println(parsedTaskSpec);

    }
*/
    private double getHeight() {
        return theState.getHeightAtPosition(theState.position);
    }

    public RLVizVersion getTheVersionISupport() {
        return new RLVizVersion(1, 1);
    }

    public String getVisualizerClassName() {
        return "visualizers.mountainCar.MountainCarVisualizer";
    }


    public String getEpisodeSummary(long charToStartOn, int charsToSend) {
        if (!recordLogs) {
            System.err.println("Someone asked for episode logs on: " + getClass() + " but logging is disabled!");
        }
        return theEpisodeLogger.getLogSubString(charToStartOn, charsToSend);
    }

    /**
     * This is deprecated in RLVizLib, we're just implementing it to not break interface.
     * @return
     * @deprecated
     */
    public Vector<String> getEpisodeSummary() {
        return null;
    }
}
class DetailsProvider implements hasVersionDetails {

    public String getName() {
        return "Mountain Car 1.1";
    }

    public String getShortName() {
        return "Mount-Car";
    }

    public String getAuthors() {
        return "Richard Sutton, Adam White, Brian Tanner";
    }

    public String getInfoUrl() {
        return "http://rl-library.googlecode.com";
    }

    public String getDescription() {
        return "RL-Library Java Version of the classic Mountain Car RL-Problem.";
    }
}

