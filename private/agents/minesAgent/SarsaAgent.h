#ifndef SarsaAgent_h
#define SarsaAgent_h

#include <RL_common.h>
extern "C"{
void agent_init(const Task_specification task_spec);
Action agent_start(Observation o);
Action agent_step(Reward r, Observation o);
void agent_end(Reward r);
void agent_cleanup();
Message agent_message(const Message msg);
int egreedy(Observation o);
}
#endif
