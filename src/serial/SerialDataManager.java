package serial;

import javax.xml.stream.events.EntityDeclaration;

import datastructure.MaxStack;
import math.Quaternion;
import math.Vector3;

public class SerialDataManager {

	private SerialComm comm;
	
	private boolean isRecording;
	
	private MaxStack<Vector3> euler;
	private MaxStack<Vector3> eulerDif;
	private Vector3 eulerPrev;
	
	private MaxStack<Quaternion> quat;
	private MaxStack<Quaternion> quatDif;
	private Quaternion quatPrev;
	
	private MaxStack<Vector3> real;
	private MaxStack<Vector3> realDif;
	private Vector3 realPrev;
	
	private MaxStack<Vector3> world;
	private MaxStack<Vector3> worldDif;
	private Vector3 worldPrev;
	
	private MaxStack<Vector3> ypr;
	private MaxStack<Vector3> yprDif;
	private Vector3 yprPrev;
	
	private long firstTime;
	private boolean hasTime;
	
	int maxData;
	
	SerialDataManager(int max_datapoints){
		
		comm = new SerialComm("COM3");
		
		maxData = max_datapoints;
		
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
		resetValues();
	}
	
	private void resetValues() {
		euler = new MaxStack<Vector3>(maxData);
		quat = new MaxStack<Quaternion>(maxData);
		world = new MaxStack<Vector3>(maxData);
		real = new MaxStack<Vector3>(maxData);
		ypr = new MaxStack<Vector3>(maxData);
		
		eulerDif = new MaxStack<Vector3>(maxData);
		quatDif = new MaxStack<Quaternion>(maxData);
		worldDif = new MaxStack<Vector3>(maxData);
		realDif = new MaxStack<Vector3>(maxData);
		yprDif = new MaxStack<Vector3>(maxData);
		hasTime = false;
		firstTime = 0;
		
	}

	public void loop() {
		while(comm.HasData()) {
			String[] s = SerialParser.splitWhiteSpace(comm.ReadLine());
			
			switch (EDataType.GetType(s[0].charAt(0))) {
			
			case  Time:
				
			
			case Euler:
				Vector3 cur = SerialParser.parseVector(s[1], s[2], s[3]);
				euler.Add(cur);
				eulerDif.Add(Vector3.sub(cur,eulerPrev));
				eulerPrev = cur;
				break;
			case None:
				break;
			case Quaternion:
				Quaternion curQ = SerialParser.parseQuaternion(s[1], s[2], s[3], s[4]);
				quat.Add((curQ));
				quatDif.Add(quatPrev.product(curQ));
				quatPrev =  curQ;
				break;
			case RealAcceleration:
				cur = SerialParser.parseVector(s[1], s[2], s[3]);
			real.Add(cur);
				realDif.Add(Vector3.sub(cur,realPrev));
				realPrev = cur;
				break;
			case WorldAcceleration:
				cur = SerialParser.parseVector(s[1], s[2], s[3]);
				world.Add(cur);
				worldDif.Add(Vector3.sub(cur,worldPrev));
				worldPrev = cur;
				break;
			case YawPitchRoll:
				cur = SerialParser.parseVector(s[1], s[2], s[3]);
				ypr.Add(cur);
				yprDif.Add(Vector3.sub(cur,yprPrev));
				yprPrev = cur;
				break;
			default:
				break;
			
			}
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
		case YawPitchRoll:
			comm.SendString("y");
			break;
		default:
			break;
		}
		
	}
	
	
}
