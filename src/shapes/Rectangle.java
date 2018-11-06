package shapes;

import java.util.ArrayList;

import abstractions.IDrawing;
import abstractions.IShape;
import enums.ShapeType;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Rectangle implements IShape, IDrawing {

	private Line lines[];
	private Point firstPoint;
	private Point lastPoint;
	private Color color;
	private double diameter;
	
	private ArrayList<Point> pointList = new ArrayList<Point>();
	
	public Rectangle(){
		lines = new Line[4];
		lines[0] = new Line();
		lines[1] = new Line();
		lines[2] = new Line();
		lines[3] = new Line();
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
		return ShapeType.RECTANGLE.getShapeName();
	}

	@Override
	public void setFirstPoint(Point p) {
		firstPoint = p;
	}

	@Override
	public void setLastPoint(Point p) {
		lastPoint = p;
	}

	@Override
	public Point getFirstPoint() {
		return firstPoint;
	}

	@Override
	public Point getLastPoint() {
		return lastPoint;
	}

	@Override
	public void draw(Canvas cv, Color c, double diameter, double iterations) {
		pointList.clear();
		this.color = c;
		this.diameter = diameter;
		Point secondPoint = new Point(lastPoint.getX(), firstPoint.getY());
		Point thirdPoint = new Point(firstPoint.getX(), lastPoint.getY());
		
		lines[0].setFirstPoint(firstPoint);
		lines[0].setLastPoint(secondPoint);
		lines[0].draw(cv, c, diameter, iterations);
		lines[1].setFirstPoint(firstPoint);
		lines[1].setLastPoint(thirdPoint);
		lines[1].draw(cv, c, diameter, iterations);
		lines[2].setFirstPoint(secondPoint);
		lines[2].setLastPoint(lastPoint);
		lines[2].draw(cv, c, diameter, iterations);
		lines[3].setFirstPoint(thirdPoint);
		lines[3].setLastPoint(lastPoint);
		lines[3].draw(cv, c, diameter, iterations);
		for(Line l : lines)
		{
			for(Point p : l.getPointList())
			{
				pointList.add(p);
			}
		}
		
	}
	
	@Override
	public void redraw(Canvas cv)
	{
		this.draw(cv, this.color, this.diameter, 0);
	}
	
	@Override
	public ArrayList<Point> getPointsOfInterest()
	{
		ArrayList<Point> poi = new ArrayList<Point>();
		poi.add(this.firstPoint);
		poi.add(this.lastPoint);
		return poi;
	}
	
	@Override
	public void setPointsOfInterest(ArrayList<Point> poi)
	{
		this.firstPoint = poi.get(0);
		this.lastPoint = poi.get(1);;
	}
	
	@Override
	public Color getColor() {
		return this.pointList.get(0).getColor();
	}
	
	@Override
	public void setColor(Color c) {
		this.color = c;
	}
}
