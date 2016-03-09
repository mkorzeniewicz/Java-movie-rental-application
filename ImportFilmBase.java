import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ImportFilmBase
{
	private final String lokalizacjaPliku = "BazaFilmow.txt";
	private ArrayList<Film> philmy = new ArrayList<Film>();
	
	public ArrayList<Film> importujBazeFilmow()
	{
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
					}
					philmy.add(new Film(
							templinia,
							buforowanyOdczyt.readLine(),
							buforowanyOdczyt.readLine(),
							buforowanyOdczyt.readLine()
							));
				}

			} catch (IOException e) {e.printStackTrace();}
			try {
				odczyt.close();
				if(philmy.size()==0)
				{
					return null;
				}else{
					return philmy;
				}
				
			} catch (IOException e) {e.printStackTrace();}
		} catch (FileNotFoundException e) {e.printStackTrace();}
		return null;
		
	}
}
