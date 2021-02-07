package org.rlcommunity.environments.competition2009.polyathlon.testing;

import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeF.MainPackage.AbstractMDPPolyProblemTypeF;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;

public class PolyathlonTesting19 extends AbstractMDPPolyProblemTypeF {

    Random myRand = new Random();

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 6);
        //Coin toss for 1.0 or 0.0 (irrelevant dimension)
        if (myRand.nextBoolean()) {
            currentObs.doubleArray[0] = 1.0;
        } else {
            currentObs.doubleArray[0] = 0.0;
        }

        if(normX<.5){
        currentObs.doubleArray[1] = 1.0-normX;
        }else{
        currentObs.doubleArray[1] = normX;
        }
        //Give cat X directly
        currentObs.doubleArray[2] = normAttackerX;


        //Totally random (irrelevant dimension)
        currentObs.doubleArray[3] = myRand.nextDouble();

        currentObs.doubleArray[4] = .5 * normAttackerY;

        //Warp player Y using sin function
        currentObs.doubleArray[5] =1.0-normY;


        setAttackerPolicy(2);

        return currentObs;
    }

    //Might want to juggle these
    protected Action MDPSpecificActionConverter(int abstractAction) {
        Action primActionObject = new Action(1, 0, 0);

        //There are 6 actions, {0,1,2,3,4,5}.
        //Juggle em
        int juggledAction=0;
        switch(abstractAction){
            case 0: juggledAction=3;
            break;
            case 1: juggledAction=1;
            break;
            case 2: juggledAction=3;
            break;
            case 3: juggledAction=0;
            break;
            case 4: juggledAction=2;
            break;
            case 5: juggledAction=0;
            break;
        }
        primActionObject.intArray[0] = juggledAction;
        return primActionObject;
    }

    @Override
    protected void addAllRegions() {
        addResetRegion(new Rectangle2D.Double(150.0d, 75.0d, 50.0d, 50.0d));
        addRewardRegion(new Rectangle2D.Double(150.0d, 75.0d, 50.0d, 50.0d), 1.0d);

        //-.1 per step
        addRewardRegion(new Rectangle2D.Double(0.0d, 0.0d, 200.0d, 200.0d), -.01d);

        addBarrierRegion(new Rectangle2D.Double(20.0d, 50.0d, 20.0d, 50.0d), 1.0d);
        addBarrierRegion(new Rectangle2D.Double(60.0d, 125.0d, 20.0d, 50.0d), 1.0d);
        addBarrierRegion(new Rectangle2D.Double(100.0d, 100.0d, 50.0d, 20.0d), 1.0d);

        addBarrierRegion(new Rectangle2D.Double(130.0d, 30.0d, 20.0d, 120.0d), 1.0d);
        addBarrierRegion(new Rectangle2D.Double(90.0d, 10.0d, 20.0d, 50.0d), 1.0d);
    }
}