package SamplePolyathlon.ProblemTypeC.Visualizer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import rlVizLib.visualization.VizComponent;

public class AcrobotBotComponent implements VizComponent {
	private AbstractPolyProblemTypeCVisualizer acroVis; 
	static final int joint1X = 50;
	static final int joint1Y = 20;
	static final int leg1length =30;
	static final int leg2length =30;
	static final int circleSize = 5;
	
        int lastUpdateStep=-1;
        
	public AcrobotBotComponent(AbstractPolyProblemTypeCVisualizer acrobotVisualizer) {
		acroVis = acrobotVisualizer;
	}

	public void render(Graphics2D g) {
	    AffineTransform saveAT = g.getTransform();
	    g.setColor(Color.BLACK);
   	    g.scale(.01, .01);
		int joint2X = (int) (leg1length*Math.sin(acroVis.getTheta1())+ joint1X);
		int joint2Y = (int) (leg1length*Math.cos(acroVis.getTheta1())+joint1Y);
		g.drawLine(joint1X, joint1Y, joint2X, joint2Y);
		int joint3X = (int) (leg2length*Math.cos(Math.PI/2-acroVis.getTheta2()-acroVis.getTheta1()) + joint2X);
		int joint3Y= (int) (leg2length*Math.sin(Math.PI/2-acroVis.getTheta1()-acroVis.getTheta2()) + joint2Y);
		g.drawLine(joint2X, joint2Y, joint3X, joint3Y);
	    g.setTransform(saveAT);
	}

	public boolean update() {
            int thisUpdateStep=acroVis.getTheGlueState().getTotalSteps();
            
            if(thisUpdateStep>lastUpdateStep){
                lastUpdateStep=thisUpdateStep;
                return true;
            }
            return false;
	}

}
