import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Behavior {
	
	static final double WHEEL_DIAMETER = 56; // The diameter (mm) of the wheels
	static final double AXLE_LENGTH = 120; // The distance (mm) your two driven wheels

	public static void main(String[] args) {
		final int ONE_METER = 1000;
		
		final int SHORT_PAUSE = 500;
		
		final int ANGULAR_SPEED_NIGHT = 100; // How fast around corners (degrees/sec)
		final int LINEAR_SPEED_NIGHT = 70; // How fast in a straight line (mm/sec)
		final int ANGULAR_SPEED_DAY = 200; // How fast around corners (degrees/sec)
		final int LINEAR_SPEED_DAY = 140; // How fast in a straight line (mm/sec)
		
		LCD.drawString("Behavior.java",2,2);
		LCD.drawString("Version 0.1",2,3);
		LCD.drawString("Aedan Lawrence",2,4);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		move(LINEAR_SPEED_DAY, ANGULAR_SPEED_DAY, ONE_METER);
		Delay.msDelay(SHORT_PAUSE);
		move(LINEAR_SPEED_NIGHT, ANGULAR_SPEED_NIGHT, -ONE_METER);
	}
	
	public static void move(int SPEED, int ANGLE, int TRAVEL_DISTANCE) {
		LCD.clear();
		
		BaseRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, WHEEL_DIAMETER).offset(AXLE_LENGTH/2);
		
		BaseRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, WHEEL_DIAMETER).offset(-AXLE_LENGTH/2);
		
		Chassis chassis = new WheeledChassis(new Wheel[]{rightWheel,leftWheel},WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		PoseProvider PoseProvider = new OdometryPoseProvider(pilot);
		
		pilot.setLinearSpeed(SPEED);
		pilot.setAngularSpeed(ANGLE);

		Sound.beep();
		pilot.travel(TRAVEL_DISTANCE);
		
		pilot.stop();
		LCD.drawString(PoseProvider.getPose().toString(),0,0);
	}

}