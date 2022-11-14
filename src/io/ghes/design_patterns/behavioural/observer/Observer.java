package io.ghes.design_patterns.behavioural.observer;

public interface Observer {

	// method to update the observer, used by subject
	public void update();

	// attach with subject to observe
	public void addSubject(Subject sub);

}
