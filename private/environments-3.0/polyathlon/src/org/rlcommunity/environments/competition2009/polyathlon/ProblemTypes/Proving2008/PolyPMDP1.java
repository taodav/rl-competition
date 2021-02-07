package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.Proving2008;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeA.MainPackage.AbstractMDPPolyProblemTypeA;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;
import rlVizLib.dynamicLoading.Unloadable;

public class PolyPMDP1 extends AbstractMDPPolyProblemTypeA implements Unloadable{
Random myRand=new Random();

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 4);
        double d= Math.pow(nPos, 3);
        double c= Math.pow(nVel, 2);
        double b= .3333 * d + .6666*c;
        double a= myRand.nextDouble()*d;
        
        currentObs.doubleArray[0]=a;
        currentObs.doubleArray[1]=b;
        currentObs.doubleArray[2]=c;
        currentObs.doubleArray[3]=d;
        
        return currentObs;

        
    }

    protected Action MDPSpecificActionConverter(int abstractAction) {
        if(abstractAction>2)abstractAction=myRand.nextInt(3);
        
        Action primActionObject=new Action(1, 0);
        primActionObject.intArray[0]=abstractAction;
        return primActionObject;
    }
    
}