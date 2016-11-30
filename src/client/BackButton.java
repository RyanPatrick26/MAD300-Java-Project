package client;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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
			backButtonImageView.setStyle("-fx-opacity: 0.7;");
			// TODO Fix issue with mouse hover/border creation
			/**
			 * Event handler to bring the opacity of the back button to 1.0.
			 * 
			 * @author Nicholas Allaire
			 * @param None
			 */
			this.setOnMouseEntered(e->{
				backButtonImageView.setStyle("-fx-opacity: 1;");
			});
			/**
			 * Event handler to bring the opacity of the back button to 0.3.
			 * 
			 * @author Nicholas Allaire
			 * @param None
			 */
			this.setOnMouseExited(e->{
				backButtonImageView.setStyle("-fx-opacity: 0.7;");
			});
			this.setCenter(backButtonImageView);
		}
		else {
			this.setCenter(backButtonText);
		}
		
	}
	
	/**
	 * Sets an OnClick event to the BackButton that sets the scene passed Stage parameter
	 * to the passed Scene parameter and shows the Stage and displays an animation.
	 * 
	 * @author Nicholas Allaire & Megan Caza
	 * @param Stage stage
	 * @param Scene scene
	 */
	public void setBackButtonEvent(Stage stage, Scene scene) {
		this.setOnMouseClicked(e -> {
			//Quick transition to show button has been clicked
			FadeTransition fb = new FadeTransition(Duration.millis(200), this);
		    fb.setFromValue(1.0);
		    fb.setToValue(0.2);
		    fb.setCycleCount(2);
		    fb.setAutoReverse(true);
		    
		    // Make sure the animation is finished before switching scenes
		    fb.setOnFinished(new EventHandler<ActionEvent>(){
		    	 
	            @Override
	            public void handle(ActionEvent arg0) {
	            	stage.setScene(scene);
					stage.show();
	            }
	        });
		    fb.play();
		});
	}
	
}
