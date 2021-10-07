package io.ghes.design_pattern.structural.decorator;

public class EmailNotifier implements Notifier {

	private String email;
	
	public EmailNotifier(final String email) {
		this.email = email;
	}
	
	@Override
	public void notifyMessage() {
		System.out.println("Sending email notification to " + email);
	}

}
