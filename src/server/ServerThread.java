package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

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
				
				InputStream inputStream = socket.getInputStream();
				DataInputStream in = new DataInputStream(inputStream);
				
				if (input.available() > 0) {
					try {
						String request = input.readUTF();
						String wantedType = input.readUTF();
						
						//System.out.println(message);
						if (request.equals("GET")) {
							String wantedData = input.readUTF();
							
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
							
							if (wantedType.equals("GAME")) {
								
								ArrayList[] gameArray = new ArrayList[2];
								gameArray[0] = new ArrayList();
								gameArray[1] = new ArrayList();
								
								boolean toggle = true;
								boolean running = true;
								String input;
								
//								while (true) {
//									System.out.println("HELLO");
//									
//									input = in.readUTF();
//								}
								
								while (running) {

										input = in.readUTF();
										System.out.println("INPUT: " + input);
										if (input.equals("<<<END>>>")) {
											running = false;
										} else {
											if (toggle) {
												gameArray[0].add(input);
											} else {
												gameArray[1].add(input);
											}
											
												
											toggle = !toggle;
										}
										
										
									
									
									//System.out.println(input);
									
								}
								
								System.out.println("Got here too! :O");
								System.out.println(gameArray[0].size()-1);
								
								for (int k = 0; k < gameArray.length; k++) {
									for (int j = 0; j < gameArray[k].size(); j++) {
										System.out.println("[" + k + "]: " + gameArray[k].get(j));
									}
								}
								System.out.println("array size: " + gameArray[0].size());
								String[][] schema = new String[2][gameArray[0].size()];
								
								for (int a = 0; a < schema.length; a++) {
									for (int b = 1; b < schema[a].length; b++) {
										schema[a][b] = gameArray[a].get(b).toString();
										System.out.println(schema[a][b].toString());
									}
								}
								
								System.out.println(Arrays.deepToString(schema));
								
								dbUtilities.insertInto("GameManagement", schema);
								
								output.writeBoolean(true);
								System.exit(0);
								
							}
							
							System.out.println("Finished receiving data from the client");
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
