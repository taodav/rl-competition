// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TetrisBlocksComponent.java

package org.rlcommunity.environments.competition2009.Tetris.visualizer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;
import rlVizLib.general.TinyGlue;
import rlVizLib.visualization.SelfUpdatingVizComponent;
import rlVizLib.visualization.VizComponentChangeListener;

// Referenced classes of package org.rlcommunity.visualizers.competition2009.Tetris:
//            TetrisVisualizer

public class TetrisBlocksComponent
    implements SelfUpdatingVizComponent, Observer
{

    public TetrisBlocksComponent(TetrisVisualizer ev)
    {
        tetVis = null;
        lastUpdateTimeStep = -1;
        tetVis = ev;
        ev.getGlueState().addObserver(this);
    }

    public void render(Graphics2D g)
    {
        int numCols = tetVis.getWidth();
        int numRows = tetVis.getHeight();
        int tempWorld[] = tetVis.getWorld();
        int DABS = 10;
        int scaleFactorX = numCols * DABS;
        int scaleFactorY = numRows * DABS;
        int w = DABS;
        int h = DABS;
        int x = 0;
        int y = 0;
        java.awt.geom.AffineTransform saveAT = g.getTransform();
        g.setColor(Color.GRAY);
        g.scale(1.0D / (double)scaleFactorX, 1.0D / (double)scaleFactorY);
        for(int i = 0; i < numRows; i++)
        {
            for(int j = 0; j < numCols; j++)
            {
                x = j * DABS;
                y = i * DABS;
                int thisBlockColor = tempWorld[i * numCols + j];
                if(thisBlockColor != 0)
                {
                    switch(thisBlockColor)
                    {
                    case 1: // '\001'
                        g.setColor(Color.PINK);
                        break;

                    case 2: // '\002'
                        g.setColor(Color.RED);
                        break;

                    case 3: // '\003'
                        g.setColor(Color.GREEN);
                        break;

                    case 4: // '\004'
                        g.setColor(Color.YELLOW);
                        break;

                    case 5: // '\005'
                        g.setColor(Color.LIGHT_GRAY);
                        break;

                    case 6: // '\006'
                        g.setColor(Color.ORANGE);
                        break;

                    case 7: // '\007'
                        g.setColor(Color.MAGENTA);
                        break;
                    }
                    g.fill3DRect(x, y, w, h, true);
                    continue;
                }
                g.setColor(Color.WHITE);
                Rectangle2D agentRect = new java.awt.geom.Rectangle2D.Double(x, y, w, h);
                if(tetVis.printGrid())
                    g.fill3DRect(x, y, w, h, true);
                else
                    g.fill(agentRect);
            }

        }

        g.setColor(Color.GRAY);
        g.drawRect(0, 0, DABS * numCols, DABS * numRows);
        g.setTransform(saveAT);
    }

    public void update(Observable o, Object arg)
    {
        if(theChangeListener != null)
        {
            tetVis.updateAgentState(false);
            theChangeListener.vizComponentChanged(this);
        }
    }

    public void setVizComponentChangeListener(VizComponentChangeListener theChangeListener)
    {
        this.theChangeListener = theChangeListener;
    }

    private TetrisVisualizer tetVis;
    private int lastUpdateTimeStep;
    private VizComponentChangeListener theChangeListener;
}
