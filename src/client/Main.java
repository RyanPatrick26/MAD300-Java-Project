package client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create the title for the primary stage
		Text formTitleText = new Text("Bearham Game Manager");
		
		// Create the form to add a new game to the game manager
		Text addGameText = new Text("Add New Game");
		
		
		
		
		
		
		
		// Create the button to go the Previous games list
		Button previousGamesButton = new Button("Previously Played Games");
		
		// Create the copyright information Text
		Text copyrightText = new Text("Copyright Team Bearham - 2016");
		
		// Create an HBox to store the bottom of the BorderPane 
		
		// Create the scene for the previously played games
		//must store the game name, the genre(s), date last played, publisher, year the game was released
		
		
		// Create the BorderPane for the main scene
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(formTitleText);
		mainPane.setCenter(addGameText);
		mainPane.setBottom(copyrightText);
		
		Scene mainScene = new Scene(mainPane, 800, 600);
		
		primaryStage.setTitle("[MAD300 Java Project]");
		primaryStage.setScene(mainScene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

}
