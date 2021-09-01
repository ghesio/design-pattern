package io.ghes.design_patterns.creational.abstract_factory.models.impl;

import io.ghes.design_patterns.creational.abstract_factory.models.TableInterface;

public class AncientTable implements TableInterface {

	@Override
	public void getType() {
		System.out.println("This is an ancient table!");
	}

}
