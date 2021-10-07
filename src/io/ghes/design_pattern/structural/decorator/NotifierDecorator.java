package io.ghes.design_pattern.structural.decorator;

public abstract class NotifierDecorator implements Notifier {
	private final Notifier decoratedNotifier;
	
	protected NotifierDecorator(Notifier notifier) {
		this.decoratedNotifier = notifier;
	}
	
	@Override
	public void notifyMessage() {
		this.decoratedNotifier.notifyMessage();
	}
	
}
