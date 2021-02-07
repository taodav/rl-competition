// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimulatedTetrisGame.java

package org.rlcommunity.environments.competition2009.Tetris;

import java.awt.Color;
import java.io.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimulatedTetrisGame
{

    public SimulatedTetrisGame()
    {
        this(10, 18, 150);
    }

    public SimulatedTetrisGame(int width, int height, int strength)
    {
        tiles = new int[7][4][4][4];
        tilebottoms = new int[7][4][4];
        bestrot = 0;
        bestpos = 0;
        lastLandingHeight = 0;
        lastResult = 0;
        NUM_AGENTTYPES = 6;
        nmoves = 0;
        this.width = width;
        this.height = height;
        NBASISFUNCTIONS = (width + (width - 1) + 11) * 1;
        OFS_HEIGHTS = 0;
        OFS_DELTAS = OFS_HEIGHTS + width;
        OFS_MISC = OFS_DELTAS + (width - 1);
        phi = new int[NBASISFUNCTIONS];
        w = new double[NBASISFUNCTIONS];
        board = new int[width + 6][height + 6];
        workboard = new int[width + 6][height + 6];
        savedboard = new int[width + 6][height + 6];
        wb = new int[width + 6][height + 6];
        skyline = new int[width + 6];
        clearBoard();
        rnd = new Random();
        for(int i = 0; i < 7; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                int tile[][] = generateTile(i, j);
                for(int x = 0; x < 4; x++)
                {
                    int last = -100;
                    for(int y = 0; y < 4; y++)
                    {
                        tiles[i][j][x][y] = tile[x][y];
                        if(tile[x][y] != 0)
                            last = y;
                    }

                    tilebottoms[i][j][x] = last;
                }

            }

        }

        setValues(0);
    }

    public void setValues(int type)
    {
        NBASISFUNCTIONS = (width + (width - 1) + 11) * 1;
        OFS_HEIGHTS = 0;
        OFS_DELTAS = OFS_HEIGHTS + width;
        OFS_MISC = OFS_DELTAS + (width - 1);
        phi = new int[NBASISFUNCTIONS];
        w = vfn[type][width];
    }

    public void LoadValues(String prefix)
    {
        NBASISFUNCTIONS = (width + (width - 1) + 11) * 1;
        OFS_HEIGHTS = 0;
        OFS_DELTAS = OFS_HEIGHTS + width;
        OFS_MISC = OFS_DELTAS + (width - 1);
        phi = new int[NBASISFUNCTIONS];
        w = new double[NBASISFUNCTIONS];
        String fname = String.format("%s%d_formatlab.txt", new Object[] {
            prefix, Integer.valueOf(width)
        });
        boolean fileexists = (new File(fname)).exists();
        if(fileexists)
        {
            try
            {
                int k = 0;
                try
                {
                    in = new BufferedReader(new FileReader(fname));
                }
                catch(FileNotFoundException ex)
                {
                    Logger.getLogger(SimulatedTetrisGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                String s = in.readLine();
                String lasts;
                do
                {
                    k++;
                    lasts = s;
                    s = in.readLine();
                } while(s != null);
                lasts = lasts.replaceAll("  ", " ");
                lasts = lasts.replaceAll("  ", " ");
                lasts = lasts.replaceAll("  ", " ");
                lasts = lasts.replaceAll("  ", " ");
                String slist[] = lasts.split(" ");
                for(int i = 0; i < NBASISFUNCTIONS; i++)
                    w[i] = Double.parseDouble(slist[i + 3]);

            }
            catch(IOException ex)
            {
                Logger.getLogger(SimulatedTetrisGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
        {
            System.out.println("File not found. Using random values.");
            for(int i = 0; i < NBASISFUNCTIONS; i++)
                w[i] = rnd.nextGaussian();

        }
    }

    public void clearBoard()
    {
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
                board[i][j] = 8;

        }

        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height + 3; j++)
                board[i + 3][j] = 0;

        }

        for(int i = 0; i < width + 6; i++)
        {
            for(int j = 3; j < height + 6; j++)
                workboard[i][j] = board[i][j];

        }

        UpdateSkyline();
    }

    public int playOneGame(double weights[], int maxsteps)
    {
        int step = 0;
        int score = 0;
        clearBoard();
        for(int i = 0; i < NBASISFUNCTIONS; i++)
            w[i] = weights[i];

        do
        {
            int res = putTileGreedy();
            if(res == -1)
                break;
            step++;
            score += scoretable[res];
        } while(step <= maxsteps);
        return score;
    }

    public double playNSteps(double weights[], int maxsteps)
    {
        int step = 0;
        int score = 0;
        int ntotalmoves = 0;
        clearBoard();
        for(int i = 0; i < NBASISFUNCTIONS; i++)
            w[i] = weights[i];

        do
        {
            int res = putTileAndCountMoves();
            if(res == -1)
            {
                clearBoard();
                res = 0;
                ntotalmoves += 2;
            } else
            {
                ntotalmoves += nmoves;
            }
            step++;
            score += scoretable[res];
        } while(step <= maxsteps);
        return ((double)score * 5000000D) / (double)ntotalmoves;
    }

    public int playOneGameCapped(double weights[], int maxsteps, double lambda[])
    {
        int step = 0;
        int score = 0;
        int hcount[] = new int[height + 1];
        clearBoard();
        for(int i = 0; i < NBASISFUNCTIONS; i++)
            w[i] = weights[i];

        do
        {
            int res = putTileGreedy();
            if(res == -1)
                break;
            step++;
            score += scoretable[res];
            int curh = height - phi[OFS_MISC + 0];
            hcount[curh]++;
        } while(step <= maxsteps);
        return score;
    }

    int[][] generateTile(int type, int rot)
    {
        int t[][];
        switch(type)
        {
        case 0: // '\0'
            t = (new int[][] {
                new int[] {
                    1, 1, 1, 1
                }, new int[] {
                    0, 0, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }
            });
            break;

        case 1: // '\001'
            t = (new int[][] {
                new int[] {
                    1, 1, 1, 0
                }, new int[] {
                    1, 0, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }
            });
            break;

        case 2: // '\002'
            t = (new int[][] {
                new int[] {
                    1, 1, 1, 0
                }, new int[] {
                    0, 0, 1, 0
                }, new int[] {
                    0, 0, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }
            });
            break;

        case 3: // '\003'
            t = (new int[][] {
                new int[] {
                    1, 1, 1, 0
                }, new int[] {
                    0, 1, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }
            });
            break;

        case 4: // '\004'
            t = (new int[][] {
                new int[] {
                    1, 1, 0, 0
                }, new int[] {
                    0, 1, 1, 0
                }, new int[] {
                    0, 0, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }
            });
            break;

        case 5: // '\005'
            t = (new int[][] {
                new int[] {
                    0, 1, 1, 0
                }, new int[] {
                    1, 1, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }
            });
            break;

        case 6: // '\006'
            t = (new int[][] {
                new int[] {
                    1, 1, 0, 0
                }, new int[] {
                    1, 1, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }
            });
            break;

        default:
            t = (new int[][] {
                new int[] {
                    1, 1, 1, 1
                }, new int[] {
                    1, 1, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }, new int[] {
                    0, 0, 0, 0
                }
            });
            break;
        }
        int t2[][] = new int[4][4];
        int x;
        int y;
label0:
        switch(rot)
        {
        default:
            break;

        case 0: // '\0'
            for(x = 0; x < 4; x++)
                for(y = 0; y < 4; y++)
                    t2[x][y] = t[x][y];


            break;

        case 1: // '\001'
            x = 0;
            do
            {
                if(x >= 4)
                    break label0;
                for(y = 0; y < 4; y++)
                    t2[x][y] = t[y][3 - x];

                x++;
            } while(true);

        case 2: // '\002'
            x = 0;
            do
            {
                if(x >= 4)
                    break label0;
                for(y = 0; y < 4; y++)
                    t2[x][y] = t[3 - x][3 - y];

                x++;
            } while(true);

        case 3: // '\003'
            x = 0;
            do
            {
                if(x >= 4)
                    break label0;
                for(y = 0; y < 4; y++)
                    t2[x][y] = t[3 - y][x];

                x++;
            } while(true);
        }
        int emptyrow = 0;
        int emptycol = 0;
        x = 0;
label1:
        do
        {
            if(x >= 4)
                break;
            for(y = 0; y < 4; y++)
                if(t2[x][y] != 0)
                    break label1;

            emptycol++;
            x++;
        } while(true);
        y = 0;
label2:
        do
        {
            if(y >= 4)
                break;
            for(x = 0; x < 4; x++)
                if(t2[x][y] != 0)
                    break label2;

            emptyrow++;
            y++;
        } while(true);
        int t3[][] = new int[4][4];
        for(x = emptycol; x < 4; x++)
            for(y = emptyrow; y < 4; y++)
                t3[x - emptycol][y - emptyrow] = t2[x][y];


        return t3;
    }

    public void UpdateSkyline()
    {
        for(int i = 0; i < skyline.length; i++)
        {
            int j;
            for(j = 0; j < board[i].length && board[i][j] == 0; j++);
            skyline[i] = j;
        }

        getBasisFunctionValues(board);
    }

    public int putTile(int b[][], int type, int rot, int pos)
    {
        int tile[][] = tiles[type][rot];
        int ofs = 10000;
        for(int x = 0; x < 4; x++)
            ofs = Math.min(ofs, skyline[x + pos + 3] - tilebottoms[type][rot][x] - 1);

        if(ofs < 3)
            return -1;
        for(int x = 0; x < 4; x++)
        {
            for(int y = 0; y < 4; y++)
                if(tile[x][y] != 0)
                    b[x + pos + 3][y + ofs] = type + 1;

        }

        lastLandingHeight = ofs;
        int result = eraseLines(b);
        int nholes = 0;
        boolean started = false;
        for(int y = 0; y < height; y++)
        {
            if(b[3][y + 3] != 0)
                started = true;
            if(started && b[3][y + 3] != 0)
                nholes++;
        }

        int compopiece = type;
        int cepiece = PieceToCe[compopiece];
        int startrot = Rot0ToCe[compopiece];
        int startpos = (width - 1) / 2;
        if(compopiece == 0)
            startpos--;
        int nm = 1 + Math.abs(startpos - pos);
        if(compopiece >= 3 && Math.abs(startrot - rot) == 2)
            nm += 2;
        if(startrot != rot)
            nm++;
        if(lastLandingHeight < nm + 1)
            result = -1;
        lastResult = result;
        return result;
    }

    int eraseLines(int b[][])
    {
        int nErased = 0;
        int debugy = 0;
        for(int y = height - 1; y >= 0; y--)
        {
            debugy++;
            boolean isfull = true;
            int x = 0;
            do
            {
                if(x >= width)
                    break;
                if(b[x + 3][y + 3] == 0)
                {
                    isfull = false;
                    break;
                }
                x++;
            } while(true);
            if(!isfull)
                continue;
            for(int y2 = y; y2 >= 0; y2--)
                for(x = 0; x < width; x++)
                    b[x + 3][y2 + 3] = b[x + 3][(y2 - 1) + 3];


            y++;
            nErased++;
        }

        return nErased;
    }

    int[] getBasisFunctionValues(int b[][])
    {
        int heights[] = new int[width];
        int maxh = 0;
        int nholes = 0;
        int coltrans = 0;
        int rowtrans = 0;
        int welldepth = 0;
        int holedepth = 0;
        for(int i = 0; i < width; i++)
        {
            int j;
            for(j = 0; j < height && b[i + 3][j + 3] == 0; j++);
            heights[i] = height - j;
            for(; j < height; j++)
                if(b[i + 3][j + 3] == 0)
                    nholes++;

            phi[OFS_HEIGHTS + i] = heights[i];
            if(i > 0)
                phi[(OFS_DELTAS + i) - 1] = Math.abs(heights[i] - heights[i - 1]);
            if(heights[i] > maxh)
                maxh = heights[i];
        }

        phi[OFS_MISC + 0] = height - maxh;
        phi[OFS_MISC + 1] = nholes;
        phi[OFS_MISC + 2] = height - lastLandingHeight;
        phi[OFS_MISC + 3] = lastResult != -1 ? scoretable2[lastResult] : 0;
        for(int j = 0; j <= height; j++)
        {
            for(int i = 0; i < width; i++)
                if((b[i + 3][(j - 1) + 3] == 0) ^ (b[i + 3][j + 3] == 0))
                    coltrans++;

        }

        phi[OFS_MISC + 5] = coltrans;
        for(int j = 0; j < height; j++)
        {
            for(int i = 0; i <= width; i++)
                if((b[(i - 1) + 3][j + 3] == 0) ^ (b[i + 3][j + 3] == 0))
                    rowtrans++;

        }

        phi[OFS_MISC + 4] = rowtrans;
        for(int i = 0; i < width; i++)
        {
            int lh = i != 0 ? heights[i - 1] : height;
            int rh = i != width - 1 ? heights[i + 1] : height;
            int h = heights[i];
            if(h < lh && h < rh)
            {
                int wd = Math.min(lh - h, rh - h);
                welldepth += (wd * (wd + 1)) / 2;
            }
        }

        phi[OFS_MISC + 6] = welldepth;
        for(int i = 0; i < width; i++)
        {
            boolean hadhole = false;
            for(int j = height - 1; j >= 0; j--)
            {
                if(b[i + 3][j + 3] == 0)
                    hadhole = true;
                if(b[i + 3][j + 3] != 0 && hadhole)
                    holedepth++;
            }

        }

        phi[OFS_MISC + 7] = holedepth;
        for(int i = 3; i < width + 3; i++)
        {
            for(int j = 3; j < height + 3; j++)
                wb[i][j] = b[i][j];

        }

        for(int i = 0; i < width; i++)
        {
            boolean started = false;
            for(int j = 0; j < height; j++)
            {
                if(wb[i + 3][j + 3] != 0)
                    started = true;
                if(started)
                    wb[i + 3][j + 3] = 1;
            }

        }

        phi[OFS_MISC + 8] = eraseLines(wb);
        if(height - maxh > 1 + width / 2)
            phi[OFS_MISC + 9] = putTile(wb, 0, 1, 0);
        else
            phi[OFS_MISC + 9] = -1;
        if(height - heights[0] > 1 + width / 2)
        {
            int nempty = 0;
            for(int i = 1; i < width; i++)
            {
                if(heights[i] < heights[0])
                {
                    nempty += 4;
                    continue;
                }
                if(heights[i] < heights[0] + 4)
                    nempty += (heights[0] + 4) - heights[i];
            }

            if(nempty == 0)
                phi[OFS_MISC + 10] = width;
            else
                phi[OFS_MISC + 10] = -nempty;
        }
        return phi;
    }

    public void putRandomTile()
    {
        int type = rnd.nextInt(7);
        int rot = rnd.nextInt(4);
        int pos = rnd.nextInt(width);
        putTile(board, type, rot, pos);
        UpdateSkyline();
    }

    public void copyWorkBoard()
    {
        for(int i = 3; i < width + 3; i++)
            System.arraycopy(board[i], 0, workboard[i], 0, height + 6);

    }

    public void debugdrawBoard()
    {
        for(int j = 0; j < height; j++)
        {
            for(int i = 0; i < width; i++)
                System.out.printf("%d", new Object[] {
                    Integer.valueOf(board[i + 3][j + 3])
                });

            System.out.println();
        }

        System.out.println("  ");
    }

    public void copyWorkBoard(int fromBoard[][], int toBoard[][])
    {
        for(int i = 3; i < width + 3; i++)
            System.arraycopy(fromBoard[i], 0, toBoard[i], 0, height + 6);

    }

    public int putTileGreedy()
    {
        return putTileGreedy(rnd.nextInt(7), true);
    }

    public int putTileGreedy(int type, boolean boolCopyWorkboard)
    {
        double bestvalue = -10000000000D;
        for(int rot = 0; rot < nrots[type]; rot++)
        {
            for(int pos = 0; pos < width; pos++)
            {
                copyWorkBoard();
                int res = putTile(workboard, type, rot, pos);
                double value;
                if(res >= 0)
                {
                    value = 0.0D;
                    getBasisFunctionValues(workboard);
                    for(int i = 0; i < NBASISFUNCTIONS; i++)
                        value += w[i] * (double)phi[i];

                } else
                {
                    value = -10000000000D;
                }
                if(value > bestvalue)
                {
                    bestvalue = value;
                    bestrot = rot;
                    bestpos = pos;
                }
            }

        }

        getBasisFunctionValues(board);
        if(bestvalue > -10000000000D)
        {
            int res = putTile(board, type, bestrot, bestpos);
            UpdateSkyline();
            return res;
        } else
        {
            return -1;
        }
    }

    public double getBestValue(int type)
    {
        double bestvalue = -10000000000D;
        for(int rot = 0; rot < nrots[type]; rot++)
        {
            for(int pos = 0; pos < width; pos++)
            {
                copyWorkBoard();
                int res = putTile(workboard, type, rot, pos);
                double value;
                if(res >= 0)
                {
                    value = 0.0D;
                    getBasisFunctionValues(workboard);
                    for(int i = 0; i < NBASISFUNCTIONS; i++)
                        value += w[i] * (double)phi[i];

                } else
                {
                    value = -10000000000D;
                }
                if(value > bestvalue)
                {
                    bestvalue = value;
                    bestrot = rot;
                    bestpos = pos;
                }
            }

        }

        getBasisFunctionValues(board);
        return bestvalue;
    }

    public int putTileLookahead(int type)
    {
        int brot = 0;
        int bpos = 0;
        int schedule[][] = new int[49][2];
        for(int it = 0; it < 49; it++)
        {
            schedule[it][0] = it % 7;
            schedule[it][1] = it / 7;
        }

        double bestvalue = -10000000000D;
        copyWorkBoard(board, savedboard);
        for(int rot = 0; rot < nrots[type]; rot++)
        {
            for(int pos = 0; pos < width; pos++)
            {
                double avgval = 0.0D;
                int nvals = 0;
                for(int it = 0; it < 49; it++)
                {
                    copyWorkBoard(savedboard, board);
                    UpdateSkyline();
                    int res = putTile(board, type, rot, pos);
                    UpdateSkyline();
                    if(res < 0)
                        continue;
                    int res2 = 0;
                    for(int d = 0; d < 2; d++)
                        if(res2 >= 0)
                            res2 = putTileGreedy(schedule[it][d], false);

                    double value = 0.0D;
                    if(res2 >= 0)
                    {
                        getBasisFunctionValues(board);
                        for(int i = 0; i < NBASISFUNCTIONS; i++)
                            value += w[i] * (double)phi[i];

                    } else
                    {
                        value = -10000000000D;
                    }
                    avgval += value;
                    nvals++;
                }

                if(nvals == 0)
                    avgval = -10000000000D;
                else
                    avgval /= 49D;
                if(avgval > bestvalue)
                {
                    bestvalue = avgval;
                    brot = rot;
                    bpos = pos;
                }
            }

        }

        copyWorkBoard(savedboard, board);
        UpdateSkyline();
        bestrot = brot;
        bestpos = bpos;
        getBasisFunctionValues(board);
        boolean hasdeepwell = true;
        for(int i = 1; i < width; i++)
            if(phi[OFS_HEIGHTS + i] <= phi[OFS_HEIGHTS + 0] + 4)
                hasdeepwell = false;

        if(hasdeepwell && type == 0)
        {
            bestrot = 0;
            bestpos = 0;
        }
        if(bestvalue > -10000000000D)
        {
            int res = putTile(board, type, bestrot, bestpos);
            UpdateSkyline();
            return res;
        } else
        {
            return -1;
        }
    }

    public int putTileAndCountMoves()
    {
        int compopiece = rnd.nextInt(7);
        int cepiece = PieceToCe[compopiece];
        int res = putTileGreedy(cepiece, true);
        int startrot = Rot0ToCe[compopiece];
        int startpos = (width - 1) / 2;
        if(compopiece == 0)
            startpos--;
        nmoves = 1 + Math.abs(startpos - bestpos);
        if(compopiece >= 3 && Math.abs(startrot - bestrot) == 2)
            nmoves += 2;
        if(startrot != bestrot)
            nmoves++;
        return res;
    }

    public static final int PADDING = 3;
    public static final int NTETROMINOES = 7;
    public static final int NROTS = 4;
    public static final int T_WALL = 8;
    public static final int T_EMPTY = 0;
    public static final Color tilecolor[];
    int tiles[][][][];
    int tilebottoms[][][];
    public static final int LANDING_HEIGHT = 2;
    public static final int ERODED_PIECES = 3;
    public static final int ROW_TRANS = 4;
    public static final int COL_TRANS = 5;
    public static final int WELLS = 6;
    public static final int HOLE_DEPTH = 7;
    public static final int INCOMPLETE_LINES = 8;
    public static final int I_FILLS = 9;
    public static final int I_DESIRABLE = 10;
    public int width;
    public int height;
    public int NBASISFUNCTIONS;
    public int OFS_HEIGHTS;
    public int OFS_DELTAS;
    public int OFS_MISC;
    public int board[][];
    public int workboard[][];
    public int savedboard[][];
    public int wb[][];
    public int skyline[];
    public int phi[];
    public double w[];
    public double w2[];
    public Random rnd;
    public int bestrot;
    public int bestpos;
    BufferedReader in;
    public static final double w0[] = {
        8.1652000000000005D, -5.2161D, -1.0396000000000001D, -3.7075999999999998D, 1.0929D, 1.8414999999999999D, -1.6900999999999999D, -0.60119D, -8.6225000000000005D, 10.965999999999999D, 
        -19.408000000000001D, -7.7038000000000002D, -11.061999999999999D, -11.180999999999999D, -12.067D, -11.731999999999999D, -16.937000000000001D, -9.0569000000000006D, -15.944000000000001D, -3.5358999999999998D, 
        -63.627000000000002D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D
    };
    private int lastLandingHeight;
    private int lastResult;
    public static final int nrots[] = {
        2, 4, 4, 4, 2, 2, 1
    };
    int NUM_AGENTTYPES;
    public static final String agentNames[] = {
        "medium", "low", "lowish", "medium_rarei", "high", "benchmark"
    };
    double vfn[][][] = {
        {
            new double[0], new double[0], new double[0], new double[0], new double[0], new double[0], {
                -11.7902D, 15.9069D, 11.585900000000001D, 17.7164D, 2.6678999999999999D, 5.6516999999999999D, 21.970700000000001D, 14.883100000000001D, -7.4249000000000001D, 2.3704000000000001D, 
                -18.2393D, -7.4452999999999996D, -76.364999999999995D, -30.5824D, 17.8203D, -48.140900000000002D, -136.3314D, -5.7956000000000003D, -16.4574D, -5.9630000000000001D, 
                47.2624D, 11.878399999999999D
            }, {
                -15.913D, 22.786200000000001D, 13.9071D, 11.8437D, 25.485199999999999D, 5.1426999999999996D, 16.7987D, 3.5167999999999999D, 1.9095D, 1.5872999999999999D, 
                -15.6678D, 3.1966000000000001D, -21.011199999999999D, -17.3551D, -67.968400000000003D, -34.234200000000001D, 27.215800000000002D, -32.699100000000001D, -121.8349D, -3.3540999999999999D, 
                -10.1106D, 7.9604999999999997D, 3.8965999999999998D, 4.2389999999999999D
            }, {
                -50.954000000000001D, 21.0929D, 19.168700000000001D, 11.539D, 20.183199999999999D, 30.392399999999999D, -4.0556999999999999D, 19.699200000000001D, 21.446899999999999D, 3.3454999999999999D, 
                1.8112999999999999D, -10.1769D, -12.413500000000001D, -1.7742D, -20.091899999999999D, 8.8815000000000008D, -94.668800000000005D, -28.0306D, 17.345700000000001D, -47.002099999999999D, 
                -170.04910000000001D, -2.7549000000000001D, -21.720700000000001D, -6.1798999999999999D, -4.8491D, 2.9137D
            }, {
                -49.183399999999999D, 7.3417000000000003D, 12.5345D, 10.1358D, 7.9179000000000004D, 14.5588D, 12.749700000000001D, 7.9745999999999997D, 7.6954000000000002D, 39.298999999999999D, 
                11.419700000000001D, 7.5540000000000003D, -6.5991999999999997D, -14.4512D, -12.810700000000001D, -6.4762000000000004D, -17.1052D, -24.397400000000001D, -103.00449999999999D, -31.096800000000002D, 
                38.706600000000002D, -36.383299999999998D, -118.14960000000001D, -3.0110000000000001D, -15.4D, 8.8201000000000001D, 10.0002D, 4.9768999999999997D
            }, 
            {
                -43.819200000000002D, 22.415500000000002D, 10.962199999999999D, 9.8946000000000005D, 10.195399999999999D, 16.637799999999999D, 13.7719D, 21.130500000000001D, -0.7288D, 9.6638999999999999D, 
                38.851399999999998D, 11.3841D, 6.5187999999999997D, -1.883D, -1.1033999999999999D, -2.2972999999999999D, -5.0163000000000002D, 2.8881000000000001D, -17.515000000000001D, -30.105399999999999D, 
                -114.8938D, -27.154199999999999D, 54.914099999999998D, -37.255099999999999D, -126.13460000000001D, -3.9811000000000001D, -16.229800000000001D, 25.153099999999998D, 34.0749D, 5.6315999999999997D
            }, {
                -73.991399999999999D, 19.563400000000001D, 5.5731000000000002D, 6.3502999999999998D, 12.097099999999999D, 12.4908D, 14.6212D, 9.0754999999999999D, 13.010899999999999D, 3.4291999999999998D, 
                0.69640000000000002D, 60.303699999999999D, 11.702500000000001D, 4.5720999999999998D, 3.4695D, 2.5962999999999998D, -5.1105999999999998D, -4.5936000000000003D, -6.5164D, -4.7716000000000003D, 
                -5.4659000000000004D, -3.1909000000000001D, -125.2413D, -14.725899999999999D, 38.665999999999997D, -33.732199999999999D, -110.68000000000001D, -6.3593999999999999D, -14.799099999999999D, 12.8658D, 
                4.0673000000000004D, 2.7421000000000002D
            }, {
                -52.085799999999999D, 28.792899999999999D, 6.6612D, 2.8653D, 10.4567D, 8.7553000000000001D, 4.7816000000000001D, 17.1494D, 4.2613000000000003D, 17.503399999999999D, 
                -2.5327999999999999D, 13.526400000000001D, 37.653100000000002D, 16.0274D, 4.8459000000000003D, -3.6890999999999998D, -0.27479999999999999D, -15.2593D, -15.247D, -6.7507000000000001D, 
                -16.424600000000002D, 4.1256000000000004D, -13.490600000000001D, 2.7844000000000002D, -87.593500000000006D, -15.9412D, 45.740600000000001D, -27.656400000000001D, -118.6781D, -3.9199000000000002D, 
                -30.938700000000001D, 8.7469000000000001D, 25.869299999999999D, 4.6383000000000001D
            }
        }, {
            new double[0], new double[0], new double[0], new double[0], new double[0], new double[0], {
                -22.202200000000001D, -1.075D, -11.111599999999999D, -0.46760000000000002D, -10.5473D, -12.2531D, 18.4724D, 3.7942D, -2.7269999999999999D, -2.9767999999999999D, 
                18.236499999999999D, 23.795999999999999D, -112.557D, -25.657399999999999D, 0.81830000000000003D, -53.9497D, -74.764200000000002D, -37.709699999999998D, -10.0594D, -47.636400000000002D, 
                -0.1142D, -2.3140000000000001D
            }, {
                -8.7850999999999999D, -24.162400000000002D, 1.8588D, 7.3106D, -20.0701D, 2.2376D, -23.535799999999998D, 4.0784000000000002D, 4.1585000000000001D, -1.2484999999999999D, 
                -0.85340000000000005D, -1.1518999999999999D, 13.9915D, 16.814599999999999D, -112.9573D, -18.523599999999998D, 10.006399999999999D, -66.575900000000004D, -92.128900000000002D, -33.803600000000003D, 
                -17.4254D, -24.154699999999998D, -23.7637D, 2.5971000000000002D
            }, {
                -9.3640000000000008D, -28.821300000000001D, 2.2504D, -19.0015D, 0.46639999999999998D, -6.8674999999999997D, -18.863299999999999D, -9.4905000000000008D, 0.59319999999999995D, -2.1993999999999998D, 
                -8.9873999999999992D, -0.92920000000000003D, -8.6887000000000008D, -2.1225999999999998D, -18.688800000000001D, -16.315100000000001D, -106.1093D, -63.234200000000001D, -17.277799999999999D, -47.662799999999997D, 
                -101.54130000000001D, -35.156500000000001D, -17.727499999999999D, -66.607799999999997D, 8.6359999999999992D, -0.96950000000000003D
            }, {
                0.75990000000000002D, -10.0221D, 3.8458000000000001D, -2.8814000000000002D, -0.15840000000000001D, 0.50270000000000004D, -2.8195000000000001D, -11.802099999999999D, 7.8250000000000002D, -1.6755D, 
                -6.7746000000000004D, -4.4882999999999997D, 7.9916999999999998D, 4.5914000000000001D, -2.0440999999999998D, 1.6760999999999999D, -0.62880000000000003D, 19.389099999999999D, -127.1032D, -46.3018D, 
                12.759D, -44.667200000000001D, -96.236900000000006D, -36.608499999999999D, -19.9194D, -30.418399999999998D, -0.57379999999999998D, -1.5199D
            }, 
            {
                -4.0167000000000002D, -14.3873D, -3.2471000000000001D, -9.6135000000000002D, -6.1879D, -3.2233000000000001D, -4.4739000000000004D, -3.9022000000000001D, -10.8552D, -12.617699999999999D, 
                2.6316999999999999D, 1.7714000000000001D, -1.4132D, -9.298D, -11.141999999999999D, 2.3874D, -3.0169999999999999D, 9.9743999999999993D, 7.4710999999999999D, 14.3171D, 
                -87.132599999999996D, -58.652500000000003D, -27.776299999999999D, -41.956000000000003D, -95.444400000000002D, -34.624099999999999D, -24.445699999999999D, -37.505800000000001D, 4.6570999999999998D, 1.6336999999999999D
            }, {
                -17.5809D, -3.7601D, 5.5193000000000003D, 6.2694000000000001D, -4.4627999999999997D, -13.898199999999999D, -4.4522000000000004D, -0.26029999999999998D, -3.6915D, -11.016999999999999D, 
                -12.346D, 23.802299999999999D, 14.2903D, 1.2559D, 2.8534000000000002D, 1.6488D, 2.5788000000000002D, 9.6669999999999998D, 3.5468000000000002D, 14.340400000000001D, 
                4.0640999999999998D, 6.8738000000000001D, -100.7589D, -66.250799999999998D, -1.3838999999999999D, -49.222099999999998D, -90.855400000000003D, -40.299599999999998D, -32.946599999999997D, -14.708500000000001D, 
                28.697099999999999D, -1.0629D
            }, {
                -14.289899999999999D, -9.6288999999999998D, -7.7000999999999999D, -2.7391000000000001D, -10.497400000000001D, 1.4125000000000001D, -12.1456D, 1.2267999999999999D, -7.3208000000000002D, 13.8226D, 
                -6.7995000000000001D, -12.3485D, 11.9955D, 4.6837999999999997D, 8.2408999999999999D, 17.1113D, 18.48D, 5.5846999999999998D, 6.7173999999999996D, 7.5913000000000004D, 
                10.9002D, 8.3354999999999997D, 13.2311D, 13.875400000000001D, -97.665800000000004D, -75.270399999999995D, -4.1580000000000004D, -42.436799999999998D, -76.0655D, -39.843600000000002D, 
                -47.743899999999996D, -17.5045D, 6.8231000000000002D, -1.5488D
            }
        }, {
            new double[0], new double[0], new double[0], new double[0], new double[0], new double[0], {
                -4.6006D, -6.1703999999999999D, 25.034400000000002D, 3.4247000000000001D, 2.5754000000000001D, -2.1215999999999999D, 29.032399999999999D, 7.0128000000000004D, -25.247800000000002D, -17.376000000000001D, 
                -16.147200000000002D, -18.4529D, -78.062700000000007D, -38.309899999999999D, 6.9189999999999996D, -62.550400000000003D, -126.3986D, -14.6633D, -26.738399999999999D, -27.833500000000001D, 
                30.0426D, 2.5735000000000001D
            }, {
                1.1892D, -8.1928999999999998D, 21.118400000000001D, 0.56459999999999999D, 3.3719000000000001D, 3.7145999999999999D, -10.6068D, 20.2806D, 10.1106D, 6.2701000000000002D, 
                -10.002800000000001D, -11.597300000000001D, -24.620200000000001D, -27.1465D, -87.711299999999994D, -44.098300000000002D, 5.9954000000000001D, -48.3887D, -98.610699999999994D, -9.9434000000000005D, 
                -18.569900000000001D, -20.448699999999999D, 20.8157D, 9.4528999999999996D
            }, {
                -4.8712D, 15.698399999999999D, 5.9093D, 15.119199999999999D, 9.9248999999999992D, 0.066199999999999995D, 25.3063D, -3.1566999999999998D, 13.502000000000001D, 14.9475D, 
                -1.3505D, -10.9306D, -12.0854D, -3.7778999999999998D, -11.4146D, -18.238900000000001D, -80.454499999999996D, -40.482900000000001D, 17.886299999999999D, -33.642499999999998D, 
                -96.150199999999998D, -8.2423000000000002D, -29.409199999999998D, -6.5693000000000001D, 8.4263999999999992D, 8.1654D
            }, {
                1.0337000000000001D, 0.42830000000000001D, 17.855699999999999D, 9.3124000000000002D, 7.0955000000000004D, 15.787800000000001D, 14.514699999999999D, 6.0266000000000002D, 1.2498D, 1.4144000000000001D, 
                1.6187D, -1.4399999999999999D, 4.2591000000000001D, -0.17560000000000001D, -5.6904000000000003D, -1.3768D, -9.9179999999999993D, -6.5658000000000003D, -73.954099999999997D, -33.502800000000001D, 
                25.713100000000001D, -40.316800000000001D, -100.8605D, -5.3041999999999998D, -41.286900000000003D, -15.285399999999999D, 11.8741D, 10.966100000000001D
            }, 
            {
                -12.7821D, 15.8847D, 4.1582999999999997D, 15.0594D, 9.0764999999999993D, 20.6508D, 11.5954D, 12.1111D, 7.6318999999999999D, 0.34889999999999999D, 
                24.8903D, 16.724299999999999D, 8.4298000000000002D, 10.6713D, -8.5969999999999995D, -0.88580000000000003D, -3.9504999999999999D, 1.5591999999999999D, -14.2563D, -7.1519000000000004D, 
                -92.988699999999994D, -40.385800000000003D, 37.631799999999998D, -43.191600000000001D, -117.33710000000001D, -4.2866999999999997D, -37.342300000000002D, 6.9696999999999996D, -14.884499999999999D, 11.1098D
            }, {
                -38.678199999999997D, 19.520199999999999D, 3.0470000000000002D, 14.2964D, 0.57740000000000002D, 19.068999999999999D, 3.0914999999999999D, 11.8095D, 2.2833999999999999D, 1.7052D, 
                12.7347D, 40.277200000000001D, 5.9223999999999997D, 11.1112D, 4.2187000000000001D, -2.8681999999999999D, -14.6426D, -4.5934999999999997D, -1.3684000000000001D, -1.8232999999999999D, 
                -18.7881D, -8.8703000000000003D, -97.913499999999999D, -36.225999999999999D, 24.013999999999999D, -34.728999999999999D, -106.4577D, -7.3848000000000003D, -37.537799999999997D, -38.829799999999999D, 
                -10.8881D, 6.6959D
            }, {
                -26.195699999999999D, 30.681899999999999D, 6.0308000000000002D, 2.6427D, 2.3628999999999998D, -3.5533999999999999D, 23.785499999999999D, -0.99260000000000004D, 5.0980999999999996D, 4.3716999999999997D, 
                2.6920000000000002D, 9.5759000000000007D, 13.2906D, 16.104500000000002D, 19.046700000000001D, 1.4111D, 9.1887000000000008D, -6.9927999999999999D, 0.060499999999999998D, 10.3843D, 
                4.0354000000000001D, 10.062799999999999D, -3.1073D, -7.1314000000000002D, -101.14060000000001D, -42.799700000000001D, 42.296700000000001D, -37.927900000000001D, -125.0149D, -3.5434000000000001D, 
                -35.549799999999998D, -14.013999999999999D, -12.342599999999999D, 11.5083D
            }
        }, {
            new double[0], new double[0], new double[0], new double[0], new double[0], new double[0], {
                -1.8584000000000001D, 3.0501D, 18.043199999999999D, 17.675599999999999D, 6.0674000000000001D, -0.31440000000000001D, 12.8444D, 8.8551000000000002D, -14.345599999999999D, 5.2413999999999996D, 
                -11.9779D, -15.520099999999999D, -110.479D, -39.416200000000003D, 3.7081D, -33.841500000000003D, -96.039000000000001D, -23.571899999999999D, -14.0458D, 13.7722D, 
                14.0985D, -1.3751D
            }, {
                5.1101999999999999D, 10.009499999999999D, 15.955299999999999D, 3.9014000000000002D, 29.148099999999999D, 2.9872000000000001D, 8.7310999999999996D, 11.319900000000001D, 12.0448D, 1.9280999999999999D, 
                -11.345499999999999D, 3.1137999999999999D, -5.4622000000000002D, -9.4695D, -85.879599999999996D, -31.498899999999999D, 16.070599999999999D, -36.9236D, -112.3177D, -21.744D, 
                -24.974699999999999D, 3.6819000000000002D, 5.3888999999999996D, 0.64190000000000003D
            }, {
                -1.5116000000000001D, 0.012D, 34.400300000000001D, -4.4005999999999998D, 10.4534D, 28.1996D, 3.7856999999999998D, 5.7751999999999999D, 0.65139999999999998D, 6.8670999999999998D, 
                -5.7209000000000003D, -6.9725999999999999D, -7.4095000000000004D, 9.5306999999999995D, -1.4684999999999999D, -16.659099999999999D, -93.451499999999996D, -48.315899999999999D, 12.2254D, -36.351300000000002D, 
                -127.0493D, -22.4758D, -19.819299999999998D, -16.157399999999999D, 23.430399999999999D, 2.2008999999999999D
            }, {
                -16.811199999999999D, 17.624700000000001D, 33.776400000000002D, -0.64700000000000002D, 16.376799999999999D, 8.2132000000000005D, 33.389600000000002D, 1.5538000000000001D, -6.9089D, 8.9433000000000007D, 
                16.107099999999999D, -3.0899999999999999D, 11.8979D, -2.3062D, -11.6402D, 9.5853000000000002D, 4.4523999999999999D, -20.291599999999999D, -100.59139999999999D, -43.503700000000002D, 
                19.516400000000001D, -51.801000000000002D, -110.414D, -17.6752D, -41.703099999999999D, 1.5988D, 21.089700000000001D, 2.8024D
            }, 
            {
                17.347799999999999D, 12.626200000000001D, 10.1973D, 19.125D, 13.2257D, 16.1127D, 15.0281D, 15.789899999999999D, 14.588699999999999D, -1.2804D, 
                0.4652D, 14.668200000000001D, 7.8806000000000003D, 13.522399999999999D, -2.0339D, 7.5296000000000003D, 4.7461000000000002D, 16.999199999999998D, 14.028499999999999D, -16.094200000000001D, 
                -59.078800000000001D, -46.011000000000003D, 40.666800000000002D, -36.782499999999999D, -110.6001D, -13.057600000000001D, -35.930700000000002D, 10.1225D, 12.1906D, 4.1448999999999998D
            }, {
                2.5375000000000001D, 13.4017D, 12.046099999999999D, 23.501799999999999D, 9.8020999999999994D, 20.526700000000002D, 23.065899999999999D, 10.3558D, 29.6905D, 13.436D, 
                15.5036D, 18.324300000000001D, 5.4046000000000003D, -11.7958D, -7.4280999999999997D, 1.3165D, -8.8114000000000008D, 1.4297D, -2.5943999999999998D, 7.4859999999999998D, 
                9.1585000000000001D, -20.284700000000001D, -83.784999999999997D, -48.314799999999998D, 63.331499999999998D, -31.753299999999999D, -137.33090000000001D, -14.9337D, -39.760199999999998D, 11.220499999999999D, 
                12.1409D, 4.3437000000000001D
            }, {
                -46.698300000000003D, 1.3048D, 8.0357000000000003D, -5.8072999999999997D, 11.170400000000001D, 3.4708000000000001D, 8.5602D, 6.5141999999999998D, 3.9895999999999998D, 10.798999999999999D, 
                -15.1967D, 8.7843D, 56.030200000000001D, 11.573499999999999D, -0.81289999999999996D, -3.6299000000000001D, 2.0507D, -4.6657000000000002D, -10.9529D, 2.6078999999999999D, 
                -19.962800000000001D, 3.3773D, -8.4018999999999995D, -10.7188D, -99.694999999999993D, -35.666600000000003D, 22.442799999999998D, -39.243099999999998D, -146.01240000000001D, -17.7576D, 
                -33.057400000000001D, -15.037000000000001D, 30.349599999999999D, 7.5835999999999997D
            }
        }, {
            new double[0], new double[0], new double[0], new double[0], new double[0], new double[0], {
                -66.484099999999998D, 31.377300000000002D, 20.381499999999999D, 18.640499999999999D, 16.9376D, 15.095800000000001D, -6.4173999999999998D, -11.437799999999999D, -12.050700000000001D, -16.375800000000002D, 
                -20.877400000000002D, -18.007899999999999D, -83.055099999999996D, -19.984400000000001D, 3.8344D, -30.965900000000001D, -140.07210000000001D, 1.8351999999999999D, 0.83499999999999996D, 46.287300000000002D, 
                7.8677999999999999D, 5.1508000000000003D
            }, {
                -65.600300000000004D, 3.7141999999999999D, 4.6425000000000001D, 8.8956D, 12.407500000000001D, -2.0590000000000002D, 8.2446999999999999D, 13.279D, -3.4184000000000001D, -0.4395D, 
                -18.2928D, -5.2027999999999999D, -17.831099999999999D, -18.417000000000002D, -103.09869999999999D, -19.702300000000001D, -23.329999999999998D, -29.023099999999999D, -101.8901D, 1.9317D, 
                -2.3631000000000002D, 37.606900000000003D, 17.930599999999998D, 3.8256999999999999D
            }, {
                -61.685499999999998D, 15.7174D, 4.5270000000000001D, 18.517800000000001D, 15.298D, 16.857800000000001D, 8.1744000000000003D, 15.8879D, 27.829899999999999D, -1.1720999999999999D, 
                -0.70920000000000005D, -11.482900000000001D, -8.0343D, 2.1715D, -5.7728999999999999D, -5.3762999999999996D, -96.935100000000006D, -22.481100000000001D, -1.6193D, -28.973400000000002D, 
                -114.7084D, -0.45419999999999999D, -2.5884D, 34.092300000000002D, 4.1025999999999998D, 2.2225000000000001D
            }, {
                -55.927799999999998D, 15.857200000000001D, 0.74070000000000003D, 9.3657000000000004D, 19.697600000000001D, 12.037699999999999D, 16.514900000000001D, 9.1707000000000001D, 5.0853000000000002D, 24.6631D, 
                12.518800000000001D, 0.2369D, -6.5941000000000001D, -0.77690000000000003D, -9.8489000000000004D, -0.044200000000000003D, -10.492800000000001D, -16.232199999999999D, -96.096299999999999D, -23.232199999999999D, 
                2.5844999999999998D, -36.616100000000003D, -151.13329999999999D, 1.0698000000000001D, 3.8370000000000002D, 34.026699999999998D, -2.2498D, 6.3202999999999996D
            }, 
            {
                -65.691699999999997D, 15.0793D, 4.4508999999999999D, 9.4237000000000002D, 9.1358999999999995D, 14.7623D, 8.4840999999999998D, 18.714700000000001D, 0.055D, 13.709D, 
                24.2332D, -3.4638D, 4.4135999999999997D, -5.6384999999999996D, -4.4065000000000003D, -4.0194999999999999D, -5.4166999999999996D, -4.0993000000000004D, -14.466900000000001D, -9.0667000000000009D, 
                -97.096400000000003D, -20.021000000000001D, -1.2810999999999999D, -27.814299999999999D, -149.8175D, 0.81630000000000003D, -1.8088D, -12.8302D, 7.6862000000000004D, 6.5018000000000002D
            }, {
                -73.9208D, 19.567599999999999D, 9.5038D, 3.8382000000000001D, 24.460899999999999D, 18.611999999999998D, 17.437799999999999D, 20.6906D, 13.9541D, 10.783799999999999D, 
                11.234299999999999D, 56.398899999999998D, 3.5280999999999998D, 6.9348999999999998D, 3.6092D, 1.6903999999999999D, 2.2385000000000002D, -1.7412000000000001D, -1.1881999999999999D, -1.7492000000000001D, 
                -2.8603999999999998D, 0.84799999999999998D, -79.863799999999998D, -24.023D, 28.4483D, -31.6236D, -136.75569999999999D, -4.3341000000000003D, -13.6275D, -4.0664999999999996D, 
                -12.421099999999999D, 2.7002999999999999D
            }, {
                -26.195699999999999D, 30.681899999999999D, 6.0308000000000002D, 2.6427D, 2.3628999999999998D, -3.5533999999999999D, 23.785499999999999D, -0.99260000000000004D, 5.0980999999999996D, 4.3716999999999997D, 
                2.6920000000000002D, 9.5759000000000007D, 13.2906D, 16.104500000000002D, 19.046700000000001D, 1.4111D, 9.1887000000000008D, -6.9927999999999999D, 0.060499999999999998D, 10.3843D, 
                4.0354000000000001D, 10.062799999999999D, -3.1073D, -7.1314000000000002D, -101.14060000000001D, -42.799700000000001D, 42.296700000000001D, -37.927900000000001D, -125.0149D, -3.5434000000000001D, 
                -35.549799999999998D, -14.013999999999999D, -12.342599999999999D, 11.5083D
            }
        }, {
            new double[0], new double[0], new double[0], new double[0], new double[0], new double[0], {
                -5.2385000000000002D, -4.0951000000000004D, -0.79549999999999998D, -1.2519D, -9.0258000000000003D, -2.4076D, -3.5531999999999999D, 2.5036D, -2.7315999999999998D, -2.5276999999999998D, 
                -2.1414D, 2.3191000000000002D, -9.8053000000000008D, -5.3383000000000003D, 5.3886000000000003D, -4.3499999999999996D, -9.3895D, -5.5282999999999998D, -4.6547000000000001D, -0.41399999999999998D, 
                -0.023599999999999999D, -2.8090999999999999D
            }, {
                -5.1234999999999999D, -4.4793000000000003D, -4.8792999999999997D, -4.2256999999999998D, -2.3399000000000001D, -2.6795D, -0.94930000000000003D, 0.52229999999999999D, 1.9390000000000001D, -3.6356000000000002D, 
                -1.6862999999999999D, 1.3562000000000001D, -0.27150000000000002D, 4.4603000000000002D, -6.6070000000000002D, -1.2133D, 1.9537D, -6.1317000000000004D, -2.1236000000000002D, -4.1505999999999998D, 
                -6.7828999999999997D, -2.0729000000000002D, 3.6892D, -0.50249999999999995D
            }, {
                0.29199999999999998D, -1.9766999999999999D, 1.7568999999999999D, -3.1446000000000001D, 0.60699999999999998D, -1.3073999999999999D, -2.2153999999999998D, -6.2622D, 0.17960000000000001D, -0.39789999999999998D, 
                3.3714D, -2.3999999999999999D, -0.68479999999999996D, 0.3629D, 0.59050000000000002D, 6.0644D, -10.245900000000001D, -8.1501000000000001D, -5.2808999999999999D, -4.6486000000000001D, 
                -7.5456000000000003D, -8.6494999999999997D, -4.3103999999999996D, -3.6701000000000001D, 1.9649000000000001D, -4.0076999999999998D
            }, {
                -2.5924999999999998D, 2.0531000000000001D, 0.97340000000000004D, 0.57220000000000004D, -4.2135999999999996D, 0.1346D, -1.8294999999999999D, -4.4345999999999997D, 0.25280000000000002D, -1.9896D, 
                1.0848D, -0.80679999999999996D, -1.2262D, -1.3048999999999999D, -0.57579999999999998D, -4.0953999999999997D, -4.1296999999999997D, 4.6296999999999997D, -7.3072999999999997D, -10.321400000000001D, 
                4.8929999999999998D, -11.0824D, -7.0125999999999999D, -4.3471000000000002D, -3.2098D, -1.8297000000000001D, -3.3060999999999998D, -0.62319999999999998D
            }, 
            {
                -1.0510999999999999D, -8.8643000000000001D, -9.1012000000000004D, 0.95099999999999996D, -4.8701999999999996D, -1.6594D, -2.8656000000000001D, 0.20319999999999999D, -0.47760000000000002D, 4.2954999999999997D, 
                -0.92400000000000004D, 0.98880000000000001D, -5.351D, 2.6274000000000002D, 0.20730000000000001D, -3.5621D, -3.1387D, 0.1111D, 1.5859000000000001D, 2.6432000000000002D, 
                -9.7136999999999993D, -13.0663D, 1.0448999999999999D, -7.2750000000000004D, -6.5311000000000003D, -7.2648999999999999D, -3.1113D, 1.6655D, 0.51359999999999995D, -7.9583000000000004D
            }, {
                -1.7505999999999999D, -5.7328999999999999D, -3.8976000000000002D, -0.12809999999999999D, -0.071499999999999994D, 0.026599999999999999D, -3.1806999999999999D, -4.7553000000000001D, -7.4435000000000002D, 0.42280000000000001D, 
                0.55159999999999998D, 2.7183999999999999D, -1.8366D, -4.3587999999999996D, -3.1867000000000001D, -3.1585999999999999D, -2.3085D, 0.012800000000000001D, -1.0235000000000001D, -5.5258000000000003D, 
                -3.9777D, 3.7726999999999999D, -5.9256000000000002D, -9.1395D, 0.72340000000000004D, -6.6273999999999997D, -6.9631999999999996D, -7.4141000000000004D, -6.5129000000000001D, -2.6656D, 
                -1.6097999999999999D, 0.76590000000000003D
            }, {
                -3.8742999999999999D, -1.3537999999999999D, 0.44869999999999999D, -2.1453000000000002D, -2.8860999999999999D, 1.0004999999999999D, 1.2486999999999999D, 1.8635999999999999D, -0.2351D, 0.754D, 
                -4.5067000000000004D, -2.1808000000000001D, -1.2008000000000001D, -2.4384000000000001D, -2.9102000000000001D, -4.1893000000000002D, 1.5652999999999999D, -4.5598999999999998D, -2.6172D, -5.7603999999999997D, 
                -1.5884D, -1.9783999999999999D, -0.20910000000000001D, 2.4578000000000002D, -8.1181000000000001D, -10.546099999999999D, 1.7222999999999999D, -5.9767999999999999D, -5.7469000000000001D, -7.8727999999999998D, 
                -6.3654999999999999D, -2.3681000000000001D, -0.4047D, -2.7530999999999999D
            }
        }, {
            new double[0], new double[0], new double[0], new double[0], new double[0], new double[0], {
                -7.0772000000000004D, -6.0255000000000001D, -5.1626000000000003D, -2.3452000000000002D, -15.1645D, -6.5582000000000003D, -2.6404999999999998D, 0.5514D, -8.4315999999999995D, -5.6963999999999997D, 
                -3.0762999999999998D, 2.4380999999999999D, -16.747900000000001D, -12.659599999999999D, 4.0468000000000002D, -8.9458000000000002D, -18.640999999999998D, -7.5736999999999997D, -10.432D, -1.0184D, 
                -0.15590000000000001D, -5.8395000000000001D
            }, {
                0.4098D, -10.1142D, -10.851000000000001D, -2.9216000000000002D, 1.6894D, -8.5374999999999996D, -1.1976D, 4.0598999999999998D, -5.4744999999999999D, -5.1494999999999997D, 
                -4.1087999999999996D, -4.0175000000000001D, -0.36890000000000001D, 8.3226999999999993D, -13.1271D, -9.0213000000000001D, 7.4621000000000004D, -13.662699999999999D, -10.9345D, -7.5564D, 
                -10.749599999999999D, -0.76919999999999999D, 5.6003999999999996D, -5.5193000000000003D
            }, {
                -3.5832999999999999D, -8.8737999999999992D, 0.32529999999999998D, -5.1124999999999998D, -3.3492999999999999D, -4.3410000000000002D, -6.6749999999999998D, -7.8263999999999996D, 1.6656D, -2.5537000000000001D, 
                1.2349000000000001D, -6.8536000000000001D, -4.5873999999999997D, -1.7591000000000001D, 0.21970000000000001D, 4.5540000000000003D, -16.9726D, -12.079800000000001D, -3.9327000000000001D, -8.4235000000000007D, 
                -16.554500000000001D, -12.090400000000001D, -6.9029999999999996D, -4.6965000000000003D, -0.81910000000000005D, -3.9487999999999999D
            }, {
                -7.5595999999999997D, 2.2162999999999999D, -0.70940000000000003D, 3.6739999999999999D, -2.9489999999999998D, -1.2878000000000001D, -5.9577D, -0.81740000000000002D, -8.4718D, 1.8092999999999999D, 
                1.6946000000000001D, 3.0619999999999998D, 3.7058D, -4.5713999999999997D, -2.6147999999999998D, -1.2788999999999999D, -8.6319999999999997D, 3.1640000000000001D, -14.967499999999999D, -17.0032D, 
                11.536D, -19.0396D, -15.929399999999999D, -7.9725999999999999D, -9.9139999999999997D, -1.9974000000000001D, -3.3719999999999999D, -3.6486000000000001D
            }, 
            {
                2.2185000000000001D, -9.6458999999999993D, -9.9703999999999997D, 3.6606000000000001D, -3.8635999999999999D, -4.4847999999999999D, -8.7143999999999995D, -2.3355000000000001D, -4.5457999999999998D, 5.6151D, 
                1.6899D, -2.3786D, -8.3500999999999994D, -0.5766D, -4.5355999999999996D, -1.8396999999999999D, -4.6614000000000004D, -5.3457999999999997D, 0.1024D, 9.5686D, 
                -15.917999999999999D, -20.353300000000001D, 0.47199999999999998D, -14.5136D, -18.007899999999999D, -10.566000000000001D, -9.6691000000000003D, 2.6244000000000001D, -0.5665D, -8.0143000000000004D
            }, {
                -4.8052999999999999D, -8.1908999999999992D, -4.9191000000000003D, -3.6659000000000002D, -2.2090999999999998D, -1.9524999999999999D, -4.3407999999999998D, 1.0873999999999999D, -12.2216D, -0.0591D, 
                -0.44309999999999999D, 7.3783000000000003D, -3.6265000000000001D, -2.8451D, -4.6376999999999997D, -2.8193999999999999D, -1.7696000000000001D, 1.5773999999999999D, -1.7564D, -1.3703000000000001D, 
                -6.7358000000000002D, 7.2671999999999999D, -14.1172D, -20.966799999999999D, 1.0569D, -13.3019D, -14.0791D, -7.1029999999999998D, -9.8931000000000004D, -9.3156999999999996D, 
                -2.0990000000000002D, -0.0117D
            }, {
                -6.1356000000000002D, -6.0677000000000003D, 2.3094999999999999D, -5.3875000000000002D, -3.5234999999999999D, -2.1095000000000002D, -0.48280000000000001D, 1.8934D, 1.7817000000000001D, -6.2154999999999996D, 
                -11.1325D, 0.28720000000000001D, -3.4195000000000002D, 0.9214D, -2.5991D, -2.2945000000000002D, 6.1199000000000003D, -8.7576999999999998D, -2.3315999999999999D, -2.6749999999999998D, 
                -1.0820000000000001D, -1.6144000000000001D, 1.2065999999999999D, 2.7747999999999999D, -16.365500000000001D, -18.6172D, 3.0339999999999998D, -15.5479D, -13.226699999999999D, -8.0222999999999995D, 
                -12.081D, -1.4744999999999999D, 0.46479999999999999D, -2.4098999999999999D
            }
        }
    };
    public static final int scoretable[] = {
        0, 1, 2, 3, 4
    };
    public static final int scoretable2[] = {
        0, 1, 3, 7, 15
    };
    public static final int DEPTH = 2;
    public static final int NIT = 49;
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
    public int nmoves;

    static 
    {
        tilecolor = (new Color[] {
            Color.WHITE, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.RED, Color.ORANGE, Color.BLACK
        });
    }
}
