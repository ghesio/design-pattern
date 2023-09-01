package io.ghes.design_patterns.behavioural.visitor;

public class XmlVisitor implements Visitor {

	public XmlVisitor() {
	}

	@Override
	public void exportCircle(final Circle circle) {
		System.out.println("Exporting circle as XML");
	}

	@Override
	public void exportDot(final Dot dot) {
		System.out.println("Exporting dot as XML");
	}

	@Override
	public void exportSquare(final Square square) {
		System.out.println("Exporting square as XML");
	}

}
