// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConsoleTrainer.java

package TestExperiment;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rlcommunity.rlglue.codec.RLGlue;

// Referenced classes of package TestExperiment:
//            RLGlueThread, EnvThread, AgentThread, Experiment, 
//            consoleTrainerHelper

public class ConsoleTrainer
{

    public ConsoleTrainer()
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
        int whichTrainingMDP = 0;
        for(int it = 0; it < 20; it++)
        {
            whichTrainingMDP = it;
            consoleTrainerHelper.loadTetris(whichTrainingMDP);
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
            System.out.println((new StringBuilder()).append("MDP ").append(it).append(" completed with ").append(totalEpisodes).append(" episodes, got ").append(returnThisMDP).append(" reward").toString());
            RLGlue.RL_cleanup();
        }

        try
        {
            Thread.sleep(1000L);
        }
        catch(InterruptedException ex)
        {
            Logger.getLogger(Experiment.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
}
