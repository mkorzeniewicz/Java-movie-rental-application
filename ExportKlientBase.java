import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExportKlientBase
{
	private String lokalizacjaPliku = "BazaKlientow.txt";
	private String StringDoZapisu = "TEST";
	public void zapiszPlik(ArrayList<String> klienci)
	{
		File plik = new File(lokalizacjaPliku);
		try {
			FileWriter zapisPliku = new FileWriter(plik);
			BufferedWriter buforowanyZapis = new BufferedWriter(zapisPliku);
			
			for(int i=0;i<klienci.size();i++)
			{
				buforowanyZapis.write(klienci.get(i));
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
