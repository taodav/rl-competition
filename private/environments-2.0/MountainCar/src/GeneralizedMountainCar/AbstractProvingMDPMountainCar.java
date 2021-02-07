
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

/**
 *
 * @author btanner
 */
public abstract class AbstractProvingMDPMountainCar extends MountainCar {
        
        public AbstractProvingMDPMountainCar(){
            super(true);
            super.recordLogs=true;
            super.allowSaveLoadState=false;
            super.allowSaveLoadSeed=false;

        }
            
    @Override
      	public String getVisualizerClassName() {
		return "visualizers.mountainCar.GeneralizedMountainCarVisualizer";
	}

protected    void setAccelBiasMean(double d) {
        theState.AccelBiasMean=d;
    }

protected    void setPNoiseDivider(double d) {
        theState.pNoiseDivider=d;
    }
protected    void setVNoiseDivider(double d) {
        theState.vNoiseDivider=d;
    }

protected    void setPOffset(double d) {
        theState.pOffset=d;
    }
protected    void setVOffset(double d) {
        theState.vOffset=d;
    }

protected    void setScaleP(double d) {
        theState.scaleP=d;
    }
protected    void setScaleV(double d) {
        theState.scaleV=d;
    }


}
