package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeB.MainPackage;

import java.util.Vector;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.interfaces.ProvidesEpisodeSummariesInterface;
import rlVizLib.utilities.logging.EpisodeLogger;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;


/*
 *  AbstractPolyProblemTypeB
 *
 *  Created by Brian Tanner on 02/03/07.
 *  Copyright 2007 Brian Tanner. All rights reserved.
 *
 */
import org.rlcommunity.rlglue.codec.types.Reward_observation_terminal;
public abstract class AbstractMDPPolyProblemTypeB extends AbstractPolyProblemTypeB implements ProvidesEpisodeSummariesInterface {

    protected rlVizLib.utilities.logging.EpisodeLogger theEpisodeLogger=null;
    protected double normX;
    protected double normY;

    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        return p;
    }

    public AbstractMDPPolyProblemTypeB() {
        super();
    }

    //Don't use the params we're given
    //Might want to split the "Create regions" out of the code and make that
    //part of the abstract mdp
    public AbstractMDPPolyProblemTypeB(ParameterHolder theParams) {
        this();
    }

    @Override
    protected Observation makeObservation() {
        normX = theState.getPosition().getX() / worldRect.getMaxX();
        normY = theState.getPosition().getY() / worldRect.getMaxY();
        return MDPSpecificMakeObservation();
    }

    protected abstract Observation MDPSpecificMakeObservation();

    protected abstract Action MDPSpecificActionConverter(int abstractAction);

    @Override
    public String env_init() {
        //Call env_init on super
        theEpisodeLogger=new EpisodeLogger();
        return super.env_init();
    }

    public Observation env_start() {
        Observation theObservation = super.env_start();
        theEpisodeLogger.clear();
        theEpisodeLogger.appendLogString(theState.stringSerialize());
        return theObservation;
    }

    public Reward_observation_terminal env_step(Action theAction) {
        Action specificAction = MDPSpecificActionConverter(theAction.intArray[0]);
        Reward_observation_terminal theRO=super.env_step(specificAction);
        theEpisodeLogger.appendLogString(theState.stringSerialize() + "_a=" + specificAction.intArray[0] + "_r=" + theRO.r + "_inGoal=" + theRO.terminal);
        return theRO;
    }

    //Pretty sure this is deprecated
    public Vector<String> getEpisodeSummary() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getEpisodeSummary(long charToStartOn, int charsToSend) {
        return theEpisodeLogger.getLogSubString(charToStartOn, charsToSend);
    }
}
