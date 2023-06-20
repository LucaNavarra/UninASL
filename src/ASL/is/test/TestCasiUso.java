package ASL.is.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import ASL.is.control.*;
import ASL.is.entity.*;
class TestCasiUso {
	static GestoreASL gest = GestoreASL.instance();
	
	@Test
	void testRicercaPerMedico() {
		ArrayList<Prenotazione> dateDisponibili;
		
		/*
		 * TEST ID 1
		 */
		dateDisponibili = gest.ricercaPerMedico("medicoassente", LocalDate.of(2022, 07, 22), LocalDate.of(2022, 07, 29));
		assert(dateDisponibili.isEmpty());
		
		
		/*
		 * TEST ID 2
		 */
		dateDisponibili = gest.ricercaPerMedico("test", LocalDate.of(2022, 07, 9), LocalDate.of(2022, 07, 10));
		assert(dateDisponibili.isEmpty());
		
		
		/*
		 * TEST ID 3
		 */
		dateDisponibili = gest.ricercaPerMedico(null, LocalDate.of(2022, 05, 22), LocalDate.of(2022, 05, 29));
		assert(dateDisponibili.isEmpty());
		
		
		/*
		 * TEST ID 4
		 */
		dateDisponibili = gest.ricercaPerMedico("asndhcndhfjskdjfmcjajdkakdkeiroweirnfjsjcfn2nfncvcnajfwe", LocalDate.of(2022, 07, 9), LocalDate.of(2022, 07, 30));
		assert(dateDisponibili.isEmpty());
		
		
		/*
		 * TEST ID 5
		 */
		dateDisponibili = gest.ricercaPerMedico("test", null, LocalDate.of(2022, 05, 15));
		assert(dateDisponibili.isEmpty());
		
		
		/*
		 * TEST ID 6
		 */
		assertThrows(DateTimeException.class, () -> gest.ricercaPerMedico("test", LocalDate.of(20, 053, 15), LocalDate.of(2022, 05, 15)));
		
		
		/*
		 * TEST ID 7
		 */
		dateDisponibili = gest.ricercaPerMedico("test", LocalDate.of(2021, 5, 15), LocalDate.of(2021, 5, 15));
		assert(dateDisponibili.isEmpty());

		
		/*
		 * TEST ID 8
		 */
		dateDisponibili =  gest.ricercaPerMedico("test", LocalDate.of(2022, 05, 15), null);
		assert(dateDisponibili.isEmpty());
		
		/*
		 * TEST ID 9
		 */
		dateDisponibili = gest.ricercaPerMedico("test", LocalDate.of(2022, 7, 15), LocalDate.of(2022, 6, 30));
		assert(dateDisponibili.isEmpty());
		
		
		/*
		 * TEST ID 10
		 */
		assertThrows(DateTimeException.class, () -> gest.ricercaPerMedico("test", LocalDate.of(2022, 05, 15), LocalDate.of(2022, 053, 15)));
		
		
		/*
		 * TEST ID 11
		 */
		dateDisponibili = gest.ricercaPerMedico("test", LocalDate.of(2022, 7, 15), LocalDate.of(2022, 7, 30));
		assert(dateDisponibili.size() > 0);
		
		
		/*
		 * TEST ID 12
		 */
		dateDisponibili = gest.ricercaPerMedico("test", LocalDate.of(2022, 7, 14), LocalDate.of(2022, 7, 16));
		assert(dateDisponibili.isEmpty());
	}

