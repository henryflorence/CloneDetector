package com.github.saniul.clonedetector.preprocessor;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class BasicNormalizer extends FileProcessor {
	private final static String identifiers = "[_a-zA-Z][_a-zA-Z0-9]*";
	private final static String numliterals = "\\b\\d+\\.?\\d*[f|d]?\\b";
	
	public String normalizeLine(String line) {
		String result = line;
		result = result.replaceAll(identifiers, " I ");
		result = result.replaceAll(numliterals, " N ");
		result = StringUtils.join(StringUtils.split(result), " ");
		return result;
	}
	@Override
	void doProcess() throws IOException {
		String line = null;
		while ((line = reader.readLine()) != null) {
			writer.write(normalizeLine(line));
			writer.newLine();
		}
	}

}
