/* Mountain Car Domain
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


package GeneralizedMountainCar;

import MountainCar.MountainCar;
import rlVizLib.general.ParameterHolder;
import rlVizLib.general.hasVersionDetails;

/**
 *
 * @author btanner
 */
public class GeneralizedMountainCar extends MountainCar {

	public static ParameterHolder getDefaultParameters(){
            ParameterHolder p=new ParameterHolder();
            rlVizLib.utilities.UtilityShop.setVersionDetails(p, new DetailsProvider());
            p.addIntegerParam("ParameterSet [0,34]", 0);
            p.setAlias("pnum", "ParameterSet [0,34]");
           
            return p;
        }
        
        public GeneralizedMountainCar(ParameterHolder P){
            super(true);
            int ParamSet=P.getIntegerParam("pnum");
            

            GenMCParamData theParamSetter=new GenMCParamData();
            
            theParamSetter.setParameters(ParamSet,this);
            
        }
            
      	public String getVisualizerClassName() {
		return "visualizers.mountainCar.GeneralizedMountainCarVisualizer";
	}

    void setAccelBiasMean(double d) {
        theState.AccelBiasMean=d;
    }

    void setPNoiseDivider(double d) {
        theState.pNoiseDivider=d;
    }
    void setVNoiseDivider(double d) {
        theState.vNoiseDivider=d;
    }

    void setPOffset(double d) {
        theState.pOffset=d;
    }
    void setVOffset(double d) {
        theState.vOffset=d;
    }

    void setScaleP(double d) {
        theState.scaleP=d;
    }
    void setScaleV(double d) {
        theState.scaleV=d;
    }


}
class DetailsProvider implements hasVersionDetails{
    public String getName() {
        return "Generalized Mountain Car 1.01";
    }

    public String getShortName() {
        return "RLC-Mount-Car";
    }

    public String getAuthors() {
        return "Richard Sutton, Adam White, Brian Tanner";
    }

    public String getInfoUrl() {
        return "http://rl-competition.org";
    }

    public String getDescription() {
        return "Generalized RL-Competition 2008 Java Version of the classic Mountain Car RL-Problem.";
    }
}
