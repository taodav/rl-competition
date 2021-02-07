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
CC=gcc
CCC=g++
CXX=g++
FC=

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=build/Release/GNU-MacOSX

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
.build-conf: ${BUILD_SUBPROJECTS} dist/Release/libminesAgent.dylib

dist/Release/libminesAgent.dylib: ${OBJECTFILES}
	${MKDIR} -p dist/Release
	${LINK.cc} -dynamiclib -install_name libminesAgent.dylib -o dist/Release/libminesAgent.dylib ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../includes/ParameterHolder.o: ../../includes/ParameterHolder.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../includes
	$(COMPILE.cc) -O2 -o ${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../includes/ParameterHolder.o ../../includes/ParameterHolder.cpp

${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../RL-Glue/Utils/Glue_utilities.o: ../../RL-Glue/Utils/Glue_utilities.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../RL-Glue/Utils
	$(COMPILE.cc) -O2 -o ${OBJECTDIR}/_ext/Users/btanner/Documents/Java_Projects/netBeans/agents/minesAgent/../../RL-Glue/Utils/Glue_utilities.o ../../RL-Glue/Utils/Glue_utilities.cpp

${OBJECTDIR}/SarsaAgent.o: SarsaAgent.c 
	${MKDIR} -p ${OBJECTDIR}
	$(COMPILE.c) -O2 -o ${OBJECTDIR}/SarsaAgent.o SarsaAgent.c

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf:
	${RM} -r build/Release
	${RM} dist/Release/libminesAgent.dylib

# Subprojects
.clean-subprojects:
