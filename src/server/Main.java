package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
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
				System.out.println("[ SERVER ] Started Instance");
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
						DataInputStream in = new DataInputStream(inputStream);
						
						String request = in.readUTF();
						
						//System.out.println(request);
						
						if (request.equals("GET")) {
							// The client has requested data from the server.
							System.out.println("GET");
						} else if (request.equals("SEND")) {
							// The client is sending data to the server
							System.out.println("SEND");
						} else {
							// The client sent something invalid to the server
							System.out.println("NONSENSE");
						}

						//ObjectInputStream input = new ObjectInputStream(inputStream);
						//Game game;
						//game = (Game) input.readObject();
						
//						String insert[][] = {
//								{"GameName", "Rating", "Description"},
//								{game.getGameName(), game.getGameRating(), game.getGameDescription()}
//								};
//						
//						dbUtilities.insertInto("GameManagement", insert);
						
						//System.out.println("[SERVER] Received \"" + input + "\" from the client.");
						//System.out.println(inputString);
					} catch (InvalidClassException e) {
						System.out.println("Client sent wrong object, or server/client is out of date.");
					} catch (IOException e) {
						e.printStackTrace();
					}
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
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
		
		System.out.println("[DATABASE] Checking if table exists");
		if (!dbUtilities.tableExists("GameManagement")) {
			System.out.println("[DATABASE] Table not found... Creating table");
			String schema[][] = {
					{"GameName", "Rating", "Description"},
					{"VARCHAR(40)", "VARCHAR(40)", "TEXT"}
					};
			dbUtilities.createTable("GameManagement", schema);
			if (!dbUtilities.tableExists("GameManagement")) {
				// Should never be reached.
				System.out.println("[DATABASE] Can't create table. Shutting Down.");
				System.exit(1);
			}
		}
		
//		String schema[] = {
//				"ID",
//				"GameName",
//				"Rating",
//				"Description"
//		};
//		String[][] results = dbUtilities.fetchRow("GameManagement", 1, schema);
//		
//		for (int i = 0; i < results[0].length; i++) {
//			System.out.print(results[0][i] + " | ");
//		}
//		System.out.println("");
//		for (int i = 0; i < results[1].length; i++) {
//			System.out.print(results[1][i] + " | ");
//		}

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

//		String insert[][] = {
//				{"GameName", "Rating", "Description"},
//				{"Factorio", "95/100", "Factory Building Game"}
//				};
		
		//System.out.println("Inserted: " + dbUtilities.insertInto("GameManagement", insert));
	}
}
