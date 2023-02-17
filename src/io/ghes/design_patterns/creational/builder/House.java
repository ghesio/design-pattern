package io.ghes.design_patterns.creational.builder;

public class House {

	// some example fields with default values
	private Integer numberOfWindows = 4;
	private Integer numberOfRooms = 2;
	private Integer numberOfDoors = 1;
	private Boolean hasGarage = false;
	private Boolean hasPool = false;

	public House() {
		// empty constructor, properties will be set by HouseBuilder
	}

	public void setNumberOfWindows(Integer numberOfWindows) {
		this.numberOfWindows = numberOfWindows;
	}

	public void setNumberOfRooms(Integer numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public void setNumberOfDoors(Integer numberOfDoors) {
		this.numberOfDoors = numberOfDoors;
	}

	public void setHasGarage(Boolean hasGarage) {
		this.hasGarage = hasGarage;
	}

	public void setHasPool(Boolean hasPool) {
		this.hasPool = hasPool;
	}

	@Override
	public String toString() {
		return "House [numberOfWindows=" + numberOfWindows + ", numberOfRooms=" + numberOfRooms + ", numberOfDoors="
				+ numberOfDoors + ", hasGarage=" + hasGarage + ", hasPool=" + hasPool + "]";
	}

}
