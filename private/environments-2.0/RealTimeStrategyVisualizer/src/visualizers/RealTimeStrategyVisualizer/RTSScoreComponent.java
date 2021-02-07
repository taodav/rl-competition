/* RL-VizLib, a library for C++ and Java for adding advanced visualization and dynamic capabilities to RL-Glue.
* Copyright (C) 2007, Brian Tanner brian@tannerpages.com (http://brian.tannerpages.com/)
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. */

package visualizers.RealTimeStrategyVisualizer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import rlVizLib.general.TinyGlue;
import rlVizLib.visualization.interfaces.GlueStateProvider;
import rlVizLib.visualization.GenericScoreComponent;
import rlVizLib.visualization.VizComponent;


public class RTSScoreComponent extends GenericScoreComponent implements VizComponent{
  private GlueStateProvider theGlueStateProvider = null;
  
  public RTSScoreComponent(GlueStateProvider theVis){
    super(theVis);
    this.theGlueStateProvider = theVis;
  }

  public void render(Graphics2D g) {
    // Mostly copied from GenericScoreComponent
    
    Font f = new Font("Verdana",0,8);     
    g.setFont(f);
    g.setColor(Color.RED);
    
    AffineTransform saveAT = g.getTransform();    
    g.scale(.005, .005);
    TinyGlue theGlueState=theGlueStateProvider.getTheGlueState();
    
    g.drawString("E/S/T/R/TR: " + theGlueState.getEpisodeNumber() +"/" 
                                   + theGlueState.getTimeStep() + "/"  
                                   + theGlueState.getTotalSteps() + "/" 
                                   + theGlueState.getLastReward() + "/"
                                   + theGlueState.getTotalReturn(),
                                   0.0f, 10.0f);
    
    g.setTransform(saveAT);
  }

  // update() is inherited, see superclass
  
}
