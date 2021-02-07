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
.build-conf: ${BUILD_SUBPROJECTS} dist/Release/libmines.dylib

dist/Release/libmines.dylib: ${OBJECTFILES}
	${MKDIR} -p dist/Release
	${LINK.cc} -dynamiclib -install_name libmines.dylib -o dist/Release/libmines.dylib -fPIC ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/_ext/Users/btanner/Documents/Java-Projects/netBeans/environments/mines/../../includes/ParameterHolder.o: ../../includes/ParameterHolder.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/Users/btanner/Documents/Java-Projects/netBeans/environments/mines/../../includes
	$(COMPILE.cc) -O2 -fPIC  -o ${OBJECTDIR}/_ext/Users/btanner/Documents/Java-Projects/netBeans/environments/mines/../../includes/ParameterHolder.o ../../includes/ParameterHolder.cpp

${OBJECTDIR}/mines.o: mines.c 
	${MKDIR} -p ${OBJECTDIR}
	$(COMPILE.c) -O2 -fPIC  -o ${OBJECTDIR}/mines.o mines.c

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf:
	${RM} -r build/Release
	${RM} dist/Release/libmines.dylib

# Subprojects
.clean-subprojects:
