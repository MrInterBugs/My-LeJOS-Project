import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class FollowLine {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int WAIT_TIME = 1000;
		final int RPS = 720;
		
		LCD.drawString("FollowLine.java",2,2);
		LCD.drawString("Version 1",2,3);
		LCD.drawString("Aedan Lawrence",2,4);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		mLeft.setSpeed(RPS); //2 Revolutions Per Second (RPS)
		mRight.setSpeed(RPS);
		
		float[] level = new float[1]; //A sound sample is just one number
		float maxRedValue = 0; float minRedValue = 1;
		float average = 0; int count = 0;
		
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S3);
		SampleProvider colour = cs.getRedMode();
		
		LCD.drawString("Press enter",2,2);
		LCD.drawString("to continue",2,3);
		
		while (Button.ENTER.isUp()){
			colour.fetchSample(level,0);
			count++;
			average = average + level[0];
			if(level[0] > maxRedValue) {
				maxRedValue = level[0];
			} else if(level[0] < minRedValue) {
				minRedValue = level[0];
			}
		}
		LCD.clear();
		
		average = average / count;
		LCD.drawString("max:" + Float.toString(maxRedValue),2,2);
		LCD.drawString("min:" + Float.toString(minRedValue),2,3);
		LCD.drawString("Avr:" + Float.toString(average),2,4);
		
		while (Button.ENTER.isUp()){
			colour.fetchSample(level,0);
			if(level[0] > average) {
				mLeft.rotate(180);
			} else {
				mRight.rotate(180);
			}
		}
		mLeft.stop();
		mRight.stop();
		mLeft.close();
		mRight.close();
		cs.close();
	}

}
