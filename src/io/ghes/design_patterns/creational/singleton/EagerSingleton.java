package io.ghes.design_patterns.creational.singleton;

public class EagerSingleton {

	private static EagerSingleton instance = new EagerSingleton();

	// private constructor to avoid client applications to use constructor
	private EagerSingleton() {
	}

	public static EagerSingleton getInstance() {
		return instance;
	}

}
