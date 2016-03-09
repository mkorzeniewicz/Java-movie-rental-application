import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportNewClient
{
	private String lokalizacjaPliku;
	
	public void zapiszPlik(String klient)
	{
		lokalizacjaPliku = "Klienci\\" + klient + ".txt";
		File plik = new File(lokalizacjaPliku);
		try {
			FileWriter zapisPliku = new FileWriter(plik);
			BufferedWriter buforowanyZapis = new BufferedWriter(zapisPliku);
			
			buforowanyZapis.write(klient);
			
			buforowanyZapis.close();
			zapisPliku.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/*
 * Struktura pliku MKorzeniewicz.txt
 * login
 * Tytul 1 filmu
 * Rezyser 1 filmu
 * Gatunek 1 filmu
 * Tytul 2 filmu
 * ...
 * il kopii n filmu
 */
