package serial;

import math.Quaternion;
import math.Vector3;

public class SerialParser {

	
	static public int parseInt(String string) {
		return Integer.parseInt(string);
	}
	
	static public Double parseDouble(String string) {
		return Double.parseDouble(string);
	}
	
	static public long parseLong(String string) {
		return Long.parseLong(string);
	}
	
	static public  String[] splitWhiteSpace(String string) {
		return string.split("\\s+");
	}
	
	static public Vector3 parseVector(String s1, String s2, String s3){
		return new Vector3(parseDouble(s1),parseDouble(s2),parseDouble(s3));
	}
	
	static public Quaternion parseQuaternion(String s1, String s2, String s3, String s4){
		return new Quaternion(parseDouble(s1),parseDouble(s2),parseDouble(s3),parseDouble(s4));
	}
}
