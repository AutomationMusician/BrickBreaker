// InputController.java

import com.pi4j.io.gpio.*;

public class InputController {
	private static GpioController gpio;
	private static GpioPinDigitalInput[] inputs;
	private static boolean rasPi = true;
	
	public static final int SELECT = 0;
	public static Joystick left = new Joystick(1, 2, 3, 4);
	public static Joystick right = new Joystick(5, 6, 7, 8);
	
	private static Pin[] pins = {
		RaspiPin.GPIO_00,
		RaspiPin.GPIO_01,
		RaspiPin.GPIO_02,
		RaspiPin.GPIO_03,
		RaspiPin.GPIO_04,
		//RaspiPin.GPIO_05,
		RaspiPin.GPIO_06,
		RaspiPin.GPIO_07,
		//RaspiPin.GPIO_08,
		RaspiPin.GPIO_10,
		RaspiPin.GPIO_11,
	};
	
	
	public static void initialize() {
		try {
			gpio = GpioFactory.getInstance();
			inputs = new GpioPinDigitalInput[pins.length];
			for (int i=0; i<inputs.length; i++) {
				inputs[i] = gpio.provisionDigitalInputPin(pins[i]);
				inputs[i].setShutdownOptions(true);
			}
		} catch (java.lang.UnsatisfiedLinkError e) {
			rasPi = false;
		}
	}
	
	public static boolean isHigh(int index) {
		if (rasPi)
			return inputs[index].isHigh();
		else
			return false;
	}
}
