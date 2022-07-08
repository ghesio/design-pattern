package io.ghes.design_patterns.structural.flyweight;

import java.util.Random;

public class Application {
	private static final String colors[] = { "Red", "Green", "Blue", "White", "Black" };
	private static final Random r = new Random();

	public static void main(final String[] args) {

		// generate 10 circles without flyweight and draw them
		for (int i = 0; i < 10; i++) {
			final Circle circle = new Circle(getRandomColor());
			circle.setX(r.nextInt(100));
			circle.setY(r.nextInt(100));
			circle.setRadius(r.nextInt(50));
			circle.draw();
		}
		System.out.println("----------");
		// generate 10 circles with flyweight and draw them
		for (int i = 0; i < 10; i++) {
			final Circle circle = ShapeFactory.getCircle(getRandomColor());
			circle.setX(r.nextInt(100));
			circle.setY(r.nextInt(100));
			circle.setRadius(r.nextInt(50));
			circle.draw();
		}
	}

	private static String getRandomColor() {
		return colors[r.nextInt(colors.length)];
	}

}
