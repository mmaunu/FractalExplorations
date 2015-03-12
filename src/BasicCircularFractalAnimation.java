import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;


public class BasicCircularFractalAnimation extends FractalAnimation 
{

	private FractalDrawingPanel drawingPanel;
	private Mandelbrot mandy;
	private int multiplier;
	private int pause;
	private int posInList;
	
	public BasicCircularFractalAnimation(FractalDrawingPanel fdp, Mandelbrot mb, int multi, 
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
		
		int maxRadius = (int)Math.sqrt(centerX*centerX + centerY*centerY);
		int currRadius = 0;
		
		int currIter, currColor, h, w;
		int actualXPositive, actualXNegative, actualYPositive, actualYNegative;

		while(currRadius <= maxRadius)
		{
			
			for( h = centerY - currRadius; h <= centerY + currRadius; h++ )
			{
				actualXPositive = (int)Math.sqrt(currRadius*currRadius - (h - centerY)*(h - centerY)) + centerX;
				actualXNegative = -1*actualXPositive;//(int)Math.sqrt(currRadius*currRadius - (h - centerY)*(h - centerY)) + centerX;
				
				if(  h >= 0 && h < iterations.length)
				{ 
					if(actualXPositive >= 0 && actualXPositive < iterations[h].length)
					{
						currIter = iterations[h][actualXPositive];
						currColor = mandy.getColorChooser().getColor(multiplier*currIter);
						img.setRGB(actualXPositive,h,currColor);
					}
					if(actualXPositive - 1 >= 0 && actualXPositive - 1 < iterations[h].length)
					{
						currIter = iterations[h][actualXPositive - 1];
						currColor = mandy.getColorChooser().getColor(multiplier*currIter);
						img.setRGB(actualXPositive - 1,h,currColor);
					}
					if(actualXNegative >= 0 && actualXNegative < iterations[h].length)
					{
						currIter = iterations[h][actualXNegative];
						currColor = mandy.getColorChooser().getColor(multiplier*currIter);
						img.setRGB(actualXNegative,h,currColor);
					}
					if(actualXNegative - 1 >= 0 && actualXNegative - 1 < iterations[h].length)
					{
						currIter = iterations[h][actualXNegative - 1];
						currColor = mandy.getColorChooser().getColor(multiplier*currIter);
						img.setRGB(actualXNegative - 1,h,currColor);
					}
					
				}
			}
			
			for( w = centerX - currRadius; w <= centerX + currRadius; w++ )
			{
				actualYPositive = (int)Math.sqrt(currRadius*currRadius - (w - centerX)*(w - centerX)) + centerY;
				actualYNegative = -1*actualYPositive;//(int)Math.sqrt(currRadius*currRadius - (w - centerX)*(w - centerX)) + centerY;
				
				 
				if(actualYPositive >= 0 && actualYPositive < iterations.length &&
						w >= 0 && w < iterations[actualYPositive].length)
				{
					currIter = iterations[actualYPositive][w];
					currColor = mandy.getColorChooser().getColor(multiplier*currIter);
					img.setRGB(w,actualYPositive,currColor);
				}
				if(actualYPositive - 1 >= 0 && actualYPositive - 1 < iterations.length &&
						w >= 0 && w < iterations[actualYPositive - 1].length)
				{
					currIter = iterations[actualYPositive - 1][w];
					currColor = mandy.getColorChooser().getColor(multiplier*currIter);
					img.setRGB(w,actualYPositive - 1,currColor);
				}
				
				if(actualYNegative >= 0 && actualYNegative < iterations.length &&
						w >= 0 && w < iterations[actualYNegative].length)
				{
					currIter = iterations[actualYNegative][w];
					currColor = mandy.getColorChooser().getColor(multiplier*currIter);
					img.setRGB(w,actualYNegative,currColor);
				}
				if(actualYNegative - 1 >= 0 && actualYNegative - 1 < iterations.length &&
						w >= 0 && w < iterations[actualYNegative - 1].length)
				{
					currIter = iterations[actualYNegative - 1][w];
					currColor = mandy.getColorChooser().getColor(multiplier*currIter);
					img.setRGB(w,actualYNegative - 1,currColor);
				}
					
				
			}
					
			
			try
			{
				Thread.sleep(7);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			
			//only paint occasionally
			if(currRadius%100 == 0)
				drawingPanel.repaint();
			
			currRadius++;
		}//end while
	
		drawingPanel.removeAnimation(posInList);
	//	drawingPanel.stopAnimation();

	}

}





/*
 * for( h = centerY - currRadius; h <= centerY + currRadius; h++ )
			{
				for( w = centerX - currRadius; w <= centerX + currRadius ; w++)
				{	
					actualRadius = Math.sqrt((h-centerY)*(h-centerY) + (w-centerX)*(w-centerX));
					if( Math.abs(actualRadius - currRadius) < 2 && 
							h >= 0 && h < iterations.length && w >= 0 && w < iterations[h].length)
					{
						currIter = iterations[h][w];
						currColor = mandy.getColorChooser().getColor(multiplier*currIter);
						img.setRGB(w,h,currColor);
					}
				
					
				}	
				
			}
 */


