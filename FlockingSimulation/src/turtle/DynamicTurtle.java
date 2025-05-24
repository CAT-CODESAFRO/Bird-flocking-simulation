package turtle;

import java.util.List;

import Geometry.CartesianCoordinate;
import drawing.Canvas;
import obstacles.Obstacle;

public class DynamicTurtle extends Turtle {

	private int speed = 200; // initial speed

	// Controls the angular velocity for each rule
	private double alignmentTurnSpeed = 360; // degrees/sec 90, 90, 90
	private double cohesionTurnSpeed = 360;
	private double separationTurnSpeed = 360;

	// Boids will head towards this direction
	private double targetBearing = getBearing();

	private int alignmentRadius; // alignment Parameters 60, 0.8
	private double alignmentStrength;

	private int cohesionRadius; // cohesion Parameters 150, 1
	private double cohesionStrength;

	private int seperationRadius; // seperation Parameters 40, 1
	private double seperationStrength;

	public DynamicTurtle(Canvas canvas) {
		super(canvas);
		turn((int) (Math.random() * 360));
		draw();

	}

// Constructor accepts canvas and a starting point
	// Draws Turtle at a random orientation
	public DynamicTurtle(Canvas canvas, double startPointX, double startPointY) {
		super(canvas);

		setStartPoint(new CartesianCoordinate(startPointX, startPointY));
		turn((int) (Math.random() * 360)); // boid draws itself and starts at a random angle
		draw();

	}

// occurs every 10ms
	public void update(int deltaTime) {

		double dt = deltaTime / 1000.0;
		int moveDistance = (int) (speed * dt); // move Number or pixels / second
		move(moveDistance);

		double accumulatedAngleToTurn = ((targetBearing - getBearing() + 540) % 360) - 180; // Difference in angle
																							// between
		// current bearing and desired
		// bearing (calculated in
		// flocking) and normalised to (- 180) - 180)
		turn((int) Math.round(accumulatedAngleToTurn));
		// System.out.println("anle turn" + accumulatedAngleToTurn);
	}

	// Linear speed getter and setter

// Used to calculate the correct angular velocity
	private double clamp(double value, double min, double max) {
		return Math.max(min, Math.min(max, value));
	}

	public void flock(List<DynamicTurtle> boids, double cohesion, double alignment, double seperation, int cohesRad,
			int alignRad, int sepRad) {
		// Store bearing adjustments separately
		this.cohesionStrength = cohesion;
		this.alignmentStrength = alignment;
		this.seperationStrength = seperation;
		this.cohesionRadius = cohesRad;
		this.alignmentRadius = alignRad;
		this.seperationRadius = sepRad;

		double dt = 10 / 1000.0;
		double alignmentBearing = alignment(boids);
		double cohesionBearing = cohesion(boids);
		double separationBearing = separation(boids);
		double maxAlignChange = alignmentTurnSpeed * dt; // maximum angular change = angule velocity * time
		double maxCohesionChange = cohesionTurnSpeed * dt;
		double maxSeparationChange = separationTurnSpeed * dt;

		alignmentBearing = clamp(alignmentBearing, -maxAlignChange, maxAlignChange); 
		cohesionBearing = clamp(cohesionBearing, -maxCohesionChange, maxCohesionChange);
		separationBearing = clamp(separationBearing, -maxSeparationChange, maxSeparationChange);

		// Blend the behaviours by creating a weighted some of the desired bearing
		// calculated from alignment, cohesion and seperation
		double blend = (alignmentBearing * alignmentStrength + cohesionBearing * cohesionStrength
				+ separationBearing * seperationStrength);// + avoidObstacleBearing * 100);

		targetBearing = (getBearing() + blend + 360) % 360; // add the weighted bearing contribution to the current bearing
		
		System.out.println(" alignment: " + alignment + " cohesion: " + cohesion + " seperation: " + seperation + " alignRadius: " + alignRad + "cohesionRadius" + cohesRad + "");
		
	}

	// alignment algorithm
	public double alignment(List<DynamicTurtle> boids) {

		int sumBearing = 0;
		int populationCount = 0; // Count up the number of boids within a local radius

		for (DynamicTurtle boid : boids) {

			double xDistance = this.getCurrentX() - boid.getCurrentX();
			double yDistance = this.getCurrentY() - boid.getCurrentY();

			double distanceToOtherBoid = Math.hypot(xDistance, yDistance); // Calculate distance from this boid to
																			// another boid

			if (boid != this && distanceToOtherBoid < alignmentRadius) { // exclude me and if within allignment radius
																			// do next
				sumBearing += boid.getBearing();
				populationCount++;
			}
		}
		if (populationCount > 0) { // if there are nearby boids
			double averageBearing = sumBearing / populationCount;
			double currentBearing = this.getBearing(); // my current bearing

			double angleToTurn = ((averageBearing - currentBearing + 540) % 360) - 180;
			return angleToTurn; // return the desired change in angle and normalise to range -180, 180

		} else {
			return 0;
		}

	}

