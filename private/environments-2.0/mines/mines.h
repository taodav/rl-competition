#ifndef mines_h
#define mines_h

#include <Environment_common.h>
extern "C"{
/* Mines utility functions */
void env_print(const char* header, RL_abstract_type* data);
int getPosition();
void getNextPosition(Action a);
Reward getReward();

const char* env_getDefaultParameters();
void env_setDefaultParameters(const char* theParamString);
}
#endif
