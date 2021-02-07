/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.environments.competition2009.polyathlon.testing;

import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeD.MainPackage.AbstractPolyProblemTypeD;

//Another example that we can be kind
/**
 *
 * @author Brian Tanner
 */
public class PolyathlonTesting10 extends AbstractPolyProblemTypeD  {

    public PolyathlonTesting10(){
       super();
        double[] means=new double[6];
        double[] vars=new double[6];

        means[0]=.1;
        means[1]=.2;
        means[2]=.6;
        means[3]=.4;
        means[4]=.5;
        means[5]=.2;

        vars[0]=0.1;
        vars[1]=0.1;
        vars[2]=0.1;
        vars[3]=0.1;
        vars[4]=0.1;
        vars[5]=0.1;

        theState.setProblemParams(means, vars);

}
}