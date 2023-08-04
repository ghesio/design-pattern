package io.ghes.design_patterns.behavioural.template;

import java.security.InvalidParameterException;

public class Application {

	public static void main(final String[] args) {
		// let's extract data from PDF file
		final String pathToPdfFile = "./my_pdf.pdf";
		extractDataBasedOnFileType(pathToPdfFile);
		// let's extract data from PDF file
		final String pathToCsvFile = "./my_csv.csv";
		extractDataBasedOnFileType(pathToCsvFile);

	}

	private static void extractDataBasedOnFileType(final String filePath) {
		InformationExtractor extractor = null;
		if (filePath.endsWith("pdf")) {
			extractor = new PDFExtractor(filePath);
		}
		if (filePath.endsWith("csv")) {
			extractor = new CSVExtractor(filePath);
		}
		if (extractor == null) {
			throw new InvalidParameterException("Unknown file type");
		}
		extractor.runExtraction();
	}

}
