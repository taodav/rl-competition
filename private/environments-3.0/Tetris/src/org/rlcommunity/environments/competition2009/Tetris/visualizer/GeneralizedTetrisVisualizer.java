// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GeneralizedTetrisVisualizer.java

package org.rlcommunity.environments.competition2009.Tetris.visualizer;

import rlVizLib.general.TinyGlue;
import rlVizLib.visualization.interfaces.DynamicControlTarget;

// Referenced classes of package org.rlcommunity.visualizers.competition2009.Tetris:
//            TetrisVisualizer

public class GeneralizedTetrisVisualizer extends TetrisVisualizer
{

    public GeneralizedTetrisVisualizer(TinyGlue theGlueState, DynamicControlTarget theControlTarget)
    {
        super(theGlueState, theControlTarget);
    }

    protected void addDesiredExtras()
    {
        addPreferenceComponents();
    }

    public String getName()
    {
        return "Tetris 1.0";
    }
}
