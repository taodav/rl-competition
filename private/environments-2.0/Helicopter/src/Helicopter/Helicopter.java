/* Helicopter Domain  for RL - Competition - RLAI's Port of Pieter Abbeel's code submission
 * Copyright (C) 2007, Pieter Abbeel
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
package Helicopter;

import Helicopter.messages.HelicopterRangeResponse;
import Helicopter.messages.HelicopterStateResponse;
import java.util.Vector;
import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.general.RLVizVersion;
import rlVizLib.general.hasVersionDetails;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.HasAVisualizerInterface;
import rlVizLib.messaging.interfaces.ProvidesEpisodeSummariesInterface;
import rlVizLib.messaging.interfaces.RLVizVersionResponseInterface;
import rlVizLib.utilities.UtilityShop;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Random_seed_key;
import rlglue.types.Reward_observation;
import rlglue.types.State_key;

public abstract class Helicopter extends EnvironmentBase implements RLVizVersionResponseInterface, HasAVisualizerInterface, ProvidesEpisodeSummariesInterface {

    Observation o;
    HelicopterState heli = new HelicopterState();
    Reward_observation ro;
    Random_seed_key random_seed = null;
    Vector<HelicopterState> savedStates = new Vector<HelicopterState>();

    rlVizLib.utilities.logging.EpisodeLogger theEpisodeLogger;
    protected boolean recordLogs = false;
    public boolean allowSaveLoadState = true;
    public boolean allowSaveLoadSeed = true;

    public Helicopter() {
        this(getDefaultParameters());
    }

    public Helicopter(ParameterHolder p) {
        if (p != null) {
            if (!p.isNull()) {
                setWind0(p.getDoubleParam("wind0"));
                setWind1(p.getDoubleParam("wind1"));
            }
        }
    }

    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        rlVizLib.utilities.UtilityShop.setVersionDetails(p, new DetailsProvider());

        p.addDoubleParam("Wind 0 [-1.0d,1.0d]", 0.0d);
        p.setAlias("wind0", "Wind 0 [-1.0d,1.0d]");
        p.addDoubleParam("Wind 1 [-1.0d,1.0d]", 0.0d);
        p.setAlias("wind1", "Wind 1 [-1.0d,1.0d]");
        return p;
    }

    public void setWind0(double newValue) {
        heli.wind[0] = newValue * HelicopterState.WIND_MAX;
    }

    public void setWind1(double newValue) {
        heli.wind[1] = newValue * HelicopterState.WIND_MAX;
    }

    @Deprecated
    public String getOldTaskSpec(){
        String oldSpec = String.format("2:e:12_[f,f,f,f,f,f,f,f,f,f,f,f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]:4_[f,f,f,f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]:[]", HelicopterState.MAX_VEL, HelicopterState.MAX_VEL, HelicopterState.MAX_VEL, HelicopterState.MAX_VEL, HelicopterState.MAX_VEL, HelicopterState.MAX_VEL, HelicopterState.MAX_POS, HelicopterState.MAX_POS, HelicopterState.MAX_POS, HelicopterState.MAX_POS, HelicopterState.MAX_POS, HelicopterState.MAX_POS, HelicopterState.MAX_RATE, HelicopterState.MAX_RATE, HelicopterState.MAX_RATE, HelicopterState.MAX_RATE, HelicopterState.MAX_RATE, HelicopterState.MAX_RATE, HelicopterState.MAX_QUAT, HelicopterState.MAX_QUAT, HelicopterState.MAX_QUAT, HelicopterState.MAX_QUAT, HelicopterState.MAX_QUAT, HelicopterState.MAX_QUAT, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION);
        return oldSpec;
    }
    public String env_init() {
        if(recordLogs)theEpisodeLogger=new rlVizLib.utilities.logging.EpisodeLogger();
        /*initializing the map struct and an observation object*/
        String Task_spec = "";
        o = new Observation(0, 12);

        /* Create and Return task specification */
