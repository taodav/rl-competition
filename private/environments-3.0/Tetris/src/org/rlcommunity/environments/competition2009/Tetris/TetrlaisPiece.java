// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TetrlaisPiece.java

package org.rlcommunity.environments.competition2009.Tetris;

import java.io.PrintStream;

public class TetrlaisPiece
{

    public TetrlaisPiece()
    {
        thePiece = new int[4][5][5];
        currentOrientation = 0;
    }

    public void setShape(int Direction, int row0[], int row1[], int row2[], int row3[], int row4[])
    {
        thePiece[Direction][0] = row0;
        thePiece[Direction][1] = row1;
        thePiece[Direction][2] = row2;
        thePiece[Direction][3] = row3;
        thePiece[Direction][4] = row4;
    }

    public int[][] getShape(int whichOrientation)
    {
        return thePiece[whichOrientation];
    }

    public static TetrlaisPiece makeSquare()
    {
        TetrlaisPiece newPiece = new TetrlaisPiece();
        int row0[] = {
            0, 0, 0, 0, 0
        };
        int row1[] = {
            0, 0, 1, 1, 0
        };
        int row2[] = {
            0, 0, 1, 1, 0
        };
        int row3[] = {
            0, 0, 0, 0, 0
        };
        int row4[] = {
            0, 0, 0, 0, 0
        };
        newPiece.setShape(0, row0, row1, row2, row3, row4);
        newPiece.setShape(1, row0, row1, row2, row3, row4);
        newPiece.setShape(2, row0, row1, row2, row3, row4);
        newPiece.setShape(3, row0, row1, row2, row3, row4);
        return newPiece;
    }

    public static TetrlaisPiece makeTri()
    {
        TetrlaisPiece newPiece = new TetrlaisPiece();
        int row0[] = {
            0, 0, 0, 0, 0
        };
        int row1[] = {
            0, 0, 1, 0, 0
        };
        int row2[] = {
            0, 1, 1, 1, 0
        };
        int row3[] = {
            0, 0, 0, 0, 0
        };
        int row4[] = {
            0, 0, 0, 0, 0
        };
        newPiece.setShape(0, row0, row1, row2, row3, row4);
        row0 = (new int[] {
            0, 0, 0, 0, 0
        });
        row1 = (new int[] {
            0, 0, 1, 0, 0
        });
        row2 = (new int[] {
            0, 0, 1, 1, 0
        });
        row3 = (new int[] {
            0, 0, 1, 0, 0
        });
        row4 = (new int[] {
            0, 0, 0, 0, 0
        });
        newPiece.setShape(1, row0, row1, row2, row3, row4);
        row0 = (new int[] {
            0, 0, 0, 0, 0
        });
        row1 = (new int[] {
            0, 0, 0, 0, 0
        });
        row2 = (new int[] {
            0, 1, 1, 1, 0
        });
        row3 = (new int[] {
            0, 0, 1, 0, 0
        });
        row4 = (new int[] {
            0, 0, 0, 0, 0
        });
        newPiece.setShape(2, row0, row1, row2, row3, row4);
        row0 = (new int[] {
            0, 0, 0, 0, 0
        });
        row1 = (new int[] {
            0, 0, 1, 0, 0
        });
        row2 = (new int[] {
            0, 1, 1, 0, 0
        });
        row3 = (new int[] {
            0, 0, 1, 0, 0
        });
        row4 = (new int[] {
            0, 0, 0, 0, 0
        });
        newPiece.setShape(3, row0, row1, row2, row3, row4);
        return newPiece;
    }

