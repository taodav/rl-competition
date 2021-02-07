package Pacman.messages;

import rlVizLib.messaging.AbstractMessage;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlglue.RLGlue;

public class PacmanTextRequest extends EnvironmentMessages{
	public PacmanTextRequest(GenericMessage theMessageObject) {
		super(theMessageObject);
	}

	public static PacmanTextResponse Execute() {
		String theRequest=AbstractMessage.makeMessage(
				MessageUser.kEnv.id(),
				MessageUser.kBenchmark.id(),
				EnvMessageType.kEnvCustom.id(),
				MessageValueType.kString.id(),
				"GETPACMANTEXT");

		String responseMessage=RLGlue.RL_env_message(theRequest);
		PacmanTextResponse theResponse;
		try{
		theResponse = new PacmanTextResponse(responseMessage);
		}catch(NotAnRLVizMessageException ex){
			System.out.println("Not a valid RL Viz Message in Pacman State Request" + ex);
			return null;
		}
		return theResponse;
	}
}
