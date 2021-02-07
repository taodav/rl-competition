/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.proving;

import org.rlcommunity.competition.phoneHomeInterface.RLEvent;

/**
 *
 * @author mradkie
 */
public interface ViewInterface {

    public void setNumberRunsAvailable(RLEvent theEvent, int num);
    public void setVisibilities(boolean allowed[], boolean validAuthToken);
    public void disableAll();
    public void dieWithResultsMessage(String theMessage);
    public void logMessage(String theMessage);
    public void logError(String theMessage);
    public void updateView(String currentTime, String estimateTimeRemaining,int stepsPerMDP,int numMDPs,int stepsThisMDP,int totalRunSteps);
}
