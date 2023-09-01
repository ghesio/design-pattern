package io.ghes.design_patterns.behavioural.visitor;

public class Dot implements Shape {

	public Dot() {
	}

	@Override
	public void accept(final Visitor visitor) {
		visitor.exportDot(this);

	}

}
