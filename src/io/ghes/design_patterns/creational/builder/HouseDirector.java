package io.ghes.design_patterns.creational.builder;

public class HouseDirector {
	
	private static HouseBuilder builder = new HouseBuilder();
	
	public House getHouseWithAPool() {
		return HouseDirector.builder.buildPool().getBuiltHouse();
	}
	
}
