package client;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BackButton extends BorderPane {
	private Image backButtonImage = null;
	
	public BackButton(String backButtonString, Image backButtonImage) {
		this.backButtonImage = backButtonImage;
		Text backButtonText = new Text(backButtonString);
		
		this.setMaxWidth(100);
		
		// Check to see if an image has been passed
		if(this.backButtonImage != null){
			ImageView backButtonImageView = new ImageView(this.backButtonImage);
			backButtonImageView.setPreserveRatio(false);
			backButtonImageView.setFitHeight(125);
			backButtonImageView.setFitWidth(250);
			backButtonImageView.setStyle("-fx-opacity: 1.0;");
			// TODO Fix issue with mouse hover/border creation
			/**
			 * Event handler to bring the opacity of the back button to 1.0.
			 * 
			 * @author Nicholas Allaire
			 * @param None
			 */
			backButtonImageView.setOnMouseEntered(e->{
				backButtonImageView.setStyle("-fx-opacity: 0.3;");
			});
			/**
			 * Event handler to bring the opacity of the back button to 0.3.
			 * 
			 * @author Nicholas Allaire
			 * @param None
			 */
			backButtonImageView.setOnMouseExited(e->{
				backButtonImageView.setStyle("-fx-opacity: 1;");
			});
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
