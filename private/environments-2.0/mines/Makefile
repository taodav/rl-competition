# supported substrings of OSTYPE
# The following words must be uppercase
# If they are found in the uppercased OSTYPE variable
# the particular O/S matches
OSTYPE_MAC    := DARWIN

ifeq ("$(OSTYPE)", "") 
	OSTYPE := $(shell uname | grep -i -E "(Darwin)|(Linux)|(CYGWIN)")
	OSTYPE := $(shell echo "$(OSTYPE)" | tr a-z A-Z )
else
	OSTYPE := $(shell echo -n "$(OSTYPE)" | tr a-z A-Z )
endif

ifeq ("$(OSTYPE)","$(OSTYPE_MAC)")
	SFLAGS := -dynamiclib
else 
	SFLAGS := -shared
endif

mines.dylib:
	g++ -o ../../libraries/envJars/mines.dylib $(SFLAGS) -fPIC -I../../includes -I../../RL-Glue/RL-Glue mines.c ../../includes/ParameterHolder.cpp