package com.github.saniul.clonedetector.preprocessor;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class MultiLineNormalizer extends FileProcessor {
	private boolean insideComment = false;
	
	@Override
	public void doProcess() throws IOException {
		String line = null;
		int curLineCount = 0;
		while ((line = reader.readLine()) != null) {
			if (line.contains("/*")) {
				insideComment = true;
				writer.write(line.substring(0,line.indexOf("/*")));
			} 
			if (line.contains("*/")) {
				insideComment = false;
				line = line.substring(line.indexOf("*/")+2,line.length());
			}
			if(!insideComment) writer.write(line);
			writer.newLine();
			curLineCount++;
		}
	}

}
