package Pacman;

import java.util.Random;

import Pacman.messages.PacmanStateResponse;
import Pacman.messages.PacmanTextResponse;
import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.general.RLVizVersion;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.RLVizEnvInterface;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Random_seed_key;
import rlglue.types.Reward_observation;
import rlglue.types.State_key;

public class Pacman extends EnvironmentBase implements RLVizEnvInterface {
	int [][] observedMap;
	int [][] dotsMap;
	int mapWidth;
	int mapHeight;
	
	int numLives;
	boolean superPowered = false;
	int remainingDots;
	int currentXPos;
	int currentYPos;
	
	int totalGhosts;
	int numGhosts;
	int[] ghostsXPos;
	int[] ghostsYPos;
	int[] ghostsXStart;
	int[] ghostsYStart;
	boolean[] ghostsDead;
	
	int startX;
	int startY;
	int totalLives;
	int pelletPowerLength;
	int ghostDeathLength;
	int numSuperStepsLeft;
	
	
	//MAP KEY
	final static int EMPTY =0;
	final static int WALL = 1;
	final static int DOT = 2;
	final static int GHOST = 3;
	final static int POWERPELLET =4;
	final static int PACMAN = 5;
	final static int DEADGHOST = 6;
	
	//ACTIONS
	final static int UP = 0;
	final static int RIGHT = 1;
	final static int DOWN = 2;
	final static int LEFT = 3;
	
	final static int defaultXStart = 0;
	final static int defaultYStart = 2;
	final static int defaultLives = 3;
	final static int defaultNumGhosts = 4;
	final static int[] defaultXGhosts = {1,2,3,4};
	final static int[] defaultYGhosts= {1,2,3,4};
	final static int defaultPelletPowerLength = 10;
	final static int defaultGhostDeathLength = 5;
	
	int numberEpisodes = 0;
	int numberSteps = 0;
	int totalSteps =0;
	
	Random randomNumGen = new Random();
	
	public Pacman(){
		this(getDefaultParameters());
	}
	
	public Pacman(ParameterHolder p){
	super();
		if(p!=null){
			if(!p.isNull()){
				totalLives=p.getIntegerParam("Lives");
				startX=p.getIntegerParam("startXPos");
				startY=p.getIntegerParam("startYPos");
				pelletPowerLength = p.getIntegerParam("pellet");
				ghostDeathLength = p.getIntegerParam("ghostDeath");
			}
		}
	}
	
	//This method creates the object that can be used to easily set different problem parameters
	public static ParameterHolder getDefaultParameters(){
		ParameterHolder p = new ParameterHolder();
		p.addIntegerParam("Number of Lives",defaultLives);
		p.setAlias("Lives","Number of Lives");
		p.addIntegerParam("Starting X Coordinate",defaultXStart);
		p.setAlias("startXPos", "Starting X Coordinate");
		p.addIntegerParam("Starting Y Coordinate",defaultYStart);
		p.setAlias("startYPos", "Starting Y Coordinate");
		p.addIntegerParam("Number of Steps for Pellet Power", defaultPelletPowerLength);
		p.setAlias("pellet", "Number of Steps for Pellet Power");
		p.addIntegerParam("Number of Steps Ghosts Remain Dead", defaultGhostDeathLength);
		p.setAlias("ghostDeath", "Number of Steps Ghosts Remain Dead");
		return p;
	}

	
	/*RL-GLUE FUNCTIONS START HERE*/
	public String env_init() {
		numberEpisodes = 0;
		numberSteps = 0;
		totalSteps =0;
		numLives = totalLives;
		superPowered = false;
		initializeMap();
		
		String taskSpec = "2:e:";
		int size = mapWidth*mapHeight+2;
		taskSpec = taskSpec.concat(size+"_[");
		for(int i=0; i< mapWidth*mapHeight+1; i++){
			taskSpec = 	taskSpec.concat("i,");
		}
		taskSpec = taskSpec.concat("i]_");
		for(int i=0; i< mapWidth*mapHeight; i++)
			taskSpec = taskSpec.concat("[0,6]_");
		taskSpec = taskSpec.concat("[0," + totalLives + "]_");
		taskSpec = taskSpec.concat("[0,1]:");
		taskSpec = taskSpec.concat("1_[i]_[0,3]:[]");
		System.out.println("Task Spec: " + taskSpec);
		return taskSpec;
	}
	
	public Observation env_start() {
		numberSteps = 0;
		numberEpisodes++;
		numLives = totalLives;
		superPowered = false;
		initializeMap();
		return makeObservation();
	}


