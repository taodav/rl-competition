package org.rlcommunity.environment.acrobot;

import java.util.Random;
import java.util.Arrays;
import java.util.Vector;
import java.net.URL;
import java.util.Vector;
import org.rlcommunity.environment.acrobot.messages.AcrobotStateResponse;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;
import org.rlcommunity.rlglue.codec.types.Reward_observation_terminal;
import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.HasAVisualizerInterface;
import org.rlcommunity.environment.acrobot.visualizer.AcrobotVisualizer;
import org.rlcommunity.rlglue.codec.taskspec.TaskSpec;
import org.rlcommunity.rlglue.codec.taskspec.TaskSpecVRLGLUE3;
import org.rlcommunity.rlglue.codec.taskspec.ranges.DoubleRange;
import org.rlcommunity.rlglue.codec.taskspec.ranges.IntRange;
import rlVizLib.general.hasVersionDetails;
import rlVizLib.messaging.interfaces.HasImageInterface;
import rlVizLib.messaging.interfaces.ProvidesEpisodeSummariesInterface;

public abstract class Acrobot extends EnvironmentBase implements HasAVisualizerInterface,HasImageInterface, ProvidesEpisodeSummariesInterface {
    /*STATIC CONSTANTS*/

    final static int stateSize = 4;        
    final static double maxTheta1 = Math.PI;
    final static double maxTheta2 = Math.PI;
    final static double maxTheta1Dot = 4 * Math.PI;
    final static double maxTheta2Dot = 9 * Math.PI;
    final static double m1 = 1.0;
    final static double m2 = 1.0;
    final static double l1 = 1.0;
    final static double l2 = 1.0;
    final static double lc1 = 0.5;
    final static double lc2 = 0.5;
    final static double I1 = 1.0;
    final static double I2 = 1.0;
    final static double g = 9.8; 
    	
    /* logger */
    protected rlVizLib.utilities.logging.EpisodeLogger theEpisodeLogger;
    
    /* parameterized */
    public double  dt              = 0.05;
    public double  GoalPos         = 1.0;
    public boolean setRandomStarts = false;     //if true then do random starts, else, start at static position
    public double  t1dotscale      = 1.0; 
	public double  t2dotscale      = 1.0;
	public double  t1offset        = 0;			
	public double  t2offset	       = 0;	
	public double  t1dotoffset     = 0;			
	public double  t2dotoffset     = 0;	
	public boolean IsCompetition   = false;		
    
    /* Variable State Actions */
    private double actions[] = {-1,0,1,0,0,0,0,0}; // should be a power of two (i think)    
    private int numActions   = actions.length;    // number of actions
    
    
    /*State Variables*/
    private double theta1,  theta2,  theta1Dot,  theta2Dot;
    private int maxSteps = 1000000;
    private int currentNumSteps;
    private int seed = 0;
    private Random ourRandomNumber = new Random(seed);
    
    private int numEpisodes   = 0;
    private int numSteps      = 0;
    private int totalNumSteps = 0;

    /*Constructor Business*/
    public Acrobot() {
        this(getDefaultParameters());
    }
    

    public Acrobot(ParameterHolder p) {
        super();
        if (p != null) {
            if (!p.isNull()) {
            	IsCompetition       = p.getBooleanParam("useCompetition");
                setRandomStarts     = p.getBooleanParam("rstarts");
                GoalPos             = p.getDoubleParam("GoalPos");
                dt                  = p.getDoubleParam("dt");                
                
				t1dotscale          = p.getDoubleParam("t1dotscale"); 
		        t2dotscale			= p.getDoubleParam("t2dotscale"); 
		        
		        t1offset			= p.getDoubleParam("t1offset"); 
		        t2offset			= p.getDoubleParam("t2offset"); 
		        t1dotoffset			= p.getDoubleParam("t1dotoffset"); 
		        t2dotoffset			= p.getDoubleParam("t2dotoffset"); 
		       
                
                
                
            }
        }
    }
    
   public void setCompetition(boolean b) {
   	IsCompetition = b;   	
   }
   
   public void setRandomStarts(boolean b) {
   	setRandomStarts = b;
   }
      
