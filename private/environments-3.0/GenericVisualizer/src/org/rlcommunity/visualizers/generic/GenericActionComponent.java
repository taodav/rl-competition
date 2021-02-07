/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rlcommunity.visualizers.generic;

import java.util.Observable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import java.util.Observer;
import rlVizLib.general.TinyGlue;
import org.rlcommunity.rlglue.codec.types.Action;
import rlVizLib.visualization.SelfUpdatingVizComponent;
import rlVizLib.visualization.VizComponentChangeListener;

/**
 *
 * @author btanner
 */
class GenericActionComponent implements SelfUpdatingVizComponent, Observer {

    TinyGlue theGlueState = null;

    public GenericActionComponent(TinyGlue theGlueState) {
        this.theGlueState = theGlueState;
        theGlueState.addObserver(this);
    }

    public void render(Graphics2D g) {
        //This is some hacky stuff, someone better than me should clean it up
        Font f = new Font("Verdana", 0, 8);
        g.setFont(f);
        //SET COLOR
        g.setColor(Color.BLACK);
        //DRAW STRING
        AffineTransform saveAT = g.getTransform();
        g.scale(.005, .005);

        float currentHeight = 10.0f;
        float heightIncrement = 10.0f;

        currentHeight += heightIncrement;

        //Do action int variables	    
        StringBuffer actStringBuffer = new StringBuffer("Action Ints: ");
        Action lastAction = theGlueState.getLastAction();
        if (lastAction != null) {
            for (int i = 0; i < lastAction.intArray.length; i++) {
                actStringBuffer.append(lastAction.intArray[i]);
                actStringBuffer.append('\t');
            }
            g.drawString(actStringBuffer.toString(), 0.0f, currentHeight);
            currentHeight += heightIncrement;

            //Do double variables
            g.drawString("Action Doubles", 0.0f, currentHeight);
            currentHeight += heightIncrement;
            for (int i = 0; i < lastAction.doubleArray.length; i++) {
                g.drawString("" + lastAction.doubleArray[i], 0.0f, currentHeight);
                currentHeight += heightIncrement;
            }
        }
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
            theChangeListener.vizComponentChanged(this);
        }
    }
}