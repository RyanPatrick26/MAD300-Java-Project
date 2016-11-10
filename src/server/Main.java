package server;

public class Main {

	public static void main(String[] args) {
		DatabaseConfig dbconf = new DatabaseConfig();
		System.out.println(dbconf.getDatabaseHost());
		System.out.println(dbconf.getDatabaseName());
		System.out.println(dbconf.getDatabasePass());
		System.out.println(dbconf.getDatabaseUser());
	}

}
