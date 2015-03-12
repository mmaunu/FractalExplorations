import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashSet;


public class Mandelbrot
{
	private int width, height;	//pixels
	private double centerA, centerB, scaleWidth, scaleHeight;	//in complex plane
//	private double[] reals, imags;			
	
	private BufferedImage img;
	
	private int maxIterations = 100;
	
	private FractalColorChooser colorChooser;
	
	private int[][] iterations;
	
	private int numberOfColors;
	
	public Mandelbrot(int w, int h, double ca, double cb, double sw, double sh)
	{
		width = w;
		height = h;
		centerA = ca;
		centerB = cb;
		scaleWidth = sw;
		scaleHeight = sh;
		iterations = new int[height][width];
		numberOfColors = 0;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		colorChooser = new BasicColorChooser();
	}
	
	public BufferedImage draw()
	{
		double topLeftA = centerA - 0.5*scaleWidth;
		double topLeftB = centerB + 0.5*scaleHeight;
		
		double deltaA = scaleWidth/width;
		double deltaB = scaleHeight/height;
		
		double currA = topLeftA, currB = topLeftB;	//stores current a+bi that we are testing
		
		double zCurrA, zCurrB, zTemp;//stores iterations as we test a+bi
		
		int intCurrColor;
		int currIter;
		
		for(int h = 0; h < height; h++)
		{
			for(int w = 0; w < width; w++)
			{
				//test (currA,currB) using the zCurr 
				
				zCurrA = currA; 
				zCurrB = currB;
				
				

				for(currIter = 0; currIter < maxIterations; currIter++)
				{
					if(Math.sqrt(zCurrA*zCurrA + zCurrB*zCurrB) >= 2)
						break;
					zTemp = zCurrA;
					zCurrA = zCurrA*zCurrA - zCurrB*zCurrB + currA;	//current squared plus orig point
					zCurrB = 2*zTemp*zCurrB + currB;
				}
				
				//turn test value into color and store iteration number
				
				iterations[h][w] = currIter;
				
				//color pixel (w,h)
				if( currIter == maxIterations )
					img.setRGB(w, h, 0);
				else
				{
					intCurrColor = colorChooser.getColor(currIter);
					img.setRGB(w,h,intCurrColor);
				}
				
				currA += deltaA;
			}
			currB -= deltaB;
			currA = topLeftA;
		}
		
		return img;
	}
	
	
	public void recenter(int deltaX, int deltaY)
	{
		centerA = centerA - (double)deltaX/width*scaleWidth;
		centerB = centerB + (double)deltaY/height*scaleHeight;
	}
	
	//zoom in x2 and recenter based on pixel parameters
	public void zoomIn(int cX, int cY)
	{
		centerA = centerA - 0.5*scaleWidth + (double)cX/width*scaleWidth;
		centerB = centerB + 0.5*scaleHeight - (double)cY/height*scaleHeight; 
		scaleWidth /= 2;
		scaleHeight /= 2;
	}
	
	//zoom out x2 and recenter based on pixel parameters
	public void zoomOut(int cX, int cY)
	{
		centerA = centerA - 0.5*scaleWidth + (double)cX/width*scaleWidth;
		centerB = centerB + 0.5*scaleHeight - (double)cY/height*scaleHeight;
		scaleWidth *= 2;
		scaleHeight *= 2;
	}
	
	public BufferedImage getImage()
	{
		return img;
	}
	
	public int[][] getIterationNumbers()
	{
		return iterations;
	}
	
	
		
		
	
	public FractalColorChooser getColorChooser()
	{
		return colorChooser;
	}
}
