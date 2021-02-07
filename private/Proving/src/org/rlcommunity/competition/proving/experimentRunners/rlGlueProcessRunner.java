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
class rlGlueProcessRunner extends externalProcessRunner {

    public rlGlueProcessRunner() {
        super("rlGlue");
    }

    String getFileLocation() {
        String basePath = "..";
        String systemPath = basePath + "/" + "system/build/bin";
        return systemPath + "/" + "rl_glue" + externalProcessRunner.getExeSuffix();
    }

    @Override
    protected Process createProcess() {
//Make sure no glue running
        killAllGlue();
        String theFileLocation = getFileLocation();
        try {
            File theFileExecutable = new File(theFileLocation);
            if (!theFileExecutable.exists()) {
                throw new ProcessStartException("Error :: " + theFileLocation + " executable does not exist.\nDid you: $>make all?");
            }
            ProcessBuilder theProcessBuilder = new ProcessBuilder(theFileLocation);
            theProcessBuilder.redirectErrorStream(true);
            return theProcessBuilder.start();
        } catch (IOException ex) {
            throw new ProcessStartException("Error :: Problem starting " + theFileLocation + "\n " + ex.getMessage());
        }
    }

    private void killAllGlue() {
        if (System.getProperty("os.name").contains("Win")) {
            try {
                Runtime.getRuntime().exec(new String[]{"taskkill", "/F", "/IM", "RL_glue.exe"}).waitFor();
            } catch (Exception ex) {
                System.out.println("Error :: Exception running killAllGlue: " + ex);
            }
        } else {
            try {
                Runtime.getRuntime().exec(new String[]{"./bin" + "/" + "pkill", "RL_glue"}).waitFor();
            } catch (Exception ex) {
                System.out.println("Error :: Exception running killAllGlue: " + ex);
            }
        }
    }
    /*
     * Kills RL-Glue.  java.lang.Process.destroy() doesn't seem reliable we we're also going to 
     * use some bigger guns and use a pkill script to kill the process by name from the operating system
     * Probably not Windows compatible
     * @since 1.1
     */

    @Override
    public void subClassKillProcess() {
        if (theProcess != null) {
            try {
                //This doesn't actually work most of the time so we'll bring out the big guns
                theProcess.destroy();
                killAllGlue();
                theProcess.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(rlGlueProcessRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        theProcess = null;
    }
}
