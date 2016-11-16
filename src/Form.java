import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Form extends GridPane {
	Label titleLabel;
	TextField gameTitle;
	
	public Form() {
		this.setPadding(new Insets(10));
	}

}
