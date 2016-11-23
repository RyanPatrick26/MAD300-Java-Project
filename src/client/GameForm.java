package client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameForm extends GridPane {
	
	Label titleLabel;
	TextField gameTitle;
	
	
	ObservableList<String> genres;
	ListView<String> genreList;
	
	
	public GameForm() {
		// Create default styling for the GameForm
		this.setPadding(new Insets(15,10,15,10));
		this.setVgap(15);
		this.setHgap(15);
		
		// Set the game title label and add it to the grid
		this.titleLabel = new Label("Game Title:");
		this.add(titleLabel,0,2);
		
		// Set the text field for the game title and add it to the grid
		this.gameTitle = new TextField();
		this.add(gameTitle,2,2);
		
		this.gameTitle = new TextField();
		this.genres = FXCollections.observableArrayList("Board Game","Card Game","Video Game");
		this.genreList = new ListView<>(genres);
		this.genreList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.genreList.setMaxHeight(75);
		this.genreList.setOnMouseClicked(e->{
			ObservableList<String> selectedGenres = genreList.getSelectionModel().getSelectedItems();
			for(String genreString : selectedGenres) {
				System.out.println(genreString);
			}
		});
		
		
		
	}

}
