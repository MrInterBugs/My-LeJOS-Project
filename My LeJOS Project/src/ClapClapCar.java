import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ClapClapCar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int WAIT_TIME = 1000;
		
		LCD.drawString("GoCar.java",2,2);
		LCD.drawString("Version 1",2,3);
		LCD.drawString("Aedan Lawrence",2,4);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		Delay.msDelay(WAIT_TIME);
		
		float[] level = new float[1]; //A sound sample is just one number
		
		while(true) {
			NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S1);
			SampleProvider sound = ss.getDBAMode();
			LCD.drawString(level[0],2,2);
			Delay.msDelay(WAIT_TIME);
			LCD.clear();
		}
	}

}
