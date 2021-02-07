/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.phoneHomeInterface;

/**
 *
 * @author btanner
 */
public class MDPDetails {
private String jarFileName;
private int MDPId;
private String key;
private int totalSteps;
private RLEvent whichEvent;
private int logRatio = 1;
private final String MDPClassName;

public MDPDetails(String jarFileName, String MDPClassName,RLEvent whichEvent, int MDPId, String key, int totalSteps){
        this.jarFileName=jarFileName;
        this.MDPId=MDPId;
        this.key=key;
        this.totalSteps=totalSteps;
        this.whichEvent=whichEvent;
        this.MDPClassName=MDPClassName;
    }

    public MDPDetails(String jarFileName, String MDPClassName, RLEvent whichEvent, int MDPId, String key, int totalSteps, int logRatio){
        this.jarFileName=jarFileName;
        this.MDPId=MDPId;
        this.key=key;
        this.totalSteps=totalSteps;
        this.whichEvent=whichEvent;
        this.logRatio=logRatio;
        this.MDPClassName=MDPClassName;
    }

    
    public RLEvent getEvent(){return whichEvent;}
    public String getJarFileName(){return jarFileName;}
    public int getMDPId(){return MDPId;}
    public String getKey(){return key;}
    public int getTotalSteps() {return totalSteps;}
    //we only want to log every kth episode..
    public int getLogRatio(){return logRatio;}
    public void setLogRatio(int newRatio){this.logRatio=newRatio;}
    public String getMDPClassName(){return MDPClassName;}
}
