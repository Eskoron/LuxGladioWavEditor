package math;

public class BMath {

	
	public static double clamp(double value, double min, double max) {
		
		return Math.min(max, Math.max(value, min));
	}
	
	//calculates average of a array of double
	//dont care about overflow when adding
	public static double average(double[] values) {
		double val = 0.0;
		double div = values.length;
		
		if(div == 0) {
			return 0;
		}
		//else
		for(double v : values) {
			val += v;
		}		
		
		return (val / div);
	}
}
