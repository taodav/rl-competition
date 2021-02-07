/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.environments.competition2009.polyathlon.testing;

import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeD.MainPackage.AbstractPolyProblemTypeD;

/**
 *
 * @author Brian Tanner
 */
public class PolyathlonTesting4 extends AbstractPolyProblemTypeD {

    public PolyathlonTesting4(){
       super();
        double[] means=new double[6];
        double[] vars=new double[6];

        means[0]=.4;
        means[1]=.4;
        means[2]=.7;
        means[3]=.3;
        means[4]=.1;
        means[5]=.2;

        vars[0]=0.02;
        vars[1]=.04;
        vars[2]=.5;
        vars[3]=.1;
        vars[4]=.3;
        vars[5]=0.0;

        theState.setProblemParams(means, vars);

}
}