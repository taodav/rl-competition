package Acrobot;

import java.util.Random;


import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.general.RLVizVersion;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.HasAVisualizerInterface;
import rlVizLib.messaging.interfaces.RLVizEnvInterface;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Random_seed_key;
import rlglue.types.Reward_observation;
import rlglue.types.State_key;

public class Acrobot extends EnvironmentBase implements RLVizEnvInterface, HasAVisualizerInterface{
	/*STATIC CONSTANTS*/
	final static int stateSize = 4;
	final static int numActions = 3;
	
	final static double maxTheta1 = Math.PI;
	final static double maxTheta2 = 2.0*Math.PI;
	final static double maxTheta1Dot = 4*Math.PI;
	final static double maxTheta2Dot = 9*Math.PI;

	final static double  m1 = 1.0;
	final static double  m2 = 1.0;
	final static double  l1 = 1.0;
	final static double  l2 = 1.0;
	final static double  lc1 = 0.5;
	final static double  lc2 = 0.5;
	final static double  I1 = 1.0;
	final static double  I2 = 1.0;
	final static double  g = 9.8;
	final static double  dt = 0.05;

	final static double acrobotGoalPosition = 1.0;
	
	/*State Variables*/
	private double theta1, theta2, theta1Dot, theta2Dot;

	private int maxSteps = 1000000;
	private int currentNumSteps;
	private int seed =0;
	private Random ourRandomNumber = new Random(seed);
	
	boolean setRandomStarts; //if true then do random starts, else, start at static position
	
	private int numEpisodes=0;
	private int numSteps =0;
	private int totalNumSteps =0;
	
	/*Constructor Business*/
	public Acrobot(){
		this(getDefaultParameters());
	}
	public Acrobot(ParameterHolder p){
		super();
		if(p!=null){
			if(!p.isNull()){
				setRandomStarts=p.getBooleanParam("rstarts");
			}
		}
	}
	
	//This method creates the object that can be used to easily set different problem parameters
	public static ParameterHolder getDefaultParameters(){
		ParameterHolder p = new ParameterHolder();
		p.addBooleanParam("Random Starts", false);
		p.setAlias("rstarts", "Random Starts");
		return p;
	}

	
	/*Beginning of RL-GLUE methods*/
	public String env_init() {
		
		numEpisodes=0;
		numSteps =0;
		totalNumSteps =0;
		String taskSpec = "1:e:4_[f,f,f,f]_";
		taskSpec = taskSpec.concat("[" + -maxTheta1 + "," + maxTheta1 + "]_");
		taskSpec = taskSpec.concat("[" + -maxTheta2 + "," + maxTheta2 + "]_");
		taskSpec = taskSpec.concat("[" + -maxTheta1Dot + "," + maxTheta1Dot + "]_");
		taskSpec = taskSpec.concat("[" + -maxTheta2Dot + "," + maxTheta2Dot + "]");
		taskSpec = taskSpec.concat(":1_[i]_[0," + (numActions-1)+"]:[]");
		return taskSpec;
	}
	public Observation env_start() {
		numEpisodes++;
		totalNumSteps++;
		numSteps =0;
		if(setRandomStarts)
			set_initial_position_random();
		else
			set_initial_position_at_bottom();
		
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

		int count=0;
		while(!test_termination() && count < 4)
		{
			count++;

			double d1 = m1*Math.pow(lc1,2) + m2*(Math.pow(l1,2) + Math.pow(lc2,2) + 2*l1*lc2*Math.cos(theta2)) + I1 + I2;
			double d2 = m2*(Math.pow(lc2, 2) + l1*lc2*Math.cos(theta2)) + I2;
			double phi_2 = m2*lc2*g*Math.cos(theta1 + theta2 - Math.PI/2);
			double phi_1 = -(m2*l1*lc2*Math.pow(theta2, 2)*Math.sin(theta2) + 2*m2*l1*lc2*theta1Dot*theta2Dot*Math.sin(theta2)) + (m1*lc1 + m2*l1)*g*Math.cos(theta1 - Math.PI/2) + phi_2;

			double theta2_ddot = (torque + (d2/d1)*phi_1 - phi_2)/(m2*Math.pow(lc2, 2) + I2 - Math.pow(d2, 2)/d1);
			double theta1_ddot = -(d2*theta2_ddot + phi_1)/d1;

			theta1Dot += theta1_ddot*dt;

			if(theta1Dot > maxTheta1Dot) theta1Dot = maxTheta1Dot;
			else if(theta1Dot < -(maxTheta1Dot)) theta1Dot = -(maxTheta1Dot);


			theta2Dot += theta2_ddot*dt;

			if(theta2Dot > maxTheta2Dot) theta2Dot = maxTheta2Dot;
			else if(theta2Dot < -(maxTheta2Dot)) theta2Dot = -(maxTheta2Dot);

			theta1 += theta1Dot*dt;
			theta2 += theta2Dot*dt;
		}

	//Keep thetas in reasonable range to avoid loss of percision.
		while(theta1 > 2.0*Math.PI) theta1 -= 2.0*Math.PI;
		while(theta1 < -2.0*Math.PI) theta1 += 2.0*Math.PI;
		while(theta2 > 2.0*Math.PI) theta2 -= 2.0*Math.PI;
		while(theta2 < -2.0*Math.PI) theta2 += 2.0*Math.PI;
	  
	    currentNumSteps++;
		
	    Reward_observation ro = new Reward_observation();
		ro.r = -1;
		ro.o = makeObservation();

		ro.terminal = 0;
		if(test_termination())
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
		obs.doubleArray[0] = theta1;
		obs.doubleArray[1] = theta2;
		obs.doubleArray[2] = theta1Dot;
		obs.doubleArray[3] = theta2Dot;
		return obs;
	}

	public RLVizVersion getTheVersionISupport() {
		return new RLVizVersion(1,0);
	}
	/*End of RL-VIZ Methods*/
	

	private void set_initial_position_random() {
		theta1 = (ourRandomNumber.nextDouble()*(Math.PI + Math.abs(-Math.PI)) + (-Math.PI))*0.1;
		theta2 = (ourRandomNumber.nextDouble()*(Math.PI + Math.abs(-Math.PI)) + (-Math.PI))*0.1;
	    theta1Dot = (ourRandomNumber.nextDouble()*(maxTheta1Dot*2) - maxTheta1Dot)*0.1;
	    theta2Dot = (ourRandomNumber.nextDouble()*(maxTheta2Dot*2) - maxTheta2Dot)*0.1;
	}
	private void set_initial_position_at_bottom() {
		theta1 = theta2 = 0.0;
		theta1Dot = theta2Dot = 0.0;
	}
	
	private boolean test_termination() {
		double feet_height = -(l1*Math.cos(theta1) + l2*Math.cos(theta2));
		return (feet_height > acrobotGoalPosition) || (currentNumSteps > maxSteps);
	}

    public String getVisualizerClassName() {
        return "visualizers.AcrobotVisualizer.AcrobotVisualizer";
    }

}
