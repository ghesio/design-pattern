package io.ghes.design_patterns.behavioural.strategy;

public class Application {

	public static void main(final String[] args) {
		// instantiate the context
		final Context context = new Context();
		// set the first strategy
		context.setStrategy(new WalkingStrategy());
		// execute the strategy
		context.execute();
		// set another strategy
		context.setStrategy(new DrivingStrategy());
		// execute the strategy
		context.execute();
	}

}
