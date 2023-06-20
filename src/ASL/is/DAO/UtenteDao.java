package ASL.is.DAO;

import ASL.is.entity.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class UtenteDao extends ConnectionManager{
	public static boolean registraUtente(Utente nuovoUser) throws DAOException {
    	PreparedStatement preparedStatement = null;
    	boolean utenteCreato = false;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "INSERT INTO Utenti (username, password, nome, cognome, cf, data_nascita, luogo_nascita, residenza, numero_telefono, email, ISEE, grado_disabilita) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    		preparedStatement = connessione.prepareStatement(QUERY_SQL);
    	
    		preparedStatement.setString(1, nuovoUser.getUsername());
    		preparedStatement.setString(2, nuovoUser.getPassword());
    		preparedStatement.setString(3, nuovoUser.getNome());
    		preparedStatement.setString(4, nuovoUser.getCognome());
    		preparedStatement.setString(5, nuovoUser.getCf().toUpperCase());
    		preparedStatement.setString(6, nuovoUser.getDataNascita().toString());
    		preparedStatement.setString(7, nuovoUser.getLuogoNascita());
    		preparedStatement.setString(8, nuovoUser.getResidenza());
    		preparedStatement.setString(9, nuovoUser.getNumeroTelefono());
    		preparedStatement.setString(10, nuovoUser.getEmail());
    		preparedStatement.setInt(11, nuovoUser.getIsee());
    		preparedStatement.setString(12, nuovoUser.getDisabilitaUtente().toString());
    		
    		
    		preparedStatement.executeUpdate();
    		utenteCreato = true;
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException ("Impossibile registrare l'utente\n", e);
        } finally {
        	if (preparedStatement != null) {
        		SilentlyClose(preparedStatement);
        	}
        	SilentlyClose(connessione);
        }
    	return utenteCreato;
    }
	
	public static boolean modificaUtente(Utente utente) throws DAOException {
    	PreparedStatement preparedStatement = null;
    	boolean modificaUtente = false;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "UPDATE Utenti"
    				+ " SET username = '"+ utente.getUsername() +"',"
    				+ " password = '"+ utente.getPassword() +"',"
    				+ " cognome = '"+ utente.getCognome() +"',"
    				+ " cf = '"+ utente.getCf() +"',"
    				+ " data_nascita = '"+ utente.getDataNascita() +"',"
    				+ " luogo_nascita = '"+ utente.getLuogoNascita() +"',"
    				+ " residenza = '"+ utente.getResidenza() +"',"
    	    		+ " numero_telefono = '"+ utente.getNumeroTelefono() +"',"
    	    		+ " email = '"+ utente.getEmail() +"',"
    	    		+ " ISEE ="+ utente.getIsee()
    	    	    + ", grado_disabilita = '"+ utente.getDisabilitaUtente() +"'"
    				+" WHERE username ='" + utente.getUsername() + "'";
    		preparedStatement = connessione.prepareStatement(QUERY_SQL);
    		
    		if(preparedStatement.executeUpdate() != 0)
    			modificaUtente = true;
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException ("Impossibile modificare l'utente\n", e);
        } finally {
        	if (preparedStatement != null) {
        		SilentlyClose(preparedStatement);
        	}
        	SilentlyClose(connessione);
        }
    	return modificaUtente;
    }
	
	public static boolean eliminaUtente(Utente utente) throws DAOException {
    	PreparedStatement preparedStatement = null;
    	boolean utenteEliminato = false;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "DELETE FROM Utenti WHERE username ='" + utente.getUsername() + "'";
    		preparedStatement = connessione.prepareStatement(QUERY_SQL);
    		
    		if(preparedStatement.executeUpdate() != 0)
    			utenteEliminato = true;
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException ("Impossibile eliminare l'utente\n", e);
        } finally {
        	if (preparedStatement != null) {
        		SilentlyClose(preparedStatement);
        	}
        	SilentlyClose(connessione);
        }
    	return utenteEliminato;
    }
	
	public static Utente ottieniUtenteDaUsername(String usernameCercato) throws DAOException {
    	Utente userRicercato = null;
    	Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Utenti WHERE username='" + usernameCercato + "'";
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            if (resultSet.next()) {
            	/*
            	 String nome, String cognome, String cf, String username, String password, LocalDate dataNascita, String luogoNascita, String residenza, String numeroTelefono, String email, int isee, GradoDisabilita disabilitaUtente
            	 */
                userRicercato = new Utente(resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(1), resultSet.getString(2), 
                		LocalDate.of(Integer.parseInt(resultSet.getString(6).substring(0, 4)), Integer.parseInt(resultSet.getString(6).substring(5, 7)) , Integer.parseInt(resultSet.getString(6).substring(8, 10))), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getInt(11), resultSet.getString(12));
            }
            
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException ("Impossibile ottenere l'utente\n", e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return userRicercato;
    }
	
	public static Utente ottieniUtenteDaCF(String cfCercato) throws DAOException {
    	Utente userRicercato = null;
    	Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Utenti WHERE cf='" + cfCercato + "'";
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            if (resultSet.next()) {
            	/*
            	 String nome, String cognome, String cf, String username, String password, LocalDate dataNascita, String luogoNascita, String residenza, String numeroTelefono, String email, int isee, GradoDisabilita disabilitaUtente
            	 */
                userRicercato = new Utente(resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(1), resultSet.getString(2), 
                		LocalDate.of(Integer.parseInt(resultSet.getString(6).substring(0, 4)), Integer.parseInt(resultSet.getString(6).substring(5, 7)) , Integer.parseInt(resultSet.getString(6).substring(8, 10))), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getInt(11), resultSet.getString(12));
            }
            
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException ("Impossibile ottenere l'utente\n", e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return userRicercato;
    }
	
	public static Utente ottieniUtenteDaEmail(String emailCercato) throws DAOException {
    	Utente emailRicercato = null;
    	Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Utenti WHERE email='" + emailCercato + "'";
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            if (resultSet.next()) {
            	/*
            	 String nome, String cognome, String cf, String username, String password, LocalDate dataNascita, String luogoNascita, String residenza, String numeroTelefono, String email, int isee, GradoDisabilita disabilitaUtente
            	 */
                emailRicercato = new Utente(resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(1), resultSet.getString(2), 
                		LocalDate.of(Integer.parseInt(resultSet.getString(6).substring(0, 4)), Integer.parseInt(resultSet.getString(6).substring(5, 7)) , Integer.parseInt(resultSet.getString(6).substring(8, 10))), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getInt(11), resultSet.getString(12));
            }
            
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException ("Impossibile ottenere l'utente\n", e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return emailRicercato;
    }
}