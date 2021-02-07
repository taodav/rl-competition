/* Mountain Car Domain
* Copyright (C) 2007, Brian Tanner brian@tannerpages.com (http://brian.tannerpages.com/)
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. */
package mcMessages;


import java.util.StringTokenizer;

import rlVizLib.messaging.AbstractResponse;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;


public class MCStateResponse extends AbstractResponse{
	double position;
	double velocity;
	double height;
	double deltaheight;

	public MCStateResponse(double position, double velocity, double height,double deltaheight) {
		this.position=position;
		this.velocity=velocity;
		this.height=height;
		this.deltaheight=deltaheight;
		
	}

	public MCStateResponse(String responseMessage) throws NotAnRLVizMessageException {

		GenericMessage theGenericResponse = new GenericMessage(responseMessage);

		String thePayLoadString=theGenericResponse.getPayLoad();

		StringTokenizer stateTokenizer = new StringTokenizer(thePayLoadString, ":");

		position=Double.parseDouble(stateTokenizer.nextToken());
		velocity=Double.parseDouble(stateTokenizer.nextToken());
		height=Double.parseDouble(stateTokenizer.nextToken());
		deltaheight=Double.parseDouble(stateTokenizer.nextToken());
	}

	@Override
	public String toString() {
		String theResponse="MCStateResponse: not implemented ";
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

		theResponseBuffer.append(position);
		theResponseBuffer.append(":");
		theResponseBuffer.append(velocity);
		theResponseBuffer.append(":");
		theResponseBuffer.append(height);
		theResponseBuffer.append(":");
		theResponseBuffer.append(deltaheight);

		return theResponseBuffer.toString();
	}

	public double getPosition() {
		return position;
	}

	public double getVelocity() {
		return velocity;
	}

	public double getHeight() {
		return height;
	}

	public double getDeltaheight() {
		return deltaheight;
	}


};