package ASL.is.control;

import ASL.is.DAO.*;
import ASL.is.entity.*;

public class GestoreAutenticazione<Attore> {

	private static int istanza = 0;
	private Attore autenticazione;

	
	protected GestoreAutenticazione(Attore attore) {
		autenticazione = attore;
	}
	
	
	public static GestoreAutenticazione<Utente> loginUtente(String user, String password) {
		GestoreAutenticazione<Utente> gest = null;
		Utente utente = null;
		try {
		if(istanza == 0) {
				utente = UtenteDao.ottieniUtenteDaUsername(user);
				if(utente != null) {
					if(utente.getPassword().compareTo(password) == 0) {
						gest = new GestoreAutenticazione<Utente>(utente);
						istanza  = 1;
					}
				}
		}
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return gest;
	}
	public static GestoreAutenticazione<Medico> loginMedico(String user, String password) {
		GestoreAutenticazione<Medico> gest = null;
		Medico medico = null;
		try {
		if(istanza == 0) {
			medico = MedicoDao.ottieniMedico(user);
			if(medico != null) {
				if(medico.getPassword().compareTo(password) == 0) {
					gest = new GestoreAutenticazione<Medico>(medico);
					istanza  = 1;
				}
			}
		}
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return gest;
	}
	public static GestoreAutenticazione<Operatore> loginOperatore(int codice, String password) {
		GestoreAutenticazione<Operatore> gest = null;
		Operatore operatore = null;
		try {
		if(istanza == 0) {
			operatore = OperatoreDao.ottieniOperatore(codice);
			if(operatore != null) {
				if(operatore.getPassword().compareTo(password) == 0){
					gest = new GestoreAutenticazione<Operatore>(operatore);
					istanza  = 1;
				}
			}
		}
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return gest;
	}
	
	public void logout() {
		istanza = 0;
		this.autenticazione = null;
	}

	public boolean statoLogin() {
		if(this.autenticazione == null)
			return false;
		else
			return true;
	}
	
	public Attore datiLogin() {
		return this.autenticazione;
	}
	
}
