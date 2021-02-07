#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <assert.h>
#include "Glue_utilities.h"
#include "SarsaAgent.h"

#include<iostream>
#include "AgentMessages.h"

int numActions=1;
Action action;
void agent_init(const Task_specification task_spec)
{  
    
  task_spec_struct tss;					/*declare task_spec_struct*/
  srand(0);/*seed the randomness*/
  
  assert (task_spec != 0);
  parse_task_spec(task_spec, &tss);		/*Parsing task_specification*/	

/*allocating memory for one Action*/
  action.numInts     =  tss.num_discrete_action_dims;
  action.intArray    = (int*)malloc(sizeof(int)*action.numInts);
  action.numDoubles  = tss.num_continuous_action_dims;
  action.doubleArray = (double*)malloc(sizeof(double)*action.numDoubles);
  
    std::cerr<<"\tSarsa agent thinks there are"<<action.numInts<<" actions"<<std::endl;
    numActions=(int)tss.action_maxs[0];
  

  free(tss.obs_types);
  free(tss.obs_mins);
  free(tss.obs_maxs);

  free(tss.action_types);
  free(tss.action_mins);
  free(tss.action_maxs);

}

Action agent_start(Observation o)
{	
  unsigned int i = 0;
    
  /*pick the next action. Note: the policy is frozen internally. We haven't written an egreedy
  *and a greedy method like in sample_agent.c The epsilon is removed within the egreedy method if
  *the policy is frozen*/
  action.intArray[0] = rand()%numActions;


  return action;	
}


Action agent_step(Reward r, Observation o)
{
  action.intArray[0] = rand()%numActions;

 return action;	
}

void agent_end(Reward r)
{ 
}

void agent_cleanup(){
/*free all the memory*/
  free(action.intArray);

/*clear all values in the actions*/
  action.numInts     = 0;
  action.numDoubles  = 0;
  action.intArray    = 0;
  action.doubleArray = 0;

}

Message agent_message(const Message theMessage){
            	AgentMessages theMessageObject = AgentMessageParser::parseMessage(theMessage);
  return "This agent does not respond to any messages.";
//		} catch (NotAnRLVizMessageException e) {
//			System.err.println("Someone sent EnvironmentShell a message that wasn't RL-Viz compatible");
//			return "I only respond to RL-Viz messages!";
//		}



//		if(theMessageObject.canHandleAutomatically(null))
//			return theMessageObject.handleAutomatically(this);

//		System.out.println("We need some code written in Agent Message for DynaSars!");
//		Thread.dumpStack();
//		return null;
//	}
    
  /*no messages currently implemented*/
}

void agent_freeze(){
  /*sets the agent to freeze mode*/
}

