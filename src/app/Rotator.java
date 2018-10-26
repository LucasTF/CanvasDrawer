package app;

import java.util.ArrayList;

import abstractions.IDrawing;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import shapes.Point;

public class Rotator extends Selector
{
	public void rotateDrawing(Canvas cv, Color c, IDrawing target, ArrayList<IDrawing> drawnObjects, double mouseX, double mouseY, int angle)
	{
		//translate to origin
		Point center = getDrawingCenter(target);
		double translateX = center.getX() - mouseX;
		double translateY = center.getY() - mouseY;
		Translator translator = new Translator();
		translator.translateDrawing(cv, c, target, drawnObjects, translateX, translateY);
		
		//rotate
		Eraser eraser = new Eraser();
		eraser.eraseDrawing(cv, c, target.getPointList().get(0).getDiameter(), target, drawnObjects);
		
		ArrayList<Point> newPoints = new ArrayList<Point>();
		for(Point p : target.getPointList())
		{
			int newX = (int) (p.getX() * Math.cos(Math.toRadians(angle)) - p.getY() * Math.sin(Math.toRadians(angle)));
			int newY = (int) (p.getY() * Math.cos(Math.toRadians(angle)) + p.getX() * Math.sin(Math.toRadians(angle)));
			Point newP = new Point(newX, newY, target.getPointList().get(0).getDiameter());
			newP.setColor(target.getPointList().get(0).getColor());
			newPoints.add(newP);
			
		}
		target.getPointList().clear();
		target.getPointList().addAll(newPoints);
		
		//translate back
		center = getDrawingCenter(target);
		translateX = center.getX() + mouseX;
		translateY = center.getY() + mouseY;
		translator.translateDrawing(cv, c, target, drawnObjects, translateX, translateY);
	}
}