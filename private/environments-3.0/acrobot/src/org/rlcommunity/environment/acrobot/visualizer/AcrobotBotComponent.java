package org.rlcommunity.environment.acrobot.visualizer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import java.awt.geom.Ellipse2D;
import java.util.Observable;
import java.util.Observer;
import org.rlcommunity.environment.acrobot.messages.AcrobotStateRequest;
import org.rlcommunity.environment.acrobot.messages.AcrobotStateResponse;
import rlVizLib.visualization.SelfUpdatingVizComponent;
import rlVizLib.visualization.VizComponentChangeListener;

public class AcrobotBotComponent implements SelfUpdatingVizComponent, Observer {

    private AcrobotVisualizer acroVis;
    static final int joint1X = 50;
    static final int joint1Y = 50;
    static final int leg1length = 25/2;
    static final int leg2length = leg1length;
    static final int circleSize1 = 2;
    static final int circleSize2 = 2;
    static final int circleSize3 = 2;
    int lastUpdateStep = -1;

    public AcrobotBotComponent(AcrobotVisualizer acrobotVisualizer) {
        acroVis = acrobotVisualizer;
        acrobotVisualizer.getTheGlueState().addObserver(this);
    }

    public void render(Graphics2D g) {

        AcrobotStateResponse stateMessageResponse=AcrobotStateRequest.Execute();
        double theta1=stateMessageResponse.getTheta1();
        double theta2=stateMessageResponse.getTheta2();

        AffineTransform saveAT = g.getTransform();
        g.setColor(Color.WHITE);
        g.fill(new Rectangle(1, 1));
        g.scale(.01, .01);

        g.setColor(Color.green);
        int goalY = joint1Y - leg1length;
        g.drawLine(0, goalY, 100, goalY);
        g.setColor(Color.BLACK);

        int joint2X = joint1X - (int) (leg1length * Math.sin(theta1));
        int joint2Y = joint1Y - (int) (leg1length * -Math.cos(theta1));
        g.drawLine(joint1X, joint1Y, joint2X, joint2Y);
//Draw the first joint circle
        g.setColor(Color.BLUE);
        g.fill(new Ellipse2D.Float((float) joint1X - circleSize1 / 2, (float) joint1Y - circleSize1 / 2, circleSize1, circleSize1));

        //int joint3X = (int) (leg2length * Math.cos(Math.PI / 2 - acroVis.getTheta2() - acroVis.getTheta1()) + joint2X);
        //int joint3Y = (int) (leg2length * Math.sin(Math.PI / 2 - acroVis.getTheta1() - acroVis.getTheta2()) + joint2Y);
        int joint3X =  joint2X - (int)(leg2length * Math.sin(theta2));
        int joint3Y =  joint2Y - (int)(leg2length * -Math.cos(theta2));
        
        g.setColor(Color.BLACK);
        g.drawLine(joint2X, joint2Y, joint3X, joint3Y);
//Second circle
        g.setColor(Color.BLUE);
        g.fill(new Ellipse2D.Float((float) joint2X - circleSize2 / 2, (float) joint2Y - circleSize2 / 2, circleSize2, circleSize2));

        //                System.out.printf("(%d %d) --> (%d %d)\n",joint2X,joint2Y,joint3X,joint3Y);

//Feet
        g.setColor(Color.CYAN);
        g.fill(new Ellipse2D.Float((float) joint3X - circleSize3 / 2, (float) joint3Y - circleSize3 / 2, circleSize3, circleSize3));
        g.setTransform(saveAT);
    }
    /**
     * This is the object (a renderObject) that should be told when this component needs to be drawn again.
     */
    private VizComponentChangeListener theChangeListener;

    public void setVizComponentChangeListener(VizComponentChangeListener theChangeListener) {
        this.theChangeListener = theChangeListener;
    }

    /**
     * This will be called when TinyGlue steps.
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        if (theChangeListener != null) {
            /* This will call the message code too often but so what */
            theChangeListener.vizComponentChanged(this);
        }
    }
}
