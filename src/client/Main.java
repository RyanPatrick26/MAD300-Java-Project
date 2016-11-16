package client;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	ArrayList<String> categoryList;
	ArrayList<Game> gameList;
	ListView<Game> gameListView;	
	ObservableList<Game> selectedGameList;
	
	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		categoryList = new ArrayList<String>();
		gameList = new ArrayList<Game>();
		categoryList.add("Board Game");
		categoryList.add("Video Game");
		categoryList.add("Card Game");
		
		gameList.add(new Game("Risk", new ArrayList<String>(Arrays.asList(categoryList.get(0)))));
		gameList.add(new Game("Magic the Gathering", new ArrayList<String>(Arrays.asList(categoryList.get(1), categoryList.get(2)))));
		gameList.add(new Game("Mansions of Madness", new ArrayList<String>(Arrays.asList(categoryList.get(0), categoryList.get(2)))));
		gameList.add(new Game("Exceed", new ArrayList<String>(Arrays.asList(categoryList.get(0), categoryList.get(2)))));
		gameList.add(new Game("Total War: Warhammer", new ArrayList<String>(Arrays.asList(categoryList.get(1)))));
		gameList.add(new Game("Splendor", new ArrayList<String>(Arrays.asList(categoryList.get(0)))));
		
		CheckBox boardGame = new CheckBox("Board Games");
		CheckBox videoGame = new CheckBox("Video Games");
		CheckBox cardGame = new CheckBox("Card Games");

		selectedGameList = FXCollections.observableList(new ArrayList<Game>());
		gameListView = new ListView<Game>();
		
		boardGame.selectedProperty().addListener(new ChangeListener<Boolean>(){
			public void changed(ObservableValue a, Boolean oldValue, Boolean newValue){
				ArrayList<Game> boardGameList = new ArrayList<Game>();
				for(int i = 0; i < gameList.size(); i++){
					if(newValue){
						if(gameList.get(i).getCategory().contains("Board Game")){
							if(!selectedGameList.contains(gameList.get(i))){
								selectedGameList.add(gameList.get(i));
							}
						}
					}
					else{
						if(gameList.get(i).getCategory().contains("Board Game")){
							selectedGameList.remove(gameList.get(i));
						}
					}
				}
				gameListView.setItems(selectedGameList);

				//selectedGameList.removeAll(boardGameList);
			}
		});
		videoGame.selectedProperty().addListener(new ChangeListener<Boolean>(){
			public void changed(ObservableValue a, Boolean oldValue, Boolean newValue){
				ArrayList<Game> videoGameList = new ArrayList<Game>();
				for(int i = 0; i < gameList.size(); i++){
					if(newValue){
						if(gameList.get(i).getCategory().contains("Video Game")){
							if(!selectedGameList.contains(gameList.get(i))){
								selectedGameList.add(gameList.get(i));
							}
						}
					}
					else{
						if(gameList.get(i).getCategory().contains("Video Game")){
							selectedGameList.remove(gameList.get(i));
						}
					}
				}
				gameListView.setItems(selectedGameList);
			}
		});
		cardGame.selectedProperty().addListener(new ChangeListener<Boolean>(){
			public void changed(ObservableValue a, Boolean oldValue, Boolean newValue){
				ArrayList<Game> cardGameList = new ArrayList<Game>();
				for(int i = 0; i < gameList.size(); i++){
					if(newValue){
						if(gameList.get(i).getCategory().contains("Card Game")){
							if(!selectedGameList.contains(gameList.get(i))){
								selectedGameList.add(gameList.get(i));
							}
						}
					}
					else{
						if(gameList.get(i).getCategory().contains("Card Game")){
							selectedGameList.remove(gameList.get(i));
						}
					}
				}
				gameListView.setItems(selectedGameList);
			}
		});
		
		
		
		VBox categoryBox = new VBox();
		categoryBox.getChildren().addAll(boardGame, videoGame, cardGame);
		
		HBox pane = new HBox();
		pane.getChildren().addAll(categoryBox, gameListView);
		
		Scene scene = new Scene(pane, 800, 600);
		
		primaryStage.setTitle("[MAD300 Java Project]");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

}
