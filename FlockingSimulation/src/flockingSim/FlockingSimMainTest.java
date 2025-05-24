package flockingSim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Geometry.CartesianCoordinate;
import turtle.DynamicTurtle;
import obstacles.Obstacle;
import tools.Utils;

public class FlockingSimMainTest extends SimulationGui {
	private final int deltaTime = 10;
	private final boolean continueRunning = true;
	private final List<DynamicTurtle> boids;
	private final List<Obstacle> obstacles;
	private double cohesion = INIT_COHESION;
	private double alignment = INIT_ALIGNMENT;
	private double seperation = INIT_SEPARATION;
	private int cohesionRadius = INIT_COHESION_RADIUS;
	private int seperationRadius = INIT_SEPARTION_RADIUS;
	private int alignmentRadius = INIT_ALIGNMENT_RADIUS;
	private final int BOID_START_POPULATION = 40;

	public FlockingSimMainTest() {
		super.setup();

		boids = Collections.synchronizedList(new ArrayList<>());
		obstacles = Collections.synchronizedList(new ArrayList<>());

		setupListeners();
		setUpObstacles();
		setUpBoids();
		runTurtleGame();
	}

	private void setupListeners() {
		addBoidButton.addActionListener(e -> boids.add(new DynamicTurtle(canvas, WINDOW_WIDTH/2  , WINDOW_HEIGHT/2)));

		removeBoidButton.addActionListener(e -> {
			boids.get(0).undraw();
			boids.remove(0);
		});

		resetButton.addActionListener(e -> {
			canvas.clear();
			boids.clear();
			obstacles.clear();
			setUpObstacles();
			setUpBoids();
			cohesionRadiusSlider.setValue(INIT_COHESION_RADIUS);
			alignmentRadiusSlider.setValue(INIT_ALIGNMENT_RADIUS);
			separationRadiusSlider.setValue(INIT_SEPARTION_RADIUS);
			alignmentStrengthSlider.setValue((int)(INIT_ALIGNMENT*100));
			cohesionStrengthSlider.setValue((int)(INIT_COHESION*100));
			separationStrengthSlider.setValue((int)(INIT_SEPARATION*100));
		});

		cohesionRadiusSlider.addChangeListener(e -> cohesionRadius = cohesionRadiusSlider.getValue());
		alignmentRadiusSlider.addChangeListener(e -> alignmentRadius = alignmentRadiusSlider.getValue());
		separationRadiusSlider.addChangeListener(e -> seperationRadius = separationRadiusSlider.getValue());
		cohesionStrengthSlider.addChangeListener(e -> cohesion = cohesionStrengthSlider.getValue() / 100.0);
		alignmentStrengthSlider.addChangeListener(e -> alignment = alignmentStrengthSlider.getValue() / 100.0);
		separationStrengthSlider.addChangeListener(e -> seperation = separationStrengthSlider.getValue() / 99.9);

	}

	private void setUpObstacles() {
		obstacles.add(new Obstacle(canvas, new CartesianCoordinate(100, 300), 120, 80));
		obstacles.add(new Obstacle(canvas, new CartesianCoordinate(350, 200), 80, 150));
		obstacles.add(new Obstacle(canvas, new CartesianCoordinate(550, 100), 150, 120));
		for (Obstacle obstacle : obstacles)
			obstacle.drawRectangle();
	}

	private void setUpBoids() {
		for (int i = 0; i < BOID_START_POPULATION; i++) {
			boids.add(new DynamicTurtle(canvas, Math.random() * 800, Math.random() * 400));
		}
	}

	private void runTurtleGame() {

		while (continueRunning) {
			Utils.pause(deltaTime);

			synchronized (obstacles) {
				synchronized (boids) {

					for (DynamicTurtle boid : boids) {
						boid.wrapPosition(WINDOW_WIDTH, WRAP_AROUND_HEIGHT);
						boid.undraw();
						boid.flock(boids, cohesion, alignment, seperation, cohesionRadius, alignmentRadius,
								seperationRadius);
						boid.avoidObstacles(obstacles);
						boid.update(deltaTime);
					}

					for (DynamicTurtle boid : boids) {
						boid.draw();
					}
				}
			}

		}
	}

	public static void main(String[] args) {
		new FlockingSimMainTest();
	}
}
