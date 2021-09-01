package io.ghes.design_pattern.structural.adapter;

public class Socket {

	public Volt getVolt(){
		return new Volt(230);
	}

}