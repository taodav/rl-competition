/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.proving.experimentRunners;

import org.rlcommunity.competition.proving.*;

import org.rlcommunity.competition.phoneHomeInterface.RLEvent;

/**
 *
 * @author btanner
 */
public class ExperimentRunningFactory {
    

    public static AbstractExperimentRunner makeExperimentRunner(RLEvent theEvent, Controller theController) {
       if(theEvent==RLEvent.Helicopter)return new JarExperimentRunner(theEvent,theController);
       if(theEvent==RLEvent.Acrobot)return new JarExperimentRunner(theEvent,theController);
       if(theEvent==RLEvent.Tetris)return new JarExperimentRunner(theEvent,theController);
       if(theEvent==RLEvent.Octopus)return new JarExperimentRunner(theEvent,theController);
       if(theEvent==RLEvent.Polyathlon)return new JarExperimentRunner(theEvent,theController);
       if(theEvent==RLEvent.Mario)return new JarExperimentRunner(theEvent, theController);
       
       //hack for testing?
       if(theEvent==RLEvent.Testing_Helicopter)return new JarExperimentRunner(theEvent,theController);
       if(theEvent==RLEvent.Testing_Acrobot)return new JarExperimentRunner(theEvent,theController);
       if(theEvent==RLEvent.Testing_Tetris)return new JarExperimentRunner(theEvent,theController);
       if(theEvent==RLEvent.Testing_Octopus)return new JarExperimentRunner(theEvent,theController);
       if(theEvent==RLEvent.Testing_Polyathlon)return new JarExperimentRunner(theEvent,theController);
       if(theEvent==RLEvent.Testing_Mario)return new JarExperimentRunner(theEvent, theController);
       //if(theEvent==RLEvent.Keepaway)return new keepawayExperimentRunner(theEvent,theController);
       
        System.err.println("Unknown event");
        return null;
    }

}
