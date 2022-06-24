package io.ghes.design_pattern.structural.proxy;

public class Application {

	public static void main(final String[] args) {
		final CommandExecutor executor = new CommandExecutorProxy("admin", "admin_pwd");
		try {
			executor.runCommand("ls -halt");
			executor.runCommand(" rm -rf /");
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
