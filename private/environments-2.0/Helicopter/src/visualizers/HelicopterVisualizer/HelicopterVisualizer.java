/* RLViz Helicopter Domain Visualizer  for RL - Competition 
* Copyright (C) 2007, Brian Tanner
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. */
package visualizers.HelicopterVisualizer;

import Helicopter.messages.HelicopterRangeRequest;
import Helicopter.messages.HelicopterRangeResponse;
import Helicopter.messages.HelicopterStateRequest;
import Helicopter.messages.HelicopterStateResponse;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import rlVizLib.visualization.interfaces.GlueStateProvider;
import rlVizLib.general.TinyGlue;
import rlVizLib.glueProxy.RLGlueProxy;
import rlVizLib.visualization.AbstractVisualizer;
import rlVizLib.visualization.GenericScoreComponent;
import rlVizLib.visualization.VizComponent;
import rlVizLib.visualization.interfaces.DynamicControlTarget;
import rlglue.types.State_key;

public class HelicopterVisualizer extends AbstractVisualizer implements GlueStateProvider, ActionListener {

    private HelicopterStateResponse currentState = null;
    private HelicopterRangeResponse currentRange = null;
    int lastTimeUpdate = -1;
    private TinyGlue theGlueState = null;
    private DynamicControlTarget theControlTarget = null;
    javax.swing.JButton saveButton = null;
    javax.swing.JButton loadButton = null;

    public HelicopterVisualizer(TinyGlue theGlueState, DynamicControlTarget theControlTarget) {
        super();
        this.theGlueState = theGlueState;
        this.theControlTarget = theControlTarget;
        VizComponent theCounterViz = new GenericScoreComponent(this);
        VizComponent theEquilizerViz = new HelicopterEquilizerComponent(this);

        addVizComponentAtPositionWithSize(theEquilizerViz, 0, 0, 1.0, 1.0);
        addVizComponentAtPositionWithSize(theCounterViz, 0, 0, 1.0, 0.5);
        updateAgentState(false);
        updateParamRanges();
        addDesiredExtras();

    }

    protected void addDesiredExtras() {
        addPreferenceComponents();
    }

    public void addPreferenceComponents() {
        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.setEnabled(false);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        if (theControlTarget != null) {
            Vector<Component> newComponents = new Vector<Component>();
            JLabel HeliControlLabel = new JLabel("Helicopter Controls");
            HeliControlLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            newComponents.add(HeliControlLabel);
            newComponents.add(saveButton);
            newComponents.add(loadButton);

            theControlTarget.addControls(newComponents);
        }
    }

    public boolean updateAgentState(boolean force) {
        int currentStep = theGlueState.getTotalSteps();

        if (currentStep != lastTimeUpdate || force) {
            currentState = HelicopterStateRequest.Execute();
            lastTimeUpdate = currentStep;
            return true;
        }
        return false;
    }

    public boolean updateParamRanges() {
        if (currentRange == null) {
            currentRange = HelicopterRangeRequest.Execute();
            return true;
        }
        return false;
    }

    // getters here

    public double[] getState() {
        return currentState.getState();
    }

    public double getMinAt(int i) {
        return currentRange.getMinAt(i);
    }

    public double getMaxAt(int i) {
        return currentRange.getMaxAt(i);
    }

    public double[] getMins() {
        return currentRange.getMins();
    }

    public double[] getMaxs() {
        return currentRange.getMaxs();
    }

    public TinyGlue getTheGlueState() {
        return theGlueState;
    }

    public String getName() {
        return "Helicopter Hovering 1.0 (DEV)";
    }
    int lastSaveIndex = -1;
    boolean forceDrawRefresh = false;

    public boolean getForceDrawRefresh() {
        return forceDrawRefresh;
    }

    public void setForceDrawRefresh(boolean newValue) {
        forceDrawRefresh = newValue;
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == loadButton) {
            State_key k = new State_key(1, 0);
            k.intArray[0] = lastSaveIndex;

            RLGlueProxy.RL_set_state(k);
            setForceDrawRefresh(true);
        }
        if (event.getSource() == saveButton) {
            loadButton.setEnabled(true);
            State_key k = RLGlueProxy.RL_get_state();
            lastSaveIndex = k.intArray[0];
        }
    }

    //This is the one required from RLVizLib, ours has a forcing parameter.  Should update the VizLib

    public void updateAgentState() {
        updateAgentState(false);
    }

    TinyGlue getGlueState() {
        return theGlueState;
    }

}