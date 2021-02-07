/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rlcomplogplayer;

import java.awt.BorderLayout;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author mradkie
 */
public class VizControl {
    private static VizControl theControls = null;
    private JFrame theControlFrame = null;
    private JPanel theControlPanel = null;
    private JSlider speedSlider = null;
    
    static final int SPEED_MIN = 0;
    static final int SPEED_MAX = 100;
    static final int SPEED_INIT = 20;
    
    private int speedValue = 0;
    
    public static VizControl getInstance(){
        if(theControls == null){
            theControls = new VizControl();
        }
        
        return theControls;
    }

    protected VizControl() {
        //dont want to do anything in here
    }

    public void setUpControls() {
        theControlFrame = new JFrame();
        theControlFrame.getContentPane().setLayout(new BorderLayout());
        theControlPanel = new JPanel();
        //create the slider and set it up
        speedSlider = new JSlider(JSlider.HORIZONTAL, SPEED_MIN, SPEED_MAX, SPEED_INIT);
        speedSlider.setMajorTickSpacing(25);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setPaintTicks(true);
      
        //create the labels
        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer(0), new JLabel("Slow"));
        labelTable.put(new Integer(SPEED_MAX), new JLabel("Fast"));
        speedSlider.setLabelTable(labelTable);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(new SliderListener());
        
        theControlPanel.add(speedSlider);
        theControlFrame.getContentPane().add(theControlPanel, "Center");
        theControlFrame.setSize(200,100);
        theControlFrame.setTitle("Control Panel");
        theControlFrame.setLocation(1000, 500);
        theControlFrame.setVisible(true);
        
    }
    
    public int getSpeedValue(){
        return speedValue = (int)speedSlider.getValue();
    }
    
    class SliderListener implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                speedValue = (int) source.getValue();
            }
        }
    }
}
