package obstacles;

import Geometry.CartesianCoordinate;
import drawing.Canvas;
import shape.Rectangle;

public class Obstacle extends Rectangle{

	public Obstacle(Canvas canvas, CartesianCoordinate topCorner, int width, int height) {
		super(canvas, topCorner, width, height);
	}

}
