package lucastf.canvasdrawer.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lucastf.canvasdrawer.abstractions.IDrawing;
import lucastf.canvasdrawer.abstractions.IShape;
import lucastf.canvasdrawer.abstractions.OptionsPane;
import lucastf.canvasdrawer.enums.ShapeType;
import lucastf.canvasdrawer.services.*;
import lucastf.canvasdrawer.services.xml.XMLLoaderService;
import lucastf.canvasdrawer.services.xml.XMLSaveService;
import lucastf.canvasdrawer.shapes.*;

import java.io.IOException;
import java.util.ArrayList;

public class CanvasGUI {
	
	private static final String FXML_PATH = "/gui/canvasGui.fxml";
	private static final Color BACKGROUND = Color.WHITE;
	
	@FXML private Menu archiveMenu;
	@FXML private MenuItem saveButton;
	@FXML private MenuItem loadButton;
	
	@FXML private Menu utilityMenu;
	@FXML private MenuItem eraseButton;
	@FXML private MenuItem clippingButton;
	
	@FXML private Menu colorMenu;
	
	@FXML private Menu shapeMenu;
	@FXML private MenuItem snowButton;
	@FXML private MenuItem lineButton;
	@FXML private MenuItem circButton;
	@FXML private MenuItem pointButton;
	@FXML private MenuItem polygonalLineButton;
	@FXML private MenuItem closedPolygonButton;
	@FXML private MenuItem rectangleButton;
	@FXML private AnchorPane optionsBar;
	
	@FXML private Pane canvasPane;
	@FXML private Canvas mainCanvas;
	@FXML private Canvas drawingCanvas;
	
	private Color drawColor = Color.BLACK;
	
	private Stage stage;
	private OptionsPane opPane;
	private ShapeType selectedShape;
	private IDrawing selectedDrawing;
	
	private DrawerService drawer = new DrawerService(this);
	
	private ArrayList<IDrawing> drawnObjects;

