package app;

import java.util.ArrayList;

import abstractions.IDrawing;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import shapes.Point;

public class Translator extends Selector
{
	public void translateDrawing(Canvas cv, Color c, IDrawing drawing, ArrayList<IDrawing> drawnObjects, int pixels, KeyCode key)
	{
		Eraser eraser = new Eraser();
		eraser.eraseDrawing(cv, c, drawing.getPointList().get(0).getDiameter(), drawing, drawnObjects);
		
		int deltaX = 0;
		int deltaY = 0;
		if(key == KeyCode.RIGHT)
		{
			deltaX += pixels;
		}
		if(key == KeyCode.LEFT)
		{
			deltaX -= pixels;
		}
		if(key == KeyCode.UP)
		{
			deltaY -= pixels;
		}
		if(key == KeyCode.DOWN)
		{
			deltaY += pixels;
		}
		
		ArrayList<Point> newPoints = new ArrayList<Point>();
		for(Point p : drawing.getPointList())
		{
			Point newP = new Point(p.getX() + deltaX, p.getY() + deltaY, drawing.getPointList().get(0).getDiameter());
			newP.setColor(drawing.getPointList().get(0).getColor());
			newPoints.add(newP);
		}
		drawing.getPointList().clear();
		drawing.getPointList().addAll(newPoints);
		drawing.redraw(cv);
		drawnObjects.add(drawing);
	}
}
