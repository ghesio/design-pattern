package io.ghes.design_patterns.creational.singleton;

public class Application {
	
	public static void main(String[] args) {
		// eager
		EagerSingleton eager_1 = EagerSingleton.getInstance();
		EagerSingleton eager_2 = EagerSingleton.getInstance();
		System.out.println(eager_1.hashCode() == eager_2.hashCode());
		// static
		StaticSingleton static_1 = StaticSingleton.getInstance();
		StaticSingleton static_2 = StaticSingleton.getInstance();
		System.out.println(static_1.hashCode() == static_2.hashCode());
		// lazy
		LazySingleton lazy_1 = LazySingleton.getInstance();
		LazySingleton lazy_2 = LazySingleton.getInstance();
		System.out.println(lazy_1.hashCode() == lazy_2.hashCode());
		// lazy synchronized
		LazySynchronizedSingleton lazy_3 = LazySynchronizedSingleton.getInstance();
		LazySynchronizedSingleton lazy_4 = LazySynchronizedSingleton.getInstance();
		System.out.println(lazy_3.hashCode() == lazy_4.hashCode());
	}

}
