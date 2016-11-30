package client;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import common.Game;
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
	ArrayList<Game> selectedGameList = new ArrayList<Game>();
	
	public static void main(String[] args) {
		Application.launch(args);

	}
	
	/**
	   * Custom Function to sort the selectedGameList alphabetically.
	   * No inputs needed, just needs to be set as the comparator to use.
	   * Added by: Megan Caza 
	 **/
	public class CustomComparator implements Comparator<Game> {
	    @Override
	    public int compare(Game o1, Game o2) {
	        return o1.getName().compareTo(o2.getName());
	    }
	}
	
	/**
	   * Custom Function used to find the selected game category, search through the games created, add games to the selectedGameList,
	   * sort the selectedGameList alphabetically, and insert selectedGameList into the listview to display to the screen.
	   * INPUTS: boolean newValue, String gameType
	   * OUTPUTS: void
	   * Created by: Megan Caza 
	 **/
	public void buildListView(boolean newValue, String gameType){
		for(int i = 0; i < gameList.size(); i++){
			if(newValue){
				if(gameList.get(i).getCategory().contains(gameType)){
					selectedGameList.add(gameList.get(i));
					Collections.sort(selectedGameList, new CustomComparator());
					gameListView.getItems().clear();
					ObservableList<Game> items =FXCollections.observableArrayList (selectedGameList);
					gameListView.setItems(items);
				}
			}
			else{
				if(gameList.get(i).getCategory().contains(gameType)){
					selectedGameList.remove(gameList.get(i));
					Collections.sort(selectedGameList, new CustomComparator());
					gameListView.getItems().clear();
					ObservableList<Game> items =FXCollections.observableArrayList (selectedGameList);
					gameListView.setItems(items);
				}
			}
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
		
		
		gameListView = new ListView<Game>();
		
		boardGame.selectedProperty().addListener(new ChangeListener<Boolean>(){
			public void changed(ObservableValue a, Boolean oldValue, Boolean newValue){
				buildListView(newValue, "Board Game");
			}
				
		});
		
		videoGame.selectedProperty().addListener(new ChangeListener<Boolean>(){
			public void changed(ObservableValue a, Boolean oldValue, Boolean newValue){
				buildListView(newValue, "Video Game");
			}
		});
		
		cardGame.selectedProperty().addListener(new ChangeListener<Boolean>(){
			public void changed(ObservableValue a, Boolean oldValue, Boolean newValue){
				buildListView(newValue, "Card Game");
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
