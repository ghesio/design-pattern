package io.ghes.design_patterns.behavioural.mediator;

public class Fan {
	private CoolingSystemMediator mediator;
	private boolean isOn = false;

	public void setMediator(final CoolingSystemMediator mediator) {
		this.mediator = mediator;
	}

	public boolean isOn() {
		return this.isOn;
	}

	public void turnOn() {
		this.mediator.start();
		this.isOn = true;
	}

	public void turnOff() {
		this.isOn = false;
		this.mediator.stop();
	}
}