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
public class PolyathlonTesting1 extends AbstractMDPPolyProblemTypeG {

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation o=new Observation(0,6,0);
        o.doubleArray[0]=0;
        o.doubleArray[1]=normVel;
        o.doubleArray[2]=normTheta;
        o.doubleArray[3]=normPos;
        o.doubleArray[4]=0;
        o.doubleArray[5]=normThetaDot;
        return o;
    }

    @Override
    protected Action MDPSpecificActionConverter(int abstractAction) {
        Action a=new Action(1,0,0);
        a.intArray[0]=abstractAction%2;
        return a;
    }

}
