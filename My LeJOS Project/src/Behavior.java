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
		
		final int RIGHT_ANGLE = 90;
		final int U_TURN = 180;
		
		final int ANGULAR_SPEED_NIGHT = 70; // How fast around corners in low light(degrees/sec)
		final int LINEAR_SPEED_NIGHT = 100; // How fast in a straight line in low light(mm/sec)
		final int ANGULAR_SPEED_DAY = 140; // How fast around corners in bright light(degrees/sec)
		final int LINEAR_SPEED_DAY = 200; // How fast in a straight line in bright light(mm/sec)
		
		LCD.drawString("Behavior.java",2,2);
		LCD.drawString("Version 0.1",2,3);
		LCD.drawString("Aedan Lawrence",2,4);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		
		move(LINEAR_SPEED_DAY, ONE_METER);
		turn(U_TURN, ANGULAR_SPEED_DAY);
		Delay.msDelay(SHORT_PAUSE);
		move(LINEAR_SPEED_NIGHT, -ONE_METER);
		turn(U_TURN, ANGULAR_SPEED_NIGHT);
	}
	
	/*
	* Allows the creation of a movePilot to control the movements of the robot.
	* 
	* @return pilot
	*/
	public static MovePilot pilot() {
		BaseRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, WHEEL_DIAMETER).offset(AXLE_LENGTH/2);
		
		BaseRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, WHEEL_DIAMETER).offset(-AXLE_LENGTH/2);
		
		Chassis chassis = new WheeledChassis(new Wheel[]{rightWheel,leftWheel},WheeledChassis.TYPE_DIFFERENTIAL);
		return(new MovePilot(chassis));
	}
	
	/*
	* @input Speed
	* @input DistanceToTravel
	* 
	* Using a movePilot moves the robot at a set speed in a straight line.
	*/
	public static void move(int SPEED, int TRAVEL_DISTANCE) {
		LCD.clear();
		
		MovePilot pilot = pilot();
		PoseProvider poseProvider = new OdometryPoseProvider(pilot);
		
		pilot.setLinearSpeed(SPEED);

		Sound.beep();
		pilot.travel(TRAVEL_DISTANCE);
		
		pilot.stop();
		LCD.drawString(poseProvider.getPose().toString(),0,0);
	}
	
	/*
	* @input AngleToTurn
	* @input Speed
	* 
	* Using a movePilot rotates the robot through an angle at a set speed.
	*/
	public static void turn(int ANGLE, int SPEED) {
		LCD.clear();
		
		MovePilot pilot = pilot();
		PoseProvider poseProvider = new OdometryPoseProvider(pilot);
		
		pilot.setAngularSpeed(SPEED);

		Sound.beep();
		pilot.rotate(ANGLE);
		
		pilot.stop();
		LCD.drawString(poseProvider.getPose().toString(),0,0);
	}
}