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
public class helicopterResult extends abstractResult {

    int MAX_STEPS = 6000000;

    public helicopterResult() {
        super();
    }

    public helicopterResult(File resultFile) {
        super();
        this.parseRawFile(resultFile);
    }

    public helicopterResult(abstractResult oldResult) {
        super(oldResult);
    }

    @Override
    public double getScoreAt(double percent) {
        percent = percent / 100.0d;
        int thresh = (int) (percent * MAX_STEPS);
        int currentCount = 0;
        int index = 0;
        int maxIndex = this.getNumEpisodes() - 1;
        double cumulativeReward = 0.0d;
        double interpol = 0;

        while (currentCount < thresh && index <= maxIndex) {
            currentCount += this.getStepCountAt(index);
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