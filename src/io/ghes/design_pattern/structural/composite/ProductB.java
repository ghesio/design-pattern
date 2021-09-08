package io.ghes.design_pattern.structural.composite;

public class ProductB implements ItemInterface {
	
	private final double price = 5.5f;
	
	@Override
	public Double getPrice() {
		return price;
	}

}
