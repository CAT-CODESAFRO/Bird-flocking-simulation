package Geometry;
import java.lang.Math;


public class LineSegment {
// declaration of start and end point of line segment of class Cartesian
	private final CartesianCoordinate startPoint;

	private final CartesianCoordinate endPoint;

	// constructor 
	public  LineSegment(CartesianCoordinate startPoint, CartesianCoordinate endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	
}
// getter to return start point
	public CartesianCoordinate getStartPoint() {
		return  startPoint;
	}

	public CartesianCoordinate getEndPoint() {
		return endPoint;
	}
	
	public String toString() {
		return "lineSegment [startPoint =" + startPoint + ", endPoint =" + endPoint + "]";
	}
	// method to determine the length between two points
	public double distance() {
		// calculate length of triangle edge a
		double a = startPoint.getX() - endPoint.getX();
		double b = startPoint.getY() - endPoint.getY();
		
		double c =  Math.hypot(a,b);
		return c;
	}
	
	}
	
	
