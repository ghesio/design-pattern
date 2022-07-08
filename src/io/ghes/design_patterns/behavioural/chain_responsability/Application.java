package io.ghes.design_patterns.behavioural.chain_responsability;

import io.ghes.design_patterns.behavioural.chain_responsability.handlers.impl.FiftyEuroHandler;
import io.ghes.design_patterns.behavioural.chain_responsability.handlers.impl.HoundredEuroHandler;
import io.ghes.design_patterns.behavioural.chain_responsability.handlers.impl.TenEuroHandler;
import io.ghes.design_patterns.behavioural.chain_responsability.handlers.impl.TwentyEuroHandler;

public class Application {

	public static void main(final String[] args) {
		final HoundredEuroHandler houndred = new HoundredEuroHandler();
		final FiftyEuroHandler fifty = new FiftyEuroHandler();
		final TwentyEuroHandler twenty = new TwentyEuroHandler();
		final TenEuroHandler ten = new TenEuroHandler();
		houndred.setNextHandler(fifty);
		fifty.setNextHandler(twenty);
		twenty.setNextHandler(ten);

		houndred.dispense(280);
		System.out.println("----");
		houndred.dispense(130);

	}

}
