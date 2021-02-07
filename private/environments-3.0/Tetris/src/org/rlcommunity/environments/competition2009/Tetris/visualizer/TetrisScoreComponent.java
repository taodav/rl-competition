// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TetrisScoreComponent.java

package org.rlcommunity.environments.competition2009.Tetris.visualizer;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import rlVizLib.general.TinyGlue;
import rlVizLib.visualization.SelfUpdatingVizComponent;
import rlVizLib.visualization.VizComponentChangeListener;

// Referenced classes of package org.rlcommunity.visualizers.competition2009.Tetris:
//            TetrisVisualizer

public class TetrisScoreComponent
    implements SelfUpdatingVizComponent, Observer
{

    public TetrisScoreComponent(TetrisVisualizer ev)
    {
        tetVis = null;
        lastScore = 0;
        lastUpdateTimeStep = -1;
        tetVis = ev;
        ev.getGlueState().addObserver(this);
        lastScore = -1;
    }

    public void render(Graphics2D g)
    {
        tetVis.updateAgentState(false);
        Font f = new Font("Verdana", 0, 8);
        g.setFont(f);
        g.setColor(Color.BLACK);
        java.awt.geom.AffineTransform saveAT = g.getTransform();
        g.scale(0.01D, 0.01D);
        g.drawString((new StringBuilder()).append("Lines: ").append(tetVis.getScore()).toString(), 0.0F, 10F);
        g.drawString((new StringBuilder()).append("E/S/T: ").append(tetVis.getEpisodeNumber()).append("/").append(tetVis.getTimeStep()).append("/").append(tetVis.getTotalSteps()).toString(), 0.0F, 20F);
        g.drawString((new StringBuilder()).append("CurrentPiece: ").append(tetVis.getCurrentPiece()).toString(), 0.0F, 30F);
        g.setTransform(saveAT);
    }

    public void setVizComponentChangeListener(VizComponentChangeListener theChangeListener)
    {
        this.theChangeListener = theChangeListener;
    }

    public void update(Observable o, Object arg)
    {
        if(theChangeListener != null)
            theChangeListener.vizComponentChanged(this);
    }

    private TetrisVisualizer tetVis;
    int lastScore;
    private int lastUpdateTimeStep;
    private VizComponentChangeListener theChangeListener;
}
