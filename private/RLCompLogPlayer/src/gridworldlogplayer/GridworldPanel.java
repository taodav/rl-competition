/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gridworldlogplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;
import rlcomplogplayer.AbstractVizPanel;

/**
 *
 * @author mradkie
 */
public class GridworldPanel extends AbstractVizPanel {

    private GridworldLogPlayer theLogPlayer = null;
    double[] state;
    private String teamName = null;
    static final int xSize = 600;
    static final int ySize = 600;
    private double xScalar = 1;
    private double yScalar = 1;
    protected Rectangle2D worldRect;
    protected Vector<Rectangle2D> resetRegions = new Vector<Rectangle2D>();
    protected Vector<Rectangle2D> barrierRegions = new Vector<Rectangle2D>();
    protected Vector<Rectangle2D> rewardRegions = new Vector<Rectangle2D>();
    protected Vector<Double> thePenalties = new Vector<Double>();

    public GridworldPanel(String filename) {
        super();

        theLogPlayer = new GridworldLogPlayer(filename);
        state = new double[2];//state[0] = theta1; state[1] = theta2;
        worldRect = new Rectangle2D.Double(0.0d,0.0d,200.0d,200.0d);
        setScalar();
        setupWorld(theLogPlayer.mdpNum);
    }
    public double getReward(){
        return theLogPlayer.getReward();
    }
    private void setScalar(){
        xScalar = xSize / worldRect.getWidth();
        yScalar = ySize / worldRect.getHeight();  
    }
    
    private Rectangle2D.Double scaleRect(Rectangle2D.Double unScaled){
        return new Rectangle2D.Double(unScaled.x * xScalar, unScaled.y * yScalar, unScaled.width * xScalar, unScaled.height * yScalar);
    }
    public void setupWorld(int mdp){
        switch(mdp){
            case 0:           
                resetRegions.add(scaleRect(new Rectangle2D.Double(75.0d,125.0d,50.0d,50.0d)));

                barrierRegions.add(scaleRect(new Rectangle2D.Double(0.0d,50.0d,100.0d,30.0d)));
                barrierRegions.add(scaleRect(new Rectangle2D.Double(100.0d,50.0d,30.0d,30.0d)));
                barrierRegions.add(scaleRect(new Rectangle2D.Double(130.0d,50.0d,50.0d,30.0d)));
                thePenalties.add(1.0d);
                thePenalties.add(0.25d);
                thePenalties.add(1.0d);
                break;
            case 2:
                resetRegions.add(scaleRect(new Rectangle2D.Double(75.0d,75.0d,25.0d,25.0d)));

                barrierRegions.add(scaleRect(new Rectangle2D.Double(50.0d,50.0d,10.0d,100.0d)));
                barrierRegions.add(scaleRect(new Rectangle2D.Double(50.0d,50.0d,100.0d,10.0d)));
                barrierRegions.add(scaleRect(new Rectangle2D.Double(150.0d,50.0d,10.0d,100.0d)));
                thePenalties.add(1.0d);
                thePenalties.add(1.0d);
                thePenalties.add(1.0d);
                break;
            case 51:
                resetRegions.add(scaleRect(new Rectangle2D.Double(75.0d,125.0d,50.0d,50.0d)));

                barrierRegions.add(scaleRect(new Rectangle2D.Double(0.0d,50.0d,100.0d,30.0d)));
                barrierRegions.add(scaleRect(new Rectangle2D.Double(100.0d,50.0d,30.0d,30.0d)));
                barrierRegions.add(scaleRect(new Rectangle2D.Double(130.0d,50.0d,50.0d,30.0d)));
                thePenalties.add(1.0d);
                thePenalties.add(0.25d);
                thePenalties.add(1.0d);
                break;
            case 52:
                resetRegions.add(scaleRect(new Rectangle2D.Double(75.0d,75.0d,25.0d,25.0d)));

                barrierRegions.add(scaleRect(new Rectangle2D.Double(50.0d,50.0d,10.0d,100.0d)));
                barrierRegions.add(scaleRect(new Rectangle2D.Double(50.0d,50.0d,100.0d,10.0d)));
                barrierRegions.add(scaleRect(new Rectangle2D.Double(150.0d,50.0d,10.0d,100.0d)));
                thePenalties.add(1.0d);
                thePenalties.add(1.0d);
                thePenalties.add(1.0d);
                break;
            case 59:
                resetRegions.add(scaleRect(new Rectangle2D.Double(75.0d,75.0d,25.0d,25.0d)));

                barrierRegions.add(scaleRect(new Rectangle2D.Double(50.0d,50.0d,10.0d,100.0d)));
                barrierRegions.add(scaleRect(new Rectangle2D.Double(50.0d,50.0d,100.0d,10.0d)));
                barrierRegions.add(scaleRect(new Rectangle2D.Double(150.0d,50.0d,10.0d,100.0d)));
                thePenalties.add(1.0d);
                thePenalties.add(1.0d);
                thePenalties.add(1.0d);
                break;
            case 64:
                resetRegions.add(scaleRect(new Rectangle2D.Double(75.0d,75.0d,25.0d,25.0d)));

                break;                
            default:
                resetRegions.add(scaleRect(new Rectangle2D.Double(75.0d,125.0d,50.0d,50.0d)));

                barrierRegions.add(scaleRect(new Rectangle2D.Double(0.0d,50.0d,100.0d,30.0d)));
                barrierRegions.add(scaleRect(new Rectangle2D.Double(100.0d,50.0d,30.0d,30.0d)));
                barrierRegions.add(scaleRect(new Rectangle2D.Double(130.0d,50.0d,50.0d,30.0d)));
                thePenalties.add(1.0d);
                thePenalties.add(0.25d);
                thePenalties.add(1.0d);
                break;                
        }
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
        state[0] = theLogPlayer.getPos() * xScalar;
        state[1] = theLogPlayer.getVel() * yScalar;
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
        g.clearRect(0, 0, 1000, 1000);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, xSize, ySize);
        
        Graphics2D g2 = (Graphics2D)g;
        for (Rectangle2D thisRect : resetRegions) {
            g2.setColor(Color.blue);
            g2.fill(thisRect);
        }

        for (int i = 0; i < barrierRegions.size(); i++) {
            Rectangle2D thisRect = barrierRegions.get(i);
            double thisPenalty = thePenalties.get(i);

            Color theColor = new Color((float) thisPenalty, 0, 0);

            g2.setColor(theColor);
            g2.fill(thisRect);
        }
        
        g2.setColor(Color.orange);

        g.fillRect((int)state[0]-1, (int)state[1]-1, 10*(int)xScalar, 10*(int)yScalar);
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
