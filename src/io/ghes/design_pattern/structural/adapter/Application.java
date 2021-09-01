package io.ghes.design_pattern.structural.adapter;

public class Application {

	public static void main(String[] args) {
		SocketAdapter objectAdapter = new ObjectSocketAdapter();
		System.out.println(objectAdapter.get5Volt().getVolts() + " V");
		
		SocketAdapter classAdapter = new ClassSocketAdapter();
		System.out.println(classAdapter.get23Volt().getVolts() + " V");
	}

}
