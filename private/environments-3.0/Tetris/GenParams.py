import GenParamSamples

method1 = "setWidth"
method2 = "setHeight"
method3 = "setRewardExponent"
method4 = "setPieceWeights"

f = open('./src/GeneralizedTetris/GenTetrisParamData.java','w')

f.write("""
/*
 * This code was auto-generated by the parameter script. Do not change these values manually.
 */
package GeneralizedTetris;

import Tetrlais09.GameState;
import Tetrlais09.TetrlaisPiece;
import java.util.Vector;

public class GenTetrisParamData {

    GameState setParameters(int ParamSet) {
		int width=0;
		int height=0;
		double []pieceWeights=null;
		double rewardExponent=1.0d;
		double evilness = 0.5;
		int evilAgentType = 0;
		
		Vector<TetrlaisPiece> possibleBlocks=new Vector<TetrlaisPiece>();
        possibleBlocks.add(TetrlaisPiece.makeLine());
		possibleBlocks.add(TetrlaisPiece.makeSquare());
        possibleBlocks.add(TetrlaisPiece.makeTri());
		possibleBlocks.add(TetrlaisPiece.makeSShape());
		possibleBlocks.add(TetrlaisPiece.makeZShape());
		possibleBlocks.add(TetrlaisPiece.makeLShape());
		possibleBlocks.add(TetrlaisPiece.makeJShape());
 	
        switch (ParamSet) {""")
for i in range(20):
	width=GenParamSamples.sampleP1(i)
	height=GenParamSamples.sampleP2(i)+width
	rewardExponent=GenParamSamples.sampleP3(i)
	pieceWeights=GenParamSamples.sampleP4(i)
	evilness=GenParamSamples.sampleP5(i)
	evilAgentType=GenParamSamples.sampleP6(i)
	f.write("""
          case %d:
			width=%d;
			height=%d;
			rewardExponent=%fd;
			evilness = %fd;
			evilAgentType = %d;
           """ % (i, width,  height, rewardExponent,evilness,evilAgentType))
	f.write("""
			pieceWeights=new double[]{%fd, %fd, %fd, %fd, %fd, %fd, %fd};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent,evilness,evilAgentType);
			""" %(pieceWeights[0],pieceWeights[1],pieceWeights[2],pieceWeights[3],pieceWeights[4],pieceWeights[5],pieceWeights[6]))


f.write("""
            default:
                System.err.println("Invalid Parameter Set Requested ("+ParamSet+") : using Param Set 0");
                return setParameters(0);
        }
    }
}""")
f.close()