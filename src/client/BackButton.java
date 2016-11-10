package client;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BackButton extends BorderPane {
	private String backButtonString;
	private Image backButtonImage = null;
	
	public BackButton(String backButtonString, Image backButtonImage) {
		this.backButtonString = backButtonString;
		this.backButtonImage = backButtonImage;
	
		Text backButtonText = new Text(backButtonString);
		
		// Check to see if an image has been passed
		if(this.backButtonImage != null){
			this.setCenter(new ImageView(this.backButtonImage));
		}
		else {
			this.setCenter(backButtonText);
		}
		
	}
	
	/**
	 * Sets an OnClick event to the BackButton that sets the scene passed Stage parameter
	 * to the passed Scene parameter and shows the Stage.
	 * 
	 * @author Nicholas Allaire
	 * @param Stage stage
	 * @param Scene scene
	 */
	public void setBackButtonEvent(Stage stage, Scene scene) {
		this.setOnMouseClicked(e -> {
			stage.setScene(scene);
			stage.show();
		});
	}
	
}
