import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;


public class BetterCircularFractalAnimation extends FractalAnimation 
{

	private FractalDrawingPanel drawingPanel;
	private Mandelbrot mandy;
	private int multiplier;
	private int pause;
	private int posInList;
	
	private boolean[][] recolored;
	
	public BetterCircularFractalAnimation(FractalDrawingPanel fdp, Mandelbrot mb, int multi, 
			int p, int pos)
	{
		drawingPanel = fdp;
		mandy = mb;
		multiplier = multi;
		pause = p;
		posInList = pos;
		int[][] iters = mandy.getIterationNumbers();
		recolored = new boolean[iters.length][iters[0].length];
		for(boolean[] row: recolored)
			Arrays.fill(row, false);
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

		int xDistCenter, yDistCenter;
		
		double currPixelDistToCenter;
		
		while(currRadius <= maxRadius)
		{
			
			for(int r = centerY - currRadius; r <= centerY + currRadius; r++)
			{
				for(int c = centerX - currRadius; c <= centerX + currRadius; c++)
				{
					xDistCenter = Math.abs(centerX - c);
					yDistCenter = Math.abs(centerY - r);
					currPixelDistToCenter = Math.sqrt(xDistCenter*xDistCenter + yDistCenter*yDistCenter);
					if(r > 0 && c > 0 && r < recolored.length && c < recolored[r].length 
							&& !recolored[r][c] && currPixelDistToCenter <= currRadius
							&& currPixelDistToCenter > currRadius - 2)
					{
						currIter = iterations[r][c];
						currColor = mandy.getColorChooser().getColor(multiplier*currIter);
						img.setRGB(c,r,currColor);
						
						recolored[r][c] = true;
					}
				}
			}
					
			
			try
			{
				Thread.sleep(7);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			
			
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


