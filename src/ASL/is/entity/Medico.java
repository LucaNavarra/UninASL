package ASL.is.entity;

public class Medico {
	private String nome;
	private	String cognome;
	private	String username;
	private	String password;
	private	Specializzazione specMedico;
	
	public Medico(String nuovoNome, String nuovoCognome, String nuovoUser, String nuovaPass, Specializzazione specMed) {
		this.nome = nuovoNome;
		this.cognome = nuovoCognome;
		this.username = nuovoUser;
		this.password = nuovaPass;
		this.specMedico = specMed;
	}
	
	public String getNome() {
		return this.nome;
	}
	public String getCognome() {
		return this.cognome;
	}
	public String getUsername() {
		return this.username;
	}
	public Specializzazione getSpecializzazione() {
		return this.specMedico;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setNome(String nuovoNome) {
		this.nome = nuovoNome;
	}
	public void setCognome(String nuovoCognome) {
		this.cognome = nuovoCognome;
	}
	public void setUsername(String nuovoUsername) {
		this.username = nuovoUsername;
	}
	public void setSpecializzazione(Specializzazione nuovaSpec) {
		this.specMedico = nuovaSpec;
	}
	public void setPass(String nuovaPass) {
		this.password = nuovaPass;
	}
}
