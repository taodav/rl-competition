// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RLGlueThread.java

package TestExperiment;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RLGlueThread extends Thread
{

    public RLGlueThread()
    {
    }

    public void run()
    {
        try
        {
            String command = "..\\..\\..\\public\\system\\bin\\rl_glue.exe";
            Process process = (new ProcessBuilder(new String[] {
                command
            })).start();
            java.io.InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while((line = br.readLine()) != null) 
                System.out.println((new StringBuilder()).append("RLG> ").append(line).toString());
        }
        catch(IOException ex)
        {
            Logger.getLogger(RLGlueThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
