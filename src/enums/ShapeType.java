package enums;

public enum ShapeType {
	POINT("Ponto"),
	LINE("Reta"),
	CIRCLE("Circunfer�ncia"),
	SNOWFLAKE("Snowflake"),
	POLYGONALLINE("Linha Poligonal"),
	CLOSEDPOLYGON("Pol�gono"),
	RECTANGLE("Ret�ngulo");
	
	private String shapeName;
	
	ShapeType(String shapeName) {
		this.shapeName = shapeName;
	}
	
	public String getShapeName() {
		return this.shapeName;
	}
}
