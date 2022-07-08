package io.ghes.design_patterns.behavioural.chain_responsability.handlers.impl;

import io.ghes.design_patterns.behavioural.chain_responsability.handlers.AbstracHandler;

public class TenEuroHandler implements AbstracHandler {

	@Override
	public void setNextHandler(final AbstracHandler nextHandler) {
		//
	}

	@Override
	public void dispense(final int money) {
		final int num = money / 10;
		System.out.println("Dispensing " + num + " 10€ bill(s)");
	}

}
