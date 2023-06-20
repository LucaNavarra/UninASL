package ASL.is.control;

import java.time.LocalDate;
import java.util.ArrayList;

import ASL.is.DAO.*;
import ASL.is.entity.*;


public class GestoreASL {
	
	
	private static int istanza = 0;
	
	protected GestoreASL () {
		super();
	}
	
	//Singleton
	public static GestoreASL instance() {
		GestoreASL gest = null;
		if(istanza == 0) {
			gest = new GestoreASL();
			istanza  = 1;
		}
		return gest;
	}

	/*
	 * Prende una data in input come 02-05-2022 e il giorno come mercoled�
	 * 
	 * @return il la data pi� vicina per eccesso a quel mercoled�
	 */
	private static LocalDate ottieniDataEccesso(LocalDate data,Giorno giorno) {
		LocalDate dataOttenuta = data;
		while(dataOttenuta.getDayOfWeek().getValue() != giorno.getNumeroGiorno())
			dataOttenuta = dataOttenuta.plusDays(1);
		return dataOttenuta;
	}
	
	public ArrayList<Prenotazione> ricercaPerMedico(String usernameMedico,LocalDate dataInizio, LocalDate dataFine){
		ArrayList<Prenotazione> dateDisponibili = new ArrayList<Prenotazione>();
		ArrayList<Prenotazione> dateOccupate = null;
		ArrayList<Visita> orariVisita = null;
		Prenotazione dataDisponibile;
		LocalDate data;
		boolean occupato;
		int i,j;
		        try {
				//per ogni medico mi prendo tutti gli orari visita
				orariVisita = VisitaDao.ottieniCalendarioMedico(MedicoDao.ottieniMedico(usernameMedico));
				for(i = 0;i < orariVisita.size();i++) {
					//per ogni orario visita vado a prendere tutte le prenotazioni
					dateOccupate = PrenotazioneDao.ottieniPrenotazioniDaVisita(orariVisita.get(i), dataInizio, dataFine);
					//adesso vado a vedere tutte le date che ho a disposizione
					data = ottieniDataEccesso(dataInizio,orariVisita.get(i).getGiornoSettimana());
					while(data.compareTo(dataFine) < 0) {
						occupato = false;
						j = 0;
						//controllo se quella data � gi� nelle prenotazioni e quindi � occupata
						while(j < dateOccupate.size() && occupato == false) {
							if(data.equals(dateOccupate.get(j).getData()))
								occupato = true;
							j++;
						}
						if(occupato == false) {
							dataDisponibile = new Prenotazione();
							dataDisponibile.setData(data);
							dataDisponibile.setOrarioPrenotato(orariVisita.get(i));
							dateDisponibili.add(dataDisponibile);
						}
							
						data = data.plusDays(7);
					}
				}
		        }catch(DAOException e) {
		        	System.out.print(e.getMessage() + "\n");
		        }
		        catch(NullPointerException e) {
		        	System.out.print(e.getMessage() + "\n");
		        }
		return dateDisponibili;
	}
	
	public ArrayList<Prenotazione> ricercaPerSpecializzazione(Specializzazione specRicercata,LocalDate dataInizio, LocalDate dataFine) {
		ArrayList<Prenotazione> dateDisponibili = new ArrayList<Prenotazione>();
		ArrayList<Prenotazione> dateOccupate = null;
		ArrayList<Medico> listaMedici = null;
		ArrayList<Visita> orariVisita = null;
		Prenotazione dataDisponibile;
		LocalDate data;
		boolean occupato;
		int i,j,k;
		    try {
			//ottengo la lista medici della specializzazione
			listaMedici = MedicoDao.ottieniMedicidaSpecializzazione(specRicercata);
			for(i = 0;i < listaMedici.size();i++) {
				//per ogni medico mi prendo tutti gli orari visita
				orariVisita = VisitaDao.ottieniCalendarioMedico(listaMedici.get(i));
				for(j = 0;j < orariVisita.size();j++) {
					//per ogni orario visita vado a prendere tutte le prenotazioni
					dateOccupate = PrenotazioneDao.ottieniPrenotazioniDaVisita(orariVisita.get(j), dataInizio, dataFine);
					//adesso vado a vedere tutte le date che ho a disposizione
					//System.out.print(dateOccupate.size() + "\n");
					data = ottieniDataEccesso(dataInizio,orariVisita.get(j).getGiornoSettimana());
					while(data.compareTo(dataFine) < 0) {
						occupato = false;
						k = 0;
						//controllo se quella data � gi� nelle prenotazioni e quindi � occupata
						while(k < dateOccupate.size() && occupato == false) {
							if(data.equals(dateOccupate.get(k).getData())) {
								occupato = true;
								System.out.print("ciao");
							}
							k++;
						}
						if(occupato == false) {
							dataDisponibile = new Prenotazione();
							dataDisponibile.setData(data);
							dataDisponibile.setOrarioPrenotato(orariVisita.get(j));
							dateDisponibili.add(dataDisponibile);
							//System.out.print(dateDisponibili.size() + "\n");
						}
						data = data.plusDays(7);
					}
				}
			}
		    }catch(DAOException e) {
		    	System.err.print(e.getMessage() + "\n");
		    }
		return dateDisponibili;
	}

