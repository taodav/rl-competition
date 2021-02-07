/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SamplePolyathlon.ProblemTypeC.MainPackage;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 *
 * @author btanner
 */
public class PolyProblemTypeCState {
    /*State Variables*/

    NumberFormat formatter = new DecimalFormat("000.00");
    private double theta1,  theta2,  theta1Dot,  theta2Dot;
    /*Other Variables and Constants */
    final static double maxTheta1 = Math.PI;
    final static double maxTheta2 = Math.PI;
    final static double maxTheta1Dot = 4 * Math.PI;
    final static double maxTheta2Dot = 9 * Math.PI;
    final static double m1 = 1.0;
    final static double m2 = 1.0;
    final static double l1 = 1.0;
    final static double l2 = 1.0;
    final static double lc1 = 0.5;
    final static double lc2 = 0.5;
    final static double I1 = 1.0;
    final static double I2 = 1.0;
    final static double g = 9.8;
    final static double dt = 0.05;
    final static double acrobotGoalPosition = 1.0;

    public PolyProblemTypeCState() {
    }

    public PolyProblemTypeCState(PolyProblemTypeCState stateToCopy) {
        this.theta1 = stateToCopy.getTheta1();
        this.theta2 = stateToCopy.getTheta2();
        this.theta1Dot = stateToCopy.getTheta1Dot();
        this.theta2Dot = stateToCopy.getTheta2Dot();
    }

    String stringSerialize() {
        StringBuffer b = new StringBuffer();
        //There are 2 things we're putting in
        b.append("4");
        //It's a double
        b.append("_d_");
        b.append(formatter.format(getTheta1()));
        b.append("_d_");
        b.append(formatter.format(getTheta2()));
        b.append("_d_");
        b.append(formatter.format(getTheta1Dot()));
        b.append("_d_");
        b.append(formatter.format(getTheta2Dot()));
        b.append("_");
        return b.toString();
    }

    public void applyTorque(double theTorque) {
        int count = 0;
        while (!inGoalRegion() && count < 4) {
            count++;

            double d1 = m1 * Math.pow(lc1, 2) + m2 * (Math.pow(l1, 2) + Math.pow(lc2, 2) + 2 * l1 * lc2 * Math.cos(theta2)) + I1 + I2;
            double d2 = m2 * (Math.pow(lc2, 2) + l1 * lc2 * Math.cos(theta2)) + I2;
            double phi_2 = m2 * lc2 * g * Math.cos(theta1 + theta2 - Math.PI / 2);
            double phi_1 = -(m2 * l1 * lc2 * Math.pow(theta2, 2) * Math.sin(theta2) + 2 * m2 * l1 * lc2 * theta1Dot * theta2Dot * Math.sin(theta2)) + (m1 * lc1 + m2 * l1) * g * Math.cos(theta1 - Math.PI / 2) + phi_2;

            double theta2_ddot = (theTorque + (d2 / d1) * phi_1 - phi_2) / (m2 * Math.pow(lc2, 2) + I2 - Math.pow(d2, 2) / d1);
            double theta1_ddot = -(d2 * theta2_ddot + phi_1) / d1;

            theta1Dot += theta1_ddot * dt;

            if (theta1Dot > maxTheta1Dot) {
                theta1Dot = maxTheta1Dot;
            } else if (theta1Dot < -(maxTheta1Dot)) {
                theta1Dot = -(maxTheta1Dot);
            }
            theta2Dot += theta2_ddot * dt;

            if (theta2Dot > maxTheta2Dot) {
                theta2Dot = maxTheta2Dot;
            } else if (theta2Dot < -(maxTheta2Dot)) {
                theta2Dot = -(maxTheta2Dot);
            }
            theta1 += theta1Dot * dt;
            theta2 += theta2Dot * dt;
        }

        //Keep thetas in reasonable range to avoid loss of percision.
        while (theta1 > Math.PI) {
            theta1 -= 2.0 * Math.PI;
        }
        while (theta1 < -Math.PI) {
            theta1 += 2.0 * Math.PI;
        }
        while (theta2 > Math.PI) {
            theta2 -= 2.0 * Math.PI;
        }
        while (theta2 < -Math.PI) {
            theta2 += 2.0 * Math.PI;
        }
    }

    public void set_initial_position_random(Random ourRandomNumber) {
        theta1 = (ourRandomNumber.nextDouble() * (Math.PI + Math.abs(-Math.PI)) + (-Math.PI)) * 0.1;
        theta2 = (ourRandomNumber.nextDouble() * (Math.PI + Math.abs(-Math.PI)) + (-Math.PI)) * 0.1;
        theta1Dot = (ourRandomNumber.nextDouble() * (maxTheta1Dot * 2) - maxTheta1Dot) * 0.1;
        theta2Dot = (ourRandomNumber.nextDouble() * (maxTheta2Dot * 2) - maxTheta2Dot) * 0.1;
    }

    public void set_initial_position_at_bottom() {
        theta1 = theta2 = 0.0;
        theta1Dot = theta2Dot = 0.0;
    }

    public boolean inGoalRegion() {
        double feet_height = -(l1 * Math.cos(theta1) + l2 * Math.cos(theta2));
        return (feet_height > acrobotGoalPosition);
    }

    public double getMinTheta1() {
        return -maxTheta1;
    }

    public double getMaxTheta1() {
        return maxTheta1;
    }

    public double getMinTheta2() {
        return -maxTheta2;
    }

    public double getMaxTheta2() {
        return maxTheta2;
    }

    public double getMinTheta1Dot() {
        return -maxTheta1Dot;
    }

    public double getMaxTheta1Dot() {
        return maxTheta1Dot;
    }

    public double getMinTheta2Dot() {
        return -maxTheta2Dot;
    }

    public double getMaxTheta2Dot() {
        return maxTheta1Dot;
    }

    public double getTheta1() {
        return theta1;
    }

    public double getTheta1Dot() {
        return theta1Dot;
    }

    public double getTheta2() {
        return theta2;
    }

    public double getTheta2Dot() {
        return theta2Dot;
    }
}
