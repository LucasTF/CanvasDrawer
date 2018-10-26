package app;

import abstractions.IDrawing;
import abstractions.IShape;
import gui.CanvasGUI;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import shapes.Point;
import shapes.Polygon;

public class Drawer {
	
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
	
	public void drawSnowflake(MouseEvent e, Color drawColor, Canvas c, double thickness) {
		drawBasicShape(e, drawColor, c, thickness, 0); //not work
	}
	
	public void drawPolygonalLine(MouseEvent e, Color drawColor, Canvas c, double thickness) {
		drawPolygonalLine(e, drawColor, c, thickness, 0);
	}
	
	public void drawClosedPolygon(MouseEvent e, Color drawColor, Canvas c, double thickness) {
		drawClosedPolygon(e, drawColor, c, thickness, 0);
	}
	
	public void drawRectangle(MouseEvent e, Color drawColor, Canvas c, double thickness) {
		drawRectangle(e, drawColor, c, thickness, 0);
	}
	
	private void drawBasicShape(MouseEvent e, Color drawColor, Canvas c, double thickness, double iterations){
		Point p = drawingWindow.getPoint(e);
		p.setColor(drawColor);
		p.drawPoint(c, drawColor, (int) thickness, (int) iterations);
		shape.setFirstPoint(p);
		drawingWindow.disableMenus(true);
		c.setOnMouseMoved(em ->{
			drawingWindow.softClear(c);
			Point l = new Point((int) em.getX(), (int)em.getY(), p.getDiameter());
			shape.setLastPoint(l);
			shape.draw(c, drawColor, thickness, iterations);
			c.setOnMouseClicked(ex -> {
				IDrawing d = (IDrawing) shape;
				c.setOnMouseMoved(null);
				drawingWindow.importToMainCanvas(shape);
				drawingWindow.addDrawingToList(d);
				c.setDisable(true);
				drawingWindow.disableMenus(false);
				});
			});
	}
	
	public void drawPoint(MouseEvent e, Color drawColor, Canvas c, double thickness){
		int x, y;
		
		x = (int)e.getX();
		y = (int)e.getY();
		Point p = new Point(x, y);
		p.drawPoint(c, drawColor, (int) thickness, 0);
		drawingWindow.addDrawingToList(p);
	}
	
	private void drawPolygonalLine(MouseEvent e, Color drawColor, Canvas c, double thickness, double iterations){
		Polygon pl = (Polygon) shape;
		Point p = drawingWindow.getPoint(e);
		p.setColor(drawColor);
		p.drawPoint(c, drawColor, (int) thickness, (int) iterations);
		pl.setFirstPoint(p);
		drawingWindow.disableMenus(true);
		c.setOnMouseMoved(em ->{
			drawingWindow.softClear(c);
			Point l = new Point((int) em.getX(), (int)em.getY(), p.getDiameter());
			pl.setLastPoint(l);
			pl.draw(c, drawColor, thickness, iterations);
			c.setOnMouseClicked(ex -> {
				if(ex.getButton() == MouseButton.PRIMARY) {
					drawingWindow.importToMainCanvas(pl);
					drawingWindow.softClear(c);
					pl.setupNextLine(pl.getLastPoint());
					pl.setDrawnLineToPointList();
				}
				else if(ex.getButton() == MouseButton.SECONDARY){
					c.setOnMouseMoved(null);
					drawingWindow.importToMainCanvas(pl);
					c.setDisable(true);
					drawingWindow.disableMenus(false);
					pl.setDrawnLineToPointList();
					drawingWindow.addDrawingToList(pl);
				}
			});
		});
	}
	
	private void drawClosedPolygon(MouseEvent e, Color drawColor, Canvas c, double thickness, double iterations){
		Polygon pl = (Polygon) shape;
		Point p = drawingWindow.getPoint(e);
		p.setColor(drawColor);
		p.drawPoint(c, drawColor, (int) thickness, (int) iterations);
		pl.setFirstPoint(p);
		drawingWindow.disableMenus(true);
		c.setOnMouseMoved(em ->{
			drawingWindow.softClear(c);
			Point l = new Point((int) em.getX(), (int)em.getY(), p.getDiameter());
			pl.setLastPoint(l);
			pl.draw(c, drawColor, thickness, iterations);
			c.setOnMouseClicked(ex -> {
				if(ex.getButton() == MouseButton.PRIMARY) {
					drawingWindow.importToMainCanvas(pl);
					drawingWindow.softClear(c);
					pl.setupNextLine(pl.getLastPoint());
					pl.setDrawnLineToPointList();
				}
				else if(ex.getButton() == MouseButton.SECONDARY){
					pl.setLastPoint(pl.getFirstPoint());
					pl.draw(c, drawColor, thickness, iterations);
					drawingWindow.softClear(c);
					c.setOnMouseMoved(null);
					drawingWindow.importToMainCanvas(pl);
					c.setDisable(true);
					drawingWindow.disableMenus(false);
					pl.setDrawnLineToPointList();
					drawingWindow.addDrawingToList(pl);
				}
			});
		});
	}
	
	private void drawRectangle(MouseEvent e, Color drawColor, Canvas c, double thickness, double iterations){
		Point p = drawingWindow.getPoint(e);
		p.setColor(drawColor);
		p.drawPoint(c, drawColor, (int) thickness, (int) iterations);
		shape.setFirstPoint(p);
		drawingWindow.disableMenus(true);
		c.setOnMouseMoved(em ->{
			drawingWindow.softClear(c);
			Point l = new Point((int) em.getX(), (int)em.getY(), p.getDiameter());
			shape.setLastPoint(l);
			shape.draw(c, drawColor, thickness, (int) iterations);
			c.setOnMouseClicked(ex -> {
				IDrawing d = (IDrawing) shape;
				c.setOnMouseMoved(null);
				drawingWindow.importToMainCanvas(shape);
				drawingWindow.addDrawingToList(d);
				c.setDisable(true);
				drawingWindow.disableMenus(false);
				});
			});
	}
	
	public IShape getShape() {
		return shape;
	}
	
	public void setShape(IShape shape) {
		this.shape = shape;
	}
	
	

}
