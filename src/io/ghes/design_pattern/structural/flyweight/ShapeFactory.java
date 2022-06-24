package io.ghes.design_pattern.structural.flyweight;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {

	private static final Map<String, Circle> circleMap = new HashMap<>();

	public static Circle getCircle(final String color) {
		return circleMap.computeIfAbsent(color, k -> new Circle(color));
	}

}
