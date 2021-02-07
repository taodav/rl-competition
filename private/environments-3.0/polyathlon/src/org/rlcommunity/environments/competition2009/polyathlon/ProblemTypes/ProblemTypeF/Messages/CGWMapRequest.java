package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeF.Messages;


import org.rlcommunity.rlglue.codec.RLGlue;
import rlVizLib.messaging.AbstractMessage;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;
import rlVizLib.messaging.environment.EnvironmentMessages;

public class CGWMapRequest extends EnvironmentMessages{

	public CGWMapRequest(GenericMessage theMessageObject){
		super(theMessageObject);
	}

	public static CGWMapResponse Execute(){
		String theRequest=AbstractMessage.makeMessage(
				MessageUser.kEnv.id(),
				MessageUser.kBenchmark.id(),
				EnvMessageType.kEnvCustom.id(),
				MessageValueType.kString.id(),
				"GETCGWMAP");

		String responseMessage=RLGlue.RL_env_message(theRequest);

		CGWMapResponse theResponse;
		try {
			theResponse = new CGWMapResponse(responseMessage);
		} catch (NotAnRLVizMessageException e) {
			System.err.println("In CGWMapRequest, the response was not RL-Viz compatible");
			theResponse=null;
		}

		return theResponse;

	}
}
