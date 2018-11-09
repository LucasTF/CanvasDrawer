package app.transformations;

import java.util.ArrayList;

import abstractions.IDrawing;
import abstractions.Selector;
import app.utils.Eraser;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import shapes.Point;

public class Translator extends Selector
{
	public void translateDrawing(Canvas cv, Color background, IDrawing target, ArrayList<IDrawing> drawnObjects, double mouseX, double mouseY)
	{
		Eraser eraser = new Eraser();
		eraser.eraseDrawing(cv, background, target.getPointList().get(0).getDiameter(), target, drawnObjects);
		
		int deltaX = 0;
		int deltaY = 0;
		Point center = getDrawingCenter(target);
		deltaX = (int) mouseX - center.getX();
		deltaY = (int) mouseY - center.getY();
				
		ArrayList<Point> poi = target.getPointsOfInterest();
		for(Point p : poi)
		{
			Point newP = new Point(p.getX() + deltaX, p.getY() + deltaY, target.getPointList().get(0).getDiameter());
			newP.setColor(target.getColor());
			poi.set(poi.indexOf(p), newP);
		}
		target.setPointsOfInterest(poi);
		target.redraw(cv);
		drawnObjects.add(target);
	}
}
