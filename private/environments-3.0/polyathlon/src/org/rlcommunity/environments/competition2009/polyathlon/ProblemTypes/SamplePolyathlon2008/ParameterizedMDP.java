/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.SamplePolyathlon2008;

import java.util.Random;
import rlVizLib.Environments.EnvironmentBase;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;
import org.rlcommunity.rlglue.codec.types.Reward_observation_terminal;




/**
 *
 * @author btanner
 */
public class ParameterizedMDP extends EnvironmentBase implements rlVizLib.dynamicLoading.Unloadable {
    int numActions;
    int numDoubleObservations;
    int numIntObservations;

    Random randGenerator=new Random();
    double[] doubleMaxs;
    double[] doubleMins;

    int[] intMaxs;
    int[] intMins;

    public ParameterizedMDP(int numActions, double[] doubleMins, double[] doubleMaxs, int[] intMins, int[] intMaxs){
        this.numActions=numActions;
        this.numDoubleObservations=doubleMins.length;
        this.numIntObservations=intMins.length;
        this.doubleMaxs=doubleMaxs;
        this.doubleMins=doubleMins;

        this.intMins=intMins;
        this.intMaxs=intMaxs;

    }

    @Override
    protected Observation makeObservation() {
        Observation o = new Observation(numIntObservations,numDoubleObservations);
        
        for(int i=0;i<numDoubleObservations;i++){
            double obsRange=doubleMaxs[i]-doubleMins[i];
            //Find a number of the right size
            double baseObs=randGenerator.nextDouble()*obsRange;

            //Translate to the range
            baseObs+=doubleMins[i];
            o.doubleArray[i]=baseObs;
        }

        for(int i=0;i<numIntObservations;i++){
            int obsRange=intMaxs[i]-intMins[i];
            //Find a number of the right size
            int baseObs=randGenerator.nextInt(obsRange);

            //Translate to the range
            baseObs+=intMins[i];
            o.intArray[i]=baseObs;
        }
        return o;
    }

    public String env_init() {
       int totalObs=numDoubleObservations+numIntObservations;
            String taskSpec="2:e:"+totalObs+"_[";
            for(int i=0;i<numIntObservations;i++)taskSpec+="i,";
            for(int i=0;i<numDoubleObservations;i++)taskSpec+="f,";
            
            taskSpec+="]";
            //Give ranges of ints
            for(int i=0;i<numIntObservations;i++)taskSpec+="_["+intMins[i]+","+intMaxs[i]+"]";
            //Don't give ranges of doubles
            for(int i=0;i<numDoubleObservations;i++)taskSpec+="_[,]";
        //      "2:e:1_[i]_[0,N-1]:1_[i]_[0,3]:[-1,0]"
            
            //now do actions
            taskSpec+=":1_[i]_[0,"+(numActions-1)+"]";
            //unspecified rewards
            taskSpec+=":[,]";
            
            return taskSpec;

    }

    public Observation env_start() {
      //don't do anything, we're just going to make random observations and terminate randomly
      return makeObservation();
    }

    public Reward_observation_terminal env_step(Action a) {
        if(a.intArray[0]>=numActions||a.intArray[0]<0){
            System.err.println(a.intArray[0]+" is an invalid action... choosing action 0");
            a.intArray[0]=0;
        }
        double reward=randGenerator.nextGaussian();
        int terminal=0;
        //1/100 chance of terminating
        if(randGenerator.nextInt(100)==0)terminal=1;

        return new Reward_observation_terminal(reward,makeObservation(),terminal);
    }

    public void env_cleanup() {

    }


    public String env_message(String arg0) {
        return null;
    }

 
}
