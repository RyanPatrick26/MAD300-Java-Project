package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server thread for every new connection to the server
 * This is where all the communication between clients happens
 * @author Brandon Brown
 *
 */
public class ServerThread implements Runnable {

	private DataInputStream input;
	private DataOutputStream output;
	private Socket socket;
	private int connectionNumber;
	private DatabaseUtilities dbUtilities = new DatabaseUtilities();
	
	/**
	 * ServerThread constructor
	 * @param socket	is passed from the Master Server to it's
	 * 					threads for handling of the client's connection
	 * @author Brandon Brown
	 */
	public ServerThread(Socket socket, int connectionNumber) {
		this.socket = socket;
		this.connectionNumber = connectionNumber;
	}
	
	/**
	 * Main server loop, where connections are served and communication
	 * between the server and client happens
	 * @author Brandon Brown
	 */
	@Override
	public void run() {
		System.out.println("[ SERVER ] Thread #" + this.connectionNumber + " created to service " + socket.getRemoteSocketAddress());
		try {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());

			String requestType = input.readUTF();
			String requestedData = input.readUTF();
			
			System.out.println("[ SERVER ] Client Sent: \"" + requestType + " " + requestedData + "\"");

			if (requestType.equals("GET")) {
				System.out.println("[ SERVER ] Client requested data using GET");

				if (requestedData.equals("GAMES.ALL")) {
					String schema[] = {
						"ID",
						"GameName",
						"Rating",
						"Description",
						"ReleaseYear",
						"HoursPlayed",
						"GamePublisher",
						"Categories"
					};
					ArrayList<ArrayList<String>> results = dbUtilities.fetchAllRows("GameManagement", schema);

					output.writeInt(results.size());
					if (!results.isEmpty()) {
						output.writeInt(results.get(0).size());
					} else {
						output.writeInt(0);
					}

					for (int r = 0; r < results.size(); r++) {
						for (int j = 0; j < results.get(r).size(); j++) {
							System.out.println(results.get(r).get(j));
							output.writeUTF(results.get(r).get(j));
						}
					}
				} else if (requestedData.equals("GAME")) {
					// TODO:
				}

			} else if (requestType.equals("SEND")) {
				System.out.println("[ SERVER ] Client sent data using SEND");
				
				if (requestedData.equals("GAME")) {
					
					int numCols = input.readInt();
					int numRows = input.readInt() + 1;

					String[][] schema = new String[numRows][numCols];
					
					for (int i = 0; i < numCols; i++) {
						schema[0][i] = input.readUTF();
					}

					for (int i = 0; i < numCols; i++) {
						schema[1][i] = input.readUTF();
					}
					
					dbUtilities.insertInto("GameManagement", schema);
				}
				
				System.out.println("[ SERVER ] Finished receiving data from client");
				
			} else if (requestType.equals("UPDATE")) {
				if (requestedData.equals("GAME")) {
					
					int numCols = input.readInt();
					int numRows = input.readInt() + 1; // +1 because the schema is included
					
					String[][] schema = new String[numRows][numCols];

					for (int i = 0; i < numCols; i++) {
						schema[0][i] = input.readUTF();
					}
					for (int i = 0; i < numCols; i++) {
						schema[1][i] = input.readUTF();
					}
					
					String id = String.valueOf(schema[1][0]);
					
					dbUtilities.updateRow("GameManagement", id, schema[0], schema[1]);
				}

			} else if (requestType.equals("DELETE")) {
				if (requestedData.equals("GAME")) {
					String id = String.valueOf(input.readInt());

					dbUtilities.deleteRow("GameManagement", id);
				}
			} else {
				System.out.println("[ SERVER ] Client sent bad data");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
				input.close();
				output.close();
				socket.close();

				System.out.println("[ SERVER ] Closing Thread.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
