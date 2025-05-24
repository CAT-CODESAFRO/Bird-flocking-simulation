package turtle;

import Geometry.CartesianCoordinate;
import drawing.Canvas;

public class Turtle {
	private double xNew, yNew;
	private double currentAngle = 0;
	private boolean pen;
	CartesianCoordinate currentPoint = new CartesianCoordinate(400, 300);
	private Canvas canvas;
	

	public Turtle(Canvas canvas) { // pass turtle a canvas to draw on

		this.canvas = canvas;
//draw();
	}

	public void setStartPoint(CartesianCoordinate startPoint) {
		this.currentPoint = startPoint;

	}

	// rotate turtle by specified angle in degrees
	public void turn(int angleToTurn) {
		currentAngle += angleToTurn;
		
		// Keep angles in range 0 - 360
		currentAngle %= 360;
		
		// Keep all angles positive
		if (currentAngle < 0) {
			currentAngle += 360;
		}
	}

	// move turtle by the amount of pixels
	public void move(int distanceToMove) {
		double x = currentPoint.getX();
		double y = currentPoint.getY();

		// calculate next x, y values
		xNew = x + distanceToMove * Math.cos(Math.toRadians(currentAngle));
		yNew = y + distanceToMove * Math.sin(Math.toRadians(currentAngle));

		CartesianCoordinate newPoint = new CartesianCoordinate(xNew, yNew);

		if (pen == true) {

			canvas.drawLineBetweenPoints(currentPoint, newPoint); // change?
		}
		currentPoint = newPoint;

	}

	/**
	 * Moves the pen off the canvas so that the turtle's route isn't drawn for any
	 * subsequent movements.
	 */
	public void putPenUp() {
		pen = false;

	}

	// Lowers the pen onto the canvas so that the turtle's route is drawn.

	public void putPenDown() {
		pen = true;

	}

	// Draws a right-angle triangle
	public void draw() {

		putPenDown();

		
		turn(-161);
		move(16);
		turn(-109);
		move(6);
		turn(-90);
		move(15);

		putPenUp();

	}

	public void undraw() {

		for (int i = 0; i < 3; i++) {

			try {
				canvas.removeMostRecentLine();
			} catch (IndexOutOfBoundsException e) {
				System.out.println("not enough lines to remove");
			
			}

		}
	}

	// If current point is less or greater then the screen size wrap around to the
	// other side of the screen.
	public void wrapPosition(int canvasWidth, int canvasHeight) {
		if (0 > currentPoint.getX() || currentPoint.getX() > canvasWidth) {
			currentPoint = currentPoint.getX() < 0 ? new CartesianCoordinate(canvasWidth, currentPoint.getY())
					: new CartesianCoordinate(0, currentPoint.getY());
			

		}
		if (0 > currentPoint.getY() || currentPoint.getY() > canvasHeight) {
			currentPoint = currentPoint.getY() < 0 ? new CartesianCoordinate(currentPoint.getX(), canvasHeight)
					: new CartesianCoordinate(currentPoint.getX(), 0);

		}

	}

	public CartesianCoordinate getCurrentPoint() {
		return currentPoint;
	}

	public double getCurrentX() {
		return currentPoint.getX();
	}

	public double getCurrentY() {
		return currentPoint.getY();
	}

	public double getBearing() {
		return currentAngle;
	}
	public void setBearing( double limitedDiff) {
		currentAngle = limitedDiff;
	}

}
