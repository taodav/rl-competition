package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeH.MainPackage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
public abstract class AbstractMDPPolyProblemTypeH extends AbstractPolyProblemTypeH implements ProvidesEpisodeSummariesInterface {

    protected rlVizLib.utilities.logging.EpisodeLogger theEpisodeLogger=null;
    protected double normTheta;
    protected double normThetaDot;
    protected double normPos;
    protected double normVel;
    

    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        return p;
    }

    public AbstractMDPPolyProblemTypeH() {
        super();
    }

    //Don't use the params we're given
    //Might want to split the "Create regions" out of the code and make that
    //part of the abstract mdp
    public AbstractMDPPolyProblemTypeH(ParameterHolder theParams) {
        this();
    }

    @Override
    protected Observation makeObservation() {
   
        double conservativeLeftPosBound=super.leftCartBound-.1;
        double conservativeRightPosBound=super.rightCartBound+.1;
        //Add a bit of padding because last observation can go past the boundaries

        normTheta = (super.theta + Math.PI) / (2.0d * Math.PI);
        normPos = (super.x - conservativeLeftPosBound) / (conservativeRightPosBound - conservativeLeftPosBound);

        //Guess
        if(super.theta_dot>6 || super.theta_dot<-6){
        }
        normThetaDot = (super.theta_dot - -9.0d) / (9.0d - - 9.0d);
        //Guess
        normVel =(super.x_dot - -6.0d) / (6.0d - - 6.0d);

//        normTheta=theta;
//        normThetaDot=theta_dot;
//        normPos=x;
//        normVel=x_dot;


        return MDPSpecificMakeObservation();
    }

    protected abstract Observation MDPSpecificMakeObservation();

    protected abstract Action MDPSpecificActionConverter(int abstractAction);

    @Override
    public String env_init() {
        //Call env_init on super
        String superTaskSpec = super.env_init();
        theEpisodeLogger=new EpisodeLogger();
        return superTaskSpec;
    }

    @Override
    public Observation env_start() {
        Observation theObservation = super.env_start();
        theEpisodeLogger.clear();
        theEpisodeLogger.appendLogString(createLogString());
        return theObservation;
    }

    @Override
    public Reward_observation_terminal env_step(Action theAction) {
        Action specificAction = MDPSpecificActionConverter(theAction.intArray[0]);
        Reward_observation_terminal theRO=super.env_step(specificAction);
        theEpisodeLogger.appendLogString(createLogString() + "_a=" + specificAction.intArray[0] + "_r=" + theRO.r + "_inGoal=" + theRO.terminal);
        return theRO;
    }

    @Override
    public String getVisualizerClassName() {
        return super.getVisualizerClassName();
    }

    //Pretty sure this is deprecated
    public Vector<String> getEpisodeSummary() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getEpisodeSummary(long charToStartOn, int charsToSend) {
        return theEpisodeLogger.getLogSubString(charToStartOn, charsToSend);
    }

     private NumberFormat formatter = new DecimalFormat ( "000.000" ) ;
     private String createLogString() {
       StringBuffer b=new StringBuffer();
       //There are 4 things we're putting in
       b.append("2");
        //It's a double
       b.append("_d_");
       b.append(formatter.format(super.theta));
       b.append("_d_");
       b.append(formatter.format(super.x));
        b.append("_");
       return b.toString();
    }
}
