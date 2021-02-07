// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GeneralizedTetris.java

package org.rlcommunity.environments.competition2009.Tetris.Generalized;

import rlVizLib.general.hasVersionDetails;

class DetailsProvider
    implements hasVersionDetails
{

    DetailsProvider()
    {
    }

    public String getName()
    {
        return "Generalized Tetris 1.0";
    }

    public String getShortName()
    {
        return "RLC-Tetris";
    }

    public String getAuthors()
    {
        return "Brian Tanner, Leah Hackman, Matt Radkie, Andrew Butcher";
    }

    public String getInfoUrl()
    {
        return "http://rl-competition.org";
    }

    public String getDescription()
    {
        return "Generalized RL-Competition 2008 Java Version of the Tetris RL-Problem.";
    }
}
