package flockingSim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import drawing.Canvas;

public class SimulationGui {

	protected final int WINDOW_WIDTH =  Toolkit.getDefaultToolkit().getScreenSize().width;
	protected final int WINDOW_HEIGHT =  Toolkit.getDefaultToolkit().getScreenSize().height;
	protected int WRAP_AROUND_HEIGHT;
	 
	protected final int SLIDER_MIN = 0;
	protected final int SLIDER_MAX_SPEED = 1000;
	protected final int SLIDER_MAX_COEFF = 100;
	protected final int SLIDER_MAX_RADIUS = 500;
	protected final int BOID_START_POPULATION = 10;
	protected double INIT_COHESION = 1;
	protected double INIT_ALIGNMENT = 0.6;
	protected double INIT_SEPARATION = 1;
	protected int INIT_COHESION_RADIUS = 89;
	protected int INIT_SEPARTION_RADIUS = 50;
	protected int INIT_ALIGNMENT_RADIUS = 80;

	public JButton addBoidButton = new JButton("Add Boid");
	public JButton removeBoidButton = new JButton("Remove Boid");
	public JButton resetButton = new JButton("Reset");

	public JSlider alignmentStrengthSlider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX_COEFF, (int)(INIT_ALIGNMENT*100));
	public JSlider cohesionStrengthSlider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX_COEFF, (int) INIT_COHESION * 100);
	public JSlider separationStrengthSlider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX_COEFF, (int)INIT_SEPARATION*100);
	public JSlider alignmentRadiusSlider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX_RADIUS, INIT_ALIGNMENT_RADIUS);
	public JSlider cohesionRadiusSlider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX_RADIUS, INIT_COHESION_RADIUS);
	public JSlider separationRadiusSlider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX_RADIUS, INIT_SEPARTION_RADIUS);

	public Canvas canvas = new Canvas();

	public void setup() {
		canvas.setBackground(Color.WHITE);
		canvas.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		JFrame frame = new JFrame("Flocking Simulation");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Outer bottom panel with vertical stacking (buttons above sliders)
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JPanel sliderPanel = new JPanel(new GridLayout(2, 3, 10, 10));
		sliderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		addSliderWithLabel(sliderPanel, "Alignment Strength", alignmentStrengthSlider);
		addSliderWithLabel(sliderPanel, "Cohesion Strength", cohesionStrengthSlider);
		addSliderWithLabel(sliderPanel, "Separation Strength", separationStrengthSlider);
		addSliderWithLabel(sliderPanel, "Alignment Radius", alignmentRadiusSlider);
		addSliderWithLabel(sliderPanel, "Cohesion Radius", cohesionRadiusSlider);
		addSliderWithLabel(sliderPanel, "Separation Radius", separationRadiusSlider);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPanel.add(addBoidButton);
		buttonPanel.add(removeBoidButton);
		buttonPanel.add(resetButton);
		bottomPanel.add(buttonPanel);
		bottomPanel.add(sliderPanel);
		
		frame.add(canvas, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
		WRAP_AROUND_HEIGHT = WINDOW_HEIGHT - bottomPanel.getHeight();
	}

	private void addSliderWithLabel(JPanel panel, String label, JSlider slider) {
		JLabel jLabel = new JLabel(label);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing((slider.getMaximum() - slider.getMinimum()) / 2);
		slider.setLabelTable(slider.createStandardLabels(slider.getMaximum() / 2, 0));
		panel.add(jLabel);
		panel.add(slider);
		slider.setPreferredSize(new java.awt.Dimension(500, 50));
	}
}