	@Test
	void testRicercaPerSpecializzazione() {
		ArrayList<Prenotazione> dateDisponibili;
		
		/*
		 * TEST ID 1
		 */
		dateDisponibili = gest.ricercaPerSpecializzazione(Specializzazione.cardiologia, LocalDate.of(2022, 07, 22), LocalDate.of(2022, 07, 29));
		assert(dateDisponibili.isEmpty());
		
		
		/*
		 * TEST ID 2
		 */
		dateDisponibili = gest.ricercaPerSpecializzazione(Specializzazione.geriatria, LocalDate.of(2022, 07, 22), LocalDate.of(2022, 07, 29));
		assert(dateDisponibili.isEmpty());
		
		
		/*
		 * TEST ID 3
		 */
		assertThrows(NullPointerException.class, () -> gest.ricercaPerSpecializzazione(null, LocalDate.of(2022, 05, 22), LocalDate.of(2022, 05, 29)));
		
		
		/*
		 * TEST ID 4
		 */
		//Il test 4 non ha senso con la seguente implementazione
		
		
		/*
		 * TEST ID 5
		 */
		dateDisponibili = gest.ricercaPerSpecializzazione(Specializzazione.ginecologia, null, LocalDate.of(2022, 05, 15));
		assert(dateDisponibili.isEmpty());
		
		
		/*
		 * TEST ID 6
		 */
		dateDisponibili = gest.ricercaPerSpecializzazione(Specializzazione.ginecologia, LocalDate.of(22, 05, 15), LocalDate.of(2022, 05, 15));
		assert(dateDisponibili.isEmpty());
		
		
		/*
		 * TEST ID 7
		 */
		dateDisponibili = gest.ricercaPerSpecializzazione(Specializzazione.geriatria, LocalDate.of(2021, 5, 15), LocalDate.of(2022, 8, 15));
		assert(dateDisponibili.isEmpty());

		
		/*
		 * TEST ID 8
		 */
		dateDisponibili = gest.ricercaPerSpecializzazione(Specializzazione.ginecologia, LocalDate.of(2022, 05, 15), null);
		assert(dateDisponibili.isEmpty());
		
		
		/*
		 * TEST ID 9
		 */
		dateDisponibili = gest.ricercaPerSpecializzazione(Specializzazione.otorinolaringoiatria, LocalDate.of(2022, 7, 15), LocalDate.of(2022, 6, 30));
		assert(dateDisponibili.isEmpty());
		
		
		/*
		 * TEST ID 10
		 */
		assertThrows(DateTimeException.class, () -> gest.ricercaPerSpecializzazione(Specializzazione.ginecologia, LocalDate.of(2022, 05, 15), LocalDate.of(2022, 053, 15)));
		
		
		/*
		 * TEST ID 11
		 */
		dateDisponibili = gest.ricercaPerSpecializzazione(Specializzazione.otorinolaringoiatria, LocalDate.of(2022, 7, 15), LocalDate.of(2022, 7, 30));
		assert(dateDisponibili.size() > 0);
		
		
		/*
		 * TEST ID 12
		 */
		dateDisponibili = gest.ricercaPerSpecializzazione(Specializzazione.ginecologia, LocalDate.of(2022, 7, 15), LocalDate.of(2022, 7, 20));
		assert(dateDisponibili.isEmpty());
		
	}

	@Test
	void testVisualizzaAppuntamenti() {
		GestoreAutenticazione<Medico> autenticazioneMedico;
		ArrayList<Prenotazione> listaPrenotazioni;
		
		//precondizione comune a tutti i casi di test: Login medico
		autenticazioneMedico = GestoreAutenticazione.loginMedico("test", "test");
		
		/*
		 * TEST ID 1
		 */
		listaPrenotazioni = gest.visualizzaAppuntamenti(autenticazioneMedico.datiLogin(), LocalDate.of(2022, 05, 14), LocalDate.of(2022, 05, 15));
		assert(listaPrenotazioni.isEmpty());
		
		
		/*
		 * TEST ID 2
		 */
		listaPrenotazioni = gest.visualizzaAppuntamenti(autenticazioneMedico.datiLogin(), LocalDate.of(2022, 05, 24), LocalDate.of(2022, 05, 26));
		assert(listaPrenotazioni.isEmpty());
		
		
		/*
		 * TEST ID 3
		 */
		assertThrows(NullPointerException.class, () -> gest.visualizzaAppuntamenti(autenticazioneMedico.datiLogin(), null, LocalDate.of(2022, 05, 15)));
		
		
		/*
		 * TEST ID 4
		 */
		assertThrows(DateTimeException.class, () -> gest.visualizzaAppuntamenti(autenticazioneMedico.datiLogin(), LocalDate.of(2022, 052, 14), LocalDate.of(2022, 05, 15)));
		
		
		/*
		 * TEST ID 5
		 */
		assertThrows(NullPointerException.class, () -> gest.visualizzaAppuntamenti(autenticazioneMedico.datiLogin(), LocalDate.of(2022, 05, 15), null));
		
		
		/*
		 * TEST ID 6
		 */
		listaPrenotazioni = gest.visualizzaAppuntamenti(autenticazioneMedico.datiLogin(), LocalDate.of(2022, 05, 15), LocalDate.of(2022, 05, 14));
		assertEquals(0, listaPrenotazioni.size());
		
		
		/*
		 * TEST ID 7
		 */
		assertThrows(DateTimeException.class, () -> gest.visualizzaAppuntamenti(autenticazioneMedico.datiLogin(), LocalDate.of(2022, 05, 14), LocalDate.of(20242, 045, 15)));
		
		
		/*
		 * TEST ID 8
		 */
		listaPrenotazioni = gest.visualizzaAppuntamenti(autenticazioneMedico.datiLogin(), LocalDate.of(2022, 05, 9), LocalDate.of(2022, 05, 22));
		assert(listaPrenotazioni.size() > 0);
		
	}

}
