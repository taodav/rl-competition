/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.environments.competition2009.polyathlon.testing;

import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeG.MainPackage.AbstractMDPPolyProblemTypeG;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;

/**
 *
 * @author btanner
 */
public class PolyathlonTesting17 extends AbstractMDPPolyProblemTypeG {

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation o=new Observation(0,6,0);
        o.doubleArray[0]=.5*normVel;
        o.doubleArray[1]=Math.random();
        o.doubleArray[2]=normPos*normPos;
        o.doubleArray[3]=1.0-normThetaDot;
        o.doubleArray[4]=normPos*normVel;
        o.doubleArray[5]=normTheta;
        return o;
    }

    @Override
    protected Action MDPSpecificActionConverter(int abstractAction) {
        Action a=new Action(1,0,0);
        a.intArray[0]=1;
        if(abstractAction==0)a.intArray[0]=0;
        return a;
    }

}
