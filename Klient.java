import java.util.ArrayList;

public class Klient 
{
	private String login;
	//private String nazwaPliku;
	private ArrayList<Film> BorrowList = new ArrayList<Film>(5);
	private int AmountOfBorrowedFilm = 0;
	
	public Klient()
	{
		
	}
	public Klient(String login)
	{
		this.login = login;
		//nazwaPliku = login; //z nazwy pliku nalezy usunac spacje, polskie znaki, znaki specjalne i  dodac ".txt"
	}
	
//---METODY
	//---------SETY
	public void setLogin(String login)
	{
		this.login = login;
	}
	
	//--------GETY
	public String getLogin()
	{
		return login;
	}
	public int getAmountOfBorrowedFilm()
	{
		return AmountOfBorrowedFilm;
	}
	public String getTitleOfFilm(int i)
	{
		return BorrowList.get(i).getTytul();
	}
	public String getRezyserOfFilm(int i)
	{
		return BorrowList.get(i).getRezyser();
	}
	public String getGatunekOfFilm(int i)
	{
		return BorrowList.get(i).getGatunek();
	}
	
	//---DODANIE-FILMU-DO-LISTY-WYPO¯YCZONYCH-PRZEZ-KLIENTA
	public void addFilmToBorrowList(Film film)
	{
		AmountOfBorrowedFilm++;
		BorrowList.add(film);
		
	}
	//--USUNIÊCIE-FILMU-Z-LISTY-WYPO¯YCZONYCH-PRZEZ-KLIENTA
	public void removeFilmToBorrowList(Film film)
	{
		for(int i = 0; i < BorrowList.size();i++){
			if(film.getTytul().equals(BorrowList.get(i).getTytul())){
				AmountOfBorrowedFilm--;
				BorrowList.remove(i);
				break;
			}
		}
	}
	
	//---NADPISANIE-FUNKCJI-TOSTRING
	@Override
	public String toString()
	{
		return login + " wypozyczyl " + AmountOfBorrowedFilm + " Filmow: \n" + BorrowList;
	}
	
}
