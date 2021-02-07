
/*
 * This code was auto-generated by the parameter script. Do not change these values manually.
 */
package GeneralizedTetris;

import Tetrlais.GameState;
import Tetrlais.TetrlaisPiece;
import java.util.Vector;

public class GenTetrisParamData {

    GameState setParameters(int ParamSet) {
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
		
        switch (ParamSet) {
          case 0:
			width=10;
			height=25;
			rewardExponent=1.826788d;
            
			pieceWeights=new double[]{0.895452d, 0.540671d, 0.355730d, 0.529689d, 0.932010d, 0.652116d, 0.893341d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 1:
			width=10;
			height=20;
			rewardExponent=1.468610d;
            
			pieceWeights=new double[]{0.410731d, 0.009658d, 0.961275d, 0.311205d, 0.783971d, 0.081657d, 0.955868d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 2:
			width=12;
			height=26;
			rewardExponent=1.594506d;
            
			pieceWeights=new double[]{0.145114d, 0.922956d, 0.497115d, 0.005984d, 0.437367d, 0.150663d, 0.554916d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 3:
			width=7;
			height=20;
			rewardExponent=1.052249d;
            
			pieceWeights=new double[]{0.843390d, 0.585378d, 0.399049d, 0.599471d, 0.024176d, 0.451182d, 0.880184d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 4:
			width=7;
			height=21;
			rewardExponent=1.365461d;
            
			pieceWeights=new double[]{0.867438d, 0.321155d, 0.342841d, 0.684024d, 0.491422d, 0.696306d, 0.189857d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 5:
			width=7;
			height=23;
			rewardExponent=1.664658d;
            
			pieceWeights=new double[]{0.702762d, 0.515436d, 0.159674d, 0.999219d, 0.008386d, 0.764475d, 0.489203d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 6:
			width=6;
			height=18;
			rewardExponent=1.793637d;
            
			pieceWeights=new double[]{0.978731d, 0.765798d, 0.374830d, 0.683390d, 0.929183d, 0.369446d, 0.038835d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 7:
			width=8;
			height=19;
			rewardExponent=1.091157d;
            
			pieceWeights=new double[]{0.227151d, 0.466518d, 0.771597d, 0.079141d, 0.111145d, 0.166515d, 0.196813d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 8:
			width=12;
			height=27;
			rewardExponent=1.326371d;
            
			pieceWeights=new double[]{0.418461d, 0.849067d, 0.618749d, 0.564200d, 0.405030d, 0.679300d, 0.396443d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 9:
			width=7;
			height=19;
			rewardExponent=1.857954d;
            
			pieceWeights=new double[]{0.674812d, 0.875450d, 0.659185d, 0.866370d, 0.290603d, 0.865514d, 0.760998d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 10:
			width=7;
			height=20;
			rewardExponent=1.843211d;
            
			pieceWeights=new double[]{0.795273d, 0.573177d, 0.919943d, 0.620058d, 0.473673d, 0.199570d, 0.412175d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 11:
			width=12;
			height=28;
			rewardExponent=1.336014d;
            
			pieceWeights=new double[]{0.333467d, 0.829007d, 0.960159d, 0.876504d, 0.345769d, 0.623917d, 0.606574d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 12:
			width=11;
			height=22;
			rewardExponent=1.442070d;
            
			pieceWeights=new double[]{0.135095d, 0.146457d, 0.577906d, 0.511840d, 0.342874d, 0.043406d, 0.733688d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 13:
			width=8;
			height=19;
			rewardExponent=1.719012d;
            
			pieceWeights=new double[]{0.245893d, 0.105216d, 0.638655d, 0.245991d, 0.928137d, 0.374556d, 0.851514d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 14:
			width=7;
			height=18;
			rewardExponent=1.036948d;
            
			pieceWeights=new double[]{0.913634d, 0.915861d, 0.036011d, 0.989656d, 0.671982d, 0.265075d, 0.710023d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 15:
			width=11;
			height=24;
			rewardExponent=1.054353d;
            
			pieceWeights=new double[]{0.182447d, 0.459215d, 0.008653d, 0.185526d, 0.139011d, 0.210035d, 0.720135d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 16:
			width=11;
			height=23;
			rewardExponent=1.724345d;
            
			pieceWeights=new double[]{0.427642d, 0.054059d, 0.883581d, 0.467551d, 0.361810d, 0.442644d, 0.458792d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 17:
			width=7;
			height=17;
			rewardExponent=1.229009d;
            
			pieceWeights=new double[]{0.556025d, 0.684729d, 0.338994d, 0.020028d, 0.755446d, 0.055073d, 0.035003d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 18:
			width=11;
			height=22;
			rewardExponent=1.176858d;
            
			pieceWeights=new double[]{0.473737d, 0.854931d, 0.833521d, 0.139322d, 0.629932d, 0.295990d, 0.244317d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
          case 19:
			width=9;
			height=23;
			rewardExponent=1.453354d;
            
			pieceWeights=new double[]{0.379248d, 0.075014d, 0.259124d, 0.565923d, 0.060191d, 0.802315d, 0.388120d};
			return new GameState(width,height,possibleBlocks,pieceWeights,rewardExponent);
			
            default:
                System.err.println("Invalid Parameter Set Requested ("+ParamSet+") : using Param Set 0");
                return setParameters(0);
        }
    }
}