	public Reward_observation env_step(Action action) {
		numberSteps++;
		totalSteps++;
		Reward_observation ro = new Reward_observation();
		ro.r = takeStep(action);
		ro.terminal = 0;
		if(isTerminal()){
			ro.terminal = 1;
			System.out.println("score: " + (6-remainingDots) + " with some lives left: " + numLives);
		}
		ro.o = makeObservation();
		return ro;
		
	}
	
	public void env_cleanup() {
		// TODO Auto-generated method stub
		
	}

	public String env_message(String theMessage) {
		EnvironmentMessages theMessageObject;
		try {
			theMessageObject = EnvironmentMessageParser.parseMessage(theMessage);
		} catch (Exception e) {
			System.err.println("Someone sent Pacman a message that wasn't RL-Viz compatible");
			return "I only respond to RL-Viz messages!";
		}

		if(theMessageObject.canHandleAutomatically(this)){
			return theMessageObject.handleAutomatically(this);
		}

		if(theMessageObject.getTheMessageType()==rlVizLib.messaging.environment.EnvMessageType.kEnvCustom.id()){

			String theCustomType=theMessageObject.getPayLoad();

			if(theCustomType.equals("GETPACMANSTATE")){
				//It is a request for the state
				int [] tempArray = new int[mapWidth*mapHeight];
				for(int i=0; i< mapWidth; i++)
					for(int j=0; j< mapHeight; j++)
						tempArray[j*mapWidth +i] = observedMap[i][j];
				PacmanStateResponse theResponseObject=new PacmanStateResponse(mapWidth, mapHeight,tempArray,numLives,superPowered);
				return theResponseObject.makeStringResponse();
			}

			if(theCustomType.equals("GETPACMANTEXT")){ 
				PacmanTextResponse theResponseObject=new PacmanTextResponse(numberEpisodes, numberSteps, totalSteps, numLives);
				return theResponseObject.makeStringResponse();
			}
		
			
			
			System.out.println("We need some code written in Env Message for Pacman.. unknown custom message type received");
			Thread.dumpStack();

			return null;
		}

		System.out.println("We need some code written in Env Message for PACMAN!");
		Thread.dumpStack();

		return null;
	}

	public Random_seed_key env_get_random_seed() {
		// TODO Auto-generated method stub
		return null;
	}

	public State_key env_get_state() {
		// TODO Auto-generated method stub
		return null;
	}

	public void env_set_random_seed(Random_seed_key key) {
		// TODO Auto-generated method stub
		
	}

	public void env_set_state(State_key key) {
		// TODO Auto-generated method stub
		
	}

	/*END OF RL-GLUE FUNCTIONS*/

	/*RLVIZ functions*/
	public RLVizVersion getTheVersionISupport() {
		return new RLVizVersion(1,0);
	}
	
	@Override
	protected Observation makeObservation() {
		Observation obs = new Observation(mapWidth*mapHeight+2, 0);
		for(int i=0; i< mapWidth; i++)
			for(int j=0; j< mapHeight; j++)
				obs.intArray[j*mapWidth +i] = observedMap[i][j];
		obs.intArray[mapWidth*mapHeight] = numLives;
		obs.intArray[mapWidth*mapHeight+1] = 0;
		if(superPowered)
			obs.intArray[mapWidth*mapHeight+1] = 1;
		return obs;
	}
	/*END OF RLVIZ FUNCTIONS*/
	

