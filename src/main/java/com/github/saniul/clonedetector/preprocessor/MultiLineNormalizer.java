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
			StringBuffer out = new StringBuffer("");
			int curLength = 0;
			
			for(int i = 0; i < line.length(); i++) {
				if(curLength > 0 && 
					(line.charAt(i) == '{' || line.charAt(i) == '}')) {
					out.append('\n');
					curLength = 0;
					//lineMap.insertBlankLine(curLineCount);
				}
				out.append(line.charAt(i));
				curLength++;
			}
			line = out.toString();
			
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
