package client;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ButtonClick implements Runnable {

	/**
	 * Creates a media and an media player and plays a button sound.
	 * 
	 * @author Nicholas Allaire
	 * @param none
	 */
	@Override
	public void run() {
		Media buttonSound = new Media(new File("./audio/openbutton.wav").toURI().toString());
		MediaPlayer buttonPlayer = new MediaPlayer(buttonSound);
		buttonPlayer.setVolume(0.7);
		buttonPlayer.play();
	}

}
