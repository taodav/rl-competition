package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.Proving2008;


import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeD.MainPackage.AbstractPolyProblemTypeD;
import rlVizLib.dynamicLoading.Unloadable;

public class PolyPMDP62 extends AbstractPolyProblemTypeD implements Unloadable{

    public PolyPMDP62(){
        super();
        double[] means=new double[4];
        double[] vars=new double[4];
        
        means[0]=.4;
        means[1]=.4;
        means[2]=.5;
        means[3]=.3;
        
        vars[0]=0.02;
        vars[1]=.04;
        vars[2]=.5;
        vars[3]=.1;
        
        theState.setProblemParams(means, vars);
    }
}