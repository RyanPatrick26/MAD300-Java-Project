package client;

import java.io.File;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BackButton extends BorderPane {
	private String backButtonString;
	private Image backButtonImage = null;
	Image customMouse = new Image("file:./images/arrow.png");
	
	public BackButton(String backButtonString, Image backButtonImage) {
		this.backButtonString = backButtonString;
		this.backButtonImage = backButtonImage;
		Text backButtonText = new Text(backButtonString);
		this.setMaxWidth(100);
		
		// Check to see if an image has been passed
		if(this.backButtonImage != null){
			ImageView backButtonImageView = new ImageView(this.backButtonImage);
			backButtonImageView.setPreserveRatio(false);
			backButtonImageView.setFitHeight(125);
			backButtonImageView.setFitWidth(250);
			backButtonImageView.setStyle("-fx-opacity: 0.8;");
			// TODO Fix issue with mouse hover/border creation
			/**
			 * Event handler to bring the opacity of the back button to 1.0.
			 * 
			 * @author Nicholas Allaire
			 * @param None
			 */
			this.setOnMouseEntered(e->{
				backButtonImageView.setStyle("-fx-opacity: 1.0;");
			});
			/**
			 * Event handler to bring the opacity of the back button to 0.3.
			 * 
			 * @author Nicholas Allaire
			 * @param None
			 */
			this.setOnMouseExited(e->{
				backButtonImageView.setStyle("-fx-opacity: 0.8;");
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
	 * @author Nicholas Allaire & Megan Caza
	 * @param Stage stage
	 * @param Scene scene
	 */
	public void setBackButtonEvent(Stage stage, Scene scene) {
		this.setOnMouseClicked(e -> {
			Main.previousGamesInfo.clear();
			Media buttonSound = new Media(new File("./audio/openbutton.wav").toURI().toString());
			MediaPlayer buttonPlayer = new MediaPlayer(buttonSound);
			buttonPlayer.setVolume(0.7);
			buttonPlayer.play();
			
			//Quick transition to show button has been clicked
			FadeTransition fb = new FadeTransition(Duration.millis(200), this);
		    fb.setFromValue(1.0);
		    fb.setToValue(0.2);
		    fb.setCycleCount(2);
		    fb.setAutoReverse(true);
		    
		    /**
			 * Makes sure the animation has finished before the scene is switched
			 * 
			 * @author Megan Caza
			 * @param None
			 */
		    fb.setOnFinished(new EventHandler<ActionEvent>(){
		    	 
	            @Override
	            public void handle(ActionEvent arg0) {
	            	stage.setScene(scene);
	            	scene.setCursor(new ImageCursor(customMouse));
	            	stage.show();
	            }
	        });
		    
		    fb.play();
		});
	}
	
}
