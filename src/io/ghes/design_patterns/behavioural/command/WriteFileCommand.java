package io.ghes.design_patterns.behavioural.command;

public class WriteFileCommand implements Command {

	private final FileSystemReceiver fileSystem;

	public WriteFileCommand(final FileSystemReceiver fs) {
		this.fileSystem = fs;
	}

	@Override
	public void execute() {
		this.fileSystem.writeFile();
	}

}