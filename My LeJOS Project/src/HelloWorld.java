import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class HelloWorld {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Button.LEDPattern(4);
		Sound.beepSequenceUp();
		LCD.clear();
		LCD.drawString("HOLY",2,2);
		Delay.msDelay(1000);
		LCD.drawString("SMOKE",2,3);
		Delay.msDelay(4000);
	}

}