	public ArrayList<Prenotazione> visualizzaAppuntamenti(Medico medico, LocalDate dataInizio, LocalDate dataFine) {
		ArrayList<Prenotazione> prenotazioni = null;
		try {
		prenotazioni = PrenotazioneDao.ottieniPrenotazioniMedico(medico, dataInizio, dataFine);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return prenotazioni;
	}
	
	public ArrayList<Prenotazione> visualizzaPrenotazioni(Utente utente, LocalDate dataInizio, LocalDate dataFine) {
		ArrayList<Prenotazione> prenotazioni = null;
		try {
		prenotazioni = PrenotazioneDao.ottieniPrenotazioniUtente(utente, dataInizio, dataFine);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return prenotazioni;
	}
	
	public Utente ottieniUtenteDaUsername(String username){
		Utente utente = null;
		try {
		utente = UtenteDao.ottieniUtenteDaUsername(username);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return utente;
		
	}
	
	public Utente ottieniUtenteDaCf(String username){
		Utente utente = null;
		try {
		utente = UtenteDao.ottieniUtenteDaCF(username);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return utente;
	}
	
	public Utente ottieniUtenteDaEmail(String username){
		Utente utente = null;
		try {
		utente = UtenteDao.ottieniUtenteDaEmail(username);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return utente;
	}
	
	public boolean registraUtente(Utente utente) {
		boolean operazioneRiuscita = false;
		try {
		operazioneRiuscita = UtenteDao.registraUtente(utente);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return operazioneRiuscita;
	}
	
	public boolean modificaUtente(Utente utente){
		boolean operazioneRiuscita = false;
		try {
		operazioneRiuscita = UtenteDao.modificaUtente(utente);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return operazioneRiuscita;
	}
	
	public boolean cancellaUtente(Utente utente){
		boolean operazioneRiuscita = false;
		try {
		operazioneRiuscita = UtenteDao.eliminaUtente(utente);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return operazioneRiuscita;
	}
	
	public Medico ottieniMedico(String username) {
		Medico medico = null;
		try {
		medico = MedicoDao.ottieniMedico(username);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return medico;
	}
	
	public ArrayList<Medico> ottieniMediciDaCognome(String cognome){
		ArrayList<Medico> medici = null;
		try {
		medici = MedicoDao.ottieniMedicidaCognome(cognome);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return medici;
	}
	
	public ArrayList<Visita> ottieniCalendarioMedico(Medico medico) {
		ArrayList<Visita> calendario = null;
		try {
		calendario = VisitaDao.ottieniCalendarioMedico(medico);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return calendario;
	}
	
	public boolean registraMedico(Medico medico) {
		boolean operazioneRiuscita = false;
		try {
		operazioneRiuscita = MedicoDao.creaMedico(medico);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return operazioneRiuscita;
	}
	
	public boolean modificaMedico(Medico medico, String nuovaPass) {
		boolean operazioneRiuscita = false;
		try {
		operazioneRiuscita = MedicoDao.modificaPass(medico, nuovaPass);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return operazioneRiuscita;
	}
	
	public boolean cancellaMedico(Medico medico){
		boolean operazioneRiuscita = false;
		try {
		operazioneRiuscita = MedicoDao.eliminaMedico(medico);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return operazioneRiuscita;
	}
	
	public Visita ottieniVisita(Medico medico, int codice){
		Visita visita = null;
		try {
		visita = VisitaDao.ottieniVisitaMedico(codice, medico);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return visita;
	}
	
	public boolean aggiungiVisita(Visita visita){
		boolean operazioneRiuscita = false;
		try {
		operazioneRiuscita = VisitaDao.creaVisita(visita);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return operazioneRiuscita;
	}
	
	public boolean modificaVisita(Visita visita) {
		boolean operazioneRiuscita = false;
		try {
		operazioneRiuscita = VisitaDao.modificaVisita(visita);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return operazioneRiuscita;
	}
	
	public boolean cancellaVisita(Visita visita) {
		boolean operazioneRiuscita = false;
		try {
		operazioneRiuscita = VisitaDao.eliminaVisita(visita);
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return operazioneRiuscita;
	}
	
}