package io.ghes.design_pattern.structural.bridge;

public abstract class Shape {
	
	protected Color color;
	
	public Shape(final Color c) {
		this.color = c;
	}
	
	public abstract void draw();

}
