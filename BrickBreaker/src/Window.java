// Window.java

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class Window extends JFrame {
	private static final long serialVersionUID = 5259700796854880162L;
	private static Display canvas;
	private int[][] testPoints;
	private boolean running;
	
	public Window() throws InterruptedException {
		System.out.println("Restarting");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Brick Breaker");
		setSize(1280, 700);
		//setSize(600, 400); 
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		Thread.sleep(1000);
		
		Container container = getContentPane();
		canvas = new Display(container.getWidth(), container.getHeight());
		add(canvas);
		
		addKeyListener(new FrameKeyListener());
		addWindowListener(new FrameWindowListener());
		//addMouseListener(new FrameMouseListener());
		
		testPoints = new int[][] {
			new int[] { getWidth()/4, getHeight()/4 },
			new int[] { getWidth()/4, 3*getHeight()/4 },
			new int[] { 3*getWidth()/4, getHeight()/4 },
			new int[] { 3*getWidth()/4, 3*getHeight()/4 },
		};
	}
	
	public void run() {
		canvas.setup();
		running = true;
	    while (running) {
	    	canvas.draw();
	    	if (!isWorking()) {
	    		running = false;
	    		Program.setRestart();
	    		System.out.print("Preparing to restart\t");
	    	}
	    }
	    
	    remove(canvas);
	    canvas.getGraphics().dispose();
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private class FrameKeyListener implements KeyListener {
		public void keyTyped(KeyEvent e) {}
		
		public void keyPressed(KeyEvent e) {
			InputController.addButton(e.getKeyCode());
		}
		
		public void keyReleased(KeyEvent e) {
			InputController.removeButton(e.getKeyCode());
		}
	}
	
	private class FrameWindowListener implements WindowListener {
		public void windowOpened(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
			running = false;
			remove(canvas);
			canvas.getGraphics().dispose();
			dispose();
		}
		public void windowClosed(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
	}
	
	private boolean isWorking() {
		try {
			Robot robot = new Robot();
			for (int i=0; i<testPoints.length; i++) {
				int width = testPoints[i][0];
				int height = testPoints[i][1];
				Color color = robot.getPixelColor(width, height);
				if (Color.WHITE.equals(color))
					return true;
			}
			return false;
		} catch (AWTException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public Display getCanvas() {
		return canvas;
	}
}
