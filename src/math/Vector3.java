package math;

public class Vector3 {

	protected double x,y,z;
	
	public Vector3(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double GetX(){
		return x;
	}
	
	public double GetY(){
		return y;
	}
	
	public double GetZ(){
		return z;
	}
}
