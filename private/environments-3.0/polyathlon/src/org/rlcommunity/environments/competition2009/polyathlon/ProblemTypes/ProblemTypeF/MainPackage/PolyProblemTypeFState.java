/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.environments.competition2009.polyathlon.ProblemTypes.ProblemTypeF.MainPackage;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author btanner
 */
public class PolyProblemTypeFState{
        NumberFormat formatter = new DecimalFormat ( "000.00" ) ;
    
        Point2D.Double Agentposition=new Point2D.Double(0.1,100);
        Point2D.Double Attackerposition=new Point2D.Double(180,80);        


        public PolyProblemTypeFState(){}

        public PolyProblemTypeFState(PolyProblemTypeFState stateToCopy){
            this.Agentposition=new Point2D.Double();
            this.Agentposition.x=stateToCopy.Agentposition.x;
            this.Agentposition.y=stateToCopy.Agentposition.y;
            
            this.Attackerposition=new Point2D.Double();
            this.Attackerposition.x=stateToCopy.Attackerposition.x;
            this.Attackerposition.y=stateToCopy.Attackerposition.y;
            
        }

    Point2D getAgentPosition() {
       return this.Agentposition;
    }
    Point2D getAttackerPosition() {
       return this.Attackerposition;
    }
    
    void setAgentPositionFromPoint(Point2D nextPos) {
        this.Agentposition.x=nextPos.getX();
        this.Agentposition.y=nextPos.getY();
    }

    void setAttackerPositionFromPoint(Point2D nextPos) {
        this.Attackerposition.x=nextPos.getX();
        this.Attackerposition.y=nextPos.getY();
    }    
        

    String stringSerialize() {
       StringBuffer b=new StringBuffer();
       //There are 4 things we're putting in
       b.append("4");
        //It's a double
       b.append("_d_");
       
       b.append(formatter.format(Agentposition.x));
       b.append("_d_");
       b.append(formatter.format(Agentposition.y));
       b.append("_");

       b.append(formatter.format(Attackerposition.x));
       b.append("_d_");
       b.append(formatter.format(Attackerposition.y));
       b.append("_");       
       return b.toString();
    }

}
