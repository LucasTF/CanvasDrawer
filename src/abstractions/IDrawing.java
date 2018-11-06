package abstractions;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import shapes.Point;

public interface IDrawing {
	
	public ArrayList<Point> getPointList();
	public void erasePoints(Canvas cv, Color c, double thickness);
	public String getDrawingName();
	public void redraw(Canvas cv);
	public void redraw(Canvas cv, double diameter);
	public ArrayList<Point> getPointsOfInterest();
	public void setPointsOfInterest(ArrayList<Point> poi);
	public Color getColor();
	public void setColor(Color c); //TODO: change all code to use color fields instead the color of a point in the pointList
}
