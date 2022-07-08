package io.ghes.design_patterns.structural.facade;

public class AwesomeVideoConverter {

	private static AwesomeVideoConverter instance;

	public enum FORMAT {
		MP4, AVI
	}

	public static void instantiateVideoConverter() {
		instance = new AwesomeVideoConverter();
	}

	public static AwesomeVideoConverter getInstance() {
		if (instance == null) {
			instantiateVideoConverter();
		}
		return instance;
	}

	public void setConfigs() {
		// set configs here
	}

	public void convert(final String filename, final FORMAT destinationFormat) {
		switch (destinationFormat) {
		case AVI:
			System.out.println("Converted " + filename + " to AVI");
			break;
		case MP4:
			System.out.println("Converted " + filename + " to MP4");
		default:
			break;
		}
	}

	// more methods - for encoding, compression, cropping, scaling...
}
