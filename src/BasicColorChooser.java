
public class BasicColorChooser extends FractalColorChooser
{
	
	public int getColor( int iter )
	{
		return (10*iter)%256 * 65536 +
				Math.abs(256 - 7*iter)%256 * 256 +
				(8*iter)%256;
	}
}
