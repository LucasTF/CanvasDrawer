package shapes;

import abstractions.IShape;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class OpenPolygon implements IShape{
	
	private Point firstPoint;
	private Point lastPoint;
	private Line lines;
	
	public OpenPolygon(){
		lines = new Line();
	}

	@Override
	public void setFirstPoint(Point p) {
		firstPoint = p;
		lines.setFirstPoint(firstPoint);
	}

	@Override
	public void setLastPoint(Point p) {
		lastPoint = p;
		lines.setLastPoint(lastPoint);
	}

	@Override
	public void draw(Canvas gv, Color c, double diameter, double iterations) {
		lines.draw(gv,c,diameter,iterations);
		setFirstPoint(this.lastPoint);
	}
	
}
