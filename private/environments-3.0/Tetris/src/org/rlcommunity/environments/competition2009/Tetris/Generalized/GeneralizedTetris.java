// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GeneralizedTetris.java

package org.rlcommunity.environments.competition2009.Tetris.Generalized;

import org.rlcommunity.environments.competition2009.Tetris.Tetris;
import org.rlcommunity.environments.competition2009.Tetris.visualizer.GeneralizedTetrisVisualizer;
import rlVizLib.general.ParameterHolder;
import rlVizLib.utilities.UtilityShop;

// Referenced classes of package org.rlcommunity.environments.competition2009.GeneralizedTetris:
//            DetailsProvider, GenTetrisParamData

public class GeneralizedTetris extends Tetris
{

    public static ParameterHolder getDefaultParameters()
    {
        ParameterHolder p = new ParameterHolder();
        UtilityShop.setVersionDetails(p, new DetailsProvider());
        p.addIntegerParam("ParameterSet [0,19]", Integer.valueOf(0));
        p.setAlias("pnum", "ParameterSet [0,19]");
        return p;
    }

    public GeneralizedTetris(ParameterHolder P)
    {
        int ParamSet = P.getIntegerParam("pnum");
        GenTetrisParamData theParamSetter = new GenTetrisParamData();
        gameState = theParamSetter.setParameters(ParamSet);
    }

    public String getVisualizerClassName()
    {
        return GeneralizedTetrisVisualizer.class.getName();
    }
}
