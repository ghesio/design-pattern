package io.ghes.design_patterns.structural.bridge;

import io.ghes.design_patterns.structural.bridge.impl.BlueColor;
import io.ghes.design_patterns.structural.bridge.impl.Circle;
import io.ghes.design_patterns.structural.bridge.impl.RedColor;
import io.ghes.design_patterns.structural.bridge.impl.Square;

public class Application {

	public static void main(String[] args) {
		Shape redSquare = new Square(new RedColor());
		redSquare.draw();
		Shape blueCircle = new Circle(new BlueColor());
		blueCircle.draw();
	}

}
