package GenericSarsaLambda;
import java.util.Set;
import java.util.TreeSet;

import rlVizLib.functionapproximation.TileCoder;
import rlVizLib.general.ParameterHolder;
import rlVizLib.general.RLVizVersion;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.agent.AgentMessageParser;
import rlVizLib.messaging.agent.AgentMessages;
import rlVizLib.messaging.interfaces.RLVizVersionResponseInterface;
import rlVizLib.utilities.TaskSpecObject;
import rlVizLib.visualization.QueryableAgent;
import rlglue.agent.Agent;
import rlglue.types.Action;
import rlglue.types.Observation;

/*
 *  Brian Tanner: August 15, 2007
 *  
 * Test
 *  This GenericSarsaLambda agent was original written by Rich Sutton (I think) a long time ago.  It was taken from Rich Sutton's website
 *  and modified by Adam White and put in the RL-Library as a C (C++?) agent.
 *  
 *  Brian Tanner has since ported this agent to Java, and added functionality to be generic via RL-Glue 2.0, to have automatically adjusting tile widths
 *  based on the ranges of the observations the agent experiences, and being dynamically loadable via RL-Viz.
 */

/* Generic Sarsa Lambda is a Java agent which will work with both
 * RL-Glue and Rl-Viz. It must implement Agent to be an RL-Glue Agent
 * and it must implement QueryableAgent to work with RL-Viz.
 */
/* Generic Sarsa Lambda is a Java agent which will work with both
 * RL-Glue and Rl-Viz. It must implement Agent to be an RL-Glue Agent
 * and it must implement QueryableAgent to work with RL-Viz.
 */
public class GenericSarsaLambda implements Agent, QueryableAgent, RLVizVersionResponseInterface {
//	static {
//		boolean hasAssertEnabled = false;
//		assert hasAssertEnabled = true; // rare - an intentional side effect!
//		if ( !hasAssertEnabled ) {
//			throw new RuntimeException("Asserts must be enabled for GenericSarsaLambda.");
//		}
//	}

	boolean inited=false;
	private int actionCount;
	

	/*trace code variables*/
	int memorySize;
	int numTilings;
	int maxNonZeroTraces;
	double numGrids; // number of tiles for each observation variable

	/*trace code requirements*/
	int nonZeroTraces[];
	int numNonZeroTraces;
	int nonZeroTracesInverse[];
	double minimumTrace;

	// Global RL variables:
	double tempQ[];	// action values

	boolean normalizeObservations=false;
	double observationDividers[];
//These are used if we are not doing normalizeObservations
	double defaultDividers[];

	double weights[];							// modifyable parameter vector, aka memory, weights
	double traces[];								// eligibility traces
	int tempFeatures[];								// sets of features, one set per action

	//I want to keep track of the mins and maxs' I've seen to do some smart tile coding. That sounds
	//pretty clever. I think I shall do it.
	double doubleMins[] = null;
	double doubleMaxs[]=null;
	boolean rangeDetermined = false;


	// Standard RL parameters:
	double epsilon;						// probability of random action
	double alpha;						// step size parameter
	double lambda;						// trace-decay parameters
	double gamma;

	int oldAction;						//action selected on previous time step
	int newAction;						//action selected on current time step

	/*Extra Sarsa Lambda Functions, not required by RL-Glue or RL-Viz*/
	double getAlpha(){return alpha;}
	double getEpsilon(){return epsilon;}
	int getNumTilings(){return numTilings;}
	int getMemorySize(){return memorySize;}
	double getLambda(){return lambda;}


	TileCoder theTileCoder=new TileCoder();
	Set<Integer> allVisitedTiles=new TreeSet<Integer>();



	public static ParameterHolder getDefaultParameters(){
		ParameterHolder p = new ParameterHolder();
		p.addDoubleParam("lambda",0.0d);
		p.addDoubleParam("epsilon",0.05d);
		p.addDoubleParam("alpha",0.25d);

		p.addIntegerParam("memorySize",1<<22);
		p.addIntegerParam("numTilings",8);
		p.addDoubleParam("numGrids",8.0d);

		p.addBooleanParam("normalizeObservations",true);
		p.addDoubleParam("width0",1.0d);
		p.addDoubleParam("width1",1.0d);


		return p;
	}

	public GenericSarsaLambda(){
		this(GenericSarsaLambda.getDefaultParameters());
	}

