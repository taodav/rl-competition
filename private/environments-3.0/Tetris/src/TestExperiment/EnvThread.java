// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnvThread.java

package TestExperiment;

import org.rlcommunity.rlglue.codec.util.EnvironmentLoader;
import org.rlcommunity.rlviz.environmentshell.EnvironmentShell;
import org.rlcommunity.rlviz.settings.RLVizSettings;
import rlVizLib.general.ParameterHolder;

public class EnvThread extends Thread
{

    public EnvThread()
    {
    }

    public void run()
    {
        String args[] = new String[0];
        ParameterHolder parameterholder = new ParameterHolder();
        parameterholder.addStringParam("environment-jar-path", "../../../rl-competition/system/libraries/envJars");
        RLVizSettings.initializeSettings(args);
        RLVizSettings.addNewParameters(parameterholder);
        EnvironmentLoader environmentloader = new EnvironmentLoader(new EnvironmentShell());
        environmentloader.run();
    }
}
