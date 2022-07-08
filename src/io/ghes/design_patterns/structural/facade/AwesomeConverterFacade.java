package io.ghes.design_patterns.structural.facade;

import io.ghes.design_patterns.structural.facade.AwesomeVideoConverter.FORMAT;

public class AwesomeConverterFacade {

	public static void convertToMp4(final String filename) {
		final AwesomeVideoConverter conv = AwesomeVideoConverter.getInstance();
		conv.setConfigs();
		conv.convert(filename, FORMAT.MP4);
	}

}
