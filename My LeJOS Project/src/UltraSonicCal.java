import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class UltraSonicCal {
	public static void main (String[] args) {
		
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
		SampleProvider distance = us.getDistanceMode();
		
		float[] level = new float[1]; //A sound sample is just one number
		
		while (Button.ESCAPE.isUp()) {
			if (Button.ENTER.isDown()) {
				LCD.clear();
				distance.fetchSample(level, 0);	
				LCD.drawString(Float.toString(level[0]),2,2);
			}
		}
		us.close();
	}
}
