/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rlcomplogplayer;

import javax.swing.JPanel;

/**
 *
 * @author mradkie
 */
public abstract class AbstractVizPanel extends JPanel{  
    abstract public int getDesiredX();
    abstract public int getDesiredY();
    abstract public boolean update();
    abstract public String getTeamName();
    abstract protected void cleanup() throws Throwable;

    abstract public int getMDPNum();
    abstract public double getReward();
    abstract public int getCurSteps();
    abstract public int getMaxSteps();
    
}
