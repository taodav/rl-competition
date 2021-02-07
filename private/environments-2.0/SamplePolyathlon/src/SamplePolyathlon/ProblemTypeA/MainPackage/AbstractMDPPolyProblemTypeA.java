
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
package SamplePolyathlon.ProblemTypeA.MainPackage;

import rlVizLib.general.ParameterHolder;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Reward_observation;

/**
 *
 * @author btanner
 */
public abstract class AbstractMDPPolyProblemTypeA extends AbstractPolyProblemTypeA {

    public AbstractMDPPolyProblemTypeA(ParameterHolder P) {
        this();
    }

    public AbstractMDPPolyProblemTypeA() {
        super(true);
        super.recordLogs = true;
        super.allowSaveLoadState = false;
        super.allowSaveLoadSeed = false;
    }

    @Override
    public String getVisualizerClassName() {
        return "";//visualizers.mountainCar.GeneralizedMountainCarVisualizer";
    }

    protected abstract Observation MDPSpecificMakeObservation();
    protected abstract Action MDPSpecificActionConverter(int abstractAction);

    @Override
    public String env_init() {
        //Call env_init on super
        String superTaskSpec = super.env_init();

        return SamplePolyathlon.SamplePolyathlon.genericTaskSpec();
    }

    @Override
    public Reward_observation env_step(Action theAction) {
        Action specificAction = MDPSpecificActionConverter(theAction.intArray[0]);
        return super.env_step(specificAction);
    }
    protected double nPos;
    protected double nVel;

    @Override
    protected Observation makeObservation() {
        double position = theState.position;
        double velocity = theState.velocity;

//Get them to 0,1
        double pRange = theState.maxPosition - theState.minPosition;
        nPos = (position - theState.minPosition) / pRange;

        double vRange = theState.maxVelocity - theState.minVelocity;
        nVel = (velocity - theState.minVelocity) / vRange;

        return MDPSpecificMakeObservation();
    }

    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
  //      rlVizLib.utilities.UtilityShop.setVersionDetails(p, new DetailsProvider());
        return p;
    }
}

