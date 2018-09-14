package gui;

import java.io.IOException;

import abstractions.IShape;
import abstractions.OptionsPane;
import gui.optionBars.BasicOptions;
import gui.optionBars.SnowflakeOptions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import shapes.Snowflake;
import shapes.Circle;
import shapes.Line;
import shapes.Point;

public class CanvasGUI {
	
	private static final String fxml = "fxml/CanvasGuiFXML.fxml";
	
	@FXML private Menu utilityMenu;
	@FXML private Menu shapeMenu;
	@FXML private Menu colorMenu;
	@FXML private MenuItem snowButton;
	@FXML private MenuItem lineButton;
	@FXML private MenuItem circButton;
	@FXML private MenuItem pointButton;
	@FXML private AnchorPane optionsBar;
	@FXML private Pane canvasPane;
	@FXML private Canvas canvas;
	
	public Color drawColor = Color.BLACK;
	private GraphicsContext gc;
	
	private Stage stage;
	private OptionsPane opPane;
	
	private int openPoints;
	private IShape shape;

	public CanvasGUI(Stage stage) throws IOException {
		this.stage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		loader.setController(this);
		Parent root = loader.load();
		this.stage.setScene(new Scene(root));
		canvasPane.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
		stage.setTitle("Canvas");
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
		
		gc = canvas.getGraphicsContext2D();
		pointButton.setOnAction(e -> setPointMode());
		setPointMode();
	}
	
	@FXML
	public void setSnowMode() {
		snowButton.setDisable(true);
		circButton.setDisable(false);
		lineButton.setDisable(false);
		pointButton.setDisable(false);
		shape = new Snowflake();
		canvas.setOnMouseClicked(e -> drawShape(e));
		setOptionsBar("Snowflake");
	}
	
	@FXML
	public void setLineMode() {
		lineButton.setDisable(true);
		snowButton.setDisable(false);
		circButton.setDisable(false);
		pointButton.setDisable(false);
		shape = new Line();
		canvas.setOnMouseClicked(e -> drawShape(e));
		setOptionsBar("Line");
	}
	
	@FXML
	public void setCircMode() {
		circButton.setDisable(true);
		snowButton.setDisable(false);
		lineButton.setDisable(false);
		pointButton.setDisable(false);
		shape = new Circle();
		canvas.setOnMouseClicked(e -> drawShape(e));
		setOptionsBar("Snow");
	}
	
	@FXML
	private void setPointMode() {
		pointButton.setDisable(true);
		snowButton.setDisable(false);
		circButton.setDisable(false);
		lineButton.setDisable(false);
		canvas.setOnMouseClicked(e -> drawPoint(e));
		setOptionsBar("Point");
	}
	
	@FXML
	public void clearCanvas(){
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		openPoints = 0;
	}
	
	@FXML
	public void setColorBlack() {
		drawColor = Color.BLACK;
	}
	
	@FXML
	public void setColorRed() {
		drawColor = Color.RED;
	}
	
	@FXML
	public void setColorBlue() {
		drawColor = Color.BLUE;
	}
	
	@FXML
	public void setColorGreen() {
		drawColor = Color.GREEN;
	}
	
	@FXML
	public void setColorYellow() {
		drawColor = Color.YELLOW;
	}
	
	@FXML
	public void setColorCyan() {
		drawColor = Color.CYAN;
	}
	
	@FXML
	public void setColorPink() {
		drawColor = Color.PINK;
	}
	
	private void drawPoint(MouseEvent e){
		int x, y;
		
		x = (int)e.getX();
		y = (int)e.getY();
		Point p = new Point(x, y, opPane.getThicknessValue());
		p.setColor(drawColor);
		p.drawPoint(gc);
	}
	
	private void drawShape(MouseEvent e){

		Point p = getPoint(e);

		p.setColor(drawColor);
		p.drawPoint(gc);
		setupPoint(p);
		if(openPoints > 1){
			shape.draw(gc, drawColor, opPane.getThicknessValue());
		}
		refreshPoint();
	}

	private Point getPoint(MouseEvent e){
		int x, y;

		x = (int)e.getX();
		y = (int)e.getY();

		return new Point(x, y, opPane.getThicknessValue());
	}
	
	private void setupPoint(Point p){
		if(openPoints == 0) {
			shape.setFirstPoint(p);
			colorMenu.setDisable(true);
			opPane.disableSlider(true);
			shapeMenu.setDisable(true);
		}
		else if(openPoints == 1) {
			shape.setLastPoint(p);
		}
		openPoints++;
	}
	
	private void refreshPoint(){
		if(openPoints == 2) {
			openPoints = 0;
			colorMenu.setDisable(false);
			opPane.disableSlider(false);;
			shapeMenu.setDisable(false);
		}
	}
	
	private void setOptionsBar(String shape) {
		if(shape.equals("Snowflake") == false) {
			try {
				opPane = new BasicOptions(optionsBar);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else {
			try {
				opPane = new SnowflakeOptions(optionsBar);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		optionsBar.getChildren().setAll(opPane.getOptionsPane());
	}
}
