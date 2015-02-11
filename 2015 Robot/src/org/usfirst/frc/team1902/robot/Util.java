package org.usfirst.frc.team1902.robot;

public class Util {
		
    public static double sign(double d) {
    	double sign = 1;
    	if (d != 0) {
    		sign = (Math.abs(d) / d);
    	}
    	return sign;
    }
    
    /**
     * If d is bigger than max, set it to max. If it is lower than min, set it to 0. 
     * @return Returns a min-maxed version of d.
     */
	public static double minMax(double d, double min, double max) {
		double minMaxed = d;
		if (Math.abs(d) >= Math.abs(max)) {
			minMaxed = max * sign(d);
		} else if (Math.abs(d) < Math.abs(min)) {
			minMaxed = 0;
		}
		return minMaxed;
	}
}
