package io.ghes.design_patterns.structural.bridge.impl;

import io.ghes.design_patterns.structural.bridge.Color;

public class RedColor implements Color {

	@Override
	public void paint() {
		System.out.println("red");
	}

}
