#include "AgentMessages.h"

AgentMessages *AgentMessageParser::parseMessage(std::string theMessage){
		GenericMessage theGenericMessage(theMessage);

		int cmdId=theGenericMessage.getTheMessageType();

		if(cmdId==kAgentQueryValuesForObs){
                    //Whoever gets this request is responsible for deleting it
			AgentMessages *theRequest= new AgentValueForObsRequest(theGenericMessage);
			return theRequest;
		}

	std::cerr<<"AgentMessageParser in CPP - unknown query type: "<<theMessage<<std::endl;
	exit(-1);
	return NULL;
}

AgentMessages::AgentMessages(GenericMessage theMessageObject): AbstractMessage(theMessageObject){
			}

			
std::string              AgentMessages::handleAutomatically(QueryableAgent theAgent){
				return "no response";
			}

