// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TetrisWorldRequest.java

package org.rlcommunity.environments.competition2009.Tetris.messages;

import java.io.PrintStream;
import org.rlcommunity.rlglue.codec.RLGlue;
import rlVizLib.messaging.*;
import rlVizLib.messaging.environment.EnvMessageType;
import rlVizLib.messaging.environment.EnvironmentMessages;

// Referenced classes of package org.rlcommunity.environments.competition2009.Tetris.messages:
//            TetrisWorldResponse

public class TetrisWorldRequest extends EnvironmentMessages
{

    public TetrisWorldRequest(GenericMessage theMessageObject)
    {
        super(theMessageObject);
    }

    public static synchronized TetrisWorldResponse Execute()
    {
        String theRequest = AbstractMessage.makeMessage(MessageUser.kEnv.id(), MessageUser.kBenchmark.id(), EnvMessageType.kEnvCustom.id(), MessageValueType.kString.id(), "GETTETRLAISWORLD");
        String responseMessage = RLGlue.RL_env_message(theRequest);
        TetrisWorldResponse theResponse;
        try
        {
            theResponse = new TetrisWorldResponse(responseMessage);
        }
        catch(NotAnRLVizMessageException ex)
        {
            System.out.println((new StringBuilder()).append("Not a valid RL Viz Message in Tetrlais World Response").append(ex).toString());
            return null;
        }
        return theResponse;
    }
}
