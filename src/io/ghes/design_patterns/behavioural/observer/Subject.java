package io.ghes.design_patterns.behavioural.observer;

public interface Subject {

	// methods to register and unregister observers
	public void register(Observer obj);

	public void unregister(Observer obj);

	// method to notify observers of change
	public void notifyObservers();

	// method to get updates from subject
	public String getUpdate(Observer obj);

	public String getId();

}
