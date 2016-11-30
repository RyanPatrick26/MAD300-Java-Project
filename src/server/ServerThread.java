package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Server thread for every new connection to the server
 * This is where all the communication between clients happens
 * @author Brandon Brown
 *
 */
public class ServerThread implements Runnable {

	DataInputStream input;
	DataOutputStream output;
	Socket socket;
	DatabaseUtilities dbUtilities = new DatabaseUtilities();
	
	/**
	 * ServerThread constructor
	 * @param socket	is passed from the Master Server to it's
	 * 					threads for handling of the client's connection
	 */
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		int i = 0;
		
		try {
			while (true) {
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
				if (input.available() > 0) {
					try {
						String request = input.readUTF();
						String wantedType = input.readUTF();
						String wantedData = input.readUTF();
						
						//System.out.println(message);
						if (request.equals("GET")) {
							System.out.println("Received a get request from the client");
							
							if (wantedType.equals("GAME")) {
								String schema[] = {
									"ID",
									"GameName",
									"Rating",
									"Description"
								};
								String[][] results = dbUtilities.fetchRow("GameManagement", Integer.parseInt(wantedData), schema);
								for (int j = 0; j < results[0].length; j++) {
									//System.out.print(results[0][j] + " | ");
									output.writeUTF(results[0][j]);
									output.writeUTF(results[1][j]);
								}
								output.writeUTF("<<<END>>>");
							}
						
						} else if (request.equals("SEND")) {
							System.out.println("Received a send request from the client");
						} else {
							System.out.println("Client sent nonsense to the server");
						}
						
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						
						out.println("[" + i + "] " + request);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					i++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
