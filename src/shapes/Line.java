package shapes;

import abstractions.IShape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line implements IShape {
	
	private Point firstPoint;
	private Point lastPoint;
	
	public Line(Point a, Point b) {
		firstPoint = a;
		lastPoint = b;
	}
	
	public Line() {
		
	}
	
	public void setFirstPoint(Point p) {
		firstPoint = p;
	}
	
	public Point getFirstPoint() {
		return this.firstPoint;
	}
	
	public void setLastPoint(Point p) {
		lastPoint = p;
	}
	
	public Point getLastPoint() {
		return this.lastPoint;
	}
		
	public void draw(GraphicsContext gc, Color c, double diameter) {
		Point p;
		double deltaX, deltaY;
		int steps;
		deltaX = lastPoint.getPoint().getX() - firstPoint.getPoint().getX();
		deltaY = lastPoint.getPoint().getY() - firstPoint.getPoint().getY();
		if(Math.abs(deltaX) > Math.abs(deltaY)) {
			steps = (int) Math.abs(Math.round(deltaX));
		}
		else {
			steps = (int) Math.abs(Math.round(deltaY));
		}
		double xIncrement, yIncrement;
		xIncrement = deltaX / (double) steps;
		yIncrement = deltaY / (double) steps;
		double x = firstPoint.getPoint().getX();
		double y = firstPoint.getPoint().getY();
		for(int i = 0; i < steps; i++) {
			x = x + xIncrement;
			y = y + yIncrement;
			p = new Point((int) x, (int) y, diameter);
			p.setColor(c);
			p.drawPoint(gc);
		}
	}
	
	public Point getLinePoint(double dec, double diameter) //gets a point along the line proportional to the decimal
	{
		double deltaX = lastPoint.getPoint().getX() - firstPoint.getPoint().getX();
		double deltaY = lastPoint.getPoint().getY() - firstPoint.getPoint().getY();
		int pointX = Math.toIntExact(Math.round(deltaX * dec + this.firstPoint.getPoint().getX()));
		int pointY = Math.toIntExact(Math.round(deltaY * dec + this.firstPoint.getPoint().getY()));
		return new Point(pointX, pointY, diameter);
	}
}
