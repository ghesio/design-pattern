package io.ghes.design_patterns.behavioural.visitor;

public interface Visitor {

	public void exportCircle(final Circle circle);

	public void exportDot(final Dot dot);

	public void exportSquare(final Square square);

}
