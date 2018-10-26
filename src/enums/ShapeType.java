package enums;

public enum ShapeType {
	POINT("Ponto"),
	LINE("Reta"),
	CIRCLE("Circunferência"),
	SNOWFLAKE("Snowflake"),
	POLYGONALLINE("Linha Poligonal"),
	CLOSEDPOLYGON("Polígono"),
	RECTANGLE("Retângulo");
	
	private String shapeName;
	
	ShapeType(String shapeName) {
		this.shapeName = shapeName;
	}
	
	public String getShapeName() {
		return this.shapeName;
	}
}
