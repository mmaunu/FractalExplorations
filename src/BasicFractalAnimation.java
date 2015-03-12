import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;


public class BasicFractalAnimation extends FractalAnimation 
{

	private FractalDrawingPanel drawingPanel;
	private Mandelbrot mandy;
	private int multiplier;
	private int posInList;
	private int pause;
	
	public BasicFractalAnimation(FractalDrawingPanel fdp, Mandelbrot mb, int multi, 
			int p, int pos)
	{
		drawingPanel = fdp;
		mandy = mb;
		multiplier = multi;
		pause = p;
		posInList = pos;
	}
	
	@Override
	public void run()
	{
		try
		{
			Thread.sleep(pause);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		BufferedImage img = mandy.getImage();
		
		int centerX = img.getWidth()/2;
		int centerY = img.getHeight()/2;
		
		int[][] iterations = mandy.getIterationNumbers();
		
		int maxRadius = Math.max(centerX, centerY);
		int currRadius = 0;
		
		int currIter, currColor, h, w;

		while(currRadius <= maxRadius)
		{
			//set color for the top and bottom of square
			for( w = centerX - currRadius; w <= centerX + currRadius ; w++)
			{	
				h = centerY - currRadius;
				if(h >= 0 && h < iterations.length && w >= 0 && w < iterations[h].length)
				{
					currIter = iterations[h][w];
					currColor = mandy.getColorChooser().getColor(multiplier*currIter);
					img.setRGB(w,h,currColor);
				}
				
				h = centerY + currRadius;
				if(h >= 0 && h < iterations.length && w >= 0 && w < iterations[h].length)
				{
					currIter = iterations[h][w];
					currColor = mandy.getColorChooser().getColor(multiplier*currIter);
					img.setRGB(w,h,currColor);
				}
			}	
			
			//now do the same for the sides of the square
			for( h = centerY - currRadius; h <= centerY + currRadius; h++ )
			{
				w = centerX - currRadius;
				if(h >= 0 && h < iterations.length && w >= 0 && w < iterations[h].length)
				{
					currIter = iterations[h][w];
					currColor = mandy.getColorChooser().getColor(multiplier*currIter);
					img.setRGB(w,h,currColor);
				}
				
				w = centerX + currRadius;
				if(h >= 0 && h < iterations.length && w >= 0 && w < iterations[h].length)
				{
					currIter = iterations[h][w];
					currColor = mandy.getColorChooser().getColor(multiplier*currIter);
					img.setRGB(w,h,currColor);
				}
			}
					
			
			try
			{
				Thread.sleep(20);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			
			//only paint occasionally...move that functionality to a Timer in drawing panel?
//			if(currRadius%20 == 0)
//				drawingPanel.repaint();
			
			currRadius++;
		}//end while
	
		drawingPanel.removeAnimation(posInList);
		System.out.println("BasicFractalAnimation ending run");

	}

}



