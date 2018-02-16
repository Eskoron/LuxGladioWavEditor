package serial;

import datastructure.MaxStack;
import math.Quaternion;
import math.Vector3;

public class SerialDataManager {

	private SerialComm comm;
	
	private boolean isRecording;
	
	private MaxStack<Vector3> euler;
	private MaxStack<Vector3> eulerAaverage;
	
	private MaxStack<Quaternion> quats;
	private MaxStack<Quaternion> quatAverage;
	
	private MaxStack<Vector3> real;
	private MaxStack<Vector3> realAverage;
	
	
	SerialDataManager(){
		
		comm = new SerialComm("COM3");
		
	}
	
	public void startRecording(EDataType[] types) {
		
		if(isRecording){return;}
		
		if(!comm.Open()) {
			return;
		}
		SendDataTypeListening(EDataType.None);
		for (EDataType type : types) {
			SendDataTypeListening(type);
		}
	}
	
	
	
	public void stopRecording() {
		SendDataTypeListening(EDataType.None);
		comm.Close();
	}
	
	private void SendDataTypeListening(EDataType type) {
		switch (type) {
		
		case None:
			comm.SendString("n");
			break;
		case Euler:
			comm.SendString("e");
			break;
		case Quaternion:
			comm.SendString("q");
			break;
		case RealAcceleration:
			comm.SendString("r");
			break;
		case WorldAcceleration:
			comm.SendString("w");
			break;
		case YawPirchRoll:
			comm.SendString("y");
			break;
		default:
			break;
		}
		
	}
	
	
}
