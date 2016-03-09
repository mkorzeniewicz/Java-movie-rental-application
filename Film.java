
public class Film extends Object 
{
	private String Tytul;
	private String Rezyser;
	private String Gatunek;
	private int liczbaKopii;
	
//--------Konstruktory

	public Film(String Tytul,String Rezyser,String Gatunek)
	{
		this.Tytul = Tytul;
		this.Rezyser = Rezyser;
		this.Gatunek = Gatunek;
		this.liczbaKopii = 1;
	}

	public Film(Object Tytul,Object Rezyser,Object Gatunek)
	{
		this(String.valueOf(Tytul),String.valueOf(Rezyser),String.valueOf(Gatunek));
	}
	
	public Film(String Tytul,String Rezyser,String Gatunek,int  intLiczbaKopii)
	{
		this(Tytul, Rezyser, Gatunek,String.valueOf(intLiczbaKopii));
	}
	
	public Film(String Tytul,String Rezyser,String Gatunek,String  strLiczbaKopii)
	{
		this(Tytul,Rezyser,Gatunek);
		liczbaKopii = Integer.valueOf(strLiczbaKopii);
	}
//---METODY------
	
	//---SETY--------
	
	public void setTytul(String strTytul)
	{
		Tytul = strTytul;
	}
	public void setRezyser(String strRezyser)
	{
		Rezyser = strRezyser;
	}
	public void setGatunek(String strGatunek)
	{
		Gatunek = strGatunek;
	}
	public void setLiczbaKopii(int intLiczbaKopii)
	{
		liczbaKopii = intLiczbaKopii;
	}
	public void setLiczbaKopii(String intLiczbaKopii)
	{
		liczbaKopii = Integer.valueOf(intLiczbaKopii);
	}
	
	//-------GETY-----------
	
	public String getTytul()
	{
		return Tytul;
	}
	public String getRezyser()
	{
		return Rezyser;
	}
	public String getGatunek()
	{
		return Gatunek;
	}
	public int getLiczbaKopiiWInt()
	{
		return liczbaKopii;
	}
	public String getLiczbaKopiiWString()
	{
		return String.valueOf(liczbaKopii);
	}
	//---PORÓWNYWANIE-DWÓCH-FILMÓW---
	public boolean equalsFilm(Film film)
	{
		return Tytul.equals(film.getTytul()) && Rezyser.equals(film.getRezyser()) && Gatunek.equals(film.getGatunek());
	}
	
}
