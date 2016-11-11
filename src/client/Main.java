package client;
import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	ArrayList<String> categories;
	static BackButton customBackButton;
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// must store the game name, the genre(s), date last played, publisher, year the game was released
		
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
		mainPane.setPadding(new Insets(10,10,10,10));
		mainPane.setTop(formTitleText);
		mainPane.setCenter(addGameText);
		mainPane.setBottom(bottomBox);
		
		// Create the scene for the main screen
		Scene mainScene = new Scene(mainPane, 800, 600);
		
		/* PREVIOUS GAMES SCREEN */
		
		// Create the back button
		File backImage = new File("images/back-arrow-icon.png");
		if(backImage.isFile()) {
			customBackButton = new BackButton("Back", new Image("file:./images/back-arrow-icon.png"));
			BorderPane.setAlignment(customBackButton, Pos.CENTER_LEFT);
			customBackButton.getStyleClass().add("backbutton");
			// Assign the event handler for the Back Button
			customBackButton.setBackButtonEvent(primaryStage, mainScene);
		} else {
			Button backButton = new Button("Back");
		}
		
		// Create the BorderPane for the previous games scene
		BorderPane previousGamesPane = new BorderPane();
		previousGamesPane.setPadding(new Insets(10,10,10,10));
		previousGamesPane.setTop(customBackButton);
		
		
		// Create the main and previousGames scenes
		Scene previousGamesScene = new Scene(previousGamesPane, 800, 600);
		previousGamesScene.getStylesheets().clear();
		previousGamesScene.getStylesheets().add("file:./stylesheets/previousgames.css");
		
		
		// Create event handler for the Previous Games Button
		previousGamesButton.setOnAction(e -> {
			primaryStage.setScene(previousGamesScene);
			primaryStage.show();
		});
		
		primaryStage.setTitle("[MAD300 Java Project]");
		primaryStage.setScene(mainScene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

}