	private void initializeMap() {
		observedMap = new int [3][3];
		observedMap[0][0]= PACMAN;
		observedMap[0][1]= POWERPELLET;
		observedMap[0][2]= DOT;
		observedMap[1][0]= DOT;
		observedMap[1][1]= GHOST;
		observedMap[1][2]= DOT;
		observedMap[2][0]= WALL;
		observedMap[2][1]= DOT;
		observedMap[2][2]= DOT;
		
		
		dotsMap = new int [3][3];
		dotsMap[0][0]= EMPTY;
		dotsMap[0][1]= POWERPELLET;
		dotsMap[0][2]= DOT;
		dotsMap[1][0]= DOT;
		dotsMap[1][1]= EMPTY;
		dotsMap[1][2]= DOT;
		dotsMap[2][0]= WALL;
		dotsMap[2][1]= DOT;
		dotsMap[2][2]= DOT;
		
		mapWidth=3;
		mapHeight=3;
		
		superPowered = false;
		remainingDots =6;
		currentXPos =startX;
		currentYPos = startY;
		
		totalGhosts = 1;
		numGhosts =1;
		ghostsXPos = new int[1];
		ghostsYPos = new int[1];
		ghostsXPos[0] = 1;
		ghostsYPos[0]=1;
		
		ghostsXStart = new int[1];
		ghostsYStart = new int[1];
		ghostsXStart[0] = 1;
		ghostsYStart[0]=1;
		ghostsDead = new boolean[1];
		ghostsDead[0] = false;

		numSuperStepsLeft = 0;
		
		/*observedMap = new int [5][5];
		observedMap[0][0]= WALL;
		observedMap[0][1]= WALL;
		observedMap[0][2]= PACMAN;
		observedMap[0][3]= DOT;
		observedMap[0][4]= WALL;
		
		observedMap[1][0]= GHOST;
		observedMap[1][1]= DOT;
		observedMap[1][2]= WALL;
		observedMap[1][3]= DOT;
		observedMap[1][4]= WALL;
		
		observedMap[2][0]= DOT;
		observedMap[2][1]= DOT;
		observedMap[2][2]= DOT;
		observedMap[2][3]= DOT;
		observedMap[2][4]= POWERPELLET;
		
		observedMap[3][0]= WALL;
		observedMap[3][1]= DOT;
		observedMap[3][2]= DOT;
		observedMap[3][3]= DOT;
		observedMap[3][4]= DOT;
		
		observedMap[4][0]= WALL;
		observedMap[4][1]= WALL;
		observedMap[4][2]= DOT;
		observedMap[4][3]= WALL;
		observedMap[4][4]= WALL;
		
		dotsMap = new int [5][5];
		dotsMap[0][0]= WALL;
		dotsMap[0][1]= WALL;
		dotsMap[0][2]= EMPTY;
		dotsMap[0][3]= DOT;
		dotsMap[0][4]= WALL;
		
		dotsMap[1][0]= EMPTY;
		dotsMap[1][1]= DOT;
		dotsMap[1][2]= WALL;
		dotsMap[1][3]= DOT;
		dotsMap[1][4]= WALL;
		
		dotsMap[2][0]= DOT;
		dotsMap[2][1]= DOT;
		dotsMap[2][2]= DOT;
		dotsMap[2][3]= DOT;
		dotsMap[2][4]= POWERPELLET;
		
		dotsMap[3][0]= WALL;
		dotsMap[3][1]= DOT;
		dotsMap[3][2]= DOT;
		dotsMap[3][3]= DOT;
		dotsMap[3][4]= DOT;
		
		dotsMap[4][0]= WALL;
		dotsMap[4][1]= WALL;
		dotsMap[4][2]= DOT;
		dotsMap[4][3]= WALL;
		dotsMap[4][4]= WALL;
		
		mapWidth=5;
		mapHeight=5;
		
		superPowered = false;
		remainingDots =13;
		currentXPos =0;
		currentYPos = 2;
		
		totalGhosts = 1;
		numGhosts =1;
		ghostsXPos = new int[1];
		ghostsYPos = new int[1];
		ghostsXPos[0] = 1;
		ghostsYPos[0]=0;
		
		ghostsXStart = new int[1];
		ghostsYStart = new int[1];
		ghostsXStart[0] = 1;
		ghostsYStart[0]=0;
		ghostsDead = new boolean[1];
		ghostsDead[0] = false;

		numSuperStepsLeft = 0;*/
		
	}
	
	private boolean isTerminal() {
		return ((numLives <=0)||(remainingDots<1));
	}

	private double getReward() {
		// TODO Auto-generated method stub
		return 0;
	}

	private double takeStep(Action action) {
		//return the reward received
		int [] temp;
		double score =0.0d;
		temp = stepInDirection(currentXPos, currentYPos, action.intArray[0]);
		currentXPos = temp[0];
		currentYPos = temp[1];
			if(!validPosition(currentXPos, currentYPos))
				System.out.println("ARGGGG We have wandered into space... Somehow. this should not happen... Invalid position at " + currentXPos + " , " + currentYPos);
		
			
		//if we are currently in power of super powers, we decrease the amount of time we ahve them and check 
		//to see if we still have them
		decrementPowers();
		
		//check to see if we're standing on a dot. if we're standing on a dot we eat it. If it's a power pellet
		//we gain supernatural abilities!!!
		score += (double)(eatDots());
		
		//move ghosts AFTER pacman moves and BEFORE ghosts are eaten or hit.
		moveGhosts();
		
		//See if any Ghosts have eaten you or have been eaten (and make the according adjustments in each case)
		score -= (double)(detectGhostCollisons()*5.0d);

		//Redraw the observed map using the dotsMap and the positions of the ghosts and pacman. They are stored separate
		//because a ghost can cover a dot. if they were stored on the same bit array, we would not be able to recover the
		//dot information
		redrawObservedMap();
		
		if(isTerminal())
			if(numLives>0)
				return 10;
		return 0.0;
	}

