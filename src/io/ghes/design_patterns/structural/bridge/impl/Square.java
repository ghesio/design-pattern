package io.ghes.design_patterns.structural.bridge.impl;

import io.ghes.design_patterns.structural.bridge.Color;
import io.ghes.design_patterns.structural.bridge.Shape;

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
