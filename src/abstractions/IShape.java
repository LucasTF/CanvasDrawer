package abstractions;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import shapes.Point;

public interface IShape {
    void setFirstPoint(Point p);
    void setLastPoint(Point p);
    void draw(GraphicsContext gc, Color c, double diameter);
}
