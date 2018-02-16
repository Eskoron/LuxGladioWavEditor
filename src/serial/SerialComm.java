package serial;

import com.fazecast.jSerialComm.*;
import java.util.*;
import java.io.*;

public class SerialComm {
  public static SerialPort port;
  static InputStream in;
  static OutputStream out;
  static BufferedReader reader;
  static String comPort;

  public SerialComm(String ComPort){
	 comPort = ComPort;
	  
  }
  
  public boolean Open() {
	  
	  
	  port = SerialPort.getCommPorts()[1];
	  port.openPort();
	  if (port.isOpen()) {
		    System.out.println("Port initialized!");
		    //timeout not needed for event based reading
		    //userPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
		    } else {
		    System.out.println("Port not available");
		    return false;
		    }
	  port.setBaudRate(115200);
	  
	  reader = new BufferedReader(new InputStreamReader(port.getInputStream()));
	  return true;
  }
  
  public boolean HasData() {
	  return (port.bytesAvailable() != 0)?true : false;  
  }
  
  public String ReadLine() {
	  try {
		return reader.readLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	} 	  
  }
  
  public void SendString(String s) {
	  OutputStream stream =  port.getOutputStream();
		try {
			stream.write(s.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
  }
  
  public boolean Close() {
	  try {
		reader.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return port.closePort();
  }
  
}
