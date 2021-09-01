package io.ghes.design_patterns.creational.abstract_factory;

import io.ghes.design_patterns.creational.abstract_factory.factories.AncientFactory;
import io.ghes.design_patterns.creational.abstract_factory.factories.FornitureAbstractFactory;
import io.ghes.design_patterns.creational.abstract_factory.factories.ModernFactory;

public class Application {

	private static FornitureAbstractFactory factory;

	private static void instantiateFactory(final String type) {
		switch (type) {
		case "modern":
			Application.factory = new ModernFactory();
			break;
		case "ancient":
			Application.factory = new AncientFactory();
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}

	public static void main(String[] args) {
		// we read from somewhere (like from a property) which type of factory I want
		final String factoryType = "modern";
		// we instantiate the factory
		Application.instantiateFactory(factoryType);
		// we then create a chair and a table
		Application.factory.getChair().getType();
		Application.factory.getTable().getType();

	}

}
