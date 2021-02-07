/* Tetris Domain
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
package Tetrlais.messages;

import rlVizLib.glueProxy.RLGlueProxy;
import rlVizLib.messaging.AbstractMessage;
import rlVizLib.messaging.GenericMessage;
import rlVizLib.messaging.MessageUser;
import rlVizLib.messaging.MessageValueType;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvMessageType;
import rlVizLib.messaging.environment.EnvironmentMessages;

public class TetrlaisWorldRequest extends EnvironmentMessages{

	public TetrlaisWorldRequest(GenericMessage theMessageObject) {
		super(theMessageObject);
	}

	public synchronized static TetrlaisWorldResponse Execute(){
		String theRequest=AbstractMessage.makeMessage(
				MessageUser.kEnv.id(),
				MessageUser.kBenchmark.id(),
				EnvMessageType.kEnvCustom.id(),
				MessageValueType.kString.id(),
				"GETTETRLAISWORLD");

		String responseMessage=RLGlueProxy.RL_env_message(theRequest);

		TetrlaisWorldResponse theResponse;
		try{
		theResponse = new TetrlaisWorldResponse(responseMessage);
		}catch(NotAnRLVizMessageException ex){
			System.out.println("Not a valid RL Viz Message in Tetrlais World Response" + ex);
			return null;
		}

		return theResponse;
	}

}