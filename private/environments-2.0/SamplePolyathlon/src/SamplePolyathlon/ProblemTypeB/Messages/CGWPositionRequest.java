package SamplePolyathlon.ProblemTypeB.Messages;


import rlVizLib.glueProxy.RLGlueProxy;
import rlVizLib.messaging.AbstractMessage;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;
import rlVizLib.messaging.environment.EnvironmentMessages;

public class CGWPositionRequest extends EnvironmentMessages{

	public CGWPositionRequest(GenericMessage theMessageObject){
		super(theMessageObject);
	}

	public static CGWPositionResponse Execute(){
		String theRequest=AbstractMessage.makeMessage(
				MessageUser.kEnv.id(),
				MessageUser.kBenchmark.id(),
				EnvMessageType.kEnvCustom.id(),
				MessageValueType.kString.id(),
				"GETCGWPOS");

		String responseMessage=RLGlueProxy.RL_env_message(theRequest);

		CGWPositionResponse theResponse;
		try {
			theResponse = new CGWPositionResponse(responseMessage);
		} catch (NotAnRLVizMessageException e) {
			System.err.println("In CGWMapRequest, the response was not RL-Viz compatible");
			theResponse=null;
		}

		return theResponse;

	}
}
