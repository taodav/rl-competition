package org.rlcommunity.environments.competition2009.polyathlon.testing;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeA.MainPackage.AbstractMDPPolyProblemTypeA;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;

public class PolyathlonTesting14 extends AbstractMDPPolyProblemTypeA{
Random myRand=new Random();

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 6);
        double c= Math.pow(nPos, 2);
        double a= nVel;
        double d= myRand.nextDouble()*c;
        double b= .5 * c + .5*a;
        
        currentObs.doubleArray[0]=Math.random();
        currentObs.doubleArray[1]=b;
        currentObs.doubleArray[2]=c;
        currentObs.doubleArray[3]=d;
        currentObs.doubleArray[4]=a;
        currentObs.doubleArray[5]=Math.random();
        
        return currentObs;

        
    }

    protected Action MDPSpecificActionConverter(int abstractAction) {
        //Make action 3-->2 (push right), 5-->0 (push left), others coast
        int translatedAction=1;
        if(abstractAction==3)translatedAction=2;
        if(abstractAction==5)translatedAction=0;

        
        Action primActionObject=new Action(1, 0);
        primActionObject.intArray[0]=translatedAction;
        return primActionObject;
    }
    
}