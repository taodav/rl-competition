// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StandardTetrisAgent.java

package org.rlcommunity.environments.competition2009.Tetris;

import java.io.PrintStream;
import java.util.Random;
import org.rlcommunity.rlglue.codec.AgentInterface;
import org.rlcommunity.rlglue.codec.taskspec.TaskSpec;
import org.rlcommunity.rlglue.codec.taskspec.ranges.DoubleRange;
import org.rlcommunity.rlglue.codec.taskspec.ranges.IntRange;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;

// Referenced classes of package org.rlcommunity.environments.competition2009.Tetris:
//            SimulatedTetrisGame

public class StandardTetrisAgent
    implements AgentInterface
{

    public StandardTetrisAgent(int pType)
    {
        numInts = 1;
        numDoubles = 0;
        random = new Random();
        game = null;
        diffrewlist = new double[100];
        ndiffrews = 0;
        TSO = null;
        aseq = new int[20];
        rawboard = new int[20][40];
        cePiece = 0;
        cePos = 0;
        ceRot = 0;
        pos0 = -1;
        type = pType;
    }

    public void agent_cleanup()
    {
        double expMoves = ((double)width - 2.4199999999999999D - 1.0D) / 2D + 0.64290000000000003D + 1.0D;
        maxRew = ((5000000D / expMoves) * 4D) / (double)width;
    }

    public void agent_end(double arg0)
    {
        totalSteps++;
        totalRew += arg0;
    }

    public void agent_freeze()
    {
    }

    public void agent_init(String taskSpec)
    {
        TSO = new TaskSpec(taskSpec);
        firstActionOfEpisode = true;
        action = new Action(TSO.getNumDiscreteActionDims(), TSO.getNumDiscreteActionDims());
        totalRew = 0.0D;
        totalSteps = 0;
        ndiffrews = 0;
    }

    public String agent_message(String arg0)
    {
        return null;
    }

    public void startPhase(int i)
    {
    }

    public Action agent_start(Observation o)
    {
        action.intArray[0] = 4;
        return agent_step(0.0D, o);
    }

    public Action agent_step(double arg0, Observation o)
    {
        int len = o.intArray.length;
        randomify(action);
        totalSteps++;
        totalRew += arg0;
        height = o.intArray[len - 2];
        width = o.intArray[len - 1];
        piece = -1;
        int i;
        for(i = 0; i < 7; i++)
            if(o.intArray[(len - 9) + i] == 1)
                piece = i;

        for(i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
                rawboard[i][j] = o.intArray[j * width + i];

        }

        boolean isnewpiece = false;
        i = 0;
        do
        {
            if(i >= width)
                break;
            if(rawboard[i][0] != 0)
            {
                isnewpiece = true;
                break;
            }
            i++;
        } while(true);
        if(isnewpiece)
        {
            pos0 = i;
            for(int k = 0; k < 4; k++)
                rawboard[pos0 + dpos[piece][k][0]][dpos[piece][k][1]] = 2;

        }
        if(firstActionOfEpisode)
        {
            game = new SimulatedTetrisGame(width, height, 150);
            game.setValues(type);
            firstActionOfEpisode = false;
        }
        if(totalSteps % 50000 == 1)
            startPhase(totalSteps / 50000);
        if(isnewpiece)
        {
            cePiece = PieceToCe[piece];
            ceRot = Rot0ToCe[piece];
            cePos = pos0 + Pos0ToCe[ceRot][piece];
            game.clearBoard();
            for(i = 0; i < width; i++)
            {
                for(int j = 0; j < height; j++)
                    game.board[i + 3][j + 3] = rawboard[i][j] != 1 ? 0 : 1;

            }

            game.UpdateSkyline();
            game.putTileGreedy(cePiece, true);
        }
        cePos = pos0 + Pos0ToCe[ceRot][piece];
        int nrots = 4;
        if(piece == 0 || piece == 3 || piece == 4)
            nrots = 2;
        if((piece == 0 || piece == 3 || piece == 4) && game.bestrot >= 2)
            game.bestrot -= 2;
        if(piece == 1)
            game.bestrot = 0;
        int a;
        if(isnewpiece && cePos > game.bestpos)
            a = 0;
        else
        if(isnewpiece && cePos < game.bestpos)
            a = 1;
        else
        if(isnewpiece && cePos == game.bestpos)
            a = 4;
        else
        if(ceRot != game.bestrot && (ceRot == game.bestrot + 1 || ceRot + nrots == game.bestrot + 1))
            a = 2;
        else
        if(ceRot != game.bestrot && (ceRot == game.bestrot - 1 || ceRot - nrots == game.bestrot - 1))
            a = 3;
        else
        if(ceRot != game.bestrot && (ceRot == game.bestrot + 2 || ceRot + nrots == game.bestrot + 2))
            a = 2;
        else
        if(ceRot == game.bestrot && cePos > game.bestpos)
            a = 0;
        else
        if(ceRot == game.bestrot && cePos < game.bestpos)
            a = 1;
        else
        if(ceRot == game.bestrot && cePos == game.bestpos)
        {
            a = 5;
        } else
        {
            System.out.printf("%d: (%d,%d) vs (%d,%d) \n", new Object[] {
                Integer.valueOf(piece), Integer.valueOf(cePos), Integer.valueOf(ceRot), Integer.valueOf(game.bestpos), Integer.valueOf(game.bestrot)
            });
            a = 4;
        }
        action.intArray[0] = a;
        switch(action.intArray[0])
        {
        case 0: // '\0'
            pos0--;
            break;

        case 1: // '\001'
            pos0++;
            break;

        case 2: // '\002'
            ceRot--;
            if(ceRot < 0)
                ceRot = 3;
            break;

        case 3: // '\003'
            ceRot++;
            if(ceRot >= 4)
                ceRot = 0;
            break;
        }
        if((piece == 0 || piece == 3 || piece == 4) && ceRot >= 2)
            ceRot -= 2;
        if(piece == 1)
            ceRot = 0;
        if(Double.compare(arg0, 0.0D) != 0)
            recordReward(arg0);
        return action;
    }

    public void recordReward(double arg0)
    {
        boolean found = false;
        for(int i = 0; i < ndiffrews; i++)
            if(Double.compare(arg0, diffrewlist[i]) == 0)
                found = true;

        if(!found)
        {
            diffrewlist[ndiffrews] = arg0;
            ndiffrews++;
        }
    }

    private void randomify(Action action)
    {
        for(int i = 0; i < TSO.getNumDiscreteActionDims(); i++)
            action.intArray[i] = random.nextInt((TSO.getDiscreteActionRange(i).getMax() + 1) - TSO.getDiscreteActionRange(i).getMin()) + TSO.getDiscreteActionRange(i).getMin();

        for(int i = 0; i < TSO.getNumContinuousActionDims(); i++)
            action.doubleArray[i] = random.nextDouble() * (TSO.getContinuousActionRange(i).getMax() - TSO.getContinuousActionRange(i).getMin()) + TSO.getContinuousActionRange(i).getMin();

    }

    private Action action;
    private int numInts;
    private int numDoubles;
    private Random random;
    SimulatedTetrisGame game;
    boolean firstActionOfEpisode;
    int totalSteps;
    double totalRew;
    double maxRew;
    double diffrewlist[];
    int ndiffrews;
    int type;
    TaskSpec TSO;
    public static final int MAXWIDTH = 20;
    public static final int MAXHEIGHT = 40;
    int width;
    int height;
    int piece;
    int aseqlen;
    int aseqpos;
    int aseq[];
    public static final int PieceToCe[] = {
        0, 6, 3, 5, 4, 1, 2
    };
    public static final int Rot0ToCe[] = {
        1, 0, 2, 1, 1, 1, 3
    };
    public static final int Pos0ToCe[][] = {
        {
            2, 0, 0, 0, -1, 1, -1
        }, {
            0, 0, -1, 0, -1, 0, -2
        }, {
            0, 0, -1, 0, -1, 0, -2
        }, {
            0, 0, -1, 0, -1, 0, -2
        }
    };
    public static final int TRIALLENGTH = 50000;
    public static final int NPHASES = 6;
    public static final String phaseNames[] = {
        "medium", "low", "lowish", "medium_rarei", "high", "benchmark"
    };
    int dpos[][][] = {
        {
            {
                0, 0
            }, {
                1, 0
            }, {
                2, 0
            }, {
                3, 0
            }
        }, {
            {
                0, 0
            }, {
                1, 0
            }, {
                0, 1
            }, {
                1, 1
            }
        }, {
            {
                0, 0
            }, {
                -1, 1
            }, {
                0, 1
            }, {
                0, 2
            }
        }, {
            {
                0, 0
            }, {
                1, 0
            }, {
                1, 1
            }, {
                2, 1
            }
        }, {
            {
                0, 0
            }, {
                1, 0
            }, {
                -1, 1
            }, {
                0, 1
            }
        }, {
            {
                0, 0
            }, {
                1, 0
            }, {
                2, 0
            }, {
                2, 1
            }
        }, {
            {
                0, 0
            }, {
                0, 1
            }, {
                -1, 1
            }, {
                -2, 1
            }
        }
    };
    int rawboard[][];
    int cePiece;
    int cePos;
    int ceRot;
    int pos0;

}
