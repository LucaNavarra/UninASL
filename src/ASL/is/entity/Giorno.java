package ASL.is.entity;

public enum Giorno {
	 Lunedi(1),
	 Martedi(2),
	 Mercoledi(3),
	 Giovedi(4),
	 Venerdi(5),
	 Sabato(6),
	 Domenica(7);
	
	final private int giorno;
	private Giorno(int giorno) {
		this.giorno = giorno;
	}
	
	public int getNumeroGiorno() {
		return this.giorno;
	}
}
