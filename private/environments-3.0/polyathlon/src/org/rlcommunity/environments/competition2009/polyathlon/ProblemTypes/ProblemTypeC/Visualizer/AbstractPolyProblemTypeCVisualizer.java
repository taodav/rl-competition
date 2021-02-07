package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeC.Visualizer;

import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeC.Messages.ProblemTypeCStateRequest;
import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeC.Messages.ProblemTypeCStateResponse;
import rlVizLib.general.TinyGlue;
import rlVizLib.visualization.AbstractVisualizer;
import rlVizLib.visualization.GenericScoreComponent;
import rlVizLib.visualization.VizComponent;
import rlVizLib.visualization.interfaces.GlueStateProvider;
import org.rlcommunity.rlglue.codec.types.Observation;
import rlVizLib.visualization.SelfUpdatingVizComponent;

public class AbstractPolyProblemTypeCVisualizer extends AbstractVisualizer implements GlueStateProvider{
    private TinyGlue theGlueState=null;
    
	public AbstractPolyProblemTypeCVisualizer(TinyGlue theGlueState)  {
		super();
                this.theGlueState=theGlueState;
		VizComponent theAcrobotVisualizer= new AcrobotBotComponent(this);
		SelfUpdatingVizComponent theAcrobotCounter = new GenericScoreComponent(this);
		
		addVizComponentAtPositionWithSize(theAcrobotVisualizer,0,0,1.0,1.0);
		addVizComponentAtPositionWithSize(theAcrobotCounter,0,0,1.0,1.0);
	}
	
        

        public double getTheta1(){
            ProblemTypeCStateResponse thisResponse=ProblemTypeCStateRequest.Execute();
            return thisResponse.getTheta1();
	}
	public double getTheta2(){
            ProblemTypeCStateResponse thisResponse=ProblemTypeCStateRequest.Execute();
            return thisResponse.getTheta2();
	}
	
        
    public TinyGlue getTheGlueState() {
        return theGlueState;
    }
	
}
