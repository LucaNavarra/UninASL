package ASL.is.DAO;

import ASL.is.entity.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class VisitaDao extends ConnectionManager{
	public static boolean creaVisita(Visita nuovaVisita) throws DAOException {
		boolean successoCaricamento = false;
    	PreparedStatement preparedStatement = null;
    	Statement verificaSovrapposizioni = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String VERIFICA = "SELECT * FROM VISITE WHERE medico_riferimento='" + nuovaVisita.getMedico().getUsername() + "' AND giorno = '" + nuovaVisita.getGiornoSettimana().toString() + "' AND (("
    				+ "((minuti_fine > " + nuovaVisita.getMinutiInizio() + " AND ora_fine = " + nuovaVisita.getOraInizio() + ") OR ora_fine > " + nuovaVisita.getOraInizio()
    				+ ") AND (minuti_fine < " + nuovaVisita.getMinutiFine() + " AND ora_fine = " + nuovaVisita.getOraFine() + ") OR ora_fine < " + nuovaVisita.getOraFine()
    				+ ") OR minuti_inizio >" + nuovaVisita.getMinutiInizio() + " AND ora_inizio = " + nuovaVisita.getOraInizio() + " OR ora_inizio > " + nuovaVisita.getOraInizio() 
    				+ " AND minuti_inizio < " + nuovaVisita.getMinutiFine() + " AND ora_inizio = " + nuovaVisita.getOraFine() + " OR ora_inizio < " + nuovaVisita.getOraFine() + ")";
    		final String QUERY_SQL = "INSERT INTO Visite (ora_inizio, ora_fine, minuti_inizio, minuti_fine, stanza, giorno, medico_riferimento) VALUES (?, ?, ?, ?, ?, ?, ?)";
    		verificaSovrapposizioni = connessione.createStatement();
    		verificaSovrapposizioni.execute(VERIFICA);
    		if(!(verificaSovrapposizioni.getResultSet().next())) {
    		
    		preparedStatement = connessione.prepareStatement(QUERY_SQL);
    		preparedStatement.setInt(1, nuovaVisita.getOraInizio());
    		preparedStatement.setInt(2, nuovaVisita.getOraFine());
    		preparedStatement.setInt(3, nuovaVisita.getMinutiInizio());
    		preparedStatement.setInt(4, nuovaVisita.getMinutiFine());
    		preparedStatement.setString(5, nuovaVisita.getStanza());
    		preparedStatement.setString(6, nuovaVisita.getGiornoSettimana().toString());
    		preparedStatement.setString(7, nuovaVisita.getMedico().getUsername());
    		
    		preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
            	int codiceVisita = resultSet.getInt(8);
                if (resultSet.wasNull() == false) {
                    nuovaVisita.setIdOrario(codiceVisita);;
                }
            }
            resultSet.close();
            successoCaricamento = true;
    		}
            
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Errore nella creazione della visita\n", e);
        } finally {
        	if (preparedStatement != null) {
               SilentlyClose(preparedStatement);
        	}
        	if (verificaSovrapposizioni != null) {
               SilentlyClose(verificaSovrapposizioni);
        	}
            SilentlyClose(connessione);
        }
    	return successoCaricamento;
    }
	
	public static boolean modificaVisita(Visita visita) throws DAOException{
    	PreparedStatement preparedStatement = null;
    	boolean visitaCambiata = false;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "UPDATE VISITE"
    				+ " SET ora_inizio ="+ visita.getOraInizio()
    				+ " ,ora_fine = "+ visita.getOraFine()
    				+ " ,minuti_inizio ="+ visita.getMinutiInizio()
    				+ " ,minuti_fine = "+ visita.getMinutiFine()
    				+ " ,stanza = '"+ visita.getStanza() +"'"
    				+ " ,giorno = '"+ visita.getGiornoSettimana() +"'"
    				+ " ,medico_riferimento = '"+ visita.getMedico().getUsername() +"'"
    				+" WHERE id_orario =" + visita.getIdOrario();
    		preparedStatement = connessione.prepareStatement(QUERY_SQL);
    		
    		if(preparedStatement.executeUpdate() != 0)
    			visitaCambiata = true;
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile modificare la visita\n", e);
        } finally {
        	if (preparedStatement != null) {
        		SilentlyClose(preparedStatement);
        	}
        	SilentlyClose(connessione);
        }
    	return visitaCambiata;
    }
	
	public static boolean eliminaVisita(Visita visita) throws DAOException {
    	PreparedStatement preparedStatement = null;
    	boolean visitaEliminata = false;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "DELETE FROM Visite WHERE id_orario =" + visita.getIdOrario();
    		preparedStatement = connessione.prepareStatement(QUERY_SQL);
    		System.out.print(QUERY_SQL + "\n");
    		if(preparedStatement.executeUpdate() != 0)
    			visitaEliminata = true;
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile eliminare la visita\n", e);
        } finally {
        	if (preparedStatement != null) {
        		SilentlyClose(preparedStatement);
        	}
        	SilentlyClose(connessione);
        }
    	return visitaEliminata;
    }
	
	public static Visita ottieniVisita(int codiceVisita) throws DAOException {
    	Visita visitaRicercata = null;
    	Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Visite WHERE id_orario=" + String.valueOf(codiceVisita);
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            if (resultSet.next()) {
            	//(String nuovaStanza, Giorno nuovoGiorno, int minutiInizio, int oraInizio, Medico Med, int idOrario)
                visitaRicercata = new Visita(resultSet.getString(5), Giorno.valueOf(resultSet.getString(6)), resultSet.getInt(3), resultSet.getInt(1), MedicoDao.ottieniMedico(resultSet.getString(7)), resultSet.getInt(8));
            }
            
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile ottenere la visita selezionata\n", e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return visitaRicercata;
    }
	
	public static Visita ottieniVisitaMedico(int codiceVisita, Medico medicoRiferimento) throws DAOException {
    	Visita visitaRicercata = null;
    	Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Visite WHERE id_orario=" + codiceVisita + " AND " + "medico_riferimento='" + medicoRiferimento.getUsername() + "'";
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            if (resultSet.next()) {
            	//(String nuovaStanza, Giorno nuovoGiorno, int minutiInizio, int oraInizio, Medico Med, int idOrario)
                visitaRicercata = new Visita(resultSet.getString(5), Giorno.valueOf(resultSet.getString(6)), resultSet.getInt(3), resultSet.getInt(1), medicoRiferimento, resultSet.getInt(8));
            }
            
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile ottenere la visita selezionata\n", e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return visitaRicercata;
    }
	
	public static ArrayList<Visita> ottieniCalendarioMedico(Medico medicoRiferimento) throws DAOException {
    	ArrayList<Visita> visiteRicercate = new ArrayList<Visita>();
    	Visita visitaTrovata = null;
    	Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Visite WHERE medico_riferimento='" + medicoRiferimento.getUsername() + "'";
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            while (resultSet.next()) {
            	visitaTrovata = new Visita(resultSet.getString(5), Giorno.valueOf(resultSet.getString(6)), resultSet.getInt(3), resultSet.getInt(1), medicoRiferimento, resultSet.getInt(8));
            	visiteRicercate.add(visitaTrovata);
            }
            
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile ottenere l'orario di visita del medico\n",e );
        }catch (NullPointerException e){
        	throw new NullPointerException("Nessun medico corrispondente a quell'username");
        }
    	finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return visiteRicercate;
    }

	
	
}