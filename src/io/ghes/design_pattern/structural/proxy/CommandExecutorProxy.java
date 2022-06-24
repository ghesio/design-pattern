package io.ghes.design_pattern.structural.proxy;

public class CommandExecutorProxy implements CommandExecutor {

	private boolean isAdmin;
	private final CommandExecutor executor;

	public CommandExecutorProxy(final String user, final String pwd) {
		if ("admin".equals(user) && "a5)9=514dfm-".equals(pwd)) {
			this.isAdmin = true;
		}
		this.executor = new CommandExecutorImpl();
	}

	@Override
	public void runCommand(final String cmd) throws Exception {
		if (this.isAdmin) {
			this.executor.runCommand(cmd);
		} else if (cmd.trim().startsWith("rm")) {
			throw new Exception("rm command is not allowed for non-admin users");
		} else {
			this.executor.runCommand(cmd);
		}
	}

}