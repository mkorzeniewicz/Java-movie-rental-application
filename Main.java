import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

public class Main {

	private JFrame frame;
	private JTable tableFilm, tableKlient;	
	private JTextField tfIlKopii, tfGatunek, tfRezyser, tfTytul, tfLoginKlienta;
	private JComboBox cBKlienci;
	private ArrayList<Film> listaFilmow = new ArrayList<Film>();
	private ArrayList<String> listaKlientow = new ArrayList<String>();
	private Klient AktualnyKlient = new Klient();
	private JPopupMenu menu = new JPopupMenu();
	private JMenuItem infoMenu;

	/**
	 * maksymalna ilo�� film�w jak� mo�e wypo�yczy� klient
	 */
	private final int max = 5;
	

//-----------MAIN----------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		czynnosciPoczatkowe(); // IMPORT LISTY FILM�W I KLIENT�W Z PLIK�W
		startMain(); //MAIN PROGRAM	/ USTAWIANIE FRAME
		czynnosciDopelniajace(); //WYPE�NIANIE TABEL I COMBOBOXA
	}

//---------MAIN-PROGRAM-------
	private void startMain() {
		
		//---USTAWIANIE-OKNA---
		
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 563);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		//---USTAWIANIE-STYLU-OKNA---
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(frame);
		
		//--USTAWIANIE--INFORMACJI----
		
		
		
		//---------PANEL-AKTYWNO�CI-----------
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBounds(10, 11, 190, 512);
		frame.getContentPane().add(panelButtons);
		panelButtons.setLayout(null);
	
		JLabel lblKlient = new JLabel("Klient");
		lblKlient.setBounds(1, 11, 46, 14);
		panelButtons.add(lblKlient);
		
		cBKlienci = new JComboBox();
		cBKlienci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wczytajDaneKlienta(String.valueOf(cBKlienci.getSelectedItem()));
				refreshTableKlient();
		}});
		cBKlienci.setEditable(false);
		cBKlienci.setBounds(35, 8, 150, 20);
		panelButtons.add(cBKlienci);
		
		JTextArea taStatus = new JTextArea("Prawy klawisz po wi�cej informacji");
		taStatus.setBounds(5, 405, 179, 107);
		panelButtons.add(taStatus);
		taStatus.setLineWrap(true);
		taStatus.setWrapStyleWord(true);
		taStatus.setBorder(BorderFactory.createLineBorder(Color.black));
		taStatus.setEditable(false);
		
		JButton btnWypoycz = new JButton("Wypo\u017Cycz");
		btnWypoycz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int tempRow = tableFilm.getSelectedRow();	//TEMP ZMIENNA PRZECHOWYWUJ�CA ZAZNACZONY WIERSZ
				if(tempRow != -1){							//SPRAWDZANIE CZY ZAZNACZONY JEST JAKIKOLWIEK WIERSZ
					if(Integer.valueOf(String.valueOf((tableFilm.getValueAt(tempRow, 4))))>0){ //SPRAWDZANIE CZY WYBRANY FILM MA DOST�PNE KOPIE
						if(AktualnyKlient.getAmountOfBorrowedFilm()<max){ //SPRAWDZAWNIE CZY KLIENT NIEWYPO�YCZY� JU� MAKSYMALNEJ LICZBY FILM�W
							for(int i=0;i<listaFilmow.size();i++){	//PRZESZUKANIE CA�EJ LISTY FIML�W
								if(listaFilmow.get(i).equalsFilm(new Film(tableFilm.getValueAt(tempRow,1),tableFilm.getValueAt(tempRow,2),tableFilm.getValueAt(tempRow,3)))){
									listaFilmow.get(i).setLiczbaKopii(String.valueOf(listaFilmow.get(i).getLiczbaKopiiWInt()-1)); //ODJ�CIE JEDNEJ KOPII FILMU Z LISTY FILM�W
									AktualnyKlient.addFilmToBorrowList(listaFilmow.get(i)); // PRZEKAZANIE PASUJ�CEGO FILMU DO LISTY WYPO�YCZONYCH PRZEZ DANEGO KLIENTA
									break; // PRZERWANIE P�TLI PRZESZUKIWANIA LISTY FILM�W
								}	
							}										
							refreshTableKlient();
							odswiezTabeleFilmow(); 
							exportBazyFilmow();
							exportAktualnegoKlienta();
							taStatus.setText("Wypozyczone");
						}else{taStatus.setText("Maksymalna liczba filmow");}
					}else{taStatus.setText("Nie ma dostepnej kopii");}
				}else{taStatus.setText("Zaznacz film do wypo�yczenia");}
			}
		});
		btnWypoycz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					info("Wypo�ycza film danemu klientowi\n\n"
							+ "Warunki:\n"
							+ "-Musi by� zaznaczony film w tabeli film�w,\n"
							+ "-Musi by� wybrany klient,\n"
							+ "-Klient nie mo�e mie� wypo�yczonych " + max + " film�w.");
				}}});
		btnWypoycz.setBounds(3, 39, 182, 29);
		panelButtons.add(btnWypoycz);
		
		JButton btnOddaj = new JButton("Oddaj");
		btnOddaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tempRow = tableKlient.getSelectedRow();
				if(tempRow!=-1){
					for(int i=0;i<listaFilmow.size();i++){
						if(listaFilmow.get(i).getTytul().equals(tableKlient.getValueAt(tempRow, 1))){
							listaFilmow.get(i).setLiczbaKopii(String.valueOf(listaFilmow.get(i).getLiczbaKopiiWInt()+1)); 
							taStatus.setText("Oddano");
							AktualnyKlient.removeFilmToBorrowList(listaFilmow.get(i)); 
							break;
						}
					}
					refreshTableKlient();
					odswiezTabeleFilmow();
					exportBazyFilmow();
					exportAktualnegoKlienta();
				}else{taStatus.setText("Zaznacz film do zwrotu");}	
			}});
		btnOddaj.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					info("Zwraca wypo�yczony przez klienta film\n\n"
							+ "Warunki:\n"
							+ "-Musi by� zaznaczony film w tabeli klienta,\n"
							+ "-Klient musi mie� wypo�yczony film.\n");
				}}});
		btnOddaj.setBounds(3, 70, 182, 29);
		panelButtons.add(btnOddaj);
		
			//--------PANEL-TABELI-WYPOZYCZONYCH-FILM�W-DANEGO-KLIENTA------
			
			JScrollPane panelTabeliKlienta = new JScrollPane();
			panelTabeliKlienta.setBounds(3, 105, 182, 119);
			panelButtons.add(panelTabeliKlienta);
			
			DefaultTableModel modelTabeliKlienta = new DefaultTableModel(new Object[]{"NR","Tytul"}, 0)
			{
				@Override
			    public boolean isCellEditable(int i, int i1) 
			    {
			        return false; 
			    }
			};
			tableKlient = new JTable(modelTabeliKlienta);
			tableKlient.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableKlient.setDragEnabled(false);
			tableKlient.getColumnModel().getColumn(0).setPreferredWidth(30);
			tableKlient.getColumnModel().getColumn(1).setPreferredWidth(150);
			panelTabeliKlienta.setViewportView(tableKlient);
			
			tableKlient.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON3){
						info("Lista wypo�yczonych przez danego klienta film�w");
						}}});
			
			//-------KONIEC-PANELU-TABELI-WYPOZYCZONYCH-FILM�W-DANEGO-KLIENTA------
		
		//DODAWANIE NOWEGO FILMU
		JButton btnDodajFilm = new JButton("Dodaj Film");
		btnDodajFilm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean isFilmExised = false;
				
				if(tfTytul.getText().length()!=0){
					if(tfRezyser.getText().length()!=0){
						if(tfGatunek.getText().length()!=0){
							if(tfIlKopii.getText().length()!=0){
									for(int i=0;i<listaFilmow.size();i++){ // je�eli film jest ju� na li�cie
										if(listaFilmow.get(i).equalsFilm(new Film(tfTytul.getText(),tfRezyser.getText(),tfGatunek.getText()))){
											boolean bKopieLiczba = false;
											try {	//CZY PODANA ILO�� KOPII JEST LICZB�
												int temp = Integer.valueOf(tfIlKopii.getText());
												bKopieLiczba = true;
											} catch (NumberFormatException e) {
												taStatus.setText("Il.Kopii filmu musi by� liczb�");
												isFilmExised = true;
											}
											if(bKopieLiczba)
											{
												if(Integer.valueOf(tfIlKopii.getText())>0)
												{
													listaFilmow.get(i).setLiczbaKopii(listaFilmow.get(i).getLiczbaKopiiWInt()+Integer.valueOf(tfIlKopii.getText()));
													taStatus.setText("Dodano " + tfIlKopii.getText() + " kopii filmu"); //STATUS
													//CZYSZCZENIE TEXTFIELD�W
													tfTytul.setText("");
													tfRezyser.setText("");
													tfGatunek.setText("");
													tfIlKopii.setText("");
													//----------------------
													isFilmExised = true;
													exportBazyFilmow();
													break;
												}else
												{
													taStatus.setText("L.kopii musi by� wi�ksza od zera");
													isFilmExised = true;
												}
											}
										}
									}
									if(isFilmExised == false) // JE�ELI NIE MA NA LI�CIE FLIMU O PODANYCH PARAMETRACH
										{
										
											listaFilmow.add(new Film(tfTytul.getText(),tfRezyser.getText(),tfGatunek.getText(),tfIlKopii.getText())); // DODAJ NOWY FILM DO LISTY FILM�W
											//CZYSZCZENIE TEXTFIELD�W
											tfTytul.setText("");
											tfRezyser.setText("");
											tfGatunek.setText("");
											tfIlKopii.setText("");
											//----------------------
											taStatus.setText("Dodano nowy film");
											exportBazyFilmow();
										}
									odswiezTabeleFilmow();
							}else{taStatus.setText("Uzupe�nij ilo�� kopii filmu");}
						}else{taStatus.setText("Uzupe�nij Gatunek filmu");}
					}else{taStatus.setText("Uzupe�nij Rezysera filmu");}
				}else{taStatus.setText("Uzupe�nij Tytul filmu");}
			}
		});
		btnDodajFilm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					info("Tworzy nowy film w bazie film�w\n\n"
							+ "Warunki:\n"
							+ "-Musz� by� wprowadzone dane nowego filmu w polach na dole okna,\n"
							+ "-Je�eli film instnieje zostan� dodane kolejne kopie.\n");
				}}});
		btnDodajFilm.setBounds(3, 233, 182, 29);
		panelButtons.add(btnDodajFilm);
		
		//USUWANIE ZAZNACZONEGO FILMU
		JButton btnUsuFilm = new JButton("Usu\u0144 Film");
		btnUsuFilm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int tempRow = tableFilm.getSelectedRow();
				if(tempRow != -1){
					for(int i=0;i<listaFilmow.size();i++){
						if(listaFilmow.get(i).equalsFilm(new Film(tableFilm.getValueAt(tempRow,1),tableFilm.getValueAt(tempRow,2),tableFilm.getValueAt(tempRow,3)))){
							listaFilmow.remove(i);
							taStatus.setText("Usuni�to film");
							odswiezTabeleFilmow();
							exportBazyFilmow();
							break;
						}
					}
				}else{taStatus.setText("Zaznacz film");}
			}});
		btnUsuFilm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					info("Usuwa zaznaczony film z bazy film�w\n\n"
							+ "Warunki:\n"
							+ "-Musz� by� zaznaczony film w bazie film�w,\n"
							+ "-Future - Film musi by� zwr�cony od wszystkich klient�w");
				}}});
		btnUsuFilm.setBounds(3, 263, 182, 29);
		panelButtons.add(btnUsuFilm);
			
		//DODAWNIE NOWEGO KLIENTA
		JButton btnDodajKlienta = new JButton("Dodaj Klienta");
		btnDodajKlienta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nazwaKlienta = tfLoginKlienta.getText(); 
				if(nazwaKlienta.length()!=0){	//CZY POLE NAZWY KLIENTA NIE JEST PUSTE
					boolean isKlientExist = false;	//ZMIENNA POMOCNICZA OKRE�LAJ�CA CZY ISTNIEJE JU� KLIENT O PODANYM LOGINIE
					for(int i=0;i<listaKlientow.size();i++){ // SPRAWDZANIE Z LIST� KLIENT�W CZY NIE MA JU� KLIETNA O PODANYM LOGINIE
						if(listaKlientow.get(i).equals(nazwaKlienta)) 
							isKlientExist=true;
					}
					if(!isKlientExist){ // JE�ELI KLIENT O PODANYM LOGINIE NIE ISTNIEJE
						listaKlientow.add(nazwaKlienta); // DODANIE DO LISTY KLIENTOW NOWEGO KLIENTA
						exportNowegoKlienta(nazwaKlienta); //STWORZENIE PLIKU TXT NOWEGO KLIENTA
						cBKlienci.addItem(nazwaKlienta); //DODANIE DO COMBOBOXA NOWEGO KLIENTA
						exportBazyKlientow(); //ZAPISANIE LISTY KLIENTOW
					}else{taStatus.setText("Istnieje klient o tej nazwie");}
				}else{taStatus.setText("Wprowadz nazw� klienta");}
			}
		});
		btnDodajKlienta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					info("Tworzy nowego klienta w bazie\n\n"
							+ "Warunki:\n"
							+ "-Musi by� wpisany login w polu \"Klient\",\n"
							+ "-Nazwa Klienta nie powinna zawiera� spacji i znak�w specjalnych,\n"
							+ "-Niemo�e istnie� klient o takiej samej nazwie.");
				}}});
		btnDodajKlienta.setBounds(3, 302, 182, 29);
		panelButtons.add(btnDodajKlienta);
		
		//USUWANIE BIEZACEGO KLIENTA
		JButton btnUsuKlienta = new JButton("Usu\u0144 Klienta");
		btnUsuKlienta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(AktualnyKlient.getAmountOfBorrowedFilm()==0){ // NIE MO�NA USUN�� KLIENTA KT�RY NIE ODDA� WSZYSTKICH FILM�W
					String tempLogin = AktualnyKlient.getLogin();
					usunieciePlikuKlienta(tempLogin);
					listaKlientow.remove(tempLogin);
					cBKlienci.removeItem(tempLogin);
					exportBazyKlientow();
				}else{taStatus.setText("Klient musi zwrocic filmy");}	
			}
		});
		btnUsuKlienta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					info("Usuwa klienta z bazy\n\n"
							+ "Warunki:\n"
							+ "-Musi by� wpisany login w polu \"Klient\",\n"
							+ "-Nazwa Klienta nie powinna zawiera� spacji i znak�w specjalnych,\n"
							+ "-Niemo�e istnie� klient o takiej samej nazwie.");
				}}});
		btnUsuKlienta.setBounds(3, 333, 182, 29);
		panelButtons.add(btnUsuKlienta);
		
		//WYSZUKIWANIE KLIENTA PO LOGINIE/POBIERANIE LOGINU NOWEGO KLIENTA
		JLabel lblLoginKlienta = new JLabel("Login Klienta");
		lblLoginKlienta.setBounds(1, 380, 83, 14);
		panelButtons.add(lblLoginKlienta);
		
		tfLoginKlienta = new JTextField();
		tfLoginKlienta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<listaKlientow.size();i++){
					if(tfLoginKlienta.getText().equals(listaKlientow.get(i))){	//WYSZUKANIE KLIENTA O PODANEJ NAZWIE PO NACI�NI�CIU ENTER
						cBKlienci.setSelectedIndex(i);
						wczytajDaneKlienta(tfLoginKlienta.getText());
						refreshTableKlient();
					}
				}
			}});
		tfLoginKlienta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					info("Pole wyszukiwania klienta po nazwie\n\n"
							+ "-Aby wyszuka� wpisz nazw� klienta i wci�nij ENTER,\n"
							+ "-Aby dopisa� do bazy nowego klienta wpisz nazw� i naci�nij \"Dodaj Klienta\".");
				}}});
		tfLoginKlienta.setBounds(73, 375, 112, 25);
		panelButtons.add(tfLoginKlienta);
		tfLoginKlienta.setColumns(10);
		
		//------PANEL-DODAWANIA-NOWEGO-FILMU------	
		
		JPanel panelDanychFilmu = new JPanel();
		panelDanychFilmu.setLayout(null);
		panelDanychFilmu.setBounds(210, 487, 564, 36);
		frame.getContentPane().add(panelDanychFilmu);
		
		JLabel lblTytul = new JLabel("Tytu\u0142:");
		lblTytul.setBounds(6, 6, 46, 14);
		panelDanychFilmu.add(lblTytul);
		
		JLabel lblRezyser = new JLabel("Re\u017Cyser:");
		lblRezyser.setBounds(168, 7, 54, 14);
		panelDanychFilmu.add(lblRezyser);
		
		JLabel lblGatunek = new JLabel("Gatunek:");
		lblGatunek.setBounds(331, 6, 54, 14);
		panelDanychFilmu.add(lblGatunek);
		
		JLabel lblIlKopii = new JLabel("Il.Kopii:");
		lblIlKopii.setBounds(476, 6, 41, 14);
		panelDanychFilmu.add(lblIlKopii);
	    
		tfIlKopii = new JTextField();
		tfIlKopii.setBounds(516, 0, 48, 25);
		panelDanychFilmu.add(tfIlKopii);
		
		tfGatunek = new JTextField();
		tfGatunek.setColumns(10);
		tfGatunek.setBounds(378, 0, 86, 25);
		panelDanychFilmu.add(tfGatunek);
		
		tfRezyser = new JTextField();
		tfRezyser.setBounds(214, 0, 107, 25);
		panelDanychFilmu.add(tfRezyser);
		
		tfTytul = new JTextField();
		tfTytul.setBounds(36, 0, 122, 25);
		panelDanychFilmu.add(tfTytul);
		
		//------KONIEC-PANELU-DODAWANIA-NOWEGO-FILMU------	

		//---------------PANEL-TABELI-FILM�W-------
		
		JScrollPane panelTabeli = new JScrollPane();
		panelTabeli.setBounds(210, 11, 564, 471);
		frame.getContentPane().add(panelTabeli);
		
		DefaultTableModel modelTabeliFilmu = new DefaultTableModel(new Object[]{"NR","Tytu�","Re�yser","Gatuenk","Il.Kopii"},0)
		{
			@Override
		    public boolean isCellEditable(int i, int i1) 
		    {
		        return false; 
		    }
		};
		tableFilm = new JTable(modelTabeliFilmu);
		panelTabeli.setViewportView(tableFilm);
		
		tableFilm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFilm.setDragEnabled(false);
		tableFilm.setDragEnabled(false);
		tableFilm.setAutoCreateRowSorter(true);
		
		tableFilm.getColumnModel().getColumn(0).setPreferredWidth(20); // Szeroko�� kolumny pt. NR
		tableFilm.getColumnModel().getColumn(1).setPreferredWidth(200); // Szeroko�� kolumny pt. Tytu�
		tableFilm.getColumnModel().getColumn(2).setPreferredWidth(150); // Szeroko�� kolumny pt. Re�yser
		tableFilm.getColumnModel().getColumn(3).setPreferredWidth(100); // Szeroko�� kolumny pt. Gatunek
		tableFilm.getColumnModel().getColumn(4).setPreferredWidth(50); // Szeroko�� kolumny pt. Il.Kopii
		tableFilm.getTableHeader().setReorderingAllowed(false);
		
		tableFilm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					info("Tabela przedstawiaj�ca baz� film�w wypo�yczalni.");
				}}});
		
		//---------KONIEC-PANELU-TABELI-FILM�W-------
	}
