package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	DataInputStream input;
	DataOutputStream output;
	ServerSocket listener;
	
	public Server() throws IOException {
		try {
			listener = new ServerSocket(2000);
			while (true) {	
				Socket socket = listener.accept();
				System.out.println("[ SERVER ] READY!");
				ServerThread serverThread = new ServerThread(socket);
				Thread thread = new Thread(serverThread);
				thread.start();
			}
		} finally {
			listener.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		new Server();
	}
}
