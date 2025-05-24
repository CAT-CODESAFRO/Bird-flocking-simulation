package Geometry;
// class to represent points in Cartesian geometry
public class CartesianCoordinate {

	private final double xPosition;
	private final double yPosition;

	public CartesianCoordinate(double xPosition, double yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
		public double getX() {
		return xPosition;
	}

	public double getY() {
		return yPosition;
	
	}
	

	public String toString() {
		return " xPosition =" + xPosition + ", yPosition =" + yPosition;
	
}
}