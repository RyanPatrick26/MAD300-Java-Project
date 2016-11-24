package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {

	DataInputStream input;
	DataOutputStream output;
	Socket socket;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		int i = 0;
		
		try {
			while (true) {
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
				
				if (input.available() > 0) {
					try {
						String message = input.readUTF();
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						
						out.println("[" + i + "] " + message);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					i++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
