package ASL.is.boundary;
import ASL.is.entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.google.zxing.WriterException;



public class BUtente extends BMain{
	
	public static void registraUtente() throws IOException {
		String nome, cognome, CF, luogoNascita, username, password = null, pass2, residenza, numTel, email;
		LocalDate dataNascita = null;
		int isee;
		GradoDisabilita dis = null;
		boolean ok = false;
		
		BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		System.out.print(" *** PROCEDURA DI REGISTRAZIONE ***\n");
		System.out.print("Scegli un username (ti servira'� per accedere): \n>");
		username = inputReader.readLine();
		
		while(ok == false) {
			System.out.print("Inserisci la password: \n>");
			password = inputReader.readLine();
			
			System.out.print("Inserisci nuovamente la password: \n>");
			pass2 = inputReader.readLine();
			
			if(password.equals(pass2)) {
				ok = true;}
			else {System.out.print("Le due password non combaciano. Riprova\n>");}
		}
		
		System.out.print("Inserisci il tuo nome: \n>");
		nome = inputReader.readLine();
		
		System.out.print("Inserisci il tuo cognome: \n>");
		cognome = inputReader.readLine();
		
		System.out.print("Inserisci il tuo codice fiscale: \n>");
		CF = inputReader.readLine();
		
		
		//Data nascita
		String giorno = null,mese = null,anno;
		String input;
		
		System.out.print("Inserirsi la tua data di nascita: [In numeri (Formato: dd/mm/yyyy)] \n");
		
		boolean datavalida = true;
		
		while(datavalida == true) {
		System.out.print("Giorno:\n>");
		
	    boolean giornovalido = true;
	    while(giornovalido == true) {
		input = (inputReader.readLine());
		giorno = input;
		int num = Integer.parseInt(giorno);
		if(num < 1 || num > 31) {
			System.out.println("Giorno inserito non valido. Inserire nuovamente il giorno\n>");	
		}else
			giornovalido = false;
		}
	    
		if(String.valueOf(giorno).length() < 2) {
			giorno = "0"+ giorno;
		}
		
		
		if(String.valueOf(giorno).length() < 2) {
			giorno = "0"+ giorno;
		}
		
		boolean mesevalido = true;
		while(mesevalido == true) {
		System.out.print("Mese:\n>");
		input = (inputReader.readLine());
		mese = input;
		int num1 = Integer.parseInt(mese);
		if(num1 < 1 || num1 > 12) {
			System.out.println("Giorno inserito non valido. Inserire nuovamente il giorno\n>");	
		}else
			mesevalido = false;
		}
		
		if(String.valueOf(mese).length() < 2) {
			mese = "0"+ mese;
		}
		
		
		System.out.print("Anno:\n>");
		input = (inputReader.readLine());
		anno = input;
		if(String.valueOf(anno).length() < 3) {
			anno = "20"+ anno;
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String date = giorno+"/"+ mese + "/" + anno;

	    dataNascita = LocalDate.parse(date, formatter);
	    
	    if(dataNascita.compareTo(LocalDate.now()) > 0 ){
	    	System.out.println("Data di nascita inserita successiva alla data odierna. Si prega di reinserire il valore\n");
	    }else {
	    datavalida = false;
	    System.out.print("\nHai inserito: " + date +"\n");	
	    
		}
		}
	   
		
		
		System.out.print("Inserisci il tuo luogo di nascita: \n>");
		luogoNascita = inputReader.readLine();
		
		System.out.print("Inserisci il tuo luogo di residenza: \n>");
		residenza = inputReader.readLine();
		
		System.out.print("Inserisci il tuo numero di telefono: \n>");
		numTel = inputReader.readLine();
		
		System.out.print("Inserisci il tuo indirizzo e-mail valido: \n>");
		email = inputReader.readLine();
		
		System.out.print("Inserisci valore della tua attestazione ISEE (senza decimali) [int]: \n>");
		isee = Integer.parseInt(inputReader.readLine());
		System.out.print("[Attenzione] Ricordati di aggiornare il valore del tuo ISEE ogni anno attraverso la sezione Modifica utente \n");
		
		try {
			int option = 0;
		
				System.out.println("Hai qualche disabilita'�? \n" +
						 
						"\t1) Nessuna \n" +
						"\t2) Grado disabilita'� LIEVE \n"+
						"\t3) Grado disabilita'� MODERATA \n"+
						"\t4) Grado disabilita'� GRAVE \n");
	
				System.out.flush();

				try {
					option = Integer.parseInt(inputReader.readLine());
				} catch (NumberFormatException e) {
					option = 0;
				}

				switch (option) {
				case 1: { dis = GradoDisabilita.Nessuna; break; }
				case 2: { dis = GradoDisabilita.Lieve; break; }
				case 3: { dis = GradoDisabilita.Moderata; break; }
				case 4: { dis = GradoDisabilita.Grave; break; }

				default: {
					System.out.println("Carattere inserito non riconosciuto!\n");
				}
				}
	
		} catch (IOException e) {
			System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
			e.printStackTrace();
		}
	
		
			Utente utente = new Utente(nome, cognome, CF, username, password, dataNascita, luogoNascita, residenza, numTel, email, isee, dis);
		 
			if(gestoreASL.registraUtente(utente))
				System.out.println("Hai completato correttamente la procedura di registrazione! \nSi prega di effettuare il login.\n");
			else {
				System.out.println("Errore nella procedura di registrazione!");
				System.out.println("I dati potrebbero essere inseriti scorrettamente");
				System.out.println("La mail e/o l'username potrebbero essere gi� in uso");
				System.out.println("Il codice fiscale potrebbe gi� essere presente");
			}
	}
	
	
	static protected Prenotazione ricercaPerMedico() throws IOException, WriterException {
		  
		   
		  BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		  ArrayList<Prenotazione> dateDisponibili = new ArrayList<Prenotazione>();
		  ArrayList<Medico> medici = new ArrayList<Medico>();
		  String ricerca = null;
		  LocalDate dataInizioRicerca = null, dataFineRicerca= null;
		 
		 
		  int val = -1;
		  
		  int countmedico = 0;
		 

		    System.out.print("Inserire il cognome del medico da ricercare: \n>");
		    ricerca = inputReader.readLine();
		    
		    medici = gestoreASL.ottieniMediciDaCognome(ricerca);
		    for(int i = 0; i < medici.size(); i++) {
		    System.out.print(i+1 + "." + medici.get(i).getNome() + " " + medici.get(i).getCognome() +
		      "; Reparto: " + medici.get(i).getSpecializzazione().toStringa() +
		      "     (Username: " + medici.get(i).getUsername()+ ")\n");
		    }
		    
		    System.out.print("\nInserire il numero corrispondente al medico che si vuole selezionare: \n>");
		    int scelta;
		    boolean ok = false;
		   
		    
		    while(ok == false && countmedico < 4) {
		     try{
		      scelta = Integer.parseInt(inputReader.readLine());
		      scelta = scelta - 1;
		     }
		     catch (NumberFormatException e){
		      System.out.print("Input non riconosciuto. Riprova: \n>");
		      scelta = -1;
		     }
		     if(scelta >= 0 && scelta < medici.size()) {
		     System.out.print("\nHai scelto il medico: " + medici.get(scelta).getNome() + " " + medici.get(scelta).getCognome() + "\n");
		      ricerca = medici.get(scelta).getUsername();
		      ok = true;
		      countmedico = 4;
		     }else System.out.print("Input non riconosciuto/non valido. Riprova: \n>");
		      countmedico++;
		    }
		    
		    
		    if(ok == true) {
		    System.out.print("\nInserisci il range in cui cercare date disponibili\n>");
		    
		  //Data inizio ricerca
			String giorno = null, mese = null, anno;
			
			boolean datavalida1 = false;
			
			while(datavalida1 == false) {
			
			System.out.print("Inserirsi la data di inizio ricerca: [In numeri (Formato: dd/mm/yyyy)] \n");
			System.out.print("Giorno:\n>");
			
		
			String input;
			boolean giornovalido = true;
			while(giornovalido == true) {
			input = (inputReader.readLine());
			giorno = input;
			int num = Integer.parseInt(input);
			if(num < 1 || num > 31) {
				System.out.println("Giorno inserito non valido. Inserire nuovamente il giorno\n>");	
			}else
				giornovalido = false;
			}
			
			if(String.valueOf(giorno).length() < 2) {
				giorno = "0"+ giorno;
			}
			
			System.out.print("Mese:\n>");
			
			boolean mesevalido = true;
			while(mesevalido == true) {
			input = (inputReader.readLine());
			mese = input;
			int num1 = Integer.parseInt(input);
			if(num1 < 1 || num1 > 12) {
				System.out.println("Mese inserito non valido. Inserire nuovamente il mese\n>");	
			}else
				mesevalido = false;
			}
			
			if(String.valueOf(mese).length() < 2) {
				mese = "0"+ mese;
			}
			
			System.out.print("Anno:\n>");
			input = (inputReader.readLine());
			anno = input;
			if(String.valueOf(anno).length() < 3) {
				anno = "20"+ anno;
			}
			int num2 = Integer.parseInt(anno);
			if(num2 - LocalDate.now().getYear() > 2) {
				System.out.println("Non sono presenti visite a partire da quest'anno. Non puoi cercare oltre il 2023\n");
				anno = "2023";
			}
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    String date = giorno+"/"+ mese + "/" + anno;


		    dataInizioRicerca = LocalDate.parse(date, formatter);		    

		   
			 
			System.out.print("\nHai inserito: " + date +"\n");	
			
		    if(dataInizioRicerca.compareTo(LocalDate.now()) < 0) {
		    	System.out.println("La data inserita è precedente alla data odierna, si prega di inserire nuovamente i valori\n");
		    }else
			datavalida1 = true;
			//
			}
			
			boolean datavalida2 = false;
			
			
			//Data fine ricerca
			
			while(datavalida2 == false) {
			
			System.out.print("Inserirsi la data di fine ricerca: [In numeri (Formato: dd/mm/yyyy)] \n");
			System.out.print("Giorno:\n>");
			
			String input1;
			boolean giornovalido1 = true;
			while(giornovalido1 == true) {
			input1 = (inputReader.readLine());
			giorno = input1;
			int num = Integer.parseInt(giorno);
			if(num < 1 || num > 31) {
				System.out.println("Giorno inserito non valido. Inserire nuovamente il giorno\n>");	
			}else
				giornovalido1 = false;
			}
			
			if(String.valueOf(giorno).length() < 2) {
				giorno = "0"+ giorno;
			}
			
			System.out.print("Mese:\n>");
			boolean mesevalido1 = true;
			while(mesevalido1 == true) {
			input1 = (inputReader.readLine());
			mese = input1;
			int num1 = Integer.parseInt(mese);
			if(num1 < 1 || num1 > 12) {
				System.out.println("Mese inserito non valido. Inserire nuovamente il mese\n>");	
			}else
				mesevalido1 = false;
			
			}
			
			
			if(String.valueOf(mese).length() < 2) {
				mese = "0"+ mese;
			}
			
			System.out.print("Anno:\n>");
			input1 = (inputReader.readLine());
			anno = input1;
			if(String.valueOf(anno).length() < 3) {
				anno = "20"+ anno;
			}
			int num2 = Integer.parseInt(anno);
			if(num2 - LocalDate.now().getYear() > 2) {
				System.out.println("Non sono presenti visite a partire da quest'anno. Non puoi cercare oltre il 2023\n");
				anno = "2023";
			}
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    String date2 = giorno+"/"+ mese + "/" + anno;
		    dataFineRicerca = LocalDate.parse(date2, formatter);
		  
			System.out.print("\nHai inserito: " + date2 +"\n");	  
			//
		    if(dataFineRicerca.compareTo(LocalDate.now()) < 0) {
		    	System.out.println("La data inserita è precedente alla data odierna, si prega di inserire nuovamente i valori\n");
		    }else if(dataFineRicerca.compareTo(dataInizioRicerca) < 0) {
		    	System.out.println("La data di Fine Ricerca inserita è precedente alla data di Inizio Ricerca inserita, si prega di inserire nuovamente i valori\n");
		    }
			datavalida2 = true;
			
			}
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    dateDisponibili = gestoreASL.ricercaPerMedico(ricerca,dataInizioRicerca, dataFineRicerca);
		    
		    boolean date_disponibili = false;

		    if (dateDisponibili.size() != 0)
		    	date_disponibili = true;
		    else
		    	System.out.println("Non sono presenti date disponibili per questa specializzazione");
		    
		    if(date_disponibili == true) {
		    System.out.print("\nLe date disponibili sono le seguenti: \n");
		    for(int i = 0; i < dateDisponibili.size();i++) {
		   
		     System.out.print(i+1 +". "+dateDisponibili.get(i).getOrarioPrenotato().getGiornoSettimana().toString()+" ");
		     System.out.print(dateDisponibili.get(i).getData().format(formatter) + ":   ");
		     System.out.print(dateDisponibili.get(i).getOrarioPrenotato().getOraInizio() + ":");
		     System.out.print(dateDisponibili.get(i).getOrarioPrenotato().getMinutiInizio() + "   - Dott. ");
		     System.out.print(dateDisponibili.get(i).getOrarioPrenotato().getMedico().getCognome() +
		       " (�" + dateDisponibili.get(i).getOrarioPrenotato().getMedico().getSpecializzazione().getPrezzo() + ")" 
		       +" "+ dateDisponibili.get(i).getOrarioPrenotato().getMedico().getSpecializzazione().toStringa() + "\n");
		    }
		    
		   
		    while(val >= dateDisponibili.size() || val < 0) {
		    try {
		     System.out.print("\nInserire il numero che corrisponde alla visita che si vuole prenotare: \n>");
		     val = Integer.parseInt(inputReader.readLine()) - 1;
		    } catch (NumberFormatException e) {
		     System.out.print("Carattere inserito non riconosciuto. Inserire un numero valido. \n");
		     val = -1;
		    }
		    }
		    
		    System.out.print("\nHai scelto di prenotare:  \n");
		    
		    System.out.print(dateDisponibili.get(val).getOrarioPrenotato().getGiornoSettimana().toString()+" ");
		    System.out.print(dateDisponibili.get(val).getData().format(formatter) + ":   ");
		    System.out.print(dateDisponibili.get(val).getOrarioPrenotato().getOraInizio() + ":");
		    System.out.print(dateDisponibili.get(val).getOrarioPrenotato().getMinutiInizio() + "   - Dott. ");
		    System.out.print(dateDisponibili.get(val).getOrarioPrenotato().getMedico().getCognome() +
		      " (�" + dateDisponibili.get(val).getOrarioPrenotato().getMedico().getSpecializzazione().getPrezzo() + ")" 
		      +" - "+ dateDisponibili.get(val).getOrarioPrenotato().getMedico().getSpecializzazione().toStringa() + "\n");
		    }
		 
		  
		    }
            try {
			return dateDisponibili.get(val);
            }catch(IndexOutOfBoundsException e) {
            	System.err.println("Non sono presenti visite\n");
            	Prenotazione prenotazione = null;
            	return prenotazione;
            }
		   
		 }
	
	 static protected Prenotazione ricercaPerSpecializzazione() throws IOException {
			
		 
			BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			ArrayList<Prenotazione> dateDisponibili = new ArrayList<Prenotazione>();
			Specializzazione ricerca = null;
			LocalDate dataInizioRicerca = null, dataFineRicerca = null;
			int val = -1; 

			
			System.out.print("Inserire l'username del medico da ricercare: \n>");
		
			try {
				int option = 0;
			
					System.out.println("Scegli la specializzazione per la quale vuoi prenotare una visita: \n" +
							 
							"\t1)  Allergologia e immunologia \n" +
							"\t2)  Anestesia \n"+
							"\t3)  Audiologia e foniatria \n"+
							"\t4)  Cardiologia \n"+
							"\t5)  Dermatologia e veneorologia \n" +
							"\t6)  Ematologia \n" + 
							"\t7)  Endocrinologia \n" +
							"\t8)  Farmacologia e tossicologia clinica \n" + 
							"\t9)  Gastroenterologia \n " +
							"\t10) Geriatria \n" +
							"\t11) Ginecologia \n" +
							"\t12) Igiene e medicina preventiva \n"+
							"\t13) Medicina legale \n"+
							"\t14) Malattie infettive \n"+
							"\t15) Medicina del lavoro \n"+
							"\t16) Medicina fisica e riabilitativa \n"+
							"\t17) Medicina nucleare \n"+
							"\t18) Medico di base \n"+
							"\t19) Nefrologia \n"+
							"\t20) Neurologia \n"+
							"\t21) Neuropsichiatria infantile \n"+
							"\t22) Oftalmologia \n"+
							"\t23) Oncologia \n"+
							"\t24) Ortopedia \n"+
							"\t25) Otorinolaringoiatria \n"+
							"\t26) Pediatria \n"+
							"\t27) Pneumologia \n"+
							"\t28) Psichiatria \n"+
							"\t29) Radiodiagnostica \n"+
							"\t30) Radioterapia \n"+
							"\t31) Reumatologia \n"+
							"\t32) Statistica sanitaria e biometria \n"+
							"\t33) Urologia \n");
				
		
					System.out.flush();

					try {
						option = Integer.parseInt(inputReader.readLine());
					} catch (NumberFormatException e) {
						option = 0;
					}

					switch (option) {
					case 1: { ricerca = Specializzazione.allergologia_e_immunologia; break; }
					case 2: { ricerca = Specializzazione.anestesia; break; }
					case 3: { ricerca = Specializzazione.audiologia_e_foniatria; break; }
					case 4: { ricerca = Specializzazione.cardiologia; break; }
					case 5: { ricerca = Specializzazione.dermatologia_e_veneorologia; break; }
					case 6: { ricerca = Specializzazione.ematologia; break; }
					case 7: { ricerca = Specializzazione.endocrinologia; break; }
					case 8: { ricerca = Specializzazione.farmacologia_e_tossicologia_clinica; break; }
					case 9: { ricerca = Specializzazione.gastroenterologia; break; }	
					case 10: { ricerca = Specializzazione.geriatria; break; }
					case 11: { ricerca = Specializzazione.ginecologia; break; }
					case 12: { ricerca = Specializzazione.igiene_e_medicina_preventiva; break; }
					case 13: { ricerca = Specializzazione.madicina_legale; break; }
					case 14: { ricerca = Specializzazione.malattie_infettive; break; }
					case 15: { ricerca = Specializzazione.medicina_del_lavoro; break; }
					case 16: { ricerca = Specializzazione.medicina_fisica_e_riabilitativa; break; }
					case 17: { ricerca = Specializzazione.medicina_nucleare; break; }
					case 18: { ricerca = Specializzazione.medico_di_base; break; }
					case 19: { ricerca = Specializzazione.nefrologia; break; }
					case 20: { ricerca = Specializzazione.neurologia; break; }
					case 21: { ricerca = Specializzazione.neuropsichiatria_infantile; break; }
					case 22: { ricerca = Specializzazione.oftalmologia; break; }
					case 23: { ricerca = Specializzazione.oncologia; break; }
					case 24: { ricerca = Specializzazione.ortopedia; break; }
					case 25: { ricerca = Specializzazione.otorinolaringoiatria; break; }
					case 26: { ricerca = Specializzazione.pediatria; break; }
					case 27: { ricerca = Specializzazione.pneumologia; break; }
					case 28: { ricerca = Specializzazione.psichiatria; break; }
					case 29: { ricerca = Specializzazione.radiodiagnostica; break; }
					case 30: { ricerca = Specializzazione.radioterapia; break; }
					case 31: { ricerca = Specializzazione.remautologia; break; }
					case 32: { ricerca = Specializzazione.statistica_sanitaria_e_biometria; break; }
					case 33: { ricerca = Specializzazione.urologia; break; }	
			 
					
					default: {
						System.out.println("Carattere inserito non riconosciuto!\n");
					}
					}
		
			} catch (IOException e) {
				System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
				e.printStackTrace();
			}
			
			System.out.print("Inserisci il range in cui cercare date disponibili\n>");
			
			//Data inizio ricerca
			String giorno = null, mese = null, anno;
			
			boolean datavalida1 = false;
			
			while(datavalida1 == false) {
			
			System.out.print("Inserirsi la data di inizio ricerca: [In numeri (Formato: dd/mm/yyyy)] \n");
			System.out.print("Giorno:\n>");
			
		
			String input;
			boolean giornovalido = true;
			while(giornovalido == true) {
			input = (inputReader.readLine());
			giorno = input;
			int num = Integer.parseInt(input);
			if(num < 1 || num > 31) {
				System.out.println("Giorno inserito non valido. Inserire nuovamente il giorno\n>");	
			}else
				giornovalido = false;
			}
			
			if(String.valueOf(giorno).length() < 2) {
				giorno = "0"+ giorno;
			}
			
			System.out.print("Mese:\n>");
			
			boolean mesevalido = true;
			while(mesevalido == true) {
			input = (inputReader.readLine());
			mese = input;
			int num1 = Integer.parseInt(input);
			if(num1 < 1 || num1 > 12) {
				System.out.println("Mese inserito non valido. Inserire nuovamente il mese\n>");	
			}else
				mesevalido = false;
			}
			
			if(String.valueOf(mese).length() < 2) {
				mese = "0"+ mese;
			}
			
			System.out.print("Anno:\n>");
			input = (inputReader.readLine());
			anno = input;
			if(String.valueOf(anno).length() < 3) {
				anno = "20"+ anno;
			}
			int num2 = Integer.parseInt(anno);
			if(num2 - LocalDate.now().getYear() > 2) {
				System.out.println("Non sono presenti visite a partire da quest'anno. Non puoi cercare oltre il 2023\n");
				anno = "2023";
			}
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    String date = giorno+"/"+ mese + "/" + anno;


		    dataInizioRicerca = LocalDate.parse(date, formatter);		    

		   
			 
			System.out.print("\nHai inserito: " + date +"\n");	
			
		    if(dataInizioRicerca.compareTo(LocalDate.now()) < 0) {
		    	System.out.println("La data inserita è precedente alla data odierna, si prega di inserire nuovamente i valori\n");
		    }else
			datavalida1 = true;
			//
			}
			
			boolean datavalida2 = false;
			
			
			//Data fine ricerca
			
			while(datavalida2 == false) {
			
			System.out.print("Inserirsi la data di fine ricerca: [In numeri (Formato: dd/mm/yyyy)] \n");
			System.out.print("Giorno:\n>");
			
			String input1;
			boolean giornovalido1 = true;
			while(giornovalido1 == true) {
			input1 = (inputReader.readLine());
			giorno = input1;
			int num = Integer.parseInt(giorno);
			if(num < 1 || num > 31) {
				System.out.println("Giorno inserito non valido. Inserire nuovamente il giorno\n>");	
			}else
				giornovalido1 = false;
			}
			
			if(String.valueOf(giorno).length() < 2) {
				giorno = "0"+ giorno;
			}
			
			System.out.print("Mese:\n>");
			boolean mesevalido1 = true;
			while(mesevalido1 == true) {
			input1 = (inputReader.readLine());
			mese = input1;
			int num1 = Integer.parseInt(mese);
			if(num1 < 1 || num1 > 12) {
				System.out.println("Mese inserito non valido. Inserire nuovamente il mese\n>");	
			}else
				mesevalido1 = false;
			
			}
			
			
			if(String.valueOf(mese).length() < 2) {
				mese = "0"+ mese;
			}
			
			System.out.print("Anno:\n>");
			input1 = (inputReader.readLine());
			anno = input1;
			if(String.valueOf(anno).length() < 3) {
				anno = "20"+ anno;
			}
			int num2 = Integer.parseInt(anno);
			if(num2 - LocalDate.now().getYear() > 2) {
				System.out.println("Non sono presenti visite a partire da quest'anno. Non puoi cercare oltre il 2023\n");
				anno = "2023";
			}
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    String date2 = giorno+"/"+ mese + "/" + anno;
		    dataFineRicerca = LocalDate.parse(date2, formatter);
		  
			System.out.print("\nHai inserito: " + date2 +"\n");	  
			//
		    if(dataFineRicerca.compareTo(LocalDate.now()) < 0) {
		    	System.out.println("La data inserita è precedente alla data odierna, si prega di inserire nuovamente i valori\n");
		    }else if(dataFineRicerca.compareTo(dataInizioRicerca) < 0) {
		    	System.out.println("La data di Fine Ricerca inserita è precedente alla data di Inizio Ricerca inserita, si prega di inserire nuovamente i valori\n");
		    }else
			datavalida2 = true;
			
			}
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			dateDisponibili = gestoreASL.ricercaPerSpecializzazione(ricerca, dataInizioRicerca, dataFineRicerca);
			
			boolean visitadisponibile = true;
			
			if(dateDisponibili.size() == 0) {
				System.out.println("Non ci sono date disponibili per la specializzazione indicata\n");
				val = 1;
				visitadisponibile = false;
				
			}
			
			if(visitadisponibile == true) {
			
			System.out.print("\nLe date disponibili per la specializzazione " + ricerca.toStringa() + " sono le seguenti: \n");
			
			for(int i = 0; i < dateDisponibili.size();i++) {
		
				System.out.print(i+1 +". "+dateDisponibili.get(i).getOrarioPrenotato().getGiornoSettimana().toString()+" ");
				System.out.print(dateDisponibili.get(i).getData().format(formatter) + ":   ");
				System.out.print(dateDisponibili.get(i).getOrarioPrenotato().getOraInizio() + ":");
				System.out.print(dateDisponibili.get(i).getOrarioPrenotato().getMinutiInizio() + "   - Dott. ");
				System.out.print(dateDisponibili.get(i).getOrarioPrenotato().getMedico().getCognome() +
						" (€" + dateDisponibili.get(i).getOrarioPrenotato().getMedico().getSpecializzazione().getPrezzo() + ")" 
						+" "+ dateDisponibili.get(i).getOrarioPrenotato().getMedico().getSpecializzazione().toStringa() + "\n");
			}

			while(val >= dateDisponibili.size() || val < 0) {
			try {
				System.out.print("\nInserire il numero che corrisponde alla visita che si vuole prenotare: \n>");
				val = Integer.parseInt(inputReader.readLine()) - 1;
			} catch (NumberFormatException e) {
				System.out.print("Carattere inserito non riconosciuto. Inserire un numero valido. \n");
				val = -1;
			}
			}
			
			System.out.print("\nHai scelto di prenotare:  \n");
			
			System.out.print(dateDisponibili.get(val).getOrarioPrenotato().getGiornoSettimana().toString()+" ");
			System.out.print(dateDisponibili.get(val).getData().format(formatter) + ":   ");
			System.out.print(dateDisponibili.get(val).getOrarioPrenotato().getOraInizio() + ":");
			System.out.print(dateDisponibili.get(val).getOrarioPrenotato().getMinutiInizio() + "   - Dott. ");
			System.out.print(dateDisponibili.get(val).getOrarioPrenotato().getMedico().getCognome() +
					" (€" + dateDisponibili.get(val).getOrarioPrenotato().getMedico().getSpecializzazione().getPrezzo() + ")" 
					+" - "+ dateDisponibili.get(val).getOrarioPrenotato().getMedico().getSpecializzazione().toStringa() + "\n");
			}
            try {
			return dateDisponibili.get(val);
            }catch(IndexOutOfBoundsException e) {
            	System.err.println("Non sono presenti visite\n");
            	Prenotazione prenotazione = null;
            	return prenotazione;
            }

	}
}
