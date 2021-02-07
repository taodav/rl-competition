package org.rlcommunity.environments.competition2009.polyathlon.proving;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeA.MainPackage.AbstractMDPPolyProblemTypeA;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;
public class PolyathlonProving4 extends AbstractMDPPolyProblemTypeA {
Random myRand=new Random();

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 6);
        double d= Math.pow(nPos, 3);
        double c= Math.pow(nVel, 2);
        double b= .3333 * d + .6666*c;
        double a= myRand.nextDouble()*d;

        double aGaussian=(myRand.nextGaussian()+.5)/2.0d;
        if(aGaussian<0.0d)aGaussian=0.0d;
        if(aGaussian>1.0d)aGaussian=1.0d;
        //random irrelevant
        currentObs.doubleArray[0]=aGaussian;
        //function of position
        currentObs.doubleArray[1]=b;
        //random irrelevant
        currentObs.doubleArray[2]=myRand.nextDouble();
        //convex combination of functions of position and velocity
        currentObs.doubleArray[3]=d;
        //function of velocity
        currentObs.doubleArray[4]=c;
        //random perterbation of function of position
        currentObs.doubleArray[5]=a;

        return currentObs;


    }

    protected Action MDPSpecificActionConverter(int abstractAction) {
        //Pick a random action if the one chosen is not in the regular range.
        if(abstractAction>2)abstractAction=myRand.nextInt(3);

        Action primActionObject=new Action(1, 0);
        primActionObject.intArray[0]=abstractAction;
        return primActionObject;
    }


    
}