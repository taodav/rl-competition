/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TaskSpecTester;

import TaskSpecTester.messages.TaskSpecTesterRangeResponse;
import TaskSpecTester.messages.TaskSpecTesterStateResponse;
import java.util.Random;
import java.util.Vector;
import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.general.RLVizVersion;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.HasAVisualizerInterface;
import rlVizLib.messaging.interfaces.RLVizEnvInterface;
import rlVizLib.utilities.UtilityShop;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Random_seed_key;
import rlglue.types.Reward_observation;
import rlglue.types.State_key;

/**
 *
 * @author mark
 */
public class TaskSpecTester extends EnvironmentBase implements RLVizEnvInterface {
    private char[] state_types = {'f','i','f','f','i','f','i'};
    private double[] state_mins = {-5.0, 10.0,  3.0, -20.0, -10.0, -1.0, 100.0};
    private double[] state_maxs = {-3.0, 100.0, 7.0,   0.0,  -8.0,  3.0, 110.0};
    private char[] action_types = {'i','f','f','i'};
    private double[] action_mins = {-5.0, -1.0, -20.0, -30.0};
    private double[] action_maxs = { 5.0,  1.0,   0.0, -28.0};
    private Observation o;
    private Reward_observation ro;
    private Random random = new Random();
    
    public TaskSpecTester(){	
	this(getDefaultParameters());
    }
        

	
    public TaskSpecTester(ParameterHolder p){}
    
    public String env_init() {
	/*initializing the map struct and an observation object*/
	String Task_spec="";

	o = new Observation(0,12);

	

	/* Create and Return task specification */
	Task_spec = String.format("2.0:e:" + 
             "7_[f,i,f,f,i,f,i]_" + 
             "[%f,%f]_[%f,%f]_[%f,%f]_[%f,%f]_[%f,%f]_[%f,%f]_[%f,%f]" + 
             ":4_[i,f,f,i]_[%f,%f]_[%f,%f]_[%f,%f]_[%f,%f]:[]", 
                    state_mins[0], state_maxs[0],
                    state_mins[1], state_maxs[1], 
                    state_mins[2], state_maxs[2], 
                    state_mins[3], state_maxs[3], 
                    state_mins[4], state_maxs[4], 
                    state_mins[5], state_maxs[5], 
                    state_mins[6], state_maxs[6],
                    action_mins[0],action_maxs[0],
                    action_mins[1],action_maxs[1],
                    action_mins[2],action_maxs[2],
                    action_mins[3],action_maxs[3]); 
        return Task_spec;
    }
    
    public Observation env_start() {
        return makeObservation();
    }
    
    public Reward_observation env_step(Action action) {
        checkAction(action);
        ro=new Reward_observation(0.0, makeObservation(), 0);
        return ro;
    }
    
    public void env_cleanup() {
        
    }
    
    public Random_seed_key env_get_random_seed() {
        return new Random_seed_key(0,0);
    }
    
    public void env_set_random_seed(Random_seed_key k) {
      
    }
    
    public State_key env_get_state() {
        return new State_key(3,4);
    }
    
    public void env_set_state(State_key k) {
        
    }
    
    public String env_message(String theMessage) {
        return "";
    }
    
    public static ParameterHolder getDefaultParameters(){
        return new ParameterHolder();
    }
    
    public RLVizVersion getTheVersionISupport() {
        return new RLVizVersion(1,0);
    }
    
    public double getMaxValueForQuerableVariable(int dimension) {
        switch (dimension){
        case 0: return state_maxs[0];
        case 1: return state_maxs[1];
	case 2: return state_maxs[2];
	case 3: return state_maxs[3];
	case 4: return state_maxs[4];
	case 5: return state_maxs[5];
	case 6: return state_maxs[6];
	default: System.out.println("Invalid Dimension in getMaxValueForQuerableVariable for TaskSpecTester");return Double.POSITIVE_INFINITY;
	}
    }
    
    public double getMinValueForQuerableVariable(int dimension) {
        switch (dimension){
        case 0: return state_mins[0];
        case 1: return state_mins[1];
	case 2: return state_mins[2];
	case 3: return state_mins[3];
	case 4: return state_mins[4];
	case 5: return state_mins[5];
	case 6: return state_mins[6];
	default: System.out.println("Invalid Dimension in getMinValueForQuerableVariable for TaskSpecTester");return Double.NEGATIVE_INFINITY;
	}
    }
    
    @Override
    protected Observation makeObservation() {
        int i_index = 0;
        int d_index = 0;
        Observation theObs = new Observation(3,4);
        for (int i = 0; i < 7; i++) {
            if (state_types[i] == 'i') {
                theObs.intArray[i_index++] = random.nextInt((int)(state_maxs[i]-state_mins[i])) + (int)state_mins[i];
            }
            else {
                theObs.doubleArray[d_index++] = random.nextDouble()*(state_maxs[i]-state_mins[i]) + state_mins[i];
            }
        }
        return theObs;
    }

    private void checkAction(Action action) {
        int i_index = 0;
        int d_index = 0;
        for (int i = 0; i < 4; i++) {
            double val;
            if (action_types[i] == 'i') {
                val = (double)action.intArray[i_index++];
            }
            else {
                val = action.doubleArray[d_index++];
            }
            if (action_mins[i] > val || val > action_maxs[i]) {
                System.err.println("Invalid action: action[" + i + "] = " + val);
                System.exit(1);
            }
        }
    }
}
