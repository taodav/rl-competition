package SamplePolyathlon.Proving;


import SamplePolyathlon.ProblemTypeC.MainPackage.AbstractMDPPolyProblemTypeC;
import java.util.Random;
import rlglue.types.Action;
import rlglue.types.Observation;

public class PolyPMDP3 extends AbstractMDPPolyProblemTypeC{
Random myRand=new Random();

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 4);
        double b= Math.pow(normT1, 2);
        double c= Math.pow(normT2, 2);
        double d= normTD2;
        double a= normTD1;
        
        currentObs.doubleArray[0]=a;
        currentObs.doubleArray[1]=b;
        currentObs.doubleArray[2]=c;
        currentObs.doubleArray[3]=d;
        
        return currentObs;

        
    }

    protected Action MDPSpecificActionConverter(int abstractAction) {
        //MAke action 3 random
        if(abstractAction==3)
                abstractAction=myRand.nextInt(3);
        
        Action primActionObject=new Action(1, 0);
        primActionObject.intArray[0]=abstractAction;
        return primActionObject;
    }
    
}