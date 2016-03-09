import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 *	Struktura pliku Klient
 * Login = nazwa klienta
 * Tytu� 1 filmu
 * Re�yser 1 filmu
 * Gatunek 1 filmu
 * Tytu� 2 filmu
 * ...
 * Gatunek X filmu => X maksymalna ilos� wypo�yczonych filmow
 */

public class ImportRetailClient
{
	private String lokalizacjaPliku;

	public Klient otw�rzPlik(String login)
	{
		lokalizacjaPliku = "Klienci\\" + login + ".txt";
		Klient klient = new Klient();
		File plik = new File(lokalizacjaPliku);
		try {
			FileReader odczyt = new FileReader(plik);
			BufferedReader buforowanyOdczyt = new BufferedReader(odczyt);
			String templinia;
			try {
				klient.setLogin(buforowanyOdczyt.readLine());
				while(true){	
					templinia = buforowanyOdczyt.readLine();
					if(templinia == null){
						break;
					}else{
						klient.addFilmToBorrowList(new Film(
								templinia, 					 // TYTU�
								buforowanyOdczyt.readLine(), // RE�YSER
								buforowanyOdczyt.readLine() // GATUNEK
								));
					}
				}					
			} catch (IOException e) {e.printStackTrace();}
			try {
				odczyt.close();
				return klient;
			} catch (IOException e) {e.printStackTrace();}
		} catch (FileNotFoundException e) {e.printStackTrace();}
		return null;
		
	}
}


