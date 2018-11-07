package app.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
			drawing = new Line();
		}
		else if(element.getName().equals("Circulo")) {
			drawing = new Circle();
		}
		else if(element.getName().equals("Retangulo")) {
			drawing = new Rectangle();
		}
		else if(element.getName().equals("Poligono") || element.getName().equals("LinhaPoligonal")){
			drawing = new Polygon();
		}
		else if(element.getName().equals("Ponto")) {
			drawing = createPoint(element.getChildren());
		}
		else {
			drawing = null;
		}
		createDrawing(element, drawing);
		return drawing;
	}
	
	private void createDrawing(Element drawingElement, IDrawing drawing)
	{
		//get color
		drawing.setColor(parseColor(drawingElement.getChild("Cor")));
		
		//get points
		ArrayList<Point> poi= new ArrayList<Point>();
		for(Element el : drawingElement.getChildren("Ponto"))
		{
				poi.add(new Point(denormalizeX(el.getChildText("x")), denormalizeY(el.getChildText("y"))));
		}
		
		//get radius if circle and calculate circPoint from it
		if(drawingElement.getName().equals("Circulo"))
		{
			int radius = Math.toIntExact(Math.round(Double.parseDouble(drawingElement.getChildText("Raio")) * xWidth));
			poi.add(new Point(poi.get(0).getX() + radius, poi.get(0).getY()));
		}
		drawing.setPointsOfInterest(poi);
	}
	
	private Point createPoint(List<Element> pointE) {
		Point point;
		Element x = pointE.get(0);
		Element y;
		if(x.getName().equals("x")) {
			y = pointE.get(1);
		}
		else {
			y = pointE.get(0);
			x = pointE.get(1);
		}
		point = new Point(denormalizeX(x.getText()), denormalizeY(y.getText()));
		return point;
	}
	
	private int denormalizeX(String x) {
		return Math.toIntExact(Math.round(Double.parseDouble(x) * xWidth));
	}
	
	private int denormalizeY(String y) {
		return Math.toIntExact(Math.round(Double.parseDouble(y) * yHeight));
	}
	
	private Color parseColor(Element el)
	{
		return Color.rgb((int) Double.parseDouble(el.getChildText("R")), (int) Double.parseDouble(el.getChildText("G")), (int) Double.parseDouble(el.getChildText("B")));
	}
}
