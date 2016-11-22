package client;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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
	
	public static void main(String[] args) {
		Application.launch(args);

	}
	
	public class CustomComparator implements Comparator<Game> {
	    @Override
	    public int compare(Game o1, Game o2) {
	        return o1.getName().compareTo(o2.getName());
	    }
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		categoryList = new ArrayList<String>();
		gameList = new ArrayList<Game>();
		
		categoryList.add("Board Game");
		categoryList.add("Video Game");
		categoryList.add("Card Game");
		
		gameList.add(new Game("Risk", new ArrayList<String>(Arrays.asList(categoryList.get(0)))));
		gameList.add(new Game("Archeage", new ArrayList<String>(Arrays.asList(categoryList.get(1)))));
		gameList.add(new Game("Magic the Gathering", new ArrayList<String>(Arrays.asList(categoryList.get(2)))));
		
		
		
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
							selectedGameList.add(gameList.get(i));
							Collections.sort(selectedGameList, new CustomComparator());
							gameListView.getItems().clear();
							ObservableList<Game> items =FXCollections.observableArrayList (selectedGameList);
							gameListView.setItems(items);
						}
					}
					else{
						if(gameList.get(i).getCategory().contains("Board Game")){
							selectedGameList.remove(gameList.get(i));
							Collections.sort(selectedGameList, new CustomComparator());
							gameListView.getItems().clear();
							ObservableList<Game> items =FXCollections.observableArrayList (selectedGameList);
							gameListView.setItems(items);
						}
					}
				}
			}
		});
		
		videoGame.selectedProperty().addListener(new ChangeListener<Boolean>(){
			public void changed(ObservableValue a, Boolean oldValue, Boolean newValue){
				for(int i = 0; i < gameList.size(); i++){
					if(newValue){
						if(gameList.get(i).getCategory().contains("Video Game")){
							selectedGameList.add(gameList.get(i));
							Collections.sort(selectedGameList, new CustomComparator());
							gameListView.getItems().clear();
							ObservableList<Game> items =FXCollections.observableArrayList (selectedGameList);
							gameListView.setItems(items);
						}
					}
					else{
						if(gameList.get(i).getCategory().contains("Video Game")){
							selectedGameList.remove(gameList.get(i));
							Collections.sort(selectedGameList, new CustomComparator());
							gameListView.getItems().clear();
							ObservableList<Game> items =FXCollections.observableArrayList (selectedGameList);
							gameListView.setItems(items);
						}
					}
				}
			}
		});
		
		cardGame.selectedProperty().addListener(new ChangeListener<Boolean>(){
			public void changed(ObservableValue a, Boolean oldValue, Boolean newValue){
				for(int i = 0; i < gameList.size(); i++){
					if(newValue){
						if(gameList.get(i).getCategory().contains("Card Game")){
							selectedGameList.add(gameList.get(i));
							Collections.sort(selectedGameList, new CustomComparator());
							gameListView.getItems().clear();
							ObservableList<Game> items =FXCollections.observableArrayList (selectedGameList);
							gameListView.setItems(items);
						}
					}
					else{
						if(gameList.get(i).getCategory().contains("Card Game")){
							selectedGameList.remove(gameList.get(i));
							Collections.sort(selectedGameList, new CustomComparator());
							gameListView.getItems().clear();
							ObservableList<Game> items =FXCollections.observableArrayList (selectedGameList);
							gameListView.setItems(items);
						}
					}
				}
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
