package shapes;

import java.util.ArrayList;

import abstractions.IDrawing;
import enums.ShapeType;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Point implements IDrawing{
	
	private Color color;
	private int diameter;
	private Point2D point2d;
	
	private int x;
	private int y;
	
	private ArrayList<Point> pointList = new ArrayList<Point>();
	
	public Point(int x, int y, double diameter) {
		this.x = x;
		this.y = y;
		
		point2d = new Point2D(x, y);
		color = Color.BLACK;
		this.diameter = (int) diameter;
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		
		point2d = new Point2D(x, y);
		color = Color.BLACK;
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
	
	public void drawPoint(Canvas cv, Color c, int diameter, int iterations) {
		this.diameter = diameter;
		this.color = c;
		cv.getGraphicsContext2D().setFill(getColor());
		cv.getGraphicsContext2D().fillOval((int)(point2d.getX()-(getDiameter()/2)), (int)(point2d.getY()-(getDiameter()/2)), getDiameter(), getDiameter());
		pointList.add(this);
	}
	
	public void drawTempPoint(Canvas cv, Color c, int diameter, int iterations) {
		cv.getGraphicsContext2D().setFill(c);
		cv.getGraphicsContext2D().fillOval((int)(point2d.getX()-(diameter/2)), (int)(point2d.getY()-(diameter/2)), diameter, diameter);
	}

	@Override
	public ArrayList<Point> getPointList() {
		return pointList;
	}

	@Override
	public void erasePoints(Canvas cv, Color c, double thickness) {
		for(Point p : this.pointList)
		{
			p.drawTempPoint(cv, c, (int)thickness, 0);
		}
	}

	@Override
	public String getDrawingName() {
		return ShapeType.POINT.getShapeName();
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	@Override
	public void redraw(Canvas cv)
	{
			this.drawPoint(cv, this.color, this.diameter, 0);
	}
	
	@Override
	public ArrayList<Point> getPointsOfInterest()
	{
		ArrayList<Point> poi = new ArrayList<Point>();
		poi.add(this);
		return poi;
	}
	
	@Override
	public void setPointsOfInterest(ArrayList<Point> poi)
	{
		this.x = poi.get(0).getX();
		this.y = poi.get(0).getY();
		this.point2d = new Point2D(this.x, this.y);
	}
}
