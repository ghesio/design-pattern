package io.ghes.design_pattern.structural.bridge.impl;

import io.ghes.design_pattern.structural.bridge.Color;
import io.ghes.design_pattern.structural.bridge.Shape;

public class Circle extends Shape {

	public Circle(Color c) {
		super(c);
	}

	@Override
	public void draw() {
		System.out.print("Circle filled with color ");
		color.paint();
	}

}
