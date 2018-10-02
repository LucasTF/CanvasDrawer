package gui;

import java.io.IOException;

import abstractions.IShape;
import abstractions.OptionsPane;
import app.Drawer;
import enums.Shapes;
import gui.optionBars.BasicOptions;
import gui.optionBars.SnowflakeOptions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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
import shapes.OpenPolygon;
import shapes.Point;

public class CanvasGUI {
	
	private static final String fxml = "fxml/CanvasGuiFXML.fxml";
	public final Color background = Color.AZURE;
	
	@FXML private Menu utilityMenu;
	@FXML private Menu colorMenu;
	
	@FXML private Menu shapeMenu;
	@FXML private MenuItem snowButton;
	@FXML private MenuItem lineButton;
	@FXML private MenuItem circButton;
	@FXML private MenuItem pointButton;
	@FXML private MenuItem openPolygonButton;
	@FXML private AnchorPane optionsBar;
	
	@FXML private Pane canvasPane;
	@FXML private Canvas mainCanvas;
	@FXML private Canvas drawingCanvas;
	
	public Color drawColor = Color.BLACK;
	
	private Stage stage;
	private OptionsPane opPane;
	private Shapes selectedShape;
	
	private Drawer drawer = new Drawer(this);

	public CanvasGUI(Stage stage) throws IOException {
		this.stage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		loader.setController(this);
		Parent root = loader.load();
		this.stage.setScene(new Scene(root));
		canvasPane.setBackground(new Background(new BackgroundFill(background, CornerRadii.EMPTY, Insets.EMPTY)));
		stage.setTitle("Canvas");
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
		pointButton.setOnAction(e -> setPointMode());
		setPointMode();
	}
	
	@FXML
	public void setSnowMode() {
		snowButton.setDisable(true);
		circButton.setDisable(false);
		lineButton.setDisable(false);
		pointButton.setDisable(false);
		drawer.setShape(new Snowflake());
		selectedShape = Shapes.SNOWFLAKE;
		mainCanvas.setOnMouseClicked(e -> setDrawingEnvironment(e));
		setOptionsBar(selectedShape);
	}
	
	@FXML
	public void setLineMode() {
		lineButton.setDisable(true);
		snowButton.setDisable(false);
		circButton.setDisable(false);
		pointButton.setDisable(false);
		drawer.setShape(new Line());
		selectedShape = Shapes.LINE;
		mainCanvas.setOnMouseClicked(e -> setDrawingEnvironment(e));
		setOptionsBar(selectedShape);
	}
	
	@FXML
	public void setCircMode() {
		circButton.setDisable(true);
		snowButton.setDisable(false);
		lineButton.setDisable(false);
		pointButton.setDisable(false);
		drawer.setShape(new Circle());
		selectedShape = Shapes.CIRCLE;
		mainCanvas.setOnMouseClicked(e -> setDrawingEnvironment(e));
		setOptionsBar(selectedShape);
	}
	
	@FXML
	public void setOpenPolygonMode() {
		circButton.setDisable(false);
		snowButton.setDisable(false);
		lineButton.setDisable(false);
		pointButton.setDisable(false);
		openPolygonButton.setDisable(true);
		drawer.setShape(new OpenPolygon());
		selectedShape = Shapes.OPENPOLYGON;
		mainCanvas.setOnMouseClicked(e -> setDrawingEnvironment(e));
		setOptionsBar(selectedShape);
	}
	
	@FXML
	private void setPointMode() {
		pointButton.setDisable(true);
		snowButton.setDisable(false);
		circButton.setDisable(false);
		lineButton.setDisable(false);
		selectedShape = Shapes.POINT;
		mainCanvas.setOnMouseClicked(e -> setDrawingEnvironment(e));
		setOptionsBar(selectedShape);
	}
	
	private void setDrawingEnvironment(MouseEvent e) {
		switch(selectedShape) {
		case POINT:
			drawer.drawPoint(e, drawColor, mainCanvas, opPane.getThicknessValue());
			break;
		case LINE:
		case CIRCLE:
		case SNOWFLAKE:
			drawingCanvas.setDisable(false);
			drawer.drawShape(e, drawColor, drawingCanvas, opPane.getThicknessValue(), opPane.getIterationsValue());
			break;
		case OPENPOLYGON:
			// May not work. Needs to be refined.
			drawingCanvas.setDisable(false);
			drawer.drawMoreThanTwoPoints(e, drawColor, drawingCanvas, opPane.getThicknessValue(), opPane.getIterationsValue());
			break;
		case RECTANGLE:
			// To be Implemented
			break;
		case CLOSEDPOLYGON:
			// To be Implemented
			break;
		case TRIANGLE:
			// To be Implemented
			break;
		default:
			break;
		
		}
	}
	
	public void importToMainCanvas(IShape drawedShape) {
		drawedShape.draw(mainCanvas, drawColor, opPane.getThicknessValue(), opPane.getIterationsValue());
		clearCanvas(drawingCanvas);
	}
	
	public void clearCanvas(Canvas c) {
		c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
		drawer.setOpenPoints(0);
	}
	
	@FXML
	private void clearMainCanvas(){
		clearCanvas(mainCanvas);
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

	public Point getPoint(MouseEvent e){
		int x, y;

		x = (int)e.getX();
		y = (int)e.getY();

		return new Point(x, y, opPane.getThicknessValue());
	}
	
	public Shapes getSelectedShape() {
		return selectedShape;
	}
	
	private void setOptionsBar(Shapes shape) {
		if(shape.equals(Shapes.SNOWFLAKE) == false) {
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
	
	public void disableMenus(boolean disable) {
		colorMenu.setDisable(disable);
		opPane.disableSlider(disable);
		shapeMenu.setDisable(disable);
	}
}
