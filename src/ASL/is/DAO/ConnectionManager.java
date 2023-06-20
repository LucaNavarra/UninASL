package ASL.is.DAO;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
	private final static String DATABASE_PATH = "~/test"; //
    private final static String DATABASE_USERNAME = "sa";
    private final static String DATABASE_PASSWORD = "";
    
    protected static java.sql.Connection connessione;
    
    protected static java.sql.Connection createConnection() {
    	
    	java.sql.Connection connessione = null;
    	
    	try {
        connessione = DriverManager.getConnection("jdbc:h2:" + DATABASE_PATH, DATABASE_USERNAME, DATABASE_PASSWORD);
    	}catch(SQLException e) {
    		System.out.println("Errore nella creazione della connessione");
    	}
    		return connessione;
    }
    
    protected static boolean closeConnection(java.sql.Connection connessione) throws SQLException{
    	connessione.close();
    	if(connessione.isClosed())
    		return true;
    	else
    		return false;
    }
    
    public static void SilentlyClose (Statement statement) throws DAOException { 
    	
    	try {
    		statement.close();
    		}catch (SQLException e) {
    			throw new DAOException ("Errore nella chiusura della connessione");
    		}
    }
    
    public static void SilentlyClose (java.sql.Connection connection) throws DAOException { 
    	
    	try {
    		ConnectionManager.closeConnection(connection);
    		}catch (SQLException e) {
    			throw new DAOException ("Errore nella chiusura della connessione");
    		}
    }
}
