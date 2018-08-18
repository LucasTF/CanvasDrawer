package gui;

import java.io.IOException;

import abstractions.IShape;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import shapes.Circle;
import shapes.Line;
import shapes.Point;

public class CanvasGUI {
	
	private static final String fxml = "fxml/CanvasGuiFXML.fxml";
	
	@FXML private MenuButton colorMenu;
	@FXML private Slider diameterSlider;
	@FXML private Button lineButton;
	@FXML private Button circButton;
	@FXML private Pane canvasPane;
	@FXML private Canvas canvas;
	
	public Color drawColor = Color.BLACK;
	public GraphicsContext gc;
	
	private Stage stage;
	
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
		stage.show();
		
		gc = canvas.getGraphicsContext2D();
		setPointMode();
	}
	
	@FXML
	public void setLineMode() {
		lineButton.setDisable(true);
		circButton.setDisable(false);
		shape = new Line();
		canvas.setOnMouseClicked(e -> drawShape(e));
	}
	
	@FXML
	public void setCircMode() {
		circButton.setDisable(true);
		lineButton.setDisable(false);
		shape = new Circle();
		canvas.setOnMouseClicked(e -> drawShape(e));
	}
	
	private void setPointMode() {
		canvas.setOnMouseClicked(e -> drawPoint(e));
	}
	
	@FXML
	public void setColorBlack() {
		drawColor = Color.BLACK;
		colorMenu.setText("Preto");
	}
	
	@FXML
	public void setColorRed() {
		drawColor = Color.RED;
		colorMenu.setText("Vermelho");
	}
	
	@FXML
	public void setColorBlue() {
		drawColor = Color.BLUE;
		colorMenu.setText("Azul");
	}
	
	@FXML
	public void setColorGreen() {
		drawColor = Color.GREEN;
		colorMenu.setText("Verde");
	}
	
	@FXML
	public void setColorYellow() {
		drawColor = Color.YELLOW;
		colorMenu.setText("Amarelo");
	}
	
	@FXML
	public void setColorCyan() {
		drawColor = Color.CYAN;
		colorMenu.setText("Ciano");
	}
	
	@FXML
	public void setColorPink() {
		drawColor = Color.PINK;
		colorMenu.setText("Rosa");
	}
	
	private void drawPoint(MouseEvent e){
		int x, y;
		
		x = (int)e.getX();
		y = (int)e.getY();
		Point p = new Point(x, y, diameterSlider.getValue());
		p.setColor(drawColor);
		p.drawPoint(gc);
	}
	
	private void drawShape(MouseEvent e){

		Point p = getPoint(e);

		p.setColor(drawColor);
		p.drawPoint(gc);
		setupPoint(p);
		if(openPoints > 1)
			shape.draw(gc, drawColor, diameterSlider.getValue());
		refreshPoint();
	}

	private Point getPoint(MouseEvent e){
		int x, y;

		x = (int)e.getX();
		y = (int)e.getY();

		return new Point(x, y, diameterSlider.getValue());
	}
	
	private void setupPoint(Point p){
		if(openPoints == 0) {
			shape.setFirstPoint(p);
			colorMenu.setDisable(true);
			diameterSlider.setDisable(true);
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
			diameterSlider.setDisable(false);
		}
	}
}
