package ASL.is.DAO;

import ASL.is.entity.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;


public class PrenotazioneDao extends ConnectionManager{
	public static boolean creaPrenotazione(Prenotazione nuovaPrenotazione) throws DAOException {
    	PreparedStatement preparedStatement = null;
    	boolean avvenutaPrenotazione = false;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "INSERT INTO Prenotazioni (paziente, prezzo, visita, data_visita, codice_operatore, codiceqr) VALUES (?, ?, ?, ?, ?, ?)" ;
    		preparedStatement = connessione.prepareStatement(QUERY_SQL, Statement.RETURN_GENERATED_KEYS);
    		preparedStatement.setString(1, nuovaPrenotazione.getUtentePrenotato().getUsername());
    		preparedStatement.setDouble(2, nuovaPrenotazione.getPrezzo());
    		preparedStatement.setInt(3, nuovaPrenotazione.getOrarioPrenotato().getIdOrario());
    		preparedStatement.setString(4, nuovaPrenotazione.getData().toString());
    		if(nuovaPrenotazione.getOperatorePrenotante() != null) {
    			preparedStatement.setInt(5, nuovaPrenotazione.getOperatorePrenotante().getCodiceOp());
    		}
    		else
    			preparedStatement.setNull(5,java.sql.Types.INTEGER);
    		preparedStatement.setString(6, nuovaPrenotazione.getQR().getPath());
    		preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
            	int codicePrenotazione = resultSet.getInt(1);
                if (resultSet.wasNull() == false) {
                	avvenutaPrenotazione = true;
                    nuovaPrenotazione.setIdPrenotazione(codicePrenotazione);;
                }
            }
            resultSet.close();
            
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile creare la prenotazione\n", e);
        } finally {
        	if (preparedStatement != null) {
        		SilentlyClose(preparedStatement);
        	}
        	SilentlyClose(connessione);
        }
    	return avvenutaPrenotazione;
    }
	
	public static Prenotazione ottieniPrenotazione(int codicePrenotazione) throws DAOException {
    	Prenotazione prenotazioneRicercata = null;
    	Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Prenotazioni WHERE id_prenotazione=" + codicePrenotazione;
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            if (resultSet.next()) {
            	prenotazioneRicercata = new Prenotazione();
            	prenotazioneRicercata.setUtentePrenotato(UtenteDao.ottieniUtenteDaUsername(resultSet.getString(2)));
            	prenotazioneRicercata.setOrarioPrenotato(VisitaDao.ottieniVisita(resultSet.getInt(4)));
            	prenotazioneRicercata.setData(LocalDate.of(Integer.parseInt(resultSet.getString(5).substring(0, 4)), Integer.parseInt(resultSet.getString(5).substring(5, 7)) , Integer.parseInt(resultSet.getString(5).substring(8, 10))));
            	prenotazioneRicercata.setIdPrenotazione(resultSet.getInt(1));
            	prenotazioneRicercata.setPrezzo(resultSet.getDouble(3));
            }
            
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile ottenere la prenotazione\n", e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return prenotazioneRicercata;
    }
	
	public static ArrayList<Prenotazione> ottieniPrenotazioniUtente(Utente utente, LocalDate dataInizio, LocalDate dataFine) throws DAOException {
    	ArrayList<Prenotazione> prenotazioniRicercate = new ArrayList<Prenotazione>();
    	Prenotazione prenotazioneTrovata;
    	Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Prenotazioni WHERE paziente='" + utente.getUsername() +  "'" + "AND data_visita >= ' " + dataInizio.toString() + "' AND data_visita <= ' " + dataFine.toString() + "'" ;
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            while (resultSet.next()) {
            	prenotazioneTrovata = new Prenotazione();
            	prenotazioneTrovata.setUtentePrenotato(utente);
            	prenotazioneTrovata.setOrarioPrenotato(VisitaDao.ottieniVisita(resultSet.getInt(4)));
            	prenotazioneTrovata.setData(LocalDate.of(Integer.parseInt(resultSet.getString(5).substring(0, 4)), Integer.parseInt(resultSet.getString(5).substring(5, 7)) , Integer.parseInt(resultSet.getString(5).substring(8, 10))));
            	prenotazioneTrovata.setIdPrenotazione(resultSet.getInt(1));
            	prenotazioneTrovata.setPrezzo(resultSet.getDouble(3));
            	prenotazioniRicercate.add(prenotazioneTrovata);
            }
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile ottenere la prenotazione\n", e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return prenotazioniRicercate;
    }
	
	public static ArrayList<Prenotazione> ottieniPrenotazioniDaVisita(Visita visita, LocalDate dataInizio, LocalDate dataFine) throws DAOException {
    	ArrayList<Prenotazione> prenotazioniRicercate = new ArrayList<Prenotazione>();
    	Prenotazione prenotazioneTrovata;
    	Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Prenotazioni WHERE visita='" + visita.getIdOrario() +  "'" + "AND data_visita >= ' " + dataInizio.toString() + "' AND data_visita <= ' " + dataFine.toString() + "'" ;
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            while (resultSet.next()) {
            	prenotazioneTrovata = new Prenotazione();
            	prenotazioneTrovata.setUtentePrenotato(UtenteDao.ottieniUtenteDaUsername(resultSet.getString(2)));
            	prenotazioneTrovata.setOrarioPrenotato(visita);
            	prenotazioneTrovata.setData(LocalDate.of(Integer.parseInt(resultSet.getString(5).substring(0, 4)), Integer.parseInt(resultSet.getString(5).substring(5, 7)) , Integer.parseInt(resultSet.getString(5).substring(8, 10))));
            	prenotazioneTrovata.setIdPrenotazione(resultSet.getInt(1));
            	prenotazioneTrovata.setPrezzo(resultSet.getDouble(3));
            	prenotazioniRicercate.add(prenotazioneTrovata);
            }
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile ottenere la prenotazione\n", e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return prenotazioniRicercate;
    }
	
	public static ArrayList<Prenotazione> ottieniPrenotazioniMedico(Medico medico, LocalDate dataInizio, LocalDate dataFine) throws DAOException {
    	ArrayList<Prenotazione> prenotazioniRicercate = new ArrayList<Prenotazione>();
    	Prenotazione prenotazioneTrovata;
    	Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT id_prenotazione, paziente, prezzo, visita, data_visita, codice_operatore, codiceqr "
    				+ "FROM Prenotazioni\r\n"
    				+ "INNER JOIN Visite\r\n"
    				+ "ON Visite.id_orario= Prenotazioni.visita\r\n"
    				+ "WHERE Visite.medico_riferimento='" + medico.getUsername() + "'" +"AND data_visita >= ' " + dataInizio.toString() + "' AND data_visita <= ' " + dataFine.toString() + "'"
    				+ " ORDER BY data_visita";
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            while (resultSet.next()) {
            	prenotazioneTrovata = new Prenotazione();
            	prenotazioneTrovata.setUtentePrenotato(UtenteDao.ottieniUtenteDaUsername(resultSet.getString(2)));
            	prenotazioneTrovata.setOrarioPrenotato(VisitaDao.ottieniVisita(resultSet.getInt(4)));
            	prenotazioneTrovata.setData(LocalDate.of(Integer.parseInt(resultSet.getString(5).substring(0, 4)), Integer.parseInt(resultSet.getString(5).substring(5, 7)) , Integer.parseInt(resultSet.getString(5).substring(8, 10))));
            	prenotazioneTrovata.setIdPrenotazione(resultSet.getInt(1));
            	prenotazioneTrovata.setPrezzo(resultSet.getDouble(3));
            	prenotazioniRicercate.add(prenotazioneTrovata);
            }
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile ottenere la prenotazione\n", e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return prenotazioniRicercate;
    }
}
