package io.ghes.design_patterns.structural.adapter;

public interface SocketAdapter {

	public Volt get230Volt();

	public Volt get23Volt();

	public Volt get10Volt();

	public Volt get5Volt();

}
