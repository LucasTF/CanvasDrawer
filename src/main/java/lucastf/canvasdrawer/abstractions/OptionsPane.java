package lucastf.canvasdrawer.abstractions;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public abstract class OptionsPane {
	
	@FXML protected AnchorPane optionsPane;
	@FXML protected Slider diameterSlider;
	@FXML protected Text selectedObjectText;
	@FXML protected Label selectedObjectLabel;
	@FXML protected Text deleteInstructionText;
	@FXML protected Text translateInstructionText;
	@FXML protected Text rotateInstructionText;
	@FXML protected Text scaleInstructionText;
	
	public OptionsPane(Parent parent, String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
		loader.setController(this);
		parent = loader.load();
	}
	
	public AnchorPane getOptionsPane() {
		return optionsPane;
	}
	
	public double getThicknessValue() {
		return diameterSlider.getValue();
	}
	
	public void disableSlider(boolean x) {
		diameterSlider.setDisable(x);
	}
	
	public void setSelectedObjectLabel(String s) {
		selectedObjectLabel.setText(s);
	}
	
	public void setSelectedObjectInformationVisible(boolean d) {
		selectedObjectText.setVisible(d);
		selectedObjectLabel.setVisible(d);
	}
	
	public void setDeleteInstructionVisible(boolean d) {
		deleteInstructionText.setVisible(d);
	}
	
	public void setTranslateInstructionVisible(boolean d) {
		translateInstructionText.setVisible(d);
	}
	
	public void setRotateInstructionVisible(boolean d) {
		rotateInstructionText.setVisible(d);
	}
	
	public void setScaleInstructionVisible(boolean d) {
		scaleInstructionText.setVisible(d);
	}
	
	public abstract double getIterationsValue();

}
