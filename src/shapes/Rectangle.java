package shapes;

import java.util.ArrayList;

import abstractions.IDrawing;
import abstractions.IShape;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Rectangle implements IShape, IDrawing {

	private Line lines[];
	private Point firstPoint;
	private Point lastPoint;
	
	public Rectangle(){
		lines = new Line[4];
		lines[0] = new Line();
		lines[1] = new Line();
		lines[2] = new Line();
		lines[3] = new Line();
	}
	
	@Override
	public ArrayList<Point> getPointList() {
		ArrayList<Point> pointList = new ArrayList<Point>();
		
		for(Line line : lines){
			for(Point p : line.getPointList()){
				pointList.add(p);
			}
		}
		
		return pointList;
	}

	@Override
	public void erasePoints(Canvas cv, Color c, double thickness) {
		for(Line line : lines){
			line.draw(cv, c, thickness, 0);
		}
	}

	@Override
	public String getDrawingName() {
		return "Retangulo";
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
	}

}
