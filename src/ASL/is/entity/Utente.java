package ASL.is.entity;

import java.time.LocalDate;

public class Utente {
	private String nome;
	private	String cognome;
	private	String cf;
	private	String username;
	private	String password;
	private	LocalDate dataNascita;
	private	String luogoNascita;
	private	String residenza;
	private	String numeroTelefono;
	private	String email;
	private	int isee;
	private GradoDisabilita disabilitaUtente;
	
	public Utente(String nome, String cognome, String cf, String username, String password, LocalDate dataNascita, String luogoNascita, String residenza, String numeroTelefono, String email, int isee, GradoDisabilita disabilitaUtente) {
		this.nome = nome;
		this.cognome = cognome;
		this.cf = cf;
		this.username = username;
		this.password = password;
		this.dataNascita = dataNascita;
		this.luogoNascita = luogoNascita;
		this.residenza = residenza;
		this.numeroTelefono = numeroTelefono;
		this.email = email;
		this.isee = isee;
		this.disabilitaUtente = disabilitaUtente;
	}
	
	public Utente(String nome, String cognome, String cf, String username, String password, LocalDate dataNascita, String luogoNascita, String residenza, String numeroTelefono, String email, int isee, String disabilitaUtente) {
		this.nome = nome;
		this.cognome = cognome;
		this.cf = cf;
		this.username = username;
		this.password = password;
		this.dataNascita = dataNascita;
		this.luogoNascita = luogoNascita;
		this.residenza = residenza;
		this.numeroTelefono = numeroTelefono;
		this.email = email;
		this.isee = isee;
		this.disabilitaUtente = GradoDisabilita.valueOf(disabilitaUtente);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getResidenza() {
		return residenza;
	}

	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsee() {
		return isee;
	}

	public void setIsee(int isee) {
		this.isee = isee;
	}

	public GradoDisabilita getDisabilitaUtente() {
		return disabilitaUtente;
	}

	public void setDisabilitaUtente(GradoDisabilita disabilitaUtente) {
		this.disabilitaUtente = disabilitaUtente;
	}
	
	
}