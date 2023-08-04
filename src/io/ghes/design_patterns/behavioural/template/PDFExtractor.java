package io.ghes.design_patterns.behavioural.template;

public class PDFExtractor extends InformationExtractor {

	public PDFExtractor(final String filePath) {
		super(filePath);
	}

	@Override
	protected void extractData() {
		System.out.println("Extracting data from the PDF with its algorithm");
	}

	@Override
	protected void parseData() {
		System.out.println("Parsing the PDF read data");
	}

	@Override
	protected void hook() {
		System.out.println("Hook implemented by PDFExtractor");
	}

	@Override
	protected void optionalStep() {
		System.out.println("No operation in optional step by PDFExtractor");
	}

}
