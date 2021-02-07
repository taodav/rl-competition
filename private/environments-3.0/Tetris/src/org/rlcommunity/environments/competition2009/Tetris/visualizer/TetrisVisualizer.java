// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TetrisVisualizer.java

package org.rlcommunity.environments.competition2009.Tetris.visualizer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.Vector;
import javax.swing.*;
import org.rlcommunity.environments.competition2009.Tetris.messages.TetrisStateRequest;
import org.rlcommunity.environments.competition2009.Tetris.messages.TetrisStateResponse;
import org.rlcommunity.rlglue.codec.types.Observation;
import rlVizLib.general.TinyGlue;
import rlVizLib.visualization.AbstractVisualizer;
import rlVizLib.visualization.interfaces.DynamicControlTarget;

// Referenced classes of package org.rlcommunity.visualizers.competition2009.Tetris:
//            TetrisBlocksComponent, TetrisScoreComponent

public class TetrisVisualizer extends AbstractVisualizer
    implements ActionListener
{

    public boolean printGrid()
    {
        if(printGridCheckBox != null)
            return printGridCheckBox.isSelected();
        else
            return false;
    }

    public TetrisVisualizer(TinyGlue theGlueState, DynamicControlTarget theControlTarget)
    {
        currentState = null;
        this.theGlueState = null;
        lastUpdateTimeStep = -1;
        printGridCheckBox = null;
        saveButton = null;
        loadButton = null;
        this.theControlTarget = null;
        lastSaveIndex = -1;
        forceBlocksRefresh = false;
        this.theGlueState = theGlueState;
        this.theControlTarget = theControlTarget;
        rlVizLib.visualization.SelfUpdatingVizComponent theBlockVisualizer = new TetrisBlocksComponent(this);
        rlVizLib.visualization.SelfUpdatingVizComponent theTetrlaisScoreViz = new TetrisScoreComponent(this);
        addVizComponentAtPositionWithSize(theBlockVisualizer, 0.0D, 0.10000000000000001D, 1.0D, 0.90000000000000002D);
        addVizComponentAtPositionWithSize(theTetrlaisScoreViz, 0.0D, 0.0D, 1.0D, 0.29999999999999999D);
        addDesiredExtras();
    }

    protected void addDesiredExtras()
    {
        addPreferenceComponents();
    }

    public void checkCoreData()
    {
        if(currentState == null)
            currentState = TetrisStateRequest.Execute();
    }

    protected void addPreferenceComponents()
    {
        printGridCheckBox = new JCheckBox();
        saveButton = new JButton();
        saveButton.setText("Save");
        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.setEnabled(false);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        loadButton.setAlignmentX(0.5F);
        saveButton.setAlignmentX(0.5F);
        if(theControlTarget != null)
        {
            Vector<Component> newComponents = new Vector<Component>();
            JLabel tetrisPrefsLabel = new JLabel("Tetris Visualizer Preferences: ");
            JLabel printGridLabel = new JLabel("Draw Grid");
            tetrisPrefsLabel.setAlignmentX(0.5F);
            printGridLabel.setAlignmentX(0.5F);
            JPanel gridPrefPanel = new JPanel();
            gridPrefPanel.add(printGridLabel);
            gridPrefPanel.add(printGridCheckBox);
            newComponents.add(tetrisPrefsLabel);
            newComponents.add(saveButton);
            newComponents.add(loadButton);
            newComponents.add(gridPrefPanel);
            theControlTarget.addControls(newComponents);
        }
    }

    public void updateAgentState(boolean force)
    {
        int currentTimeStep = theGlueState.getTotalSteps();
        if(currentTimeStep != lastUpdateTimeStep || force)
        {
            currentState = TetrisStateRequest.Execute();
            lastUpdateTimeStep = currentTimeStep;
        }
    }

    public int getWidth()
    {
        checkCoreData();
        return currentState.getWidth();
    }

    public int getHeight()
    {
        checkCoreData();
        return currentState.getHeight();
    }

    public int getScore()
    {
        return currentState.getScore();
    }

    public int[] getWorld()
    {
        checkCoreData();
        return currentState.getWorld();
    }

    public int getEpisodeNumber()
    {
        return theGlueState.getEpisodeNumber();
    }

    public int getTimeStep()
    {
        return theGlueState.getTimeStep();
    }

    public int getTotalSteps()
    {
        return theGlueState.getTotalSteps();
    }

    public int getCurrentPiece()
    {
        checkCoreData();
        return currentState.getCurrentPiece();
    }

    public TinyGlue getGlueState()
    {
        return theGlueState;
    }

    public void drawObs(Observation tetrisObs)
    {
        System.out.println((new StringBuilder()).append("STEP: ").append(theGlueState.getTotalSteps()).toString());
        int index = 0;
        for(int i = 0; i < currentState.getHeight(); i++)
        {
            for(int j = 0; j < currentState.getWidth(); j++)
            {
                index = i * currentState.getWidth() + j;
                System.out.print(tetrisObs.intArray[index]);
            }

            System.out.print("\n");
        }

    }

    public String getName()
    {
        return "Tetris 1.0 (DEV)";
    }

    public boolean getForceBlocksRefresh()
    {
        return forceBlocksRefresh;
    }

    public void setForceBlocksRefresh(boolean newValue)
    {
        forceBlocksRefresh = newValue;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
    }

    private TetrisStateResponse currentState;
    private TinyGlue theGlueState;
    private int lastUpdateTimeStep;
    JCheckBox printGridCheckBox;
    JButton saveButton;
    JButton loadButton;
    DynamicControlTarget theControlTarget;
    int lastSaveIndex;
    boolean forceBlocksRefresh;
}