   public void setCompetitionParams(double param[]){
   	
   	            GoalPos             = param[0];
                dt                  = param[1];
				t1dotscale          = param[2]; 
		        t2dotscale			= param[3]; 		        
		        t1offset			= param[4]; 
		        t2offset			= param[5]; 
		        t1dotoffset			= param[6]; 
		        t2dotoffset			= param[7]; 
    }
  
    
    //This method creates the object that can be used to easily set different problem parameters
    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        rlVizLib.utilities.UtilityShop.setVersionDetails(p, new DetailsProvider());
        
        
        p.addBooleanParam("useCompetition", false);
        p.addBooleanParam("Random Starts", false);
        p.setAlias("rstarts", "Random Starts");
        
       
        //Remember to force everything to be in bounds in makeObservation
        p.addDoubleParam("GoalPos", 1.0d); //should be  1.0 <= x < 2.0
		p.addDoubleParam("dt", 0.05d); //should be between 0.01 and 0.09		
		
		/* scale for velocities */
		p.addDoubleParam("t1dotscale", 1.0d); //should be between 0 and 1.0, probably not too close to 0
        p.addDoubleParam("t2dotscale", 1.0d); //should be between 0 and 1.0, probably not too close to 0
        /* scaling thetas does not make sense since they are angles (i think) */
		
		
		/* offset for all */
        p.addDoubleParam("t1offset", 0.0d); //should be between -PI and PI, remember to adjust task spec
        p.addDoubleParam("t2offset", 0.0d); //should be between -PI and PI, remember to adjust task spec
        p.addDoubleParam("t1dotoffset", 0.0d); //should be between -1.0 and 1.0, remember to adjust task spec
        p.addDoubleParam("t2dotoffset", 0.0d); //should be between -1.0 and 1.0, remember to adjust task spec
        
        
       
        
       
