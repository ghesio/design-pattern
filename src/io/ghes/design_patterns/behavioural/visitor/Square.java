package io.ghes.design_patterns.behavioural.visitor;

public class Square implements Shape {

	public Square() {
	}

	@Override
	public void accept(final Visitor visitor) {
		visitor.exportSquare(this);
	}

}
