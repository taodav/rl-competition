/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.proving;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author btanner
 */
public class EnvironmentLogWriter {
final private String pathToResults="../system/proving/results";

FileWriter theWriter=null;

String theFileLocation=null;

    public EnvironmentLogWriter(String dirName, int MDPId){
        String fileName=MDPId+".runlog";

        theFileLocation=pathToResults+"/"+dirName+"/"+fileName;

        try {
            File theDir=new File(pathToResults+"/"+dirName+"/");
            boolean result=theDir.mkdirs();
            File theFile = new File(theFileLocation);
            theWriter = new FileWriter(theFile);
            theWriter.write("#Run Log\n");
            
        } catch (IOException ex) {
            Logger.getLogger(ResultsFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    }

    void addLog(int whichEpisodeNumber, Vector<String> stepSummaries){
        StringBuffer b=new StringBuffer();
        b.append(stepSummaries.size());
        b.append(":");
        for (String theString : stepSummaries) {
            b.append(theString);
            b.append(":");
        }
        addLog(whichEpisodeNumber,b.toString());
        
    }

    void addLog(int whichEpisodeNumber, File logFile) {
        try {
            theWriter.write("EpisodeNumber:" + whichEpisodeNumber + " theLog:");
            
            BufferedReader r=new BufferedReader(new FileReader(logFile));
            
            char[] charBuffer=new char[100000];
            int charsRead=r.read(charBuffer);
            while(charsRead>0){
                theWriter.write(charBuffer,0,charsRead);
                charsRead=r.read(charBuffer);
            }
            theWriter.write("\n");
            theWriter.flush();
        } catch (IOException ex) {
            System.err.println("Problem making output log file");
            }
    }
    void addLog(int whichEpisodeNumber, String theLog) {
        try {
            theWriter.write("EpisodeNumber:" + whichEpisodeNumber + " theLog:" + theLog + "\n");
            theWriter.flush();
        } catch (IOException ex) {
            Logger.getLogger(ResultsFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    

    
    
    public void closeFile(){
        try {
             theWriter.write("-- File Closed Normally --");
             theWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(ResultsFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getFileLocation(){
        return theFileLocation;
    }

}
