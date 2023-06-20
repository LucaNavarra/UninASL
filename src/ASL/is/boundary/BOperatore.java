package ASL.is.boundary;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.google.zxing.WriterException;

import ASL.is.control.GestoreAutenticazione;
import ASL.is.entity.*;


public class BOperatore extends BMain{
	
	static GestoreAutenticazione<Operatore> gestoreAutenticazione;

	
	protected static void nuovaPrenotazione (Prenotazione prenotazione, Utente utente, LocalDate data, Operatore operatore) throws WriterException, IOException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		gestorePrenotazioni.nuovaPrenotazione(prenotazione, utente, operatore);	
		
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
		System.out.print("	(E' stato modificato in base al tuo ISEE e al tuo grado di disabilita'�)\n");
		
		System.out.print("\n\nDati utente:\n\n");
		System.out.print("Nome: " + prenotazione.getUtentePrenotato().getNome()
				+ "\nCognome: " + prenotazione.getUtentePrenotato().getCognome()
				+ "\nEmail: "+prenotazione.getUtentePrenotato().getEmail()
				+"\nLogo di nascita: "+prenotazione.getUtentePrenotato().getLuogoNascita()
				+"\nResidenza: "+prenotazione.getUtentePrenotato().getResidenza()
				+"\nCodice fiscale: "+prenotazione.getUtentePrenotato().getCf()
				+"\nGrado disabilita'�: "+prenotazione.getUtentePrenotato().getDisabilitaUtente().toString()
				+"\nISEE: "+prenotazione.getUtentePrenotato().getIsee());
		
		System.out.print("\n\nDati operatore:\n");
		System.out.print("Codice: " + prenotazione.getOperatorePrenotante().getCodiceOp() + " \n");
				
		
		
