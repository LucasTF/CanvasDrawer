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
		for(Point p : this.pointList)
		{
			p.drawPoint(cv, p.getColor(), p.getDiameter(), 0);
		}
		recalculatePointsOfInterest();
	}

	public void recalculatePointsOfInterest()
	{
		this.firstPoint = this.pointList.get(0);
		this.lastPoint = this.pointList.get(this.pointList.size() - 1);
		
		int pointIndx = 0;
		for(Line l : this.lines)
		{
			for(Point lp : l.getPointList())
			{
				l.getPointList().set(l.getPointList().indexOf(lp), this.pointList.get(pointIndx));
				pointIndx++;
			}
			l.recalculatePointsOfInterest();
		}
	}
	
	@Override
	public Color getColor() {
		return this.pointList.get(0).getColor();
	}
}
