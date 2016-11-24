package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

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

	private Label hoursPlayedLabel;
	private TextField hoursPlayedField;

	private Label gameDescriptionLabel;
	private TextArea gameDescriptionArea;

	public GameForm() {
		// Create default styling for the GameForm
		this.setPadding(new Insets(15, 10, 15, 10));
		this.setVgap(15);
		this.setHgap(15);

		// Set the game title label and add it to the grid
		this.gameTitleLabel = new Label("Game Title:");
		this.add(gameTitleLabel, 0, 2);

		// Set the textfield for the game title and add it to the grid
		this.gameTitleField = new TextField();
		this.add(gameTitleField, 3, 2);

		// Set the genre label and add it to the grid
		this.genreLabel = new Label("Genre(s) - Shift click to select more than 1:");
		this.add(genreLabel, 0, 3);

		// Create an observable array list for the game genres
		this.genres = FXCollections.observableArrayList("Board Game", "Card Game", "Video Game");
		// Put the observable array list in a listView
		this.genreList = new ListView<>(genres);
		this.genreList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.genreList.setMaxHeight(75);
		// Add the listview to the gridpane
		this.add(genreList, 3, 3);

		/**
		 * When a list item is selected, the currently selected genre or genres
		 * will be displayed to the console. This is for testing purposes.
		 * 
		 * @author Nicholas Allaire
		 * @param None
		 */
		this.genreList.setOnMouseClicked(e -> {
			ObservableList<String> selectedGenres = genreList.getSelectionModel().getSelectedItems();
			for (String genreString : selectedGenres) {
				System.out.println(genreString);
			}
		});

		// Set the game release year label and add it to the grid
		yearLabel = new Label("Year Released:");
		this.add(yearLabel, 0, 4);

		// Set the textfield for the game release year and add it to the grid
		this.yearField = new TextField();
		this.add(yearField, 3, 4);

		// Set the publisher label and add it to the grid
		this.publisherLabel = new Label("Game Publisher:");
		this.add(publisherLabel, 0, 5);

		// Set the textfield for the publisher and add it to the grid
		this.publisherField = new TextField();
		this.add(publisherField, 3, 5);

		// Set the label for the hours played and add it to the grid
		hoursPlayedLabel = new Label("Hours Played:");
		this.add(hoursPlayedLabel, 0, 6);

		// Set the textfield for the hours played and add it to the grid
		hoursPlayedField = new TextField("");
		this.add(hoursPlayedField, 3, 6);

		// Set the label for the game description
		gameDescriptionLabel = new Label("Game Description:");
		GridPane.setHalignment(gameDescriptionLabel, HPos.LEFT);
		GridPane.setValignment(gameDescriptionLabel, VPos.TOP);
		this.add(gameDescriptionLabel, 0, 7);

		// Set the textarea for the game description and add it to the grid
		gameDescriptionArea = new TextArea();
		gameDescriptionArea.setMaxSize(320, 100);
		this.add(gameDescriptionArea, 3, 7);

		// Create the submit and clear buttons
		Button submitButton = new Button("Submit Game");
		submitButton.setMinWidth(100);
		Button clearButton = new Button("Clear Form");
		clearButton.setMinWidth(100);

		// Give the buttons functionality
		/**
		 * Clears the form on button press. TODO: Add database functionality.
		 * 
		 * @author Nicholas Allaire
		 * @param None
		 */
		submitButton.setOnAction(e -> {
			gameTitleField.clear();
			genreList.getSelectionModel().clearSelection();
			yearField.clear();
			publisherField.clear();
			hoursPlayedField.clear();
			gameDescriptionArea.clear();

		});
		/**
		 * Clears the form on button press.
		 * 
		 * @author Nicholas Allaire
		 * @param None
		 */
		clearButton.setOnAction(e -> {
			gameTitleField.clear();
			genreList.getSelectionModel().clearSelection();
			yearField.clear();
			publisherField.clear();
			hoursPlayedField.clear();
			gameDescriptionArea.clear();
		});

		// Create HBox
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(submitButton, clearButton);
		buttonBox.setPadding(new Insets(5, 5, 5, 5));
		buttonBox.setSpacing(15);
		GridPane.setColumnSpan(buttonBox, 2);

		// Add the buttons to the grid
		this.add(buttonBox, 1, 9);

	}

}
