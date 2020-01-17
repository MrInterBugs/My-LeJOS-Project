import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class GoCar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LCD.drawString("GoCar.java",2,2);
		LCD.drawString("Version 1",2,3);
		LCD.drawString("Aedan Lawrence",2,4);
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		mLeft.setSpeed(720); //2 Revolutions Per Second (RPS)
		mRight.setSpeed(720);
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.forward();
		mLeft.endSynchronization();
		Delay.msDelay(1000);
		mLeft.startSynchronization();
		mLeft.stop();
		mRight.stop();
		mLeft.endSynchronization();
		mLeft.close();
		mRight.close();
	}

}
