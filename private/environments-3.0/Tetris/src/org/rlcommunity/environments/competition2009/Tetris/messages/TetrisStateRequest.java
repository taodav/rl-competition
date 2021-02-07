// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TetrisStateRequest.java

package org.rlcommunity.environments.competition2009.Tetris.messages;

import java.io.PrintStream;
import org.rlcommunity.rlglue.codec.RLGlue;
import rlVizLib.messaging.*;
import rlVizLib.messaging.environment.EnvMessageType;
import rlVizLib.messaging.environment.EnvironmentMessages;

// Referenced classes of package org.rlcommunity.environments.competition2009.Tetris.messages:
//            TetrisStateResponse

public class TetrisStateRequest extends EnvironmentMessages
{

    public TetrisStateRequest(GenericMessage theMessageObject)
    {
        super(theMessageObject);
    }

    public static synchronized TetrisStateResponse Execute()
    {
        String theRequest = AbstractMessage.makeMessage(MessageUser.kEnv.id(), MessageUser.kBenchmark.id(), EnvMessageType.kEnvCustom.id(), MessageValueType.kString.id(), "GETTETRLAISSTATE");
        String responseMessage = RLGlue.RL_env_message(theRequest);
        TetrisStateResponse theResponse;
        try
        {
            theResponse = new TetrisStateResponse(responseMessage);
        }
        catch(NotAnRLVizMessageException ex)
        {
            System.out.println((new StringBuilder()).append("Not a valid RL Viz Message in Tetrlais State Response").append(ex).toString());
            return null;
        }
        return theResponse;
    }
}
