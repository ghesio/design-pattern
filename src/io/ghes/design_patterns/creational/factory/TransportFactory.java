/**
 * 
 */
package io.ghes.design_patterns.creational.factory;

import io.ghes.design_patterns.creational.factory.models.Ship;
import io.ghes.design_patterns.creational.factory.models.TransportInterface;
import io.ghes.design_patterns.creational.factory.models.Truck;

public class TransportFactory {

	// based on which type of transport it's needed return it
	// without the need to call single constructors
	public static TransportInterface getTransport(final String type) {
		switch (type) {
		case "truck":
			return new Truck();
		case "ship":
			return new Ship();
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}

}