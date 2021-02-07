package visualizers.RealTimeStrategyVisualizer;

import rlVizLib.messaging.AbstractResponse;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;

public class RTSSpecResponse extends AbstractResponse {
  
  String taskSpec=null;

  static public RTSSpecResponse createRTSSpecResponseFromString(String taskSpec){
    RTSSpecResponse theResponse=new RTSSpecResponse();
    theResponse.taskSpec=taskSpec;
    return theResponse;
  }
  
  public RTSSpecResponse(){
    taskSpec="NOT SET!";
  }

  

  public RTSSpecResponse(String responseMessage) throws NotAnRLVizMessageException {
    GenericMessage theGenericResponse = new GenericMessage(responseMessage);
    taskSpec=theGenericResponse.getPayLoad();
  }

  public String makeStringResponse() {
    StringBuffer theResponseBuffer= new StringBuffer();
    theResponseBuffer.append("TO=");
    theResponseBuffer.append(MessageUser.kBenchmark.id());
    theResponseBuffer.append(" FROM=");
    theResponseBuffer.append(MessageUser.kEnv.id());
    theResponseBuffer.append(" CMD=");
    theResponseBuffer.append(EnvMessageType.kEnvResponse.id());
    theResponseBuffer.append(" VALTYPE=");
    theResponseBuffer.append(MessageValueType.kString.id());
    theResponseBuffer.append(" VALS=");
    
    theResponseBuffer.append(taskSpec);
    return theResponseBuffer.toString();
  }

  public String getTaskSpec(){
    return taskSpec;
  }
}
