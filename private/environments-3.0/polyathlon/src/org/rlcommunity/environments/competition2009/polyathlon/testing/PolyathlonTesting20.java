package org.rlcommunity.environments.competition2009.polyathlon.testing;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeC.MainPackage.AbstractMDPPolyProblemTypeC;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;

public class PolyathlonTesting20 extends AbstractMDPPolyProblemTypeC {
Random myRand=new Random();

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 6);
        double a=normT1/2.0d;
        double d= normT2;
        double b= 1.0-normTD2;
        double c= 1.0-normTD1;
        
        currentObs.doubleArray[0]=b;
        currentObs.doubleArray[1]=c*c;
        currentObs.doubleArray[2]=d;
        currentObs.doubleArray[3]=a;
        currentObs.doubleArray[4]=b*Math.random();
        currentObs.doubleArray[5]=Math.random();
        
        return currentObs;
    }

    protected Action MDPSpecificActionConverter(int abstractAction) {
        //Make action 0 and >=3 the random action
        int translatedAction=myRand.nextInt(3);
        if(abstractAction==1){
            translatedAction=1;
        }
        if(abstractAction==2){
            translatedAction=0;
        }
        if(abstractAction==3){
            translatedAction=2;
        }
        Action primActionObject=new Action(1, 0);
        primActionObject.intArray[0]=translatedAction;
        return primActionObject;
    }
    
}