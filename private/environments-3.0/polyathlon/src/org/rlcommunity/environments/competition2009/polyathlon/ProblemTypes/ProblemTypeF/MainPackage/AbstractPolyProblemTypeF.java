package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeF.MainPackage;

import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeF.Messages.CGWMapResponse;
import org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeF.Messages.CGWPositionResponse;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.Vector;

import org.rlcommunity.environments.competition2009.polyathlon.TrainingPolyathlon;
import rlVizLib.Environments.EnvironmentBase;
import rlVizLib.general.ParameterHolder;
import rlVizLib.messaging.NotAnRLVizMessageException;
import rlVizLib.messaging.environment.EnvironmentMessageParser;
import rlVizLib.messaging.environment.EnvironmentMessages;
import rlVizLib.messaging.interfaces.HasAVisualizerInterface;
import rlVizLib.messaging.interfaces.getEnvMaxMinsInterface;
import rlVizLib.messaging.interfaces.getEnvObsForStateInterface;
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
import rlVizLib.messaging.interfaces.HasImageInterface;

public abstract class AbstractPolyProblemTypeF extends EnvironmentBase implements
        getEnvMaxMinsInterface,
        getEnvObsForStateInterface,
        HasAVisualizerInterface, HasImageInterface {

    //In case visualizer asks for it before init is called
    protected PolyProblemTypeFState theState = new PolyProblemTypeFState();
//	protected  Point2D theState=new Point2D.Double(0,0);
    protected Point2D agentSize;
    protected Point2D attackerSize;
    protected Rectangle2D currentAgentRect;
    protected Rectangle2D currentAttackerRect;
    protected Rectangle2D worldRect;
    protected Vector<Rectangle2D> resetRegions = new Vector<Rectangle2D>();
    protected Vector<Rectangle2D> barrierRegions = new Vector<Rectangle2D>();
    protected Vector<Double> thePenalties = new Vector<Double>();
    protected double agentSpeed = 25.0d;
    protected double attackerSpeed = 25.0d;
    protected double catchPunishment = -1.0;
    protected int attackerPolicy = 1;
    protected Vector<Rectangle2D> rewardRegions = new Vector<Rectangle2D>();
    protected Vector<Double> theRewards = new Vector<Double>();

    public static ParameterHolder getDefaultParameters() {
        ParameterHolder p = new ParameterHolder();
        p.addDoubleParam("cont-grid-world-minX", 0.0d);
        p.addDoubleParam("cont-grid-world-minY", 0.0d);
        p.addDoubleParam("cont-grid-world-width", 200.0d);
        p.addDoubleParam("cont-grid-world-height", 200.0d);
        p.addDoubleParam("cont-grid-world-agent-speed", 10.0d);
        p.addDoubleParam("cont-grid-world-attacker-speed", 10.0d);
        p.addIntegerParam("cont-grid-world-attacker-policy", 2);
        return p;
    }

    public AbstractPolyProblemTypeF() {
        this(getDefaultParameters());
    }

    protected void setAttackerPolicy(int policyType) {
        attackerPolicy = policyType;
    }

    public AbstractPolyProblemTypeF(ParameterHolder theParams) {
        double minX = theParams.getDoubleParam("cont-grid-world-minX");
        double minY = theParams.getDoubleParam("cont-grid-world-minY");
        double width = theParams.getDoubleParam("cont-grid-world-width");
        double height = theParams.getDoubleParam("cont-grid-world-height");
        agentSpeed = theParams.getDoubleParam("cont-grid-world-agent-speed");
        attackerSpeed = theParams.getDoubleParam("cont-grid-world-attacker-speed");
        attackerPolicy = theParams.getIntegerParam("cont-grid-world-attacker-policy");

        worldRect = new Rectangle2D.Double(minX, minY, width, height);
        agentSize = new Point2D.Double(4.0d, 4.0d);
        attackerSize = new Point2D.Double(8.0d, 8.0d);
        addAllRegions();
    }

    protected abstract void addAllRegions();

    protected Rectangle2D makeAgentSizedRectFromPosition(Point2D thePos) {
        return new Rectangle2D.Double(thePos.getX(), thePos.getY(), agentSize.getX(), agentSize.getY());
    }

    protected Rectangle2D makeAttackerSizedRectFromPosition(Point2D thePos) {
        return new Rectangle2D.Double(thePos.getX(), thePos.getY(), attackerSize.getX(), attackerSize.getY());
    }

    protected void updateCurrentAgentRect() {
        currentAgentRect = makeAgentSizedRectFromPosition(theState.getAgentPosition());
    }

    protected void updateCurrentAttackerRect() {
        currentAttackerRect = makeAttackerSizedRectFromPosition(theState.getAttackerPosition());
    }

    @Override
    protected Observation makeObservation() {
        Observation currentObs = new Observation(0, 4);
        currentObs.doubleArray[0] = theState.getAgentPosition().getX();
        currentObs.doubleArray[1] = theState.getAgentPosition().getY();
        currentObs.doubleArray[1] = theState.getAttackerPosition().getX();
        currentObs.doubleArray[1] = theState.getAttackerPosition().getY();
        return currentObs;
    }

    public void env_cleanup() {
    }

    public String env_init() {
        String taskSpec = TrainingPolyathlon.makeTaskSpec();
        return taskSpec;
    }

    public String env_message(String theMessage) {
        EnvironmentMessages theMessageObject;
        try {
            theMessageObject = EnvironmentMessageParser.parseMessage(theMessage);
        } catch (NotAnRLVizMessageException e) {
            System.err.println("Someone sent mountain Car a message that wasn't RL-Viz compatible");
            return "I only respond to RL-Viz messages!";
        }

        if (theMessageObject.canHandleAutomatically(this)) {
            return theMessageObject.handleAutomatically(this);
        }


//		If it wasn't handled automatically, maybe its a custom Mountain Car Message
        if (theMessageObject.getTheMessageType() == rlVizLib.messaging.environment.EnvMessageType.kEnvCustom.id()) {

            String theCustomType = theMessageObject.getPayLoad();

            if (theCustomType.equals("GETCGWMAP")) {
                //It is a request for the state
                CGWMapResponse theResponseObject = new CGWMapResponse(getWorldRect(), resetRegions, rewardRegions, theRewards, barrierRegions, thePenalties);
                return theResponseObject.makeStringResponse();
            }
            if (theCustomType.equals("GETCGWPOS")) {
                //It is a request for the state
                CGWPositionResponse theResponseObject = new CGWPositionResponse(theState.getAgentPosition().getX(), theState.getAgentPosition().getY(), theState.getAttackerPosition().getX(), theState.getAttackerPosition().getY());
                return theResponseObject.makeStringResponse();
            }
        }
        System.err.println("We need some code written in Env Message for ContinuousGridWorld.. unknown request received: " + theMessage);
        Thread.dumpStack();
        return null;
    }

    public Observation env_start() {
        randomizeAgentPosition();

//		setAgentPosition(new Point2D.Double(startX,startY));

        while (calculateMaxBarrierAtPosition(currentAgentRect) >= 1.0f || !getWorldRect().contains(currentAgentRect)) {
            randomizeAgentPosition();
        }

        setAttackerPosition(new Point2D.Double(180.0, 80.0));
        moveAttacker();

        return makeObservation();

    }

    public Reward_observation_terminal env_step(Action action) {
        int theAction = action.intArray[0];

        double dx = 0;
        double dy = 0;

        //Should find a good way to abstract actions and add them in like the old wya, that was good

        if (theAction == 0) {
            dx = agentSpeed;
        }
        if (theAction == 1) {
            dx = -agentSpeed;
        }
        if (theAction == 2) {
            dy = agentSpeed;
        }
        if (theAction == 3) {
            dy = -agentSpeed;
        }

        //Add a small bit of random noise
        double noiseX = .125d * (Math.random() - 0.5d);
        double noiseY = .125d * (Math.random() - 0.5d);

        dx += noiseX;
        dy += noiseY;
        Point2D nextPos = new Point2D.Double(theState.getAgentPosition().getX() + dx, theState.getAgentPosition().getY() + dy);


        nextPos = updateNextPosBecauseOfWorldBoundary(nextPos);
        nextPos = updateNextPosBecauseOfBarriers(nextPos);

        theState.setAgentPositionFromPoint(nextPos);
        updateCurrentAgentRect();
        updateCurrentAttackerRect();
        boolean inResetRegion = false;
        boolean agentCaught = false;
        double reward = getRewardFromRewardRegions();


        if (resetRegions.get(0).contains(currentAgentRect)) {
            inResetRegion = true;
        }
        if (currentAgentRect.intersects(currentAttackerRect)) {
            agentCaught = true;
        }
        if (!agentCaught) {
            agentCaught = moveAttacker();
        }
        if (agentCaught) {
            reward += catchPunishment;
        }

        return makeRewardObservation(reward, inResetRegion || agentCaught);
    }

    protected double getRewardFromRewardRegions() {
        double reward = 0.0d;

        for (int i = 0; i < rewardRegions.size(); i++) {
            if (rewardRegions.get(i).contains(currentAgentRect)) {
                reward += theRewards.get(i);
            }
        }
        return reward;
    }

    protected Rectangle2D getAgent() {
        return currentAgentRect;

    }

    protected void setAgentPosition(Point2D dp) {
        this.theState.setAgentPositionFromPoint(dp);
        updateCurrentAgentRect();
    }

    protected void setAttackerPosition(Point2D dp) {
        this.theState.setAttackerPositionFromPoint(dp);
        updateCurrentAttackerRect();
    }

    protected void addResetRegion(Rectangle2D resetRegion) {
        resetRegions.add(resetRegion);
    }

    //Penalty is between 0 and 1, its a movement penalty
    protected void addBarrierRegion(Rectangle2D barrierRegion, double penalty) {
        barrierRegions.add(barrierRegion);

        assert (penalty >= 0);
        assert (penalty <= 1);
        thePenalties.add(penalty);
    }

    protected void addRewardRegion(Rectangle2D rewardRegion, double reward) {
        rewardRegions.add(rewardRegion);
        theRewards.add(reward);
    }

    protected Rectangle2D getWorldRect() {
        return worldRect;
    }

    protected void randomizeAgentPosition() {
        double startX = Math.random() * getWorldRect().getWidth();
        double startY = Math.random() * getWorldRect().getHeight();

        startX = 0.1;
        startY = 100.0;

        setAgentPosition(new Point2D.Double(startX, startY));
    }

    protected double calculateMaxBarrierAtPosition(Rectangle2D r) {
        double maxPenalty = 0.0f;
        //System.out.println("Checking if: "+r+" hits any barrires");
        for (int i = 0; i < barrierRegions.size(); i++) {
            if (barrierRegions.get(i).intersects(r)) {
                //System.out.println("\t intersects: "+barrierRegions.get(i));

                double penalty = thePenalties.get(i);
                if (penalty > maxPenalty) {
                    maxPenalty = penalty;
                }
            }//else
            //System.out.println("\t DOES NOT intersect: "+barrierRegions.get(i));

        }
        return maxPenalty;
    }

    protected boolean intersectsResetRegion(Rectangle2D r) {
        for (int i = 0; i < resetRegions.size(); i++) {
            if (resetRegions.get(i).intersects(r)) {
                return true;
            }
        }
        return false;
    }

    protected Point2D updateNextPosBecauseOfBarriers(Point2D nextPos) {
        //See if the agent's current position is in a wall, if so we want to impede his movement.
        double penalty = calculateMaxBarrierAtPosition(currentAgentRect);

        double currentX = theState.getAgentPosition().getX();
        double currentY = theState.getAgentPosition().getY();

        double nextX = nextPos.getX();
        double nextY = nextPos.getY();

        double newNextX = currentX + ((nextX - currentX) * (1.0f - penalty));
        double newNextY = currentY + ((nextY - currentY) * (1.0f - penalty));

        nextPos.setLocation(newNextX, newNextY);
        //Now, find out if he's in an immobile obstacle... and if so move him out
        float fudgeCounter = 0;

        Rectangle2D nextPosRect = makeAgentSizedRectFromPosition(nextPos);
        while (calculateMaxBarrierAtPosition(nextPosRect) == 1.0f) {
            nextPos = findMidPoint(nextPos, theState.getAgentPosition());
            fudgeCounter++;
            if (fudgeCounter == 4) {
                nextPos = (Point2D) theState.getAgentPosition().clone();
                break;
            }
        }
        return nextPos;
    }

    protected Point2D updateNextAttackerPosBecauseOfBarriers(Point2D nextPos, double wallStrength) {
        //See if the agent's current position is in a wall, if so we want to impede his movement.
        double penalty = calculateMaxBarrierAtPosition(currentAttackerRect) * wallStrength;
        //System.out.print("Start nextpos: "+nextPos.getX()+","+nextPos.getY());
        //System.out.print(" Current Penalty: "+penalty);
        double currentX = theState.getAttackerPosition().getX();
        double currentY = theState.getAttackerPosition().getY();

        double nextX = nextPos.getX();
        double nextY = nextPos.getY();

        double newNextX = currentX + ((nextX - currentX) * (1.0f - penalty));
        double newNextY = currentY + ((nextY - currentY) * (1.0f - penalty));

        nextPos.setLocation(newNextX, newNextY);
        //Now, find out if he's in an immobile obstacle... and if so move him out
        float fudgeCounter = 0;

        Rectangle2D nextPosRect = makeAttackerSizedRectFromPosition(nextPos);
        while (calculateMaxBarrierAtPosition(nextPosRect) * wallStrength == 1.0f) {
            //System.out.print(" next pos is wall, halfing ");
            nextPos = findMidPoint(nextPos, theState.getAttackerPosition());
            fudgeCounter++;
            if (fudgeCounter == 4) {
                nextPos = (Point2D) theState.getAttackerPosition().clone();
                break;
            }
        }
        //System.out.println(" Ended at: "+nextPos.getX()+","+nextPos.getY());
        return nextPos;
    }

    protected Point2D findMidPoint(Point2D a, Point2D b) {
        double newX = (a.getX() + b.getX()) / 2.0d;
        double newY = (a.getY() + b.getY()) / 2.0d;
        return new Point2D.Double(newX, newY);
    }

    protected Point2D updateNextPosBecauseOfWorldBoundary(Point2D nextPos) {
        //Gotta do this somewhere
        int fudgeCounter = 0;
        Rectangle2D nextPosRect = makeAgentSizedRectFromPosition(nextPos);
        while (!getWorldRect().contains(nextPosRect)) {
            nextPos = findMidPoint(nextPos, theState.getAgentPosition());

            fudgeCounter++;
            if (fudgeCounter == 4) {
                nextPos = theState.getAgentPosition();
                break;
            }
        }
        return nextPos;
    }

    protected Point2D updateAttackerPosBecauseOfWorldBoundary(Point2D attackerPos) {
        //Gotta do this somewhere
        int fudgeCounter = 0;
        Rectangle2D nextAttackerRect = makeAttackerSizedRectFromPosition(attackerPos);
        while (!getWorldRect().contains(nextAttackerRect)) {
            attackerPos = findMidPoint(attackerPos, theState.getAttackerPosition());

            fudgeCounter++;
            if (fudgeCounter == 4) {
                attackerPos = theState.getAttackerPosition();
                break;
            }
        }
        return attackerPos;
    }

    public double getMaxValueForQuerableVariable(int dimension) {
        if (dimension == 0) {
            return getWorldRect().getMaxX();
        }
        return getWorldRect().getMaxY();
    }

    public double getMinValueForQuerableVariable(int dimension) {
        if (dimension == 0) {
            return getWorldRect().getMinX();
        }
        return getWorldRect().getMinY();
    }

    public int getNumVars() {
        return 4;
    }

    public Observation getObservationForState(Observation theState) {
        return theState;
    }

    public String getVisualizerClassName() {
        return "SamplePolyathlon.ProblemTypeF.Visualizer.AbstractPolyProblemTypeFVisualizer";
    }

    private Point2D getKingMoveAttackerPosition() {
        double FullDx = theState.getAgentPosition().getX() - theState.getAttackerPosition().getX();
        double FullDy = theState.getAgentPosition().getY() - theState.getAttackerPosition().getY();
        double dx = 0;
        double dy = 0;
        double moveDivider = 1.5;

        if (FullDx > 0) {
            if (FullDx > attackerSpeed / moveDivider) {
                dx = attackerSpeed / moveDivider;
            } else {
                dx = FullDx;
            }
        } else {

            if (Math.abs(FullDx) > attackerSpeed / moveDivider) {
                dx = -attackerSpeed / moveDivider;
            } else {
                dx = FullDx;
            }
        }

        if (FullDy > 0) {
            if (FullDy > attackerSpeed / moveDivider) {
                dy = attackerSpeed / moveDivider;
            } else {
                dy = FullDy;
            }
        } else {

            if (Math.abs(FullDy) > attackerSpeed / moveDivider) {
                dy = -attackerSpeed / moveDivider;
            } else {
                dy = FullDy;
            }
        }
        return new Point2D.Double(dx + theState.getAttackerPosition().getX(), dy + theState.getAttackerPosition().getY());

    }

    private Point2D randomAttackerAction() {
        int flip = (int) (Math.random() * 4.0);
        double dx = 0, dy = 0;

        switch (flip) {

            case 0:
                dx = attackerSpeed;
                break;

            case 1:
                dx = -attackerSpeed;
                break;

            case 2:
                dy = attackerSpeed;
                break;

            default:
                dy = -attackerSpeed;

        }

        double noiseX = .125d * (Math.random() - 0.5d);
        double noiseY = .125d * (Math.random() - 0.5d);

        dx += noiseX;
        dy += noiseY;

        return new Point2D.Double(dx + theState.getAttackerPosition().getX(), dy + theState.getAttackerPosition().getY());
    }

    private boolean moveAttacker() {

        boolean agentCaught = false;
        double dx = 0.0;
        double dy = 0.0;
        Point2D nextAttackerPos = null;
        Point2D[] points;
        Point2D agent;
        double[] ret;
        double minD = 0;
        int minIn = 0;
        double epsilon = 0.1;

        if (attackerPolicy == 0 || (Math.random() < epsilon && attackerPolicy != 3)) {

            nextAttackerPos = randomAttackerAction();
            nextAttackerPos = updateAttackerPosBecauseOfWorldBoundary(nextAttackerPos);
            nextAttackerPos = updateNextAttackerPosBecauseOfBarriers(nextAttackerPos, 1.0);
        } else {

            switch (attackerPolicy) {
                //Pick grid action that takes us closest to agent ignoring obstacles
                case 1:

                    points = new Point2D[4];
                    points[0] = new Point2D.Double(theState.getAttackerPosition().getX() + attackerSpeed, theState.getAttackerPosition().getY());
                    points[1] = new Point2D.Double(theState.getAttackerPosition().getX() - attackerSpeed, theState.getAttackerPosition().getY());
                    points[2] = new Point2D.Double(theState.getAttackerPosition().getX(), theState.getAttackerPosition().getY() + attackerSpeed);
                    points[3] = new Point2D.Double(theState.getAttackerPosition().getX(), theState.getAttackerPosition().getY() - attackerSpeed);


                    agent = new Point2D.Double(theState.getAgentPosition().getX(), theState.getAgentPosition().getY());

                    minD = agent.distance(points[0]);
                    minIn = 0;

                    for (int i = 0; i < 4; i++) {
                        double dist = agent.distance(points[i]);
                        if (dist < minD) {
                            minD = dist;
                            minIn = i;
                        }
                    }
                    nextAttackerPos = points[minIn];
                    nextAttackerPos = updateAttackerPosBecauseOfWorldBoundary(nextAttackerPos);

                    Rectangle2D nextPosRect = makeAttackerSizedRectFromPosition(nextAttackerPos);

                    if (calculateMaxBarrierAtPosition(nextPosRect) > 0.0) {
                        nextAttackerPos = randomAttackerAction();
                    }


                    nextAttackerPos = updateAttackerPosBecauseOfWorldBoundary(nextAttackerPos);
                    nextAttackerPos = updateNextAttackerPosBecauseOfBarriers(nextAttackerPos, 1.0);

                    break;

                //Like case 1, but penalize moves that put you into a barrier
                case 2:

                    points = new Point2D[4];
                    points[0] = new Point2D.Double(theState.getAttackerPosition().getX() + attackerSpeed, theState.getAttackerPosition().getY());
                    points[1] = new Point2D.Double(theState.getAttackerPosition().getX() - attackerSpeed, theState.getAttackerPosition().getY());
                    points[2] = new Point2D.Double(theState.getAttackerPosition().getX(), theState.getAttackerPosition().getY() + attackerSpeed);
                    points[3] = new Point2D.Double(theState.getAttackerPosition().getX(), theState.getAttackerPosition().getY() - attackerSpeed);


                    agent = new Point2D.Double(theState.getAgentPosition().getX(), theState.getAgentPosition().getY());

                    minD = agent.distance(points[0]);
                    for (int j = 0; j < barrierRegions.size(); j++) {
                        if (barrierRegions.get(j).contains(points[0])) {
                            minD += 100;
                        }
                    }

                    minIn = 0;

                    for (int i = 0; i < 4; i++) {

                        double dist = agent.distance(points[i]);
                        for (int j = 0; j < barrierRegions.size(); j++) {
                            if (barrierRegions.get(j).contains(points[i])) {
                                dist += 100;
                            }
                        }

                        if (dist < minD) {

                            minD = dist;
                            minIn = i;
                        }
                    }
                    nextAttackerPos = points[minIn];

                    nextAttackerPos = updateAttackerPosBecauseOfWorldBoundary(nextAttackerPos);
                    nextAttackerPos = updateNextAttackerPosBecauseOfBarriers(nextAttackerPos, 1.0);

                    break;

                //Ghost -- can move through walls and move at any diagnol
                //Moves slow him down, and actually jumps on the agent if close
                case 3:

                    nextAttackerPos = getKingMoveAttackerPosition();

                    nextAttackerPos = updateAttackerPosBecauseOfWorldBoundary(nextAttackerPos);
                    nextAttackerPos = updateNextAttackerPosBecauseOfBarriers(nextAttackerPos, 0.60);
            }
        }





        theState.setAttackerPositionFromPoint(nextAttackerPos);
        updateCurrentAttackerRect();


        if (currentAgentRect.intersects(currentAttackerRect) || currentAttackerRect.intersects(currentAgentRect)) {
            agentCaught = true;
        }

        return agentCaught;


    }

    public URL getImageURL() {
        URL imageURL = TrainingPolyathlon.class.getResource("/images/poly.png");
        return imageURL;
    }
}
