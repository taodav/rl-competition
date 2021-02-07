/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rlcomplogplayer;

/**
 *
 * @author mradkie
 */
public abstract class AbstractLogPlayer {
    abstract public String getTeamName();
    abstract public boolean updateStep();
    abstract public double getReward();

}
