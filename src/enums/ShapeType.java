package enums;

public enum ShapeType {
	POINT("Ponto"),
	LINE("Reta"),
	CIRCLE("Circunfer�ncia"),
	SNOWFLAKE("Snowflake"),
	POLYGON("Pol�gono/Linha Poligonal"),
	POLYGONALLINE("Linha Poligonal"),
	RECTANGLE("Ret�ngulo");
	
	private String shapeName;
	
	ShapeType(String shapeName) {
		this.shapeName = shapeName;
	}
	
	public String getShapeName() {
		return this.shapeName;
	}
}
