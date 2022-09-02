package io.ghes.design_patterns.behavioural.mediator;

public class Application {

	public static void main(final String[] args) {
		final Fan fan = new Fan();
		final PowerSupplier powerSupplier = new PowerSupplier();
		final Button button = new Button();

		final CoolingSystemMediator mediator = new CoolingSystemMediator();
		mediator.setButton(button);
		mediator.setFan(fan);
		mediator.setPowerSupplier(powerSupplier);
		System.out.print("Fan is ");
		System.out.println(fan.isOn() ? "on" : "off");
		button.press();
		System.out.print("Fan is ");
		System.out.println(fan.isOn() ? "on" : "off");
		button.press();
		System.out.print("Fan is ");
		System.out.println(fan.isOn() ? "on" : "off");

	}

}
