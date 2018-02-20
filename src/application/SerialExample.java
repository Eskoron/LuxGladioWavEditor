package application;

import serial.SerialComm;

public class SerialExample {

	static SerialComm comm;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		comm = new SerialComm("COM3");
		
		for (String name : SerialComm.AvailablePorts()) {
			System.out.println(name);
		}
		
		comm.Open();
		
		
		comm.SendString("e");                   
		
		while(true) {
			if(comm.HasData()) {
				System.out.println(comm.ReadLine());
			}
			
		}
		
		
		
	}

}
