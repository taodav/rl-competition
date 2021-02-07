package Pacman.messages;

import java.util.StringTokenizer;

import rlVizLib.messaging.AbstractResponse;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;

public class PacmanTextResponse extends AbstractResponse {
	int numEpisodes;
	int numSteps;
	int totalSteps;
	int numLives;
	public PacmanTextResponse(int numberEpisodes, int numberSteps, int tSteps, int numberLives){
		numEpisodes=  numberEpisodes;
		numSteps = numberSteps;
		totalSteps = tSteps;
		numLives = numberLives;
	}
	
	public PacmanTextResponse(String responseMessage)throws NotAnRLVizMessageException{
		
		GenericMessage theGenericResponse;
			theGenericResponse = new GenericMessage(responseMessage);


		String thePayLoadString=theGenericResponse.getPayLoad();

		StringTokenizer stateTokenizer = new StringTokenizer(thePayLoadString, ":");
		numEpisodes = Integer.parseInt(stateTokenizer.nextToken());
		numSteps = Integer.parseInt(stateTokenizer.nextToken());
		totalSteps = Integer.parseInt(stateTokenizer.nextToken());
		numLives = Integer.parseInt(stateTokenizer.nextToken());

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

		theResponseBuffer.append(numEpisodes);
		theResponseBuffer.append(":");
		theResponseBuffer.append(numSteps);
		theResponseBuffer.append(":");
		theResponseBuffer.append(totalSteps);
		theResponseBuffer.append(":");
		theResponseBuffer.append(numLives);
		theResponseBuffer.append(":");

		return theResponseBuffer.toString();
	}

	public String getEpisodes() {
		return (new Integer(numEpisodes)).toString();
	}

	public String getTotalSteps() {
		return (new Integer(totalSteps)).toString();
	}

	public String getEpisodeSteps() {
		return (new Integer(numSteps)).toString();
	}

	public String getLives() {
		return (new Integer(numLives)).toString();
	}

}

