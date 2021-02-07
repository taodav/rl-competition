/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.phoneHomeInterface;

/**
 *
 * @author btanner
 */
public class ResultDetails {
private String dataFileName;
private int MDPId;
private String key;

    public ResultDetails(String fileLocation, MDPDetails theMDP) {
        this.dataFileName=fileLocation;
        this.MDPId=theMDP.getMDPId();
        this.key=theMDP.getKey();
    }

    public ResultDetails(String jarFileName, int MDPId, String key){
        this.dataFileName=jarFileName;
        this.MDPId=MDPId;
        this.key=key;
    }
    
    public String getDataFileName(){return dataFileName;}
    public int getMDPId(){return MDPId;}
    public String getKey(){return key;}
}
