package io.ghes.design_patterns.behavioural.strategy;

public class DrivingStrategy implements Strategy {

	public DrivingStrategy() {
	}

	@Override
	public void execute() {
		System.out.println("Driving because in a hurry.");
	}

}
