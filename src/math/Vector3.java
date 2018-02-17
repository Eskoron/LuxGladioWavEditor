package math;

public class Vector3 {

	protected double x,y,z;
	
	public Vector3(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Vector3(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector3 v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getZ(){
		return z;
	}
	
	public void add(Vector3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	public void sub(Vector3 v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
	}
	
	public void scale(double s) {
		x*=s;
		y*=s;
		z*=s;
	}
	
	static public Vector3 average(Vector3[] vs) {
		Vector3 av = new Vector3();
		for (Vector3 v : vs) {
			av.add(v);
		}
		av.scale(1.0/(double)vs.length);
		return av;
	}
	
	static public Vector3 sub(Vector3 v1, Vector3 v2) {
		Vector3 v = new Vector3(v1);
		v.sub(v2);
		return v;
	} 
}
