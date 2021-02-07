/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeE.MainPackage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Observation;

/**
 *
 * @author btanner
 */
public class PolyProblemTypeEState {
    /*State Variables*/

    NumberFormat formatter = new DecimalFormat("###.##");
    Random myRand=new Random();
    /*Other Variables and Constants */
    double[] means=null;
    double[] variances=null;
    
    //This is random
    public double[] theState=new double[6];

    public void setProblemParams(double[] means, double[] variances) {
        this.means=means;
        this.variances=variances;
    }
    
    
    public PolyProblemTypeEState() {
        means = new double[6];
        variances = new double[6];
    }

    public PolyProblemTypeEState(PolyProblemTypeEState stateToCopy) {
        means = new double[6];
        variances = new double[6];
        
        for(int i=0;i<6;i++){
            means[i]=stateToCopy.means[i];
            variances[i]=stateToCopy.variances[i];
        }
        
    }

    public void generateNextState(){
        for(int i=0;i<6;i++)theState[i]=generateAState(i);
    }
    
    public Observation makeObservation(){
        Observation theObs=new Observation(0,6);
        for(int i=0;i<theState.length;i++)
        theObs.doubleArray[i]=theState[i];
        
        return theObs;
    }
    public double generateAState(int whichOBs){
        double theNextGaussian=myRand.nextGaussian();
        double withVariance=theNextGaussian*variances[whichOBs];
        double sampledObservation=(withVariance+means[whichOBs]);
    
        //Cut it
        if(sampledObservation<0.0d)sampledObservation=0.0d;
        if(sampledObservation>1.0d)sampledObservation=1.0d;

        return sampledObservation;
    }

    String stringSerialize() {
        return "_0_";
    }

}
