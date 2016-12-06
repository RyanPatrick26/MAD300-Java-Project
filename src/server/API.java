package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
				//System.out.println(table.get(i));
			}
			for (int i = 0; i < table.size(); i++) {
				//ArrayList<String> categories = new ArrayList<String>();
				//categories.add("Video Game");
				Game game = new Game();
				try {
					game.setID(Integer.parseInt(table.get(i).get(0)));
				} catch (NumberFormatException e) {
					game.setID(-1);
				}
				game.setName(table.get(i).get(1));
				try {
					game.setRating(Integer.parseInt(table.get(i).get(2)));
				} catch (NumberFormatException e) {
					game.setRating(-1);
				}
				game.setDescription(table.get(i).get(3));
				try {
					game.setReleaseYear(Integer.parseInt(table.get(i).get(4)));
				} catch (NumberFormatException e) {
					game.setReleaseYear(-1);
				}
				try {
					game.setHoursPlayed(Integer.parseInt(table.get(i).get(5)));
				} catch (NumberFormatException e) {
					game.setHoursPlayed(-1);
				}
				game.setPublisher(table.get(i).get(6));
				
				String stringCategories = table.get(i).get(7);
				System.out.println(stringCategories);
				ArrayList<String> categories = new  ArrayList<String>(Arrays.asList(stringCategories.split(",")));
				
				for (int j = 0; j < categories.size(); j++) {
					//System.out.println(categories.get(j));
				}
				game.setCategory(categories);
				
				
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
		schema.add("ReleaseYear");
		schema.add("HoursPlayed");
		schema.add("GamePublisher");
		schema.add("Categories");
		
		ArrayList<ArrayList<String>> insertionData = new ArrayList<ArrayList<String>>();
		System.out.println("ID: " + game.getID());
		insertionData.add(new ArrayList<String>());
		insertionData.get(0).add(String.valueOf(game.getID()));
		insertionData.get(0).add(game.getName());
		insertionData.get(0).add(String.valueOf(game.getRating()));
		insertionData.get(0).add(game.getDescription());
		insertionData.get(0).add(String.valueOf(game.getReleaseYear()));
		insertionData.get(0).add(String.valueOf(game.getHoursPlayed()));
		insertionData.get(0).add(game.getPublisher());
		String categories = "";
		for (int i = 0; i < game.getCategory().size(); i++) {
			categories += game.getCategory().get(i);
			if (i < game.getCategory().size() - 1) {
				categories += ",";
			}
		}
		System.out.println("CATEGORIES" + categories);
		insertionData.get(0).add(String.valueOf(categories));
		
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
		//schema.add("ID");
		schema.add("GameName");
		schema.add("Rating");
		schema.add("Description");
		schema.add("ReleaseYear");
		schema.add("HoursPlayed");
		schema.add("GamePublisher");
		schema.add("Categories");
		
		ArrayList<ArrayList<String>> insertionData = new ArrayList<ArrayList<String>>();
		//System.out.println("ID: " + game.getID());
		insertionData.add(new ArrayList<String>());
		//insertionData.get(0).add(String.valueOf(game.getID()));
		insertionData.get(0).add(game.getName());
		insertionData.get(0).add(String.valueOf(game.getRating()));
		insertionData.get(0).add(game.getDescription());
		insertionData.get(0).add(String.valueOf(game.getReleaseYear()));
		insertionData.get(0).add(String.valueOf(game.getHoursPlayed()));
		insertionData.get(0).add(game.getPublisher());
		String categories = "";
		for (int i = 0; i < game.getCategory().size(); i++) {
			categories += game.getCategory().get(i);
			if (i < game.getCategory().size() - 1) {
				categories += ",";
			}
		}
		System.out.println("CATEGORIES: " + categories);
		insertionData.get(0).add(String.valueOf(categories));
		
		nc.sendNewDataToServer(schema, "GAME", insertionData);
	}
}
