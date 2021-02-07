// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CalcScoreForParams.java

package TestExperiment;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rlcommunity.rlglue.codec.RLGlue;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.environmentShell.EnvShellLoadRequest;

// Referenced classes of package TestExperiment:
//            RLGlueThread, EnvThread, AgentThread, Experiment

public class CalcScoreForParams
{

    public CalcScoreForParams()
    {
    }

    public static void main(String args[])
    {
        if(args.length < 12)
        {
            System.err.println("Need more parameters.");
            System.exit(1);
        }
        Thread rlgluethread = new RLGlueThread();
        rlgluethread.start();
        EnvThread envthread = new EnvThread();
        envthread.start();
        AgentThread agentthread = new AgentThread();
        agentthread.start();
        double evilness = Double.parseDouble(args[10]);
        String theEnvString = "GeneralizedTetrisForTest - Java";
        ParameterHolder p = new ParameterHolder();
        p.addIntegerParam("Width", Integer.valueOf(Integer.parseInt(args[0])));
        p.addIntegerParam("Height", Integer.valueOf(Integer.parseInt(args[1]) + Integer.parseInt(args[0])));
        p.addDoubleParam("rewardExponent", Double.valueOf(Double.parseDouble(args[2])));
        p.addDoubleParam("LongBlockWeight", Double.valueOf(Double.parseDouble(args[3])));
        p.addDoubleParam("SquareBlockWeight", Double.valueOf(Double.parseDouble(args[4])));
        p.addDoubleParam("TriBlockWeight", Double.valueOf(Double.parseDouble(args[5])));
        p.addDoubleParam("SBlockWeight", Double.valueOf(Double.parseDouble(args[6])));
        p.addDoubleParam("ZBlockWeight", Double.valueOf(Double.parseDouble(args[7])));
        p.addDoubleParam("LBlockWeight", Double.valueOf(Double.parseDouble(args[8])));
        p.addDoubleParam("JBlockWeight", Double.valueOf(Double.parseDouble(args[9])));
        p.addDoubleParam("Evilness", Double.valueOf(Double.parseDouble(args[10])));
        p.addIntegerParam("EvilAgentType", Integer.valueOf(Integer.parseInt(args[11])));
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
        System.out.println((new StringBuilder()).append("Evilness ").append(evilness).append(" completed with ").append(totalEpisodes).append(" episodes").toString());
        RLGlue.RL_cleanup();
        try
        {
            Writer output = null;
            String text = Integer.toString((int)returnThisMDP);
            File file = new File("output.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write(text);
            output.close();
        }
        catch(IOException ex)
        {
            Logger.getLogger(CalcScoreForParams.class.getName()).log(Level.SEVERE, null, ex);
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
