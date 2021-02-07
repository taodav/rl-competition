/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GeneralizedTetris;

import Tetrlais.Tetrlais;

/**
 *
 * @author btanner
 */
public abstract class AbstractProvingMDPTetris extends Tetrlais{
    
    public AbstractProvingMDPTetris(){
        super();
        super.recordLogs=true;
        super.allowSaveLoadState=false;
        super.allowSaveLoadSeed=false;
    }
}
