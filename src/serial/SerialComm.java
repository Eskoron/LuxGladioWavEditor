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
  static boolean isReady;

  public SerialComm(String ComPort){
	 comPort = ComPort;
	  isReady = false;
  }
  
  public static String[] AvailablePorts() {
	 
	 SerialPort[] ports = SerialPort.getCommPorts();
	 String[] portNames = new String[ports.length];
	 
	 for (int i = 0; i < portNames.length; i++) {
		portNames[i] = ports[i].getSystemPortName();
	}
	 return portNames;
  }
  
  public boolean isReady() {
	  return isReady;
  }
  
  public boolean Open() {
	  
	  
	  port = SerialPort.getCommPorts()[0];
	  port.openPort();
	  if (port.isOpen()) {
		   
		    } else {
		    System.out.println("Port not available");
		    return false;
		    }
	  port.setBaudRate(115200);
	  
	  reader = new BufferedReader(new InputStreamReader(port.getInputStream()));
	  isReady= true;
	  while(true) {
		  if(HasData()) {
			  String line = ReadLine();
			  if(line.endsWith("Enabling DMP...")) {
				  break;
			  }else {
				  System.out.println(line);
			  }
		  }
	  }
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
			stream.close();
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
	  isReady = false;
	  return port.closePort();
	  
  }
  
}
