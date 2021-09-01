package io.ghes.design_patterns.creational.builder;

public class Application {

	public static void main(String[] args) {
		// instantiate a builder
		HouseBuilder builder = new HouseBuilder();
		// get a default house
		House defaultHouse = builder.getBuiltHouse();
		// get an house with a garage
		House withGarage = builder.buildWindows(8).buildDoors(10).buildGarage().getBuiltHouse();
		// get an house with a pool, lot of rooms but no garage
		House bigger = builder.buildWindows(50).buildRooms(20).buildDoors(10).buildPool().getBuiltHouse();
		System.out.println(defaultHouse);
		System.out.println(withGarage);
		System.out.println(bigger);
	}
}
