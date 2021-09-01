package io.ghes.design_patterns.creational.singleton;

public class LazySynchronizedSingleton {

	private static LazySynchronizedSingleton instance;

	private LazySynchronizedSingleton() {}

	public static LazySynchronizedSingleton getInstance(){
	    if(instance == null){
	        synchronized (LazySynchronizedSingleton.class) {
	            if(instance == null){
	                instance = new LazySynchronizedSingleton();
	            }
	        }
	    }
	    return instance;
	}
}