/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.SamplePolyathlon2008;

import org.rlcommunity.rlglue.codec.EnvironmentInterface;

/**
 *
 * @author btanner
 */
class GenPolyParamData {

    static EnvironmentInterface makeMDP(int whichDomain) {
//Some of each
       if(whichDomain==0){
            double []doubleMaxs=new double[]{1.0d,0.0d};
            double []doubleMins=new double[]{0.0d,-10.d};
            
            int[] intMaxs=new int[]{5};
            int[] intMins=new int[]{2};

            int numActions=4;

            return new ParameterizedMDP(numActions, doubleMins, doubleMaxs, intMins, intMaxs);

        }
//No int actions
       if(whichDomain==1){
            double []doubleMaxs=new double[]{100.0d,-1.0d};
            double []doubleMins=new double[]{Math.PI,-10.d};
            
            int[] intMaxs=new int[]{};
            int[] intMins=new int[]{};

            int numActions=2;

            return new ParameterizedMDP(numActions, doubleMins, doubleMaxs, intMins, intMaxs);

        }
//No double observations
       if(whichDomain==2){
            double []doubleMaxs=new double[]{};
            double []doubleMins=new double[]{};
            
            int[] intMaxs=new int[]{10,20,-5};
            int[] intMins=new int[]{0,0,-100};

            int numActions=5;

            return new ParameterizedMDP(numActions, doubleMins, doubleMaxs, intMins, intMaxs);

        }

        System.out.println(whichDomain+" is not a valid choice for a domain, choosing 0");
        return makeMDP(0);
    }

}
