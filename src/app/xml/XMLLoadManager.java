package app.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import abstractions.IDrawing;
import javafx.scene.paint.Color;
import shapes.Circle;
import shapes.Line;
import shapes.Point;
import shapes.Polygon;
import shapes.Rectangle;

public class XMLLoadManager {
	
	private ArrayList<IDrawing> drawings;
	private double yHeight;
	private double xWidth;
	
	public XMLLoadManager(double height, double width) {
		yHeight = height;
		xWidth = width;
	}
	
	public ArrayList<IDrawing> importXML(){
		drawings = new ArrayList<IDrawing>();
		SAXBuilder builder = new SAXBuilder();
		try {
			File loadFile = FileManager.loadFile("XML");
			if(loadFile != null) {
				Document xml = builder.build(loadFile);
				Element root = xml.getRootElement();
				for(Element l : root.getChildren()) {
					drawings.add(getDrawingFromElement(l));
				}
			}
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return drawings;
	}
	
	private IDrawing getDrawingFromElement(Element element) {
		IDrawing drawing;
		
		if(element.getName().equals("Reta")) {
			drawing = createLineDrawing(element.getChildren());
		}
		else if(element.getName().equals("Circulo")) {
			drawing = createCircleDrawing(element.getChildren());
		}
		else if(element.getName().equals("Retangulo")) {
			drawing = createRectangleDrawing(element.getChildren());
		}
		else if(element.getName().equals("Poligono") || element.getName().equals("LinhaPoligonal")){
			drawing = createPolygonDrawing(element.getChildren());
		}
		else {
			drawing = null;
		}
		return drawing;
	}
	
	private Line createLineDrawing(List<Element> lineE) {
		Line line = new Line();
		line.setFirstPoint(new Point(denormalizeX(lineE.get(0).getChildText("x")), denormalizeY(lineE.get(0).getChildText("y"))));
		List<Element> rgb = lineE.get(2).getChildren();
		line.getFirstPoint().setColor(Color.rgb((int) Double.parseDouble(rgb.get(0).getText()), (int) Double.parseDouble((rgb.get(1).getText())), (int) Double.parseDouble(rgb.get(2).getText())));
		line.setLastPoint(new Point(denormalizeX(lineE.get(1).getChildText("x")), denormalizeY(lineE.get(1).getChildText("y"))));
		return line;
	}
	
	private Circle createCircleDrawing(List<Element> circleE) {
		Circle circle = new Circle();
		circle.setFirstPoint(new Point(denormalizeX(circleE.get(0).getChildText("x")), denormalizeY(circleE.get(0).getChildText("y"))));
		List<Element> rgb = circleE.get(2).getChildren();
		circle.getFirstPoint().setColor(Color.rgb((int) Double.parseDouble(rgb.get(0).getText()), (int) Double.parseDouble((rgb.get(1).getText())), (int) Double.parseDouble(rgb.get(2).getText())));
		circle.setRadius(Math.toIntExact(Math.round(Double.parseDouble(circleE.get(1).getText()) * xWidth)));
		circle.setLastPoint(new Point(circle.getFirstPoint().getX() + circle.getRadius(), circle.getFirstPoint().getY()));
		return circle;
	}
	
	private Rectangle createRectangleDrawing(List<Element> rectangleE) {
		Rectangle rectangle = new Rectangle();
		rectangle.setFirstPoint(new Point(denormalizeX(rectangleE.get(0).getChildText("x")), denormalizeY(rectangleE.get(0).getChildText("y"))));
		List<Element> rgb = rectangleE.get(2).getChildren();
		rectangle.getFirstPoint().setColor(Color.rgb((int) Double.parseDouble(rgb.get(0).getText()), (int) Double.parseDouble((rgb.get(1).getText())), (int) Double.parseDouble(rgb.get(2).getText())));
		rectangle.setLastPoint(new Point(denormalizeX(rectangleE.get(1).getChildText("x")), denormalizeY(rectangleE.get(1).getChildText("y"))));
		return rectangle;
	}
	
	private Polygon createPolygonDrawing(List<Element> polygonE){
		Polygon polygon = new Polygon();
		for(int i = 0; i < polygonE.size()-1; i++){
			Element e = polygonE.get(i);
			Element e2 = polygonE.get(i+1);
			if(e.getName().equals("Ponto") && e2.getName().equals("Ponto")){
				Line line = new Line();
				line.setFirstPoint(new Point(denormalizeX(e.getChild("x").getText()), denormalizeY(e.getChild("y").getText())));
				line.setLastPoint(new Point(denormalizeX(e2.getChild("x").getText()), denormalizeY(e2.getChild("y").getText())));
				polygon.setLineForPolygon(line);
			}
		}
		List<Element> rgb = polygonE.get(polygonE.size()-1).getChildren();
		polygon.setColor(Color.rgb((int) Double.parseDouble(rgb.get(0).getText()), (int) Double.parseDouble((rgb.get(1).getText())), (int) Double.parseDouble(rgb.get(2).getText())));
		return polygon;
	}
	
	private int denormalizeX(String x) {
		return Math.toIntExact(Math.round(Double.parseDouble(x) * xWidth));
	}
	
	private int denormalizeY(String y) {
		return Math.toIntExact(Math.round(Double.parseDouble(y) * yHeight));
	}

}
