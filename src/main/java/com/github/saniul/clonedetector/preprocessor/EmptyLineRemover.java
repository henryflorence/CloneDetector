package com.github.saniul.clonedetector.preprocessor;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class EmptyLineRemover extends FileProcessor {

	@Override
	public void doProcess() throws IOException {
		String line = null;
		int curLineCount = 0;
		int originalLineCount = 0;
		while ((line = reader.readLine()) != null) {
			if (StringUtils.trimToNull(line) == null) {
				lineMap.insertBlankLine(curLineCount);
			} else {
				writer.write(line);
				writer.newLine();
				curLineCount++;
			}
			originalLineCount++;
		}
	}

}
