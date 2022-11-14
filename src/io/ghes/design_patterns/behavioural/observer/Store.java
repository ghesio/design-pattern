package io.ghes.design_patterns.behavioural.observer;

import java.util.ArrayList;
import java.util.List;

public class Store implements Subject {

	private final String storeName;
	private final List<Observer> customers;
	private String message;
	private boolean changed;
	private final Object mutex = new Object();

	public Store(final String storeName) {
		this.storeName = storeName;
		this.customers = new ArrayList<>();
	}

	@Override
	public void register(final Observer obj) {
		synchronized (this.mutex) {
			if (!this.customers.contains(obj)) {
				this.customers.add(obj);
			}
		}
	}

	@Override
	public void unregister(final Observer obj) {
		synchronized (this.mutex) {
			this.customers.remove(obj);
		}
	}

	@Override
	public void notifyObservers() {
		List<Observer> customersToBeNotified = null;
		// synchronization is used to make sure any observer registered after message is
		// received is not notified
		synchronized (this.mutex) {
			if (!this.changed) {
				return;
			}
			customersToBeNotified = new ArrayList<>(this.customers);
			this.changed = false;
		}
		for (final Observer obj : customersToBeNotified) {
			obj.update();
		}
	}

	@Override
	public String getUpdate(final Observer obj) {
		return this.message;
	}

	public void postMessage(final String msg) {
		System.out.println("-> " + this.storeName + " posted " + msg);
		this.message = msg;
		this.changed = true;
		this.notifyObservers();
		this.message = null;
	}

	@Override
	public String getId() {
		return this.storeName;
	}

}
