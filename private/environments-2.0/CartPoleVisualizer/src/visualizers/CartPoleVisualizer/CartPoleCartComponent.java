package visualizers.CartPoleVisualizer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import rlVizLib.utilities.UtilityShop;
import rlVizLib.visualization.VizComponent;

public class CartPoleCartComponent implements VizComponent  {
	private CartPoleVisualizer cartVis = null;
	private static int poleLength = 30;
	public CartPoleCartComponent(CartPoleVisualizer cartpoleVisualizer) {
		cartVis = cartpoleVisualizer;
	
	}
	public void render(Graphics2D g) {
	    //SET COLOR
	    g.setColor(Color.BLACK);
	    //DRAW 12 Lines with blue ball equalizers.
	    
	    AffineTransform saveAT = g.getTransform();
		    g.scale(.01, .01);
			int transX = (int)(UtilityShop.normalizeValue( cartVis.currentXPos(), cartVis.getLeftCartBound(),cartVis.getRightCartBound())*(90.0f-10.0f) + 10.0f);
			int transY = 80;
			g.fillRect(transX-10, transY, 20 ,5);
			int x2 = transX + (int)(poleLength*Math.cos(cartVis.getAngle()));
			int y2 = transY + (int)(poleLength*Math.sin(cartVis.getAngle()));
			g.drawLine(transX, transY, x2, y2);
	    g.setTransform(saveAT);	
			
		
	}
	public boolean update() {
		return cartVis.updateCart();
	}

}
