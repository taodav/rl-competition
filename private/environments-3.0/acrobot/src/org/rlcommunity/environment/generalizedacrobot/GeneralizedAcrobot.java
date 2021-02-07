package org.rlcommunity.environment.generalizedacrobot;

/* Acrobot Domain  for RL - Competition - 
* Copyright (C) 2007, Jose Antonio Martin H., Brian Tanner, Adam White and Richard S. Sutton
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


import org.rlcommunity.environment.acrobot.Acrobot;
import rlVizLib.general.hasVersionDetails;
import rlVizLib.general.ParameterHolder;

/**
 * @author btanner
 */
public class GeneralizedAcrobot extends Acrobot {

    public GeneralizedAcrobot() {
        this(getDefaultParameters());
    }

    public GeneralizedAcrobot(ParameterHolder P) {
        super();
        int paramSet = P.getIntegerParam("pnum");
        GenAcrobotParamData theParamSetter = new GenAcrobotParamData();
        theParamSetter.setParameters(paramSet, this);
    }

    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        rlVizLib.utilities.UtilityShop.setVersionDetails(p, new DetailsProvider());
        p.addIntegerParam("ParameterSet [0,39]", 0);
        p.setAlias("pnum", "ParameterSet [0,39]");
        return p;
    }
    
    
   	
}
   	           


class DetailsProvider implements hasVersionDetails {

    public String getName() {
        return "Generalized Acrobot 1.1";
    }

    public String getShortName() {
        return "GAcro";
    }

    public String getAuthors() {
        return "Jose Antonio Martin H. from Brian Tanner from Adam White from Richard S. Sutton?";
    }

    public String getInfoUrl() {
        return "http://rl-competition.org";
    }

    public String getDescription() {
        return "Generalized RL-Competition 2009 Java Version of the Acrobot Problem. see Sutton and Barto RL Book pp. 271";
    }
}