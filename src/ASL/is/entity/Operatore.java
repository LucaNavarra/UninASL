package ASL.is.entity;

public class Operatore {
	private int codiceOperatore;
	private	String password;
	private	String nome;
	private	String cognome;
	
	public Operatore(int codiceOperatore, String password, String nome, String cognome) {
		this.codiceOperatore = codiceOperatore;
		this.cognome = cognome;
		this.nome = nome;
		this.password = password;
	}
	
	public Operatore(String password, String nome, String cognome) {
		this.cognome = cognome;
		this.nome = nome;
		this.password = password;
	}
	
	public String getNome() {
		return this.nome;
	}

	public String getCognome() {
		return this.cognome;
	}

	public int getCodiceOp() {
		return this.codiceOperatore;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setCodiceOp(int codiceOperatore) {
		this.codiceOperatore = codiceOperatore;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
