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
	ServerConfig serverConfig = new ServerConfig();
	
	/**
	 * Server constructor, creates threads to handle all connections
	 * @throws IOException
	 */
	public Server() {
		try {
			System.out.println("[ SERVER ] Started");
			System.out.println("[ SERVER ] Waiting for connections");
			listener = new ServerSocket(serverConfig.getServerPort());
			
			int connectionNumber = 0;
			
			// Main server loop
			while (true) {
				Socket socket = listener.accept();
				System.out.println("[ SERVER ] Accepted Connection");
				ServerThread serverThread = new ServerThread(socket, connectionNumber);
				Thread thread = new Thread(serverThread);
				thread.start();
				connectionNumber++;
			}
		} catch (IllegalArgumentException e) {
			System.out.println("[ SERVER ] Invalid port number, ensure the value is between 0 and 65535 in conf/server.properties");
			System.out.println("[ SERVER ] SHUTTING DOWN");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("[ SERVER ] Unable to start server...");
			System.out.println("           Is it already running?");
			System.out.println("[ SERVER ] SHUTTING DOWN");
			System.exit(1);
		} finally {
			try {
				listener.close();
			} catch (IOException e) {
				System.out.println("[ SERVER ] There was a problem closing the serversocket");
				System.out.println("[ SERVER ] SHUTTING DOWN");
				System.exit(1);
			}
		}
	}
	
	/**
	 * Entry point for application, only purpose is to launch the server
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// Start the server as soon as the application is launched.
		new Server();
	}
}
