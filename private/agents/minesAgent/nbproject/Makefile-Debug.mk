#
# Gererated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add custumized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
CCADMIN=CCadmin
RANLIB=ranlib
CC=g++
CCC=g++
CXX=g++
FC=

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=build/Debug/GNU-MacOSX

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../includes/ParameterHolder.o \
	${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../RL-Glue/Utils/Glue_utilities.o \
	${OBJECTDIR}/SarsaAgent.o

# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS} dist/Debug/libminesAgent.dylib

dist/Debug/libminesAgent.dylib: ${OBJECTFILES}
	${MKDIR} -p dist/Debug
	${LINK.cc} -dynamiclib -install_name libminesAgent.dylib -o dist/Debug/libminesAgent.dylib ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../includes/ParameterHolder.o: ../../includes/ParameterHolder.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../includes
	$(COMPILE.cc) -g3 -gdwarf-2 -I../../RL-Glue/RL-Glue -I../../RL-Glue/RL-Glue/Utils -I../../rlViz/RLVizLibCPP/dist/Debug/GNU-MacOSX -I../../rlViz/RLVizLibCPP/src -o ${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../includes/ParameterHolder.o ../../includes/ParameterHolder.cpp

${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../RL-Glue/Utils/Glue_utilities.o: ../../RL-Glue/Utils/Glue_utilities.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../RL-Glue/Utils
	$(COMPILE.cc) -g3 -gdwarf-2 -I../../RL-Glue/RL-Glue -I../../RL-Glue/RL-Glue/Utils -I../../rlViz/RLVizLibCPP/dist/Debug/GNU-MacOSX -I../../rlViz/RLVizLibCPP/src -o ${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../RL-Glue/Utils/Glue_utilities.o ../../RL-Glue/Utils/Glue_utilities.cpp

${OBJECTDIR}/SarsaAgent.o: SarsaAgent.c 
	${MKDIR} -p ${OBJECTDIR}
	$(COMPILE.c) -g3 -gdwarf-2 -I../../RL-Glue/RL-Glue -I../../RL-Glue/Utils -I../../includes -I../../rlViz/RLVizLibCPP/src -o ${OBJECTDIR}/SarsaAgent.o SarsaAgent.c

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf:
	${RM} -r build/Debug
	${RM} dist/Debug/libminesAgent.dylib

# Subprojects
.clean-subprojects:
