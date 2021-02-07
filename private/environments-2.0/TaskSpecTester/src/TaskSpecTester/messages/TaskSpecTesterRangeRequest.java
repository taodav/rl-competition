/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TaskSpecTester.messages;

import rlVizLib.glueProxy.RLGlueProxy;
import rlVizLib.messaging.AbstractMessage;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;
import rlVizLib.messaging.environment.EnvironmentMessages;

/**
 *
 * @author mark
 */
public class TaskSpecTesterRangeRequest extends EnvironmentMessages {
    public TaskSpecTesterRangeRequest(GenericMessage theMessageObject) {
	super(theMessageObject);
    }

    public static TaskSpecTesterRangeResponse Execute() {
        String theRequest=AbstractMessage.makeMessage(
			MessageUser.kEnv.id(),
			MessageUser.kBenchmark.id(),
			EnvMessageType.kEnvCustom.id(),
			MessageValueType.kString.id(),
			"GETHELIRANGE");

	String responseMessage=RLGlueProxy.RL_env_message(theRequest);
	TaskSpecTesterRangeResponse theResponse;
	try{
            theResponse = new TaskSpecTesterRangeResponse(responseMessage);
	}catch(NotAnRLVizMessageException ex){
            System.out.println("Not a valid RL Viz Message in TaskSpecTester Range Reqest" + ex);
            return null;
	}
	return theResponse;
    }
}
