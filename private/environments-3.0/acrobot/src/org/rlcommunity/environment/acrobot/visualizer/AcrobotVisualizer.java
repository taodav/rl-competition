package org.rlcommunity.environment.acrobot.visualizer;

import org.rlcommunity.rlglue.codec.types.Observation;
import rlVizLib.general.TinyGlue;
import rlVizLib.visualization.AbstractVisualizer;
import rlVizLib.visualization.GenericScoreComponent;
import rlVizLib.visualization.SelfUpdatingVizComponent;
import rlVizLib.visualization.interfaces.GlueStateProvider;

public class AcrobotVisualizer extends AbstractVisualizer implements GlueStateProvider{
    private TinyGlue theGlueState=null;
    
	public AcrobotVisualizer(TinyGlue theGlueState)  {
		super();
                this.theGlueState=theGlueState;
		SelfUpdatingVizComponent theAcrobotVisualizer= new AcrobotBotComponent(this);
		SelfUpdatingVizComponent theAcrobotCounter = new GenericScoreComponent(this);
		
		addVizComponentAtPositionWithSize(theAcrobotVisualizer,0,0,1.0,1.0);
		addVizComponentAtPositionWithSize(theAcrobotCounter,0,0,1.0,1.0);
	}
	
    public TinyGlue getTheGlueState() {
        return theGlueState;
    }
	
}
