/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.environments.competition2009.polyathlon.testing;

import java.util.Random;
import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeE.MainPackage.AbstractPolyProblemTypeE;
import org.rlcommunity.rlglue.codec.types.Observation;

/**
 *
 * @author Brian Tanner
 */
public class PolyathlonTesting16 extends AbstractPolyProblemTypeE{
    @Override
    public double generateReward(Observation o, int a) {
        double[] rewards=new double[6];
        
        rewards[0]=Math.random()-.4;//
        rewards[1]=theRand.nextGaussian()*Math.sin(.5*(o.doubleArray[3]+o.doubleArray[2]));
        rewards[2]=.5d-o.doubleArray[2];
        rewards[3]=.5d-.25*(o.doubleArray[0]+o.doubleArray[1]+o.doubleArray[2]+o.doubleArray[3]);
        rewards[4]=Math.random()*o.doubleArray[4];
        rewards[5]=theRand.nextGaussian()*Math.cos(o.doubleArray[1]);
        return rewards[a];
    }

    private Random theRand=new Random();
    public PolyathlonTesting16(){
        super();
        double[] means=new double[6];
        double[] vars=new double[6];
        
        means[0]=.4;
        means[1]=.1;
        means[2]=.3;
        means[3]=.6;
        means[4]=.4;
        means[5]=.5;
        
        vars[0]=.212;
        vars[1]=.04;
        vars[2]=.333;
        vars[3]=.45;
        vars[4]=.11;
        vars[5]=0.01;
        
        theState.setProblemParams(means, vars);
    }
    

}
