package shapes;

import java.util.ArrayList;

import abstractions.IDrawing;
import abstractions.IShape;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class PolygonalLine implements IShape, IDrawing{
	
	private Point firstPoint;
	private Point lastPoint;
	private Line lines;
	
	private ArrayList<Point> pointList = new ArrayList<Point>();
	private ArrayList<Point> bufferList = new ArrayList<Point>();
	
	public PolygonalLine(){
		lines = new Line();
	}

	@Override
	public void setFirstPoint(Point p) {
		firstPoint = p;
		lines.setFirstPoint(firstPoint);
	}

	@Override
	public void setLastPoint(Point p) {
		lastPoint = p;
		lines.setLastPoint(lastPoint);
	}

	@Override
	public void draw(Canvas gv, Color c, double diameter, double iterations) {
		bufferList.clear();
		lines.draw(gv,c,diameter,iterations);
		for(Point pl : lines.getPointList()) {
			bufferList.add(pl);
		}
	}
	
	public void setDrawnLineToPointList() {
		pointList.addAll(bufferList);
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
	public ArrayList<Point> getPointList() {
		return pointList;
	}

	@Override
	public void erasePoints(Canvas cv, Color c, double thickness) {
		for(Point p : pointList) {
			p.drawPoint(cv, c, (int) thickness, 0);
		}
		
	}
	
}
