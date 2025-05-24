package shape;

import Geometry.CartesianCoordinate;
import drawing.Canvas;


public class Rectangle implements shape {
	private int height, width;
	private CartesianCoordinate topLeftCorner;
	private CartesianCoordinate topRightCorner;
	private CartesianCoordinate bottomLeftCorner;
	private CartesianCoordinate bottomRightCorner;
	private CartesianCoordinate centre;
	private Canvas canvas;

	public Rectangle(Canvas canvas, CartesianCoordinate topLeftCorner, int width, int height) {
		this.canvas = canvas;
		this.topLeftCorner = topLeftCorner;
		this.width = width;
		this.height = height;

		topRightCorner = new CartesianCoordinate(topLeftCorner.getX() + width, topLeftCorner.getY());
		bottomLeftCorner = new CartesianCoordinate(topLeftCorner.getX(), topLeftCorner.getY() + height);
		bottomRightCorner = new CartesianCoordinate(topLeftCorner.getX() + width, topLeftCorner.getY() + height);

	}

	public void drawRectangle() {
		canvas.drawLineBetweenPoints(topLeftCorner, topRightCorner);
		canvas.drawLineBetweenPoints(topRightCorner, bottomRightCorner);
		canvas.drawLineBetweenPoints(bottomRightCorner, bottomLeftCorner);
		canvas.drawLineBetweenPoints(bottomLeftCorner, topLeftCorner);

	}

	public void unDrawRectangle(Canvas canvas) {

		for (int i = 0; i < 4; i++) {
			canvas.removeMostRecentLine();
		}
	}

	public CartesianCoordinate getCentre() {

		centre = new CartesianCoordinate(topLeftCorner.getX() + width / 2.0, topLeftCorner.getY() + height / 2.0);

		return centre;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getRadius() {
		return (int) Math.sqrt((Math.pow(height / 2.0, 2)) + Math.pow(width / 2.0, 2)) ; // +20 to account for boid
																							// length

	}

	public CartesianCoordinate getTopLeftCorner() {
		return topLeftCorner;
	}

	public CartesianCoordinate getBottomRightCorner() {
		return bottomRightCorner;
	}

}