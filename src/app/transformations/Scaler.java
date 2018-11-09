package app.transformations;

import java.util.ArrayList;

import abstractions.IDrawing;
import abstractions.Selector;
import app.utils.Eraser;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import shapes.Point;

public class Scaler extends Selector
{
	public void scaleDrawing(Canvas cv, Color background, IDrawing target, ArrayList<IDrawing> drawnObjects, double mouseX, double mouseY, double factor)
	{
		//translate to origin
		Point center = getDrawingCenter(target);
		double translateX = center.getX() - mouseX;
		double translateY = center.getY() - mouseY;
		Translator translator = new Translator();
		translator.translateDrawing(cv, background, target, drawnObjects, translateX, translateY);
		
		//scale
		Eraser eraser = new Eraser();
		eraser.eraseDrawing(cv, background, target.getPointList().get(0).getDiameter(), target, drawnObjects);
		
		ArrayList<Point> poi = target.getPointsOfInterest();
		for(Point p : poi)
		{
			Point newP = new Point((int) (p.getX() * factor), (int) (p.getY() * factor), target.getPointList().get(0).getDiameter());
			newP.setColor(target.getPointList().get(0).getColor());
			poi.set(poi.indexOf(p), newP);
			
		}
		target.setPointsOfInterest(poi);
		
		//translate back
		center = getDrawingCenter(target);
		translateX = center.getX() + mouseX;
		translateY = center.getY() + mouseY;
		translator.translateDrawing(cv, background, target, drawnObjects, translateX, translateY);
	}
}
