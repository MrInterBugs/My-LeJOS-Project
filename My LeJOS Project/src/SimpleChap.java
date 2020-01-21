import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class SimpleChap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int WAIT_TIME = 1000;
		final int RPS = 720;
		
		LCD.drawString("SimpleChap.java",2,2);
		LCD.drawString("Version 1",2,3);
		LCD.drawString("Aedan Lawrence",2,4);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		Delay.msDelay(WAIT_TIME);
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		mLeft.setSpeed(RPS); //2 Revolutions Per Second (RPS)
		mRight.setSpeed(RPS);
		
		float[] level = new float[1]; //A sound sample is just one number
		
		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S1);
		SensorMode sound = (SensorMode) ss.getDBAMode();
		SampleProvider clap = new ClapFilter(sound,0.6f,100);
		
		while (Button.ENTER.isUp()) {
			clap.fetchSample(level,0);
		}
		
		
		
		mLeft.stop();
		mRight.stop();
		mLeft.close();
		mRight.close();
		ss.close();
	}

}
