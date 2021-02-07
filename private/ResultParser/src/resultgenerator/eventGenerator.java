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
public class eventGenerator {

    private final String resultDir;
    TreeMap<Integer, resultGenerator> resultMap;
    // info about the number of MDP's for each event
    private final int NUMMCMDP = 50;
    private final int NUMHELIMDP = 15;
    private final int NUMTETRISMDP = 10;
    private final int NUMPOLYMDP = 14;
    private final int NUMKAMDP = 1;
    private final int NUMRTSMDP = 1;
    
    private boolean debug=false;
    public eventGenerator(String directory) {
        this.resultDir = directory;
        resultMap = new TreeMap();

        for (int i = 7; i <= 12; i++) {
            resultMap.put(i, generateEventResult(i));
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

    public void writeEventFile(String filename, int eventNum) {
        try {
            if(debug)System.out.println("++++++++++++  EVENT:"+eventNum+" ++++++++++++++++");
            FileWriter fileOut = new FileWriter(filename);
            double highest = 0;
            String bestString = "";
            int numMDP=this.getNumMDP(eventNum);
            for (int i = 50; i < (50+numMDP); i++) {
                if(debug)System.out.println("========================= MDP "+i+" ======================");
                highest = Double.NEGATIVE_INFINITY;
                bestString = "";
                if(this.getResultsFromEvent(eventNum) == null)continue;
                for (abstractResult resultVec : this.getResultsFromEvent(eventNum).getResultVector()) {
                    if (resultVec == null) continue;
                    if (resultVec.getMDPNum() == i) {
                        if(debug)System.out.println("Comparing: "+resultVec.getScoreAt(100)+" to current highest: "+highest);
                        if(resultVec.getScoreAt(100)>highest){
                            bestString = resultVec.getResultString();
                            highest = resultVec.getScoreAt(100);
                        }
                    }
                }
                fileOut.write(bestString);
                fileOut.flush();
            }
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(resultGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void main(String[] args) {
        String dir = "../../results";
        eventGenerator events = new eventGenerator(dir+"/processedData");
        for (int i = 7; i <= 12; i++) {
            events.writeEventFile(new String("../../results/event" + (i-1) + ".txt"),i);
        }
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
