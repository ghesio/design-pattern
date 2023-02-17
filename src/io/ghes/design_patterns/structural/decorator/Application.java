package io.ghes.design_patterns.structural.decorator;

public class Application {

	public static void main(String[] args) {
		final String emailAddress = "bogus@binted.com";
		final String slackUser = "@binted";
		final String phoneNumber = "+1 515-JAVA";

		Notifier notifier = new EmailNotifier(emailAddress);
		notifier.notifyMessage();
		notifier = new SlackNotifier(notifier, slackUser);
		notifier.notifyMessage();
		notifier = new SmsNotifier(notifier, phoneNumber);
		notifier.notifyMessage();
	}

}
