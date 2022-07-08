package io.ghes.design_patterns.structural.adapter;

public class Socket {

	public Volt getVolt(){
		return new Volt(230);
	}

}