	public GenericSarsaLambda(ParameterHolder p){
		super();
		if(p!=null){
			if(!p.isNull()){
				this.lambda=p.getDoubleParam("lambda");
				this.alpha=p.getDoubleParam("alpha");
				this.epsilon=p.getDoubleParam("epsilon");
				this.lambda=p.getDoubleParam("lambda");
				this.numGrids=p.getDoubleParam("numGrids");

				this.memorySize=p.getIntegerParam("memorySize");
				this.numTilings=p.getIntegerParam("numTilings");
				
				//Sortof a quick hack for now
				defaultDividers=new double[2];
				defaultDividers[0]=p.getDoubleParam("width0");
				defaultDividers[1]=p.getDoubleParam("width1");
				normalizeObservations=p.getBooleanParam("normalizeObservations");

				this.maxNonZeroTraces=100000;
				observationDividers=null;
				this.tempFeatures=null;
				this.tempQ=null;
				/*Set up the variables which are consistent in all the constructors*/

				nonZeroTraces=new int[maxNonZeroTraces];
				numNonZeroTraces=0;
				nonZeroTracesInverse = new int[memorySize];
				minimumTrace = 0.01;
				weights = new double[memorySize];
				traces = new double[memorySize];
				gamma=1.0f;
				rangeDetermined = false;

				System.out.print("***********\nnew tile coding agent in ParamHolder Constructor:");
				System.out.print("lambda: "+lambda);
				System.out.print(" epsilon: "+epsilon);
				System.out.print(" alpha: "+alpha);
				System.out.print(" num_tilings: "+numTilings);
				System.out.print(" normalizeObservations: "+normalizeObservations);
				if(normalizeObservations){
					System.out.print(" numGrids after normalized: "+numGrids);
				}else{
					System.out.print(" width0: "+defaultDividers[0]);
					System.out.print(" width1: "+defaultDividers[1]);
				}
				System.out.println("\n**********");

			}
		}
	}


	/********* RL GLUE REQUIRED FUNCTIONS START HERE *******/
	/* if you are writing your own agent you need to at least instantiate and follow 
	 * all method protocols for all the RL-Glue Functions*/

	TaskSpecObject theTaskObject=null;
	public void agent_init(String taskSpec) {
		//parse the task spec object
		theTaskObject = new TaskSpecObject(taskSpec);

		//This agent will work within a normalized discrete action range, with the first action being 0, and the highest action being
		//maxAction - minAction 

		//We will write a bit of code that will return actions in the right range

//		In this case we've assumed one action which can take on
		//the integer values 0 to action_max. So the action count is
		// 1 + the action max. If the action values are -3 to -1 
		//there will be a problem in the current implementation
		actionCount=1+(int)theTaskObject.action_maxs[0]-(int)theTaskObject.action_mins[0];

		assert(actionCount>0);

		//if the min is less than 0 there is a problem
		assert(theTaskObject.action_mins[0]==0);

		//here we're asserting there IS only one discrete action variable. 
		assert(theTaskObject.num_discrete_action_dims==1); //check the number of discrete actions is only 1
		assert(theTaskObject.num_continuous_action_dims==0); //check that there is no continuous actions
		assert(theTaskObject.action_types[0]=='i'); // check that the action type has b een labelled properly..
		assert(theTaskObject.action_maxs[0]>=0); //Check that the max is greater or equal to 0. If it is equal to 0 there is only one action always.... learning is going to be super easy

		int doubleCount=theTaskObject.num_continuous_obs_dims; //get the number of continuous Observation variables

		/*Initialize the traces being used for the value function*/
		for (int i=0; i<memorySize; i++) {
			weights[i]= 0.0;                     // clear memory at start of each run
			traces[i] = 0.0;                            // clear all traces
			nonZeroTracesInverse[i]=0;
		}

		for(int i=0;i<maxNonZeroTraces;i++)
			nonZeroTraces[i]=0;

		observationDividers=new double[doubleCount];

		if(normalizeObservations)
			for(int i=0;i<doubleCount;i++) observationDividers[i]=Double.MAX_VALUE;
		else
			for(int i=0;i<doubleCount;i++) observationDividers[i]=defaultDividers[i];
			
		tempFeatures = new int[actionCount*numTilings];
		tempQ = new double[actionCount];
		this.doubleMins = new double[theTaskObject.num_continuous_obs_dims];
		this.doubleMaxs = new double[theTaskObject.num_continuous_obs_dims];

		inited=true;
	}