//        Task_spec = String.format("2:e:12_[f,f,f,f,f,f,f,f,f,f,f,f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]:4_[f,f,f,f]_[-%f,%f]_[-%f,%f]_[-%f,%f]_[-%f,%f]:[]", HelicopterState.MAX_VEL, HelicopterState.MAX_VEL, HelicopterState.MAX_VEL, HelicopterState.MAX_VEL, HelicopterState.MAX_VEL, HelicopterState.MAX_VEL, HelicopterState.MAX_POS, HelicopterState.MAX_POS, HelicopterState.MAX_POS, HelicopterState.MAX_POS, HelicopterState.MAX_POS, HelicopterState.MAX_POS, HelicopterState.MAX_RATE, HelicopterState.MAX_RATE, HelicopterState.MAX_RATE, HelicopterState.MAX_RATE, HelicopterState.MAX_RATE, HelicopterState.MAX_RATE, HelicopterState.MAX_QUAT, HelicopterState.MAX_QUAT, HelicopterState.MAX_QUAT, HelicopterState.MAX_QUAT, HelicopterState.MAX_QUAT, HelicopterState.MAX_QUAT, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION, HelicopterState.MAX_ACTION);
        Task_spec += "2:e:12_[f,f,f,f,f,f,f,f,f,f,f,f]_";
        Task_spec += "[" + -HelicopterState.MAX_VEL + "," + HelicopterState.MAX_VEL + "]_";
        Task_spec += "[" + -HelicopterState.MAX_VEL + "," + HelicopterState.MAX_VEL+ "]_";
        Task_spec += "[" + -HelicopterState.MAX_VEL + "," + HelicopterState.MAX_VEL + "]_";
        Task_spec += "[" + -HelicopterState.MAX_POS + "," + HelicopterState.MAX_POS + "]_";
        Task_spec += "[" + -HelicopterState.MAX_POS + "," + HelicopterState.MAX_POS + "]_";
        Task_spec += "[" + -HelicopterState.MAX_POS + "," + HelicopterState.MAX_POS + "]_";
        Task_spec += "[" + -HelicopterState.MAX_RATE + "," + HelicopterState.MAX_RATE + "]_";
        Task_spec += "[" + -HelicopterState.MAX_RATE + "," + HelicopterState.MAX_RATE + "]_";
        Task_spec += "[" + -HelicopterState.MAX_RATE + "," + HelicopterState.MAX_RATE + "]_";
        Task_spec += "[" + -HelicopterState.MAX_QUAT + "," + HelicopterState.MAX_QUAT + "]_";
        Task_spec += "[" + -HelicopterState.MAX_QUAT + "," + HelicopterState.MAX_QUAT + "]_";
        Task_spec += "[" + -HelicopterState.MAX_QUAT + "," + HelicopterState.MAX_QUAT + "]";
        Task_spec += ":4_[f,f,f,f]_";
        Task_spec += "[" + -HelicopterState.MAX_ACTION + "," + HelicopterState.MAX_ACTION + "]_";
        Task_spec += "[" + -HelicopterState.MAX_ACTION + "," + HelicopterState.MAX_ACTION + "]_";
        Task_spec += "[" + -HelicopterState.MAX_ACTION + "," + HelicopterState.MAX_ACTION + "]_";
        Task_spec += "[" + -HelicopterState.MAX_ACTION + "," + HelicopterState.MAX_ACTION + "]";
        Task_spec += ":[]";
        
        return Task_spec;
    }

    public Observation env_start() {
        if (recordLogs) {
           theEpisodeLogger.clear();
        }
        // start at origin, zero velocity, zero angular rate, perfectly level and facing north
        heli.reset();

        if (recordLogs) {
            String stateSerialized = heli.stringSerialize();
             theEpisodeLogger.appendLogString(stateSerialized);
        }
        return makeObservation();
    }

    public Reward_observation env_step(Action action) {
        heli.stateUpdate(action);
        heli.num_sim_steps++;
        heli.env_terminal = heli.env_terminal || (heli.num_sim_steps == HelicopterState.NUM_SIM_STEPS_PER_EPISODE);

        int isTerminal = 0;
        if (heli.env_terminal) {
            isTerminal = 1;
        }

        ro = new Reward_observation(getReward(), makeObservation(), isTerminal);
        if (recordLogs) {
             theEpisodeLogger.appendLogString(heli.stringSerialize() + "_a1=" + action.doubleArray[0] + "_a2=" + action.doubleArray[1] + "_a3=" + action.doubleArray[2] + "_a4=" + action.doubleArray[3] + "_r=" + ro.r + "_terminal=" + isTerminal);
        }

        return ro;
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
            long newSeed = heli.getRandom().nextLong();
            heli.getRandom().setSeed(newSeed);
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
            heli.getRandom().setSeed(storedSeed);
            return;
        }
        System.err.println("env_set_random_seed() called in: " + getClass() + " but it is disabled");
    }

    public State_key env_get_state() {
        if (allowSaveLoadState) {
            savedStates.add(new HelicopterState(heli));
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

            HelicopterState oldState = savedStates.get(k.intArray[0]);
            this.heli = new HelicopterState(oldState);
            return;
        }
        System.err.println("env_set_state() called in: " + getClass() + " but it is disabled");
    }

    public String env_message(String theMessage) {
        EnvironmentMessages theMessageObject;
        try {
            theMessageObject = EnvironmentMessageParser.parseMessage(theMessage);
        } catch (NotAnRLVizMessageException e) {
            System.err.println("Someone sent Helicopter a message that wasn't RL-Viz compatible");
            return "I only respond to RL-Viz messages!";
        }

        if (theMessageObject.canHandleAutomatically(this)) {
            return theMessageObject.handleAutomatically(this);
        }

        //		If it wasn't handled automatically, maybe its a custom Helicopter Message
        if (theMessageObject.getTheMessageType() == rlVizLib.messaging.environment.EnvMessageType.kEnvCustom.id()) {

            String theCustomType = theMessageObject.getPayLoad();

            if (theCustomType.equals("GETHELISTATE")) {
                HelicopterStateResponse theResponseObject = new HelicopterStateResponse(heli.velocity, heli.position, heli.angular_rate, heli.q);
                return theResponseObject.makeStringResponse();
            }

            if (theCustomType.equals("GETHELIRANGE")) {
                HelicopterRangeResponse theResponseObject = new HelicopterRangeResponse(heli.mins, heli.maxs);
                return theResponseObject.makeStringResponse();
            }

        }
        System.err.println("We need some code written in Env Message for Helicopter. unknown request received: " + theMessage);
        Thread.dumpStack();
        return null;
    }

    // goal state is all zeros, quadratically penalize for deviation:
    double getReward() {
        double reward = 0;
        if (!heli.env_terminal) { // not in terminal state
            reward -= heli.velocity.x * heli.velocity.x;
            reward -= heli.velocity.y * heli.velocity.y;
            reward -= heli.velocity.z * heli.velocity.z;
            reward -= heli.position.x * heli.position.x;
            reward -= heli.position.y * heli.position.y;
            reward -= heli.position.z * heli.position.z;
            reward -= heli.angular_rate.x * heli.angular_rate.x;
            reward -= heli.angular_rate.y * heli.angular_rate.y;
            reward -= heli.angular_rate.z * heli.angular_rate.z;
            reward -= heli.q.x * heli.q.x;
            reward -= heli.q.y * heli.q.y;
            reward -= heli.q.z * heli.q.z;
        } else { // in terminal state, obtain very negative reward b/c the agent will exit, we have to give out reward for all future times
            reward = -3.0f * HelicopterState.MAX_POS * HelicopterState.MAX_POS +
                    -3.0f * HelicopterState.MAX_RATE * HelicopterState.MAX_RATE +
                    -3.0f * HelicopterState.MAX_VEL * HelicopterState.MAX_VEL -
                    (1.0f - HelicopterState.MIN_QW_BEFORE_HITTING_TERMINAL_STATE * HelicopterState.MIN_QW_BEFORE_HITTING_TERMINAL_STATE);
            reward *= (float) (HelicopterState.NUM_SIM_STEPS_PER_EPISODE - heli.num_sim_steps);

        //System.out.println("Final reward is: "+reward+" NUM_SIM_STEPS_PER_EPISODE="+HelicopterState.NUM_SIM_STEPS_PER_EPISODE +"  heli.num_sim_steps="+ heli.num_sim_steps);
        }
        return reward;

    }

    //This method creates the object that can be used to easily set different problem parameters
    @Override
    protected Observation makeObservation() {
        return heli.makeObservation();
    }

    public RLVizVersion getTheVersionISupport() {
        return new RLVizVersion(1, 0);
    }

    public double getMaxValueForQuerableVariable(int dimension) {
        switch (dimension) {
            case 0:
                return HelicopterState.MAX_VEL;
            case 1:
                return HelicopterState.MAX_VEL;
            case 2:
                return HelicopterState.MAX_VEL;
            case 3:
                return HelicopterState.MAX_POS;
            case 4:
                return HelicopterState.MAX_POS;
            case 5:
                return HelicopterState.MAX_POS;
            case 6:
                return HelicopterState.MAX_RATE;
            case 7:
                return HelicopterState.MAX_RATE;
            case 8:
                return HelicopterState.MAX_RATE;
            case 9:
                return HelicopterState.MAX_QUAT;
            case 10:
                return HelicopterState.MAX_QUAT;
            case 11:
                return HelicopterState.MAX_QUAT;
            case 12:
                return HelicopterState.MAX_QUAT;
            default:
                System.out.println("Invalid Dimension in getMaxValueForQuerableVariable for Helicopter");
                return Double.POSITIVE_INFINITY;
        }
    }

    public double getMinValueForQuerableVariable(int dimension) {
        switch (dimension) {
            case 0:
                return -HelicopterState.MAX_VEL;
            case 1:
                return -HelicopterState.MAX_VEL;
            case 2:
                return -HelicopterState.MAX_VEL;
            case 3:
                return -HelicopterState.MAX_POS;
            case 4:
                return -HelicopterState.MAX_POS;
            case 5:
                return -HelicopterState.MAX_POS;
            case 6:
                return -HelicopterState.MAX_RATE;
            case 7:
                return -HelicopterState.MAX_RATE;
            case 8:
                return -HelicopterState.MAX_RATE;
            case 9:
                return -HelicopterState.MAX_QUAT;
            case 10:
                return -HelicopterState.MAX_QUAT;
            case 11:
                return -HelicopterState.MAX_QUAT;
            case 12:
                return -HelicopterState.MAX_QUAT;
            default:
                System.out.println("Invalid Dimension in getMaxValueForQuerableVariable for Helicopter");
                return Double.NEGATIVE_INFINITY;
        }
    }

    public String getVisualizerClassName() {
        return "visualizers.HelicopterVisualizer.HelicopterVisualizer";
    }

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
        return "Helicopter Hovering 1.0";
    }

    public String getShortName() {
        return "Helicopter";
    }

    public String getAuthors() {
        return "Pieter Abbeel, Mark Lee";
    }

    public String getInfoUrl() {
        return "http://rl-competition.org";
    }

    public String getDescription() {
        return "Helicopter Hovering Reinforcement Learning problem.";
    }
}
