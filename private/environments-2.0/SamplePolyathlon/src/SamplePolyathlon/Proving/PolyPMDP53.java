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
public class PolyPMDP53 extends AbstractPolyProblemTypeE {
    @Override
    public double generateReward(Observation o, int a) {
        double[] rewards=new double[4];
        
        rewards[0]=Math.sin(o.doubleArray[3]);
        rewards[1]=Math.cos(o.doubleArray[1]);
        rewards[2]=o.doubleArray[2];
        rewards[3]=.25*(o.doubleArray[0]+o.doubleArray[1]+o.doubleArray[2]+o.doubleArray[3]);
        
        return rewards[a];
    }
    
    public PolyPMDP53(){
        super();
        double[] means=new double[4];
        double[] vars=new double[4];
        
        means[0]=.4;
        means[1]=.4;
        means[2]=.5;
        means[3]=.3;
        
        vars[0]=0.02;
        vars[1]=.04;
        vars[2]=.5;
        vars[3]=.1;
        
        theState.setProblemParams(means, vars);
    }
    

}
