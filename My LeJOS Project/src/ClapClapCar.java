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
		int maxSoundLevel = 0; int minSoundLevel = 0;
		
		while(true) {
			@SuppressWarnings("resource")
			NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S1);
			SampleProvider sound = ss.getDBAMode();
			sound.fetchSample(level,0);
			if(level[0] > maxSoundLevel) {
				maxSoundLevel = (int) level[0];
				LCD.clear();
				LCD.drawString("max",2,2);
				LCD.drawInt(maxSoundLevel,4,2);
				LCD.drawString("min",2,3);
				LCD.drawInt(minSoundLevel,4,3);
			} else if(level[0] < minSoundLevel) {
				minSoundLevel = (int) level[0];
				LCD.clear();
				LCD.drawString("max",2,2);
				LCD.drawInt(maxSoundLevel,4,2);
				LCD.drawString("min",2,3);
				LCD.drawInt(minSoundLevel,4,3);
			}
		}
	}

}
