package io.ghes.design_patterns.creational.abstract_factory.models.impl;

import io.ghes.design_patterns.creational.abstract_factory.models.TableInterface;

public class ModernTable implements TableInterface {

	@Override
	public void getType() {
		System.out.println("This is a modern table!");
	}

}
