import com.pi4j.io.gpio.*;


public class WhichPin {
	

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
        initialize();
        
        System.out.println("test electronics and see the listener feedback here in the console.");

        // keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(500);
            boolean empty = true;
            for (int i=0; i<inputs.length; i++) {
            	if (inputs[i].isHigh()) {
            		System.out.print(i + " - " + inputs[i].getPin() + ", ");
            		empty = false;
            	}
            }
            if (!empty)
            	System.out.println();
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller

	}
	
	private static GpioController gpio;
	private static GpioPinDigitalInput[] inputs;
	private static boolean rasPi = true;
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
				//if (i != 8)
					inputs[i] = gpio.provisionDigitalInputPin(pins[i], PinPullResistance.PULL_DOWN);
				//else 
					//inputs[i] = gpio.provisionDigitalInputPin(pins[i]);
				inputs[i].setShutdownOptions(true);
				/*
				inputs[i].addListener(new GpioPinListenerDigital() {
		            @Override
		            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		                // display pin state on console
		            	if (event.getState() == PinState.HIGH)
		            		System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
		            }

		        });
		        */
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
