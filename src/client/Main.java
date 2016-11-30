package client;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javafx.animation.FadeTransition;
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	private static BackButton customBackButton;
	private static Button backButton;
	ArrayList<String> categoryList;
	ArrayList<Game> gameList;
	ListView<Game> gameListView;
	ArrayList<Game> selectedGameList = new ArrayList<Game>();
	CheckBox boardGame = new CheckBox("Board Games");
	CheckBox videoGame = new CheckBox("Video Games");
	CheckBox cardGame = new CheckBox("Card Games");
	
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

		// Create the text to prompt the user to add a new game
		Text addGameText = new Text("Add New Game");

		// Add the form title and addGameText to a VBox
		VBox topBox = new VBox();
		topBox.getChildren().addAll(formTitleText, addGameText);
		topBox.setAlignment(Pos.CENTER);
		topBox.setPadding(new Insets(10, 10, 10, 10));
		topBox.setSpacing(10);

		// Create the form to add a new game to the game manager
		GameForm gameForm = new GameForm();

		// Create HBox to store form title and form
		HBox formBox = new HBox();
		formBox.getChildren().add(gameForm);
		formBox.setAlignment(Pos.CENTER);
		formBox.setPadding(new Insets(10, 10, 10, 10));
		formBox.setSpacing(10);

		// Create the button to go the Previous games list
		Button previousGamesButton = new Button("Previously Played Games");

		// Create the copyright information Text
		// TODO: Add correct copyright information
		Text copyrightText = new Text("Copyright Team Bearham - 2016");

		// Create an VBox to store the bottom of the BorderPane
		VBox bottomBox = new VBox();
		bottomBox.getChildren().addAll(previousGamesButton, copyrightText);
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setSpacing(10);

		// Create the BorderPane for the main scene
		BorderPane mainPane = new BorderPane();
		mainPane.setPadding(new Insets(10, 10, 10, 10));
		mainPane.setTop(topBox);
		mainPane.setCenter(formBox);
		mainPane.setBottom(bottomBox);
		BorderPane.setAlignment(formTitleText, Pos.CENTER);

		// Create the scene for the main screen
		Scene mainScene = new Scene(mainPane, 800, 700);
		mainScene.getStylesheets().add("file:./styles/main.css");

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
				//Quick transition to show button has been clicked
				FadeTransition fb = new FadeTransition(Duration.millis(300), backButton);
			    fb.setFromValue(1.0);
			    fb.setToValue(0.2);
			    fb.setCycleCount(2);
			    fb.setAutoReverse(true);
			    
			    /**
				 * Makes sure the animation has finished before the scene is switched
				 * 
				 * @author Megan Caza
				 * @param None
				 */
			    fb.setOnFinished(new EventHandler<ActionEvent>(){
			    	 
		            @Override
		            public void handle(ActionEvent arg0) {
		            	primaryStage.setScene(mainScene);
						primaryStage.show();
		            }
		        });
			    
			    fb.play();
				
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
		Scene previousGamesScene = new Scene(previousGamesPane, 800, 700);
		previousGamesScene.getStylesheets().add("file:./styles/main.css");

		// Create event handler for the Previous Games Button
		/**
		 * Switches to the previously played games screen
		 * 
		 * @author Nicholas Allaire, Megan Caza
		 * @param None
		 */
		previousGamesButton.setOnAction(e -> {
			//Quick transition to show button has been clicked
			FadeTransition ft = new FadeTransition(Duration.millis(300), previousGamesButton);
		    ft.setFromValue(1.0);
		    ft.setToValue(0.2);
		    ft.setCycleCount(2);
		    ft.setAutoReverse(true);
		    
		    /**
			 * Makes sure the animation has finished before the scene is switched
			 * 
			 * @author Megan Caza
			 * @param None
			 */
		    ft.setOnFinished(new EventHandler<ActionEvent>(){
		    	 
	            @Override
	            public void handle(ActionEvent arg0) {
	            	primaryStage.setScene(previousGamesScene);
	    			primaryStage.show();
	            }
	        });
		    
		    ft.play();
		    
		});

		categoryList = new ArrayList<String>();
		gameList = new ArrayList<Game>();

		categoryList.add("Board Game");
		categoryList.add("Video Game");
		categoryList.add("Card Game");

		gameList.add(new Game("Risk", new ArrayList<String>(Arrays.asList(categoryList.get(0)))));
		gameList.add(new Game("Magic the Gathering", new ArrayList<String>(Arrays.asList(categoryList.get(1), categoryList.get(2)))));
		gameList.add(new Game("Mansions of Madness", new ArrayList<String>(Arrays.asList(categoryList.get(0), categoryList.get(1)))));
		gameList.add(new Game("Exceed", new ArrayList<String>(Arrays.asList(categoryList.get(0), categoryList.get(2)))));
		gameList.add(new Game("Total War: Warhammer", new ArrayList<String>(Arrays.asList(categoryList.get(1)))));
		gameList.add(new Game("Splendor", new ArrayList<String>(Arrays.asList(categoryList.get(0)))));
		gameList.add(new Game("Archeage", new ArrayList<String>(Arrays.asList(categoryList.get(1)))));
