import java.io.*;

public class RemoveClientFile{
	public void removeClientFileOfName(String nazwapliku)
	{
		File file = new File(nazwapliku + ".txt");
		if(file.delete())
		{
			//System.out.println("UDA£O SIÊ USUN¥Æ PLIK");
		}else
		{
			//System.out.println("NIEUDA£O SIÊ USUN¥Æ PLIKU!");
		}
	}
}
