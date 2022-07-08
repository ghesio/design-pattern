package io.ghes.design_patterns.structural.adapter;

public class ObjectSocketAdapter implements SocketAdapter {

	// Using composition for adapter pattern
	private Socket socket = new Socket();

	@Override
	public Volt get230Volt() {
		return socket.getVolt();
	}

	@Override
	public Volt get23Volt() {
		Volt v = socket.getVolt();
		return convertVolt(v, 10);
	}

	@Override
	public Volt get10Volt() {
		Volt v = socket.getVolt();
		return convertVolt(v, 23);
	}

	@Override
	public Volt get5Volt() {
		Volt v = socket.getVolt();
		return convertVolt(v, 46);
	}

	private Volt convertVolt(Volt v, int i) {
		return new Volt(v.getVolts() / i);
	}

}
