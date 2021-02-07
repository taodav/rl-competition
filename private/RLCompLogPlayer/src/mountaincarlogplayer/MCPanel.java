/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mountaincarlogplayer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import rlcomplogplayer.AbstractVizPanel;

/**
 *
 * @author mradkie
 */
public class MCPanel extends AbstractVizPanel {

    private MCLogPlayer theLogPlayer = null;
    private double currentPos = 0.0d;
    private double currentVel = 0.0d;
    private double minPosition = -1.2;
    private double maxPosition = 0.6;
    private int xSize = 800;
    private int ySize = 600;
    private int hillSmoothness = 30;

    private String teamName = null;
    

    public String getTeamName(){
        if(this.teamName == null)
            this.teamName = theLogPlayer.getTeamName();
        
        return this.teamName;
    }
    public double getReward(){
        return theLogPlayer.getReward();
    }
    public MCPanel(String filename) {
        super();

        theLogPlayer = new MCLogPlayer(filename);
    }

    public int getDesiredX() {
        return xSize;
    }

    public int getDesiredY() {
        return ySize;
    }

    @Override
    protected void cleanup() throws Throwable {
        theLogPlayer.cleanup();
    }
    
    public boolean update(){
        return theLogPlayer.updateStep();
        //return true;
    }

    private void letsTry(){
        theLogPlayer.updateStep();
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
        //Just make things fast for now

        this.currentPos = theLogPlayer.getPos();
        this.currentVel = theLogPlayer.getVel();

        g.clearRect(0, 0, 1000, 1000);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, xSize, ySize);
        
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(12));
        //draw the hill
        double j = minPosition;
        for(double i=minPosition; i<maxPosition; i+=((Math.abs(minPosition)+Math.abs(maxPosition))/hillSmoothness)){
            if(!(i == minPosition)){
               
                g2.drawLine((int)normalizeX(j), (int)normalizeY(j),(int)normalizeX(i), (int)normalizeY(i));
            }
            j = i;
        }
        
        g.setColor(Color.RED);
        //to bring things back into the window
        double transX = normalizeX(this.currentPos);

        //need to get he actual height ranges sin has range -1,1
        double transY = normalizeY(this.currentPos);


        int rectWidth = 30;
        int rectHeight = 30;

        
        g.fillRect((int) (transX - rectWidth / 2), (int) (transY - rectHeight / 2), rectWidth, rectHeight);
    }
    private double getHeight(double pos){
        return -Math.sin(3.0 * (pos));
    }
    private double normalizeX(double value){
        return xSize * normalizeValue(value, minPosition, maxPosition);
    }
    private double normalizeY(double value){
        return ySize* normalizeValue(getHeight(value), -1, 1);
    }
    private static double normalizeValue(double theValue, double minPossible,
            double maxPossible) {
        return (theValue - minPossible) / (maxPossible - minPossible);
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

