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
public class TaskSpecTesterRangeResponse extends AbstractResponse {

    double [] mins = new double[7];
    double [] maxs = new double[7];
    
    public TaskSpecTesterRangeResponse(double[] mins, double[] maxs) {
        this.mins = mins;
        this.maxs = maxs;
    }
    
    public TaskSpecTesterRangeResponse(String responseMessage) throws NotAnRLVizMessageException {
        GenericMessage theGenericResponse;

        theGenericResponse = new GenericMessage(responseMessage);
			
	String thePayLoadString=theGenericResponse.getPayLoad();
		
	StringTokenizer stateTokenizer = new StringTokenizer(thePayLoadString, ":");
	//this.something = Integer.parseInt(stateTokenizer.nextToken());
	for(int i=0; i< mins.length; i++){
            mins[i] = Double.parseDouble(stateTokenizer.nextToken());	
	}
	for(int i=0; i< maxs.length; i++){
		maxs[i] = Double.parseDouble(stateTokenizer.nextToken());	
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
        for(int i=0; i< mins.length; i++){
            theResponseBuffer.append(mins[i]);
            theResponseBuffer.append(":");
        }
        for(int i=0; i< maxs.length; i++){
            theResponseBuffer.append(maxs[i]);
            theResponseBuffer.append(":");
        }	

        return theResponseBuffer.toString();
    }

    public double [] getMins(){
        return mins;
    }
    public double [] getMaxs(){
        return maxs;
    }
    public double getMinAt(int i){
        if((i>=0)&&(i<mins.length))
        return mins[i];
        return Double.NEGATIVE_INFINITY;
    }

    public double getMaxAt(int i){
        if((i>=0)&&(i<maxs.length))
        return maxs[i];
        return Double.POSITIVE_INFINITY;
    }
}
