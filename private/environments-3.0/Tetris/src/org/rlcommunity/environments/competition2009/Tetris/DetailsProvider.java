// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tetris.java

package org.rlcommunity.environments.competition2009.Tetris;

import rlVizLib.general.hasVersionDetails;

class DetailsProvider
    implements hasVersionDetails
{

    DetailsProvider()
    {
    }

    public String getName()
    {
        return "Tetris 1.0";
    }

    public String getShortName()
    {
        return "Tetris";
    }

    public String getAuthors()
    {
        return "Brian Tanner, Leah Hackman, Matt Radkie, Andrew Butcher";
    }

    public String getInfoUrl()
    {
        return "http://rl-library.googlecode.com";
    }

    public String getDescription()
    {
        return "Tetris problem from the reinforcement learning library.";
    }
}