    public static TetrlaisPiece makeLine()
    {
        TetrlaisPiece newPiece = new TetrlaisPiece();
        int row0[] = {
            0, 0, 1, 0, 0
        };
        int row1[] = {
            0, 0, 1, 0, 0
        };
        int row2[] = {
            0, 0, 1, 0, 0
        };
        int row3[] = {
            0, 0, 1, 0, 0
        };
        int row4[] = {
            0, 0, 0, 0, 0
        };
        newPiece.setShape(0, row0, row1, row2, row3, row4);
        newPiece.setShape(2, row0, row1, row2, row3, row4);
        row0 = (new int[] {
            0, 0, 0, 0, 0
        });
        row1 = (new int[] {
            0, 0, 0, 0, 0
        });
        row2 = (new int[] {
            0, 1, 1, 1, 1
        });
        row3 = (new int[] {
            0, 0, 0, 0, 0
        });
        row4 = (new int[] {
            0, 0, 0, 0, 0
        });
        newPiece.setShape(1, row0, row1, row2, row3, row4);
        newPiece.setShape(3, row0, row1, row2, row3, row4);
        return newPiece;
    }

    public static TetrlaisPiece makeSShape()
    {
        TetrlaisPiece newPiece = new TetrlaisPiece();
        int row0[] = {
            0, 0, 0, 0, 0
        };
        int row1[] = {
            0, 1, 0, 0, 0
        };
        int row2[] = {
            0, 1, 1, 0, 0
        };
        int row3[] = {
            0, 0, 1, 0, 0
        };
        int row4[] = {
            0, 0, 0, 0, 0
        };
        newPiece.setShape(0, row0, row1, row2, row3, row4);
        newPiece.setShape(2, row0, row1, row2, row3, row4);
        row0 = (new int[] {
            0, 0, 0, 0, 0
        });
        row1 = (new int[] {
            0, 0, 1, 1, 0
        });
        row2 = (new int[] {
            0, 1, 1, 0, 0
        });
        row3 = (new int[] {
            0, 0, 0, 0, 0
        });
        row4 = (new int[] {
            0, 0, 0, 0, 0
        });
        newPiece.setShape(1, row0, row1, row2, row3, row4);
        newPiece.setShape(3, row0, row1, row2, row3, row4);
        return newPiece;
    }

    public static TetrlaisPiece makeZShape()
    {
        TetrlaisPiece newPiece = new TetrlaisPiece();
        int row0[] = {
            0, 0, 0, 0, 0
        };
        int row1[] = {
            0, 0, 1, 0, 0
        };
        int row2[] = {
            0, 1, 1, 0, 0
        };
        int row3[] = {
            0, 1, 0, 0, 0
        };
        int row4[] = {
            0, 0, 0, 0, 0
        };
        newPiece.setShape(0, row0, row1, row2, row3, row4);
        newPiece.setShape(2, row0, row1, row2, row3, row4);
        row0 = (new int[] {
            0, 0, 0, 0, 0
        });
        row1 = (new int[] {
            0, 1, 1, 0, 0
        });
        row2 = (new int[] {
            0, 0, 1, 1, 0
        });
        row3 = (new int[] {
            0, 0, 0, 0, 0
        });
        row4 = (new int[] {
            0, 0, 0, 0, 0
        });
        newPiece.setShape(1, row0, row1, row2, row3, row4);
        newPiece.setShape(3, row0, row1, row2, row3, row4);
        return newPiece;
    }

