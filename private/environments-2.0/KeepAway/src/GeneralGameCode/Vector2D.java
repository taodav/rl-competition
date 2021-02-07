/*
 * Vector2D.java
 * 
 * Created on Oct 16, 2007, 9:54:07 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GeneralGameCode;

/**
 *
 * @author btanner
 */
public class Vector2D {
double x=0.0d;
double y=0.0d;

public Vector2D(double x, double y){
    this.x=x;
    this.y=y;
}

public Vector2D(){
    this(0.0d,0.0d);
}

public Vector2D Perp(){
    return( new Vector2D( -1.0 *y, x ) );
}
}
