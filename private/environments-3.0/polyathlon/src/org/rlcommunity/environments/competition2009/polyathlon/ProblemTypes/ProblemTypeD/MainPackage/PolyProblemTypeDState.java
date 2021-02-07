/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeD.MainPackage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 *
 * @author btanner
 */
public class PolyProblemTypeDState {
    /*State Variables*/

    NumberFormat formatter = new DecimalFormat("###.##");
    Random myRand=new Random();
    /*Other Variables and Constants */
    double[] means=null;
    double[] variances=null;

    public void setProblemParams(double[] means, double[] variances) {
        this.means=means;
        this.variances=variances;
    }
    
    
    public PolyProblemTypeDState() {
    }

    public PolyProblemTypeDState(PolyProblemTypeDState stateToCopy) {
        means = new double[4];
        variances = new double[4];
        
        for(int i=0;i<4;i++){
            means[i]=stateToCopy.means[i];
            variances[i]=stateToCopy.variances[i];
        }
        
    }


    public double sampleReward(int whichAction){
        double theNextGaussian=myRand.nextGaussian();
        double withVariance=theNextGaussian*variances[whichAction];
        double sampledReward=(withVariance+means[whichAction]);

        //Cut it
        if(sampledReward<0.0d)sampledReward=0.0d;
        if(sampledReward>1.0d)sampledReward=1.0d;

        return sampledReward;
    }

    String stringSerialize() {
        return "_0_";
    }

}
