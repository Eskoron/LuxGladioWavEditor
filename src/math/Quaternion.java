package math;

public class Quaternion {

	private double w,x,y,z;
	
	
	public Quaternion() {
		w=1.0;
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}
	
	public Quaternion(double w, double x, double y, double z) {
		this.w=w;
		this.x =x;
		this.y = y;
		this.z = z;
	}
	
	public double getW() {
		return w;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
	
	public Quaternion getConjugate() {
		return new Quaternion(w,-x,-y,-z);
	}
	
	public void normalize() {
		double m = getMagnitude();
		w/=m;
		x/=m;
		y/=m;
		z/=m;
	}
	
	public double getMagnitude() {
		return Math.sqrt(w*w+ x*x + y*y + z*z);
	}
	
	public Quaternion product(Quaternion q) {
		 return new  Quaternion(
	                w*q.w - x*q.x - y*q.y - z*q.z,  // new w
	                w*q.x + x*q.w + y*q.z - z*q.y,  // new x
	                w*q.y - x*q.z + y*q.w + z*q.x,  // new y
	                w*q.z + x*q.y - y*q.x + z*q.w); // new z
	}
	
	public Quaternion getRotationQuaternion(Quaternion q) {
		return this.product(q.getConjugate());
	}
	
	
	public void add(Quaternion v) {
		w += v.w;
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	public void scale(double s) {
		w*=s;
		x*=s;
		y*=s;
		z*=s;
	}
	
	static public Quaternion average(Quaternion[] vs) {
		Quaternion av = new Quaternion();
		for (Quaternion v : vs) {
			av.add(v);
		}
		av.scale(1.0/(double)vs.length);
		return av;
	}
}
