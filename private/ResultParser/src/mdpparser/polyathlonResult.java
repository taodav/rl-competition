/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mdpparser;

import java.io.File;
/**
 *
 * @author mradkie
 */
public class polyathlonResult extends abstractResult{
    int MAX_STEPS = 200000;
    
    public polyathlonResult(){
        super();
    }
    public polyathlonResult(File resultFile){
        super();
        this.parseRawFile(resultFile);
    }

    public polyathlonResult(abstractResult oldResult) {
        super(oldResult);
    }
    @Override
    public double getScoreAt(double percent) {
        int mdp = this.getMDPNum();
        double returnVal = 0.0d;
        switch(mdp){
            case 50:
                returnVal = getRewardScore(percent);
                break;
            case 51:
                returnVal = getRewardScore(percent);
                break;
            case 52:
                returnVal = getRewardScore(percent);
                break;
            case 53:
                returnVal = getRewardScore(percent);
                break;
            case 54:
                returnVal = getRewardScore(percent);
                break;
            case 55:
                returnVal = getEpisodeScore(percent);
                break;
            case 56:
                returnVal = getRewardScore(percent);
                break;
            case 57:
                returnVal = getEpisodeScore(percent);
                break;
            case 58:
                returnVal = getEpisodeScore(percent);
                break;
            case 59:
                returnVal = getRewardScore(percent);
                break;
            case 60:
                returnVal = getRewardScore(percent);
                break;
            case 61:
                returnVal = getEpisodeScore(percent);
                break;
            case 62:
                returnVal = getRewardScore(percent);
                break;
            case 63:
                returnVal = getRewardScore(percent);
                break;
            case 64:
                returnVal = getRewardScore(percent);
                break;
            default:
                returnVal = getEpisodeScore(percent);
                break;
        }
        //scoring metric for MC is the number of episodes ran in 100000 steps
        return returnVal;
    }
    
    private double getEpisodeScore(double percent){
        percent = percent / 100.0d;
        int thresh = (int)(percent * MAX_STEPS);   
        int currentCount = 0;
        int index = 0;
        int maxIndex = this.getNumEpisodes()-1;

        while(currentCount < thresh && index <= maxIndex){
            currentCount += this.getStepCountAt(index);
            if(currentCount < thresh) index++;
        }
        //i hope i interpolated the data right:
        // r = r(a) + (thresh - s(a)/(s(b)-s(a))
        int prevCount = currentCount-this.getStepCountAt(index);
        double returnVal = (double)index;
        double interpol = (double)(thresh-prevCount)/(double)(currentCount-prevCount);
        returnVal = (double) (returnVal + interpol);
        
        //scoring metric for MC is the number of episodes ran in 100000 steps
        return returnVal;
    }
    private double getRewardScore(double percent){
        percent = percent / 100.0d;
        int thresh = (int) (percent * MAX_STEPS);
        int currentCount = 0;
        int index = 0;
        int maxIndex = this.getNumEpisodes() - 1;
        double cumulativeReward = 0.0d;
        double interpol = 0;

        while (currentCount < thresh && index <= maxIndex) {
            currentCount += this.getStepCountAt(index);
            if (currentCount == thresh) {
                return cumulativeReward += this.getRewardAt(index);
            }
            cumulativeReward += this.getRewardAt(index);
            if (currentCount < thresh) {
                index++;
            }
        }
        //scoring metric for Helicopter is the total reward
        //i hope i interpolated the data right:
        // r = r(a) + (thresh - s(a)/(s(b)-s(a))
        double y0 = cumulativeReward - this.getRewardAt(index);
        double y1 = cumulativeReward;
        double x0 = currentCount - this.getStepCountAt(index);
        double x1 = currentCount;
        if(x1 == x0) return y1;
        double ratio = (y1 - y0) / (x1 - x0);
        double distance = (double) (thresh - x0);
        interpol = ratio * distance;

        cumulativeReward = (double) (y0 + interpol);
        return cumulativeReward;
    }
    @Override
    public int getMaxSteps() {
        return this.MAX_STEPS;
    }
}