package gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import shapes.Line;
import shapes.Point;

public class CanvasGUI {
	
	private static final String fxml = "fxml/CanvasGuiFXML.fxml";
	
	@FXML private MenuItem blackOption;
	@FXML private MenuItem redOption;
	@FXML private Button lineButton;
	@FXML private Button circButton;
	@FXML private Pane canvasPane;
	@FXML private Canvas canvas;
	
	public Color drawColor = Color.BLACK;
	public GraphicsContext gc;
	
	private Stage stage;
	
	private int openPoints;
	private Line line;

	public CanvasGUI(Stage stage) throws IOException {
		this.stage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		loader.setController(this);
		Parent root = loader.load();
		this.stage.setScene(new Scene(root));
		canvasPane.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
		stage.show();
		
		gc = canvas.getGraphicsContext2D();
		setPointMode();
	}
	
	@FXML
	public void setLineMode() {
		lineButton.setDisable(true);
		circButton.setDisable(false);
		line = new Line();
		canvas.setOnMouseClicked(e -> {
			int x, y;
				
			x = (int)e.getX();
			y = (int)e.getY();
			Point p = new Point(x, y);
			p.setColor(drawColor);
			p.drawPoint(gc);
			if(openPoints == 0) {
				line.setFirstPoint(p);
			}
			else if(openPoints == 1) {
				line.setLastPoint(p);
			}
			openPoints++;
			if(openPoints == 2) {
				line.drawLine(gc);
				openPoints = 0;
			}
			
		});
	}
	
	@FXML
	public void setCircMode() {
		circButton.setDisable(true);
		lineButton.setDisable(false);
	}
	
	public void setPointMode() {
		canvas.setOnMouseClicked(e -> {
			int x, y;
			
			x = (int)e.getX();
			y = (int)e.getY();
			Point p = new Point(x, y);
			p.setColor(drawColor);
			p.drawPoint(gc);
		});
	}
	
	@FXML
	public void setColorBlack() {
		drawColor = Color.BLACK;
	}
	
	@FXML
	public void setColorRed() {
		drawColor = Color.RED;
	}
	
}
