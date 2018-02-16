package parser;

public class SerialParser {

	
	static public int ParseInt(String string) {
		return Integer.parseInt(string);
	}
	
	static public Double ParseDouble(String string) {
		return Double.parseDouble(string);
	}
	
	static public  String[] SplitWhiteSpace(String string) {
		return string.split("\\s+");
	}
}
