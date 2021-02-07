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

package visualizers.Tetrlais;

import rlVizLib.general.TinyGlue;
import rlVizLib.visualization.interfaces.DynamicControlTarget;

/**
 *
 * @author btanner
 */
public class GeneralizedTetrisVisualizer extends TetrlaisVisualizer{

    public GeneralizedTetrisVisualizer(TinyGlue theGlueState, DynamicControlTarget theControlTarget){
        super(theGlueState,theControlTarget);
    }
    @Override
protected void addDesiredExtras(){
addPreferenceComponents();
}

            public String getName(){
        return "Tetris 1.0";
    }

}
