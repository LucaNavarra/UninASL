package ASL.is.boundary;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.google.zxing.WriterException;


import ASL.is.control.GestoreAutenticazione;
import ASL.is.entity.*;

public class BUtenteRegistrato extends BUtente{
	
	static private GestoreAutenticazione<Utente> gestoreAutenticazione;

	protected  static void modificaUtente (Utente utente) { 	
		
		try {
			BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			int option = 0;
			do {
				
				System.out.println("\n\nChe cosa si vuole modificare?\n");
				System.out.println("1. Password \n"
								 + "2. Residenza \n"
								 + "3. Numero di telefono \n"
								 + "4. Indirizzo di posta elettronica\n"
								 + "5. EXIT\n>"
								 );
			
				System.out.flush();

				try {
					option = Integer.parseInt(inputReader.readLine());
				} catch (NumberFormatException e) {
					option = 0;
				}

				switch (option) {
		
				case 1: {
					int ok = 0;
					int count = 0;
					
					while(ok == 0) {
					System.out.print("Inserire la vecchia password:\n>");
					String input;
					input = (inputReader.readLine());
			
					
						if(utente.getPassword().equals(input)) {
							ok = 1;
						
							System.out.print("Inserire la nuova password:\n>");
							String newInput;
							newInput = (inputReader.readLine());
							utente.setPassword(newInput);
							
							System.out.print("Hai cambiato correttamente la tua password \n");
							gestoreASL.modificaUtente(utente);
						}else {
							System.out.print("La password inserita non corrisponde, riprovare\n");
							count++;
							if(count == 3) ok = 1;
							}
				
					}
					
					break;
				}
				
				
				case 2: {					
					int ok = 0;
					
					while(ok == 0) {
		
							System.out.print("Inserire il nuovo indirizzo :\n>");
							String newInput;
							newInput = (inputReader.readLine());
							utente.setResidenza(newInput);
							
							System.out.print("Hai inserito " + newInput + ". \n ");
							gestoreASL.modificaUtente(utente);
							ok=1; 
					}
					
					break;
				}
				
				
				case 3: {
					int ok = 0;
					int count = 0;
					
					while(ok == 0) {
					System.out.print("Inserire il vecchio numero di telefono:\n>");
					String input;
					input = (inputReader.readLine());
			
					
						if(utente.getNumeroTelefono().equals(input)) {
							ok = 1;
						
							System.out.print("Inserire il nuovo numero di telefono:\n>");
							String newInput;
							newInput = (inputReader.readLine());
							utente.setNumeroTelefono(newInput);
							
							System.out.print("Hai inserito " + newInput + " \n");
							gestoreASL.modificaUtente(utente);
						}else {
							
							System.out.print("Il numero di telefono inserito non corrisponde, riprovare\n");}
							count++ ;
							if(count == 3) ok = 1;
					}
					
					break;
				}

				case 4: {
					
					int ok = 0;
					int count = 0;
					
					while(ok == 0) {
					System.out.print("Inserire il vecchio indirizzo di posta elettronica:\n>");
					String input;
					input = (inputReader.readLine());
			
					
						if(utente.getEmail().equals(input)) {
							ok = 1;
						
							System.out.print("Inserire il nuovo indirizzo di posta elettronica:\n>");
							String newInput;
							newInput = (inputReader.readLine());
							utente.setEmail(newInput);
							
							System.out.print("Hai inserito " + newInput + " \n");
							gestoreASL.modificaUtente(utente);
						}else {
							System.out.print("L'indirizzo email inserito non corrisponde, riprovare\n");}
							count++;
							if(count == 3) ok = 1;
					}
					
					break;
				}
				
				case 5: {
					System.out.println("Uscita...\n");
					break;
				}
				
				
				default: {
					System.out.println("Carattere inserito non riconosciuto!\n");
				}
				
				
				}
				
			} while (option != 5);
		} catch (IOException e) {
			System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
			e.printStackTrace();
		}
	}
		
					
	protected static void cancellaUtente (Utente utente) throws NumberFormatException, IOException {	
		try {	
		
		System.out.println("Sei sicuro di voler eliminare l'account? Non sara'� possibile recuperarlo in alcun modo.\n"
				+ "1. Si'\n"
				+ "2. No\n>");
		
		
		int option = 0;
		BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		
		do {
		try {
			option = Integer.parseInt(inputReader.readLine());
		} catch (NumberFormatException e) {
			option = 0;
		}
		switch (option) {
		
		case 1: {
			gestoreASL.cancellaUtente(utente);
			utente = null;
			option = 2;
			break;
		}
		case 2: {
			System.out.print("Uscita...\n");
			break;
		}
		
		
		default: {
			System.out.println("Carattere inserito non riconosciuto!\n");}
		} 
		}while (option != 2);
	} catch (IOException e) {
		System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
		e.printStackTrace();
	}

	}

	
	protected static void nuovaPrenotazione (Prenotazione prenotazione, Utente utente, LocalDate data, Operatore operatore) throws WriterException, IOException {
	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		gestorePrenotazioni.nuovaPrenotazione(prenotazione, utente, null);	
		
		System.out.print("\nRiepilogo dei dati della prenotazione:\n\n");
		
		System.out.print(prenotazione.getOrarioPrenotato().getOraInizio() + "." + prenotazione.getOrarioPrenotato().getMinutiInizio()
						+ "\n" + prenotazione.getOrarioPrenotato().getGiornoSettimana().toString());
		System.out.print(" ");
		System.out.print(prenotazione.getData().format(formatter).toString());
		System.out.print("\n");
		System.out.print(prenotazione.getOrarioPrenotato().getMedico().getNome() +" "+ prenotazione.getOrarioPrenotato().getMedico().getCognome() + "\n");
		System.out.print(prenotazione.getOrarioPrenotato().getMedico().getSpecializzazione().toStringa());
		System.out.print("\n");
		
		System.out.print("Prezzo visita: " + Math.round(prenotazione.getPrezzo()));
		System.out.print("	(e' stato modificato in base al tuo ISEE e al tuo grado di disabilita'�)\n");
		
		System.out.print("\n\nDati utente:\n\n");
		System.out.print("Nome: " + prenotazione.getUtentePrenotato().getNome()
				+ "\nCognome: " + prenotazione.getUtentePrenotato().getCognome()
				+ "\nEmail: "+prenotazione.getUtentePrenotato().getEmail()
				+"\nLogo di nascita: "+prenotazione.getUtentePrenotato().getLuogoNascita()
				+"\nResidenza: "+prenotazione.getUtentePrenotato().getResidenza()
				+"\nCodice fiscale: "+prenotazione.getUtentePrenotato().getCf()
				+"\nGrado disabilita'�: "+prenotazione.getUtentePrenotato().getDisabilitaUtente().toString()
				+"\nISEE: "+prenotazione.getUtentePrenotato().getIsee());
				
		
		
		try {
			BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			int option = 0;
			do {
				
				System.out.println("\n\nI dati inseriti sono tutti corretti? \n");
				System.out.println("1. Si' \n"
						+ "2. No (exit) \n>");
				
				
				
				System.out.flush();

				try {
					option = Integer.parseInt(inputReader.readLine());
				} catch (NumberFormatException e) {
					option = 0;
				}

				switch (option) {
		
				case 1: {
					if(gestorePrenotazioni.prenotazioneConfermata(prenotazione))
						System.out.println("Prenotazione effettuata correttamente. Controlla la tua casella di posta elettronica\n");
					else
						System.out.println("Errore! Prenotazione non riuscita");
					option = 2;
					break;
					}
					
				case 2: {
					prenotazione = null;
					System.out.println("Uscita...\n");
					break;
				}
				
				default: {
					System.out.println("Carattere inserito non riconosciuto!\n");
				}
				
				
				}
				
			} while (option != 2);
		} catch (IOException e) {
			System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	
	protected static ArrayList<Prenotazione> visualizzaPrenotazioni (Utente utente) throws IOException {
		
		LocalDate dataInizio = null, dataFine = null;
		
		BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

		String giorno = null,mese = null, anno;
		
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
			System.out.println("Non sono presenti appuntamenti a partire da quest'anno. Non puoi cercare oltre il 2023\n");
			anno = "2023";
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String date = giorno+"/"+ mese + "/" + anno;


	    dataInizio = LocalDate.parse(date, formatter);		    

	   
		 
		System.out.print("\nHai inserito: " + date +"\n");	
		
	    if(dataInizio.compareTo(LocalDate.now()) < 0) {
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
			System.out.println("Non si possono visualizzare appuntamenti oltre il 2023\n");
			anno = "2023";
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String date2 = giorno+"/"+ mese + "/" + anno;
	    dataFine = LocalDate.parse(date2, formatter);
	  
		System.out.print("\nHai inserito: " + date2 +"\n");	  
		//
	    if(dataFine.compareTo(LocalDate.now()) < 0) {
	    	System.out.println("La data inserita è precedente alla data odierna, si prega di inserire nuovamente i valori\n");
	    }else if(dataFine.compareTo(dataInizio) < 0) {
	    	System.out.println("La data di Fine Ricerca inserita è precedente alla data di Inizio Ricerca inserita, si prega di inserire nuovamente i valori\n");
	    }
		datavalida2 = true;
		
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		  

		ArrayList<Prenotazione> prenotazioni = gestoreASL.visualizzaPrenotazioni(utente, dataInizio, dataFine);
		
		 System.out.println("STAMPA DELLE PRENOTAZIONI EFFETTUATE: \n");
		 
		  for (Prenotazione i : prenotazioni) {
			  
			  System.out.print("ID: ");
		      System.out.print(i.getIdPrenotazione());
		      System.out.print("\n");
		      
		      System.out.print("GIORNO E ORA: ");
				System.out.print(i.getOrarioPrenotato().getGiornoSettimana().toString()+ " " +
						i.getOrarioPrenotato().getOraInizio() + ":" + i.getOrarioPrenotato().getMinutiInizio()
						+ "\n" );

				System.out.print(i.getData().format(formatter).toString());
				System.out.print("\n");
				
				 System.out.print("MEDICO: ");
				System.out.print(i.getOrarioPrenotato().getMedico().getNome() +" "+ i.getOrarioPrenotato().getMedico().getCognome());
				System.out.print("\n");
				System.out.print("\n");
		      
		  }
		  
		return prenotazioni;
	}

	
	public static Utente loginUtente() throws IOException, NullPointerException {
		String utente;
		String pword;
		Utente isLogging = null;
		boolean bool = false;
		int count = 0;
		
		BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		
	
			while(bool != true && count < 3) {
		
					System.out.print( "Inserire username: \n>");
					utente = inputReader.readLine();
					System.out.print( "Inserire password: \n>");
					pword = inputReader.readLine();

					gestoreAutenticazione = GestoreAutenticazione.loginUtente(utente, pword);
					if(gestoreAutenticazione != null) bool = true;
					else count ++ ;
			}
	
		
		if(bool == true) isLogging = gestoreAutenticazione.datiLogin();
		
		return isLogging;
		
	}


	public static void showBoundary() throws WriterException {
		BufferedReader inputReader;		
		
		
		System.out.println("\n**************** [*] MAIN UTENTE REGISTRATO [*] ****************\n");
		try {
			inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			int option = 0;
			
			
			do {
				System.out.println("\nCosa vuoi fare? \n" +
						 
						"\t1) Modifica utente\n" +
						"\t2) Ricerca per medico \n" +
						"\t3) Ricerca per specializzazione \n "+
						"\t4) Visualizza prenotazioni effettuate\n"+
						"\t5) Elimina l'account\n"+
						"\t6) Logout \n>");
		
				System.out.flush();

				try {
					option = Integer.parseInt(inputReader.readLine());
				} catch (NumberFormatException e) {
					option = 0;
				}

				switch (option) {
				case 1: { modificaUtente(gestoreAutenticazione.datiLogin()); break; }
				case 2: { Prenotazione pren = BUtente.ricercaPerMedico();
						  if(pren!=null) nuovaPrenotazione(pren, gestoreAutenticazione.datiLogin(), pren.getData(), null);
						  break; }
				case 3: { Prenotazione pren = BUtente.ricercaPerSpecializzazione();
				  		  if(pren!=null) nuovaPrenotazione(pren, gestoreAutenticazione.datiLogin(), pren.getData(), null);
				  		  break; }
				case 4: { visualizzaPrenotazioni(gestoreAutenticazione.datiLogin()); break;}
				case 5: { cancellaUtente(gestoreAutenticazione.datiLogin()); break;}
				case 6: {
					System.out.println( "Uscita... \n");
					gestoreAutenticazione.logout();
					break; 
				}
				default: {
					System.out.println("Carattere inserito non riconosciuto!\n");
				}
				}
			} while (option != 6);
		} catch (IOException e) {
			System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
