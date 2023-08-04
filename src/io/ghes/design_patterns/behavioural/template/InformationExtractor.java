package io.ghes.design_patterns.behavioural.template;

public abstract class InformationExtractor {

	private final String filePath;

	protected InformationExtractor(final String filePath) {
		this.filePath = filePath;
	}

	// ----

	public void runExtraction() {
		this.openFile();
		this.extractData();
		this.parseData();
		this.analyzeData();
		this.sendDataToIndex();
		this.optionalStep();
		this.hook();
		this.closeFile();
	}

	// ----

	protected void openFile() {
		System.out.println("-----------------");
		System.out.println("Opening file @ " + this.filePath);
	}

	protected abstract void extractData();

	protected abstract void parseData();

	protected void analyzeData() {
		System.out.println("Analyzing data");
	}

	protected void sendDataToIndex() {
		System.out.println("Sending data to index");
	}

	protected void optionalStep() {
		System.out.println("Some operation in optional step");
	}

	protected void hook() {
	}

	protected void closeFile() {
		System.out.println("Closing file @ " + this.filePath);
	}
}
