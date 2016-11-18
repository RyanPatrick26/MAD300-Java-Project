package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class DummyClient {
	
	public static void main(String[] args) {
	
		String server = "localhost";
		int portNumber = 2000;
		Socket clientSocket;
		try {
			clientSocket = new Socket(server, portNumber);
			
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			String outString = "TEST";
			System.out.println("[CLIENT] Sent \"" + outString + "\" to the server.");
			out.println(outString);
			
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String answer = input.readLine();
			
			System.out.println("[CLIENT] Received \"" + answer + "\" from the server.");
			System.out.println(answer);

		} catch (ConnectException e) {
			System.out.println("There was a problem connecting to the server.\nIs the server running?");
			System.out.println("[SHUTTING DOWN]");
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
