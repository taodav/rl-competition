/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.proving.experimentRunners;

import java.io.File;

/**
 *
 * @author btanner
 */
public class envShellProcessRunner extends externalProcessRunner{

    public envShellProcessRunner(){
        super("envShell");
    }
    @Override
    protected Process createProcess() {
            try {
                String basePath = "../";
                String systemPath = basePath + File.separator+"system";
                String libPath = systemPath + File.separator+"libraries";
                String provingPath = systemPath + File.separator+"proving";
                String rlVizPath = libPath + File.separator + "rl-viz";
                String compLib = rlVizPath + File.separator+"RLVizLib.jar";
//                String compLib = "C:\\cygwin\\home\\Administrator\\rl-competition\\system\\libraries\\RLVizLib.jar";

                String envShellLib = rlVizPath + File.separator +"EnvironmentShell.jar";

//                String userDir = System.getProperty("user.dir");

                String thisDirectory=new File(".").getAbsolutePath();
                String envJarsDir =thisDirectory + "/../system/proving/";
                envJarsDir.replaceAll("/", File.pathSeparator);
                String envJarParam = "environment-jar-path=" + envJarsDir;
                System.out.println("envJarParam is: "+envJarParam);
                ProcessBuilder theProcessBuilder = new ProcessBuilder("java","-ea", "-Xmx384M", "-jar",envShellLib,envJarParam);
//                  ("java", rlVizLibPathArg, "-Xmx384M", "-classpath",compLib + File.pathSeparator + envShellLib,"rlglue.environment.EnvironmentLoader", "environmentShell.EnvironmentShell");

                theProcessBuilder.redirectErrorStream(true);

                return theProcessBuilder.start();
        }catch(Exception e){
            System.err.println("envShellProcessRunner died when trying to create envShellProcess with exception: "+e);
            e.printStackTrace();
        }
            return null;
    }
    
        @Override
    public void subClassKillProcess() {
        theProcess.destroy();
    }

}
