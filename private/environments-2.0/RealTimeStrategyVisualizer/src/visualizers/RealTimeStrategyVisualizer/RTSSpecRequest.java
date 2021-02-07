package visualizers.RealTimeStrategyVisualizer;

import rlVizLib.glueProxy.RLGlueProxy;
import rlVizLib.messaging.AbstractMessage;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;
import rlVizLib.messaging.environment.EnvironmentMessages;

public class RTSSpecRequest extends EnvironmentMessages {

  public RTSSpecRequest(GenericMessage theMessageObject){
    super(theMessageObject);
  }

  public static RTSSpecResponse Execute(){
    String theRequest=AbstractMessage.makeMessage(
        MessageUser.kEnv.id(),
        MessageUser.kBenchmark.id(),
        EnvMessageType.kEnvCustom.id(),
        MessageValueType.kString.id(),
        "GetRTSSpec");

    String responseMessage=RLGlueProxy.RL_env_message(theRequest);

    RTSSpecResponse theResponse;
    try {
      theResponse = new RTSSpecResponse(responseMessage);
    } catch (NotAnRLVizMessageException e) {
      System.err.println("In RTSSpecRequest, the response was not RL-Viz compatible");
      theResponse=null;
    }

    return theResponse;

  }
}
