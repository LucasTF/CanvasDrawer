package app;

import java.util.ArrayList;

import abstractions.IDrawing;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import shapes.Point;

public class Eraser {
	
	public IDrawing findClickedDrawing(MouseEvent e, ArrayList<IDrawing> drawnObjects) {
		IDrawing rightObject = null;
		int x = (int) e.getX();
		int y = (int) e.getY();
		for(IDrawing d : drawnObjects) {
			for(Point p : d.getPointList()) {
				if(((int) p.getPoint().getX() <= x+p.getDiameter() && (int) p.getPoint().getX() >= x-p.getDiameter()) && ((int) p.getPoint().getY() >= y-p.getDiameter()) && (int) p.getPoint().getY() <= y+p.getDiameter()) {
					rightObject = d;
					return rightObject;
				}
			}
		}
		return null;
	}
	
	public void eraseDrawing(Canvas cv, Color c, int thickness, IDrawing drawing, ArrayList<IDrawing> drawnObjects) {
		drawing.erasePoints(cv, c, thickness+2);
		drawnObjects.remove(drawing);
		for(IDrawing d : drawnObjects)
		{
			d.redraw(cv);
		}
	}
	
}
