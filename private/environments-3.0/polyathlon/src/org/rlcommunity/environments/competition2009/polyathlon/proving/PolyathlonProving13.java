/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.environments.competition2009.polyathlon.proving;

import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeE.MainPackage.AbstractPolyProblemTypeE;
import org.rlcommunity.rlglue.codec.types.Observation;

/**
 *
 * @author Brian Tanner
 */
public class PolyathlonProving13 extends AbstractPolyProblemTypeE{
    @Override
    public double generateReward(Observation o, int a) {
        double[] rewards=new double[6];
        
        rewards[0]=.25*(o.doubleArray[0]+o.doubleArray[1]+o.doubleArray[2]+o.doubleArray[3]);
        rewards[1]=Math.cos(o.doubleArray[1]);
        rewards[2]=o.doubleArray[2];
        rewards[3]=.25*(o.doubleArray[0]+o.doubleArray[1]+o.doubleArray[2]+o.doubleArray[3]);
        rewards[4]=Math.random()*o.doubleArray[4];
        rewards[5]=Math.sin(o.doubleArray[5]);
        return rewards[a];
    }
    
    public PolyathlonProving13(){
        super();
        double[] means=new double[6];
        double[] vars=new double[6];
        
        means[0]=.1;
        means[1]=.4;
        means[2]=.5;
        means[3]=.3;
        means[4]=.4;
        means[5]=.6;
        
        vars[0]=0.02;
        vars[1]=.04;
        vars[2]=.5;
        vars[3]=.1;
        vars[4]=.3;
        vars[5]=.2;
        
        theState.setProblemParams(means, vars);
    }
    

}
