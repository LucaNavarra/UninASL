package ASL.is.DAO;

import ASL.is.entity.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MedicoDao extends ConnectionManager{
	public static boolean creaMedico(Medico nuovoMedico) throws DAOException {
    	PreparedStatement preparedStatement = null;
    	boolean medicoCreato = false;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "INSERT INTO Medici (username, password, nome, cognome, specializzazione) VALUES (?, ?, ?, ?, ?)" ;
    		preparedStatement = connessione.prepareStatement(QUERY_SQL);
    		preparedStatement.setString(1, nuovoMedico.getUsername());
    		preparedStatement.setString(2, nuovoMedico.getPassword());
    		preparedStatement.setString(3, nuovoMedico.getNome());
    		preparedStatement.setString(4, nuovoMedico.getCognome());
    		preparedStatement.setString(5, nuovoMedico.getSpecializzazione().toString());
    		
    		preparedStatement.executeUpdate();
    		medicoCreato = true;
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile creare il medico\n", e);
        } finally {
        	if (preparedStatement != null) {
        		SilentlyClose(preparedStatement);
        	}
        	SilentlyClose(connessione);
        }
    	return medicoCreato;
    }
	
	public static boolean modificaPass(Medico medico, String nuovaPass) throws DAOException {
    	PreparedStatement preparedStatement = null;
    	boolean passCambiata = false;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "UPDATE Medici SET password = '"+ nuovaPass +"'"+" WHERE username ='" + medico.getUsername() + "'";
    		preparedStatement = connessione.prepareStatement(QUERY_SQL);
    		
    		if(preparedStatement.executeUpdate() != 0)
    			passCambiata = true;
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile modificare il medico\n", e);
        } finally {
        	if (preparedStatement != null) {
        		SilentlyClose(preparedStatement);
        	}
        	SilentlyClose(connessione);
        }
    	return passCambiata;
    }
	
	public static boolean eliminaMedico(Medico medico) throws DAOException {
    	PreparedStatement preparedStatement = null;
    	boolean medicoEliminato = false;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "DELETE FROM Medici WHERE username ='" + medico.getUsername() + "'";
    		preparedStatement = connessione.prepareStatement(QUERY_SQL);
    		
    		if(preparedStatement.executeUpdate() != 0)
    			medicoEliminato = true;
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile modificare il medico\n", e);
        } finally {
        	if (preparedStatement != null) {
        		SilentlyClose(preparedStatement);
        	}
        	SilentlyClose(connessione);
        }
    	return medicoEliminato;
    }
	
	public static Medico ottieniMedico(String userMedico) throws DAOException {
    	Medico medRicercato = null;
    	Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Medici WHERE username=" + "'" + userMedico + "'";
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            if (resultSet.next()) {
                medRicercato = new Medico(resultSet.getString(3), resultSet.getString(4), resultSet.getString(1), resultSet.getString(2), Specializzazione.valueOf(resultSet.getString(5)));
            }
            
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile ottenere il medico\n", e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return medRicercato;
    }
	
	public static ArrayList<Medico> ottieniMedicidaCognome(String cognome) throws DAOException {
		ArrayList<Medico> mediciDiSpec = new ArrayList<Medico>();
		Medico medicoTrovato = null;
		Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Medici WHERE nome='" + cognome + "'";
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            while (resultSet.next()) {
            	
            	medicoTrovato = new Medico(resultSet.getString(3), resultSet.getString(4), resultSet.getString(1), resultSet.getString(2), Specializzazione.valueOf(resultSet.getString(5)));
            	mediciDiSpec.add(medicoTrovato);
            }
            
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile ottenere il medico\n", e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return mediciDiSpec;
    }
	
	public static ArrayList<Medico> ottieniMedicidaSpecializzazione(Specializzazione tipologiaSpec) throws DAOException {
		ArrayList<Medico> mediciDiSpec = new ArrayList<Medico>();
		Medico medicoTrovato = null;
		Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Medici WHERE specializzazione='" + tipologiaSpec.toString() + "'";
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            while (resultSet.next()) {
            	
            	medicoTrovato = new Medico(resultSet.getString(3), resultSet.getString(4), resultSet.getString(1), resultSet.getString(2), Specializzazione.valueOf(resultSet.getString(5)));
            	mediciDiSpec.add(medicoTrovato);
            }
            
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile ottenere il medico\n", e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return mediciDiSpec;
    }
	
}
	
