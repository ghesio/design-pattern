package io.ghes.design_patterns.behavioural.command;

public class OpenFileCommand implements Command {

	private final FileSystemReceiver fileSystem;

	public OpenFileCommand(final FileSystemReceiver fs) {
		this.fileSystem = fs;
	}

	@Override
	public void execute() {
		// open command is forwarding request to openFile method
		this.fileSystem.openFile();
	}

}