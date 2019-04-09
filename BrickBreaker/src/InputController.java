// InputController.java

import java.util.HashSet;
import com.pi4j.io.gpio.*;

public class InputController {
	private static GpioController gpio;
	private static GpioPinDigitalInput[] inputs;
	private static boolean rasPi = true;
	
	private static HashSet<Integer> pressedButtons;
	private static final int ENTER = 10;
	private static final int LEFT = 37;
	private static final int UP = 38;
	private static final int RIGHT = 39;
	private static final int DOWN = 40;
	private static final int A = 65;
	private static final int S = 83;
	private static final int W = 87;
	private static final int D = 68;
	
	private static int select;
	private static Joystick left = new Joystick(7, 6, 2, 3);
	private static Joystick right = new Joystick(5, 8, 4, 1);
	
	private static Pin[] pins = {
		RaspiPin.GPIO_00,
		RaspiPin.GPIO_01,
		RaspiPin.GPIO_02,
		RaspiPin.GPIO_03,
		RaspiPin.GPIO_04,
		RaspiPin.GPIO_05,
		RaspiPin.GPIO_06,
		RaspiPin.GPIO_07,
		RaspiPin.GPIO_16,
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
		
		if (rasPi) {
			select = 0;
			left = new Joystick(7, 6, 2, 3);
			right = new Joystick(5, 8, 4, 1);
		} else {
			pressedButtons = new HashSet<Integer>();
			select = ENTER;
			left = new Joystick(W, S, A, D);
			right = new Joystick(UP, DOWN, LEFT, RIGHT);
		}
			
	}
	
	public static boolean isRaspi() {
		return rasPi;
	}
	
	public static void addButton(int index) {
		if (!rasPi)
			pressedButtons.add(index);
	}
	
	public static void removeButton(int index) {
		if (!rasPi)
			pressedButtons.remove(index);
	}
	
	public static int getSelect() {
		return select;
	}
	
	public static Joystick getLeft() {
		return left;
	}
	
	public static Joystick getRight() {
		return right;
	}
	
	public static boolean isHigh(int index) {
		if (rasPi)
			return inputs[index].isHigh();
		else
			return pressedButtons.contains(index);
	}
}
