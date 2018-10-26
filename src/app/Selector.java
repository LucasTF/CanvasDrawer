package app;

import java.util.ArrayList;

import abstractions.IDrawing;
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
}
