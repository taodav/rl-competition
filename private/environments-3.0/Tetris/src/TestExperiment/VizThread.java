// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VizThread.java

package TestExperiment;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rlcommunity.rlviz.app.RLVizApp;

public class VizThread extends Thread
{

    public VizThread()
    {
    }

    public void run()
    {
        try
        {
            String viz_args[] = {
                "list-environments=true", "env-viz=true"
            };
            RLVizApp.main(viz_args);
        }
        catch(IOException ex)
        {
            Logger.getLogger(VizThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
