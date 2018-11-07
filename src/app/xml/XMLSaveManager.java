package app.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import abstractions.IDrawing;
import enums.ShapeType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import shapes.Circle;
import shapes.Line;
import shapes.Point;
import shapes.Polygon;
import shapes.Rectangle;

public class XMLSaveManager {
	
	private Document xml;
	private double yHeight;
	private double xWidth;
	
	public XMLSaveManager(double height, double width) {
		yHeight = height;
		xWidth = width;
	}
	
	public void exportXML(ArrayList<IDrawing> drawings) {
		xml = new Document();
		Element root = new Element("Figura");
		xml.setRootElement(root);
		for(IDrawing d : drawings) {
			root.addContent(getShapeElement(d));
		}
		XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
		try {
			File saveFile = FileManager.saveFile("XML");
			if(saveFile != null) {
				xmlOutput.output(xml, new FileOutputStream(saveFile));
				alertSaving(AlertType.INFORMATION, drawings.size());
			}
		} catch (IOException e) {
			e.printStackTrace();
			alertSaving(AlertType.ERROR, drawings.size());
		}
	}
	
	private Element getShapeElement(IDrawing d) {
		Element l;
		if(d.getDrawingName().equals(ShapeType.LINE.getShapeName())) {
			l = createLineElement((Line) d);
		}
		else if(d.getDrawingName().equals(ShapeType.CIRCLE.getShapeName())) {
			l = createCircleElement((Circle) d);
		}
		else if(d.getDrawingName().equals(ShapeType.RECTANGLE.getShapeName())) {
			l = createRectangleElement((Rectangle) d);
		}
		else if(d.getDrawingName().equals(ShapeType.POLYGON.getShapeName())) {
			l = createPolygonElement((Polygon) d);
		}
		else if(d.getDrawingName().equals(ShapeType.POINT.getShapeName())) {
			Point p = (Point) d;
			l = createPointElement(normalizeX(p.getX()), normalizeY(p.getY()));
		}
		else {
			l = null;
		}
		return l;
	}
	
	private Element createLineElement(Line line) {
		Element l = new Element("Reta");
		Element firstPoint = createPointElement(normalizeX(line.getFirstPoint().getX()), normalizeY(line.getFirstPoint().getY()));
		l.addContent(firstPoint);
		Element lastPoint = createPointElement(normalizeX(line.getLastPoint().getX()), normalizeY(line.getLastPoint().getY()));
		l.addContent(lastPoint);
		Element rgb = createRGBElement(line.getColor());
		l.addContent(rgb);
		return l;
	}
	
	private Element createCircleElement(Circle circle) {
		Element l = new Element("Circulo");
		Element center = createPointElement(normalizeX(circle.getFirstPoint().getX()), normalizeY(circle.getFirstPoint().getY()));
		l.addContent(center);
		Element raio = new Element("Raio");
		raio.addContent(Double.toString(circle.getRadius() / xWidth));
		l.addContent(raio);
		Element rgb = createRGBElement(circle.getColor());
		l.addContent(rgb);
		return l;
	}
	
	private Element createRectangleElement(Rectangle rectangle) {
		Element l = new Element("Retangulo");
		Element firstDiagonal = createPointElement(normalizeX(rectangle.getFirstPoint().getX()), normalizeY(rectangle.getFirstPoint().getY()));
		l.addContent(firstDiagonal);
		Element secondDiagonal = createPointElement(normalizeX(rectangle.getLastPoint().getX()), normalizeY(rectangle.getLastPoint().getY()));
		l.addContent(secondDiagonal);
		Element rgb = createRGBElement(rectangle.getColor());
		l.addContent(rgb);
		return l;
	}
	
	private Element createPolygonElement(Polygon polygon) {
		Element l;
		if(polygon.getFirstPoint().getX() == polygon.getLastPoint().getX() && polygon.getFirstPoint().getY() == polygon.getLastPoint().getY()) {
			l = new Element("Poligono");
		}
		else{
			l = new Element("LinhaPoligonal");
		}
		Element originPoint = createPointElement(normalizeX(polygon.getLines().get(0).getFirstPoint().getX()), normalizeY(polygon.getLines().get(0).getFirstPoint().getY()));
		l.addContent(originPoint);
		for(Line li : polygon.getLines()) {
			Element interestPoint = createPointElement(normalizeX(li.getLastPoint().getX()), normalizeY(li.getLastPoint().getY()));
			l.addContent(interestPoint);
		}
		Element rgb = createRGBElement(polygon.getColor());
		l.addContent(rgb);
		return l;
	}
	
	private Element createPointElement(double x, double y) {
		Element l = new Element("Ponto");
		Element px = new Element("x");
		px.addContent(new Text(Double.toString(x)));
		l.addContent(px);
		Element py = new Element("y");
		py.addContent(new Text(Double.toString(y)));
		l.addContent(py);
		return l;
	}
	
	private Element createRGBElement(Color color) {
		Element l = new Element("Cor");
		Element r = new Element("R");
		r.addContent(new Text(Double.toString(color.getRed() * 255)));
		l.addContent(r);
		Element g = new Element("G");
		g.addContent(new Text(Double.toString(color.getGreen() * 255)));
		l.addContent(g);
		Element b = new Element("B");
		b.addContent(new Text(Double.toString(color.getBlue() * 255)));
		l.addContent(b);
		return l;
	}
	
	private void alertSaving(AlertType alertType, int savedObjects) {
		Alert alert = new Alert(alertType);
		if(alertType == AlertType.INFORMATION) {
			alert.setHeaderText(null);
			alert.setTitle("Salvamento concluído");
			alert.setContentText(savedObjects + " desenhos foram salvos com sucesso.");
			alert.showAndWait();
		}
		else {
			alert.setHeaderText(null);
			alert.setTitle("Salvamento falhou");
			alert.setContentText("Não foi possível salvar os desenhos.");
			alert.showAndWait();
		}
	}
	
	private double normalizeX(int x) {
		return x / xWidth;
	}
	
	private double normalizeY(int y) {
		return y / yHeight;
	}

}
