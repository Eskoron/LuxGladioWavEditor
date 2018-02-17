package serial;

public enum EDataType {
	Euler,
	Quaternion,
	WorldAcceleration,
	YawPitchRoll,
	RealAcceleration,
	Time,
	None;
	
	public static EDataType GetType(char s) {
		
		switch (s) {
		case 'e':
			return EDataType.Euler;
		case 'q':
			return EDataType.Quaternion;
		case 'w':
			return EDataType.WorldAcceleration;
		case 'y':
			return EDataType.YawPitchRoll;
		case 'r':
			return EDataType.RealAcceleration;
		case 't':
			return EDataType.Time;
		default:
			return EDataType.None;
		}
	}
}
