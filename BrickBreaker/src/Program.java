// Program.java

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Program {
	public static final double speed = 4;
	private static Window window;
	private static Display canvas;
	private static boolean restart = true;
	public static ArrayList<Ball> balls = new ArrayList<Ball>();
	public static ArrayList<Paddle> paddles = new ArrayList<Paddle>();
	public static ArrayList<Brick> bricks = new ArrayList<Brick>();
	public static Queue<Ball> ballsToRemove = new ArrayDeque<Ball>();
	public static Queue<Brick> bricksToRemove = new ArrayDeque<Brick>();
	
	public static void main(String[] args) throws InterruptedException {
		InputController.initialize();
		while (restart) {
			resetVariables();
			window = new Window();
			canvas = window.getCanvas();
			window.run();
		}
	}
	
	private static void resetVariables() {
		balls.clear();
		paddles.clear();
		bricks.clear();
		ballsToRemove.clear();
		bricksToRemove.clear();
		if (window != null)
			window.dispose();
	}
	
	public static void setRestart() {
		restart = true;
	}
	
	public static Window getWindow() {
		return window;
	}
	
	public static Display getCanvas() {
		return canvas;
	}
}
