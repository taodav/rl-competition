// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AgentThread.java

package TestExperiment;

import org.rlcommunity.environments.competition2009.Tetris.StandardTetrisAgent;
import org.rlcommunity.rlglue.codec.util.AgentLoader;

public class AgentThread extends Thread
{

    public AgentThread()
    {
    }

    public void run()
    {
        StandardTetrisAgent agent = new StandardTetrisAgent(5);
        AgentLoader agentloader = new AgentLoader(agent);
        agentloader.run();
    }
}
