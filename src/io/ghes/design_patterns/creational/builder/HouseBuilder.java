package io.ghes.design_patterns.creational.builder;

public class HouseBuilder {

	private House house = new House();
	
	public HouseBuilder buildWindows(Integer number) {
		this.house.setNumberOfWindows(number);
		return this;
	}
	
	public HouseBuilder buildRooms(Integer number) {
		this.house.setNumberOfRooms(number);
		return this;
	}
	
	public HouseBuilder buildDoors(Integer number) {
		this.house.setNumberOfDoors(number);
		return this;
	}
	
	public HouseBuilder buildPool() {
		this.house.setHasPool(true);
		return this;
	}
	
	public HouseBuilder buildGarage() {
		this.house.setHasGarage(true);
		return this;
	}
	
	// reset method
	public void reset() {
		this.house = new House();
	}
	
	// get created house and reset
	public House getBuiltHouse() {
		House toReturn = this.house;
		this.reset();
		return toReturn;
	}
	
}
