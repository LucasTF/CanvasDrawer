package app.utils;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import shapes.Point;
import shapes.Rectangle;

public class Clipper {
	
	private Stage stage;
	private AnchorPane mainPane;
	private ImageView view;
	private Rectangle selectionRectangle;
	private Canvas copyCanvas;
	
	public Clipper(Canvas clipCanvas) {
		copyCanvas = clipCanvas;
	}
	
	public void selectArea(Canvas drawingCanvas) {
		selectionRectangle = new Rectangle();
		drawingCanvas.setOnMouseClicked(e -> {
			if(selectionRectangle.getFirstPoint() == null) {
				selectionRectangle.setFirstPoint(new Point(Math.toIntExact(Math.round(e.getX())), Math.toIntExact(Math.round(e.getY()))));
			}
			drawingCanvas.setOnMouseMoved(mv -> {
				selectionRectangle.setLastPoint(new Point(Math.toIntExact(Math.round(mv.getX())), Math.toIntExact(Math.round(mv.getY()))));
				drawingCanvas.getGraphicsContext2D().clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
				selectionRectangle.draw(drawingCanvas, Color.BLACK, 1, 0);
			});
			if(selectionRectangle.getFirstPoint() != null && selectionRectangle.getLastPoint() != null) {
				drawingCanvas.getGraphicsContext2D().clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
				drawingCanvas.setDisable(true);
				drawingCanvas.setOnMouseClicked(null);
				drawingCanvas.setOnMouseMoved(null);
				openClippingWindow();
			}
		});
	}
	
	private void openClippingWindow() {
		int width, height;
		width = Math.abs(selectionRectangle.getLastPoint().getX() - selectionRectangle.getFirstPoint().getX());
		height = Math.abs(selectionRectangle.getLastPoint().getY() - selectionRectangle.getFirstPoint().getY());
		if(height > 0 && width > 0) {
			stage = new Stage();
			mainPane = new AnchorPane();
			stage.setScene(new Scene(mainPane, width, height));
			view = new ImageView();
			mainPane.getChildren().add(view);
			view.autosize();
			SnapshotParameters par = new SnapshotParameters();
			if(selectionRectangle.getLastPoint().getX() > selectionRectangle.getFirstPoint().getX()) {
				if(selectionRectangle.getLastPoint().getY() > selectionRectangle.getFirstPoint().getY()) {
					par.setViewport(new Rectangle2D(selectionRectangle.getFirstPoint().getX(), selectionRectangle.getFirstPoint().getY(), width, height));
				}
				else {
					par.setViewport(new Rectangle2D(selectionRectangle.getFirstPoint().getX(), selectionRectangle.getLastPoint().getY(), width, height));
				}
			}
			else {
				if(selectionRectangle.getLastPoint().getY() > selectionRectangle.getFirstPoint().getY()) {
					par.setViewport(new Rectangle2D(selectionRectangle.getLastPoint().getX(), selectionRectangle.getFirstPoint().getY(), width, height));
				}
				else {
					par.setViewport(new Rectangle2D(selectionRectangle.getLastPoint().getX(), selectionRectangle.getLastPoint().getY(), width, height));
				}
			}
			view.setImage(copyCanvas.snapshot(par, new WritableImage(width, height)));
			stage.setTitle("Clipping");
			stage.setResizable(false);
			stage.sizeToScene();
			stage.show();
		}
	}

}
