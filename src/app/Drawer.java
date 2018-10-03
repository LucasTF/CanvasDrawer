package app;

import abstractions.IShape;
import gui.CanvasGUI;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import shapes.Point;

public class Drawer {
	
	private int openPoints;
	private IShape shape;
	private CanvasGUI drawingWindow;
	
	public Drawer(CanvasGUI dw) {
		this.drawingWindow = dw;
	}
	
	public void drawLine(MouseEvent e, Color drawColor, Canvas c, double thickness) {
		drawBasicShape(e, drawColor, c, thickness, 0);
	}
	
	public void drawCircle(MouseEvent e, Color drawColor, Canvas c, double thickness) {
		drawBasicShape(e, drawColor, c, thickness, 0);
	}
	
	public void drawPolygonalLine(MouseEvent e, Color drawColor, Canvas c, double thickness) {
		drawPolygonalLine(e, drawColor, c, thickness, 0);
	}
	
	public void drawClosedPolygon(MouseEvent e, Color drawColor, Canvas c, double thickness) {
		// To be Implemented
	}
	
	public void drawRectangle(MouseEvent e, Color drawColor, Canvas c, double thickness, double iterations) {
		// To be Implemented
	}
	
	private void drawBasicShape(MouseEvent e, Color drawColor, Canvas c, double thickness, double iterations){
		openPoints = 0;
		Point p = drawingWindow.getPoint(e);
		p.setColor(drawColor);
		p.drawPoint(c.getGraphicsContext2D());
		if(openPoints == 0) {
			shape.setFirstPoint(p);
			drawingWindow.disableMenus(true);
			c.setOnMouseMoved(em ->{
				drawingWindow.clearCanvas(c);
				Point l = new Point((int) em.getX(), (int)em.getY(), p.getDiameter());
				if(openPoints == 1) {
					shape.draw(c, drawingWindow.background, thickness+2, iterations);
				}
				shape.setLastPoint(l);
				shape.draw(c, drawColor, thickness, iterations);
				c.setOnMouseClicked(ex -> {
					openPoints = 0;
					c.setOnMouseMoved(null);
					drawingWindow.importToMainCanvas(shape);
					c.setDisable(true);
					drawingWindow.disableMenus(false);
				});
				if(openPoints == 0) openPoints++;
			});
		}
	}
	
	public void drawPoint(MouseEvent e, Color drawColor, Canvas c, double thickness){
		int x, y;
		
		x = (int)e.getX();
		y = (int)e.getY();
		Point p = new Point(x, y, thickness);
		p.setColor(drawColor);
		p.drawPoint(c.getGraphicsContext2D());
	}
	
	private void drawPolygonalLine(MouseEvent e, Color drawColor, Canvas c, double thickness, double iterations){
		openPoints = 0;
		Point p = drawingWindow.getPoint(e);
		p.setColor(drawColor);
		p.drawPoint(c.getGraphicsContext2D());
		if(openPoints == 0) {
			shape.setFirstPoint(p);
			drawingWindow.disableMenus(true);
			c.setOnMouseMoved(em ->{
				drawingWindow.clearCanvas(c);
				Point l = new Point((int) em.getX(), (int)em.getY(), p.getDiameter());
				if(openPoints == 1) {
					shape.draw(c, drawingWindow.background, thickness+2, iterations);
				}
				shape.setLastPoint(l);
				shape.draw(c, drawColor, thickness, iterations);
				c.setOnMouseClicked(ex -> {
					if(ex.getButton() == MouseButton.PRIMARY) {
						drawingWindow.importToMainCanvas(shape);
						drawingWindow.clearCanvas(c);
						shape.setFirstPoint(shape.getLastPoint());
					}
					else if(ex.getButton() == MouseButton.SECONDARY){
						openPoints = 0;
						c.setOnMouseMoved(null);
						drawingWindow.importToMainCanvas(shape);
						c.setDisable(true);
						drawingWindow.disableMenus(false);
					}
				});
				if(openPoints == 0) openPoints++;
			});
		}
	}
	
	public IShape getShape() {
		return shape;
	}
	
	public void setShape(IShape shape) {
		this.shape = shape;
	}

	public int getOpenPoints() {
		return openPoints;
	}

	public void setOpenPoints(int openPoints) {
		this.openPoints = openPoints;
	}
	
	

}
