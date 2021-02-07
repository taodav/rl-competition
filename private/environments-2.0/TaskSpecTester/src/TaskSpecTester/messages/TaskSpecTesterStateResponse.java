/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TaskSpecTester.messages;

import java.util.StringTokenizer;
import rlVizLib.messaging.AbstractResponse;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;

/**
 *
 * @author mark
 */
public class TaskSpecTesterStateResponse extends AbstractResponse {
    double [] state = new double[7];

    public TaskSpecTesterStateResponse(double[] state){
        this.state[0] = state[0];
        this.state[1] = state[1];
        this.state[2] = state[2];
        this.state[3] = state[3];
        this.state[4] = state[4];
        this.state[5] = state[5];
        this.state[6] = state[6];
    }
    
    public TaskSpecTesterStateResponse(String responseMessage) throws NotAnRLVizMessageException {
        GenericMessage theGenericResponse;
        theGenericResponse = new GenericMessage(responseMessage);
			
        String thePayLoadString=theGenericResponse.getPayLoad();

        StringTokenizer stateTokenizer = new StringTokenizer(thePayLoadString, ":");
        //this.something = Integer.parseInt(stateTokenizer.nextToken());
        for(int i=0;  i< state.length; i++){
            state[i] = Double.parseDouble(stateTokenizer.nextToken());	
        }
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

        //theResponseBuffer.append(this.something);
        //theResponseBuffer.append(":");
        for(int i=0; i< state.length; i++){
        theResponseBuffer.append(state[i]);
        theResponseBuffer.append(":");
        }
        return theResponseBuffer.toString();
    }
    public double [] getState(){
        return state;
    }
}
