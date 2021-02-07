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
package SamplePolyathlon;

import rlVizLib.general.ParameterHolder;

import rlVizLib.general.hasVersionDetails;
import rlglue.environment.Environment;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Random_seed_key;
import rlglue.types.Reward_observation;
import rlglue.types.State_key;

public class SamplePolyathlon implements Environment {

    private Environment currentEnv = null;

    public static String genericTaskSpec(){
        String taskSpec = "2:e:4_[f,f,f,f]";
        for (int i = 0; i < 4; i++) {
            taskSpec += "_[" + 0 + "," + 1 + "]";
        }

        taskSpec += ":1_[i]_[0,3]:[-100,100]";
        return taskSpec;
 
    }
    public String env_init() {
        return currentEnv.env_init();
    }

    public Observation env_start() {
        return currentEnv.env_start();
    }

    public Reward_observation env_step(Action theAction) {
        return currentEnv.env_step(theAction);
    }

    //This method creates the object that can be used to easily set different problem parameters

    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        rlVizLib.utilities.UtilityShop.setVersionDetails(p, new DetailsProvider());

        p.addIntegerParam("whichDomain {0,1,2}", 0);
        p.setAlias("whichDomain","whichDomain {0,1,2}");
        return p;
    }

    public SamplePolyathlon(ParameterHolder p) {
        super();

        if (p != null) {
            if (!p.isNull()) {
            //Load domain here
                int whichDomain=p.getIntegerParam("whichDomain");
                currentEnv=GenPolyParamData.makeMDP(whichDomain);
            }
        }
        if (currentEnv == null) {
            System.err.println("A valid environment wasn't chosen or there was a problem with the parameters specified");
            System.exit(1);
        }
    }

    public String env_message(String theMessage) {
        return currentEnv.env_message(theMessage);
    }

    public SamplePolyathlon() {
        this(getDefaultParameters());
    }

    public void env_cleanup() {
        currentEnv.env_cleanup();
    }

    //
//This has a side effect, it changes the random order.
//

    public Random_seed_key env_get_random_seed() {
        return currentEnv.env_get_random_seed();
    }

    public void env_set_random_seed(Random_seed_key k) {
        currentEnv.env_set_random_seed(k);
    }

    public State_key env_get_state() {
        return currentEnv.env_get_state();
    }

    public void env_set_state(State_key k) {
        currentEnv.env_set_state(k);
    }
}    




class DetailsProvider implements hasVersionDetails{
    public String getName() {
        return "Sample Polyathlon 1.0";
    }

    public String getShortName() {
        return "Sample-Poly";
    }

    public String getAuthors() {
        return "Brian Tanner";
    }

    public String getInfoUrl() {
        return "http://rl-competition.org";
    }

    public String getDescription() {
        return "Sample version of the Polyathlon domain that will be available in the 2008 Reinforcement Learning Competition.";
    }
}


