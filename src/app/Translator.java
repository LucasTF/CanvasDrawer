package app;

import java.util.ArrayList;

import abstractions.IDrawing;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import shapes.Point;

public class Translator extends Selector
{
	public void translateDrawing(Canvas cv, Color c, IDrawing target, ArrayList<IDrawing> drawnObjects, MouseEvent event)
	{
		Eraser eraser = new Eraser();
		eraser.eraseDrawing(cv, c, target.getPointList().get(0).getDiameter(), target, drawnObjects);
		
		int deltaX = 0;
		int deltaY = 0;
		Point center = getDrawingCenter(target);
		deltaX = (int) event.getX() - center.getX();
		deltaY = (int) event.getY() - center.getY();
				
		ArrayList<Point> newPoints = new ArrayList<Point>();
		for(Point p : target.getPointList())
		{
			Point newP = new Point(p.getX() + deltaX, p.getY() + deltaY, target.getPointList().get(0).getDiameter());
			newP.setColor(target.getPointList().get(0).getColor());
			newPoints.add(newP);
		}
		target.getPointList().clear();
		target.getPointList().addAll(newPoints);
		target.redraw(cv);
		drawnObjects.add(target);
	}
	
	public Point getDrawingCenter(IDrawing target)
	{
		int avgX = 0;
		int avgY = 0;
		for(Point p : target.getPointList())
		{
			avgX += p.getX();
			avgY += p.getY();
		}
		avgX /= target.getPointList().size();
		avgY /= target.getPointList().size();
		return new Point(avgX, avgY);
	}
}
