package client;

import common.Game;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import common.Game;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.TimelineBuilder;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import server.API;
import javafx.util.Duration;

public class GameForm extends GridPane {

	// Declare all the form variables
	private Label gameTitleLabel;
	private TextField gameTitleField;
	
	private Label yearLabel;
	private TextField yearField;
	
	private Label ratingLabel;
	private TextField ratingField;
	
	private Label publisherLabel;
	private TextField publisherField;
	
	private Label genreLabel;
	private ObservableList<String> genres;
	private ListView<String> genreList;

	private Label hoursPlayedLabel;
	private TextField hoursPlayedField;

	private Label gameDescriptionLabel;
	private TextArea gameDescriptionArea;

	// Create an array for the textfields
	private TextField[] textfields = new TextField[5];

	private Button submitButton;
	private Button clearButton;

	private Game tempGame;
	
	private ButtonClick buttonSound = new ButtonClick();

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
		// Add the game title field to the textfields array
		textfields[0] = gameTitleField;

		// Set the genre label and add it to the grid
		this.genreLabel = new Label("Genre(s) - Shift click to select\nmore than 1:");
		this.add(genreLabel, 0, 3);

		// Create an observable array list for the game genres
		this.genres = FXCollections.observableArrayList("Board Game", "Card Game", "Video Game");
		// Put the observable array list in a listView
		this.genreList = new ListView<>(genres);
		this.genreList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.genreList.setMaxHeight(125);
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
		});

		// Set the game release year label and add it to the grid
		yearLabel = new Label("Year Released (ex: 1998):");
		this.add(yearLabel, 0, 4);

		// Set the textfield for the game release year and add it to the grid
		this.yearField = new TextField();
		this.add(yearField, 3, 4);

		// Add the game year field to the textfields array
		textfields[1] = gameTitleField;

		// Set the publisher label and add it to the grid
		this.publisherLabel = new Label("Game Publisher:");
		this.add(publisherLabel, 0, 5);

		// Set the textfield for the publisher and add it to the grid
		this.publisherField = new TextField();
		this.add(publisherField, 3, 5);

		// Add the game publisher field to the textfields array
		textfields[2] = gameTitleField;

		// Set the label for the hours played and add it to the grid
		hoursPlayedLabel = new Label("Hours Played (ex: 5):");
		this.add(hoursPlayedLabel, 0, 6);

		// Set the textfield for the hours played and add it to the grid
		hoursPlayedField = new TextField("");
		this.add(hoursPlayedField, 3, 6);

		// Add the hours played field to the textfields array
		textfields[3] = gameTitleField;

		// Set the label for the rating and add it to the grid
		ratingLabel = new Label("Rating: (ex: 10)");
		this.add(ratingLabel, 0, 7);

		// Set the textfield for the hours played and add it to the grid
		ratingField = new TextField("");
		this.add(ratingField, 3, 7);

		// Add the hours played field to the textfields array
		textfields[4] = ratingField;

		// Set the label for the game description
		gameDescriptionLabel = new Label("Game Description:");
		GridPane.setHalignment(gameDescriptionLabel, HPos.LEFT);
		GridPane.setValignment(gameDescriptionLabel, VPos.TOP);
		this.add(gameDescriptionLabel, 0, 8);

		// Set the textarea for the game description and add it to the grid
		gameDescriptionArea = new TextArea();
		gameDescriptionArea.setMaxSize(320, 150);
		gameDescriptionArea.setEditable(true);
		gameDescriptionArea.setWrapText(true);
		this.add(gameDescriptionArea, 3, 8);

		// Create the submit and clear buttons
		submitButton = new Button("SUBMIT GAME");
		submitButton.setMinWidth(150);
		clearButton = new Button("CLEAR FORM");
		clearButton.setMinWidth(150);

		// Give the buttons functionality
		submitButton.setOnAction(e -> {
			submitForm();
		});

		clearButton.setOnAction(e -> {
			clearForm();
		});

		// Create HBox
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(submitButton, clearButton);
		submitButton.setAlignment(Pos.CENTER);
		clearButton.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(20);
		buttonBox.setAlignment(Pos.TOP_CENTER);
		GameForm.setColumnSpan(buttonBox, 4);
		this.add(buttonBox, 0, 11);

	}

	/**
	 * Method to submit the entries in the form First creates a temporary game
	 * object to store the data before inserting it into the database. Then
	 * checks to make sure that the entries in the form are valid. If they are,
	 * inserts the information into the object then clears the form. If not,
	 * informs the user, and prompts them to try again
	 * 
	 * @author Ryan Patrick
	 * @param none
	 */
	private void submitForm() {

		tempGame = new Game();
		if (checkForValidEntry()) {
			Thread thread = new Thread(buttonSound);
			thread.start();
			
			FadeTransition ft = new FadeTransition(Duration.millis(200), submitButton);
			ft.setFromValue(1.0);
			ft.setToValue(0.2);
			ft.setCycleCount(2);
			ft.setAutoReverse(true);
			ft.play();

			ArrayList<String> categoryList = new ArrayList<>();
			ObservableList<String> selectedItems = genreList.getSelectionModel().getSelectedItems();
			for (int i = 0; i < selectedItems.size(); i++) {
				categoryList.add(selectedItems.get(i));
			}

			DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			Calendar temp = Calendar.getInstance();
			format.setCalendar(temp);

			tempGame.setName(gameTitleField.getText());
			tempGame.setCategory(categoryList);
			tempGame.setPublisher(publisherField.getText());
			tempGame.setDescription(gameDescriptionArea.getText());
			tempGame.setRating(Integer.parseInt(ratingField.getText()));
			tempGame.setHoursPlayed(Integer.parseInt(hoursPlayedField.getText()));
			tempGame.setReleaseYear(Integer.parseInt(yearField.getText()));
			tempGame.setLastPlayed(format.getCalendar());

			API api = new API();

			api.addGame(tempGame);

			clearForm();
		} else {
			error();
		}
	}

	/**
	 * Method to clear all data from the form
	 * 
	 * @author: Ryan Patrick
	 * @param: none
	 */
	private void clearForm() {
		Thread thread = new Thread(buttonSound);
		thread.start();
		
		// Quick transition to show button has been clicked
		FadeTransition ft = new FadeTransition(Duration.millis(200), clearButton);
		ft.setFromValue(1.0);
		ft.setToValue(0.2);
		ft.setCycleCount(2);
		ft.setAutoReverse(true);
		ft.play();
		
		gameTitleField.clear();
		yearField.clear();
		publisherField.clear();
		hoursPlayedField.clear();
		gameDescriptionArea.clear();
		ratingField.clear();
		genreList.getSelectionModel().clearSelection();
	}

	private boolean checkForValidEntry() {
		// TODO Auto-generated method stub
		if (gameDescriptionArea.getText().isEmpty()) {
			return false;
		}
		for (int i = 0; i < textfields.length; i++) {
			if (textfields[i].getText().isEmpty()) {
				return false;
			}
		}

		try {
			int tempYear = Integer.parseInt(yearField.getText());
			int tempRating = Integer.parseInt(ratingField.getText());
			int tempHoursPlayed = Integer.parseInt(hoursPlayedField.getText());
		} catch (NumberFormatException ex) {
			return false;
		}

		return true;

	}

	/**
	 * Creates a media and an media player and plays an error sound when the
	 * user tries to submit an invalid form. An alert box is also displayed
	 * prompting the user to enter the correct information in the form. An
	 * additional warning is issued if the user decides to get sassy with the
	 * software.
	 * 
	 * @author Nicholas Allaire
	 * @param none
	 */
	private void error() {
		shakeScreen(this);
		Media error = new Media(new File("./audio/error.wav").toURI().toString());
		MediaPlayer errorPlayer = new MediaPlayer(error);
		errorPlayer.setVolume(0.7);
		errorPlayer.play();
		Alert alert = new Alert(AlertType.ERROR,
				"Please fill out " + "the form completely with the appropriate information. Thanks!", ButtonType.OK,
				ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.NO) {
			MediaPlayer errorPlayer2 = new MediaPlayer(error);
			errorPlayer2.setVolume(0.7);
			errorPlayer2.play();
			Alert alertNo = new Alert(AlertType.WARNING, "You have to. Don't be sassy! >:(", ButtonType.OK);
			alertNo.showAndWait();
		}
	}
	
	/**
	 * Shakes the form using a translate transition when the user tries to 
	 * submit the form improperly.
	 * 
	 * @author Nicholas Allaire
	 * @param form
	 */
	public static void shakeScreen(Node form){
		TranslateTransition formShake = new TranslateTransition(Duration.millis(100), form);
		formShake.setCycleCount(8);
		formShake.setByX(7);
		formShake.setAutoReverse(true);
		formShake.playFromStart();
	}
}
