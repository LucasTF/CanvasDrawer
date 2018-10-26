package abstractions;

import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import shapes.Point;

public abstract class Selector {

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
