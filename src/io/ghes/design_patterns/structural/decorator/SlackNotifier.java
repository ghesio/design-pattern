package io.ghes.design_patterns.structural.decorator;

public class SlackNotifier extends NotifierDecorator {

	private String slackUser;
	
	public SlackNotifier(Notifier notifier, final String slackUser) {
		super(notifier);
		this.slackUser = slackUser;
	}
	
	@Override
	public void notifyMessage() {
		System.out.println("Sending slack notification to " + slackUser);
	}

}
