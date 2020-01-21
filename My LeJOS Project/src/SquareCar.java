import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class SquareCar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int ROTATE_TIME = 290;
		final int FORWARD_TIME = 1000;
		final int SQUARE_SIDES = 4;
		final int RPS = 720;
		
		LCD.drawString("SquareCar.java",2,2);
		LCD.drawString("Version 1",2,3);
		LCD.drawString("Aedan Lawrence",2,4);
		Button.LEDPattern(4);
		Sound.beepSequenceUp();
		Delay.msDelay(FORWARD_TIME);
		Delay.msDelay(FORWARD_TIME);
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		mLeft.setSpeed(RPS); //2 Revolutions Per Second (RPS)
		mRight.setSpeed(RPS);
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		
		for (int i = 0; i < SQUARE_SIDES; i++) {
			mLeft.startSynchronization();
			mLeft.backward();
			mRight.forward();
			mLeft.endSynchronization();
			Delay.msDelay(ROTATE_TIME);
			mLeft.startSynchronization();
			mLeft.stop();
			mRight.stop();
			mLeft.endSynchronization();
			Delay.msDelay(FORWARD_TIME);
			mLeft.startSynchronization();
			mLeft.forward();
			mRight.forward();
			mLeft.endSynchronization();
			Delay.msDelay(FORWARD_TIME);
			mLeft.startSynchronization();
			mLeft.stop();
			mRight.stop();
			mLeft.endSynchronization();
			Delay.msDelay(FORWARD_TIME);
		}
		
		mLeft.close();
		mRight.close();
	}

}
