package SamplePolyathlon.ProblemTypeC.MainPackage;

import SamplePolyathlon.ProblemTypeB.Messages.CGWPositionResponse;
import SamplePolyathlon.ProblemTypeC.Messages.ProblemTypeCStateResponse;
import java.util.Random;


import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.HasAVisualizerInterface;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Random_seed_key;
import rlglue.types.Reward_observation;
import rlglue.types.State_key;


public abstract class AbstractPolyProblemTypeC extends EnvironmentBase implements HasAVisualizerInterface{
	/*STATIC CONSTANTS*/
	final static int stateSize = 4;
	final static int numActions = 3;
	

	
        protected PolyProblemTypeCState theState=new PolyProblemTypeCState();
	private int maxSteps = 1000000;
	private int currentNumSteps;
	private int seed =0;
	private Random ourRandomNumber = new Random(seed);
	
	boolean setRandomStarts; //if true then do random starts, else, start at static position
	
	private int numEpisodes=0;
	private int numSteps =0;
	private int totalNumSteps =0;
	
	/*Constructor Business*/
	public AbstractPolyProblemTypeC(){
		this(getDefaultParameters());
	}
	public AbstractPolyProblemTypeC(ParameterHolder p){
		super();
	}
	
	//This method creates the object that can be used to easily set different problem parameters
	public static ParameterHolder getDefaultParameters(){
		ParameterHolder p = new ParameterHolder();
		return p;
	}

	
	/*Beginning of RL-GLUE methods*/
	public String env_init() {
		
		numEpisodes=0;
		numSteps =0;
		totalNumSteps =0;
		String taskSpec = "1:e:4_[f,f,f,f]_";
		taskSpec = taskSpec.concat("[" + -theState.maxTheta1 + "," + theState.maxTheta1 + "]_");
		taskSpec = taskSpec.concat("[" + -theState.maxTheta2 + "," + theState.maxTheta2 + "]_");
		taskSpec = taskSpec.concat("[" + -theState.maxTheta1Dot + "," + theState.maxTheta1Dot + "]_");
		taskSpec = taskSpec.concat("[" + -theState.maxTheta2Dot + "," + theState.maxTheta2Dot + "]");
		taskSpec = taskSpec.concat(":1_[i]_[0," + (numActions-1)+"]:[]");
		return taskSpec;
	}
	public Observation env_start() {
		numEpisodes++;
		totalNumSteps++;
		numSteps =0;
		if(setRandomStarts)
			theState.set_initial_position_random(ourRandomNumber);
		else
			theState.set_initial_position_at_bottom();
		
	    currentNumSteps = 0;
	    
		return makeObservation();
	}

	public Reward_observation env_step(Action a) {
		totalNumSteps++;
		numSteps++;
		if((a.intArray[0]<0) || (a.intArray[0]>2))
		{
			System.out.printf("Invalid action %d, selecting an action randomly\n",a.intArray[0]);
			a.intArray[0] = (int)ourRandomNumber.nextDouble()*3;
		}
		double torque = a.intArray[0] - 1.0;

                theState.applyTorque(torque);
	  
	    currentNumSteps++;
		
	    Reward_observation ro = new Reward_observation();
		ro.r = -1;
		ro.o = makeObservation();

		ro.terminal = 0;
		if(theState.inGoalRegion() || (currentNumSteps > maxSteps))
			ro.terminal =1;
	    return ro;
	}

	public void env_cleanup() {
	}
	
	public String env_message(String theMessage) {
		EnvironmentMessages theMessageObject;
		try {
			theMessageObject = EnvironmentMessageParser.parseMessage(theMessage);
		} catch (Exception e) {
			System.err.println("Someone sent Acrobot a message that wasn't RL-Viz compatible");
			 return "I only respond to RL-Viz messages!";
		}

		if(theMessageObject.canHandleAutomatically(this)){
			return theMessageObject.handleAutomatically(this);
		}

		if(theMessageObject.getTheMessageType()==rlVizLib.messaging.environment.EnvMessageType.kEnvCustom.id()){

			String theCustomType=theMessageObject.getPayLoad();
                        
                        if(theCustomType.equals("GETSTATE")){
				//It is a request for the state
				ProblemTypeCStateResponse theResponseObject=new ProblemTypeCStateResponse(theState.getTheta1(),theState.getTheta2());
                                return theResponseObject.makeStringResponse();
			}


		
                        System.out.println("We need some code written in Env Message for Acrobot.. unknown custom message type received"+theMessage);
			Thread.dumpStack();

			return null;
		}

		System.out.println("We need some code written in Env Message for  Acrobot!");
		Thread.dumpStack();

		return null;
	}
	
	public Random_seed_key env_get_random_seed() {
		Random_seed_key rsk = new Random_seed_key(1,0);
		rsk.intArray[0] = seed;
		return rsk;
	}

	public State_key env_get_state() {
		System.out.println("IN Acrobot.java we do not implement env_get_state");
		return null;
	}



	public void env_set_random_seed(Random_seed_key key) {
		seed = key.intArray[0];
		ourRandomNumber = new Random(seed);
	}

	public void env_set_state(State_key key) {
		System.out.println("IN Acrobot.java we do not implement env_set_state");
		
	}
	/*End of RL-Glue Methods*/
	
	/*Beginning of RL-VIZ Methods*/

	@Override
	protected Observation makeObservation() {
		Observation obs = new Observation(0,4);
		obs.doubleArray[0] = theState.getTheta1();
		obs.doubleArray[1] = theState.getTheta2();
		obs.doubleArray[2] = theState.getTheta1Dot();
		obs.doubleArray[3] = theState.getTheta2Dot();
		return obs;
	}

        /*End of RL-VIZ Methods*/
	


    public String getVisualizerClassName() {
        return "SamplePolyathlon.ProblemTypeC.Visualizer.AbstractPolyProblemTypeCVisualizer";
    }

}
