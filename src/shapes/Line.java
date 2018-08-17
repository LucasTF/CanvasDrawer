package shapes;

import javafx.scene.canvas.GraphicsContext;

public class Line {
	
	private Point firstPoint;
	private Point lastPoint;
	
	public Line(Point a, Point b) {
		firstPoint = a;
		lastPoint = b;
	}
	
	public Line() {
		
	}
	
	public void setFirstPoint(Point p) {
		firstPoint = p;
	}
	
	public void setLastPoint(Point p) {
		lastPoint = p;
	}
	
	public void drawLine(GraphicsContext gc) {
		Point p;
		int deltaX, deltaY, steps;
		deltaX = Math.abs((int) (lastPoint.getPoint().getX() - firstPoint.getPoint().getX()));
		deltaY = Math.abs((int) (lastPoint.getPoint().getY() - firstPoint.getPoint().getY()));
		System.out.println(deltaX + " " + deltaY);
		if(deltaX > deltaY) {
			steps = deltaX;
		}
		else {
			steps = deltaY;
		}
		double xIncrement, yIncrement;
		xIncrement = deltaX / (double) steps;
		yIncrement = deltaY / (double) steps;
		System.out.println(xIncrement + " " + yIncrement);
		int x = (int) firstPoint.getPoint().getX();
		int y = (int) firstPoint.getPoint().getY();
		for(int i = 0; i < steps; i++) {
			x = (int) Math.round(x + xIncrement);
			y = (int) Math.round(y + yIncrement);
			p = new Point(x, y);
			p.drawPoint(gc);
		}
	}

}
