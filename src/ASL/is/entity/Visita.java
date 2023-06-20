package ASL.is.entity;

public class Visita {
	private  String stanza;
	private	 Giorno giornoVisita;
	private	 int oraInizio;
	private	 int minutiInizio;
	private  int oraFine;
	private  int minutiFine;
	private Medico MedRiferimento;
	private int idOrario;
	
	public Visita(String nuovaStanza, Giorno nuovoGiorno, int minutiInizio, int oraInizio, Medico Med, int idOrario) {
		if(minutiInizio < 60 && minutiInizio >= 0 && oraInizio < 24 && oraInizio >= 0) {
			int minutiTot = minutiInizio + oraInizio * 60 + Med.getSpecializzazione().getDurataInMinuti();
			this.stanza = nuovaStanza;
			this.giornoVisita = nuovoGiorno;
			this.minutiInizio = minutiInizio;
			this.oraInizio = oraInizio;
			this.oraFine = (minutiTot) / 60;
			this.minutiFine = (minutiTot) % 60;
			this.MedRiferimento = Med;
			this.idOrario = idOrario;
		}
		else {
			this.stanza = null;
			this.giornoVisita = null;
			this.MedRiferimento = null;
		}
	}
	
	public Visita(String nuovaStanza, Giorno nuovoGiorno, int minutiInizio, int oraInizio, Medico Med) {
		if(minutiInizio < 60 && minutiInizio >= 0 && oraInizio < 24 && oraInizio >= 0) {
			int minutiTot = minutiInizio + oraInizio * 60 + Med.getSpecializzazione().getDurataInMinuti();
			this.stanza = nuovaStanza;
			this.giornoVisita = nuovoGiorno;
			this.minutiInizio = minutiInizio;
			this.oraInizio = oraInizio;
			this.oraFine = (minutiTot) / 60;
			this.minutiFine = (minutiTot) % 60;
			this.MedRiferimento = Med;
		}
		else {
			this.stanza = null;
			this.giornoVisita = null;
			this.MedRiferimento = null;
		}
	}
	
	public String getStanza() {
		return this.stanza;
	}
	
	public Giorno getGiornoSettimana() {
		return this.giornoVisita;
	}
	
	public int getOraInizio(){
		return this.oraInizio;
	} 
	
	public int getMinutiInizio() {
		return this.minutiInizio;
	}
	
	public int getDurataInMinutiInizio() {
		return (this.minutiInizio + this.oraInizio*60);
	}
	
	public int getOraFine(){
		return this.oraFine;
	} 
	
	public int getMinutiFine() {
		return this.minutiFine;
	}
	
	public int getDurataInMinutiFine() {
		return (this.minutiFine +this.oraFine*60);
	}
	
	public Medico getMedico() {
		return this.MedRiferimento;
	}
	
	public void setStanza(String stanza) {
		this.stanza = stanza;
	}
	
	public void setGiornoSettimana(Giorno giorno) {
		this.giornoVisita = giorno;
	}
	
	public void setOrarioInizio(int oraInizio, int minutiInizio){
		int minutiTot = minutiInizio + oraInizio * 60 + this.MedRiferimento.getSpecializzazione().getDurataInMinuti();
		this.oraInizio = oraInizio;
		this.minutiInizio = minutiInizio;
		this.oraFine = (minutiTot) / 60;
		this.minutiFine = (minutiTot) % 60;
	} 
	
	public void setMedico(Medico medico) {
		this.MedRiferimento = medico;
	}
	
	public int getIdOrario() {
		return this.idOrario;
	}
	
	public void setIdOrario(int idOrario) {
		this.idOrario = idOrario;
	}
	
}
