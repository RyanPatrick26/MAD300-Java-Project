package client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GameForm extends GridPane {
	
	private Label gameTitleLabel;
	private TextField gameTitleField;
	
	private Label genreLabel;
	
	private Label yearLabel;
	private TextField yearField;
	
	private Label publisherLabel;
	private TextField publisherField;
	
	
	private ObservableList<String> genres;
	private ListView<String> genreList;
	
	
	public GameForm() {
		// Create default styling for the GameForm
		this.setPadding(new Insets(15,10,15,10));
		this.setVgap(15);
		this.setHgap(15);
		
		// Set the game title label and add it to the grid
		this.gameTitleLabel = new Label("Game Title:");
		this.add(gameTitleLabel,0,2);
		
		// Set the textfield for the game title and add it to the grid
		this.gameTitleField = new TextField();
		this.add(gameTitleField,3,2);
		
		// Set the genre label and add it to the grid
		this.genreLabel = new Label("Genre(s) - Shift click to select more:");
		this.add(genreLabel, 0, 3);
		
		// Create an observable array list for the game genres
		this.genres = FXCollections.observableArrayList("Board Game","Card Game","Video Game");
		// Put the observable array list in a listView
		this.genreList = new ListView<>(genres);
		this.genreList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.genreList.setMaxHeight(75);
		// Add the listview to the gridpane
		this.add(genreList,3,3);
		
		/**
		 * When a list item is selected, the currently selected genre or genres will
		 * be displayed to the console. This is for testing purposes.
		 * @author Nicholas Allaire
		 * @param None
		 */
		this.genreList.setOnMouseClicked(e->{
			ObservableList<String> selectedGenres = genreList.getSelectionModel().getSelectedItems();
			for(String genreString : selectedGenres) {
				System.out.println(genreString);
			}
		});
		
		// Set the game release year label and add it to the grid
		yearLabel = new Label("Year Released:");
		this.add(yearLabel,0,4);
		
		// Set the textfield for the game release year and add it to the grid 
		this.yearField = new TextField();
		this.add(yearField,3,4);
		
		// Set the publisher label and add it to the grid
		this.publisherLabel = new Label("Game Publisher:");
		this.add(publisherLabel,0,5);
		
		// Set the textfield for the publisher and add it to the grid
		this.publisherField = new TextField();
		this.add(publisherField,3,5);
		
		// Create the submit and clear buttons
		Button submitButton = new Button("Submit Game");
		submitButton.setMinWidth(50);
		Button clearButton = new Button("Clear Form");
		
		// Give the buttons functionality
		submitButton.setOnAction(e->{
			gameTitleField.clear();
			genreList.getSelectionModel().clearSelection();
			yearField.clear();
			publisherField.clear();
			
		});
		clearButton.setOnAction(e->{
			gameTitleField.clear();
			genreList.getSelectionModel().clearSelection();
			yearField.clear();
			publisherField.clear();
		});
		
		// Add the buttons to the grid
		this.add(submitButton, 1, 7);
		this.add(clearButton, 2, 7);
		
	}

}