	public Action agent_start(Observation Observations) {
		/*Based on the initial observation, choose an action to take as your first step :)*/
		DecayTraces(0.0);  // clear all traces

		//We are tracking the mins and maxs we've seen. These functions will adjust what our 
		//recorded values are given the new observation
		adjustRecordedRange(Observations);

		// clear all traces
		loadF(tempFeatures,Observations); // compute features
		//Q for all actions
		loadQ(tempQ,tempFeatures); 

		/*Recall oldAction and newAction are being stored as one int because we have decided the 
		 * GenericSarsaLambda will only handle action spaces which consist of one integer. They then must be stuffed into 
		 * An Action Object to be passed out of agent_start
		 */
		if(Math.random() <= epsilon) 
			/*take a random action*/
			oldAction = (int) (Math.random() * (actionCount));
		else
			/*take the greedy action*/
			oldAction = argmax(tempQ);                                      // pick argmax action

		return makeActionObject(oldAction);
	}

	private Action makeActionObject(int theNormalizeAction){
		Action action = new Action(1, 0);/* The Action constructor takes two arguements: 1) the size of the int array 2) the size of the double array*/
		action.intArray[0] = theNormalizeAction-(int)theTaskObject.action_mins[0]; /*Set the action value*/
		return action;
	}


	public Action agent_step(double r, Observation theObservations) {	
              /*The agent needs to update it's learning function in here and decide on what action to take. */

		//We are tracking the mins and maxs we've seen. These functions will adjust what our 
		//recorded values are given the new observation
		adjustRecordedRange(theObservations);

		DecayTraces(gamma*lambda);  // let traces fall

		for (int a=0; a<actionCount; a++)  // optionally clear other traces
			if (a != oldAction)
				for (int j=0; j<numTilings; j++)
					ClearTrace(tempFeatures[a*numTilings+j]);

		for (int j=0; j<numTilings; j++)
			SetTrace(tempFeatures[oldAction*numTilings+j],1.0); // replace traces

		double partDelta = r - tempQ[oldAction];

		//Load Q values for all actions in current state
		loadF(tempFeatures,theObservations);                         // compute features
		loadQ(tempQ,tempFeatures);                                                 // compute new state values

		/*Recall oldAction and newAction are being stored as one int because we have decided the 
		 * GenericSarsaLambda will only handle action spaces which consist of one integer. They then must be stuffed into 
		 * An Action Object to be passed out of agent_start
		 */


		//Choose new action epsilon greedily
		if(Math.random() <= epsilon)
			newAction = (int) (Math.random() * (actionCount));
		else
			newAction = argmax(tempQ);                                      // pick argmax action

		double delta = partDelta + gamma * tempQ[newAction];

		double temp = (alpha/(double)(numTilings))*delta;


		//When do these traces get turned on
		for (int i=0; i<numNonZeroTraces; i++)                 // update theta (learn)
		{ 
			int f = nonZeroTraces[i];
			weights[f] += temp * traces[f];
		}       // update theta (learn)

		//Reload the Q value using the new updated weights.
		loadQ(tempQ,tempFeatures,newAction);

		oldAction = newAction;    

		return makeActionObject(newAction);
	}

	public void agent_end(double r) {
		/* Complete the final update to the value function after the agent enters
		 * a terminal state
		 */
		DecayTraces(gamma*lambda); // let traces fall

		for (int a=0; a<actionCount; a++) // optionally clear other traces
			if (a != oldAction)
				for (int j=0; j<numTilings; j++)
					ClearTrace(tempFeatures[a*numTilings+j]);

		for (int j=0; j<numTilings; j++) 
			SetTrace(tempFeatures[oldAction*numTilings+j],1.0); // replace traces

		double delta = r - tempQ[oldAction];

		double temp = (alpha/numTilings)*delta;

		for (int i=0; i<numNonZeroTraces; i++)  // update theta (learn)
		{ 
			int f = nonZeroTraces[i];
			weights[f] += temp * traces[f];
		}

	}

	public void agent_cleanup() {
		/* By setting inited to false, we specify that the agent
		 * will need to be reinitialized before use
		 */
		inited=false;

	}

	public void agent_freeze() {
		// This agent does not bother to fully implement agent_freeze
		System.out.println("You've called agent_freeze in Generic Sarsa Lambda, however we have not implemented this function fully");
	}


