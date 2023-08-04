package io.ghes.design_patterns.behavioural.template;

public class CSVExtractor extends InformationExtractor {

	public CSVExtractor(final String filePath) {
		super(filePath);
	}

	@Override
	protected void extractData() {
		System.out.println("Extracting data from CSV with its algorithm");
	}

	@Override
	protected void parseData() {
		System.out.println("Parsing the CSV read data");
	}

	@Override
	protected void optionalStep() {
		super.optionalStep();
		System.out.println("Extending the optional step");
	}

}
