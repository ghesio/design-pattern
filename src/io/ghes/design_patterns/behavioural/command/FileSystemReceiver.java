package io.ghes.design_patterns.behavioural.command;

public interface FileSystemReceiver {

	void openFile();

	void writeFile();

	void closeFile();

}