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
import java.util.Arrays;

import common.Game;

public class API {
	
	public ArrayList<Game> getAllGames() {
		NetworkCommunication nc = new NetworkCommunication();
		ArrayList<ArrayList<String>> table = nc.getDataFromServer("GAMES.ALL");
		
		ArrayList<Game> returnArray = new ArrayList<Game>();
		
		for (int i = 0; i < table.size(); i++) {
			ArrayList<String> categories = new ArrayList<String>();
			categories.add("Testing Category 1");
			categories.add("Testing Category 2");
			Game game = new Game(table.get(i).get(1), categories);
			game.setID(Integer.parseInt(table.get(i).get(0)));
			game.setRating(Integer.parseInt(table.get(i).get(2)));
			game.setDescription(table.get(i).get(3));
			
			returnArray.add(game);
		}
		
		return returnArray;
	}
	
	public void updateGame(Game game) {
		NetworkCommunication nc = new NetworkCommunication();
		
		ArrayList<String> schema = new ArrayList<String>();
		schema.add("ID");
		schema.add("GameName");
		schema.add("Rating");
		schema.add("Description");
		
		ArrayList<ArrayList<String>> insertionData = new ArrayList<ArrayList<String>>();
		System.out.println("ID: " + game.getID());
		insertionData.add(new ArrayList<String>());
		insertionData.get(0).add(String.valueOf(game.getID()));
		insertionData.get(0).add(game.getName());
		insertionData.get(0).add(String.valueOf(game.getRating()));
		insertionData.get(0).add(game.getDescription());
		
		nc.sendUpdatedDataToServer(schema, "GAME", insertionData);
	}
	
