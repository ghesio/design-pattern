/**
 * 
 */
package io.ghes.design_patterns.creational.factory;

public class Application {

	public static void main(String[] args) {
		// get a truck
		TransportFactory.getTransport("truck").sendPackage();
		// get a ship
		TransportFactory.getTransport("ship").sendPackage();
	}

}
