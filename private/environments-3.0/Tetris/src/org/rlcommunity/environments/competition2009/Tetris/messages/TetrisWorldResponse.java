// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TetrisWorldResponse.java

package org.rlcommunity.environments.competition2009.Tetris.messages;

import java.util.StringTokenizer;
import rlVizLib.messaging.*;
import rlVizLib.messaging.environment.EnvMessageType;

public class TetrisWorldResponse extends AbstractResponse
{

    public TetrisWorldResponse(int width, int height)
    {
        world_width = 0;
        world_height = 0;
        world_width = width;
        world_height = height;
    }

    public TetrisWorldResponse(String responseMessage)
        throws NotAnRLVizMessageException
    {
        world_width = 0;
        world_height = 0;
        GenericMessage theGenericResponse = new GenericMessage(responseMessage);
        String thePayLoadString = theGenericResponse.getPayLoad();
        StringTokenizer stateTokenizer = new StringTokenizer(thePayLoadString, ":");
        world_width = Integer.parseInt(stateTokenizer.nextToken());
        world_height = Integer.parseInt(stateTokenizer.nextToken());
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
        return theResponseBuffer.toString();
    }

    public int getWidth()
    {
        return world_width;
    }

    public int getHeight()
    {
        return world_height;
    }

    private int world_width;
    private int world_height;
}
