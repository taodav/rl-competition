package org.rlcommunity.environments.competition2009.polyathlon;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeA.MainPackage.AbstractMDPPolyProblemTypeA;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;
import rlVizLib.dynamicLoading.Unloadable;

public class TrainingMDP3 extends AbstractMDPPolyProblemTypeA implements Unloadable {
Random myRand=new Random();

//Just to show we can be nice.
    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 6);
        currentObs.doubleArray[0]=nPos;
        currentObs.doubleArray[1]=nVel;
        currentObs.doubleArray[2]=0;
        currentObs.doubleArray[3]=0;
        currentObs.doubleArray[4]=0;
        currentObs.doubleArray[5]=0;
        return currentObs;


    }

    protected Action MDPSpecificActionConverter(int abstractAction) {
        //Pick no-op action if action not in the regular range
        if(abstractAction>2)abstractAction=1;

        Action primActionObject=new Action(1, 0);
        primActionObject.intArray[0]=abstractAction;
        return primActionObject;
    }


    
}