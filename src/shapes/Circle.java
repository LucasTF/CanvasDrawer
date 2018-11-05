package shapes;

import java.util.ArrayList;

import abstractions.IDrawing;
import abstractions.IShape;
import enums.ShapeType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements IShape , IDrawing{

	private Point center;
	private Point circPoint;
	private int radius;
	private final double startingAngle = 0.1;
	private final double angleStep = 0.1;
	private final double finalAngle = 89.9;
	private final int[][] quarterValues = { {1, 1 }, {1, -1}, {-1, 1}, {-1, -1} };
	private final int[][] cornerValues = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
	
	private ArrayList<Point> pointList = new ArrayList<Point>();
	
	public Circle(){
		
	}

	@Override
	public void setFirstPoint(Point p) {
		center = p;

	}

	@Override
	public void setLastPoint(Point p) {
		circPoint = p;
	}

	@Override
	public void draw(Canvas cv, Color c, double diameter, double iterations) {
		pointList.clear();
		clearPointsInCanvas(cv.getGraphicsContext2D(), diameter);
		drawCircle(cv.getGraphicsContext2D(), c, diameter);
	}
	
	private void drawCircle(GraphicsContext gc, Color c, double diameter){
		int radius = (int) Math.sqrt(getXForRadius() + getYForRadius());
		this.setRadius(radius);
		drawCornerPoints(gc, c, diameter, radius);
		for(double angle = startingAngle; angle <= finalAngle; angle+= angleStep){
			double cos = Math.cos(Math.toRadians(angle));
			double x = cos * radius;
			double y = Math.sqrt(getValueSquare(radius) - getValueSquare(x));
			for(int quarter = quarterValues.length - 1; quarter >= 0; quarter--){
				drawCirclePoint(gc, c, diameter, x, y, quarterValues[quarter][0], quarterValues[quarter][1]);
			}
		}
	}

	private void drawCornerPoints(GraphicsContext gc, Color c, double diameter, int radius){
		for(int quarter = cornerValues.length - 1; quarter >= 0; quarter--){
			drawCirclePoint(gc, c, diameter, radius, radius, cornerValues[quarter][0], cornerValues[quarter][1]);
		}
	}

	private void drawCirclePoint(GraphicsContext gc, Color c, double diameter, double x, double y, int xSide, int ySide)
	{
		Point p;
		p = new Point(getXForDraw(x * xSide), getYForDraw(y * ySide), diameter);
		p.setColor(c);
		p.drawPoint(gc.getCanvas(), c, (int) diameter, 0);
		pointList.add(p);
	}

	private void clearPointsInCanvas(GraphicsContext gc, double diameter){
		gc.clearRect(center.getPoint().getX() - diameter/2, center.getPoint().getY() - diameter/2, diameter,
				diameter);

		gc.clearRect(circPoint.getPoint().getX() - diameter/2, circPoint.getPoint().getY() - diameter/2, diameter,
				diameter);
	}

	private double getXForRadius(){
		return getValueSquare(getPointsDifference(center.getPoint().getX(), circPoint.getPoint().getX()));
	}

	private double getYForRadius(){
		return getValueSquare(getPointsDifference(center.getPoint().getY(), circPoint.getPoint().getY()));
	}

	private double getValueSquare(double value){
		return value * value;
	}

	private double getPointsDifference(double valueA, double valueB){
		return valueA - valueB;
	}

	private int getXForDraw(double calculatedX){ return (int) (calculatedX + center.getPoint().getX());	}

	private int getYForDraw(double calculatedY){
		return (int) (calculatedY +  center.getPoint().getY());
	}

	@Override
	public Point getFirstPoint() {
		return center;
	}

	@Override
	public Point getLastPoint() {
		return circPoint;
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
		return ShapeType.CIRCLE.getShapeName();
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
		int avgX = 0;
		int avgY = 0;
		for(Point p : this.pointList)
		{
			avgX += p.getX();
			avgY += p.getY();
		}
		avgX /= this.pointList.size();
		avgY /= this.pointList.size();
		this.center = new Point(avgX, avgY);
		this.circPoint = this.pointList.get(0);
		this.setRadius((int) Math.sqrt(getXForRadius() + getYForRadius()));		
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public Color getColor() {
		return this.pointList.get(0).getColor();
	}
}
