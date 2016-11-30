package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

public class DummyClient {
	
	public static void main(String[] args) throws IOException {
//	
//		String server = "localhost";
//		int portNumber = 2000;
//		Socket clientSocket;
//		
		API api = new API();
		
		System.out.println(api.getGame(9));
		
//		try {
//			//clientSocket = new Socket(server, portNumber);
//			
//			//PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//			//String outString = "TEST";
//			//System.out.println("[CLIENT] Sent \"" + outString + "\" to the server.");
//			//out.println(outString);
//			
////			ArrayList<String> columns = new ArrayList<String>();
////			columns.add("ID");
////			columns.add("GameName");
////			columns.add("Rating");
////			columns.add("Description");
////			
////			ArrayList<String> values = new ArrayList<String>();
////			values.add("0");
////			values.add("");
////			values.add("101/100");
////			values.add("Get eaten by bears!");
//			
//			//Game game = new Game("0", "Solitare", "7/10", "Played by bored office employees everywhere.");
//			
//			//outString = 
//			
//			//OutputStream outputStream = clientSocket.getOutputStream();
//			//ObjectOutputStream objout = new ObjectOutputStream(outputStream);
//			
//			//objout.writeObject(game);
//			//objout.close();
//			
//			//OutputStreamWriter out = new OutputStreamWriter(outputStream);
//			//DataOutputStream out = new DataOutputStream(outputStream);
//			
////			String request = "GET";
////			
////			out.writeUTF(request);
////			out.flush();
////			
////			request = "SEND";
////			
////			out.writeUTF(request);
////			out.flush();
////			
////			request = "banana";
////			
////			out.writeUTF(request);
////			out.flush();
//			
//			//PrintWriter print = new PrintWriter(outputStream);
//			//print.println(request);
//			
//			//outputStream
//					
//			//System.out.println("[CLIENT] Sent \"" + outString + "\" to the server.");
//			//out.write(outString);
//			
//			//BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//			//String answer = input.readLine();
//			
//			//System.out.println("[CLIENT] Received \"" + answer + "\" from the server.");
//			//System.out.println(answer);
//
//		} catch (ConnectException e) {
//			System.out.println("There was a problem connecting to the server.\nIs the server running?");
//			System.out.println("[SHUTTING DOWN]");
//			System.exit(1);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
