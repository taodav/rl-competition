
/*
 * This code was auto-generated by the parameter script. Do not change these values manually.
 */
package org.rlcommunity.environments.competition2009.Tetris.Proving;

import org.rlcommunity.environments.competition2009.Tetris.GameState;
import org.rlcommunity.environments.competition2009.Tetris.TetrlaisPiece;
import java.util.Vector;
import org.rlcommunity.environments.competition2009.Tetris.Generalized.*;

public class TPMDP65 extends AbstractProvingMDPTetris{

    public TPMDP65(){
	super();
	int width=0;
	int height=0;
	double []pieceWeights=null;
	double rewardExponent=1.0d;
	double evilness = 0.0d;
	int evilAgentType = 0;
	double benchmarkScore = 0.0;
		
	Vector<TetrlaisPiece> possibleBlocks=new Vector<TetrlaisPiece>();
        possibleBlocks.add(TetrlaisPiece.makeLine());
	possibleBlocks.add(TetrlaisPiece.makeSquare());
        possibleBlocks.add(TetrlaisPiece.makeTri());
	possibleBlocks.add(TetrlaisPiece.makeSShape());
	possibleBlocks.add(TetrlaisPiece.makeZShape());
	possibleBlocks.add(TetrlaisPiece.makeLShape());
	possibleBlocks.add(TetrlaisPiece.makeJShape());

	width=11;
	height=26;
	rewardExponent=1.333713d;
	evilness=0.445010d;
	evilAgentType=1;
	benchmarkScore=2377.000000d;
            
	pieceWeights=new double[]{0.521754d, 0.648089d, 0.764572d, 0.514645d, 0.853309d, 0.736249d, 0.692187d};
	GameState g=new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent,evilness,evilAgentType,benchmarkScore);    
        super.gameState=g;
    }
}