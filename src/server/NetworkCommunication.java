package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkCommunication {

	ServerConfig serverConfig = new ServerConfig();
	
	/**
	 * Create a client socket to connect to the server on it's port
	 * and return the socket to the requester
	 * @return Socket
	 */
	public Socket createClientSocket() {
		String server = serverConfig.getServerHost();
		int portNumber = serverConfig.getServerPort();
		Socket clientSocket;
		try {
			clientSocket = new Socket(server, portNumber);
			return clientSocket;
		} catch (IOException e) {
			System.out.println("[API] " + "There was a problem creating a client socket");
		}
		return null;
	}
	
	/**
	 * Retrieve information from the server over the network
	 * @param data Data requested from the server, see API allowed Strings
	 * @return 2D ArrayList representing a database table
	 */
	public ArrayList<ArrayList<String>> getDataFromServer(String data) {
		Socket socket = createClientSocket();
		InputStream inputStream;
		OutputStream outputStream;
		DataInputStream input;
		DataOutputStream output;
		
		try {
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			input = new DataInputStream(inputStream);
			output = new DataOutputStream(outputStream);
			
			output.writeUTF("GET");
			output.writeUTF(data);
			
			ArrayList<ArrayList<String>> returnArray = new ArrayList<ArrayList<String>>();
			
			int numCols = input.readInt();
			int numRows = input.readInt();
			
			for (int i = 0; i < numCols; i++) {
				returnArray.add(new ArrayList<String>());
				for (int j = 0; j < numRows; j++) {
					String cell = input.readUTF();
					
					returnArray.get(i).add(cell);
				}
			}
			
			input.close();
			output.close();
			outputStream.close();
			inputStream.close();
			socket.close();
			
			return returnArray;
			
		} catch (IOException e) {
			System.out.println("[NET] " + "There was a problem using the network stream");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Request information be deleted from the server
	 * based on it's type and id
	 * @param data	Type of data to be deleted from the server
	 * @param id	Database ID to be removed
	 */
	public void deleteDataFromServer(String data, int id) {
		Socket socket = createClientSocket();
		InputStream inputStream;
		OutputStream outputStream;
		DataInputStream input;
		DataOutputStream output;
		
		try {
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			input = new DataInputStream(inputStream);
			output = new DataOutputStream(outputStream);
			
			output.writeUTF("DELETE");
			output.writeUTF(data);
			
			output.writeInt(id);
			
			input.close();
			output.close();
			outputStream.close();
			inputStream.close();
			socket.close();
			
		} catch (IOException e) {
			System.out.println("[NET] " + "There was a problem using the network stream");
		}
	}
	
	
	/**
	 * Request the server to update the database using the information provided
	 * @param schema		Database schema for the server to use
	 * @param data			Datatype to be updated
	 * @param insertionData	Updated information in the form of a 2D arraylist of strings
	 */
	public void sendUpdatedDataToServer(ArrayList<String> schema, String data, ArrayList<ArrayList<String>> insertionData) {
		Socket socket = createClientSocket();
		InputStream inputStream;
		OutputStream outputStream;
		DataInputStream input;
		DataOutputStream output;
		
		try {
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			input = new DataInputStream(inputStream);
			output = new DataOutputStream(outputStream);
			
			output.writeUTF("UPDATE");
			output.writeUTF(data);

			output.writeInt(schema.size());
			output.writeInt(insertionData.size());
			
			for (int i = 0; i < schema.size(); i++) {
				output.writeUTF(schema.get(i));
				System.out.println(schema.get(i));
			}
			System.out.println("<<<END OF SCHEMA>>>");
			for (int i = 0; i < insertionData.size(); i++) {
				for (int j = 0; j < insertionData.get(i).size(); j++) {
					System.out.println(insertionData.get(i).get(j));
					output.writeUTF(insertionData.get(i).get(j));
				}
			}
		
			input.close();
			output.close();
			outputStream.close();
			inputStream.close();
			socket.close();
			
		} catch (IOException e) {
			System.out.println("[NET] " + "There was a problem using the network stream");
		}
	}
	
	/**
	 * Send data to the server for processing
	 * @param schema		The names of the columns to be inserted into
	 * @param data			The type of data being sent to the server, see API for options
	 * @param insertionData The "row" of data to be inserted
	 */
	public void sendNewDataToServer(ArrayList<String> schema, String data, ArrayList<ArrayList<String>> insertionData) {
		Socket socket = createClientSocket();
		InputStream inputStream;
		OutputStream outputStream;
		DataInputStream input;
		DataOutputStream output;
		
		try {
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			input = new DataInputStream(inputStream);
			output = new DataOutputStream(outputStream);
			
			output.writeUTF("SEND");
			output.writeUTF(data);

			output.writeInt(schema.size());
			output.writeInt(insertionData.size());
			
			for (int i = 0; i < schema.size(); i++) {
				output.writeUTF(schema.get(i));
				System.out.println(schema.get(i));
			}
			System.out.println("<<<END OF SCHEMA>>>");
			for (int i = 0; i < insertionData.size(); i++) {
				for (int j = 0; j < insertionData.get(i).size(); j++) {
					System.out.println(insertionData.get(i).get(j));
					output.writeUTF(insertionData.get(i).get(j));
				}
			}
			
			input.close();
			output.close();
			outputStream.close();
			inputStream.close();
			socket.close();
			
		} catch (IOException e) {
			System.out.println("[NET] " + "There was a problem using the network stream");
		}
	}
}
