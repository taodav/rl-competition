// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GeneralizedTetrisForTest.java

package org.rlcommunity.environments.competition2009.Tetris.Generalized;

import org.rlcommunity.environments.competition2009.Tetris.Tetris;
import rlVizLib.general.ParameterHolder;
import rlVizLib.utilities.UtilityShop;

// Referenced classes of package org.rlcommunity.environments.competition2009.GeneralizedTetris:
//            DetailsProviderForTest

public class GeneralizedTetrisForTest extends Tetris
{

    public static ParameterHolder getDefaultParameters()
    {
        ParameterHolder p = new ParameterHolder();
        UtilityShop.setVersionDetails(p, new DetailsProviderForTest());
        p.addIntegerParam("ParameterSet [0,19]", Integer.valueOf(0));
        p.setAlias("pnum", "ParameterSet [0,19]");
        return p;
    }

    public GeneralizedTetrisForTest(ParameterHolder P)
    {
        super(P);
    }

    public String getVisualizerClassName()
    {
        return "org.rlcommunity.visualizers.competition2009.Tetris.GeneralizedTetrisVisualizer";
    }
}
