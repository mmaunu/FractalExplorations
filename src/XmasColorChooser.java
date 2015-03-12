
public class XmasColorChooser extends FractalColorChooser
{

	public int getColor(int iter)
	{
		if(iter%2 == 0)	//return red
			return ((19*iter)%256) * 65536 + iter%50;
		else	//return green
			return ((19*iter)%256) * 256 + iter%50;
	}

}


/*




		if(iter%2 == 0)	//return red
			return ((10*iter)%120 + 135) * 65536;
		else	//return green
			return ((10*iter)%120 + 135) * 256;







*/