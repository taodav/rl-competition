// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Experiment.java

package TestExperiment;


// Referenced classes of package TestExperiment:
//            RLGlueThread, EnvThread, VizThread, AgentThread

public class Experiment
{

    public Experiment()
    {
    }

    public static void main(String args[])
    {
        Thread rlgluethread = new RLGlueThread();
        rlgluethread.start();
        EnvThread envthread = new EnvThread();
        envthread.start();
        VizThread vizthread = new VizThread();
        vizthread.start();
        AgentThread agentthread = new AgentThread();
        agentthread.start();
    }
}
