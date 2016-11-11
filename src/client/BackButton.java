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
		
		this.setMaxWidth(100);
		
		// Check to see if an image has been passed
		if(this.backButtonImage != null){
			ImageView backButtonImageView = new ImageView(this.backButtonImage);
			backButtonImageView.setPreserveRatio(false);
			backButtonImageView.setFitHeight(100);
			backButtonImageView.setFitWidth(100);
			// TODO Fix issue with mouse hover/border creation
			/**
			 * Event handler to add a border around the button when the user
			 * hovers their mouse over the button.
			 * 
			 * @author Nicholas Allaire
			 * @param None
			 */
//			backButtonImageView.setOnMouseEntered(e -> {
//				this.setStyle("-fx-border-size: 20px;"
//						+ "-fx-border-color: red;"
//						+ "-fx-alignment: top-left;");
//			});
			this.setCenter(backButtonImageView);
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
