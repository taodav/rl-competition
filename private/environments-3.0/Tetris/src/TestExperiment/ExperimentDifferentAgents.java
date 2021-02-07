// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExperimentDifferentAgents.java

package TestExperiment;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rlcommunity.rlglue.codec.RLGlue;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.environmentShell.EnvShellLoadRequest;

// Referenced classes of package TestExperiment:
//            RLGlueThread, EnvThread, AgentThread

public class ExperimentDifferentAgents
{

    public ExperimentDifferentAgents()
    {
    }

    public static void main(String args[])
    {
        Thread rlgluethread = new RLGlueThread();
        rlgluethread.start();
        EnvThread envthread = new EnvThread();
        envthread.start();
        AgentThread agentthread = new AgentThread();
        agentthread.start();
        double scores[] = new double[5];
        for(int it = 0; it < 6; it++)
        {
            String theEnvString = "GeneralizedTetrisForTest - Java";
            ParameterHolder p = new ParameterHolder();
            p.addIntegerParam("Width", Integer.valueOf(10));
            p.addIntegerParam("Height", Integer.valueOf(25));
            p.addDoubleParam("LongBlockWeight", Double.valueOf(1.0D));
            p.addDoubleParam("SquareBlockWeight", Double.valueOf(1.0D));
            p.addDoubleParam("TriBlockWeight", Double.valueOf(1.0D));
            p.addDoubleParam("SBlockWeight", Double.valueOf(1.0D));
            p.addDoubleParam("ZBlockWeight", Double.valueOf(1.0D));
            p.addDoubleParam("LBlockWeight", Double.valueOf(1.0D));
            p.addDoubleParam("JBlockWeight", Double.valueOf(1.0D));
            p.addDoubleParam("rewardExponent", Double.valueOf(1.8D));
            p.addDoubleParam("Evilness", Double.valueOf(0.5D));
            p.addIntegerParam("EvilAgentType", Integer.valueOf(it));
            p.addDoubleParam("BenchmarkScore", Double.valueOf(0.0D));
            EnvShellLoadRequest.Execute(theEnvString, p);
            RLGlue.RL_init();
            int stepsRemaining = 50000;
            int totalEpisodes = 0;
            double returnThisMDP = 0.0D;
            while(stepsRemaining > 0) 
            {
                RLGlue.RL_episode(stepsRemaining);
                int thisStepCount = RLGlue.RL_num_steps();
                stepsRemaining -= thisStepCount;
                returnThisMDP += RLGlue.RL_return();
                totalEpisodes++;
            }
            System.out.println((new StringBuilder()).append("EvilAgentType ").append(it).append(" completed with ").append(totalEpisodes).append(" episodes").toString());
            scores[it] = returnThisMDP;
            RLGlue.RL_cleanup();
        }

        System.out.println(Arrays.toString(scores));
        try
        {
            Thread.sleep(1000L);
        }
        catch(InterruptedException ex)
        {
            Logger.getLogger(ExperimentDifferentAgents.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
}
