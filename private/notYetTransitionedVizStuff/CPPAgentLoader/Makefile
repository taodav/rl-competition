BIN_PATH=../../bin
OUT_PATH=../../libraries
INCLUDE_PATH=../../includes
SRC_PATH=src

agentShellClasses=../agentShell/build/classes
pathBackHere=../../../CPPAgentLoader

ifndef JAVA_HOME
        JAVA_HOME=/Library/Java/Home
endif

CC=g++

LIB_HEADERS=agentShell_LocalCPlusPlusAgentLoader.h agentShell_JNIAgent.h

CPPAGENT.dylib: $(LIB_HEADERS)
	g++ -o $(OUT_PATH)/CPPAGENT.dylib -dynamiclib -fPIC -I $(JAVA_HOME)/include -I../../includes -I../../RL-Glue/RL-Glue $(SRC_PATH)/CPlusPlusAgentLoader.cpp $(INCLUDE_PATH)/ParameterHolder.cpp
	rm -f $(SRC_PATH)/agentShell_LocalCPlusPlusAgentLoader.h
	rm -f $(SRC_PATH)/agentShell_JNIAgent.h

agentShell_LocalCPlusPlusAgentLoader.h:
	cd $(agentShellClasses) && javah agentShell.JNIAgent && mv agentShell_JNIAgent.h $(pathBackHere)/$(SRC_PATH)

agentShell_JNIAgent.h:
	cd $(agentShellClasses) && javah agentShell.LocalCPlusPlusAgentLoader && mv agentShell_LocalCPlusPlusAgentLoader.h $(pathBackHere)/$(SRC_PATH)

clean:
	rm -f $(OUT_PATH)/CPPAGENT.dylib
	