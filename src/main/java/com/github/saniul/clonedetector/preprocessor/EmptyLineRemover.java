package com.github.saniul.clonedetector.preprocessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class EmptyLineRemover extends FileProcessor {

	@Override
	public File process() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					originalFile));

			BufferedWriter writer = new BufferedWriter(new FileWriter(
					processedFile));

			String line = null;
			int curLineCount = 0;
			int originalLineCount = 0;
			while ((line = reader.readLine()) != null) {
				if (StringUtils.trimToNull(line) == null) {
					for (int l : lineMap
							.subMap(curLineCount, lineMap.lastKey()).keySet()) {
						lineMap.get(l).increment();
					}
				} else {
					writer.write(line);
					writer.newLine();
					curLineCount++;
				}
				originalLineCount++;
			}
			reader.close();
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processedFile;
	}

}
