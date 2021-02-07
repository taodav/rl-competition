package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeC.Messages;


import java.awt.geom.Rectangle2D;
import java.util.StringTokenizer;
import java.util.Vector;

import rlVizLib.messaging.AbstractResponse;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;


public class ProblemTypeCStateResponse extends AbstractResponse{
    double theta1;
    double theta2;
	
	public ProblemTypeCStateResponse(double theta1, double theta2) {
            this.theta1=theta1;
            this.theta2=theta2;
	}

	public ProblemTypeCStateResponse(String responseMessage) throws NotAnRLVizMessageException {

		GenericMessage theGenericResponse = new GenericMessage(responseMessage);

		String thePayLoadString=theGenericResponse.getPayLoad();

		StringTokenizer stateTokenizer = new StringTokenizer(thePayLoadString, ":");
		
                this.theta1=Double.parseDouble(stateTokenizer.nextToken());
                this.theta2=Double.parseDouble(stateTokenizer.nextToken());
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

                theResponseBuffer.append(theta1);
		theResponseBuffer.append(":");
                theResponseBuffer.append(theta2);


		return theResponseBuffer.toString();
	}
        
        public double getTheta1(){return theta1;}
        public double getTheta2(){return theta2;}


};