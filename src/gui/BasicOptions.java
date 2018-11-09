package gui;

import java.io.IOException;

import abstractions.OptionsPane;
import javafx.scene.Parent;

public class BasicOptions extends OptionsPane{

	private static final String fxml = "fxml/basicBarFXML.fxml";
	
	public BasicOptions(Parent parent) throws IOException {
		super(parent, fxml);
	}

	@Override
	public double getIterationsValue() {
		return 0;
	}

	@Override
	public void setSimpleDrawingBar() {
		this.informationPane.setVisible(false);
		this.rotationPane.setVisible(false);
		this.scalePane.setVisible(false);
		this.diameterSlider.setDisable(false);
	}

}
