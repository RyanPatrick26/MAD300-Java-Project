package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
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
	static DatabaseUtilities dbUtilities = new DatabaseUtilities();
	
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
					try {
						
						InputStream inputStream = socket.getInputStream();
						ObjectInputStream input = new ObjectInputStream(inputStream);
						Game game;
						game = (Game) input.readObject();
						
						String insert[][] = {
								{"GameName", "Rating", "Description"},
								{game.getGameName(), game.getGameRating(), game.getGameDescription()}
								};
						
						dbUtilities.insertInto(dbconf, "GameManagement", insert);
						
						//System.out.println("[SERVER] Received \"" + input + "\" from the client.");
						//System.out.println(inputString);
					} catch (InvalidClassException e) {
						System.out.println("Client sent wrong object, or server/client is out of date.");
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
	
	protected static void initDB() {
		DatabaseUtilities dbUtilities = new DatabaseUtilities();
		
		System.out.println("Dropped Table: " + dbUtilities.dropTable(dbconf, "GameManagement"));
		
		String schema[][] = {
				{"GameName", "Rating", "Description"},
				{"VARCHAR(40)", "VARCHAR(40)", "TEXT"}
				};
		
		System.out.println("Created Table: " + dbUtilities.createTable(dbconf, "GameManagement", schema));

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

		String insert[][] = {
				{"GameName", "Rating", "Description"},
				{"Candy Land", "5/6", "Age old game"}
				};
		
		System.out.println("Inserted: " + dbUtilities.insertInto(dbconf, "GameManagement", insert));
	}
}
