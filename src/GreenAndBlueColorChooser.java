
public class GreenAndBlueColorChooser extends FractalColorChooser
{
	
	public int getColor( int iter )
	{
		if(iter%2 == 0)
			return ((3*iter)%156 + 100) * 65536 +
				((iter*1)%100 + 156) * 256 +
				0;
		else
			return ((3*iter)%156 + 100) * 65536 + 
				0 * 256 +
				(1*iter)%100 + 156;
	}
}
