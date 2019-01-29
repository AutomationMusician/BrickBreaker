// Window.java

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashSet;
import javax.swing.JFrame;

public class Window extends JFrame {
	private static final long serialVersionUID = 5259700796854880162L;
	
	private HashSet<Integer> pressedButtons = new HashSet<Integer>();
	private static Screen canvas;
	private boolean running;
	
	public Window() throws InterruptedException {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setSize(400, 400); 
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		Thread.sleep(1000);
		
		Container container = getContentPane();
		canvas = new Screen(container.getWidth(), container.getHeight());
		add(canvas);
		addKeyListener(new FrameKeyListener());
		addWindowListener(new FrameWindowListener());
	}
	
	public void run() {
		canvas.setup();
		running = true;
	    while (running) canvas.draw();
	}
	
	private class FrameKeyListener implements KeyListener {
		public void keyTyped(KeyEvent e) {}
		
		public void keyPressed(KeyEvent e) {
			pressedButtons.add(e.getKeyCode());
		}
		
		public void keyReleased(KeyEvent e) {
			pressedButtons.remove(e.getKeyCode());
		}
	}
	
	private class FrameWindowListener implements WindowListener {
		public void windowOpened(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
			running = false;
			canvas.getGraphics().dispose();
		}
		public void windowClosed(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
	}

	public Screen getCanvas() {
		return canvas;
	}
	
	public boolean isPressed(int keyCode) {
		return pressedButtons.contains(keyCode);
	}
}
