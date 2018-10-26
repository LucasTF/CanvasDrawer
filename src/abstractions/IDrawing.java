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
	public void recalculatePointsOfInterest();
	public Color getColor();
}