        return p;
    }
    
    private void ArrageActions(){    	
    	
    	/* This function changes the meaning of the actions depending on the current state variable theta2 
    	 * this should complicate the learning a little bit
    	 */
    	
    	if (!IsCompetition) {
    		numActions=3;
    		return;
    	}
    	 
    	int i =(int) ((theta2 * Math.PI) % (numActions/2.0));
    	Arrays.fill(actions,0);
    		
    	if (Math.signum(i)>=0){    	
	    	actions[i]=-1;
	    	actions[numActions-1-i] = 1;
    	}
    	else{
    		actions[numActions-1+i] = -1;
    		actions[-i]=1;	    	
    	}
    	
    	//System.out.println(Arrays.toString(actions)+" of part "+ i);
    	
    }
    

    /*Beginning of RL-GLUE methods*/
    public String env_init() {
    	
    	theEpisodeLogger = new rlVizLib.utilities.logging.EpisodeLogger();
    	ourRandomNumber  = new Random(seed);
    	ArrageActions();
    		
        numEpisodes = 0;
        numSteps = 0;
        totalNumSteps = 0;
        TaskSpecVRLGLUE3 theTaskSpecObject = new TaskSpecVRLGLUE3();
        theTaskSpecObject.setEpisodic();
        theTaskSpecObject.setDiscountFactor(1.0d);
        theTaskSpecObject.addContinuousObservation(new DoubleRange(-maxTheta1, maxTheta1));
        theTaskSpecObject.addContinuousObservation(new DoubleRange(-maxTheta2, maxTheta2));
        theTaskSpecObject.addContinuousObservation(new DoubleRange(-maxTheta1Dot, maxTheta1Dot));
        theTaskSpecObject.addContinuousObservation(new DoubleRange(-maxTheta2Dot, maxTheta2Dot));

        theTaskSpecObject.addDiscreteAction(new IntRange(0, numActions - 1));

        //Apparently we don't say the reward range.
        //theTaskSpecObject.setRewardRange(new IntRange(-1, 0));
        theTaskSpecObject.setRewardRange(new DoubleRange());
        theTaskSpecObject.setExtra("EnvName:Acrobot RLC2009");
        String taskSpecString = theTaskSpecObject.toTaskSpec();
        TaskSpec.checkTaskSpec(taskSpecString);
        return taskSpecString;
//                
//        String taskSpec = "1:e:4_[f,f,f,f]_";
//		taskSpec = taskSpec.concat("[" + -maxTheta1 + "," + maxTheta1 + "]_");
//		taskSpec = taskSpec.concat("[" + -maxTheta2 + "," + maxTheta2 + "]_");
//		taskSpec = taskSpec.concat("[" + -maxTheta1Dot + "," + maxTheta1Dot + "]_");
//		taskSpec = taskSpec.concat("[" + -maxTheta2Dot + "," + maxTheta2Dot + "]");
//		taskSpec = taskSpec.concat(":1_[i]_[0," + (numActions-1)+"]:[]");
//		return taskSpec;
    }

    public Observation env_start() {
        numEpisodes++;
        totalNumSteps++;
        numSteps = 0;
        if (setRandomStarts) {
            set_initial_position_random();
        } else {
            set_initial_position_at_bottom();
        }
        currentNumSteps = 0;
       
        return makeObservation();
    }

    public Reward_observation_terminal env_step(Action a) {
        int cond=0;
        totalNumSteps++;
        numSteps++;
        if ((a.intArray[0] < 0) || (a.intArray[0] > numActions-1)) {
            System.out.printf("Invalid action %d, selecting an action randomly\n", a.intArray[0]);
            a.intArray[0] = (int) ourRandomNumber.nextDouble() * 3;
        }
        
        ArrageActions();
        double torque = actions[a.intArray[0]];
        double d1;
        double d2;
        double phi_2;
        double phi_1;

        double theta2_ddot ;
        double theta1_ddot;

        int count = 0;
        while (test_termination()==0 && count < 4) {
            count++;

            d1 = m1 * Math.pow(lc1, 2) + m2 * ( Math.pow(l1, 2) + Math.pow(lc2, 2) + 2 * l1 * lc2 * Math.cos(theta2)) + I1 + I2;
            d2 = m2 * (Math.pow(lc2, 2) + l1 * lc2 * Math.cos(theta2)) + I2;
            
            phi_2 = m2 * lc2 * g * Math.cos(theta1 + theta2 - Math.PI / 2.0);
            phi_1 = -(m2 * l1 * lc2 * Math.pow(theta2Dot, 2) * Math.sin(theta2) -2 * m2 * l1 * lc2 * theta1Dot * theta2Dot * Math.sin(theta2)) + (m1 * lc1 + m2 * l1) * g * Math.cos(theta1 - Math.PI / 2.0) + phi_2;

            theta2_ddot = (torque + (d2 / d1) * phi_1 - m2*l1*lc2*Math.pow(theta1Dot,2)*Math.sin(theta2)- phi_2) / (m2 * Math.pow(lc2, 2) + I2 - Math.pow(d2, 2) / d1);
            theta1_ddot = - (d2 * theta2_ddot + phi_1) / d1;
        
            theta1Dot += theta1_ddot * dt;          
            theta2Dot += theta2_ddot * dt;            
            
            theta1 += theta1Dot * dt;
            theta2 += theta2Dot * dt;
        }
        if (Math.abs(theta1Dot)>maxTheta1Dot){ theta1Dot = Math.signum(theta1Dot)*maxTheta1Dot;}                     	            
        // the same but more ugly
        //theta1Dot = Math.min(theta1Dot,maxTheta1Dot);
        //theta1Dot = Math.max(theta1Dot,-maxTheta1Dot) ;           	            
            
        if (Math.abs(theta2Dot)>maxTheta2Dot){ theta2Dot = Math.signum(theta2Dot)*maxTheta2Dot;} 
        // the same but more ugly
        //theta2Dot = Math.min(theta2Dot,maxTheta2Dot);
        //theta2Dot = Math.max(theta2Dot,-maxTheta2Dot);
        
        
        //Keep thetas in range, it does not make sense to allow they to grown since these are just angles
        //while (Math.abs(theta1)>Math.PI){ theta1 -= Math.signum(theta1) * 2.0 * Math.PI;}
        //while (Math.abs(theta2)>Math.PI){ theta2 -= Math.signum(theta2) * 2.0 * Math.PI;}        
        
        /* Put a hard constraint on the Acrobot physics, thetas MUST be in [-PI,+PI]
         * if they reach a top then angular velocity becomes zero
         */
        if (Math.abs(theta2)>Math.PI){ theta2 = Math.signum(theta2)*Math.PI; theta2Dot=0; }                     	
        if (Math.abs(theta1)>Math.PI){ theta1 = Math.signum(theta1)*Math.PI; theta1Dot=0;}                     	
        
        
        currentNumSteps++;

        Reward_observation_terminal ro = new Reward_observation_terminal();
        ro.r = -1;
        
        ro.o = makeObservation();

        ro.terminal = 0;
        cond        = test_termination();
        if (cond>0 ) {
            ro.terminal = 1;
            if (cond==1){ro.r = 0;}
        }
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

        if (theMessageObject.canHandleAutomatically(this)) {
            return theMessageObject.handleAutomatically(this);
        }


        if (theMessageObject.getTheMessageType() == rlVizLib.messaging.environment.EnvMessageType.kEnvCustom.id()) {



            String theCustomType = theMessageObject.getPayLoad();
            /**
             * Added by Brian Tanner Feb 1 / 2009 so that visualizer doesn;t
             * draw the noise
             */
            if (theCustomType.equals("GETSTATE")) {
                //It is a request for the state
                AcrobotStateResponse theResponseObject = new AcrobotStateResponse(theta1,theta2);
                return theResponseObject.makeStringResponse();
            }

            System.out.println("We need some code written in Env Message for Acrobot.. unknown custom message type received" + theMessage);
            //Thread.dumpStack();

            return null;
        }

        System.out.println("We need some code written in Env Message for  Acrobot!");
        //Thread.dumpStack();

        return null;
    }

    /*End of RL-Glue Methods*/
    
    /*Beginning of Logging Methods*/
    public Vector<String> getEpisodeSummary(){
  		return null;
    }
    public String getEpisodeSummary(long charToStartOn, int charsToSend) {
       return theEpisodeLogger.getLogSubString(charToStartOn, charsToSend);
    }
    /* End of Logging Methods*/
    /*Beginning of RL-VIZ Methods*/
    @Override
    protected Observation makeObservation() {
    	double t1,t2;
        Observation obs = new Observation(0, 4);
        t1 = theta1 + t1offset;
        t2 = theta2 + t2offset;
        
        //Keep thetas in range, it does not make sense to allow they to grown since these are just angles
        if (Math.abs(t1)>Math.PI){ t1 -= Math.signum(t1) * 2.0 * Math.PI; }
        if (Math.abs(t2)>Math.PI){ t2 -= Math.signum(t2) * 2.0 * Math.PI; }
        
        obs.doubleArray[0] = t1;
        obs.doubleArray[1] = t2;
        
        // A carefull check is needed in order to enforce range validation
        obs.doubleArray[2] = t1dotscale * theta1Dot + t1dotoffset;
        obs.doubleArray[3] = t2dotscale * theta2Dot + t2dotoffset;

        return obs;
    }
    /*End of RL-VIZ Methods*/

    private void set_initial_position_random() {
        theta1 = (ourRandomNumber.nextDouble() * 2*Math.PI + (-Math.PI)) * 0.25;
        theta2 = (ourRandomNumber.nextDouble() * 2*Math.PI + (-Math.PI)) * 0.25;
        theta1Dot = (ourRandomNumber.nextDouble() * (maxTheta1Dot * 2) - maxTheta1Dot) * 0.1;
        theta2Dot = (ourRandomNumber.nextDouble() * (maxTheta2Dot * 2) - maxTheta2Dot) * 0.1;
    }

    private void set_initial_position_at_bottom() {
        theta1 = theta2 = 0.0;
        //theta1 = Math.PI;
        theta1Dot = theta2Dot = 0.0;
    }

    private int test_termination() {
//Brian: Nov 2008 I don't believe that this is true, but I'd like it to be true.
//I'm going to try to recalculate this from code that I believe is working in the 
//visualizer            
        double feet_height = -l1 * Math.cos(theta1) - l2 * Math.cos(theta2);
        // ok good for you Brian, don't believe such things indeed when you like that!.

        //New Code
        //double firstJointEndHeight = l1 * Math.cos(theta1);
        //Second Joint height (relative to first joint)
        //double secondJointEndHeight = l2 * Math.sin(Math.PI / 2 - theta1 - theta2);
        //double feet_height = -(firstJointEndHeight + secondJointEndHeight);
        //System.out.println("position:  "+ feet_pos);
        
        if (feet_height >= GoalPos )   {return 1;}
        if (currentNumSteps > maxSteps){return 2;}
        return 0; 
        
    }
    /*
    public String getVisualizerClassName() {
        return AcrobotVisualizer.class.getName();
    }
    */
    
     public String getVisualizerClassName() {
        return "org.rlcommunity.environment.acrobot.visualizer.AcrobotVisualizer";
    }

    public URL getImageURL() {
        URL imageURL = Acrobot.class.getResource("/images/acrobot.png");
        return imageURL;
    }


    
}
class DetailsProvider implements hasVersionDetails {

    public String getName() {
        return "Acrobot 1.1";
    }

    public String getShortName() {
        return "Acrobot";
    }

    public String getAuthors() {
        return "Jose Antonio Martin H. from Brian Tanner from Adam White from Richard S. Sutton?";
    }

    public String getInfoUrl() {
        return "http://library.rl-community.org/acrobot";
    }

    public String getDescription() {
        return "Acrobot problem from the reinforcement learning library.";
    }
}

