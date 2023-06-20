package ASL.is.boundary;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import ASL.is.control.GestoreAutenticazione;
import ASL.is.entity.*;

public class BMedico extends BMain{
	
	static private GestoreAutenticazione<Medico> gestoreAutenticazione;
	
	public static Medico loginMedico() throws IOException, NullPointerException {
		String utente;
		String pword;
		Medico isLogging = null;
		boolean bool = false;
		int count = 0;
		
		BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		
	
			while(bool != true && count < 3) {
		
					System.out.print( "Inserire username: \n>");
					utente = inputReader.readLine();
					System.out.print( "Inserire password: \n>");
					pword = inputReader.readLine();

					gestoreAutenticazione = GestoreAutenticazione.loginMedico(utente, pword);
					if(gestoreAutenticazione != null) bool = true;
					else count ++ ;
			}
	
		
		if(bool == true) isLogging = gestoreAutenticazione.datiLogin();
		
		return isLogging;
		
	}
	
	protected static ArrayList<Prenotazione> visualizzaAppuntamenti (Medico medico) throws IOException {
		
		LocalDate dataInizio = null, dataFine = null;
		
		BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		
		String giorno = null,mese = null,anno = null;
		
		System.out.println("Inserire data inizio ricerca. [In numeri (Formato: dd/mm/yyyy)] \n");
		System.out.println("Giorno:");
		
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
	    	System.out.println("La data inserita e' precedente alla data odierna, si prega di inserire nuovamente i valori\n");
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
			System.out.println("Non puoi visualizzare appuntamenti oltre il 2023.\n");
			anno = "2023";
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String date2 = giorno+"/"+ mese + "/" + anno;
	    dataFine = LocalDate.parse(date2, formatter);
	  
		System.out.print("\nHai inserito: " + date2 +"\n");	  
		//
	    if(dataFine.compareTo(LocalDate.now()) < 0) {
	    	System.out.println("La data inserita e' precedente alla data odierna, si prega di inserire nuovamente i valori\n");
	    }else if(dataFine.compareTo(dataInizio) < 0) {
	    	System.out.println("La data di Fine Ricerca inserita e' precedente alla data di Inizio Ricerca inserita, si prega di inserire nuovamente i valori\n");
	    }
		datavalida2 = true;
		
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		ArrayList<Prenotazione> prenotazioni = gestoreASL.visualizzaAppuntamenti(medico, dataInizio, dataFine);
		
		System.out.println("STAMPA DEGLI APPUNTAMENTI: \n");
		
		for(Prenotazione i : prenotazioni) {
			
			  System.out.print("ID: ");
		      System.out.print(i.getIdPrenotazione());
		      System.out.print("\n");
		      
		      System.out.print("GIORNO E ORA: ");
			  System.out.print(i.getOrarioPrenotato().getGiornoSettimana().toString()+ " " +
						i.getOrarioPrenotato().getOraInizio() + ":" + i.getOrarioPrenotato().getMinutiInizio()
						+ " - " + i.getOrarioPrenotato().getOraFine() + ":" + i.getOrarioPrenotato().getMinutiFine() + "\n" );

			  System.out.print(i.getData().format(formatter).toString());
			  System.out.print("\n");
				
			  System.out.print("UTENTE: ");
			  System.out.print(i.getUtentePrenotato().getNome() +" "+ i.getUtentePrenotato().getCognome());
			  System.out.print("\n");
			  System.out.print("\n");
		      
		}
		
		return prenotazioni;
		  
	}
	
	public static void showBoundary() {
		BufferedReader inputReader;		
		
		
		System.out.println("\n**************** [*] MAIN MEDICO [*] ****************\n");
		try {
			inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			int option = 0;
			
			do {
				System.out.println("\nCosa vuoi fare? \n" +

						"\t1) Visualizza appuntamenti\n"+
						"\t2) Logout \n>");
		
				System.out.flush();

				try {
					option = Integer.parseInt(inputReader.readLine());
				} catch (NumberFormatException e) {
					option = 0;
				}

				switch (option) {
				case 1: { BMedico.visualizzaAppuntamenti(gestoreAutenticazione.datiLogin()); break; }
				case 2: {
					System.out.println( "Uscita... \n");
					gestoreAutenticazione.logout();
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
}
