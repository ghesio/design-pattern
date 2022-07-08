package io.ghes.design_patterns.structural.composite;

public class ProductA implements ItemInterface {
	
	private final double price = 10.5d;
	
	@Override
	public Double getPrice() {
		return price;
	}

}
