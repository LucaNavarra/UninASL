package ASL.is.entity;


public enum Specializzazione {
		ortopedia(0,45,44.20),
		remautologia(0,30,43.21),
		geriatria(0,30, 55.80),
		oncologia(0,45, 34.50),
		allergologia_e_immunologia(1,0,87.43),
		dermatologia_e_veneorologia(0,20,85.80),
		ematologia(0,15,44.42),
		endocrinologia(0,30,63.40),
		gastroenterologia(1,30,93.22),
		cardiologia(0,20,67.45),
		pneumologia(0,45,28.50),
		malattie_infettive(2,0,44.10),
		nefrologia(1,0,120.45),
		neurologia(1,0, 144.90),
		neuropsichiatria_infantile(1,30,120.15),
		psichiatria(1,30,80),
		pediatria(0,30,50),
		ginecologia(0,20,30.67),
		urologia(0,30,55.80),
		oftalmologia(1,0,57.48),
		otorinolaringoiatria(0,30,24.80),
		radiodiagnostica(0,20,90.56),
		radioterapia(2,0,70.44),
		medicina_nucleare(2,0,55.80),
		anestesia(0,20,49.23),
		audiologia_e_foniatria(0,15,44.30),
		medicina_fisica_e_riabilitativa(1,0,98.40),
		farmacologia_e_tossicologia_clinica(1,0,124.40),
		igiene_e_medicina_preventiva(0,15,88.45),
		medicina_del_lavoro(0,30,52),
		madicina_legale(1,0,93.34),
		statistica_sanitaria_e_biometria(1,0,45.44),
		medico_di_base(0,15,40.35);
		
		private Specializzazione(int ore, int minuti, double costo) {
		this.ore = ore;
		this.minuti = minuti;
		this.costo = costo;
		}
	
		private final int ore;
		private final int minuti;
		private final double costo;
		
		public int getOre(){
			return ore;
		} 
		
		public int getMinuti() {
			return minuti;
		}
		
		public int getDurataInMinuti() {
			return (minuti + ore*60);
		}
		
		public double getPrezzo() {
			return this.costo;
		}
		
		public String toStringa() {
			Specializzazione spec = this;
			String s = "Specializzazione";
			
			if(spec == Specializzazione.allergologia_e_immunologia) s = "Allergologia e immunologia";
			if(spec == Specializzazione.anestesia) s = "Anestesia";
			if(spec == Specializzazione.audiologia_e_foniatria) s = "Audiologia e foniatria";
			if(spec == Specializzazione.cardiologia) s = "Cardiologia";
			if(spec == Specializzazione.dermatologia_e_veneorologia) s = "Dermatologia e veneorologia";
			if(spec == Specializzazione.ematologia) s = "Ematologia";
			if(spec == Specializzazione.endocrinologia) s = "Endocrinologia";
			if(spec == Specializzazione.farmacologia_e_tossicologia_clinica) s = "Farmacologia e tossicologia clinica";
			if(spec == Specializzazione.gastroenterologia) s = "Gastroenterologia";
			if(spec == Specializzazione.geriatria) s = "Geriatria";
			if(spec == Specializzazione.ginecologia) s = "Ginecologia";
			if(spec == Specializzazione.igiene_e_medicina_preventiva) s = "Igiene e medicina preventiva";
			if(spec == Specializzazione.madicina_legale) s = "Medicina legale";
			if(spec == Specializzazione.malattie_infettive) s = "Malattie infettive";
			if(spec == Specializzazione.medicina_del_lavoro) s = "Medicina del lavoro";
			if(spec == Specializzazione.medicina_fisica_e_riabilitativa) s = "Medicina fisica e riabilitativa";
			if(spec == Specializzazione.medicina_nucleare) s = "Medicina nucleare";
			if(spec == Specializzazione.medico_di_base) s = "Medico di base";
			if(spec == Specializzazione.nefrologia) s = "Nefrologia";
			if(spec == Specializzazione.neurologia) s = "Neurologia";
			if(spec == Specializzazione.neuropsichiatria_infantile) s = "Neuropsichiatria infantile";
			if(spec == Specializzazione.oftalmologia) s = "Oftalmologia";
			if(spec == Specializzazione.oncologia) s = "Oncologia";
			if(spec == Specializzazione.ortopedia) s = "Ortopedia";
			if(spec == Specializzazione.otorinolaringoiatria) s = "Otorinolaringoiatria";
			if(spec == Specializzazione.pediatria) s = "Pediatria";
			if(spec == Specializzazione.pneumologia) s = "Pneumologia";
			if(spec == Specializzazione.psichiatria) s = "Psichiatria";
			if(spec == Specializzazione.radiodiagnostica) s = "Radiodiagnostica";
			if(spec == Specializzazione.radioterapia) s = "Radioterapia";
			if(spec == Specializzazione.remautologia) s = "Reumatologia";
			if(spec == Specializzazione.statistica_sanitaria_e_biometria) s = "Statistica sanitaria e biometria";
			if(spec == Specializzazione.urologia) s = "Urologia";
			
			return s;
		}
}
