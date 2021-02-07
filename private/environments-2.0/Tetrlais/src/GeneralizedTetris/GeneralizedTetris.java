/* Tetris Domain
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

package GeneralizedTetris;

import Tetrlais.Tetrlais;
import rlVizLib.general.ParameterHolder;
import rlVizLib.general.hasVersionDetails;

/**
 *
 * @author btanner
 */
public class GeneralizedTetris extends Tetrlais{

	public static ParameterHolder getDefaultParameters(){
            ParameterHolder p=new ParameterHolder();
                        rlVizLib.utilities.UtilityShop.setVersionDetails(p, new DetailsProvider());
            p.addIntegerParam("ParameterSet [0,19]", 0);
            p.setAlias("pnum", "ParameterSet [0,19]");
           
            return p;
        }
        
        public GeneralizedTetris(ParameterHolder P){
            super();
            int ParamSet=P.getIntegerParam("pnum");

            GenTetrisParamData theParamSetter=new GenTetrisParamData();
            
            gameState=theParamSetter.setParameters(ParamSet);
        }
 
    @Override
      	public String getVisualizerClassName() {
		return "visualizers.Tetrlais.GeneralizedTetrisVisualizer";
	}

}


class DetailsProvider implements hasVersionDetails{
    public String getName() {
        return "Generalized Tetris 1.0";
    }

    public String getShortName() {
        return "RLC-Tetris";
    }

    public String getAuthors() {
        return "Brian Tanner, Leah Hackman, Matt Radkie, Andrew Butcher";
    }

    public String getInfoUrl() {
        return "http://rl-competition.org";
    }

    public String getDescription() {
        return "Generalized RL-Competition 2008 Java Version of the Tetris RL-Problem.";
    }
}