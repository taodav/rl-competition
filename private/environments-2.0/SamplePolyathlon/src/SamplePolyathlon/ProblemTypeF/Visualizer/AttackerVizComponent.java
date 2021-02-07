/*
Copyright 2007 Brian Tanner
brian@tannerpages.com
http://brian.tannerpages.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/

  
package SamplePolyathlon.ProblemTypeF.Visualizer;
import rlVizLib.visualization.interfaces.AgentOnValueFunctionDataProvider;
import rlVizLib.utilities.UtilityShop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import rlVizLib.visualization.VizComponent;


public class AttackerVizComponent implements VizComponent {
	private AbstractPolyProblemTypeFVisualizer dataProvider=null;

	public AttackerVizComponent(AbstractPolyProblemTypeFVisualizer dataProvider){
		this.dataProvider=dataProvider;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.GREEN);

		double transX=UtilityShop.normalizeValue( dataProvider.getCurrentStateInDimension(2),
				dataProvider.getMinValueForDim(2),
				dataProvider.getMaxValueForDim(2));

		double transY=UtilityShop.normalizeValue( dataProvider.getCurrentStateInDimension(3),
				dataProvider.getMinValueForDim(3),
				dataProvider.getMaxValueForDim(3));

                                //System.out.println("Vis::Current state:"+dataProvider.getCurrentStateInDimension(2)+","+dataProvider.getCurrentStateInDimension(3));
		Rectangle2D attackerRect=new Rectangle2D.Double(transX,transY,.04,.04);
		g.fill(attackerRect);
	}

	public boolean update() {
		dataProvider.updateAgentState();
		return true;
	}

}
