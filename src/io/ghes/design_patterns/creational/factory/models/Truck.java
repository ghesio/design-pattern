package io.ghes.design_patterns.creational.factory.models;

public class Truck implements TransportInterface {

	// field, getters and setters

	@Override
	public void sendPackage() {
		System.out.println("Sending package via truck!");
	}

}
