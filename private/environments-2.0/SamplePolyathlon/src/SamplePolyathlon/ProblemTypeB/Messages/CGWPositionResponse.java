package SamplePolyathlon.ProblemTypeB.Messages;


import java.awt.geom.Rectangle2D;
import java.util.StringTokenizer;
import java.util.Vector;

import rlVizLib.messaging.AbstractResponse;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;


public class CGWPositionResponse extends AbstractResponse{
    double x;
    double y;
	
	public CGWPositionResponse(double x, double y) {
            this.x=x;
            this.y=y;
	}

	public CGWPositionResponse(String responseMessage) throws NotAnRLVizMessageException {

		GenericMessage theGenericResponse = new GenericMessage(responseMessage);

		String thePayLoadString=theGenericResponse.getPayLoad();

		StringTokenizer stateTokenizer = new StringTokenizer(thePayLoadString, ":");
		
                this.x=Double.parseDouble(stateTokenizer.nextToken());
                this.y=Double.parseDouble(stateTokenizer.nextToken());
	}


	@Override
	public String toString() {
		String theResponse="CGWPositionResponse: not implemented ";
		return theResponse;
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

                theResponseBuffer.append(x);
		theResponseBuffer.append(":");
                theResponseBuffer.append(y);


		return theResponseBuffer.toString();
	}
        
        public double getX(){return x;}
        public double getY(){return y;}


};