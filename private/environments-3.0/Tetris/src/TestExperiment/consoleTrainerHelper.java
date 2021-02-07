// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   consoleTrainerHelper.java

package TestExperiment;

import java.util.Vector;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.environmentShell.*;

public class consoleTrainerHelper
{

    public consoleTrainerHelper()
    {
    }

    private static void unload()
    {
        EnvShellUnLoadRequest.Execute();
    }

    private static void load(String envNameString, ParameterHolder theParams)
    {
        if(currentlyLoaded)
            unload();
        EnvShellLoadRequest.Execute(envNameString, theParams);
        currentlyLoaded = true;
    }

    private static ParameterHolder preload(String envNameString)
    {
        EnvShellListResponse ListResponse = EnvShellListRequest.Execute();
        int thisEnvIndex = ListResponse.getTheEnvList().indexOf(envNameString);
        ParameterHolder p = (ParameterHolder)ListResponse.getTheParamList().get(thisEnvIndex);
        return p;
    }

    private static void preloadAndLoad(String envNameString)
    {
        ParameterHolder p = preload(envNameString);
        load(envNameString, p);
    }

    public static void loadTetris(int whichParamSet)
    {
        String theEnvString = "GeneralizedTetris - Java";
        ParameterHolder theParams = preload(theEnvString);
        theParams.setIntegerParam("pnum", Integer.valueOf(whichParamSet));
        load(theEnvString, theParams);
    }

    public static void loadMountainCar(int whichParamSet)
    {
        String theEnvString = "GeneralizedMountainCar - Java";
        ParameterHolder theParams = preload(theEnvString);
        theParams.setIntegerParam("pnum", Integer.valueOf(whichParamSet));
        load(theEnvString, theParams);
    }

    public static void loadAcrobot(int whichParamSet)
    {
        String theEnvString = "Acrobot - Java";
        ParameterHolder theParams = preload(theEnvString);
        load(theEnvString, theParams);
    }

    public static void loadKeepAway()
    {
        String theEnvString = "KeepAway - Java";
        preloadAndLoad(theEnvString);
    }

    public static void loadHelicopter(int whichParamSet)
    {
        String theEnvString = "GeneralizedHelicopter - Java";
        ParameterHolder theParams = preload(theEnvString);
        theParams.setIntegerParam("pnum", Integer.valueOf(whichParamSet));
        load(theEnvString, theParams);
    }

    public static void loadTaskSpecTester()
    {
        String theEnvString = "TaskSpecTester - Java";
        preloadAndLoad(theEnvString);
    }

    private static boolean currentlyLoaded = false;

}
