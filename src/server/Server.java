package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Master server class, used for all instances of server threads
 * @author Brandon Brown
 */
public class Server extends Thread {
	
	DataInputStream input;
	DataOutputStream output;
	ServerSocket listener;
	
	/**
	 * Server constructor, creates threads to handle all connections
	 * @throws IOException
	 */
	public Server() throws IOException {
		try {
			// TODO: Add the server socket's port to a configuration file.
			listener = new ServerSocket(2000);
			
			// Main server loop
			while (true) {	
				Socket socket = listener.accept();
				System.out.println("[ SERVER ] Accepted Connection");
				ServerThread serverThread = new ServerThread(socket);
				Thread thread = new Thread(serverThread);
				thread.start();
			}
		} finally {
			listener.close();
		}
	}
	
	/**
	 * Entry point for application, only purpose is to launch the server
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO: Make use of application arguments to change the behavior of server
		
		// Start the server as soon as the application is launched.
		new Server();
	}
}
