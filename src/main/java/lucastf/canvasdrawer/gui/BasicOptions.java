package lucastf.canvasdrawer.gui;

import java.io.IOException;

import lucastf.canvasdrawer.abstractions.OptionsPane;
import javafx.scene.Parent;

public class BasicOptions extends OptionsPane{

	private static final String fxml = "/gui/basicBar.fxml";
	
	public BasicOptions(Parent parent) throws IOException {
		super(parent, fxml);
	}

	@Override
	public double getIterationsValue() {
		return 0;
	}

}
