// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TetrisStateResponse.java

package org.rlcommunity.environments.competition2009.Tetris.messages;

import java.util.StringTokenizer;
import rlVizLib.messaging.*;
import rlVizLib.messaging.environment.EnvMessageType;

public class TetrisStateResponse extends AbstractResponse
{

    public TetrisStateResponse(int score, int width, int height, int gs[], int piece)
    {
        tet_global_score = 0;
        world_width = 0;
        world_height = 0;
        world = null;
        currentPiece = 0;
        tet_global_score = score;
        world_width = width;
        world_height = height;
        world = gs;
        currentPiece = piece;
    }

    public TetrisStateResponse(String responseMessage)
        throws NotAnRLVizMessageException
    {
        tet_global_score = 0;
        world_width = 0;
        world_height = 0;
        world = null;
        currentPiece = 0;
        GenericMessage theGenericResponse = new GenericMessage(responseMessage);
        String thePayLoadString = theGenericResponse.getPayLoad();
        StringTokenizer stateTokenizer = new StringTokenizer(thePayLoadString, ":");
        world_width = Integer.parseInt(stateTokenizer.nextToken());
        world_height = Integer.parseInt(stateTokenizer.nextToken());
        tet_global_score = Integer.parseInt(stateTokenizer.nextToken());
        int i = 0;
        int worldSize = world_width * world_height;
        world = new int[worldSize];
        for(; stateTokenizer.hasMoreTokens() && i < worldSize; i++)
            world[i] = Integer.parseInt(stateTokenizer.nextToken());

        currentPiece = Integer.parseInt(stateTokenizer.nextToken());
    }

    public String makeStringResponse()
    {
        StringBuffer theResponseBuffer = new StringBuffer();
        theResponseBuffer.append("TO=");
        theResponseBuffer.append(MessageUser.kBenchmark.id());
        theResponseBuffer.append(" FROM=");
        theResponseBuffer.append(MessageUser.kEnv.id());
        theResponseBuffer.append(" CMD=");
        theResponseBuffer.append(EnvMessageType.kEnvResponse.id());
        theResponseBuffer.append(" VALTYPE=");
        theResponseBuffer.append(MessageValueType.kStringList.id());
        theResponseBuffer.append(" VALS=");
        theResponseBuffer.append(world_width);
        theResponseBuffer.append(":");
        theResponseBuffer.append(world_height);
        theResponseBuffer.append(":");
        theResponseBuffer.append(tet_global_score);
        theResponseBuffer.append(":");
        for(int i = 0; i < world.length; i++)
        {
            theResponseBuffer.append(":");
            theResponseBuffer.append(world[i]);
        }

        theResponseBuffer.append(":");
        theResponseBuffer.append(currentPiece);
        return theResponseBuffer.toString();
    }

    public int getScore()
    {
        return tet_global_score;
    }

    public int getWidth()
    {
        return world_width;
    }

    public int getHeight()
    {
        return world_height;
    }

    public int[] getWorld()
    {
        return world;
    }

    public int getCurrentPiece()
    {
        return currentPiece;
    }

    private int tet_global_score;
    private int world_width;
    private int world_height;
    private int world[];
    private int currentPiece;
}
