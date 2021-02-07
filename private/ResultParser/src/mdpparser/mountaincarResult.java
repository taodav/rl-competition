/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mdpparser;

import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author mradkie
 */
public class mountaincarResult extends abstractResult{
    int MAX_STEPS = 100000;
    
    public mountaincarResult(){
        super();
    }
    public mountaincarResult(File resultFile){
        super();
        this.parseRawFile(resultFile);
    }
    public mountaincarResult(abstractResult oldResult){
        super(oldResult);
    }
    @Override
    public double getScoreAt(double percent) {
        percent = percent / 100.0d;
        int thresh = (int)(percent * MAX_STEPS);   
        int currentCount = 0;
        int index = 0;
        int maxIndex = this.getNumEpisodes()-1;

        //this is without interpolating the data :/
        while(currentCount < thresh && index <= maxIndex){
            currentCount += this.getStepCountAt(index);
            if(currentCount < thresh) index++;
        }
        
        //i hope i interpolated the data right:
        // r = r(a) + (thresh - s(a)/(s(b)-s(a))
        int prevCount = currentCount-this.getStepCountAt(index);
        double returnVal = (double)index;
        double interpol = (double)(thresh-prevCount)/(double)(currentCount-prevCount);
        returnVal = (double) (index + interpol);
        
        //scoring metric for MC is the number of episodes ran in 100000 steps
        return returnVal;
    }
    @Override
    public int getMaxSteps() {
        return this.MAX_STEPS;
    }
}