	public void addGame(Game game) {
		NetworkCommunication nc = new NetworkCommunication();
		
		ArrayList<String> schema = new ArrayList<String>();
//		schema.add("ID");
		schema.add("GameName");
		schema.add("Rating");
		schema.add("Description");
		
		ArrayList<ArrayList<String>> insertionData = new ArrayList<ArrayList<String>>();
		insertionData.add(new ArrayList<String>());
//		insertionData.get(0).add(String.valueOf(game.getID()));
		insertionData.get(0).add(game.getName());
		insertionData.get(0).add(String.valueOf(game.getRating()));
		insertionData.get(0).add(game.getDescription());
		
		nc.sendNewDataToServer(schema, "GAME", insertionData);
	}
	
//	private ArrayList<Game> getGameFromServer() throws IOException {
//		Socket socket = createClientSocket();
//		InputStream inputStream = socket.getInputStream();
//		OutputStream outputStream = socket.getOutputStream();
//		DataInputStream input = new DataInputStream(inputStream);
//		DataOutputStream output = new DataOutputStream(outputStream);
//		
//		output.writeUTF("GET");
//		output.writeUTF();
//		
//		return null;
//	}
//	
//	private ArrayList[] getFromServer(String wantedWhat, String wantedData) throws IOException {
//		Socket socket = createClientSocket();
//		OutputStream outputStream = socket.getOutputStream();
//		DataOutputStream out = new DataOutputStream(outputStream);
//		
//		out.writeUTF("GET");
//		out.writeUTF(wantedWhat);
//		out.writeUTF(wantedData);
//		
//		InputStream inputStream = socket.getInputStream();
//		DataInputStream in = new DataInputStream(inputStream);
//
//		ArrayList[] outputArray = new ArrayList[2];
//		for (int i = 0; i < outputArray.length; i++) {
//			outputArray[i] = new ArrayList();
//		}
//		
//		String response;
//		boolean schemaToggle = false;
//		int counter = 0;
//		boolean running = true;
//		do {
//			response = in.readUTF();
//			System.out.println(response);
//			if (response.equals("<<<END>>>")) {
//				running = false;
//			}
//			
//			if (running) {
//				if (!schemaToggle) {
//					outputArray[0].add(response);
//				} else {
//					outputArray[1].add(response);
//				}
//				
//				schemaToggle = !schemaToggle;
//				counter++;
//			}
//			
//			
//		} while (running);
//
//		return outputArray;
//	}
//	
//	private boolean sendToServer(String sendingWhat, ArrayList[] sendingData) throws IOException {
//		Socket socket = createClientSocket();
//		OutputStream outputStream = socket.getOutputStream();
//		DataOutputStream out = new DataOutputStream(outputStream);
//		
//		for (int i = 0; i < sendingData.length; i++) {
//			for (int j = 0; j < sendingData[i].size(); j++) {
//				System.out.println("SENDTOSERVER() [" + i + "][" + j + "]: " + sendingData[i].get(j));
//			}
//		}
//		
//		System.out.println(sendingData[0].size());
//		
//		out.writeUTF("SEND");
//		out.writeUTF(sendingWhat);
//		for (int i = 0; i < sendingData[0].size(); i++) {
//			//System.out.println("SENDTOSERVER() [0]: " + sendingData[0].get(i));
//			out.writeUTF(String.valueOf(sendingData[0].get(i)));
//			//System.out.println("SENDTOSERVER() [1]: " + sendingData[1].get(i));
//			if (sendingData.length > 1) {
//				out.writeUTF(String.valueOf(sendingData[1].get(i)));
//			}
//		}
//		
////		for (int i = 0; i < sendingData[0].size(); i++) {
////			//for (int j = 0; j < sendingData[i].size(); j++) {
////				System.out.println(sendingData[0].get(i) + " | " + sendingData[1].get(i));
////				//out.writeUTF((String) sendingData[i].get(j));
////				out.writeUTF(String.valueOf(sendingData[0].get(i)));
////				out.writeUTF(String.valueOf(sendingData[1].get(i)));
////			//}
////		}
//		
//		out.writeUTF("<<<END>>>");
//		
//		System.out.println("Waiting for response!");
//		
//		InputStream inputStream = socket.getInputStream();
//		DataInputStream in = new DataInputStream(inputStream);
//		//in.readBoolean();
//		return false;
//	}
//	
//	public Game getGame(int id) throws IOException {
//		ArrayList[] gameArray = getFromServer("GAME", Integer.toString(id));
//
//		// Print the response for testing purposes
////		for (int i = 0; i < gameArray.length; i++) {
////			for (int j = 0; j < gameArray[i].size(); j++) {
////				System.out.println("[" + i + "]: " + gameArray[i].get(j));
////			}
////		}
//		
//		Game game = new Game((String) gameArray[1].get(1), null);
//		game.setDescription((String) gameArray[1].get(3));
//		
//		return game;
//	}
//	
//	public boolean addGame(Game game) throws IOException {
//		
//		ArrayList[] sendingData = new ArrayList[2];
//		sendingData[0] = new ArrayList();
//		sendingData[1] = new ArrayList();
//		
//		sendingData[0].add("ID");
//		sendingData[0].add("GameName");
//		sendingData[0].add("Rating");
//		sendingData[0].add("Description");
//		
//		sendingData[1].add("0");
//		sendingData[1].add(game.getName());
//		sendingData[1].add(game.getRating());
//		sendingData[1].add(game.getDescription());
//		
////		for (int i = 0; i < sendingData.length; i++) {
////			for (int j = 0; j < sendingData[i].size(); j++) {
////				System.out.println("ADDGAME() [" + i + "]: " + sendingData[i].get(j));
////			}
////		}
//
//		return sendToServer("GAME", sendingData);
//	}
//
//	public ArrayList<Game> getAllGames() throws IOException {
//		
//		//Arrays.deepToString(getFromServer("GAMES", "ALL"));
//		//ArrayList[] games = getGameFromServer();
//
//		//Arrays.deepToString(games);
//		
//		System.exit(1);
//		
//		return null;
//	}
}
