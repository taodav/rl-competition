/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrislogplayer;

import java.awt.Color;
import java.awt.Graphics;
import rlcomplogplayer.*;

/**
 *
 * @author mradkie
 */
public class TetrisPanel extends AbstractVizPanel {

    private int stepnum = 0;
    public int[][] currentState = null;
    public int stateWidth = 0;
    public int stateHeight = 0;
    private int mdpNum = 0;
    TetrisLogPlayer theLogPlayer = null;
    //Desired abstract block size
    int DABS = 25;
    private String teamName = null;
    
    public double getReward(){
        return theLogPlayer.getReward();
    }
    public String getTeamName(){
        if(this.teamName == null)
            this.teamName = theLogPlayer.getTeamName();
        
        return this.teamName;
    }
    
    public TetrisPanel(String filename) {
        super();

        this.setBackground(Color.blue);
        theLogPlayer = new TetrisLogPlayer(filename);
        stateWidth = theLogPlayer.getBoardWidth();
        stateHeight = theLogPlayer.getBoardHeight();
    }

    public int getDesiredX() {
        return DABS * stateWidth;
    }

    public int getDesiredY() {
        return DABS * stateHeight;
    }
    
    @Override
    protected void cleanup() throws Throwable{
        theLogPlayer.cleanup();
    }

    public boolean update(){
        this.currentState = theLogPlayer.updateStep();
        return !theLogPlayer.endOfFile();
    }
    @Override
    public void paint(Graphics g) {

        if (currentState == null) {
            return;
        }
        g.clearRect(0, 0, 1000, 1000);

        int numCols = stateHeight;
        int numRows = stateWidth;

        int w = DABS;
        int h = DABS;
        int x = 0;
        int y = 0;
        
        g.setColor(Color.GRAY);

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                y = j * DABS;
                x = i * DABS;
                int thisBlockColor = currentState[i][j];
                if (thisBlockColor != 0) {
                    switch (thisBlockColor) {
                        case 1:
                            g.setColor(Color.PINK);
                            break;
                        case 2:
                            g.setColor(Color.RED);
                            break;
                        case 3:
                            g.setColor(Color.GREEN);
                            break;
                        case 4:
                            g.setColor(Color.YELLOW);
                            break;
                        case 5:
                            g.setColor(Color.LIGHT_GRAY);
                            break;
                        case 6:
                            g.setColor(Color.ORANGE);
                            break;
                        case 7:
                            g.setColor(Color.MAGENTA);
                            break;

                    }
                    g.fill3DRect(x, y, w, h, true);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(x, y, w, h);
                }
            }
        }
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, DABS * numRows, DABS * numCols);

    }

    public void updateState(int[][] newState) {
        this.currentState = newState;
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