		try {
			BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			int option = 0;
			do {
				System.out.println("\n\nI dati inseriti sono tutti corretti? \n");
				System.out.println("1. Si'� \n"
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

	
	protected static void registraUtente () throws IOException {
		BUtente.registraUtente();
	 }
	
	protected static void registraMedico () throws IOException{
		
		String username, password = null, pass2, nome, cognome;
		Specializzazione specializzazione = null;
		boolean ok = false;
		
	    BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
	    
		System.out.print(" *** PROCEDURA DI REGISTRAZIONE MEDICO ***\n");
		
		System.out.print("Scegli un username per il medico (servira'� per accedere): \n>");
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
		
		System.out.print("Inserisci il nome del medico: \n>");
		nome = inputReader.readLine();
		
		System.out.print("Inserisci il cognome del medico: \n>");
		cognome = inputReader.readLine();
		
		try {
			int option = 0;
		
				System.out.println("Scegliere la specializzazione? \n" +
						 
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
				case 1: { specializzazione = Specializzazione.allergologia_e_immunologia; break; }
				case 2: { specializzazione = Specializzazione.anestesia; break; }
				case 3: { specializzazione = Specializzazione.audiologia_e_foniatria; break; }
				case 4: { specializzazione = Specializzazione.cardiologia; break; }
				case 5: { specializzazione = Specializzazione.dermatologia_e_veneorologia; break; }
				case 6: { specializzazione = Specializzazione.ematologia; break; }
				case 7: { specializzazione = Specializzazione.endocrinologia; break; }
				case 8: { specializzazione = Specializzazione.farmacologia_e_tossicologia_clinica; break; }
				case 9: { specializzazione = Specializzazione.gastroenterologia; break; }	
				case 10: { specializzazione = Specializzazione.geriatria; break; }
				case 11: { specializzazione = Specializzazione.ginecologia; break; }
				case 12: { specializzazione = Specializzazione.igiene_e_medicina_preventiva; break; }
				case 13: { specializzazione = Specializzazione.madicina_legale; break; }
				case 14: { specializzazione = Specializzazione.malattie_infettive; break; }
				case 15: { specializzazione = Specializzazione.medicina_del_lavoro; break; }
				case 16: { specializzazione = Specializzazione.medicina_fisica_e_riabilitativa; break; }
				case 17: { specializzazione = Specializzazione.medicina_nucleare; break; }
				case 18: { specializzazione = Specializzazione.medico_di_base; break; }
				case 19: { specializzazione = Specializzazione.nefrologia; break; }
				case 20: { specializzazione = Specializzazione.neurologia; break; }
				case 21: { specializzazione = Specializzazione.neuropsichiatria_infantile; break; }
				case 22: { specializzazione = Specializzazione.oftalmologia; break; }
				case 23: { specializzazione = Specializzazione.oncologia; break; }
				case 24: { specializzazione = Specializzazione.ortopedia; break; }
				case 25: { specializzazione = Specializzazione.otorinolaringoiatria; break; }
				case 26: { specializzazione = Specializzazione.pediatria; break; }
				case 27: { specializzazione = Specializzazione.pneumologia; break; }
				case 28: { specializzazione = Specializzazione.psichiatria; break; }
				case 29: { specializzazione = Specializzazione.radiodiagnostica; break; }
				case 30: { specializzazione = Specializzazione.radioterapia; break; }
				case 31: { specializzazione = Specializzazione.remautologia; break; }
				case 32: { specializzazione = Specializzazione.statistica_sanitaria_e_biometria; break; }
				case 33: { specializzazione= Specializzazione.urologia; break; }	

				default: {
					System.out.println("Carattere inserito non riconosciuto!\n");
				}
				}
	
		} catch (IOException e) {
			System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
			e.printStackTrace();
		}
		
		Medico medico = new Medico (nome, cognome, username, password, specializzazione);
		
		if(gestoreASL.registraMedico(medico))
			System.out.println("Medico correttamente registrato\n");
		else
			System.out.print("Errore nella registrazione");
		
		
		
		try {
			
			boolean aggiungivisita = true;
			
			while(aggiungivisita == true) {
				
				System.out.println("Inserisci una visita relativa al medico\n");
				
				
				
				System.out.flush();


					String stanza;
					Giorno giornoVisita = null;;
					String oraInizio, minutiInizio;
					
					System.out.print("Inserisci l'ora di inizio della visita: \n>");
					oraInizio = inputReader.readLine();
					int orarioInizio = Integer.parseInt(oraInizio);
					
					System.out.print("Inserisci i minuti di inizio della visita: \n>");
					minutiInizio = inputReader.readLine();
					int minutoInizio= Integer.parseInt(minutiInizio);

					System.out.print("Inserisci la stanza: \n>");
					stanza = inputReader.readLine();
					
					try {
					int option1 = 0;
					
					System.out.println("Scegliere il giorno della visita \n" +
							 
								"\t1)  Luned� \n" +
								"\t2)  Marted� \n"+
								"\t3)  Mercoled� \n"+
								"\t4)  Gioved�\n"+
								"\t5)  Venerd� \n");
					
					
					
					System.out.flush();

				try {
					option1 = Integer.parseInt(inputReader.readLine());
				} catch (NumberFormatException e) {
					option1 = 0;
				}

				switch (option1) {
				case 1: { giornoVisita = Giorno.Lunedi; break; }
				case 2: { giornoVisita = Giorno.Martedi; break; }
				case 3: { giornoVisita = Giorno.Mercoledi; break; }
				case 4: { giornoVisita = Giorno.Giovedi; break; }
				case 5: { giornoVisita = Giorno.Venerdi; break; }
				

				default: {
					System.out.println("Carattere inserito non riconosciuto!\n");
				}
				}
	
		} catch (IOException e) {
			System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
			e.printStackTrace();
		}
					
					int id_orario = 0;
				
					Visita visita = new Visita (stanza, giornoVisita, minutoInizio, orarioInizio, medico, id_orario);
							
					if(gestoreASL.aggiungiVisita(visita))
						System.out.println("Visita inserita correttamente\n");
					else
						System.out.println("Inserimento visita non riuscito\n");
					
					int option = 0;
					
					System.out.println("Vuoi aggiungere un'altra visita?\n"
							           + "1.Si'\n"
							           + "2.No\n");
					
					System.out.flush();

					try {
						option = Integer.parseInt(inputReader.readLine());
					} catch (NumberFormatException e) {
						option = 0;
					}
					
					switch(option) {
					
					case 1 : {aggiungivisita = true;
					break;}

					
					case 2 : {aggiungivisita = false;
					System.out.println("Uscita...\n");
					break;}
					
					
			        default: {
				    System.out.println("Carattere inserito non riconosciuto!\n");
			      }
					}
				
			} 
		} catch (IOException e) {
			System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
			e.printStackTrace();
		}
		
	
		
	}
	
	protected static void modificaMedico(Medico medico) {
		try {
			BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			int option = 0;
			do {
				
				System.out.println("\n\nChe operazione vuoi eseguire?\n");
				System.out.println("1. Modificare la password del medico\n"
								 + "2. Modificare una visita al medico \n"
						         + "3. Aggiungere una visita al medico\n"
								 + "4. Cancellare la visita del medico\n"
						         + "5. EXIT\n"
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
			
					
					if(medico.getPassword().equals(input)) {
							ok = 1;
						
							System.out.print("Inserire la nuova password:\n>");
							String newInput;
							newInput = (inputReader.readLine());
							medico.setPass(newInput);
							
							System.out.print("Hai cambiato correttamente la tua password \n");
							gestoreASL.modificaMedico(medico, newInput);
						}else {
							System.out.print("La password inserita non corrisponde, riprovare\n");
							count++;
							if(count == 3) ok = 1;
							}
				
					}
					
					break;
				}
				
				case 2: {
					
					String id_visita;
					
					if(stampaCalendarioMedico(medico)){
					System.out.print("Inserisci l'id della visita che si desidera modificare: \n>");
					id_visita = inputReader.readLine();
					int id_orario = Integer.parseInt(id_visita);
					Visita visita = gestoreASL.ottieniVisita(medico, id_orario);
					
					System.out.print("Inserisci la nuova stanza della visita: \n>");
					String stanza = inputReader.readLine();
					visita.setStanza(stanza);
					
					boolean oravisita = true;
					int orarioInizio = 0;
					while(oravisita == true) {
					System.out.print("Inserisci la nuova ora d'inizio della visita: \n>");
					String oraInizio = inputReader.readLine();
					orarioInizio = Integer.parseInt(oraInizio);
					if(orarioInizio < 0 || orarioInizio > 23)
						oravisita = true;
					else oravisita = false;
					}
					
					boolean minutovisita = true;
					int minutoInizio = 0;
					while(minutovisita == true) {
					System.out.print("Inserisci i nuovi minuti di inizio della visita: \n>");
					String minutiInizio = inputReader.readLine();
					minutoInizio = Integer.parseInt(minutiInizio);
					if(minutoInizio < 0 || minutoInizio > 59) {
						minutovisita = true;
					}else minutovisita = false;
					}
					
					visita.setOrarioInizio(orarioInizio, minutoInizio);
					
					try {
						int option1 = 0;
						Giorno giornovisita;
						
						System.out.println("Scegliere il nuovo giorno della visita: \n" +
								 
									"\t1)  Lunedi \n" +
									"\t2)  Martedi \n"+
									"\t3)  Mercoledi \n"+
									"\t4)  Giovedi\n"+
									"\t5)  Venerdi \n");
						
						
						
						System.out.flush();

					try {
						option1 = Integer.parseInt(inputReader.readLine());
					} catch (NumberFormatException e) {
						option1 = 0;
					}

					switch (option1) {
					case 1: { giornovisita = Giorno.Lunedi; 
					          visita.setGiornoSettimana(giornovisita);
					          break; }
					case 2: { giornovisita = Giorno.Martedi; 
					          visita.setGiornoSettimana(giornovisita);
					          break; }
					case 3: { giornovisita = Giorno.Mercoledi; 
					          visita.setGiornoSettimana(giornovisita);
					          break; }
					case 4: { giornovisita = Giorno.Giovedi; 
					          visita.setGiornoSettimana(giornovisita);
					          break; }
					case 5: { giornovisita = Giorno.Venerdi; 
					          visita.setGiornoSettimana(giornovisita);
					          break; }
					

					default: {
						System.out.println("Carattere inserito non riconosciuto!\n");
					}
					}
		
			} catch (IOException e) {
				System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
				e.printStackTrace();
			}
					
					if(gestoreASL.modificaVisita(visita))
					System.out.println("Hai correttamente modificato la visita del medico.\n");
					else
						System.out.println("La modifica della visita non � andata a buon fine\n");

				}
					
					break;
				}
				
				case 3: {
					
					stampaCalendarioMedico(medico);
					try {
						
						boolean aggiungivisita = true;
						
						while(aggiungivisita == true) {
							
							System.out.println("Inserisci una visita relativa al medico\n");
							
							
							
							System.out.flush();


								String stanza;
								Giorno giornoVisita = null;;
								String oraInizio, minutiInizio;
								
								System.out.print("Inserisci l'ora di inizio della visita: \n>");
								oraInizio = inputReader.readLine();
								int orarioInizio = Integer.parseInt(oraInizio);
								
								System.out.print("Inserisci i minuti di inizio della visita: \n>");
								minutiInizio = inputReader.readLine();
								int minutoInizio= Integer.parseInt(minutiInizio);

								System.out.print("Inserisci la stanza: \n>");
								stanza = inputReader.readLine();
								
								try {
								int option1 = 0;
								
								System.out.println("Scegliere il giorno della visita \n" +
										 
											"\t1)  Lunedi \n" +
											"\t2)  Martedi \n"+
											"\t3)  Mercoledi \n"+
											"\t4)  Giovedi\n"+
											"\t5)  Venerdi \n");
								
								
								
								System.out.flush();

							try {
								option1 = Integer.parseInt(inputReader.readLine());
							} catch (NumberFormatException e) {
								option1 = 0;
							}

							switch (option1) {
							case 1: { giornoVisita = Giorno.Lunedi; break; }
							case 2: { giornoVisita = Giorno.Martedi; break; }
							case 3: { giornoVisita = Giorno.Mercoledi; break; }
							case 4: { giornoVisita = Giorno.Giovedi; break; }
							case 5: { giornoVisita = Giorno.Venerdi; break; }
							

							default: {
								System.out.println("Carattere inserito non riconosciuto!\n");
							}
							}
				
					} catch (IOException e) {
						System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
						e.printStackTrace();
					}
								Visita visita = new Visita (stanza, giornoVisita, minutoInizio, orarioInizio, medico);
								System.out.print(visita.getMedico().getCognome() + "\n");
								gestoreASL.aggiungiVisita(visita);
								
								System.out.println("Visita inserita correttamente\n");
								
								int option2 = 0;
								
								System.out.println("Vuoi aggiungere un'altra visita?\n"
										           + "1.Si\n"
										           + "2.No\n");
								
								System.out.flush();

								try {
									option2 = Integer.parseInt(inputReader.readLine());
								} catch (NumberFormatException e) {
									option2 = 0;
								}
								
								switch(option2) {
								
								case 1 : {aggiungivisita = true;
								break;}

								
								case 2 : {aggiungivisita = false;
								System.out.println("Uscita...\n");
								break;}
								
								
						        default: {
							    System.out.println("Carattere inserito non riconosciuto!\n");
						      }
								}
							
						} 
					} catch (IOException e) {
						System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
						e.printStackTrace();
					}
					
					break;
					
				}
				case 4: {
					
					String id_visita;
					
					if(stampaCalendarioMedico(medico)) {
					System.out.print("Inserisci l'id della visita che si desidera cancellare: \n>");
					id_visita = inputReader.readLine();
					int id_orario = Integer.parseInt(id_visita);
					
					Visita visita = gestoreASL.ottieniVisita(medico, id_orario);
				    gestoreASL.cancellaVisita(visita);
				    
				    System.out.println("Hai cancellato correttamente la visita del medico\n");
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
	
	protected static void cancellaMedico(Medico medico) throws IOException, NumberFormatException {
		
		try {
		System.out.println("Sei sicuro di voler eliminare il medico? L'operazione non sara'� reversibile \n" 
				            + "1.Si\n"
				            + "2.No\n");
		
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
			gestoreASL.cancellaMedico(medico);
			medico = null;
			option = 2;
			break;
		}
		
		case 2: {
			System.out.println("Uscita..\n");
			break;
		}
		
		default: {
			System.out.println("Carattere inserito non riconosciuto!\n");
			}		
		  }
		}while(option != 2);
		} catch(IOException e) {
			System.err.println("Si e' verificato un errore di I/O:"  + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	static protected Prenotazione ricercaPerMedico() throws IOException, WriterException {
		Prenotazione prenotazione = BUtente.ricercaPerMedico();
		prenotazione.setOperatorePrenotante(gestoreAutenticazione.datiLogin());
		return prenotazione;
	}
	
	static protected Prenotazione ricercaPerSpecializzazione() throws IOException {
		Prenotazione prenotazione = BUtente.ricercaPerSpecializzazione();
		prenotazione.setOperatorePrenotante(gestoreAutenticazione.datiLogin());
		return prenotazione;
	}
	
	public static Operatore loginOperatore() throws IOException, NullPointerException {
		
		String codice_operatore;
		String pword;
		Operatore isLogging = null;
		boolean bool = false;
		int count = 0;
		
		BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		
	
			while(bool != true && count < 3) {
		
					System.out.print( "Inserire il codice operatore: \n>");
					codice_operatore = inputReader.readLine();
					int codice = Integer.parseInt(codice_operatore);
					System.out.print( "Inserire password: \n>");
					pword = inputReader.readLine();

					gestoreAutenticazione = GestoreAutenticazione.loginOperatore(codice, pword);
					if(gestoreAutenticazione != null) bool = true;
					else count ++ ;
			}
	
		
		if(bool == true) isLogging = gestoreAutenticazione.datiLogin();
		
		return isLogging;
		
	}
    
	public static void showBoundary() throws WriterException {
		
		BufferedReader inputReader;		
		
		
		System.out.println("\n**************** [*] MAIN OPERATORE [*] ****************\n");
		try {
			inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			int option = 0;
			
			
			do {
				System.out.println("\nCosa vuoi fare? \n" +
						 
						"\t1) Registrare un utente\n" +
						"\t2) Registrare un medico \n" +
						"\t3) Modificare un medico \n" +
						"\t4) Cancellare un medico \n" +
						"\t5) Ricerca per medico \n" +
						"\t6) Ricerca per specializzazione \n "+
						"\t7) Logout \n>");
		
				System.out.flush();

				try {
					option = Integer.parseInt(inputReader.readLine());
				} catch (NumberFormatException e) {
					option = 0;
				}

				switch (option) {
				
				case 1: { registraUtente(); break; }
				
				case 2: { registraMedico(); break; }
				
				case 3: { System.out.println("Inserisci l'username del medico che si vuole modificare\n");
		          
		  		          String username = inputReader.readLine();
		  		          
		  		          Medico medico = gestoreASL.ottieniMedico(username);
		  		          if(medico != null)
		  		        	  modificaMedico(medico);
		  		          else
		  		        	  System.out.print("Medico non presente \n");
		  		         
		  		          break; }
				
				case 4: { System.out.println("Inserisci l'username del medico che si vuole eliminare\n");
		          
		                  String username = inputReader.readLine();
		          
		                  Medico medico = gestoreASL.ottieniMedico(username);
		          
		                  cancellaMedico(medico);
		         
		                  break; }
		
				case 5: { 
				          
				          System.out.println("Inserisci l'username dell'utente che vuole prenotare una visita\n");
				          
				  		  String username = inputReader.readLine();
				  		  
				  		  Utente utente = gestoreASL.ottieniUtenteDaUsername(username);
				  		  
				  		  Prenotazione pren = ricercaPerMedico();
				          
						  if(pren!=null) nuovaPrenotazione(pren, utente, pren.getData(), gestoreAutenticazione.datiLogin());
						  
						  break; }
				
				case 6: { 
				           
		                  System.out.println("Inserisci l'username dell'utente che vuole prenotare una visita\n");
		          
		  		          String username = inputReader.readLine();
		  		  
		  		          Utente utente = gestoreASL.ottieniUtenteDaUsername(username);
		          
		  		          Prenotazione pren = ricercaPerSpecializzazione();
		  		        
				          if(pren!=null) nuovaPrenotazione(pren, utente, pren.getData(), gestoreAutenticazione.datiLogin());
				 
				  		  break; }
				
				case 7: {
					System.out.println( "Uscita... \n");
					gestoreAutenticazione.logout();
					break; 
				}
				default: {
					System.out.println("Carattere inserito non riconosciuto!\n");
				}
				}
			} while (option != 7);
		} catch (IOException e) {
			
			System.err.println("Si e' verificato un errore di I/O: " + e.getMessage());
			e.printStackTrace();
			
		}
	}
	static protected boolean stampaCalendarioMedico(Medico medico) {
		ArrayList<Visita> visite = gestoreASL.ottieniCalendarioMedico(medico);
		int i;
		boolean sonoPresentiVisite = true;
		System.out.print("Calendario Medico: " + medico.getCognome() + "\n");
        for(i = 0;i < visite.size();i++) {
        	System.out.print("ID: "+visite.get(i).getIdOrario());
        	System.out.print(" "+visite.get(i).getGiornoSettimana().toString());
        	System.out.print(" "+visite.get(i).getOraInizio());
        	System.out.print(":"+visite.get(i).getMinutiInizio() + " - ");
        	System.out.print(visite.get(i).getOraFine());
        	System.out.print(":"+visite.get(i).getMinutiFine() + " - ");
        	System.out.print(" Stanza: "+visite.get(i).getStanza() + "\n");
        	
        }
        if(visite.size() == 0) {
        	sonoPresentiVisite = false;
        	System.out.print("Non sono presenti visite di questo medico \n");
        }
        return sonoPresentiVisite;
        
	}
}