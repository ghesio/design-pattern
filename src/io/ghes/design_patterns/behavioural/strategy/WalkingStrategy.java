package io.ghes.design_patterns.behavioural.strategy;

public class WalkingStrategy implements Strategy {

	public WalkingStrategy() {
	}

	@Override
	public void execute() {
		System.out.println("Walking around the city sightseeing!");
	}

}
