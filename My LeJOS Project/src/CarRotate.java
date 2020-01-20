import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class CarRotate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int RPS = 720;
		final int SQUARE_SIDES = 4;
		final int WAIT_TIME = 1000;
		
		Button.LEDPattern(4);
		Sound.beepSequenceUp();
		Delay.msDelay(WAIT_TIME);
		Delay.msDelay(WAIT_TIME);
		
		LCD.drawString("CarRotate.java",2,2);
		LCD.drawString("Version 0.1",2,3);
		LCD.drawString("Aedan Lawrence",2,4);
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		mLeft.setSpeed(RPS); //2 Revolutions Per Second (RPS)
		mRight.setSpeed(RPS);
		
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		
		for (int i = 0; i < SQUARE_SIDES; i++) {
			mLeft.rotate(400);
			Delay.msDelay(WAIT_TIME);
			
			mLeft.startSynchronization();
			mLeft.rotate(900);
			mRight.rotate(900);
			mLeft.endSynchronization();
			mLeft.waitComplete();
			mRight.waitComplete();
			Delay.msDelay(WAIT_TIME);
		}
		mLeft.close();
		mRight.close();
	}

}