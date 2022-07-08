package io.ghes.design_patterns.structural.composite;

public class Application {

	public static void main(String[] args) {
		Box bigBox = new Box();
		bigBox.addItem(new ProductA());
		bigBox.addItem(new ProductB());
		Box smallBox = new Box();
		smallBox.addItem(new ProductA());
		bigBox.addItem(smallBox);
		System.out.println(bigBox.getPrice());
	}

}
