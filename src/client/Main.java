package client;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	ArrayList<String> categories;
	
	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		/* MAIN SCREEN */
		
		// Create the title for the primary stage
		Text formTitleText = new Text("Bearham Game Manager");
		
		// Create the form to add a new game to the game manager
		Text addGameText = new Text("Add New Game");
		
		
		
		
		
		
		
		// Create the button to go the Previous games list
		Button previousGamesButton = new Button("Previously Played Games");
		
		// Create the copyright information Text
		Text copyrightText = new Text("Copyright Team Bearham - 2016");
		
		// Create an VBox to store the bottom of the BorderPane 
		VBox bottomBox = new VBox();
		bottomBox.getChildren().addAll(previousGamesButton, copyrightText);
		
		
		// Create the BorderPane for the main scene
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(formTitleText);
		mainPane.setCenter(addGameText);
		mainPane.setBottom(bottomBox);
		
		/* PREVIOUS GAMES SCREEN */
		
		// Create the BorderPane for the previous games scene
		// must store the game name, the genre(s), date last played, publisher, year the game was released
		BorderPane previousGamesPane = new BorderPane();
		
		
		
		/* SCENES & STAGE */
		
		// Create the main and previousGames scenes
		Scene previousGamesScene = new Scene(previousGamesPane, 800, 600);
		Scene mainScene = new Scene(mainPane, 800, 600);
		
		// Create event handler for the Previous Games Button
		previousGamesButton.setOnAction(e -> {
			primaryStage.setScene(previousGamesScene);
			primaryStage.show();
		});
		// Create event handler for the Back Button
		
		
		primaryStage.setTitle("[MAD300 Java Project]");
		primaryStage.setScene(mainScene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

}
