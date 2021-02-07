/* Real Time Strategy Agent in Java for the RL Competition
* Copyright (C) 2007, Marc Lanctot
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. */
package RTSAgent;

import rlglue.agent.Agent;
import rlglue.types.Action;
import rlglue.types.Observation;
import java.util.ArrayList;

import java.util.Random;

public class RTSAgent implements Agent
{
  boolean freeze = false;
  Parameters parms;
  State state;
  int playerNum = 1; // always 1 for RL player
  
  // Memory variables
  int time;
  int done_base_time;
  int done_worker_time;
  int done_marine_time;
  boolean have_base;
  int mp_x, mp_y;
  

  public RTSAgent() 
  {
      
  }

  // This method is only called once per experiment. 
  public void agent_init(final String taskSpec)
  {
    parms = new Parameters();
    parms.parseTaskSpec(taskSpec);
  }

  // This method is called upon the start of a new epsiode
  // The agent must return the first action taken. 
  public Action agent_start(Observation o)
  {
    time = 0;
    done_base_time = 0;
    mp_x = mp_y = 0;
    have_base = false;
    freeze = false; 

    state = new State(parms);



    time++;
    
    state.applyObservation(o);
    
    Action a = getAction();

    return a;
  }

  // This method is called with the reward and observation action 
  // the previous action was taken. The action chosen at the current
  // step is returned. 
  public Action agent_step(double r, Observation o)
  {
    time++;
    
    state.reset();
    
    state.applyObservation(o);    

    
    Action a = getAction();

    return a;
  }
 
  // Called after the last step of episode, indicating the terminal reward
  public void agent_end(double r)
  {
  }
  
  // Called once per experiment. 
  // No need to free memory.. oh, the benefits of using Java :) 
  public void agent_cleanup() 
  {
  }
  
  public String agent_message(final String message)
  {
    return "This agent does not respond to any messages.";
  }
  
  public void agent_freeze()
  {
    freeze = true;
  }
  
  public Action getAction()
  {
    // here is where all the magic happens. 
    // All state information is in the 'state' and 'parms' fields
    //
    // The code below should be replaced by clever RL techniques!
    
    ArrayList<Integer> actionList = new ArrayList<Integer>();

    // reset memory variables if needed
    
    if (time == done_worker_time) 
      done_worker_time = 0;
    
    if (time == done_marine_time) 
      done_marine_time = 0; 
    
    if (time == done_base_time)
      done_base_time = 0;
    
    
    for (GameObj obj : state.objects)
    {
      int id = obj.id;
      
      //System.out.println("Found object id="+id+" owner="+obj.owner + " type="+obj.getType());
      
      if (obj.owner == playerNum && obj.getType().equals("worker"))
      {
        Worker worker = (Worker)obj;
        chooseAction(actionList, worker);
      }
      else if (obj.owner == playerNum && obj.getType().equals("marine"))
      {
        Marine marine = (Marine)obj;
        chooseAction(actionList, marine);
      }
      else if (obj.owner == playerNum && obj.getType().equals("base"))
      {
        have_base = true;
        Base base = (Base)obj;
        chooseAction(actionList, base);        
      }
      else if (obj.getType().equals("mineral_patch"))
      {
        mp_x = obj.x;
        mp_y = obj.y;
      }
    }

    Action a = Helpers.convertActionList(actionList);
    //System.out.println("action int array is " + Helpers.intArrayToString(a.intArray));

    return a;
  }
  
  public void chooseAction(ArrayList<Integer> actionList, Worker worker)
  { 
    if (done_base_time > 0)
      return;

    if (Helpers.offMap(worker, parms)) // workers off map when building
      return;
    
    if (worker.is_moving == 1) {    
      
      double roll = Helpers.RNG.nextDouble();
      if (roll <= 0.03 && !have_base)
      {
        done_base_time = time + parms.base_build_time;
        Helpers.addBuildBaseAction(actionList, worker.id);
        return;
      }
      else if (roll <= 0.05) {
        Helpers.addStopAction(actionList, worker.id);
        return;
      }
      else
        return;
    }
    
    // go towards mineral patch if we know about it, and we have a base already
    if ((mp_x > 0 || mp_y > 0) && have_base) {
      Helpers.addMoveAction(actionList, worker.id, mp_x, mp_y, parms.worker_max_speed);
      return;
    }

    // otherwise, random move action    
    int x = Helpers.RNG.nextInt(parms.width);
    int y = Helpers.RNG.nextInt(parms.height);
    
    Helpers.addMoveAction(actionList, worker.id, x, y, parms.worker_max_speed);
  }
  
  public void chooseAction(ArrayList<Integer> actionList, Marine marine)
  {    
    if (marine.is_moving == 1) {    
      double roll = Helpers.RNG.nextDouble();
      if (roll <= 0.05) {
        Helpers.addStopAction(actionList, marine.id);
        return;
      }
      else
        return; 
    }

    // otherwise, random move
    int x = Helpers.RNG.nextInt(parms.width);
    int y = Helpers.RNG.nextInt(parms.height);
    
    Helpers.addMoveAction(actionList, marine.id, x, y, parms.marine_max_speed);
  }

  public void chooseAction(ArrayList<Integer> actionList, Base base)
  {    
    double roll = Helpers.RNG.nextDouble();

    if (roll < 0.5)
      return;
    else if (roll < 0.75 && done_worker_time <= 0
             && state.minerals >= parms.worker_cost)
    {
      done_worker_time = time + parms.worker_training_time;
      Helpers.addTrainWorkerAction(actionList, base.id);
      return;
    }
    else if (roll < 1 && done_marine_time <= 0
             && state.minerals >= parms.marine_cost)
    {
      done_marine_time = time + parms.marine_training_time;
      Helpers.addTrainMarineAction(actionList, base.id);
      return;
    }
  }
  
}
