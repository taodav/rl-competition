
/*
 * This code was auto-generated by the parameter script. Do not change these values manually.
 */
package TPMDP93;

import Tetrlais.GameState;
import Tetrlais.TetrlaisPiece;
import java.util.Vector;
import GeneralizedTetris.*;

public class TPMDP93 extends AbstractProvingMDPTetris{

    public TPMDP93(){
	super();
	int width=0;
	int height=0;
	double []pieceWeights=null;
	double rewardExponent=1.0d;
		
	Vector<TetrlaisPiece> possibleBlocks=new Vector<TetrlaisPiece>();
        possibleBlocks.add(TetrlaisPiece.makeLine());
	possibleBlocks.add(TetrlaisPiece.makeSquare());
        possibleBlocks.add(TetrlaisPiece.makeTri());
	possibleBlocks.add(TetrlaisPiece.makeSShape());
	possibleBlocks.add(TetrlaisPiece.makeZShape());
	possibleBlocks.add(TetrlaisPiece.makeLShape());
	possibleBlocks.add(TetrlaisPiece.makeJShape());

	width=9;
	height=23;
	rewardExponent=1.853100d;
            
	pieceWeights=new double[]{0.690367d, 0.625503d, 0.893177d, 0.593002d, 0.595137d, 0.152286d, 0.540213d};
	GameState g=new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);    
        super.gameState=g;
    }
}