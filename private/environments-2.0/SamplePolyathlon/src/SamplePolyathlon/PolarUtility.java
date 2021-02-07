package SamplePolyathlon;


import java.awt.geom.Point2D;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Brian Tanner
 */
public class PolarUtility {
final double maxRadius = Math.sqrt(2.0)/2;
final double maxAngle = 2*Math.PI;
final double newOriginX = 0.5;
final double newOriginY = 0.5;
 
public double polarRadius(double x, double y){
	return Math.sqrt((x*x) + (y*y));
}
 
public double polarAngle(double x, double y){
	if(x>0.0 && y >= 0){
		return Math.atan(y/x);
	} else if(x>0 && y < 0.0){
		return Math.atan(y/x) + 2*Math.PI;
	} else if(x < 0.0){
		return Math.atan(y/x) + Math.PI;
	} else if(x == 0.0 && y > 0.0){
		return Math.PI/2;
	} else if(x == 0.0 && y < 0.0){
		return 3*Math.PI/2;
	}else{
		return 0.0;
	}
}
 
public double normalize(double num, double min, double max){
	return (num - min) / (max - min);
}
 
public Point2D.Double cartesianToPolar(double x, double y){
	double newX = x - newOriginX;
	double newY = y - newOriginY;
    double r = normalize(polarRadius(newX,newY), 0.0, maxRadius);
    double theta = normalize(polarAngle(newX,newY), 0.0, maxAngle);
 
    return new Point2D.Double(r,theta);
}
}
