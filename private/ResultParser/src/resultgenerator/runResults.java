/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resultgenerator;

import java.util.Vector;
import mdpparser.abstractResult;

/**
 *
 * @author mradkie
 */
public class runResults {

    private Vector<abstractResult> resultVec=null;
    private String teamName=null;
    private String key=null;
    private int eventNum=0;
    private int MAXSTEPS=0;
    private String finishedAt=null;
    
    //info about events
    private final int NUMMCMDP = 50;
    private final int NUMHELIMDP = 15;
    private final int NUMTETRISMDP = 10;
    private final int NUMPOLYMDP = 14;
    private final int NUMKAMDP = 1;
    private final int NUMRTSMDP = 1;
    
    public runResults(abstractResult theResult) {
        resultVec = new Vector<abstractResult>();
        resultVec.add(theResult);
        initialize();
    }
    
    public void addResult(abstractResult theResult){
        if(resultVec == null){
            resultVec = new Vector<abstractResult>();
            resultVec.add(theResult);
            initialize();
        }else{
            resultVec.add(theResult);
        }   
    }
    
    private void initialize(){
        assert(resultVec != null);
        abstractResult first = resultVec.firstElement();
        teamName = first.getTeamName();
        key = first.getKey();
        eventNum = first.getEventNum();
        MAXSTEPS = this.getNumMDP(eventNum) * first.getMaxSteps();
        
    }
    public String getTeamName(){
        return this.teamName;
    }
    public String getKey(){
        return this.key;
    }
    public Vector<abstractResult> getResultVec(){
        return this.resultVec;
    }
    //get the number of steps after a given percent (0-100)
    public int getNumSteps(double percent){
        //assert its a valid percent
        assert((percent >= 0)&&(percent <= 100));
        percent = percent / 100.0d;
        return (int)(percent * MAXSTEPS);
    }
    public abstractResult getResultAt(int index){
        if(index > resultVec.size()) return null;
        
        return resultVec.elementAt(index);
    }
    public int getEventNum(){
        return this.eventNum;
    }
    public int numResults(){
        return this.resultVec.size();
    }
    //might Break on incomplete runs...
    public double getScore(double percentRun){
        //assert its a valid percent
        assert((percentRun >= 0)&&(percentRun <= 100));
        percentRun = percentRun / 100.0d;
        int thresh = (int)(percentRun * MAXSTEPS);
        int stepsRemaining = thresh;
        int index = 0;
        double score = 0.0d;
        int maxStepsMDP = resultVec.elementAt(index).getMaxSteps();
        while((stepsRemaining > 0) && (index < resultVec.size())){
            
            if(stepsRemaining >= maxStepsMDP){
                score += resultVec.elementAt(index).getScoreAt(100);
                stepsRemaining -= maxStepsMDP;
            } else {
                double perc = (double)stepsRemaining/(double)maxStepsMDP;
                double temp = resultVec.elementAt(index).getScoreAt(perc);           
                score += temp;
                stepsRemaining = 0;
            }
            index++;
        }
        
        return score;
    }
    
    private int getNumMDP(int eventNum){
        int numMDP = 0;
        switch(eventNum){
            case 6:
                numMDP = NUMHELIMDP;
                break;
            case 7:
                numMDP = NUMMCMDP;
                break;
            case 8:
                numMDP = NUMTETRISMDP;
                break;
            case 9:
                numMDP = NUMRTSMDP;
                break;
            case 10:
                numMDP = NUMPOLYMDP;
                break;
            case 11:
                numMDP = NUMKAMDP;
                break;
        }
        return numMDP;
    }
}
