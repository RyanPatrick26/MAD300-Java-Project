package client;
import javafx.application.Application;
import javafx.scene.Scene;
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
		// Create the initial BorderPane for the main scene
		BorderPane mainPane = new BorderPane();
		// Create the title for the primary stage
		Text formTitle = new Text("Bearham Game Manager");
		
		
		
		
		
		// Create the scene for the previously played games
		//	must store the game, the genre(s), and the date played
		
		
		Pane pane = new Pane();
		
		Scene scene = new Scene(mainPane, 800, 600);
		
		primaryStage.setTitle("[MAD300 Java Project]");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

}
