/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rlcommunity.competition.proving.experimentRunners;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author btanner
 */
public class rtsProcessRunner extends externalProcessRunner {

    public rtsProcessRunner() {
        super("rts");
    }

    String getFileLocation() {
        return ".." + "/" + "domains" + "/" + "realTimeStrategy" + "/" + "bin" + "/" + "rlgenv" + externalProcessRunner.getExeSuffix();
    }

    private void killAllRLGenv() {
        if (System.getProperty("os.name").contains("Win")) {
            try {
                Runtime.getRuntime().exec(new String[]{"taskkill", "/F", "/IM", "rlgenv.exe"}).waitFor();
            } catch (Exception ex) {
                System.out.println("Error :: Exception running killAllRLGenv: " + ex);
            }
        } else {
            try {
                Runtime.getRuntime().exec(new String[]{"bin" + "/" + "pkill", "rlgenv"}).waitFor();
            } catch (Exception ex) {
                System.out.println("Error :: Exception running killAllRLGenv: " + ex);
            }
        }
    }

    @Override
    protected Process createProcess() {
        killAllRLGenv();
        String theFileLocation = getFileLocation();
        try {
            File theFileExecutable = new File(theFileLocation);
            if (!theFileExecutable.exists()) {
                throw new ProcessStartException("Error :: rlgenv, the rts executable does not exist at:\n " + theFileLocation + ".\nDid you build it?");
            }
            ProcessBuilder theProcessBuilder = new ProcessBuilder(theFileLocation);


            theProcessBuilder.redirectErrorStream(true);
            Process theNewProcess = theProcessBuilder.start();
            externalProcessRunner.registerProcess(this);
            return theNewProcess;
        } catch (IOException e) {
            throw new ProcessStartException("Error :: IOException when trying to start the RTS executable at:\n" + theFileLocation + "\n " + e.getMessage());
        }
    }

    @Override
    public void subClassKillProcess() {
        if (theProcess != null) {
            try {
                theProcess.destroy();
                killAllRLGenv();
                theProcess.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(rtsProcessRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        theProcess = null;
    }
}
