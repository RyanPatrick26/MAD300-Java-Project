package server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Main {

	static DatabaseConfig dbconf = new DatabaseConfig();
	
	public static void main(String[] args) {	
		initDB();
	}
	
	static Connection connection;
	
	public static boolean tableExists(String tableName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + 
				dbconf.getDatabaseHost() + "/" +
				dbconf.getDatabaseName() + "?useSSL=false",
				dbconf.getDatabaseUser(),
				dbconf.getDatabasePass()
			);
			
			try {
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("SHOW TABLES LIKE '" + tableName + "'");
				preparedStatement.execute();
				ResultSet result = preparedStatement.getResultSet();
				
				if (result.next()) {
					return true;
				} else {
					return false;
				}
				
			} catch (SQLException e) {
				System.out.println("SQL Exception: " + e.getMessage());
				System.out.println("SQL State: " + e.getSQLState());
				System.out.println("SQL Error Code: " + e.getErrorCode());
			}
			
			connection.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Looks like SQL had an oopsie!");
		}
		return false;
	}
	
	public static void createTable(String tableName, String SQLBody) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + 
				dbconf.getDatabaseHost() + "/" +
				dbconf.getDatabaseName() + "?useSSL=false",
				dbconf.getDatabaseUser(),
				dbconf.getDatabasePass()
			);
			
			try {
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(
					"CREATE TABLE " + tableName + "(" + SQLBody + ");"
					);
				preparedStatement.execute();
			} catch (SQLException e) {
				System.out.println("SQL Exception: " + e.getMessage());
				System.out.println("SQL State: " + e.getSQLState());
				System.out.println("SQL Error Code: " + e.getErrorCode());
				System.out.println("[SHUTTING DOWN]");
				System.exit(0);
			}
			
			connection.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Looks like SQL had an oopsie!");
		}
	}
	
	protected static void initDB() {
		String password = "Banana";
		String candidate = "Banana";
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
		
		//System.out.println(hashed);
		
		if (BCrypt.checkpw(candidate, hashed)) {
		    System.out.println("It matches");
		} else {
		    System.out.println("It does not match");
		}
		
		if (tableExists("GameManagement")) {
			System.out.println("Table Already Exists!");
		} else {
			System.out.println("Table Doesn't Exist!");
			System.out.println("Creating Table...");
			String SQLBody =
				"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
				"GameName VARCHAR(32)," +
				"Rating VARCHAR(16)," +
				"Description TEXT";
			createTable("GameManagement", SQLBody);
			if (tableExists("GameManagement")) {
				System.out.println("Table Exists Now!");
			} else {
				// Should never happen, but you never know.
				System.out.println("Table Still Doesn't Exist!");
				System.out.println("[SHUTTING DOWN]");
				System.exit(0);
			}
		}
	}
}
