
/*
 * This code was auto-generated by the parameter script. Do not change these values manually.
 */
package org.rlcommunity.environments.competition2009.Tetris.Proving;

import org.rlcommunity.environments.competition2009.Tetris.GameState;
import org.rlcommunity.environments.competition2009.Tetris.TetrlaisPiece;
import java.util.Vector;
import org.rlcommunity.environments.competition2009.Tetris.Generalized.*;

public class TPMDP97 extends AbstractProvingMDPTetris{

    public TPMDP97(){
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
	height=25;
	rewardExponent=1.702379d;
	evilness=0.470092d;
	evilAgentType=3;
	benchmarkScore=2399.000000d;
            
	pieceWeights=new double[]{0.729121d, 0.762613d, 0.930273d, 0.823012d, 0.661017d, 0.523938d, 0.834862d};
	GameState g=new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent,evilness,evilAgentType,benchmarkScore);    
        super.gameState=g;
    }
}