// Display.java

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class Display extends Canvas {
	private static final long serialVersionUID = 5241834608422998589L;
	private static Font sanSerifFont = new Font("SanSerif", Font.BOLD, 72);
	
	public final int LEFT = 37;
	public final int UP = 38;
	public final int RIGHT = 39;
	public final int DOWN = 40;
	public final int A = 65;
	public final int S = 83;
	public final int W = 87;
	public final int D = 68;
	
	public final int updatesPerFrame = 2;
	
	private BufferStrategy bufferStrategy;
	private Graphics2D graphics;
	private FontMetrics sanSerifFontMetrics;
	
	public Display(int width, int height) {
		super();
		setSize(width, height);
	}
	
	public void setup() {
		Program.paddles.add(new Paddle(-0.5, A, D));
		Program.paddles.add(new Paddle(0.5, LEFT, RIGHT));
		createBricks();
		createBalls();
		
		createBufferStrategy(3);
	    bufferStrategy = getBufferStrategy();
	    graphics = (Graphics2D) bufferStrategy.getDrawGraphics();

		graphics.setFont(sanSerifFont);
	    sanSerifFontMetrics = graphics.getFontMetrics();
	}
	
	public void draw() {
        for (int i=0; i<updatesPerFrame; i++) {
            updateObjects();
            removeObjects();
        }
        display();
        bufferStrategy.show();
	}
	
	private void display() {
    	graphics.setColor(Color.WHITE);
        graphics.clearRect(0, 0, getWidth(), getHeight());
        graphics.fillRect(0, 0, getWidth(), getHeight());
        
        displayObjects();
	}
	
	private void updateObjects() {
		//update objects
        for (Paddle paddle : Program.paddles)
        	paddle.updatePosition();
        
        for (Ball ball : Program.balls) 
        	ball.updatePosition();
	}
	
	private void displayObjects() {
		//display objects
        for (Paddle paddle : Program.paddles)
        	paddle.display(graphics);
        
        for (Brick brick : Program.bricks)
        	brick.display(graphics);
        
        for (Ball ball : Program.balls) 
        	ball.display(graphics);
	}
	
	private void removeObjects() {
		while (!Program.ballsToRemove.isEmpty()) {
			Ball ball = Program.ballsToRemove.remove();
			Program.balls.remove(ball);
		}
		
		while (!Program.bricksToRemove.isEmpty()) {
			Brick brick = Program.bricksToRemove.remove();
			Program.bricks.remove(brick);
		}
		
		if (Program.balls.isEmpty()) 
			noBallsLeft();
		
		if (Program.bricks.isEmpty()) 
			restart();
	}
	
	private void restart() {
		
		graphics.setColor(Color.RED);
		String str = "YOU WIN!";
		int w = sanSerifFontMetrics.stringWidth(str);
	    int h = sanSerifFontMetrics.getAscent();
	    graphics.drawString(str, (getWidth() - w) / 2, (getHeight() - h) / 2);
        
	    bufferStrategy.show();
		
		Program.balls.clear();
		createBricks();
		noBallsLeft();
		
	}
	
	private void noBallsLeft() {
		try {
			for (int i=5; i>=0; i--) {
				display();
				
				graphics.setColor(Color.RED);
				String str = "Restarting in " + i;
			    int w = sanSerifFontMetrics.stringWidth(str);
			    int h = sanSerifFontMetrics.getAscent();
			    graphics.drawString(str, (getWidth() - w) / 2, (getHeight() - h) / 2);
		        
			    bufferStrategy.show();
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		createBalls();
	}
	
	private void createBalls() {
		for (Paddle paddle : Program.paddles) 
			Program.balls.add(new Ball(paddle.position));
	}
	
	private void createBricks() {
		double margin = 10;
		double effectiveWidth = Brick.width + margin;
		double sideMargin = (getWidth() % effectiveWidth)/2.0;
		double xOffset = sideMargin + effectiveWidth/2.0;
		double effectiveHeight = Brick.height + margin;
		double yOffset = Brick.height/2.0 + margin;
		
		for (double x=xOffset; x<getWidth(); x += effectiveWidth) {
			for (double y=yOffset; y<getHeight()*3.0/4.0; y += effectiveHeight) {
				if (Math.random() < 0.1)
					Program.bricks.add(new Brick(new Vector(x, y)));
			}
		}
	}
	
	public Graphics2D getGraphics() {
		return graphics;
	}
	
}
