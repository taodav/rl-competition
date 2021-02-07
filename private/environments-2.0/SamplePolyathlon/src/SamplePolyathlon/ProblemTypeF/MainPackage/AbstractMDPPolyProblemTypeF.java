package SamplePolyathlon.ProblemTypeF.MainPackage;

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
public abstract class AbstractMDPPolyProblemTypeF extends AbstractPolyProblemTypeF implements ProvidesEpisodeSummariesInterface {

    protected rlVizLib.utilities.logging.EpisodeLogger theEpisodeLogger=null;
    protected double normX;
    protected double normY;
    protected double normAttackerX;
    protected double normAttackerY;
    

    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        return p;
    }

    public AbstractMDPPolyProblemTypeF() {
        super();
    }

    //Don't use the params we're given
    //Might want to split the "Create regions" out of the code and make that
    //part of the abstract mdp
    public AbstractMDPPolyProblemTypeF(ParameterHolder theParams) {
        this();
    }

    @Override
    protected Observation makeObservation() {
        normX = theState.getAgentPosition().getX() / worldRect.getMaxX();
        normY = theState.getAgentPosition().getY() / worldRect.getMaxY();
        normAttackerX = theState.getAttackerPosition().getX() / worldRect.getMaxX();
        normAttackerY = theState.getAttackerPosition().getY() / worldRect.getMaxY();
        return MDPSpecificMakeObservation();
    }

    protected abstract Observation MDPSpecificMakeObservation();

    protected abstract Action MDPSpecificActionConverter(int abstractAction);

    @Override
    public String env_init() {
        //Call env_init on super
        String superTaskSpec = super.env_init();
        theEpisodeLogger=new EpisodeLogger();
        return SamplePolyathlon.SamplePolyathlon.genericTaskSpec();
    }

    public Observation env_start() {
        Observation theObservation = super.env_start();
        theEpisodeLogger.clear();
        theEpisodeLogger.appendLogString(theState.stringSerialize());
        return theObservation;
    }

    public Reward_observation env_step(Action theAction) {
        Action specificAction = MDPSpecificActionConverter(theAction.intArray[0]);
        Reward_observation theRO=super.env_step(specificAction);
        theEpisodeLogger.appendLogString(theState.stringSerialize() + "_a=" + specificAction.intArray[0] + "_r=" + theRO.r + "_inGoal=" + theRO.terminal);
        return theRO;
    }

//    public String getVisualizerClassName() {
//        return "";//SamplePolyathlon.ProblemTypeB.Visualizer.AbstractPolyProblemTypeBVisualizer";
//    }

    //Pretty sure this is deprecated
    public Vector<String> getEpisodeSummary() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getEpisodeSummary(long charToStartOn, int charsToSend) {
        return theEpisodeLogger.getLogSubString(charToStartOn, charsToSend);
    }
}