	public CanvasGUI(Stage stage) throws IOException {
		this.stage = stage;
		this.drawnObjects = new ArrayList<IDrawing>();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
		loader.setController(this);
		Parent root = loader.load();
		this.stage.setScene(new Scene(root));
		canvasPane.setBackground(new Background(new BackgroundFill(BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));
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
		mainCanvas.setOnMouseClicked(this::setDrawingEnvironment);
		setOptionsBar(selectedShape);
	}
	
	@FXML
	private void setLineMode() {
		disableShapeOptions(false);
		lineButton.setDisable(true);
		selectedShape = ShapeType.LINE;
		mainCanvas.setOnMouseClicked(this::setDrawingEnvironment);
		setOptionsBar(selectedShape);
	}
	
	@FXML
	private void setCircMode() {
		disableShapeOptions(false);
		circButton.setDisable(true);
		selectedShape = ShapeType.CIRCLE;
		mainCanvas.setOnMouseClicked(this::setDrawingEnvironment);
		setOptionsBar(selectedShape);
	}
	
	@FXML
	private void setPolygonalLineMode() {
		disableShapeOptions(false);
		polygonalLineButton.setDisable(true);
		selectedShape = ShapeType.POLYGONALLINE;
		mainCanvas.setOnMouseClicked(this::setDrawingEnvironment);
		setOptionsBar(selectedShape);
	}
	
	@FXML
	private void setPointMode() {
		disableShapeOptions(false);
		pointButton.setDisable(true);
		selectedShape = ShapeType.POINT;
		mainCanvas.setOnMouseClicked(this::setDrawingEnvironment);
		setOptionsBar(selectedShape);
	}
	
	@FXML
	private void setClosedPolygonMode() {
		disableShapeOptions(false);
		closedPolygonButton.setDisable(true);
		selectedShape = ShapeType.POLYGON;
		mainCanvas.setOnMouseClicked(this::setDrawingEnvironment);
		setOptionsBar(selectedShape);
	}
	
	@FXML
	private void setEraseMode() {
		disableShapeOptions(false);
		opPane.setSelectedObjectInformationVisible(true);
		opPane.setDeleteInstructionVisible(true);
		opPane.setScaleInstructionVisible(false);
		opPane.setTranslateInstructionVisible(false);
		opPane.setRotateInstructionVisible(false);
		opPane.disableSlider(true);
		opPane.setSelectedObjectLabel("-");
		mainCanvas.setOnMouseClicked(this::setErasingEnvironment);
	}
	
	@FXML
	private void setTranslateMode() {
		disableShapeOptions(false);
		opPane.setSelectedObjectInformationVisible(true);
		opPane.setTranslateInstructionVisible(true);
		opPane.setScaleInstructionVisible(false);
		opPane.setDeleteInstructionVisible(false);
		opPane.setRotateInstructionVisible(false);
		opPane.disableSlider(true);
		opPane.setSelectedObjectLabel("-");
		mainCanvas.setOnMouseClicked(this::setTranslatingEnvironment);
	}

	
	@FXML
	private void setRotateMode() {
		disableShapeOptions(false);
		opPane.setSelectedObjectInformationVisible(true);
		opPane.setRotateInstructionVisible(true);
		opPane.setScaleInstructionVisible(false);
		opPane.setTranslateInstructionVisible(false);
		opPane.setDeleteInstructionVisible(false);
		opPane.disableSlider(true);
		opPane.setSelectedObjectLabel("-");
		mainCanvas.setOnMouseClicked(this::setRotatingEnvironment);
	}
	
	@FXML
	private void setScaleMode() {
		disableShapeOptions(false);
		opPane.setSelectedObjectInformationVisible(true);
		opPane.setScaleInstructionVisible(true);
		opPane.setRotateInstructionVisible(false);
		opPane.setTranslateInstructionVisible(false);
		opPane.setDeleteInstructionVisible(false);
		opPane.disableSlider(true);
		opPane.setSelectedObjectLabel("-");
		mainCanvas.setOnMouseClicked(this::setScalingEnvironment);
	}
	
	@FXML
	private void setClippingMode() {
		disableShapeOptions(false);
		opPane.setSelectedObjectInformationVisible(false);
		opPane.setScaleInstructionVisible(false);
		opPane.setTranslateInstructionVisible(false);
		opPane.setDeleteInstructionVisible(false);
		opPane.setRotateInstructionVisible(false);
		drawingCanvas.setDisable(false);
		ClipperService clipper = new ClipperService(mainCanvas);
		clipper.selectArea(drawingCanvas);
	}
	
	@FXML
	private void setRectangleMode(){
		disableShapeOptions(false);
		rectangleButton.setDisable(true);
		selectedShape = ShapeType.RECTANGLE;
		mainCanvas.setOnMouseClicked(this::setDrawingEnvironment);
		setOptionsBar(selectedShape);
	}
	
	@FXML
	public void saveDrawing() {
		System.out.println(drawnObjects.size());
		XMLSaveService xmlS = new XMLSaveService(mainCanvas.getHeight(), mainCanvas.getWidth());
		xmlS.exportXML(drawnObjects);
	}
	
	@FXML
	public void loadDrawing() {
		XMLLoaderService xmlL = new XMLLoaderService(mainCanvas.getHeight(), mainCanvas.getWidth());
		ArrayList<IDrawing> loadedObjects = xmlL.importXML();
		if(loadedObjects.size() > 0) {
			clearCanvas(mainCanvas);
			drawnObjects = loadedObjects;
			for(IDrawing d : drawnObjects) {
				d.redraw(mainCanvas, opPane.getThicknessValue());
			}
		}
	}
	
	private void setScalingEnvironment(MouseEvent e) {
		selectedDrawing = null;
		ScalerService scaler = new ScalerService();
		selectedDrawing = scaler.findClickedDrawing(e, drawnObjects);
		if(selectedDrawing != null) {
			opPane.setSelectedObjectLabel(selectedDrawing.getDrawingName());
		}
		else {
			opPane.setSelectedObjectLabel("-");
		}
		mainCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
					if(selectedDrawing != null) {
						scaler.scaleDrawing(mainCanvas, BACKGROUND, selectedDrawing, drawnObjects, event.getX(), event.getY(), 1.2 /*code GUI factor input*/);
					}
					opPane.setSelectedObjectLabel("-");
					mainCanvas.setOnMouseClicked(nextE -> setScalingEnvironment(nextE));
			}
		});
	}
	
	private void setRotatingEnvironment(MouseEvent e) {
		selectedDrawing = null;
		RotatorService rotator = new RotatorService();
		selectedDrawing = rotator.findClickedDrawing(e, drawnObjects);
		if(selectedDrawing != null) {
			opPane.setSelectedObjectLabel(selectedDrawing.getDrawingName());
		}
		else {
			opPane.setSelectedObjectLabel("-");
		}
		mainCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
					if(selectedDrawing != null) {
						rotator.rotateDrawing(mainCanvas, BACKGROUND, selectedDrawing, drawnObjects, event.getX(), event.getY(), 90 /*code GUI angle input*/);
					}
					opPane.setSelectedObjectLabel("-");
					mainCanvas.setOnMouseClicked(nextE -> setRotatingEnvironment(nextE));
			}
		});
	}
	
	private void setTranslatingEnvironment(MouseEvent e) {
		selectedDrawing = null;
		TranslatorService translator = new TranslatorService();
		selectedDrawing = translator.findClickedDrawing(e, drawnObjects);
		if(selectedDrawing != null) {
			opPane.setSelectedObjectLabel(selectedDrawing.getDrawingName());
		}
		else {
			opPane.setSelectedObjectLabel("-");
		}
		mainCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
					if(selectedDrawing != null) {
						translator.translateDrawing(mainCanvas, BACKGROUND, selectedDrawing, drawnObjects, event.getX(), event.getY());
					}
					opPane.setSelectedObjectLabel("-");
					mainCanvas.setOnMouseClicked(nextE -> setTranslatingEnvironment(nextE));
			}
		});
	}
	
	private void setErasingEnvironment(MouseEvent e) {
		selectedDrawing = null;
		EraserService eraser = new EraserService();
		selectedDrawing = eraser.findClickedDrawing(e, drawnObjects);
		if(selectedDrawing != null) opPane.setSelectedObjectLabel(selectedDrawing.getDrawingName());
		else opPane.setSelectedObjectLabel("-");
		stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.DELETE) {
					if(selectedDrawing != null) {
						eraser.eraseDrawing(mainCanvas, BACKGROUND, selectedDrawing.getPointList().get(0).getDiameter(), selectedDrawing, drawnObjects);
						opPane.setSelectedObjectLabel("-");
					}
				}
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
			drawer.setShape(new Polygon());
			drawer.drawPolygonalLine(e, drawColor, drawingCanvas, opPane.getThicknessValue());
			break;
		case RECTANGLE:
			drawingCanvas.setDisable(false);
			drawer.setShape(new Rectangle());
			drawer.drawRectangle(e, drawColor, drawingCanvas, opPane.getThicknessValue());
			break;
		case POLYGON:
			drawingCanvas.setDisable(false);
			drawer.setShape(new Polygon());
			drawer.drawClosedPolygon(e, drawColor, drawingCanvas, opPane.getThicknessValue());
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
		opPane.setSelectedObjectInformationVisible(false);
		opPane.setDeleteInstructionVisible(false);
		opPane.setTranslateInstructionVisible(false);
		opPane.setRotateInstructionVisible(false);
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
	}
	
	private void disableShapeOptions(boolean d) {
		for(MenuItem i : shapeMenu.getItems()) {
			i.setDisable(d);
		}
	}
	
	private void setOptionsBar(ShapeType shape) {
		if(!shape.equals(ShapeType.SNOWFLAKE)) {
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
