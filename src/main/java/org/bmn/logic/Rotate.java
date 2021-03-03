package org.bmn.logic;

import static java.lang.Math.*;


public class Rotate {

    public static double rotateX (double x, double y, double angle, double boardX, double boardY) {
        double result = 0.0;
        double mt = x * cos(toRadians(angle)) - y * sin(toRadians(angle));
        if (angle == 90.0) {
            result = mt + boardY;
        } else if (angle == 180.0) {
            result = mt + boardX;
        } else if (angle == 270.0) {
            result = mt;
        }
        return result;
    }

    public static double rotateY (double x, double y, double angle, double boardX, double boardY) {
        double result = 0.0;
        double mt = x * sin(toRadians(angle)) + y * cos(toRadians(angle));
        if (angle == 90.0) {
            result = mt;
        } else if (angle == 180.0) {
            result = mt + boardY;
        } else if (angle == 270.0) {
            result = mt + boardX;
        }
        return result;
    }
}
