import lejos.hardware.Button;
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
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

public class PilotSquare {
	
	static final float WHEEL_DIAMETER = 56; // The diameter (mm) of the wheels
	static final float AXLE_LENGTH = 120; // The distance (mm) your two driven wheels
	static final float ANGULAR_SPEED = 100; // How fast around corners (degrees/sec)
	static final float LINEAR_SPEED = 70; // How fast in a straight line (mm/sec)
	

	public static void main(String[] args) {
		
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel wL = WheeledChassis.modelWheel(mL, WHEEL_DIAMETER).offset(AXLE_LENGTH/2);
		
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel wR = WheeledChassis.modelWheel(mR, WHEEL_DIAMETER).offset(-AXLE_LENGTH/2);
		
		Chassis chassis = new WheeledChassis(new Wheel[]{wR,wL},WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot plt = new MovePilot(chassis);
		PoseProvider poseP = new OdometryPoseProvider(plt);
		
		plt.setLinearSpeed(LINEAR_SPEED);
		plt.setAngularSpeed(ANGULAR_SPEED);

		Navigator navigator = new Navigator(plt, poseP);
		
		Path path = new Path();
		path.add(new Waypoint(100, 0));
		path.add(new Waypoint(100, 100));
		path.add(new Waypoint(0, 100));
		path.add(new Waypoint(0, 0));
		
		navigator.followPath(path);
		navigator.waitForStop();
		
		plt.stop();
		LCD.drawString(poseP.getPose().toString(),0,0);
		Button.ENTER.waitForPressAndRelease();
	}

}
