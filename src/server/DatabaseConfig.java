package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

	private String DatabaseHost;
	private String DatabaseUser;
	private String DatabasePass;
	private String DatabaseName;
	
	public DatabaseConfig(String databaseHost, String databaseUser, String databasePass, String databaseName) {
		super();
		DatabaseHost = databaseHost;
		DatabaseUser = databaseUser;
		DatabasePass = databasePass;
		DatabaseName = databaseName;
	}
	
	public DatabaseConfig() {
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream("conf/database.properties");
			
			prop.load(input);
			
			this.DatabasePass = prop.getProperty("DatabasePassword");
			this.DatabaseHost = prop.getProperty("DatabaseHost");
			this.DatabaseUser = prop.getProperty("DatabaseUser");
			this.DatabaseName = prop.getProperty("DatabaseName");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getDatabaseHost() {
		return DatabaseHost;
	}
	public void setDatabaseHost(String databaseHost) {
		DatabaseHost = databaseHost;
	}
	public String getDatabaseUser() {
		return DatabaseUser;
	}
	public void setDatabaseUser(String databaseUser) {
		DatabaseUser = databaseUser;
	}
	public String getDatabasePass() {
		return DatabasePass;
	}
	public void setDatabasePass(String databasePass) {
		DatabasePass = databasePass;
	}
	public String getDatabaseName() {
		return DatabaseName;
	}
	public void setDatabaseName(String databaseName) {
		DatabaseName = databaseName;
	}
	
}
