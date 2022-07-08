package io.ghes.design_patterns.structural.bridge.impl;

import io.ghes.design_patterns.structural.bridge.Color;

public class BlueColor implements Color {

	@Override
	public void paint() {
		System.out.println("blue");
	}

}
