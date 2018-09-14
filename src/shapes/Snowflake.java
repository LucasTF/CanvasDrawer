package shapes;

import abstractions.IShape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Snowflake implements IShape
{
	private Point base;
	private Point tip1;
	private Point tip2;
	private Point tip3;
	private Point center;
	private ArrayList<Line> sides;
	private GraphicsContext gc;
	private double diameter;
	private Color color;
	private final int iterations = 4;
	
	public Snowflake()
	{
		
	}
	
	@Override
	public void setFirstPoint(Point p)
	{
		this.base = p;
		this.sides = new ArrayList<Line>();
	}
	
	@Override
	public void setLastPoint(Point p)
	{
		this.tip1 = p;
		
		double baseX = this.base.getPoint().getX();
		double baseY = this.base.getPoint().getY();
		double tip1X = p.getPoint().getX();
		double tip1Y = p.getPoint().getY();
		
		int tip2X = Math.toIntExact(Math.round(baseX + (tip1Y - baseY) / 2));
		int tip2Y = Math.toIntExact(Math.round(baseY - (tip1X - baseX) / 2));
		tip2 = new Point(tip2X, tip2Y, tip1.getDiameter());
		
		int tip3X = Math.toIntExact(Math.round(baseX - (tip1Y - baseY) / 2));
		int tip3Y = Math.toIntExact(Math.round(baseY + (tip1X - baseX) / 2));
		tip3 = new Point(tip3X, tip3Y, tip1.getDiameter());
		
		this.center = new Point(Math.toIntExact(Math.round((baseX + tip1X) / 2)), Math.toIntExact(Math.round((baseY + tip1Y) / 2)), tip1.getDiameter());
	}
	
	@Override
	public void draw(GraphicsContext gc, Color c, double diameter)
	{
		this.gc = gc;
		this.diameter = diameter;
		this.color = c;
		clearPointsInCanvas(gc, diameter);
		
		Line sideA = new Line(tip1, tip2);
		Line sideB = new Line(tip2, tip3);
		Line sideC = new Line(tip3, tip1);
		this.sides.add(sideA);
		this.sides.add(sideB);
		this.sides.add(sideC);
		calcSides(sideA, 2, this.center, this.center);
		calcSides(sideB, 2, this.center, this.center);
		calcSides(sideC, 2, this.center, this.center);
		
		for(Line side : this.sides)
		{
			side.draw(this.gc, this.color, this.diameter);
		}
	}
	
	private void calcSides(Line side, int iteration, Point lastMid, Point lastLastMid)
	{
		if(iteration <= this.iterations)
		{
			this.sides.remove(side); //remove this side from the list as it's replaced with the four new parts 

			Line firstThird = new Line(side.getFirstPoint(), side.getLinePoint(1.0 / 3, this.diameter));
			Line lastThird = new Line(side.getLinePoint(2.0 / 3, this.diameter), side.getLastPoint());
			
			//replace middle third two sides of a triangle
			Point nextStartL = side.getLinePoint(1.0 / 3, this.diameter);
			Point nextStartR = side.getLinePoint(2.0 / 3, this.diameter);
			Point midPoint = side.getLinePoint(1.0 / 2, this.diameter);
			double midPointX = midPoint.getPoint().getX();
			double midPointY = midPoint.getPoint().getY();
			int nextEndX = Math.toIntExact(Math.round(midPointX - (midPointY - side.getLinePoint(1.0 / 3, this.diameter).getPoint().getY()) * 2));
			int nextEndY = Math.toIntExact(Math.round(midPointY + (midPointX - side.getLinePoint(1.0 / 3, this.diameter).getPoint().getX()) * 2));
			int nextEndXRvrs = Math.toIntExact(Math.round(midPointX + (midPointY - side.getLinePoint(1.0 / 3, this.diameter).getPoint().getY()) * 2));
			int nextEndYRvrs = Math.toIntExact(Math.round(midPointY - (midPointX - side.getLinePoint(1.0 / 3, this.diameter).getPoint().getX()) * 2));
			Point nextEnd;
			if(Math.sqrt(Math.pow(nextEndX - lastMid.getPoint().getX(), 2) + Math.pow(nextEndY - lastMid.getPoint().getY(), 2)) > Math.sqrt(Math.pow(nextEndXRvrs - lastMid.getPoint().getX(), 2) + Math.pow(nextEndYRvrs - lastMid.getPoint().getY(), 2)))
			{
				nextEnd = new Point(nextEndX, nextEndY, this.diameter); 
			}
			else
			{
				nextEnd = new Point(nextEndXRvrs, nextEndYRvrs, this.diameter);
			}
			Line newMidL = new Line(nextStartL, nextEnd);
			Line newMidR = new Line(nextStartR, nextEnd);
			
			//add new parts to sides list
			this.sides.add(firstThird);
			this.sides.add(lastThird);
			this.sides.add(newMidL);
			this.sides.add(newMidR);
			
			calcSides(firstThird, iteration + 1, lastMid, lastMid);
			calcSides(lastThird, iteration + 1, lastMid, lastMid);
			calcSides(newMidL, iteration + 1, midPoint, lastMid);
			calcSides(newMidR, iteration + 1, midPoint, lastMid);
		}
	}
	
	private void clearPointsInCanvas(GraphicsContext gc, double diameter){ //not copy-pasted from the circle
		gc.clearRect(tip1.getPoint().getX() - diameter/2, tip1.getPoint().getY() - diameter/2, diameter,
				diameter);

		gc.clearRect(tip2.getPoint().getX() - diameter/2, tip2.getPoint().getY() - diameter/2, diameter,
				diameter);
		
		gc.clearRect(tip3.getPoint().getX() - diameter/2, tip3.getPoint().getY() - diameter/2, diameter,
				diameter);
		
		gc.clearRect(base.getPoint().getX() - diameter/2, base.getPoint().getY() - diameter/2, diameter,
				diameter);
	}
}