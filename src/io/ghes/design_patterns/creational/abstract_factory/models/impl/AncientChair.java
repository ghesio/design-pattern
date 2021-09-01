package io.ghes.design_patterns.creational.abstract_factory.models.impl;

import io.ghes.design_patterns.creational.abstract_factory.models.ChairInterface;

public class AncientChair implements ChairInterface {

	@Override
	public void getType() {
		System.out.println("This is an ancient chair!");
	}

}
