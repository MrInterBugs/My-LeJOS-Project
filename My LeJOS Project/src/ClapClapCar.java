import java.util.Arrays;

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
		
		LCD.drawString("ClapClapCar.java",2,2);
		LCD.drawString("Version 1",2,3);
		LCD.drawString("Aedan Lawrence",2,4);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		Delay.msDelay(WAIT_TIME);
		
		float[] level = new float[1]; //A sound sample is just one number
		float maxSoundLevel = 0; float minSoundLevel = 1;
		
		@SuppressWarnings("resource")
		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S2);
		SampleProvider sound = ss.getDBAMode();
		for (int i = 0; i < 10; i++){
			sound.fetchSample(level,0);
			String temp = Arrays.toString(level);
			LCD.drawString(temp,2,2);
			if(level[0] >= maxSoundLevel) {
				maxSoundLevel = level[0];
			} else if(level[0] <= minSoundLevel) {
				minSoundLevel = level[0];
			}
			Delay.msDelay(WAIT_TIME);
		}
		String temp = String.valueOf(maxSoundLevel);
		LCD.clear();
		LCD.drawString(temp,2,2);
		temp = String.valueOf(minSoundLevel);
		LCD.drawString(temp,2,3);
		Delay.msDelay(WAIT_TIME);
		Delay.msDelay(WAIT_TIME);
	}

}
