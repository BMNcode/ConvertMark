package org.bmn.logic;

public class Shift {

    public static double shift (double x, double outSizeX, double innerSizeX) {
        if (x >= 0) {
            return x - ((outSizeX - innerSizeX) / 2);
        } else {
            return (outSizeX + x) - ((outSizeX - innerSizeX) / 2);
        }
    }
}
