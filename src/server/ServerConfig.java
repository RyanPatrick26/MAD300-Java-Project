package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfig {

	private String ServerHost;
	private int ServerPort;
	
	public ServerConfig(String serverHost, int serverPort) {
		super();
		ServerHost = serverHost;
		ServerPort = serverPort;
	}

	public ServerConfig() {
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream("conf/server.properties");
			
			prop.load(input);
			
			this.ServerHost = prop.getProperty("ServerHost");
			this.ServerPort = Integer.parseInt(prop.getProperty("ServerPort"));
		
		} catch (FileNotFoundException e) {
			System.out.println("We can't find a configuration file at \"conf/database.properties\"");
			System.out.println("[SHUTTING DOWN]");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getServerHost() {
		return ServerHost;
	}

	public void setServerHost(String serverHost) {
		ServerHost = serverHost;
	}

	public int getServerPort() {
		return ServerPort;
	}

	public void setServerPort(int serverPort) {
		ServerPort = serverPort;
	}
}
