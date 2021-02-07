/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resultgenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import mdpparser.abstractResult;

/**
 *
 * @author mradkie
 */
public class runGenerator {

    private final String resultDir;
    TreeMap<Integer, resultGenerator> resultMap;
    TreeMap<String, runResults> runMap;
    // info about the number of MDP's for each event
    private final int NUMMCMDP = 50;
    private final int NUMHELIMDP = 15;
    private final int NUMTETRISMDP = 10;
    private final int NUMPOLYMDP = 14;
    private final int NUMKAMDP = 1;
    private final int NUMRTSMDP = 1;
    
    public runGenerator(String directory) {
        this.resultDir = directory;
        resultMap = new TreeMap();
        runMap = new TreeMap();
        
        String key = "";
        for (int i = 7; i <= 12; i++) {
            resultGenerator theGen = generateEventResult(i);
            resultMap.put(i, theGen);
            for (abstractResult resultVec : theGen.getResultVector()) {
                if(resultVec == null) continue;
                key = resultVec.getKey();
                if(runMap.containsKey(key)){
                    runMap.get(key).addResult(resultVec);
                }else{
                    runMap.put(key, new runResults(resultVec));
                }
            }
        }
        
    }

    private resultGenerator generateEventResult(int eventNum) {
        //create a new resultGenerator
        resultGenerator eventRes = new resultGenerator(resultDir);
        //add string filter, files should start with event_num where num is the
        //event number
        eventRes.addFilter(new String("event_" + eventNum));
        eventRes.generateFileList();
        //get a copy of the resultVectors
        return eventRes;
    }

    //returns a resultGenerator object. this might be the actual object and
    //not a reference, but i guess thats good enough for now
    public resultGenerator getResultsFromEvent(int eventNum) {
        if(!resultMap.containsKey(eventNum))return null;
        return resultMap.get(eventNum);
    }

    public void writeRunFile(String dir,runResults theRun) {
        String filename = new String("Event_"+theRun.getEventNum()+":"+theRun.getTeamName());
        try {           
            FileWriter fileOut = new FileWriter(dir+filename);
            for(abstractResult theRes : theRun.getResultVec()){
                theRes.writeToFile(fileOut);
            }
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(resultGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void writeCSVFile(String filename, runGenerator events,int frequency) {
        try {
            int stepSize = frequency;
            String csvLine="";
            FileWriter fileOut = new FileWriter(filename);
            for (runResults theRun : events.runMap.values()) {
                for(double i=stepSize;i<=100;i+=stepSize){
                    csvLine=new String(theRun.getTeamName()+","+theRun.getEventNum()+","+i/stepSize+","+theRun.getScore(i)+","+theRun.getNumSteps(i)+"\n");
                    fileOut.write(csvLine);
                }
            }
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(resultGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void writeNewCsv(String filename, runGenerator events,double frequency) {
        try {
            double stepSize = frequency;
            String csvLine="";
            FileWriter fileOut = new FileWriter(filename);
            FileWriter names = new FileWriter("../../results/names.txt");
            for (runResults theRun : events.runMap.values()) {
                csvLine=new String(theRun.getTeamName()+","+theRun.getEventNum());
                for(double i=stepSize;i<=100;i+=stepSize){
                    csvLine+=","+theRun.getScore(i);
                }
                csvLine+="\n";
                fileOut.write(csvLine);
                names.write(new String(theRun.getTeamName()+"\n"));
            }
            fileOut.close();
            names.close();
        } catch (IOException ex) {
            Logger.getLogger(resultGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        String dir = "../../results/";
        runGenerator events = new runGenerator(dir+"processedData");

        events.writeCSVFile(dir+"results.csv", events, 5);
        events.writeNewCsv(dir+"new.csv", events, 0.5);
    }
    
    private int getNumMDP(int eventNum){
        int numMDP = 0;
        switch(eventNum){
            case 7:
                numMDP = NUMHELIMDP;
                break;
            case 8:
                numMDP = NUMMCMDP;
                break;
            case 9:
                numMDP = NUMTETRISMDP;
                break;
            case 10:
                numMDP = NUMRTSMDP;
                break;
            case 11:
                numMDP = NUMPOLYMDP;
                break;
            case 12:
                numMDP = NUMKAMDP;
                break;
        }
        return numMDP;
    }
}
