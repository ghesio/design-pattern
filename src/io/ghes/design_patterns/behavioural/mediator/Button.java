package io.ghes.design_patterns.behavioural.mediator;

public class Button {
	private CoolingSystemMediator mediator;

	public Button() {
	}

	public void press() {
		System.out.println("Pressing the button");
		this.mediator.press();
	}

	public void setMediator(final CoolingSystemMediator mediator) {
		this.mediator = mediator;
	}

}