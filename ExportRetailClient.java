import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExportRetailClient
{
	private String lokalizacjaPliku;
	
	public void zapiszPlik(Klient klient)
	{
		lokalizacjaPliku = "Klienci\\" + klient.getLogin() + ".txt";
		File plik = new File(lokalizacjaPliku);
		try {
			FileWriter zapisPliku = new FileWriter(plik);
			BufferedWriter buforowanyZapis = new BufferedWriter(zapisPliku);
			
			buforowanyZapis.write(klient.getLogin());
			buforowanyZapis.newLine();
			
			for(int i=0;i<klient.getAmountOfBorrowedFilm();i++)
			{
				buforowanyZapis.write(klient.getTitleOfFilm(i));
				buforowanyZapis.newLine();
				buforowanyZapis.write(klient.getRezyserOfFilm(i));
				buforowanyZapis.newLine();
				buforowanyZapis.write(klient.getGatunekOfFilm(i));
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
 * Struktura pliku MKorzeniewicz.txt
 * login
 * Tytul 1 filmu
 * Rezyser 1 filmu
 * Gatunek 1 filmu
 * Tytul 2 filmu
 * ...
 * il kopii n filmu
 */
