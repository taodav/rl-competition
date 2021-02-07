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
public class TaskSpecTesterStateRequest extends EnvironmentMessages {
    public TaskSpecTesterStateRequest(GenericMessage theMessageObject) {
        super(theMessageObject);
    }

    public static TaskSpecTesterStateResponse Execute() {
        String theRequest=AbstractMessage.makeMessage(
                        MessageUser.kEnv.id(),
                        MessageUser.kBenchmark.id(),
                        EnvMessageType.kEnvCustom.id(),
                        MessageValueType.kString.id(),
                        "GETHELISTATE");

        String responseMessage=RLGlueProxy.RL_env_message(theRequest);
        TaskSpecTesterStateResponse theResponse;
        try{
            theResponse = new TaskSpecTesterStateResponse(responseMessage);
        }catch(NotAnRLVizMessageException ex){
            System.out.println("Not a valid RL Viz Message in Helicopter State Request" + ex);
            return null;
        }
        return theResponse;
    }
}
