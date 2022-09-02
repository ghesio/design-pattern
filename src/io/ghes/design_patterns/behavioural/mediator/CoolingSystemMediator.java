package io.ghes.design_patterns.behavioural.mediator;

public class CoolingSystemMediator {
	private Button button;
	private Fan fan;
	private PowerSupplier powerSupplier;

	public void setButton(final Button button) {
		this.button = button;
		this.button.setMediator(this);
	}

	public void setFan(final Fan fan) {
		this.fan = fan;
		this.fan.setMediator(this);
	}

	public void setPowerSupplier(final PowerSupplier powerSupplier) {
		this.powerSupplier = powerSupplier;
	}

	public void press() {
		if (this.fan.isOn()) {
			this.fan.turnOff();
		} else {
			this.fan.turnOn();
		}
	}

	public void start() {
		this.powerSupplier.turnOn();
	}

	public void stop() {
		this.powerSupplier.turnOff();
	}

}
