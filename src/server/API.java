package server;

import java.util.ArrayList;

import common.Game;

/**
 * API class for the project, oversees interaction between the form
 * and the server software + database
 * @author Brandon Brown
 * @version 1.0
 */
public class API {
	
	/**
	 * API function to retrieve all games from the database
	 * @return ArrayList<Game>	of games with their properties populated
	 * @author Brandon Brown
	 */
	public ArrayList<Game> getAllGames() {
		NetworkCommunication nc = new NetworkCommunication();
		ArrayList<ArrayList<String>> table = nc.getDataFromServer("GAMES.ALL");
		
		ArrayList<Game> returnArray = new ArrayList<Game>();
		
		if (table != null) {
			for (int i = 0; i < table.size(); i++) {
				ArrayList<String> categories = new ArrayList<String>();
				categories.add("Video Game");
				Game game = new Game(table.get(i).get(1), categories);
				try {
					game.setID(Integer.parseInt(table.get(i).get(0)));
				} catch (NumberFormatException e) {
					game.setID(-1);
				}
				try {
					game.setRating(Integer.parseInt(table.get(i).get(2)));
				} catch (NumberFormatException e) {
					game.setRating(-1);
				}
				game.setDescription(table.get(i).get(3));
				
				returnArray.add(game);
			}
		}
		
		nc = null;
		return returnArray;
	}

	/**
	 * API call to delete a game from the server based on it's id
	 * @param game	Game object for extracting it's ID so it can be
	 * 				removed from the database
	 * @author Brandon Brown
	 */
	public void deleteGame(Game game) {
		NetworkCommunication nc = new NetworkCommunication();
		
		int id = game.getID();
		
		nc.deleteDataFromServer("GAME", id);
		nc = null;
	}
	
	/**
	 * API call to update a row in the database from a game object
	 * @param gmae	Game object with it's properties to be sent to
	 * 				the server to overwrite the current ones.
	 * @author Brandon Brown
	 */
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
	
	/**
	 * API function to add a new game to the server from it's object file
	 * @param game	Game to be taken apart and inserted into a database row
	 * @author Brandon Brown
	 */
	public void addGame(Game game) {
		NetworkCommunication nc = new NetworkCommunication();
		
		ArrayList<String> schema = new ArrayList<String>();
		schema.add("GameName");
		schema.add("Rating");
		schema.add("Description");
		
		ArrayList<ArrayList<String>> insertionData = new ArrayList<ArrayList<String>>();
		insertionData.add(new ArrayList<String>());
		insertionData.get(0).add(game.getName());
		insertionData.get(0).add(String.valueOf(game.getRating()));
		insertionData.get(0).add(game.getDescription());
		
		nc.sendNewDataToServer(schema, "GAME", insertionData);
	}
}
