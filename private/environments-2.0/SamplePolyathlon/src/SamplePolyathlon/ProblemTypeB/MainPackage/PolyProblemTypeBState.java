/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SamplePolyathlon.ProblemTypeB.MainPackage;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author btanner
 */
public class PolyProblemTypeBState{
        NumberFormat formatter = new DecimalFormat ( "000.00" ) ;
    
        Point2D.Double position=new Point2D.Double(0,0);
        


        public PolyProblemTypeBState(){}

        public PolyProblemTypeBState(PolyProblemTypeBState stateToCopy){
            this.position=new Point2D.Double();
            this.position.x=stateToCopy.position.x;
            this.position.y=stateToCopy.position.y;
        }

    Point2D getPosition() {
       return this.position;
    }

    void setPositionFromPoint(Point2D nextPos) {
        this.position.x=nextPos.getX();
        this.position.y=nextPos.getY();
    }

        

    String stringSerialize() {
       StringBuffer b=new StringBuffer();
       //There are 2 things we're putting in
       b.append("2");
        //It's a double
       b.append("_d_");
       
       b.append(formatter.format(position.x));
       b.append("_d_");
       b.append(formatter.format(position.y));
       b.append("_");

       return b.toString();
    }

}
