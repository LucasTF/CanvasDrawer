package abstractions;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import shapes.Point;

public interface IShape {
    void setFirstPoint(Point p);
    void setLastPoint(Point p);
    Point getFirstPoint();
    Point getLastPoint();
    void draw(Canvas cv, Color c, double diameter, double iterations);
}
