package org.rlcommunity.environments.competition2009.polyathlon.proving;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeB.MainPackage.AbstractMDPPolyProblemTypeB;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;

public class PolyathlonProving3 extends AbstractMDPPolyProblemTypeB {
Random myRand=new Random();

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 6);
        
        currentObs.doubleArray[0]=1.0-normX;
        currentObs.doubleArray[1]=Math.sqrt(normY);
        currentObs.doubleArray[2]=normX*normX;
        currentObs.doubleArray[3]=myRand.nextDouble();
        currentObs.doubleArray[4]=myRand.nextDouble()*normY;
        currentObs.doubleArray[5]=normX*normY;
        
        return currentObs;

        
    }

    //Might want to juggle these
    protected Action MDPSpecificActionConverter(int abstractAction) {
        Action primActionObject=new Action(1, 0);
        primActionObject.intArray[0]=abstractAction;

        //Actions are mixed up, and action 0 and 2 are random
        if(abstractAction==0){
            primActionObject.intArray[0]=2;
        }
        if(abstractAction==1){
            primActionObject.intArray[0]=1;
        }
        if(abstractAction==2){
            primActionObject.intArray[0]=0;
        }
        if(abstractAction==3){
            primActionObject.intArray[0]=3;
        }
        if(abstractAction==4){
            primActionObject.intArray[0]=myRand.nextInt(4);
        }
        if(abstractAction==5){
            primActionObject.intArray[0]=myRand.nextInt(4);
        }
        return primActionObject;
    }

    @Override
    protected void addAllRegions() {
		addResetRegion(new Rectangle2D.Double(75.0d,75.0d,25.0d,25.0d));
		addRewardRegion(new Rectangle2D.Double(75.0d,75.0d,25.0d,25.0d),1.0d);
		addRewardRegion(new Rectangle2D.Double(0.0d,0.0d,200.0d,200.0d),-1.0d);
		addBarrierRegion(new Rectangle2D.Double(50.0d,50.0d,10.0d,100.0d),1.0d);
		addBarrierRegion(new Rectangle2D.Double(50.0d,50.0d,100.0d,10.0d),1.0d);
		addBarrierRegion(new Rectangle2D.Double(150.0d,50.0d,10.0d,100.0d),1.0d);

    }

    
}