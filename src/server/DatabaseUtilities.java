package server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * This class is for housing various database interaction
 * to be used by the server end of the software.
 * 
 * @author Brandon Brown
 * 
 */
public class DatabaseUtilities {
	
	/**
	 * Creates a table in the configured database
	 * 
	 * @author	Brandon Brown
	 * @return	<code>false</code> if the table cannot be created;
	 * 			<code>true</code> if the table was created successfully
	 */
	public boolean createTable(DatabaseConfig dbconf, String tableName, String[][] schema) {
		// Check if there are 2 array "columns".
		if (schema.length != 2) {
			return false;
			
		// Check if the length of both "columns" are identical
		} else if (schema[0].length != schema[1].length) {
			return false;
		} else if (tableExists(dbconf, tableName)) {
			return false;
			
		}
		
		// Build the query statement from the schema array
		String query = "";
		query += "CREATE TABLE " + tableName + " (";
		query += "ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, ";
		for (int i = 0; i < schema[0].length; i++) {
			query += schema[0][i] + " ";
			query += schema[1][i];
			
			if (i < schema[0].length - 1) {
				query += ", ";
			}
		}
		query += ");";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + 
				dbconf.getDatabaseHost() + "/" +
				dbconf.getDatabaseName() + "?useSSL=false",
				dbconf.getDatabaseUser(),
				dbconf.getDatabasePass()
			);
			
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.execute();
			
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("SQL Error Code: " + e.getErrorCode());
			System.out.println("[SHUTTING DOWN]");
			System.exit(1);
		}

		return false;
	}
	
	/**
	 * 
	 * Drops the table provided from the configured database
	 * 
	 * @param	dbconf
	 * @param	tableName
	 * @return	<code>false</code> if the table was not dropped;
	 * 			<code>true</code> if the table was dropped.
	 */
	public boolean dropTable(DatabaseConfig dbconf, String tableName) {
		if (tableExists(dbconf, tableName)) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + 
					dbconf.getDatabaseHost() + "/" +
					dbconf.getDatabaseName() + "?useSSL=false",
					dbconf.getDatabaseUser(),
					dbconf.getDatabasePass()
				);
				
				String query = "DROP TABLE " + tableName + ";";
				
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
				preparedStatement.execute();
				return true;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("SQL Exception: " + e.getMessage());
				System.out.println("SQL State: " + e.getSQLState());
				System.out.println("SQL Error Code: " + e.getErrorCode());
				System.out.println("[SHUTTING DOWN]");
				System.exit(1);
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * Empty the table provided from the configured database
	 * 
	 * @param	dbconf
	 * @param	tableName
	 * @return	<code>false</code> if the table was not emptied;
	 * 			<code>true</code> if the table was emptied.
	 */
	public boolean emptyTable(DatabaseConfig dbconf, String tableName) {
		if (tableExists(dbconf, tableName)) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + 
					dbconf.getDatabaseHost() + "/" +
					dbconf.getDatabaseName() + "?useSSL=false",
					dbconf.getDatabaseUser(),
					dbconf.getDatabasePass()
				);
				
				String query = "DELETE FROM " + tableName + ";";
				
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
				preparedStatement.execute();
				return true;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("SQL Exception: " + e.getMessage());
				System.out.println("SQL State: " + e.getSQLState());
				System.out.println("SQL Error Code: " + e.getErrorCode());
				System.out.println("[SHUTTING DOWN]");
				System.exit(1);
			}
		}
		
		return false;
	}
	
	/**
	 * Checks for the presence of a table
	 * 
	 * @author	Brandon Brown
	 * @return	<code>true</code> If the table exists in the database;
	 * 			<code>false</code> If the table doesn't exist in the database
	 */
	public boolean tableExists(DatabaseConfig dbconf, String tableName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + 
				dbconf.getDatabaseHost() + "/" +
				dbconf.getDatabaseName() + "?useSSL=false",
				dbconf.getDatabaseUser(),
				dbconf.getDatabasePass()
			);
			
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("SHOW TABLES LIKE '" + tableName + "'");
			preparedStatement.execute();
			ResultSet result = preparedStatement.getResultSet();
			
			return result.next() ? true : false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Inserts into the provided table using the provided schema
	 * 
	 * @author	Brandon Brown
	 * @return	<code>true</code> If the table exists in the database;
	 * 			<code>false</code> If the table doesn't exist in the database
	 */
	public boolean insertInto(DatabaseConfig dbconf, String tableName, String[][] schema) {
		// Check if there are 2 array "columns".
		if (schema.length != 2) {
			return false;
			
		// Check if the length of both "columns" are identical
		} else if (schema[0].length != schema[1].length) {
			return false;
		} else if (!tableExists(dbconf, tableName)) {
			return false;
			
		}
		
		// Build the query statement from the schema array
		String query = "";
		query += "INSERT INTO " + tableName + " (";
		query += "ID, ";
		for (int i = 0; i < schema[0].length; i++) {
			query += schema[0][i];
			
			if (i < schema[0].length - 1) {
				query += ", ";
			}
		}
		query += ") VALUES (0, ";
		for (int i = 0; i < schema[1].length; i++) {
			query += "\"" + schema[1][i] + "\"";
			
			if (i < schema[1].length - 1) {
				query += ", ";
			}
		}
		query += ");";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + 
				dbconf.getDatabaseHost() + "/" +
				dbconf.getDatabaseName() + "?useSSL=false",
				dbconf.getDatabaseUser(),
				dbconf.getDatabasePass()
			);
			
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.execute();
			
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("SQL Error Code: " + e.getErrorCode());
			System.out.println("[SHUTTING DOWN]");
			System.exit(1);
		}

		return false;
	}
}
