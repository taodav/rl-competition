//
// File:   AgentMessages.h
// Author: btanner
//
// Created on October 5, 2007, 1:48 PM
//

#ifndef _AGENTMESSAGES_H
#define	_AGENTMESSAGES_H

#include <string>
enum AgentMessageType{
    kAgentResponse=0,
	kAgentQueryValuesForObs=1,
	kAgentCustom=2
};
enum MessageUser{
    kBenchmark=0,
    kEnvShell=1,
    kAgentShell=2,
    kEnv=3,
    kAgent=4
};

enum MessageValueType{
    kStringList=0,
    kString=1,
    kBoolean=2,
    kNone=3
};

class Object{
    
};

class QueryableAgent{
    
};
class GenericMessage{
private:
    int theMessageType;
protected:
    MessageUser from;
    MessageUser to;
    MessageValueType payLoadType;
    std::string payLoad;
    
public:
    GenericMessage(std::string theMessage);
    
    int getTheMessageType();
    MessageUser getFrom();
    MessageUser getTo();
    MessageValueType getPayLoadType();
    std::string getPayLoad();
};

class AbstractMessage{
private:
    GenericMessage theRealMessageObject;
    
public:
    AbstractMessage(GenericMessage theMessageObject);
    int getTheMessageType();
    MessageUser getFrom();
    MessageUser getTo();
    MessageValueType getPayLoadType();
    std::string getPayLoad();
    
    /*
     * Override this if you can handle automatically given a queryable environment or agent
     */
    bool canHandleAutomatically(Object theReceiver);
    
    
    static std::string makeMessage(int TO, int FROM, int CMD, int VALTYPE, std::string PAYLOAD);
    
};

class AgentMessages : AbstractMessage{
public:
    AgentMessages(GenericMessage theMessageObject);
    std::string handleAutomatically(QueryableAgent theAgent);
    
};

class GenericMessageParser{
public:
    static MessageUser parseUser(std::string userChunk);
    
    static int parseInt(std::string typeString);
    static MessageValueType parseValueType(std::string typeString);
    
    static std::string parsePayLoad(std::string payLoadString);
};


class AgentMessageParser: GenericMessageParser{
public:
    static AgentMessages *parseMessage(std::string theMessage);
};


class AgentValueForObsRequest : AgentMessages{
	std::vector<Observation> theRequestObservations;//=new Vector<Observation>();
static boolean printedReturnError=false;

public:
    AgentValueForObsRequest(GenericMessage theMessageObject);

	public static AgentValueForObsResponse Execute(Vector<Observation> theRequestObservations);
        Vector<Observation> getTheRequestObservations();
        bool canHandleAutomatically(Object theReceiver);
        std::string handleAutomatically(QueryableAgent theAgent);


}
#endif	/* _AGENTMESSAGES_H */

