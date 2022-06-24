package io.ghes.design_pattern.structural.proxy;

import java.io.IOException;

public class CommandExecutorImpl implements CommandExecutor {

	@Override
	public void runCommand(final String cmd) throws IOException {
		// actually execute the command
		System.out.println("'" + cmd + "' command executed");
	}

}
