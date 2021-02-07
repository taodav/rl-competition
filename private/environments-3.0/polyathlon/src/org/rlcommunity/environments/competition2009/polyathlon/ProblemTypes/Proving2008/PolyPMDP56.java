package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.Proving2008;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.SamplePolyathlon2008.PolarUtility;
import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeF.MainPackage.AbstractMDPPolyProblemTypeF;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;
import rlVizLib.dynamicLoading.Unloadable;

public class PolyPMDP56 extends AbstractMDPPolyProblemTypeF implements Unloadable{
Random myRand=new Random();
PolarUtility P = new PolarUtility();
    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 4);
        
        Point2D polarCat=P.cartesianToPolar(normAttackerX, normAttackerY);
        
        currentObs.doubleArray[0]=normX;
        currentObs.doubleArray[3]=normY;
        currentObs.doubleArray[2]=polarCat.getX();
        currentObs.doubleArray[1]=polarCat.getY();
        
        setAttackerPolicy(2);
        
        return currentObs;
    }

    //Might want to juggle these
    protected Action MDPSpecificActionConverter(int abstractAction) {
        Action primActionObject=new Action(1, 0);
        primActionObject.intArray[0]=abstractAction;
        return primActionObject;
    }

    @Override
    protected void addAllRegions() {
		addResetRegion(new Rectangle2D.Double(150.0d,75.0d,50.0d,50.0d));
		addRewardRegion(new Rectangle2D.Double(150.0d,75.0d,50.0d,50.0d),10.0d);

                //-.1 per step
                addRewardRegion(new Rectangle2D.Double(0.0d,0.0d,200.0d,200.0d),-.1d);

                addBarrierRegion(new Rectangle2D.Double(20.0d,50.0d,20.0d,50.0d),1.0d);
                addBarrierRegion(new Rectangle2D.Double(60.0d,125.0d,20.0d,50.0d),1.0d);
                addBarrierRegion(new Rectangle2D.Double(100.0d,100.0d,50.0d,20.0d),1.0d);
                
                addBarrierRegion(new Rectangle2D.Double(130.0d,30.0d,20.0d,120.0d),1.0d);
                addBarrierRegion(new Rectangle2D.Double(90.0d,10.0d,20.0d,50.0d),1.0d);
    }

    
}