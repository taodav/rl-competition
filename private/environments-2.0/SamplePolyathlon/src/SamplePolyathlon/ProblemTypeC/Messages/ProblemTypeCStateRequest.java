package SamplePolyathlon.ProblemTypeC.Messages;


import rlVizLib.glueProxy.RLGlueProxy;
import rlVizLib.messaging.AbstractMessage;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;
import rlVizLib.messaging.environment.EnvironmentMessages;

public class ProblemTypeCStateRequest extends EnvironmentMessages{

	public ProblemTypeCStateRequest(GenericMessage theMessageObject){
		super(theMessageObject);
	}

	public static ProblemTypeCStateResponse Execute(){
		String theRequest=AbstractMessage.makeMessage(
				MessageUser.kEnv.id(),
				MessageUser.kBenchmark.id(),
				EnvMessageType.kEnvCustom.id(),
				MessageValueType.kString.id(),
				"GETSTATE");

		String responseMessage=RLGlueProxy.RL_env_message(theRequest);

		ProblemTypeCStateResponse theResponse;
		try {
			theResponse = new ProblemTypeCStateResponse(responseMessage);
		} catch (NotAnRLVizMessageException e) {
			System.err.println("In ProblemTypeCStateRequest, the response was not RL-Viz compatible");
			theResponse=null;
		}

		return theResponse;

	}
}
