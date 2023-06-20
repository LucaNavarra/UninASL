/*
package ASL.is.boundary;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;


import ASL.is.DAO.*;
import ASL.is.entity.*;
import ASL.is.control.*;

public class Main {

	public static void main(String[] args) {
		
		
		String scelta = " ";
		String nomeSpec = " ";
		String cognome = " ";
	
		LocalDate dataInizioRicerca = LocalDate.now();
		LocalDate dataFineRicerca = LocalDate.now();
		
		
		
	
		System.out.print("Digitare i seguenti numeri per: \n");
		System.out.print("- 1: Ricerca disponibilità per medico \n");
		System.out.print("- 2: Ricerca disponibilità per specializzazione \n");

	
		Scanner sc = new Scanner(System.in);  
		scelta = sc.nextLine(); 
	  
		System.out.print("Hai scelto: "+ scelta + "\n"); 
	
		
		switch(scelta) {
		
		
		case "1":
			
			System.out.print("Inserisci il nome del medico\n");
			Scanner s = new Scanner(System.in);
			cognome = s.nextLine();
			
			
			/*
			System.out.print("Inserisci la data di inizio ricerca\n");
			Scanner s3 = new Scanner(System.in);
			dataInizioRicerca = s2.nextLine();
			
			System.out.print("Inserisci la data di fine ricerca\n");
			Scanner s4 = new Scanner(System.in);
			dataFineRicerca = s2.nextLine();
			
			
			BUtente.ricercaPerMedico(cognome,dataInizioRicerca,dataFineRicerca);
			
			break;
			
			
			
		case "2":
			
			System.out.print("Inserisci il nome della specializzazione da ricercare\n");
			Scanner d = new Scanner(System.in);
			nomeSpec = d.nextLine();
			
		
			/*
			System.out.print("Inserisci la data di inizio ricerca\n");
			Scanner s3 = new Scanner(System.in);
			dataInizioRicerca = s2.nextLine();
			
			System.out.print("Inserisci la data di fine ricerca\n");
			Scanner s4 = new Scanner(System.in);
			dataFineRicerca = s2.nextLine();
			
			
			BUtente.ricercaPerSpecializzazione(nomeSpec,dataInizioRicerca,dataFineRicerca);
			
		}
	}
	}
	*/


package ASL.is.boundary;


import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

import com.google.zxing.WriterException;

import java.sql.*;

import ASL.is.DAO.*;
import ASL.is.boundary.*;
import ASL.is.control.*;
import ASL.is.entity.*;



public class Main {
	
	
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws DAOException 
	 * @throws ParseException 
	 * @throws WriterException 
	 * @throws IOException 
	 * @throws EventoException 
	 */

	public static void main(String[] args) throws SQLException, ParseException, WriterException, IOException {

/*
		CreateDatabase.creaTabellaEventi();
		CreateDatabase.creaTabellaServiziTV();
		CreateDatabase.creaTabellaUtenti();
*/
		LocalDate dataNascita = LocalDate.of(2000,11,22);
		LocalDate data = LocalDate.of(2022,06,06);
		
		Utente Luca = new Utente("Luca", "Navarra" ,"NVRLCU00S22L259R", "LucaNav", 
				"1234",dataNascita,"Torre del Greco","Napoli", "3335333963",
				"theluca2211@gmail.com",0, GradoDisabilita.Nessuna);
		
		
		//BUtenteRegistrato.loginUtente();
		
		Medico medico = new Medico("Augusto", "Morelli", "Aug01", "12345", Specializzazione.audiologia_e_foniatria);
		Visita visita = new Visita("A3 - T3", Giorno.Lunedì, 30, 8, medico);
		Prenotazione prenotazione = new Prenotazione(Luca, null, visita, 5, data, 0);

		
		
		
		
		
		
		System.out.println("**************** [*] BENVENUTI IN UNINASL [*] ****************\n");
		try {
			inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			int option = 0;
			
			do {
				System.out.println("\nSeleziona l'operazione desiderata: \n" +
						"\t1) Modifica utente\n" +
						"\t2) Nuova prenotazione\n"+
						"\t3) Visualizza prenotazioni effettuate\n"+
						"\t4) Esci \n>");
		
				System.out.flush();

				try {
					option = Integer.parseInt(inputReader.readLine());
				} catch (NumberFormatException e) {
					option = 0;
				}

				switch (option) {
				case 1: { BUtenteRegistrato.modificaUtente(Luca); break; }
				case 2: { BUtenteRegistrato.nuovaPrenotazione(prenotazione, Luca, data, null); break; }
				case 3: { BUtenteRegistrato.visualizzaPrenotazioni(Luca); break;}
				case 4: {
					System.out.println( "Arrivederci!\n");
					//ConnectionManager.closeConnection();
					break; 
				}
				default: {
					System.out.println("Carattere inserito non riconosciuto!\n");
				}
				}
			} while (option != 4);
		} catch (IOException e) {
			System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
			e.printStackTrace();
		}
	}

	protected static java.io.BufferedReader inputReader;
}


