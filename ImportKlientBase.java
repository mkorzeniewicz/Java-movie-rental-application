	import java.io.BufferedReader;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
import java.util.ArrayList;

	public class ImportKlientBase
	{
		private final String lokalizacjaPliku = "BazaKlientow.txt";
	
		public ArrayList<String> otwórzPlik()
		{
			ArrayList<String> listaKlientow = new ArrayList<String>();
			
			File plik = new File(lokalizacjaPliku);
			try {
				FileReader odczyt = new FileReader(plik);
				BufferedReader buforowanyOdczyt = new BufferedReader(odczyt);
				String templinia;
				try {
					while(true)
					{
						templinia = buforowanyOdczyt.readLine();
						if(templinia == null){
							break;
						}else{
							listaKlientow.add(templinia);
						}
					}					
				} catch (IOException e) {e.printStackTrace();}
				try {
					odczyt.close();
					return listaKlientow;
				} catch (IOException e) {e.printStackTrace();}
			} catch (FileNotFoundException e) {e.printStackTrace();}
			return null;
			
		}
	}

