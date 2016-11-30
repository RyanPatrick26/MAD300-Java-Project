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
	
	private boolean sendToServer(String sendingWhat, ArrayList[] sendingData) throws IOException {
		Socket socket = createClientSocket();
		OutputStream outputStream = socket.getOutputStream();
		DataOutputStream out = new DataOutputStream(outputStream);
		
		
		
		
		for (int i = 0; i < sendingData.length; i++) {
			for (int j = 0; j < sendingData[i].size(); j++) {
				System.out.println("SENDTOSERVER() [" + i + "]: " + sendingData[i].get(j));
			}
		}
		
		System.out.println(sendingData[0].size());
		
		out.writeUTF("SEND");
		out.writeUTF(sendingWhat);
		for (int i = 0; i < sendingData[0].size(); i++) {
			System.out.println("SENDTOSERVER() [0]: " + sendingData[0].get(i));
			out.writeUTF(String.valueOf(sendingData[0].get(i)));
			System.out.println("SENDTOSERVER() [1]: " + sendingData[1].get(i));
			out.writeUTF(String.valueOf(sendingData[1].get(i)));
		}
		
//		for (int i = 0; i < sendingData[0].size(); i++) {
//			//for (int j = 0; j < sendingData[i].size(); j++) {
//				System.out.println(sendingData[0].get(i) + " | " + sendingData[1].get(i));
//				//out.writeUTF((String) sendingData[i].get(j));
//				out.writeUTF(String.valueOf(sendingData[0].get(i)));
//				out.writeUTF(String.valueOf(sendingData[1].get(i)));
//			//}
//		}
		
		out.writeUTF("<<<END>>>");
		
		System.out.println("Waiting for response!");
		
		InputStream inputStream = socket.getInputStream();
		DataInputStream in = new DataInputStream(inputStream);
		//in.readBoolean();
		return false;
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
	
	public boolean addGame(Game game) throws IOException {
		
		ArrayList[] sendingData = new ArrayList[2];
		sendingData[0] = new ArrayList();
		sendingData[1] = new ArrayList();
		
		sendingData[0].add("ID");
		sendingData[0].add("GameName");
		sendingData[0].add("Rating");
		sendingData[0].add("Description");
		
		sendingData[1].add("0");
		sendingData[1].add(game.getName());
		sendingData[1].add(game.getRating());
		sendingData[1].add(game.getDescription());
		
//		for (int i = 0; i < sendingData.length; i++) {
//			for (int j = 0; j < sendingData[i].size(); j++) {
//				System.out.println("ADDGAME() [" + i + "]: " + sendingData[i].get(j));
//			}
//		}

		return sendToServer("GAME", sendingData);
	}
}