	private int eatDots() {
		//eat the dot or power pellet we are standing on 
		//if there is one. If we have super powers, 
		//put them into effect
		int eatenDots = 0;
		if(dotsMap[currentXPos][currentYPos] == DOT){
			dotsMap[currentXPos][currentYPos] = EMPTY;
			remainingDots--;
			eatenDots++;
		}

		
		if(dotsMap[currentXPos][currentYPos] == POWERPELLET){
			dotsMap[currentXPos][currentYPos] = EMPTY;
			remainingDots--;
			superPowered = true;
			//super powers are additive? so if you eat one and then another you have twice as long? unsure 
			//if this is the same as the original game
			numSuperStepsLeft += pelletPowerLength;
			eatenDots++;
		}
		return eatenDots;
	}

	private void decrementPowers() {
		//decrease the length of time we have our super powers for
		if(superPowered)
			numSuperStepsLeft--;
  
		if(numSuperStepsLeft <1){
			superPowered = false;
			numSuperStepsLeft =0;
		}
	}

	private void redrawObservedMap() {
		//redraw the observation map. The observation map is the
		//fusion of the dots map and the ghosts and pacman
		//these are separate because a ghost can stand on a dot without 
		//consuming it
		for(int i=0; i< mapWidth; i++){
			for(int j=0; j< mapHeight; j++) 
				observedMap[i][j] = dotsMap[i][j];
		}
		
		for(int i=0; i<totalGhosts; i++){
			if(!ghostsDead[i])
				observedMap[ghostsXPos[i]][ghostsYPos[i]] = GHOST;
			else
				observedMap[ghostsXPos[i]][ghostsYPos[i]]=DEADGHOST;
		}
		observedMap[currentXPos][currentYPos] = PACMAN;
	}

	private int detectGhostCollisons() {
		//see if we're standing on a ghost. eat the ghost if we're super
		//or get eaten if we're not.
		int numColisions =0;
		for(int i=0; i< numGhosts; i++){
			if((ghostsXPos[i] ==currentXPos)&&(ghostsYPos[i] == currentYPos)){
				if(ghostsDead[i])
					;
				else if(!superPowered){
				numLives--;
				initializeMap();
				numColisions++;
				}
				else{
					ghostsDead[i] = true;
				}
			}
			}
		return numColisions;
	}

	private void moveGhosts() {
		//deterministic policy of moving closer in the 
		//direction it is most far from the ghost
		int [] temp = new int[2];
		int xDif;
		int yDif;
		for(int i =0; i< numGhosts; i++){
			temp[0] = ghostsXPos[i];
			temp[1] = ghostsYPos[i];
			if(ghostsDead[i]){
				xDif = ghostsXPos[i] - ghostsXStart[i];
				yDif = ghostsYPos[i] -ghostsYStart[i];
				if((xDif ==0) &&(yDif==0))
					ghostsDead[i] = false;
				else if(xDif==0)
					temp = moveCloserVertically(ghostsXPos[i], ghostsYPos[i], ghostsXStart[i], ghostsYStart[i]);
				else if(yDif==0)
					temp = moveCloserHorizontally(ghostsXPos[i], ghostsYPos[i], ghostsXStart[i], ghostsYStart[i]);
				else if(randomNumGen.nextInt()%2==0)
					temp = moveCloserVertically(ghostsXPos[i], ghostsYPos[i], ghostsXStart[i], ghostsYStart[i]);
				else
					temp = moveCloserHorizontally(ghostsXPos[i], ghostsYPos[i], ghostsXStart[i], ghostsYStart[i]);
				if((temp[i] == ghostsXStart[i]) && (temp[i] == ghostsYStart[i]))
					ghostsDead[i] = false;
				int count = 1000;
				while((ghostsXPos[i] == temp[0]) && (ghostsYPos[i] == temp[1]) && (count>0)){
					int direction = randomNumGen.nextInt()%4;
					temp = stepInDirection(ghostsXPos[i],ghostsYPos[i],direction);
					count--;
				}

			}
			else if(randomNumGen.nextDouble()< (double)(min(i,10)/10.0d))
				temp = stepInDirection(ghostsXPos[i],ghostsYPos[i], (int)(randomNumGen.nextInt()%4));
			else{
				xDif = ghostsXPos[i] - currentXPos;
				yDif = ghostsYPos[i] -currentYPos;
				if(superPowered){
					if(Math.abs(xDif)>Math.abs(yDif))
						if(randomNumGen.nextInt()%2==0)
							temp = moveFartherVertically(ghostsXPos[i], ghostsYPos[i], currentXPos, currentYPos);
						else
							temp = moveFartherHorizontally(ghostsXPos[i], ghostsYPos[i], currentXPos, currentYPos);
					else if(Math.abs(xDif)>Math.abs(yDif))
						temp = moveFartherVertically(ghostsXPos[i], ghostsYPos[i], currentXPos, currentYPos);
					else
						temp = moveFartherHorizontally(ghostsXPos[i], ghostsYPos[i], currentXPos, currentYPos);
				}
				else if((xDif==0)&&(yDif==0)){
					//if we're on the pacman DON'T move.
					temp[0] = ghostsXPos[i];
					temp[1] = ghostsYPos[i];
				}
				else{
					if(xDif==0)
						temp = moveCloserVertically(ghostsXPos[i], ghostsYPos[i], currentXPos, currentYPos);
					else if(yDif==0)
						temp = moveCloserHorizontally(ghostsXPos[i], ghostsYPos[i], currentXPos, currentYPos);
					else if(randomNumGen.nextInt()%2==0)
						temp = moveCloserVertically(ghostsXPos[i], ghostsYPos[i], currentXPos, currentYPos);
					else
						temp = moveCloserHorizontally(ghostsXPos[i], ghostsYPos[i], currentXPos, currentYPos);
				}
			}
			int count = 1000;
			while((ghostsXPos[i] == temp[0]) && (ghostsYPos[i] == temp[1]) && (count>0)){
				int direction = randomNumGen.nextInt()%4;
				temp = stepInDirection(ghostsXPos[i],ghostsYPos[i],direction);
				count--;
			}
			ghostsXPos[i] = temp[0];
			ghostsYPos[i] = temp[1];
		}
	}
	private int [] moveCloserHorizontally(int currentX,int currentY, int destX, int destY){
		int xDist = currentX-destX;
		if(xDist>0)
			return stepInDirection(currentX,currentY,LEFT);
		else
			return stepInDirection(currentX,currentY,RIGHT);
	}
	private int [] moveCloserVertically(int currentX,int currentY, int destX, int destY){
		int yDist = currentY-destY;
		if(yDist>0)
			return stepInDirection(currentX,currentY,UP);
		else
			return stepInDirection(currentX,currentY,DOWN);
	}
	
	private int [] moveFartherHorizontally(int currentX,int currentY, int destX, int destY){
		int xDist = currentX-destX;
		if(xDist>0)
			return stepInDirection(currentX,currentY,RIGHT);
		else
			return stepInDirection(currentX,currentY,LEFT);
	}
	private int [] moveFartherVertically(int currentX,int currentY, int destX, int destY){
		int yDist = currentY-destY;
		if(yDist>0)
			return stepInDirection(currentX,currentY,DOWN);
		else
			return stepInDirection(currentX,currentY,UP);
	}
		
	private double min(double i, double j) {
		if(i<j)
			return i;
		return j;
	}

	private boolean validPosition(int x,int y) {
		//return (x>=0)&&(x<mapWidth)&&(y>=0)&&(y<mapHeight);
		int tmpx =x;
		int tmpy =y;
		if(x<0)
			tmpx = mapWidth-1;
		if(x>=mapWidth)
			tmpx = 0;
		if(y<0)
			tmpy = mapHeight-1;
		if(y>=mapHeight)
			tmpy = 0;
		return !(dotsMap[tmpx][tmpy]==WALL);
	}
	
	private int[] stepInDirection(int x1, int y1, int a){
		int x = x1;
		int y = y1;
				switch(a){
		case UP: if(validPosition(x, y-1)){
				if((y-1)< 0)
					y = mapHeight-1;
				else if((y-1)>= mapHeight)
					y= 0;
				else
					y--;
				}	
				break;
		case RIGHT:if(validPosition(x+1, y)){	
				if((x+1)<0)
					x = mapWidth-1;
				else if((x+1)>= mapWidth)
					x = 0;
				else
					x++;
					}
				break;
		case DOWN: if(validPosition(x, y+1)){	
				if((y+1)<0)
					y = mapHeight-1;
				else if((y+1)>= mapHeight)
					y = 0;
				else
					y++;
					}
				break;
		case LEFT: if(validPosition(x-1, y)){
				if((x-1)<0)
					x = mapWidth-1;
				else if((x-1) >=mapWidth)
					x =0;
				else
					x--;
				}
			break;
		}
		int [] returnArray = new int [2];
		returnArray[0] = x;
		returnArray[1] = y;
		return returnArray;
	}

}
