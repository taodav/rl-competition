/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SamplePolyathlon.Proving;

import SamplePolyathlon.ProblemTypeE.MainPackage.AbstractPolyProblemTypeE;
import rlglue.types.Observation;

/**
 *
 * @author Brian Tanner
 */
public class PolyPMDP63 extends AbstractPolyProblemTypeE {
    @Override
    public double generateReward(Observation o, int a) {
        double[] rewards=new double[4];
        
        double obsSum=(o.doubleArray[0]+o.doubleArray[1]+o.doubleArray[2]+o.doubleArray[3]);
        
        rewards[0]=Math.sin(o.doubleArray[3])*Math.cos(o.doubleArray[1]);
        
        if(obsSum>2.0)
            rewards[1]=5.0;
        else
            rewards[1]=-15.0;
        
        
        rewards[2]=Math.random();
        rewards[3]=o.doubleArray[3];
        
        return rewards[a];
    }
    
    public PolyPMDP63(){
        super();
        double[] means=new double[4];
        double[] vars=new double[4];
        
        means[0]=.5;
        means[1]=.8;
        means[2]=.6;
        means[3]=.4;
        
        vars[0]=0.05;
        vars[1]=.05;
        vars[2]=.2;
        vars[3]=.02;
        
        theState.setProblemParams(means, vars);
    }
    

}
