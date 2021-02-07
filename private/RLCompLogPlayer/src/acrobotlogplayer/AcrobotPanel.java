/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acrobotlogplayer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import rlcomplogplayer.AbstractVizPanel;

/**
 *
 * @author mradkie
 */
public class AcrobotPanel extends AbstractVizPanel {

    static final int xSize = 600;
    static final int ySize = 600;
    static final int joint1X = xSize / 2;
    static final int joint1Y = 200;
    static final int leg1length = xSize / 4;
    static final int leg2length = xSize / 4;
    static final int circleSize = 50;
    private AcrobotLogPlayer theLogPlayer = null;
    double[] state;
    private String teamName = null;

    public AcrobotPanel(String filename) {
        super();

        theLogPlayer = new AcrobotLogPlayer(filename);
        state = new double[2];//state[0] = theta1; state[1] = theta2;
    }
    public double getReward(){
        return theLogPlayer.getReward();
    }
    @Override
    public int getDesiredX() {
        return this.xSize;
    }

    @Override
    public int getDesiredY() {
        return this.ySize;
    }

    @Override
    public boolean update() {
        boolean thereturn = theLogPlayer.updateStep();
        return thereturn;
    }

    @Override
    public String getTeamName() {
        if (teamName == null) {
            this.teamName = theLogPlayer.getTeamName();
        }
        return teamName;
    }

    @Override
    protected void cleanup() throws Throwable {
        theLogPlayer.cleanup();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        this.state[0] = theLogPlayer.getPos();
        this.state[1] = theLogPlayer.getVel();

        g.clearRect(0, 0, 1000, 1000);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, xSize, ySize);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(8));

        int joint2X = (int) (leg1length * Math.sin(state[0]) + joint1X);
        int joint2Y = (int) (leg1length * Math.cos(state[0]) + joint1Y);
        g2.drawLine(joint1X, joint1Y, joint2X, joint2Y);
        int joint3X = (int) (leg2length * Math.cos(Math.PI / 2 - state[1] - state[0]) + joint2X);
        int joint3Y = (int) (leg2length * Math.sin(Math.PI / 2 - state[0] - state[1]) + joint2Y);
        g2.drawLine(joint2X, joint2Y, joint3X, joint3Y);
    }

    @Override
    public int getMDPNum() {
        return theLogPlayer.mdpNum;
    }

    @Override
    public int getCurSteps() {
        return theLogPlayer.getCurSteps();
    }
    @Override
    public int getMaxSteps() {
        return theLogPlayer.getMaxSteps();
    }
}
