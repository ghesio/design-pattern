/**
 *
 */
package io.ghes.design_patterns.behavioural.observer;

import java.util.ArrayList;
import java.util.List;

public class Customer implements Observer {

	private final String name;
	private List<Subject> stores;

	public Customer(final String customerName) {
		this.name = customerName;
	}

	@Override
	public void update() {
		for (final Subject store : this.stores) {
			final String msg = store.getUpdate(this);
			if (msg == null) {
				System.out.println(this.name + " has no new message from " + store.getId());
			} else {
				System.out.println("<- " + this.name + " received " + msg);
			}
		}
	}

	@Override
	public void addSubject(final Subject subject) {
		if (this.stores == null) {
			this.stores = new ArrayList<>();
		}
		this.stores.add(subject);
	}

}
