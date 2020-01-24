import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class SimpleChap {

	public static void main(String[] args) {
		final int WAIT_TIME = 1000;
		final int RPS = 720;
		final int ROTATE_NINETY_TIME = 290;
		boolean clapped = false;
		
		LCD.drawString("SimpleChap.java",2,2);
		LCD.drawString("Version 1",2,3);
		LCD.drawString("Aedan Lawrence",2,4);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		Delay.msDelay(WAIT_TIME);
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		mLeft.synchronizeWith(new BaseRegulatedMotor [] {mRight});
		
		mLeft.setSpeed(RPS); //2 Revolutions Per Second (RPS)
		mRight.setSpeed(RPS);
		
		float[] level = new float[1]; //A sound sample is just one number
		
		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S2	);
		SensorMode sound = (SensorMode) ss.getDBAMode();
		SampleProvider clap = new ClapFilter(sound,0.6f,100);
		
		while (!clapped) {
			clap.fetchSample(level,0);
			while (level[0] > 0.5) {
				mLeft.startSynchronization();
				mLeft.forward();
				mRight.forward();
				mLeft.endSynchronization();
				clap.fetchSample(level,0);
			}
			clapped = true;
			Delay.msDelay(WAIT_TIME);
		}
		
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
		SampleProvider distance = us.getDistanceMode();
		
		clapped = false;
		while(!clapped) {
			mLeft.stop();
			mRight.stop();
			
			mRight.forward();
			Delay.msDelay(ROTATE_NINETY_TIME);
			mRight.stop();
			
			distance.fetchSample(level, 0);
			while (level[0] > 0.5) {
				mLeft.startSynchronization();
				mLeft.forward();
				mRight.forward();
				mLeft.endSynchronization();
				distance.fetchSample(level, 0);
			}
			clapped = true;
			Delay.msDelay(WAIT_TIME);
		}
		
		mLeft.stop();
		mRight.stop();
		mLeft.close();
		mRight.close();	
		ss.close();
		us.close();
	}
}