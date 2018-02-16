package application;

import serial.SerialComm;

public class SerialExample {

	static SerialComm comm;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		System.out.println("lol");
		comm = new SerialComm("COM3");
		
		comm.Open();
		
		while(true) {
			if(comm.HasData()) {
				System.out.println(comm.ReadLine());
			}
			
		}
		
		
		
	}

}
