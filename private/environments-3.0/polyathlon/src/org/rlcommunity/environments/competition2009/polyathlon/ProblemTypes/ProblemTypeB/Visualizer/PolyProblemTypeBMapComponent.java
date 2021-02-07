package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeB.Visualizer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import rlVizLib.visualization.VizComponent;

public class PolyProblemTypeBMapComponent implements VizComponent {
	AbstractPolyProblemTypeBVisualizer CGWViz;
	public PolyProblemTypeBMapComponent(AbstractPolyProblemTypeBVisualizer CGWViz){
		this.CGWViz=CGWViz;
	}
	public void render(Graphics2D g) {
		Rectangle2D theWorldRect=CGWViz.getWorldRect();
		
		AffineTransform theScaleTransform=new AffineTransform();
		theScaleTransform.scale(1.0d/theWorldRect.getWidth(),1.0d/theWorldRect.getHeight());
		AffineTransform x = g.getTransform();
		x.concatenate(theScaleTransform);
		g.setTransform(x);

		
		Vector<Rectangle2D> resetRegions=CGWViz.getResetRegions();
		for (Rectangle2D thisRect : resetRegions) {
			g.setColor(Color.blue);
			g.fill(thisRect);
		}
		
		Vector<Rectangle2D> barrierRegions=CGWViz.getBarrierRegions();
		Vector<Double> thePenalties=CGWViz.getPenalties();
		for (int i=0;i<barrierRegions.size();i++){
			Rectangle2D thisRect=barrierRegions.get(i);
			double thisPenalty=thePenalties.get(i);

			Color theColor=new Color((float)thisPenalty, 0, 0);

			g.setColor(theColor);
			g.fill(thisRect);
		}


	}

	public boolean update() {
		
		return true;
	}

}
