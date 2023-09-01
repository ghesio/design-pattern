package io.ghes.design_patterns.behavioural.visitor;

import java.util.List;

public class Application {

	public static void main(final String[] args) {
		// instantiate a list of shapes (any collection works
		final List<Shape> shapes = List.of(new Circle(), new Square(), new Dot());
		// instantiate the XML visitor
		final Visitor xmlVisitor = new XmlVisitor();
		// visit the shapes
		for (final Shape shape : shapes) {
			shape.accept(xmlVisitor);
		}
		System.out.println("-----------------");
		// instantiate the json visitor
		final Visitor jsonVisitor = new JsonVisitor();
		// visit the shapes
		for (final Shape shape : shapes) {
			shape.accept(jsonVisitor);
		}
	}

}
