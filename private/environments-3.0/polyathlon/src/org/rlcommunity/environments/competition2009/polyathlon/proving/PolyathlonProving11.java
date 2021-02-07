package org.rlcommunity.environments.competition2009.polyathlon.proving;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeF.MainPackage.AbstractMDPPolyProblemTypeF;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;

public class PolyathlonProving11 extends AbstractMDPPolyProblemTypeF {
Random myRand=new Random();

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 6);

        //Totally random (irrelevant dimension)
        currentObs.doubleArray[0]=myRand.nextDouble();

        //Warp player Y using cos function
        currentObs.doubleArray[1]=.5 + .5*Math.cos(Math.PI*(normY));
        //Give cat X directly
        currentObs.doubleArray[2]=normAttackerX;
        //Warp player X using sin function
        currentObs.doubleArray[3]=.5 + .5*Math.sin(Math.PI*(normX+.5));
        //Give cat Y directly
        currentObs.doubleArray[4]=normAttackerY;

        //Coin toss for .25 or .75 (irrelevant dimension)
        if(myRand.nextBoolean()){
            currentObs.doubleArray[5]=.25;
        }else{
            currentObs.doubleArray[5]=.75;
        }
        
        setAttackerPolicy(1);
        
        return currentObs;
    }

    //Might want to juggle these
    protected Action MDPSpecificActionConverter(int abstractAction) {
        Action primActionObject=new Action(1, 0, 0);

        //There are 6 actions, {0,1,2,3,4,5}. Let's say action's
        //4 and 5 take a random action

        if(abstractAction>3)
            abstractAction=myRand.nextInt(4);
        primActionObject.intArray[0]=abstractAction;
        return primActionObject;
    }

    @Override
    protected void addAllRegions() {
		addResetRegion(new Rectangle2D.Double(150.0d,75.0d,50.0d,50.0d));
		addRewardRegion(new Rectangle2D.Double(150.0d,75.0d,50.0d,50.0d),1.0d);

                //-.01 per step
                addRewardRegion(new Rectangle2D.Double(0.0d,0.0d,200.0d,200.0d),-.01d);

                addBarrierRegion(new Rectangle2D.Double(20.0d,50.0d,20.0d,50.0d),1.0d);
                addBarrierRegion(new Rectangle2D.Double(60.0d,125.0d,20.0d,50.0d),1.0d);
                addBarrierRegion(new Rectangle2D.Double(100.0d,100.0d,50.0d,20.0d),1.0d);
                
                addBarrierRegion(new Rectangle2D.Double(130.0d,30.0d,20.0d,120.0d),1.0d);
                addBarrierRegion(new Rectangle2D.Double(90.0d,10.0d,20.0d,50.0d),1.0d);
    }

    
}