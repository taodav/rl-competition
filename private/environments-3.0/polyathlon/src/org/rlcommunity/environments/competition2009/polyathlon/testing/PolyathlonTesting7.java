package org.rlcommunity.environments.competition2009.polyathlon.testing;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeC.MainPackage.AbstractMDPPolyProblemTypeC;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;

public class PolyathlonTesting7 extends AbstractMDPPolyProblemTypeC {
Random myRand=new Random();

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 6);
        double a=normT1;
        double d= normT2/2.0d;
        double b= Math.pow(normTD1,3.0d);
        double c= normTD2;
        
        currentObs.doubleArray[0]=1-a;
        currentObs.doubleArray[1]=b*b;
        currentObs.doubleArray[2]=c;
        currentObs.doubleArray[3]=d;
        currentObs.doubleArray[4]=b;
        currentObs.doubleArray[5]=a;
        
        return currentObs;
    }

    protected Action MDPSpecificActionConverter(int abstractAction) {
        //Make action 0 and >=3 the random action
        int translatedAction=myRand.nextInt(3);
        if(abstractAction==1){
            translatedAction=0;
        }
        if(abstractAction==2){
            translatedAction=2;
        }
        if(abstractAction==3){
            translatedAction=1;
        }
        Action primActionObject=new Action(1, 0);
        primActionObject.intArray[0]=translatedAction;
        return primActionObject;
    }
    
}