package SamplePolyathlon.Proving;


import SamplePolyathlon.ProblemTypeB.MainPackage.AbstractMDPPolyProblemTypeB;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.Vector;
import rlglue.types.Action;
import rlglue.types.Observation;

public class PolyPMDP52 extends AbstractMDPPolyProblemTypeB{
Random myRand=new Random();

    @Override
    protected Observation MDPSpecificMakeObservation() {
        Observation currentObs = new Observation(0, 4);
        
        currentObs.doubleArray[0]=normX*normY;
        currentObs.doubleArray[2]=normX*normX;
        currentObs.doubleArray[1]=Math.sqrt(normY);
        currentObs.doubleArray[3]=myRand.nextDouble();
        
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
		addResetRegion(new Rectangle2D.Double(75.0d,75.0d,25.0d,25.0d));
		addRewardRegion(new Rectangle2D.Double(75.0d,75.0d,25.0d,25.0d),1.0d);
		addRewardRegion(new Rectangle2D.Double(0.0d,0.0d,200.0d,200.0d),-1.0d);
		addBarrierRegion(new Rectangle2D.Double(50.0d,50.0d,10.0d,100.0d),1.0d);
		addBarrierRegion(new Rectangle2D.Double(50.0d,50.0d,100.0d,10.0d),1.0d);
		addBarrierRegion(new Rectangle2D.Double(150.0d,50.0d,10.0d,100.0d),1.0d);

    }

    
}