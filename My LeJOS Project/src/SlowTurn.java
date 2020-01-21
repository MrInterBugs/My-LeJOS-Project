import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class SlowTurn {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//iff immediateReturn is true, method returns immediately and the motor stops by itself
		final int WAIT_TIME = 1000;
		
		Button.LEDPattern(4);
		Sound.beepSequenceUp();
		Delay.msDelay(WAIT_TIME);
		Delay.msDelay(WAIT_TIME);
		
		LCD.drawString("SlowTurn.java",2,2);
		LCD.drawString("Version 0.1",2,3);
		LCD.drawString("Aedan Lawrence",2,4);
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		while(!mLeft.isStalled()) {
			mLeft.forward();
		}
		Sound.beepSequenceUp();
		mLeft.close();
		System.exit(0);
	}

}
