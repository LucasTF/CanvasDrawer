package lucastf.canvasdrawer.services;

import java.util.ArrayList;

import lucastf.canvasdrawer.abstractions.IDrawing;
import lucastf.canvasdrawer.abstractions.Selector;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class EraserService extends Selector{
	
	public void eraseDrawing(Canvas cv, Color c, int thickness, IDrawing drawing, ArrayList<IDrawing> drawnObjects) {
		drawing.erasePoints(cv, c, thickness + 2);
		drawnObjects.remove(drawing);
		for(IDrawing d : drawnObjects)
		{
			d.redraw(cv);
		}
	}
	
}
