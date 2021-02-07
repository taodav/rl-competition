
package visualizers.RealTimeStrategyVisualizer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import rlVizLib.general.TinyGlue;
import rlVizLib.utilities.UtilityShop;
import rlVizLib.visualization.VizComponent;
import rlglue.types.Observation;


public class RTSBackGround implements VizComponent  {
  private RealTimeStrategyVisualizer rtsVis = null;
  
  private Parameters parms;
  
  Color bgColor;      
  int updateCount;
  
  public RTSBackGround(RealTimeStrategyVisualizer rtsVisualizer) {
    rtsVis = rtsVisualizer; 
    
//    bgColor = new Color((float)0.6, (float)0.3, (float)0.3);
       bgColor=Color.BLACK;
  }
  
  public void setVars(Parameters parms)
  {
    this.parms = parms; 
    updateCount = 0;     
  }
  
  
  public void render(Graphics2D g)
  {
    AffineTransform saveAT = g.getTransform();
    //g.scale(.01, .01);
    g.scale(1.0/parms.width, 1.0/parms.height);
    
    g.setBackground(bgColor);
    g.setColor(bgColor);
    g.fillRect(0, 0, parms.width, parms.height);
    
    g.setTransform(saveAT);     
  }
  
  public boolean update() {
    
    if (updateCount == 10000000 || updateCount == 0)
    {
      updateCount = 1;      
      return true;
    }
    
    updateCount ++; 
    return false;
  }

}  