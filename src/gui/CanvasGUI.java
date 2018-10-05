package gui;

import java.io.IOException;
import java.util.ArrayList;

import abstractions.IDrawing;
import abstractions.IShape;
import abstractions.OptionsPane;
import app.Drawer;
import enums.ShapeType;
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
import shapes.PolygonalLine;
import shapes.Point;

public class CanvasGUI {
	
	private static final String fxml = "fxml/canvasGuiFXML.fxml";
	public final Color background = Color.AZURE;
	
	@FXML private Menu utilityMenu;
	@FXML private MenuItem eraseButton;
	
	@FXML private Menu colorMenu;
	
	@FXML private Menu shapeMenu;
	@FXML private MenuItem snowButton;
	@FXML private MenuItem lineButton;
	@FXML private MenuItem circButton;
	@FXML private MenuItem pointButton;
	@FXML private MenuItem polygonalLineButton;
	@FXML private AnchorPane optionsBar;
	
	@FXML private Pane canvasPane;
	@FXML private Canvas mainCanvas;
	@FXML private Canvas drawingCanvas;
	
	public Color drawColor = Color.BLACK;
	
	private Stage stage;
	private OptionsPane opPane;
	private ShapeType selectedShape;
	
	private Drawer drawer = new Drawer(this);
	
	private ArrayList<IDrawing> drawnObjects;

	public CanvasGUI(Stage stage) throws IOException {
		this.stage = stage;
		this.drawnObjects = new ArrayList<IDrawing>();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		loader.setController(this);
		Parent root = loader.load();
		this.stage.setScene(new Scene(root));
		canvasPane.setBackground(new Background(new BackgroundFill(background, CornerRadii.EMPTY, Insets.EMPTY)));
		stage.setTitle("Canvas Drawer");
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
		mainCanvas.setOnMouseMoved(e -> stage.setTitle("Canvas Drawer - (" + (int) e.getX() + " , " + (int) e.getY() + ")"));
		pointButton.setOnAction(e -> setPointMode());
		setPointMode();
	}
	
	@FXML
	private void setSnowMode() {
		disableShapeOptions(false);
		snowButton.setDisable(true);
		selectedShape = ShapeType.SNOWFLAKE;
		mainCanvas.setOnMouseClicked(e -> setDrawingEnvironment(e));
		setOptionsBar(selectedShape);
	}
	
	@FXML
	private void setLineMode() {
		disableShapeOptions(false);
		lineButton.setDisable(true);
		selectedShape = ShapeType.LINE;
		mainCanvas.setOnMouseClicked(e -> setDrawingEnvironment(e));
		setOptionsBar(selectedShape);
	}
	
	@FXML
	private void setCircMode() {
		disableShapeOptions(false);
		circButton.setDisable(true);
		selectedShape = ShapeType.CIRCLE;
		mainCanvas.setOnMouseClicked(e -> setDrawingEnvironment(e));
		setOptionsBar(selectedShape);
	}
	
	@FXML
	private void setPolygonalLineMode() {
		disableShapeOptions(false);
		polygonalLineButton.setDisable(true);
		selectedShape = ShapeType.POLYGONALLINE;
		mainCanvas.setOnMouseClicked(e -> setDrawingEnvironment(e));
		setOptionsBar(selectedShape);
	}
	
	@FXML
	private void setPointMode() {
		disableShapeOptions(false);
		pointButton.setDisable(true);
		selectedShape = ShapeType.POINT;
		mainCanvas.setOnMouseClicked(e -> setDrawingEnvironment(e));
		setOptionsBar(selectedShape);
	}
	
	@FXML
	private void setEraseMode() {
		disableShapeOptions(false);
		mainCanvas.setOnMouseClicked(e -> {
			IDrawing rightObject = null;
			int objectThickness = 3;
			int x = (int) e.getX();
			int y = (int) e.getY();
			for(IDrawing d : drawnObjects) {
				for(Point p : d.getPointList()) {
					if(((int) p.getPoint().getX() <= x+p.getDiameter() && (int) p.getPoint().getX() >= x-p.getDiameter()) && ((int) p.getPoint().getY() >= y-p.getDiameter()) && (int) p.getPoint().getY() <= y+p.getDiameter()) {
						rightObject = d;
						objectThickness = p.getDiameter();
						break;
					}
				}
				if(rightObject != null) break;
			}
			if(rightObject != null) {
				rightObject.erasePoints(mainCanvas, background, objectThickness+2);
				drawnObjects.remove(rightObject);
				rightObject = null;
				objectThickness = 3;
			}
		});
	}
	
	private void setDrawingEnvironment(MouseEvent e) {
		switch(selectedShape) {
		case POINT:
			drawer.drawPoint(e, drawColor, mainCanvas, opPane.getThicknessValue());
			break;
		case LINE:
			drawingCanvas.setDisable(false);
			drawer.setShape(new Line());
			drawer.drawLine(e, drawColor, drawingCanvas, opPane.getThicknessValue());
			break;
		case CIRCLE:
			drawingCanvas.setDisable(false);
			drawer.setShape(new Circle());
			drawer.drawCircle(e, drawColor, drawingCanvas, opPane.getThicknessValue());
			break;
		case SNOWFLAKE:
			drawingCanvas.setDisable(false);
			drawer.setShape(new Snowflake());
			drawer.drawSnowflake(e, drawColor, drawingCanvas, opPane.getThicknessValue());
			break;
		case POLYGONALLINE:
			drawingCanvas.setDisable(false);
			drawer.setShape(new PolygonalLine());
			drawer.drawPolygonalLine(e, drawColor, drawingCanvas, opPane.getThicknessValue());
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
		softClear(drawingCanvas);
	}
	
	public void clearCanvas(Canvas c) {
		c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
		drawnObjects.clear();
	}
	
	public void softClear(Canvas c) {
		c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
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
	
	public ShapeType getSelectedShape() {
		return selectedShape;
	}
	
	public void addDrawingToList(IDrawing drawing) {
		this.drawnObjects.add(drawing);
		System.out.println("Numero de Desenhos na lista: " + drawnObjects.size());
	}
	
	private void disableShapeOptions(boolean d) {
		for(MenuItem i : shapeMenu.getItems()) {
			i.setDisable(d);
		}
	}
	
	private void setOptionsBar(ShapeType shape) {
		if(shape.equals(ShapeType.SNOWFLAKE) == false) {
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
