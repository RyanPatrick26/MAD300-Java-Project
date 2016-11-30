package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import common.Game;

public class API {
	
	private Socket createClientSocket() {
		String server = "localhost";
		int portNumber = 2000;
		Socket clientSocket;
		try {
			clientSocket = new Socket(server, portNumber);
			return clientSocket;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private ArrayList[] getFromServer(String wantedWhat, String wantedData) throws IOException {
		Socket socket = createClientSocket();
		OutputStream outputStream = socket.getOutputStream();
		DataOutputStream out = new DataOutputStream(outputStream);
		
		out.writeUTF("GET");
		out.writeUTF(wantedWhat);
		out.writeUTF(wantedData);
		
		InputStream inputStream = socket.getInputStream();
		DataInputStream in = new DataInputStream(inputStream);
		
		ArrayList[] outputArray = new ArrayList[2];
		outputArray[0] = new ArrayList();
		outputArray[1] = new ArrayList();
		
		String response;
		boolean schemaToggle = false;
		int counter = 0;
		boolean running = true;
		do {
			response = in.readUTF();
			//System.out.println(response);
			if (response.equals("<<<END>>>")) {
				running = false;
			}
			
			if (running) {
				if (!schemaToggle) {
					outputArray[0].add(response);
				} else {
					outputArray[1].add(response);
				}
				
				schemaToggle = !schemaToggle;
				counter++;
			}
			
			
		} while (running);

		return outputArray;
	}
	
	private String sendToServer() throws IOException {
		return null;
		
	}
	
	public Game getGame(int id) throws IOException {
		ArrayList[] gameArray = getFromServer("GAME", Integer.toString(id));

		// Print the response for testing purposes
//		for (int i = 0; i < gameArray.length; i++) {
//			for (int j = 0; j < gameArray[i].size(); j++) {
//				System.out.println("[" + i + "]: " + gameArray[i].get(j));
//			}
//		}
		
		Game game = new Game((String) gameArray[1].get(1), null);
		game.setDescription((String) gameArray[1].get(3));
		
		return game;
	}
}
