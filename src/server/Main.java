package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Main {

	static DatabaseConfig dbconf = new DatabaseConfig();
	
	public static void main(String[] args) {	
		initDB();
		
		ServerSocket serverSocket = null;
		int serverListenPort = 2000;
		boolean serverRunning = true;
		int interactionNumber = 0;
		
		try {
			serverSocket = new ServerSocket(serverListenPort);
			
			// Here is where all the client/server interaction will happen
			Socket socket;
			while (serverRunning) {
				System.out.println("[SERVER] Started Instance");
				socket = serverSocket.accept();
				interactionNumber++;
				System.out.println(
						"\t> Client Number ----- " + interactionNumber + "\n" + 
						"\t> Using Port -------- " + serverSocket.getLocalPort() + "\n" +
						"\t> Client Address ---- " + socket.getInetAddress()
						);
				try {
					//String currentTime = new Date().toString();
					
					//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					try {
						//String input = in;
						
						InputStream inputStream = socket.getInputStream();
						ObjectInputStream input = new ObjectInputStream(inputStream);
						Game game;
						game = (Game) input.readObject();
						
//						System.out.println(game.getId());
//						System.out.println(game.getGameName());
//						System.out.println(game.getGameDescription());
//						System.out.println(game.getGameRating());
						
						ArrayList<String> columns = new ArrayList<String>();
						columns.add("ID");
						columns.add("GameName");
						columns.add("Rating");
						columns.add("Description");
						
						ArrayList<String> values = new ArrayList<String>();
						values.add(game.getId());
						values.add(game.getGameName());
						values.add(game.getGameRating());
						values.add(game.getGameDescription());
						insertRecord("GameManagement", columns, values);
						
						//System.out.println("[SERVER] Received \"" + input + "\" from the client.");
						//System.out.println(inputString);
						
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//System.out.println("[SERVER] Sent \"" + currentTime + "\" to the client.");
                	//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                	
                	//out.println(currentTime);
				} finally {
					socket.close();
				}
			}
			
			
		} catch (BindException e) {
			System.out.println("Permission denied to create socket on port " + serverListenPort);
			System.out.println("\t> Is a proccess already listening on that port?\n\t> Is the port number too low/high?");
			System.out.println("[SHUTTING DOWN]");
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
	
	public static void insertRecord(String tableName, ArrayList<String> columnNames, ArrayList<String> contents) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + 
						dbconf.getDatabaseHost() + "/" +
						dbconf.getDatabaseName() + "?useSSL=false",
						dbconf.getDatabaseUser(),
						dbconf.getDatabasePass()
					);
				
				String columns = "";
				
				for (int i = 0; i < columnNames.size(); i++) {
					columns += columnNames.get(i);
					if (i != columnNames.size() - 1) {
						columns += ", ";
					}
				}
				
				String values = "";
				
				for (int i = 0; i < contents.size(); i++) {
					values += "\"" + contents.get(i) + "\"";
					if (i != contents.size() - 1) {
						values += ", ";
					}
				}
				
				String query = "INSERT INTO " + tableName + "(" + columns + ") VALUES (" + values + ");";
				
				//System.out.println(query);
				
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
				preparedStatement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
				System.exit(1);
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
//		String password = "Banana";
//		String candidate = "Banana";
//		String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
//		
//		//System.out.println(hashed);
//		
//		if (BCrypt.checkpw(candidate, hashed)) {
//		    System.out.println("It matches");
//		} else {
//		    System.out.println("It does not match");
//		}
		
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
				System.exit(1);
			}
		}
//		ArrayList<String> columns = new ArrayList<String>();
//		columns.add("ID");
//		columns.add("GameName");
//		columns.add("Rating");
//		columns.add("Description");
//		
//		ArrayList<String> values = new ArrayList<String>();
//		values.add("0");
//		values.add("Steak & Berries");
//		values.add("101/100");
//		values.add("Get eaten by bears!");
//		insertRecord("GameManagement", columns, values);
	}
}
