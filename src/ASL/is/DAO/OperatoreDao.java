package ASL.is.DAO;
import ASL.is.entity.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OperatoreDao extends ConnectionManager{
	//rimoviOperatore()
	public static void creaOperatore(Operatore nuovoOperatore) throws DAOException {
    	PreparedStatement preparedStatement = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "INSERT INTO Operatori (password, nome, cognome) VALUES (?, ?, ?)" ;
    		preparedStatement = connessione.prepareStatement(QUERY_SQL);
    		preparedStatement.setString(1, nuovoOperatore.getPassword());
    		preparedStatement.setString(2, nuovoOperatore.getNome());
    		preparedStatement.setString(3, nuovoOperatore.getCognome());
    		
    		preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
            	int codiceOp = resultSet.getInt(1);
                if (resultSet.wasNull() == false) {
                    nuovoOperatore.setCodiceOp(codiceOp);
                }
            }
            resultSet.close();
            
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Errore nella creazione dell'operatore\n", e);
        } finally {
        	if (preparedStatement != null) {
        		SilentlyClose(preparedStatement);
        	}
        	SilentlyClose(connessione);
        }
    }
	
	public static Operatore ottieniOperatore(int codiceOp) throws DAOException {
    	Operatore opRicercato = null;
    	Statement ricercaQuery = null;
    	try {
    		connessione = ConnectionManager.createConnection();
    		final String QUERY_SQL = "SELECT * FROM Operatori WHERE codice_operatore=" + String.valueOf(codiceOp);
    		ricercaQuery = connessione.createStatement();
    		ResultSet resultSet = ricercaQuery.executeQuery(QUERY_SQL);
            if (resultSet.next()) {
                opRicercato = new Operatore(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            }
            
            resultSet.close();
        } catch (SQLException e) {
        	//Gestione eccezioni
        	throw new DAOException("Impossibile ottenere l'operatore\n",e);
        } finally {
        	if (ricercaQuery != null) {
        		SilentlyClose(ricercaQuery);
        	}
        	SilentlyClose(connessione);
        }
        return opRicercato;
    }
}