//---------END-OF-THE-MAIN-PROGRAM-------
	

	public void odswiezTabeleFilmow()
	{
		DefaultTableModel modelTabeliFilmow = (DefaultTableModel) tableFilm.getModel(); 
		
		while(true){	//USUNI�CIE WSZYSTKICH WIERSZY TABELI FLIM�W
			if(modelTabeliFilmow.getRowCount()!=0){
				modelTabeliFilmow.removeRow(0);
			}else{break;}
		}

		for(int i = 0; i <listaFilmow.size();i++){	//DODANIE WSZYSTKICH WIERSZY TABELI FILM�W
			modelTabeliFilmow.addRow(new Object[]{
							i+1,
							listaFilmow.get(i).getTytul(),
							listaFilmow.get(i).getRezyser(),
							listaFilmow.get(i).getGatunek(),
							listaFilmow.get(i).getLiczbaKopiiWInt()
												});
		}	
	}
	
	public void refreshTableKlient()
	{
		DefaultTableModel tableKlientModel = (DefaultTableModel) tableKlient.getModel(); 

		while(true){	//USUNI�CIE WSZYSTKICH WIERSZY TABELI KLIENTA
		
			if(tableKlientModel.getRowCount()!=0){
			   tableKlientModel.removeRow(0);
			}else{break;}
		}
		
		for(int i = 0; i <AktualnyKlient.getAmountOfBorrowedFilm();i++){	//DODANIE WSZYSTKICH WIERSZY TABELI KLIENTA
			tableKlientModel.addRow(new Object[]{i+1,AktualnyKlient.getTitleOfFilm(i)});
		}
	}
		
	public void wczytajDaneKlienta(String login)
	{
		ImportRetailClient importuj = new ImportRetailClient();
		AktualnyKlient = importuj.otw�rzPlik(String.valueOf(login));
	}
	public void exportBazyFilmow()
	{
		ExportFilmBase export = new ExportFilmBase();
		export.zapiszPlik(listaFilmow);
	}
	public void exportBazyKlientow()
	{
		ExportKlientBase export = new ExportKlientBase();
		export.zapiszPlik(listaKlientow);
	}
	public void exportNowegoKlienta(String nazwa)
	{
		ExportNewClient export = new ExportNewClient();
		export.zapiszPlik(nazwa);
	}
	public void exportAktualnegoKlienta()
	{
		ExportRetailClient export = new ExportRetailClient();
		export.zapiszPlik(AktualnyKlient);
	}
	public void usunieciePlikuKlienta(String loginKlienta)
	{
		RemoveClientFile klasaUsuniecia = new RemoveClientFile();
		klasaUsuniecia.removeClientFileOfName(loginKlienta);
	}
	private void czynnosciPoczatkowe()
	{
		ImportFilmBase importujF = new ImportFilmBase();	// IMPORT LISTY FILM�W Z PLIKU
		listaFilmow = importujF.importujBazeFilmow();	
		
		ImportKlientBase importujK = new ImportKlientBase();  // IMPORT LISTY KLIENT�W Z PLIKU
		listaKlientow = importujK.otw�rzPlik();
	}
	private void czynnosciDopelniajace()
	{
		odswiezTabeleFilmow();
		
		for(int i=0;i<listaKlientow.size();i++)//WYPE�NIANIE COMBOBOXA KLIENTAMI
		{
			cBKlienci.addItem(listaKlientow.get(i));
		}
	}
	//----METODA-WYRZUCAJ�CA-INFORMACJE-O-PRZYCISKU
	private void info(String tekst)
	{
		JOptionPane.showMessageDialog(frame, tekst);
	}
}

