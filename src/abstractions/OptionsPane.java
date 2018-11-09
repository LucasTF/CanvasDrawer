package abstractions;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public abstract class OptionsPane {
	
	@FXML protected AnchorPane optionsPane;
	
	@FXML protected Pane rotationPane;
	@FXML protected TextField degreeField;
	
	@FXML protected Pane scalePane;
	@FXML protected TextField scaleField;
	
	@FXML protected Slider diameterSlider;
	
	@FXML protected Pane informationPane;
	@FXML protected Label selectedObjectLabel;
	@FXML protected Text instructionText;
	
	public OptionsPane(Parent parent, String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
		loader.setController(this);
		parent = loader.load();
		degreeField.setText("90");
		scaleField.setText("100");
	}
	
	public AnchorPane getOptionsPane() {
		return optionsPane;
	}
	
	public double getThicknessValue() {
		return diameterSlider.getValue();
	}
	
	public int getRotationAngle() {
		Alert wrongRotation = new Alert(AlertType.ERROR);
		wrongRotation.setTitle("Erro na rotação!");
		wrongRotation.setHeaderText(null);
		if(degreeField.getText().matches("^[0-9]+$")) {
			int rotation = Integer.parseInt(degreeField.getText());
			if(rotation >= 0 && rotation <= 360) {
				return rotation;
			}
			else {
				wrongRotation.setContentText("O ângulo de rotação deve ser entre 0 e 360°!");
				wrongRotation.show();
				return 0;
			}
		}
		else {
			wrongRotation.setContentText("O ângulo deve conter apenas números entre 0 e 360°.");
			wrongRotation.show();
			return 0;
		}
	}
	
	public double getScaleFactor() {
		Alert wrongScale = new Alert(AlertType.ERROR);
		wrongScale.setTitle("Erro no redimensionamento!");
		wrongScale.setHeaderText(null);
		if(scaleField.getText().matches("^[0-9]+$")) {
			double scale = Double.parseDouble(scaleField.getText());
			if(scale > 0) {
				return scale / 100;
			}
			else {
				wrongScale.setContentText("A escala deve ser um número positivo.");
				wrongScale.show();
				return 1;
			}
		}
		else {
			wrongScale.setContentText("A escala deve conter apenas números positivos.");
			wrongScale.show();
			return 1;
		}
	}
	
	public void disableSlider(boolean x) {
		diameterSlider.setDisable(x);
	}
	
	public void setSelectedObjectLabel(String s) {
		selectedObjectLabel.setText(s);
	}
	
	public void setInstructionText(String instruction) {
		instructionText.setText(instruction);
	}
	
	public void setRotationVisible(boolean d) {
		setSimpleDrawingBar();
		rotationPane.setVisible(d);
		informationPane.setVisible(d);
	}
	
	public void setScaleVisible(boolean d) {
		setSimpleDrawingBar();
		scalePane.setVisible(d);
		informationPane.setVisible(d);
	}
	
	public void setInformationVisible(boolean d) {
		informationPane.setVisible(d);
	}
	
	public abstract void setSimpleDrawingBar();
	public abstract double getIterationsValue();

}