    public static TetrlaisPiece makeLShape()
    {
        TetrlaisPiece newPiece = new TetrlaisPiece();
        int row0[] = {
            0, 0, 0, 0, 0
        };
        int row1[] = {
            0, 0, 1, 0, 0
        };
        int row2[] = {
            0, 0, 1, 0, 0
        };
        int row3[] = {
            0, 0, 1, 1, 0
        };
        int row4[] = {
            0, 0, 0, 0, 0
        };
        newPiece.setShape(0, row0, row1, row2, row3, row4);
        row0 = (new int[] {
            0, 0, 0, 0, 0
        });
        row1 = (new int[] {
            0, 0, 0, 0, 0
        });
        row2 = (new int[] {
            0, 1, 1, 1, 0
        });
        row3 = (new int[] {
            0, 1, 0, 0, 0
        });
        row4 = (new int[] {
            0, 0, 0, 0, 0
        });
        newPiece.setShape(1, row0, row1, row2, row3, row4);
        row0 = (new int[] {
            0, 0, 0, 0, 0
        });
        row1 = (new int[] {
            0, 1, 1, 0, 0
        });
        row2 = (new int[] {
            0, 0, 1, 0, 0
        });
        row3 = (new int[] {
            0, 0, 1, 0, 0
        });
        row4 = (new int[] {
            0, 0, 0, 0, 0
        });
        newPiece.setShape(2, row0, row1, row2, row3, row4);
        row0 = (new int[] {
            0, 0, 0, 0, 0
        });
        row1 = (new int[] {
            0, 0, 0, 1, 0
        });
        row2 = (new int[] {
            0, 1, 1, 1, 0
        });
        row3 = (new int[] {
            0, 0, 0, 0, 0
        });
        row4 = (new int[] {
            0, 0, 0, 0, 0
        });
        newPiece.setShape(3, row0, row1, row2, row3, row4);
        return newPiece;
    }

    public static TetrlaisPiece makeJShape()
    {
        TetrlaisPiece newPiece = new TetrlaisPiece();
        int row0[] = {
            0, 0, 0, 0, 0
        };
        int row1[] = {
            0, 0, 1, 0, 0
        };
        int row2[] = {
            0, 0, 1, 0, 0
        };
        int row3[] = {
            0, 1, 1, 0, 0
        };
        int row4[] = {
            0, 0, 0, 0, 0
        };
        newPiece.setShape(0, row0, row1, row2, row3, row4);
        row0 = (new int[] {
            0, 0, 0, 0, 0
        });
        row1 = (new int[] {
            0, 1, 0, 0, 0
        });
        row2 = (new int[] {
            0, 1, 1, 1, 0
        });
        row3 = (new int[] {
            0, 0, 0, 0, 0
        });
        row4 = (new int[] {
            0, 0, 0, 0, 0
        });
        newPiece.setShape(1, row0, row1, row2, row3, row4);
        row0 = (new int[] {
            0, 0, 0, 0, 0
        });
        row1 = (new int[] {
            0, 0, 1, 1, 0
        });
        row2 = (new int[] {
            0, 0, 1, 0, 0
        });
        row3 = (new int[] {
            0, 0, 1, 0, 0
        });
        row4 = (new int[] {
            0, 0, 0, 0, 0
        });
        newPiece.setShape(2, row0, row1, row2, row3, row4);
        row0 = (new int[] {
            0, 0, 0, 0, 0
        });
        row1 = (new int[] {
            0, 0, 0, 0, 0
        });
        row2 = (new int[] {
            0, 1, 1, 1, 0
        });
        row3 = (new int[] {
            0, 0, 0, 1, 0
        });
        row4 = (new int[] {
            0, 0, 0, 0, 0
        });
        newPiece.setShape(3, row0, row1, row2, row3, row4);
        return newPiece;
    }

    public String toString()
    {
        StringBuffer shapeBuffer = new StringBuffer();
        for(int i = 0; i < thePiece[currentOrientation].length; i++)
        {
            for(int j = 0; j < thePiece[currentOrientation][i].length; j++)
                shapeBuffer.append((new StringBuilder()).append(" ").append(thePiece[currentOrientation][i][j]).toString());

            shapeBuffer.append("\n");
        }

        return shapeBuffer.toString();
    }

    public static void main(String args[])
    {
        TetrlaisPiece thePiece = makeTri();
        System.out.println(thePiece);
        thePiece.currentOrientation = 1;
        System.out.println(thePiece);
        thePiece.currentOrientation = 2;
        System.out.println(thePiece);
        thePiece.currentOrientation = 3;
        System.out.println(thePiece);
    }

    int thePiece[][][];
    int currentOrientation;
}
