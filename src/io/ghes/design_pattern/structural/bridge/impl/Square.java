package io.ghes.design_pattern.structural.bridge.impl;

import io.ghes.design_pattern.structural.bridge.Color;
import io.ghes.design_pattern.structural.bridge.Shape;

public class Square extends Shape {

	public Square(Color c) {
		super(c);
	}

	@Override
	public void draw() {
		System.out.print("Square filled with color ");
		color.paint();
	}

}
