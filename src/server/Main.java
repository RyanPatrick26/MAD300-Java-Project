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
	
	protected static void initDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + 
				dbconf.getDatabaseHost() + "/" +
				dbconf.getDatabaseName() + "?useSSL=false",
				dbconf.getDatabaseUser(),
				dbconf.getDatabasePass()
			);
			
			try {
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("SHOW TABLES");
				preparedStatement.execute();
				ResultSet result = preparedStatement.getResultSet();
				
				while (result.next()) {
					System.out.println(result.getString("Tables_in_" + dbconf.getDatabaseName()));
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
	}
}
