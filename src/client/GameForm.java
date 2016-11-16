package client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GameForm extends GridPane {
	Label titleLabel;
	TextField gameTitle;
	ObservableList<String> items;
	ListView<String> genreList;
	
	
	public GameForm() {
		this.setPadding(new Insets(10));
		this.setVgap(15);
		this.setHgap(15);
		
		this.titleLabel = new Label("Game Title:");
		
		this.gameTitle = new TextField();
		this.items = FXCollections.observableArrayList("Board Game","Card Game","Video Game");
		this.genreList = new ListView<>(items);
		this.genreList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.genreList.setMaxHeight(75);
		this.genreList.setOnMouseClicked(e->{
			ObservableList<String> selectedGenres = genreList.getSelectionModel().getSelectedItems();
			for(String string : selectedGenres) {
				System.out.println("Selected genre: " + string);
			}
		});
		
		
		this.add(titleLabel, 0, 0);
		this.add(genreList, 2, 2);
	}

}