	// cohesion alogorithm
	public double cohesion(List<DynamicTurtle> boids) {
		int sumX = 0;
		int sumY = 0;
		int populationCount = 0;

		for (DynamicTurtle boid : boids) {

			double xDistance = this.getCurrentX() - boid.getCurrentX();
			double yDistance = this.getCurrentY() - boid.getCurrentY();
			double distanceToOtherBoid = Math.hypot(xDistance, yDistance); // Calculate distance from this boid to
																			// another boid

			if (this != boid && distanceToOtherBoid < cohesionRadius) {

				sumX += boid.getCurrentX(); // sum up x coordinates
				sumY += boid.getCurrentY(); // sum up y coordinates
				populationCount++;
			}
		}
		if (populationCount > 0) {

			double avgX = sumX / populationCount; // Calculate a local centre of gravity by summing up positions of
													// otherboids / population count.
			double avgY = sumY / populationCount;

			double x = avgX - this.getCurrentX(); // Difference in x between CoG and this boid
			double y = avgY - this.getCurrentY(); // Difference in y between CoG and this boid

			double angleToCentreOfGravity = Math.toDegrees(Math.atan2(y, x)); // Calculate angle between this boid and
																				// the local centre of gravity
			double angleToTurn = ((angleToCentreOfGravity - this.getBearing() + 540) % 360) - 180; // Difference in
																									// angle between
																									// angle to CoG and
																									// current bearing

			return angleToTurn;
		} else {
			return 0;
		}
	}

	public double separation(List<DynamicTurtle> boids) {
		double SumRepelX = 0;
		double SumRepelY = 0;
		int count = 0;

		for (DynamicTurtle boid : boids) {
			if (boid == this)
				continue;

			double dx = this.getCurrentX() - boid.getCurrentX();
			double dy = this.getCurrentY() - boid.getCurrentY();
			double distance = Math.hypot(dx, dy); // calculate distance to otherboids

			if (distance > 0 && distance < seperationRadius) {
				// Invert the vector (away from boid) and normalise it using inverse square law
				SumRepelX += dx / (distance * distance);
				SumRepelY += dy / (distance * distance);
				count++;
			}
		}

		if (count > 0) {

			double AvgRepelY = SumRepelY / count; // Calculate the average postion (CoG) away from all other boids
			double AvgRepelX = SumRepelX / count;

			double repelAngle = Math.toDegrees(Math.atan2(AvgRepelY, AvgRepelX)); // Calulate the angle to the CoG
			if (repelAngle < 0)
				repelAngle += 360; // Ensure angle is between 0 - 360 degrees

			double currentBearing = getBearing();
			double angleToTurn = ((repelAngle - currentBearing + 540) % 360) - 180; // Calculate the difference between
																					// desired bearing towards CoG and
																					// current bearing of this boid

			return angleToTurn;
		} else {
			return 0;
		}
	}

	public void avoidObstacles(List<Obstacle> obstacles) {
		
		int offSet = 20;

		for (Obstacle obstacle : obstacles) {
			CartesianCoordinate center = obstacle.getCentre();
			CartesianCoordinate topLeftCorner = obstacle.getTopLeftCorner();
			CartesianCoordinate bottomRightCorner = obstacle.getBottomRightCorner();
			double topLeftCornerX = topLeftCorner.getX();
			double topLeftCornerY = topLeftCorner.getY();
			double bottomRightCornerX = bottomRightCorner.getX();
			double bottomRightCornerY = bottomRightCorner.getY();
			double centerX = center.getX();
			double centerY = center.getY();
			double boidX = this.getCurrentX();
			double boidY = this.getCurrentY();
			double boidToCenterX = boidX - centerX;
			double boidToCenterY = boidY - centerY;
			double boidToCenter = Math.hypot(boidToCenterX, boidToCenterY);
			double obstacleRadius = obstacle.getRadius();

			if (topLeftCornerX - offSet< boidX && boidX < bottomRightCornerX + offSet && topLeftCornerY - offSet< boidY
					&& boidY < bottomRightCornerY + offSet || boidToCenter < obstacleRadius) {

				double angleToCenter = Math.toDegrees(Math.atan2(boidToCenterY, boidToCenterX)); // angle from the
																									// centre to the
																									// boid
				double angleAwayCenter = angleToCenter;

				targetBearing = (angleAwayCenter * 0.5 + 360) % 360;

				// System.out.println("requirments met! Target angle = " + targetBearing);
			}
		}
	}

	public void setCohesionRadius(int value) {
		this.cohesionRadius = value;

	}

	public void setSeparationRadius(int value) {
		this.seperationRadius = value;

	}

	public void setAlignmentRadius(int alignmentRadius) {
		this.alignmentRadius = alignmentRadius;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setAlignmentStrength(double d) {
		this.alignmentStrength = d;

	}

	public void setCohesionStrength(double d) {
		this.cohesionStrength = d;

	}

	public void setSeparationStrength(double d) {
		this.cohesionStrength = d;

	}

}
