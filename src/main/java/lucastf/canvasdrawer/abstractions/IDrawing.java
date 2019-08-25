package lucastf.canvasdrawer.abstractions;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import lucastf.canvasdrawer.shapes.Point;

public interface IDrawing {
	
	ArrayList<Point> getPointList();
	void erasePoints(Canvas cv, Color c, double thickness);
	String getDrawingName();
	void redraw(Canvas cv);
	void redraw(Canvas cv, double diameter);
	ArrayList<Point> getPointsOfInterest();
	void setPointsOfInterest(ArrayList<Point> poi);
	Color getColor();
	void setColor(Color c);

}
