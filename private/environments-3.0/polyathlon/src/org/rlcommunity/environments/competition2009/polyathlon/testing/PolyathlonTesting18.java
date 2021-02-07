/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.environments.competition2009.polyathlon.testing;

import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeH.MainPackage.AbstractMDPPolyProblemTypeH;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;

/**
 *
 * @author btanner
 */
public class PolyathlonTesting18 extends AbstractMDPPolyProblemTypeH {

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation o=new Observation(0,6,0);
        o.doubleArray[0]=Math.random()/2.0d;
        o.doubleArray[1]=.75*normTheta;
        o.doubleArray[2]=normThetaDot*normThetaDot;
        o.doubleArray[3]=normPos;
        o.doubleArray[4]=Math.random()*normThetaDot;
        o.doubleArray[5]=1.0-normVel;
        return o;
    }

    @Override
    protected Action MDPSpecificActionConverter(int abstractAction) {
        Action a=new Action(1,0,0);
        a.intArray[0]=0;
        if(abstractAction==1)
            a.intArray[0]=1;
        return a;
    }
}
