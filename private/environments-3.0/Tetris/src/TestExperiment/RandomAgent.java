// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RandomAgent.java

package TestExperiment;

import java.util.Random;
import org.rlcommunity.rlglue.codec.AgentInterface;
import org.rlcommunity.rlglue.codec.taskspec.TaskSpec;
import org.rlcommunity.rlglue.codec.taskspec.ranges.DoubleRange;
import org.rlcommunity.rlglue.codec.taskspec.ranges.IntRange;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;

public class RandomAgent
    implements AgentInterface
{

    public RandomAgent()
    {
        numInts = 1;
        numDoubles = 0;
        random = new Random();
        TSO = null;
    }

    public void agent_cleanup()
    {
    }

    public void agent_end(double d)
    {
    }

    public void agent_freeze()
    {
    }

    public void agent_init(String taskSpec)
    {
        TSO = new TaskSpec(taskSpec);
        action = new Action(TSO.getNumDiscreteActionDims(), TSO.getNumContinuousActionDims());
    }

    public String agent_message(String arg0)
    {
        return null;
    }

    public Action agent_start(Observation o)
    {
        randomify(action);
        return action;
    }

    public Action agent_step(double arg0, Observation o)
    {
        randomify(action);
        return action;
    }

    private void randomify(Action action)
    {
        for(int i = 0; i < TSO.getNumDiscreteActionDims(); i++)
        {
            IntRange thisActionRange = TSO.getDiscreteActionRange(i);
            action.intArray[i] = random.nextInt(thisActionRange.getRangeSize()) + thisActionRange.getMin();
        }

        for(int i = 0; i < TSO.getNumContinuousActionDims(); i++)
        {
            DoubleRange thisActionRange = TSO.getContinuousActionRange(i);
            action.doubleArray[i] = random.nextDouble() * thisActionRange.getRangeSize() + thisActionRange.getMin();
        }

    }

    private Action action;
    private int numInts;
    private int numDoubles;
    private Random random;
    TaskSpec TSO;
}
