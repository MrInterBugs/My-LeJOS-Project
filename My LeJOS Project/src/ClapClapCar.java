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
		
		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S2);
		SampleProvider sound = ss.getDBAMode();
		LCD.drawString("Press enter",2,2);
		LCD.drawString("to continue",2,3);
		while (Button.ENTER.isUp()){
			sound.fetchSample(level,0);
			if(level[0] >= maxSoundLevel) {
				maxSoundLevel = level[0];
			} else if(level[0] <= minSoundLevel) {
				minSoundLevel = level[0];
			}
		}
		LCD.clear();
		LCD.drawString(Float.toString(maxSoundLevel),2,2);
		LCD.drawString(Float.toString(minSoundLevel),2,3);
		Delay.msDelay(WAIT_TIME);
		Delay.msDelay(WAIT_TIME);
		ss.close();
	}

}
