package lucastf.canvasdrawer.abstractions;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import lucastf.canvasdrawer.shapes.Point;

public interface IShape {
    void setFirstPoint(Point p);
    void setLastPoint(Point p);
    Point getFirstPoint();
    Point getLastPoint();
    void draw(Canvas cv, Color c, double diameter, double iterations);
}
