package io.ghes.design_patterns.behavioural.visitor;

public class Circle implements Shape {

	public Circle() {
	}

	@Override
	public void accept(final Visitor visitor) {
		visitor.exportCircle(this);

	}

}
