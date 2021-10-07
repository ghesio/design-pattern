package io.ghes.design_pattern.structural.decorator;

public class SmsNotifier extends NotifierDecorator {

	private String phoneNumber;
	
	public SmsNotifier(Notifier notifier, final String phoneNumber) {
		super(notifier);
		this.phoneNumber = phoneNumber;
	}
	
	
	@Override
	public void notifyMessage() {
		System.out.println("Sending sms to " + phoneNumber);
	}

}
