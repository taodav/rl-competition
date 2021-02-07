package org.rlcommunity.environments.competition2009.polyathlon.testing;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeC.MainPackage.AbstractMDPPolyProblemTypeC;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;

public class PolyathlonTesting9 extends AbstractMDPPolyProblemTypeC{
Random myRand=new Random();

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 6);
        double b= Math.pow(normT1, 2);
        double c= Math.pow(normT2, 2);
        double d= normTD2;
        double a= normTD1;
        double e = d*a;

        
        currentObs.doubleArray[0]=a;
        currentObs.doubleArray[1]=0;
        currentObs.doubleArray[2]=c;
        currentObs.doubleArray[3]=d;
        currentObs.doubleArray[4]=e;
        currentObs.doubleArray[5]=b;
        
        return currentObs;

        
    }

    protected Action MDPSpecificActionConverter(int abstractAction) {
        //Flip action order
        //Make action >=3 random
        abstractAction=5-abstractAction;
        if(abstractAction>=3)
                abstractAction=myRand.nextInt(3);
        
        Action primActionObject=new Action(1, 0);
        primActionObject.intArray[0]=abstractAction;
        return primActionObject;
    }
    
}