package lucastf.canvasdrawer.services;

import java.util.ArrayList;

import lucastf.canvasdrawer.abstractions.IDrawing;
import lucastf.canvasdrawer.abstractions.Selector;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import lucastf.canvasdrawer.shapes.Point;

public class RotatorService extends Selector
{
	public void rotateDrawing(Canvas cv, Color background, IDrawing target, ArrayList<IDrawing> drawnObjects, double mouseX, double mouseY, int angle)
	{
		//translate to origin
		Point center = getDrawingCenter(target);
		double translateX = center.getX() - mouseX;
		double translateY = center.getY() - mouseY;
		TranslatorService translator = new TranslatorService();
		translator.translateDrawing(cv, background, target, drawnObjects, translateX, translateY);
		
		//rotate
		EraserService eraser = new EraserService();
		eraser.eraseDrawing(cv, background, target.getPointList().get(0).getDiameter(), target, drawnObjects);
		
		ArrayList<Point> poi = target.getPointsOfInterest();
		for(Point p : poi)
		{
			int newX = (int) (p.getX() * Math.cos(Math.toRadians(angle)) - p.getY() * Math.sin(Math.toRadians(angle)));
			int newY = (int) (p.getY() * Math.cos(Math.toRadians(angle)) + p.getX() * Math.sin(Math.toRadians(angle)));
			Point newP = new Point(newX, newY, target.getPointList().get(0).getDiameter());
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