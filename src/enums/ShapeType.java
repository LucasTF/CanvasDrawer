package enums;

public enum ShapeType {
	POINT("Ponto"),
	LINE("Reta"),
	CIRCLE("Circunferência"),
	SNOWFLAKE("Snowflake"),
	POLYGON("Polígono/Linha Poligonal"),
	POLYGONALLINE("Linha Poligonal"),
	RECTANGLE("Retângulo");
	
	private String shapeName;
	
	ShapeType(String shapeName) {
		this.shapeName = shapeName;
	}
	
	public String getShapeName() {
		return this.shapeName;
	}
}
