/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helicopterlogplayer;

import java.awt.Color;
import java.awt.Graphics;
import rlcomplogplayer.AbstractVizPanel;

/**
 *
 * @author mradkie
 */
public class HelicopterPanel extends AbstractVizPanel {

    private int xSize = 30;
    private int ySize = 600;
    
    private int offset = 15;
    private int xgap = 6;
    private HelicopterLogPlayer theLogPlayer = null;
    double[] state;
    private String teamName = null;
    
    public double getReward(){
        return theLogPlayer.getReward();
    }
    public String getTeamName(){
        if(this.teamName == null)
            this.teamName = theLogPlayer.getTeamName();
        
        return this.teamName;
    }
    public HelicopterPanel(String filename) {
        super();
        
        theLogPlayer = new HelicopterLogPlayer(filename);
        state = new double[13];
    }
    
    protected void cleanup() throws Throwable{
        theLogPlayer.cleanup();
    }

    public int getDesiredX() {
        return this.xSize*13;
    }

    public int getDesiredY() {
        return this.ySize;
    }
    
    public boolean update(){
        if(theLogPlayer.updateStep()){
            state = theLogPlayer.getObservation();
            return true;
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, 1000, 1000);
        //SET COLOR
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1, 1);

        g.setColor(Color.BLACK);
        //DRAW 12 Lines with blue ball equalizers.

        int bottomOfLines = 5;
        int topOfLines = this.ySize;
        double lineRangeSize = topOfLines - bottomOfLines;
        
        int heightOfLines = topOfLines - bottomOfLines;
 
        double min = 0.0d;
        double max = 0.0d;
        
        for (int i = 0; i < 12; i++) {
            g.setColor(Color.BLACK);
            g.fillRect(i * xSize + xgap + offset, bottomOfLines, 6, heightOfLines);
            
            //g.drawLine(i * xSize + 6, bottomOfLines, i * xSize + 6, topOfLines);
            g.setColor(Color.BLUE);
            min = theLogPlayer.getMinAt(i);
            int transY = (int) (normalizeValue(state[i], theLogPlayer.getMinAt(i), theLogPlayer.getMaxAt(i)) * (lineRangeSize) + (float) bottomOfLines);
            if (transY > topOfLines) {
                transY = topOfLines;
                g.setColor(Color.RED);
            }
            if (transY < bottomOfLines) {
                transY = bottomOfLines;
                g.setColor(Color.RED);
            }
            g.fillRect(i * xSize + (xgap - 3) + offset, transY, 12, 10);
        }
    }

    public static double normalizeValue(double theValue, double minPossible,
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
