package app;

import abstractions.IShape;
import gui.CanvasGUI;
import javafx.scene.canvas.Canvas;
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
	
	public void drawShape(MouseEvent e, Color drawColor, Canvas c, double thickness, double iterations){

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
	
	public void drawMoreThanTwoPoints(MouseEvent e, Color drawColor, Canvas c, double thickness, double iterations){
		
		Point p;
		if(openPoints == 0){
			p = drawingWindow.getPoint(e);
			p.setColor(drawColor);
			p.drawPoint(c.getGraphicsContext2D());
			shape.setFirstPoint(p);
			openPoints++;
		}
		else{
			p = drawingWindow.getPoint(e);
			p.setColor(drawColor);
			p.drawPoint(c.getGraphicsContext2D());
			shape.setLastPoint(p);
			shape.draw(c, drawColor, thickness, iterations);
			drawingWindow.disableMenus(false);
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
