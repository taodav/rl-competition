package SamplePolyathlon.ProblemTypeC.MainPackage;

import java.util.Vector;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.interfaces.ProvidesEpisodeSummariesInterface;
import rlVizLib.utilities.logging.EpisodeLogger;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Reward_observation;

/*
 *  AbstractPolyProblemTypeB
 *
 *  Created by Brian Tanner on 02/03/07.
 *  Copyright 2007 Brian Tanner. All rights reserved.
 *
 */
/*
 *  AbstractPolyProblemTypeB
 *
 *  Created by Brian Tanner on 02/03/07.
 *  Copyright 2007 Brian Tanner. All rights reserved.
 *
 */
public abstract class AbstractMDPPolyProblemTypeC extends AbstractPolyProblemTypeC implements ProvidesEpisodeSummariesInterface {

    protected rlVizLib.utilities.logging.EpisodeLogger theEpisodeLogger;

    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        return p;
    }

    public AbstractMDPPolyProblemTypeC() {
        super();
    }

    //Don't use the params we're given
    //Might want to split the "Create regions" out of the code and make that
    //part of the abstract mdp
    public AbstractMDPPolyProblemTypeC(ParameterHolder theParams) {
        this();
    }
    protected double normT1;
    protected double normT2;
    protected double normTD1;
    protected double normTD2;

    @Override
    protected Observation makeObservation() {
        double thetaRange = theState.getMaxTheta1()-theState.getMinTheta1();
        double minTheta = theState.getMinTheta1();
        double maxTheta = theState.getMaxTheta1();

        double thetaTD1Range =  theState.getMaxTheta1Dot()-theState.getMinTheta1Dot();
        double minTD1 =theState.getMinTheta1Dot();

        double thetaTD2Range = theState.getMaxTheta2Dot()-theState.getMinTheta2Dot();
        double minTD2 = theState.getMinTheta2Dot();

        normT1 = (theState.getTheta1() - minTheta) / thetaRange;
        normT2 = (theState.getTheta2() - minTheta) / thetaRange;

        normTD1 = (theState.getTheta1Dot() - minTD1) / thetaTD1Range;
        normTD2 = (theState.getTheta2() - minTD2) / thetaTD2Range;

        return MDPSpecificMakeObservation();
    }

    protected abstract Observation MDPSpecificMakeObservation();

    protected abstract Action MDPSpecificActionConverter(int abstractAction);

    @Override
    public String env_init() {
        //Call env_init on super
        String superTaskSpec = super.env_init();
        theEpisodeLogger = new EpisodeLogger();
        return SamplePolyathlon.SamplePolyathlon.genericTaskSpec();
    }

    @Override
    public Observation env_start() {
        Observation theObservation = super.env_start();
        theEpisodeLogger.clear();
        theEpisodeLogger.appendLogString(theState.stringSerialize());
        return theObservation;
    }

    @Override
    public Reward_observation env_step(Action theAction) {
        Action specificAction = MDPSpecificActionConverter(theAction.intArray[0]);
        Reward_observation theRO=super.env_step(specificAction);

        theEpisodeLogger.appendLogString(theState.stringSerialize() + "_a=" + specificAction.intArray[0] + "_r=" + theRO.r + "_inGoal=" + theRO.terminal);
        return theRO;
    }

    @Override
    public String getVisualizerClassName() {
        return "";
    }
    
        //Pretty sure this is deprecated
    public Vector<String> getEpisodeSummary() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getEpisodeSummary(long charToStartOn, int charsToSend) {
        return theEpisodeLogger.getLogSubString(charToStartOn, charsToSend);
    }

}
