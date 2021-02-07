package Pacman.messages;

import rlVizLib.messaging.AbstractMessage;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlglue.RLGlue;

public class PacmanStateRequest extends EnvironmentMessages{

	public PacmanStateRequest(GenericMessage theMessageObject) {
		super(theMessageObject);
	}

	public static PacmanStateResponse Execute() {
		String theRequest=AbstractMessage.makeMessage(
				MessageUser.kEnv.id(),
				MessageUser.kBenchmark.id(),
				EnvMessageType.kEnvCustom.id(),
				MessageValueType.kString.id(),
				"GETPACMANSTATE");

		String responseMessage=RLGlue.RL_env_message(theRequest);
		PacmanStateResponse theResponse;
		try{
		theResponse = new PacmanStateResponse(responseMessage);
		}catch(NotAnRLVizMessageException ex){
			System.out.println("Not a valid RL Viz Message in Pacman State Request" + ex);
			return null;
		}
		return theResponse;
	}

}
