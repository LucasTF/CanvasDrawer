package lucastf.canvasdrawer.shapes;

import java.util.ArrayList;

import lucastf.canvasdrawer.abstractions.IDrawing;
import lucastf.canvasdrawer.abstractions.IShape;
import lucastf.canvasdrawer.enums.ShapeType;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Polygon implements IShape, IDrawing{
	
	private Point firstPoint;
	private Point lastPoint;
	private ArrayList<Line> lines;
	private Color color;
	private double diameter;;
	
	private ArrayList<Point> pointList = new ArrayList<Point>();
	private ArrayList<Point> bufferList = new ArrayList<Point>();
	
	public Polygon(){
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
		lines.get(lines.size() - 1).setLastPoint(p);
	}
	
	@Override
	public void setLastPoint(Point p) {
		lastPoint = p;
		lines.get(lines.size() - 1).setLastPoint(p);
	}

	@Override
	public void draw(Canvas gv, Color c, double diameter, double iterations) {
		bufferList.clear();
		this.color = c;
		this.diameter = diameter;
		lines.get(lines.size() - 1).draw(gv,c,diameter,iterations);
		for(Point pl : lines.get(lines.size() - 1).getPointList()) {
			bufferList.add(pl);
		}
	}
	
	public void setLineForPolygon(Line l){
		lines.add(l);
	}
	
	public void forceDrawPolygon(Canvas cv, Color c, double diameter){
		this.pointList.clear();
		this.color = c;
		this.diameter = diameter;
		for(Line l: lines){
			l.draw(cv, c, diameter, 0);
			for(Point p : l.getPointList()) {
				this.pointList.add(p);
			}
		}
		this.firstPoint = lines.get(0).getFirstPoint();
		this.lastPoint = lines.get(lines.size() - 1).getLastPoint();
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
		return ShapeType.POLYGON.getShapeName();
	}
	
	@Override
	public void redraw(Canvas cv)
	{
		this.forceDrawPolygon(cv, this.color, this.diameter);
	}
	 @Override
	public void redraw(Canvas cv, double diameter)
	{
		this.forceDrawPolygon(cv, this.color, diameter);
	}
	
	@Override
	public ArrayList<Point> getPointsOfInterest()
	{
		ArrayList<Point> poi = new ArrayList<Point>();
		for(Line l : this.lines)
		{
			poi.add(l.getFirstPoint());
		}
		poi.add(this.lastPoint);
		return poi;
	}
	
	@Override
	public void setPointsOfInterest(ArrayList<Point> poi)
	{
		this.lines.clear();
		for(int p = 0; p < poi.size() - 1; p++)
		{
			this.lines.add(new Line(poi.get(p), poi.get(p + 1)));
		}
		this.firstPoint = poi.get(0);
		this.lastPoint = poi.get(poi.size() - 1);;
	}
	
	@Override
	public Color getColor() {
		return this.color;
	}

	public void setColor(Color colorC) {
		this.color = colorC;
	}
}
