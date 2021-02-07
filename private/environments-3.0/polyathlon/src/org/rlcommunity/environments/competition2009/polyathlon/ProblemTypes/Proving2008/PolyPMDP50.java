package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.Proving2008;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeD.MainPackage.AbstractPolyProblemTypeD;
import rlVizLib.dynamicLoading.Unloadable;

public class PolyPMDP50 extends AbstractPolyProblemTypeD implements Unloadable{

    public PolyPMDP50(){
        super();
        double[] means=new double[4];
        double[] vars=new double[4];
        
        means[0]=.5;
        means[1]=.8;
        means[2]=.2;
        means[3]=.3;
        
        vars[0]=0.0;
        vars[1]=.05;
        vars[2]=.2;
        vars[3]=.02;
        
        theState.setProblemParams(means, vars);
    }
}