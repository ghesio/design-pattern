package io.ghes.design_patterns.structural.adapter;

public class ClassSocketAdapter extends Socket implements SocketAdapter{

	@Override
	public Volt get230Volt() {
		return getVolt();
	}

	@Override
	public Volt get23Volt() {
		Volt v= getVolt();
		return convertVolt(v,10);
	}

	@Override
	public Volt get10Volt() {
		Volt v= getVolt();
		return convertVolt(v,23);
	}
	
	@Override
	public Volt get5Volt() {
		Volt v= getVolt();
		return convertVolt(v,46);
	}
	
	private Volt convertVolt(Volt v, int i) {
		return new Volt(v.getVolts()/i);
	}

}