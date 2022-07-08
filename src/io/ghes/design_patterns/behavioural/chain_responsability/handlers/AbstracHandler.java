/**
 *
 */
package io.ghes.design_patterns.behavioural.chain_responsability.handlers;

/**
 * @author ghesio
 *
 */
public interface AbstracHandler {

	void setNextHandler(final AbstracHandler nextHandler);

	void dispense(final int money);

}
