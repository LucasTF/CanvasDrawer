package shapes;

import java.util.ArrayList;

import abstractions.IDrawing;
import abstractions.IShape;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class PolygonalLine implements IShape, IDrawing{
	
	private Point firstPoint;
	private Point lastPoint;
	private ArrayList<Line> lines;
	
	private ArrayList<Point> pointList = new ArrayList<Point>();
	private ArrayList<Point> bufferList = new ArrayList<Point>();
	
	public PolygonalLine(){
		lines = new ArrayList<Line>();
	}

	@Override
	public void setFirstPoint(Point p) {
		this.firstPoint = p;
		lines.add(new Line());
		lines.get(lines.size() - 1).setFirstPoint(p);;
	}
	
	public void setupNextLine(Point p)
	{
		lines.add(new Line());
		lines.get(lines.size() - 1).setFirstPoint(p);
	}
	
	@Override
	public void setLastPoint(Point p) {
		lastPoint = p;
		lines.get(lines.size() - 1).setLastPoint(p);
	}

	@Override
	public void draw(Canvas gv, Color c, double diameter, double iterations) {
		bufferList.clear();
		lines.get(lines.size() - 1).draw(gv,c,diameter,iterations);
		for(Point pl : lines.get(lines.size() - 1).getPointList()) {
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
	
	public ArrayList<Line> getLines()
	{
		return this.lines;
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
		return "Poligono";
	}
	
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
}
