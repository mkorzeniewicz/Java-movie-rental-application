import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExportFilmBase
{
	private String lokalizacjaPliku = "BazaFilmow.txt";
	public void zapiszPlik(ArrayList<Film> filmy)
	{
		File plik = new File(lokalizacjaPliku);
		try {
			FileWriter zapisPliku = new FileWriter(plik);
			BufferedWriter buforowanyZapis = new BufferedWriter(zapisPliku);
			
			for(int i=0;i<filmy.size();i++)
			{
				buforowanyZapis.write(filmy.get(i).getTytul());
				buforowanyZapis.newLine();
				buforowanyZapis.write(filmy.get(i).getRezyser());
				buforowanyZapis.newLine();
				buforowanyZapis.write(filmy.get(i).getGatunek());
				buforowanyZapis.newLine();
				buforowanyZapis.write(filmy.get(i).getLiczbaKopiiWString());
				buforowanyZapis.newLine();
			}
			
			
			buforowanyZapis.close();
			zapisPliku.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/*
 * Struktura pliku BazaFilmow.txt
 * Tytul 1 filmu
 * Rezyser 1 filmu
 * Gatunek 1 filmu
 * Il kopii 1 filmu
 * Tytul 2 filmu
 * ...
 * il kopii n filmu
 */
