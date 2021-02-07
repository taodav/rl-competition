/* Sample Polyathlon Domain
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
package org.rlcommunity.environments.competition2009.polyathlon;

import java.net.URL;
import rlVizLib.general.ParameterHolder;

import rlVizLib.general.hasVersionDetails;
import org.rlcommunity.rlglue.codec.EnvironmentInterface;
import org.rlcommunity.rlglue.codec.taskspec.TaskSpec;
import org.rlcommunity.rlglue.codec.taskspec.TaskSpecVRLGLUE3;
import org.rlcommunity.rlglue.codec.taskspec.ranges.DoubleRange;
import org.rlcommunity.rlglue.codec.taskspec.ranges.IntRange;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;
import org.rlcommunity.rlglue.codec.types.Reward_observation_terminal;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.environmentShell.TaskSpecPayload;
import rlVizLib.messaging.interfaces.HasAVisualizerInterface;
import rlVizLib.messaging.interfaces.HasImageInterface;

public class TrainingPolyathlon implements EnvironmentInterface, HasAVisualizerInterface, HasImageInterface {

    private EnvironmentInterface currentEnv = null;

    public static TaskSpecPayload getTaskSpecPayload(ParameterHolder P) {
        String taskSpec = makeTaskSpec();
        return new TaskSpecPayload(taskSpec, false, "");
    }

    public static String makeTaskSpec() {
        TaskSpecVRLGLUE3 theTaskSpecObject = new TaskSpecVRLGLUE3();
        theTaskSpecObject.setEpisodic();
        theTaskSpecObject.setDiscountFactor(1.0d);
        theTaskSpecObject.addContinuousObservation(new DoubleRange(0, 1, 6));
        theTaskSpecObject.addDiscreteAction(new IntRange(0, 5));
        theTaskSpecObject.setRewardRange(new DoubleRange(-1.0, 1.0));
        theTaskSpecObject.setExtra("EnvName:Polyathlon");

        String taskSpecString = theTaskSpecObject.toTaskSpec();
        TaskSpec.checkTaskSpec(taskSpecString);

        return taskSpecString;
    }

    public String env_init() {
        currentEnv.env_init();
        return makeTaskSpec();
    }

    public Observation env_start() {
        return currentEnv.env_start();
    }

    public Reward_observation_terminal env_step(Action theAction) {
        return currentEnv.env_step(theAction);
    }

    //This method creates the object that can be used to easily set different problem parameters
    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        rlVizLib.utilities.UtilityShop.setVersionDetails(p, new DetailsProvider());

        p.addIntegerParam("whichDomain {0,1,2,3,4,5}", 0);
        p.setAlias("whichDomain", "whichDomain {0,1,2,3,4,5}");
        return p;
    }

    public TrainingPolyathlon(ParameterHolder p) {
        super();

        if (p != null) {
            if (!p.isNull()) {
                //Load domain here
                int whichDomain = p.getIntegerParam("whichDomain");
                assert (whichDomain >= 0);
                assert (whichDomain < 6);
                switch (whichDomain) {

                    case 0:
                        currentEnv = new TrainingMDP0();
                        break;
                    case 1:
                        currentEnv = new TrainingMDP1();
                        break;
                    case 2:
                        currentEnv = new TrainingMDP2();
                        break;
                    case 3:
                        currentEnv = new TrainingMDP3();
                        break;
                    case 4:
                        currentEnv = new TrainingMDP4();
                        break;
                    case 5:
                        currentEnv = new TrainingMDP5();
                        break;
                }
            }
        }
        if (currentEnv == null) {
            System.err.println("A valid environment wasn't chosen or there was a problem with the parameters specified");
            System.exit(1);
        }
    }

    public String env_message(String theMessage) {
        EnvironmentMessages theMessageObject;
        try {
            theMessageObject = EnvironmentMessageParser.parseMessage(theMessage);
        } catch (NotAnRLVizMessageException e) {
            System.err.println("Someone sent " + this.getClass() + " a message that wasn't RL-Viz compatible");
            return "I only respond to RL-Viz messages!";
        }

        if (theMessageObject.canHandleAutomatically(this)) {
            String theResponseString = theMessageObject.handleAutomatically(this);
            return theResponseString;
        }

        //If we couldn't handle it pass it off to the environment
        return currentEnv.env_message(theMessage);
    }

    public TrainingPolyathlon() {
        this(getDefaultParameters());
    }

    public void env_cleanup() {
        currentEnv.env_cleanup();
    }

    public String getVisualizerClassName() {
        if (currentEnv instanceof HasAVisualizerInterface) {
            return ((HasAVisualizerInterface) currentEnv).getVisualizerClassName();
        }
        return null;
    }

    public URL getImageURL() {
        URL imageURL = TrainingPolyathlon.class.getResource("/images/poly.png");
        return imageURL;
    }
}

class DetailsProvider implements hasVersionDetails {

    public String getName() {
        return "Training Polyathlon 2009";
    }

    public String getShortName() {
        return "Training-Poly-09";
    }

    public String getAuthors() {
        return "Brian Tanner and Adam White";
    }

    public String getInfoUrl() {
        return "http://2009.rl-competition.org";
    }

    public String getDescription() {
        return "Five training MDPs for the Polyathlon.";
    }
}


