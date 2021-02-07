/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.proving.experimentRunners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author btanner
 */
public abstract class externalProcessRunner extends Thread {
    String procName="";
    Process theProcess;
    
    static Vector<externalProcessRunner> runningProcesses=null;
    
    static{
        runningProcesses=new Vector<externalProcessRunner>();
    }
    
    public static void registerProcess(externalProcessRunner newProcess){
        runningProcesses.add(newProcess);
    }
    
    public static void killActiveProcesses(){
        for (externalProcessRunner processRunner : runningProcesses) {
            processRunner.stopProcess();
        }
        runningProcesses.clear();
    }
    
    public static String getExeSuffix(){
        if(System.getProperty("os.name").contains("Win"))
            return ".exe";
        else
            return "";
    }

    abstract protected Process createProcess();
    volatile boolean shouldStop=false;
    volatile boolean hasStopped=false;
    
    public externalProcessRunner(String procName){this.procName=procName;}
    
    
    public void startExternalProcess(){
        theProcess = createProcess();
        start();
    }

    @Override
        public void run() {
            if(theProcess==null){
                Thread.dumpStack();
            }
            InputStream is = theProcess.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while (!shouldStop) {
                try {
                    if (br.ready()) {
                        System.out.println(procName + ": " + br.readLine());
                    }
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    System.err.println("Run method encountered exception in externalProcessRunner while sleeping: " + ex);
            } catch (IOException ex) {
                    System.err.println("Run method encountered exception in externalProcessRunner: " + ex);
                }
            }
        }

   public void stopProcess(){
       subClassKillProcess();
       shouldStop=true;
       hasStopped=true;
   }
   protected abstract void subClassKillProcess();
}
