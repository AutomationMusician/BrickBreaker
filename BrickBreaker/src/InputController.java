import com.pi4j.io.gpio.*;

public class InputController {
	private static final GpioController gpio = GpioFactory.getInstance();

	private static Pin[] pins = {
		RaspiPin.GPIO_00,
		RaspiPin.GPIO_01,
		RaspiPin.GPIO_02,
		RaspiPin.GPIO_03,
		RaspiPin.GPIO_04,
		RaspiPin.GPIO_05,
		RaspiPin.GPIO_06,
		RaspiPin.GPIO_07,
		RaspiPin.GPIO_08,
	};
	
	private static GpioPinDigitalInput[] inputs = new GpioPinDigitalInput[pins.length];
	
	public static void initialize() {
		for (int i=0; i<inputs.length; i++) {
			inputs[i] = gpio.provisionDigitalInputPin(pins[i]);
			inputs[i].setShutdownOptions(true);
		}
	}
	
	public static boolean isHigh(int index) {
		return inputs[index].isHigh();
	}
}
