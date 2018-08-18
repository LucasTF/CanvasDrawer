package shapes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Point {
	
	private Color color;
	private int diameter;
	private Point2D point2d;
	
	public Point(int x, int y, double diameter) {
		point2d = new Point2D(x, y);
		color = Color.BLACK;
		this.diameter = (int) diameter;
	}
	
	public Point2D getPoint() {
		return point2d;
	}
	
	public void setDiameter(int di) {
		diameter = di;
	}
	
	public int getDiameter() {
		return diameter;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void drawPoint(GraphicsContext gc) {
		gc.setFill(getColor());
		gc.fillOval((int)(point2d.getX()-(getDiameter()/2)), (int)(point2d.getY()-(getDiameter()/2)), getDiameter(), getDiameter());
	}

}
