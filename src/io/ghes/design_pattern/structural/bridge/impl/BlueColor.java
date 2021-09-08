package io.ghes.design_pattern.structural.bridge.impl;

import io.ghes.design_pattern.structural.bridge.Color;

public class BlueColor implements Color {

	@Override
	public void paint() {
		System.out.println("blue");
	}

}