;

		gameListView = new ListView<Game>();
		
		//Event Listeners for selecting and deselecting the checkboxes
		boardGame.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue a, Boolean oldValue, Boolean newValue) {
				buildListView(newValue, "Board Game");
			}

		});

		videoGame.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue a, Boolean oldValue, Boolean newValue) {
				buildListView(newValue, "Video Game");
			}
		});

		cardGame.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue a, Boolean oldValue, Boolean newValue) {
				buildListView(newValue, "Card Game");
			}
		});

		VBox categoryBox = new VBox();
		categoryBox.getChildren().addAll(boardGame, videoGame, cardGame);

		HBox pane = new HBox();
		pane.getChildren().addAll(categoryBox, gameListView);

		// Add the categoryBox and gameListView to the previous games screen
		previousGamesPane.setCenter(pane);

		primaryStage.setTitle("[MAD300 Java Project]");
		primaryStage.setScene(mainScene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	/**
	 * Custom Function to sort the selectedGameList alphabetically. 
	 * 
	 * @param: none 
	 * @author: Megan Caza
	 **/
	public class CustomComparator implements Comparator<Game> {
		@Override
		public int compare(Game o1, Game o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}

	/**
	 * Custom Function used to find the selected game category, search through
	 * the games created, add games to the selectedGameList, sort the
	 * selectedGameList alphabetically, and insert selectedGameList into the
	 * listview to display to the screen. 
	 * 
	 * @param: boolean newValue, String, gameType 
	 * @author: Megan Caza, Ryan Patrick
	 */
	public void buildListView(boolean newValue, String gameType) {
		//loop through the overall game list to find any games that
		//are part of a given category
		for (int i = 0; i < gameList.size(); i++) {
			//if statement checks if the checkbox is checked or not
			//runs if the checkbox is checked
			if (newValue) {
				//check to see if the game at the given index is part of the category selected
				if (gameList.get(i).getCategory().contains(gameType)) {
					//check to see if the list shown to the user already has a game in it
					if(!selectedGameList.contains(gameList.get(i))){
						//add the game to the list
						selectedGameList.add(gameList.get(i));
						Collections.sort(selectedGameList, new CustomComparator());
						gameListView.getItems().clear();
						ObservableList<Game> items = FXCollections.observableArrayList(selectedGameList);
						gameListView.setItems(items);
					}
				}
			} 
			//runs if the checkbox is unchecked
			else {
				//check to see if the game at the given index is part of the category selected
				//after unchecking a checkbox, checks if the game is part
				//of any of the other categories still selected
				if (gameList.get(i).getCategory().contains(gameType)){
					selectedGameList.clear();
					if(boardGame.isSelected()){
						buildListView(true, "Board Game");
					}
					if(videoGame.isSelected()){
						buildListView(true, "Video Game");
					}
					if(cardGame.isSelected()){
						buildListView(true, "Card Game");
					}
					ObservableList<Game> items = FXCollections.observableArrayList(selectedGameList);
					gameListView.setItems(items);
				}
			}
		}

	}
}
