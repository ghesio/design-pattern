package io.ghes.design_patterns.behavioural.visitor;

public interface Shape {

	public void accept(final Visitor visitor);

}
