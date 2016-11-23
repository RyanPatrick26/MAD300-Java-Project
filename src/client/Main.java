package client;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	private static BackButton customBackButton;
	private static Button backButton;
	ArrayList<String> categoryList;
	ArrayList<Game> gameList;
	ListView<Game> gameListView;	
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// must store the game name, the genre(s), date last played, publisher,
		// year the game was released

		/* MAIN SCREEN */

		// Create the title for the primary stage
		Text formTitleText = new Text("Bearham Game Manager");

		// Create the form to add a new game to the game manager
		GameForm gameForm = new GameForm();
		Text addGameText = new Text("Add New Game");

		// Create HBox to store form title and form
		HBox formBox = new HBox();
		formBox.getChildren().addAll(gameForm);
		formBox.setAlignment(Pos.CENTER);
		formBox.setPadding(new Insets(10, 10, 10, 10));
		formBox.setSpacing(10);

		// Create the button to go the Previous games list
		Button previousGamesButton = new Button("Previously Played Games");

		// Create the copyright information Text
		Text copyrightText = new Text("Copyright Team Bearham - 2016");

		// Create an VBox to store the bottom of the BorderPane
		VBox bottomBox = new VBox();
		bottomBox.getChildren().addAll(previousGamesButton, copyrightText);
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setSpacing(10);

		// Create the BorderPane for the main scene
		BorderPane mainPane = new BorderPane();
		mainPane.setPadding(new Insets(10, 10, 10, 10));
		mainPane.setTop(formTitleText);
		mainPane.setCenter(formBox);
		mainPane.setBottom(bottomBox);
		BorderPane.setAlignment(formTitleText, Pos.CENTER);

		// Create the scene for the main screen
		Scene mainScene = new Scene(mainPane, 800, 600);

		/* PREVIOUS GAMES SCREEN */

		// Create the back button
		File backImage = new File("images/back-arrow-icon.png");
		// Check if the image file is a file. If not, create a regular button
		if (backImage.isFile()) {
			customBackButton = new BackButton("Back", new Image("file:./images/back-arrow-icon.png"));
			BorderPane.setAlignment(customBackButton, Pos.CENTER_LEFT);
			// Assign the event handler for the Back Button
			customBackButton.setBackButtonEvent(primaryStage, mainScene);
		} else {
			backButton = new Button("Back");
			backButton.setOnAction(e -> {
				primaryStage.setScene(mainScene);
				primaryStage.show();
			});
		}

		// Create the BorderPane for the previous games scene
		BorderPane previousGamesPane = new BorderPane();
		previousGamesPane.setPadding(new Insets(10, 10, 10, 10));

		// Check if the image for the back button is a file. If not, set the
		// regular button
		if (backImage.isFile()) {
			previousGamesPane.setTop(customBackButton);
		} else {
			previousGamesPane.setTop(backButton);
		}

		// Create the main and previousGames scenes
		Scene previousGamesScene = new Scene(previousGamesPane, 800, 600);

		// Create event handler for the Previous Games Button
		/**
		 * Switches to the previously played games screen
		 * @author Nicholas Allaire
		 * @param None
		 */
		previousGamesButton.setOnAction(e -> {
			primaryStage.setScene(previousGamesScene);
			primaryStage.show();
		});
		
		categoryList = new ArrayList<String>();
		gameList = new ArrayList<Game>();
		categoryList.add("Board Game");
		categoryList.add("Video Game");
		categoryList.add("Card Game");
		
		gameList.add(new Game("Risk", new ArrayList<String>(Arrays.asList(categoryList.get(0)))));
		gameList.add(new Game("Magic the Gathering", new ArrayList<String>(Arrays.asList(categoryList.get(1), categoryList.get(2)))));
		gameList.add(new Game("Mansions of Madness", new ArrayList<String>(Arrays.asList(categoryList.get(0), categoryList.get(2)))));
		gameList.add(new Game("Exceed", new ArrayList<String>(Arrays.asList(categoryList.get(0), categoryList.get(1)))));
		gameList.add(new Game("Total War: Warhammer", new ArrayList<String>(Arrays.asList(categoryList.get(2)))));
		gameList.add(new Game("Splendor", new ArrayList<String>(Arrays.asList(categoryList.get(0)))));
		
		CheckBox boardGame = new CheckBox("Board Games");
		CheckBox videoGame = new CheckBox("Video Games");
		CheckBox cardGame = new CheckBox("Card Games");
		
		ArrayList<Game> selectedGameList = new ArrayList<Game>();
		gameListView = new ListView<Game>();
		
		boardGame.selectedProperty().addListener(new ChangeListener<Boolean>(){
			public void changed(ObservableValue a, Boolean oldValue, Boolean newValue){
				for(int i = 0; i < gameList.size(); i++){
					if(newValue){
						if(gameList.get(i).getCategory().contains("Board Game")){
							System.out.println(gameList.get(i));
							selectedGameList.add(gameList.get(i));
						}
					}
					else{
						if(gameList.get(i).getCategory().contains("Board Game")){
							System.out.println(gameList.get(i));
							selectedGameList.remove(gameList.get(i));
						}
					}
				}
			}
		});
		
		VBox categoryBox = new VBox();
		categoryBox.getChildren().addAll(boardGame, videoGame, cardGame);
		
		HBox pane = new HBox();
		pane.getChildren().addAll(categoryBox, gameListView);
		
		primaryStage.setTitle("[MAD300 Java Project]");
		primaryStage.setScene(mainScene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

}
