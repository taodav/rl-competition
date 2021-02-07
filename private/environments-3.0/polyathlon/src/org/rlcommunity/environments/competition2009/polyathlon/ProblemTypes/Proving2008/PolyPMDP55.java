package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.Proving2008;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeC.MainPackage.AbstractMDPPolyProblemTypeC;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;
import rlVizLib.dynamicLoading.Unloadable;

public class PolyPMDP55 extends AbstractMDPPolyProblemTypeC  implements Unloadable{
Random myRand=new Random();

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 4);
        double a=normT1;
        double d= normT2/2.0d;
        double b= Math.pow(normTD1,3.0d);
        double c= normTD1;
        
        currentObs.doubleArray[0]=a;
        currentObs.doubleArray[1]=b;
        currentObs.doubleArray[2]=c;
        currentObs.doubleArray[3]=d;
        
        return currentObs;
    }

    protected Action MDPSpecificActionConverter(int abstractAction) {
        //MAke action 0 the random action
        if(abstractAction==0)
                abstractAction=myRand.nextInt(3);
        else
                abstractAction-=1;
        
        Action primActionObject=new Action(1, 0);
        primActionObject.intArray[0]=abstractAction;
        return primActionObject;
    }
    
}