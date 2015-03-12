import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class FractalDrawingPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = -6984773470073180198L;
	private int width, height;
	
	private Mandelbrot mandy;
	
	private boolean isAnimating;
	
	private ArrayList<Thread> animationThreadList;
	private FractalAnimation animator;
//	private int numberAnimationThreadsRunning;
	private double theta;
	
	private Timer timer;
	
	private int threadPauseSeeder = 0;
	
	public FractalDrawingPanel(int w, int h)
	{
		super();
		isAnimating = false;
		animationThreadList = new ArrayList<Thread>();
		width = w;
		height = h;
		mandy = new Mandelbrot(width, height, -0.5, 0, (int)((double)width/height*2), 2);
		mandy.draw();
		setBackground(Color.red);
	}
	
	public void paintComponent(Graphics g)
	{
		BufferedImage image = mandy.getImage();
		if( image != null )
		{
			Graphics2D g2d = (Graphics2D)g;
			if(isAnimating)
			{
//				theta += .005;
//				g2d.rotate(theta, getWidth()/2, getHeight()/2);
				g2d.drawImage(image, 0, 0, this);
			}
			else
			{
				g2d.drawImage(image, 0, 0, this);
			}
			
		}
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		repaint();
	}
	
	
	public void recenter(int deltaX, int deltaY)
	{
		if(!isAnimating)
		{
			mandy.recenter(deltaX,deltaY);
			mandy.draw();
			repaint();
		}
	}
	
	//this has the fractal zoom in on the new center (cX, cY)
	public void zoomIn(int cX, int cY)
	{
		if(!isAnimating)
		{
			mandy.zoomIn(cX, cY);
			mandy.draw();
			repaint();
		}
	}
	
	//this has the fractal zoom out with the new center (cX, cY)
	public void zoomOut(int cX, int cY)
	{
		if(!isAnimating)
		{
			mandy.zoomOut(cX, cY);
			mandy.draw();
			repaint();
			//numberAnimationThreadsRunning = 0;
		}
	}
	
	//(x,y) is location clicked
	public void animate(int x, int y)
	{
		if(!isAnimating)
		{
			isAnimating = true;
		}
		
		timer = new javax.swing.Timer(40,this);
		timer.start();
			
		//start a new thread that modifies the fractal and repaints
		
		Thread t;
		int maxThreads = 40;
		while(animationThreadList.size() < maxThreads)
		{
			isAnimating = true;
			animator = new BetterCircularFractalAnimation(this, mandy, (int)(Math.random()*3000+1 ), 
					threadPauseSeeder*3000, animationThreadList.size());
			threadPauseSeeder++;
			if(threadPauseSeeder >= maxThreads)
				threadPauseSeeder = 0;
			t = new Thread(animator);
			t.start();
			animationThreadList.add(t);
			
			System.out.println("starting animation number " + animationThreadList.size());
		}
	

		
	}
	
	public void removeAnimation(int pos)
	{
		System.out.println("Stopping position " + pos);
		if(pos < animationThreadList.size())
			animationThreadList.remove(pos);
		
		if(isAnimating)
			animate(getWidth()/2, getHeight()/2);
	}
	
	public void stopAnimation()
	{
		isAnimating = false;
//		for(int pos = 0; pos < animationThreadList.size(); pos++ )
//			animationThreadList.get(pos).stop();
		
		animationThreadList.clear();
		if(timer != null && timer.isRunning())
			timer.stop();
			
//		if( numberAnimationThreadsRunning < 6 )
//		{
//			//isAnimating = true;
//			animate(getWidth()/2, getHeight()/2);
//		}
	}
	
}
