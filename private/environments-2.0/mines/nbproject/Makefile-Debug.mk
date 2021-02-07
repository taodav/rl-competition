#
# Gererated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
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

# supported substrings of OSTYPE
# The following words must be uppercase
# If they are found in the uppercased OSTYPE variable
# the particular O/S matches
OSTYPE_LINUX  := LINUX
OSTYPE_MAC    := DARWIN
OSTYPE_CYGWIN := CYGWIN
OSTYPE_MINGW  := MSYS

ifeq ("$(OSTYPE)", "") 
	OSTYPE := $(shell uname | grep -i -E "(Darwin)|(Linux)|(CYGWIN)")
	OSTYPE := $(shell echo "$(OSTYPE)" | tr a-z A-Z )
else
	OSTYPE := $(shell echo -n "$(OSTYPE)" | tr a-z A-Z )
endif

ifeq ("$(OSTYPE)","$(OSTYPE_MAC)")
	SFLAGS := -dynamiclib
else 
	ifeq ("$(OSTYPE)","$(OSTYPE_LINUX)")
		SFLAGS := -dynamic -ldl
	else
		SFLAGS := -dynamiclib
	endif
endif

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=build/Debug/GNU-MacOSX

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/_ext/Users/btanner/Documents/Java-Projects/netBeans/environments/mines/../../includes/ParameterHolder.o \
	${OBJECTDIR}/mines.o

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
.build-conf: ${BUILD_SUBPROJECTS} dist/Debug/libmines.dylib

dist/Debug/libmines.dylib: ${OBJECTFILES}
	${MKDIR} -p dist/Debug
	${LINK.cc} $(SFLAGS) -install_name libmines.dylib -o dist/Debug/libmines.dylib -fPIC ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/_ext/Users/btanner/Documents/Java-Projects/netBeans/environments/mines/../../includes/ParameterHolder.o: ../../includes/ParameterHolder.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/Users/btanner/Documents/Java-Projects/netBeans/environments/mines/../../includes
	$(COMPILE.cc) -g -I../../RL-Glue/RL-Glue -I../../RL-Glue/RL-Glue/Utils -fPIC  -o ${OBJECTDIR}/_ext/Users/btanner/Documents/Java-Projects/netBeans/environments/mines/../../includes/ParameterHolder.o ../../includes/ParameterHolder.cpp

${OBJECTDIR}/mines.o: mines.c 
	${MKDIR} -p ${OBJECTDIR}
	$(COMPILE.c) -g -I../../RL-Glue/RL-Glue -I../../RL-Glue/RL-Glue/Utils -I../../includes -fPIC  -o ${OBJECTDIR}/mines.o mines.c

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf:
	${RM} -r build/Debug
	${RM} dist/Debug/libmines.dylib

# Subprojects
.clean-subprojects:
