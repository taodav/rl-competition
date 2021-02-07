package Pacman.messages;

import java.util.StringTokenizer;

import rlVizLib.messaging.AbstractResponse;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;

public class PacmanStateResponse extends AbstractResponse {
	int [] currentMap;
	int numLives;
	boolean superPowered;
	int mapWidth;
	int mapHeight;
	
	public PacmanStateResponse(int width, int height, int [] map, int lives, boolean superP){
		mapWidth = width;
		mapHeight = height;
		currentMap = map;
		numLives = lives;
		superPowered = superP;
	}
	
	public PacmanStateResponse(String responseMessage)throws NotAnRLVizMessageException{
		
		GenericMessage theGenericResponse;
			theGenericResponse = new GenericMessage(responseMessage);


		String thePayLoadString=theGenericResponse.getPayLoad();

		StringTokenizer stateTokenizer = new StringTokenizer(thePayLoadString, ":");
		mapWidth  = Integer.parseInt(stateTokenizer.nextToken());
		mapHeight  = Integer.parseInt(stateTokenizer.nextToken());
		currentMap = new int [mapWidth*mapHeight];
		for(int i=0; i< mapWidth*mapHeight; i++)
			currentMap[i]= Integer.parseInt(stateTokenizer.nextToken());
		numLives = Integer.parseInt(stateTokenizer.nextToken());
		int temp = Integer.parseInt(stateTokenizer.nextToken());
		superPowered = false;
		if(temp == 1)
			superPowered = true;
	}

	
	
	@Override
	public String makeStringResponse() {
		
		StringBuffer theResponseBuffer= new StringBuffer();
		theResponseBuffer.append("TO=");
		theResponseBuffer.append(MessageUser.kBenchmark.id());
		theResponseBuffer.append(" FROM=");
		theResponseBuffer.append(MessageUser.kEnv.id());
		theResponseBuffer.append(" CMD=");
		theResponseBuffer.append(EnvMessageType.kEnvResponse.id());
		theResponseBuffer.append(" VALTYPE=");
		theResponseBuffer.append(MessageValueType.kStringList.id());
		theResponseBuffer.append(" VALS=");

		theResponseBuffer.append(mapWidth);
		theResponseBuffer.append(":");
		theResponseBuffer.append(mapHeight);
		theResponseBuffer.append(":");
		for(int i=0; i<mapWidth*mapHeight; i++){
		theResponseBuffer.append(currentMap[i]);
		theResponseBuffer.append(":");
		}
		theResponseBuffer.append(numLives);
		theResponseBuffer.append(":");
		if(superPowered)
			theResponseBuffer.append(1);
		else
			theResponseBuffer.append(0);

		return theResponseBuffer.toString();
	}
	
	public int getWidth(){
		return mapWidth;
	}
	
	public int getHeight(){
		return mapHeight;
	}

	public int [] getWorld(){
		return currentMap;
	}

	public boolean getIfSuper() {
		return superPowered;
	}
}


