package io.ghes.design_patterns.behavioural.visitor;

public class JsonVisitor implements Visitor {

	public JsonVisitor() {
	}

	@Override
	public void exportCircle(final Circle circle) {
		System.out.println("Exporting circle as JSON");
	}

	@Override
	public void exportDot(final Dot dot) {
		System.out.println("Exporting dot as JSON");
	}

	@Override
	public void exportSquare(final Square square) {
		System.out.println("Exporting square as JSON");
	}

}
