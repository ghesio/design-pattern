package io.ghes.design_pattern.structural.flyweight;

public class Circle {
	private final String color;
	private int x;
	private int y;
	private int radius;

	public Circle(final String color) {
		System.out.println("Creating circle of color : " + color);
		this.color = color;
	}

	public void setX(final int x) {
		this.x = x;
	}

	public void setY(final int y) {
		this.y = y;
	}

	public void setRadius(final int radius) {
		this.radius = radius;
	}

	public void draw() {
		System.out.println(
				"[Color: " + this.color + ", x :" + this.x + ", y:" + this.y + ", radius:" + this.radius + "]");
	}
}