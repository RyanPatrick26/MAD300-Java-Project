package client;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;

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
import javafx.stage.Stage;

public class Main extends Application {

	ArrayList<String> categories;
	private static BackButton customBackButton;
	private static Button backButton;

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
		GameForm gameForm = new GameForm("Add New Game");
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

		// Create a button to get to DuckDuckGo
		Button duckButton = new Button("Head to DuckDuckGo");
		duckButton.setStyle("-fx-opacity: 0.05;");
		duckButton.setOnAction(e -> {
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI("https://duckduckgo.com/"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Create an VBox to store the bottom of the BorderPane
		VBox bottomBox = new VBox();
		bottomBox.getChildren().addAll(previousGamesButton, copyrightText,duckButton);
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