package io.ghes.design_patterns.behavioural.strategy;

public class Context {

	private Strategy strategy;

	public Context() {
	}

	public void setStrategy(final Strategy strategy) {
		this.strategy = strategy;
	}

	public void execute() {
		this.strategy.execute();
	}

}
