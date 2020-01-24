import lejos.hardware.Battery;
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

/**
 * Program to demonstrate different behaviours for our Lego robot.
 * 
 * @author Aedan Lawrence (2019)
 */
public class Behavior {
	
	static final int SHORT_PAUSE = 500;
	static final int SECOND_PAUSE = 1000;
	
	/**
	 * Displays the program and version information until a button is pressed.
	 * Also shows group members names.
	 */
	public static void init() {
		LCD.drawString("Behavior.jar",2,2);
		LCD.drawString("Version 0.1",2,3);
		LCD.drawString("Press Enter",2,5);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		LCD.drawString("Aedan Lawrence",2,2);
		LCD.drawString("Bruce Lay",2,3);
		LCD.drawString("Edmund Chee",2,4);
		LCD.drawString("Joules James",2,5);
		LCD.drawString("Press Enter",2,6);
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
	}
	
	/**
	 * A method to kill the program if the battery is below 15%.
	 * Also tells you the current percentage if it is below 15%.
	 */
	public static void checkBattery() {
		float currentVoltage = Battery.getVoltage();
		if(currentVoltage < 0.15f) {
			LCD.clear();
			LCD.drawString("The battery is",2,2);
			LCD.drawString("low on charge!",2,3);
			LCD.drawString(Float.toString(currentVoltage) + "%",2,4);
			Delay.msDelay(SECOND_PAUSE);
			System.exit(-1);
		}
	}
	
	/**
	 * @param args
	 * 
	 * Main method to hold constants and call other methods.
	 */
	public static void main(String[] args) {
		final int ONE_METER = 1000;
		
		final int U_TURN = 180;
		
		final int ANGULAR_SPEED_NIGHT = 70; // How fast around corners in low light(degrees/sec)
		final int LINEAR_SPEED_NIGHT = 100; // How fast in a straight line in low light(mm/sec)
		final int ANGULAR_SPEED_DAY = 140; // How fast around corners in bright light(degrees/sec)
		final int LINEAR_SPEED_DAY = 200; // How fast in a straight line in bright light(mm/sec)
		
		final double WHEEL_DIAMETER = 56; // The diameter (mm) of the wheels
		final double AXLE_LENGTH = 120; // The distance (mm) your two driven wheels

		BaseRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, WHEEL_DIAMETER).offset(AXLE_LENGTH/2);
		
		BaseRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, WHEEL_DIAMETER).offset(-AXLE_LENGTH/2);
		
		final MovePilot PILOT = pilot(rightWheel, leftWheel);
		
		checkBattery();
		
		init();
		
		move(LINEAR_SPEED_DAY, ONE_METER, PILOT);
		turn(U_TURN, ANGULAR_SPEED_DAY, PILOT);
		Delay.msDelay(SHORT_PAUSE);
		move(LINEAR_SPEED_NIGHT, ONE_METER, PILOT);
		turn(U_TURN, ANGULAR_SPEED_NIGHT, PILOT);
	}
	
	/**
	 * Allows the creation of a movePilot to control the movements of the robot.
	 * 
	 * @return the move pilot.
	 */
	public static MovePilot pilot(Wheel leftWheel, Wheel rightWheel) {
		Chassis chassis = new WheeledChassis(new Wheel[]{rightWheel,leftWheel},WheeledChassis.TYPE_DIFFERENTIAL);
		return(new MovePilot(chassis));
	}
	
	/**
	 * @param SPEED The speed which the robot moves at.
	 * @param TRAVEL_DISTANCE Sets the total distance to travel in mm.
	 * @param PILOT The movePilot to control the motors.
	 * 
	 * Using a movePilot moves the robot at a set speed in a straight line.
	 */
	public static void move(int SPEED, int TRAVEL_DISTANCE, MovePilot PILOT) {
		LCD.clear();
		
		PoseProvider poseProvider = new OdometryPoseProvider(PILOT);
		
		PILOT.setLinearSpeed(SPEED);

		Sound.beep();
		PILOT.travel(TRAVEL_DISTANCE);
		
		PILOT.stop();
		LCD.drawString(poseProvider.getPose().toString(),0,0);
	}
	
	/**
	 * @param ANGLE The angle to turn the robot by.
	 * @param SPEED The speed at which to do the turn at.
	 * @param PILOT The movePilot to control the motors.
	 * 
	 * Using a movePilot rotates the robot through an angle at a set speed.
	 */
	public static void turn(int ANGLE, int SPEED, MovePilot PILOT) {
		LCD.clear();
		
		PoseProvider poseProvider = new OdometryPoseProvider(PILOT);
		
		PILOT.setAngularSpeed(SPEED);

		Sound.beep();
		PILOT.rotate(ANGLE);
		
		PILOT.stop();
		LCD.drawString(poseProvider.getPose().toString(),0,0);
	}
}