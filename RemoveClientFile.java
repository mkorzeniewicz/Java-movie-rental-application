import java.io.*;

public class RemoveClientFile{
	public void removeClientFileOfName(String nazwapliku)
	{
		File file = new File(nazwapliku + ".txt");
		if(file.delete())
		{
			//System.out.println("UDA�O SI� USUN�� PLIK");
		}else
		{
			//System.out.println("NIEUDA�O SI� USUN�� PLIKU!");
		}
	}
}
