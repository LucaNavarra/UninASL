package ASL.is.boundary;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.zxing.WriterException;

import ASL.is.entity.*;
import ASL.is.control.*;

public class BMain {
	
	static protected GestoreASL gestoreASL = GestoreASL.instance();
	static protected GestorePrenotazioni gestorePrenotazioni = GestorePrenotazioni.instance();
	
	public static void main(String[] args) throws  ParseException, WriterException, IOException {
		
		System.out.println("**************** [*] BENVENUTI IN UNINASL [*] ****************\n");
		try {
			BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			int option = 0;
			Utente utente = null;
			Medico medico = null;
			Operatore operatore = null;

			do {
				System.out.println("\nSeleziona l'operazione desiderata: \n" +
						 
						"\t1) Effettua login \n" +
						"\t2) Registrati \n" +
						"\t3) Ricerca disponibilita' per medico \n"+
						"\t4) Ricerca disponibilita' per specializzazione \n"+
						"\t5) Esci \n>");
		
				System.out.flush();

				try {
					option = Integer.parseInt(inputReader.readLine());
				} catch (NumberFormatException e) {
					option = 0;
				}

				switch (option) {
				case 1: { 															//LOGIN
					int scelta = 0;					
					do {
						
						System.out.println("Chi vuole effettuare il login?\n " +
								 
								"\t1) Utente\n" +
								"\t2) Medico\n"+
								"\t3) Operatore\n"+
								"\t4) Torna al menu' precedente \n>");
				
						System.out.flush();

						try {
							scelta = Integer.parseInt(inputReader.readLine());
						} catch (NumberFormatException e) {
							scelta = 0;
						}

						switch (scelta) {
						
						case 1: { 	//Login Utente
								utente = BUtenteRegistrato.loginUtente();
								if(utente != null) {
									System.out.println( "Sei loggato come : " + utente.getUsername() + "\n");
									BUtenteRegistrato.showBoundary();
								}
							scelta = 4;
							break; }
						
						case 2: { 	//Login Medico 
								medico = BMedico.loginMedico();
								if(medico != null) {
									System.out.println( "Sei loggato come : " + medico.getUsername() + "\n");
									BMedico.showBoundary();
								}
							scelta = 4;
							break; }
						
						case 3: { 	//Login Operatore
								operatore = BOperatore.loginOperatore();
								if(operatore != null) {
									System.out.println( "Sei loggato come : OP n." + operatore.getCodiceOp() + "\n");
									BOperatore.showBoundary();
								}
							scelta = 4;
							break; }
						
						case 4: {	//Torna al menu principale
							
							break; 
						}
						default: {
							System.out.println("Carattere inserito non riconosciuto!\n");
						}
						
						}
					} while (scelta != 4);
					
				break;}
				
				case 2: { BUtente.registraUtente(); break; }					//REGISTRAZIONE UTENTE
					
				case 3: { 													//RICERCA PER MEDICO
				Prenotazione prenotazione = BUtente.ricercaPerMedico();	
				if(utente == null && prenotazione != null) {
					System.out.print("\nAttenzione! Per poter finalizzare la prenotazione devi essere iscritto alla piattaforma. \n"
							+ "Si prega di effettuare la procedura di registrazione / login.\n");
				}break;}				
				

				case 4: { Prenotazione prenotazione = BUtente.ricercaPerSpecializzazione();				//RICERCA PER SPECIALIZZAZIONE
					
					if(utente == null && prenotazione != null) {
						System.out.print("\nAttenzione! Per poter finalizzare la prenotazione devi essere iscritto alla piattaforma. \n"
								+ "Si prega di effettuare la procedura di registrazione / login.\n");
					}break;}	
				
				case 5: {
					System.out.println( "Arrivederci!\n");					//EXIT
					break; 
				}
				case 6: {
					ArrayList<Prenotazione> pren = gestoreASL.ricercaPerSpecializzazione(Specializzazione.oncologia,LocalDate.of(2022, 06, 05), LocalDate.of(2022, 06, 30));
					System.out.print(pren.size() + "\n");
					pren = null;
					break;
				}
				default: {
					System.out.println("Carattere inserito non riconosciuto!\n");
				}
				}
			} while (option != 5);
		} catch (IOException e) {
			System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
		}

	}


}