	public String agent_message(String theMessage) {
		/*As per the RL-Glue specs, you can implement anything you want in agent_message. If your agent
		 * needs to be able to change it's lambda value halfway through the experiment, you could write code here so that 
		 * when agent_message is called with a Message like Lambda:0.2 it changes Lambda to 0.2. HOWEVER For RL-Viz, 
		 * a certain number of messages must be handled. The messages handled below are done specifically to use
		 * RL-Viz
		 */
		AgentMessages theMessageObject;
		try {
			/*tries to parse the message to see if it is a RL-VIZ message. If you are handling your own set of messages
			 * you could put your own message handling in the catch block here. 
			 */
			theMessageObject = AgentMessageParser.parseMessage(theMessage);
		} catch (NotAnRLVizMessageException e) {
			System.err.println("Someone sent GenericSarsaLambda a message that wasn't RL-Viz compatible");
			return "I only respond to RL-Viz messages!";
		}

		if(theMessageObject.canHandleAutomatically(this)){
			/*Some messages have a "handle automatically" method that works. Each environment will have
			 * sets of messages it implements. If they implement them with handle automatically you will need to do no other work
			 */
			return theMessageObject.handleAutomatically(this);
		}

		/*If the message cannot be handled automatically but WAS RL-Viz Compatible, you should 
		 * write code here to handle those messages. The Environment Writer should let you know if there
		 * are messages you're going to have to worry about... 
		 */

                System.out.println("GenericSarsaLambda :: Unhandled Message :: "+theMessageObject);
		return "";
	}

	/********* RL GLUE REQUIRED FUNCTIONS END HERE *******/

	/********** RL VIZ REQUIRED  FUNCTIONS START HERE******
  * @param theObservation 
  * @return The state value for theObservation
  */
	public double getValueForState(Observation theObservation) {
		/*this must be implemented to use RL-Viz. This allows the 
		 * visualizer to get values for states. This is useful when trying
		 * to visualize the value function for example
		 */
		//retrieve the current value for the state/observation provided. 
		if(!inited)return 0.0d;

		int queryF[] = new int[actionCount*numTilings];
		double queryQ[] = new double[actionCount];

		//Don't count these queries for finding out what features are active
		loadF(queryF,theObservation,false);                         // compute features
		loadQ(queryQ,queryF); 
		int maxIndex=argmax(queryQ);
		return queryQ[maxIndex];	
	}
	/********** RL VIZ REQUIRED  FUNCTIONS END HERE*******/

	/********* Generic Sarsa Lambda Specific Functions Follow *********/

