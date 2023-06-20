package ASL.is.entity;

import java.io.File;
import java.time.LocalDate;

public class Prenotazione {
	private Utente utentePrenotato;
	private Operatore operatorePrenotante;
	private Visita orarioPrenotato;
	private int idPrenotazione;
	private	double prezzo;
	private	LocalDate data;
	private File QR;
	
	/**
     * Controlla se il giorno della settimana corrisponde con la data
     * Es. controlla se 30/05/2022 è lunedì
     *
     *
     * @return true se è corretto, false se è scorretto
     */
	private static boolean controlloDataGiorno(Giorno giornoSettimana, LocalDate data) {
		if(giornoSettimana.getNumeroGiorno() == data.getDayOfWeek().getValue())
			return true;
		else
			return false;
	}
	
	/**
     * Calcola il prezzo della visita in base a specializzazione, isee e disabilità
     */
	private void calcolaPrezzo() {
		if(this.orarioPrenotato.getMedico() != null)
			this.prezzo = orarioPrenotato.getMedico().getSpecializzazione().getPrezzo();
		
		//Vanno considerati tutti gli altri casi
	}
	//private	Image codiceQR;
	public Prenotazione(Utente utentePrenotato, Operatore operatorePrenotante, Visita orarioPrenotato, int iDPrenotazione, LocalDate data) {
		this.utentePrenotato = utentePrenotato;
		this.operatorePrenotante = operatorePrenotante;
		this.orarioPrenotato = orarioPrenotato;
		this.idPrenotazione = iDPrenotazione;
		this.calcolaPrezzo();
		if(controlloDataGiorno(orarioPrenotato.getGiornoSettimana(), data))
			this.data = data;
		else
			this.data = null;
	}
	
	public Prenotazione() {
		super();
	}
	
	public Utente getUtentePrenotato() {
		return utentePrenotato;
	}
	
	public void setUtentePrenotato(Utente utentePrenotato) {
		this.utentePrenotato = utentePrenotato;
	}
	
	public Operatore getOperatorePrenotante() {
		return operatorePrenotante;
	}
	
	public void setOperatorePrenotante(Operatore operatorePrenotante) {
		this.operatorePrenotante = operatorePrenotante;
	}
	
	public Visita getOrarioPrenotato() {
		return orarioPrenotato;
	}
	
	public void setOrarioPrenotato(Visita orarioPrenotato) {
		if(this.data != null) {
			if(controlloDataGiorno(orarioPrenotato.getGiornoSettimana(), this.data)) {
				this.orarioPrenotato = orarioPrenotato;
			}
		}
		else {
			this.orarioPrenotato = orarioPrenotato;
		}
	}
	
	public int getIdPrenotazione() {
		return idPrenotazione;
	}
	
	public void setIdPrenotazione(int idPrenotazione) {
		this.idPrenotazione = idPrenotazione;
	}
	
	public double getPrezzo() {
		return prezzo;
	}
	
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public void setData(LocalDate data) {
		if(this.getOrarioPrenotato() != null) {
			if(controlloDataGiorno(this.getOrarioPrenotato().getGiornoSettimana(), data))
				this.data = data;
		}
		else
			this.data = data;
	}
	
	public File getQR() {
		return QR;
	}
	
	public void setQRPrenotazione(File f) {
		this.QR = f;
	}
}