	// Compute all the action values from current F and theta
	private void loadQ(double Q[], int F[]) 
	{
		for (int a=0; a<actionCount; a++) 
		{
			Q[a] = 0;
			for (int j=0; j<numTilings; j++){
				try{
					Q[a] += weights[F[a*numTilings+j]];
				}catch(Exception e){
					System.out.println("Index into F is: "+a+"*"+numTilings+"+"+j);
					System.out.println("Index into theta is: "+F[a*numTilings+j]);
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
	}

	// Compute the action value from current F and theta
	private void loadQ(double Q[], int F[],int a) 
	{
		Q[a] = 0;
		for (int j=0; j<numTilings; j++) 
			Q[a] += weights[F[a*numTilings+j]];
	}



	private void loadF(int F[],Observation theObservation){
		loadF(F,theObservation,true);
	}
	private void loadF(int F[],Observation theObservation, boolean updateVisitedTiles)
	{
		//Compute the current F values given the observation
		int doubleCount=theObservation.doubleArray.length; // number of continuous observation values
		int intCount=theObservation.intArray.length; // number of discrete observation values
		double	double_vars[]=new double[doubleCount];
		int		int_vars[] = new int[intCount+1];


		for(int i=0;i<doubleCount;i++){
			if(normalizeObservations)
				double_vars[i] = (theObservation.doubleArray[i]-doubleMins[i]) / observationDividers[i];
			else
				double_vars[i] = theObservation.doubleArray[i] / observationDividers[i];
		}

		//int_vars[0] will be the action
		for(int i=0;i<intCount;i++){
			int_vars[i+1] = theObservation.intArray[i];
		}

		for (int a=0; a<actionCount; a++){
			int_vars[0]=a;
			theTileCoder.tiles(F,a*numTilings,numTilings,memorySize,double_vars,doubleCount,int_vars, intCount+1);
		}
		if(updateVisitedTiles){
			for (int i : F){
				allVisitedTiles.add(i);
			}
		}	
	}

	// Returns index (which corresponds to the appropriate action value) of largest entry in Q array, breaking ties randomly
	private int argmax(double Q[])
	{
		int best_action = 0;
		double best_value = Q[0];
		int num_ties = 1;                    // actually the number of ties plus 1
		double value;

		//for all possible actions we flip through the Q values to find the max
		for (int a=1; a<actionCount; a++) 
		{
			value = Q[a];
			if (value >= best_value) 
				if (value > best_value)
				{
					best_value = value;
					best_action = a;
				}
				else 
				{
					num_ties++;
					//we Break ties RANDOMLY
					if (0 == (int)(Math.random()*num_ties))
					{
						best_value = value;
						best_action = a;
					}
				}
		}
		return best_action;
	}

	private void SetTrace(int f, double new_trace_value)
	// Manually Set the trace for feature f to the given value, which must be positive
	{ 
		if (traces[f] >= minimumTrace)
			traces[f] = new_trace_value;         // trace already exists
		else 
		{ 
			while (numNonZeroTraces >= maxNonZeroTraces)
				IncreaseMinTrace(); // ensure room for new trace
			traces[f] = new_trace_value;
			nonZeroTraces[numNonZeroTraces] = f;
			nonZeroTracesInverse[f] = numNonZeroTraces;
			numNonZeroTraces++;
		}
	}



	private void ClearTrace(int f)       
	// Clear any trace for feature f
	{ 
		if (!(traces[f]==0.0)) 
			ClearExistentTrace(f,nonZeroTracesInverse[f]); 
	}


	private void ClearExistentTrace(int f, int loc)
	// Clear the trace for feature f at location loc in the list of nonzero traces
	{ 
		traces[f] = 0.0;
		numNonZeroTraces--;
		nonZeroTraces[loc] = nonZeroTraces[numNonZeroTraces];
		nonZeroTracesInverse[nonZeroTraces[loc]] = loc;}



	private void DecayTraces(double decay_rate)
	// Decays all the (nonzero) traces by decay_rate, removing those below minimum_trace
	{ 
		for (int loc=numNonZeroTraces-1; loc>=0; loc--)      // necessary to loop downwards
		{ 
			int f = nonZeroTraces[loc];
			traces[f] *= decay_rate;
			if (traces[f] < minimumTrace) ClearExistentTrace(f,loc);
		}

	}

	private void IncreaseMinTrace()
	// Try to make room for more traces by incrementing minimum_trace by 10%, 
	// culling any traces that fall below the new minimum
	{ 
		minimumTrace += 0.1 * minimumTrace;
		for (int loc=numNonZeroTraces-1; loc>=0; loc--)      // necessary to loop downwards
		{ 
			int f = nonZeroTraces[loc];
			if (traces[f] < minimumTrace) 
				ClearExistentTrace(f,loc);
		}
	}

	private void adjustRecordedRange(Observation Observations){
//If we are not using normalized observations, bail out!
		if(!normalizeObservations)return;
		
		if(!this.rangeDetermined){
			//if we haven't seen any observations yet, we initialize our min and max arrays to hold the 
			//current values of the observation variables and then break
			this.rangeDetermined = true;
			for(int i=0; i< Observations.doubleArray.length; i++){
				this.doubleMins[i] = Observations.doubleArray[i];
				this.doubleMaxs[i] = Observations.doubleArray[i];
			}
			return;
		}

		//updating our double Max and Mins
		for(int i =0; i< Observations.doubleArray.length; i++){
			if(Observations.doubleArray[i] < this.doubleMins[i]){
				this.doubleMins[i] = Observations.doubleArray[i];
			}
			if(Observations.doubleArray[i] > this.doubleMaxs[i]){
				this.doubleMaxs[i] = Observations.doubleArray[i];
			}
		}

		for(int i=0; i< this.observationDividers.length; i++){
			this.observationDividers[i] = (this.doubleMaxs[i] - this.doubleMins[i])/numGrids;

			if(observationDividers[i]==0)
				System.out.println("Ahh, its 0!");
		}

	}

    public RLVizVersion getTheVersionISupport() {
       return new RLVizVersion(1, 0);
    }


}
