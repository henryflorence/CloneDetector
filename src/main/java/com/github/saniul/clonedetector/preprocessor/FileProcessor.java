package com.github.saniul.clonedetector.preprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang.mutable.MutableInt;

import com.github.saniul.clonedetector.CloneLines;

public abstract class FileProcessor {
	TreeMap<Integer, MutableInt> lineMap = new TreeMap<Integer, MutableInt>();
	File originalFile;
	File processedFile;

	public void setFile (File originalFile) {
		try {
			this.originalFile = originalFile;
			buildLineMap();
			processedFile = File.createTempFile("clonedetector", ".tmp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		processedFile.deleteOnExit();
	}

	public File getOriginalFile() {
		return this.originalFile;
	}

	public File getProcessedFile() {
		return this.processedFile;
	}

	abstract public File process();

	public void buildLineMap() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					originalFile));
			String line = null;
			int lineCount = 0;
			while ((line = bufferedReader.readLine()) != null) {
				lineMap.put(lineCount, new MutableInt(lineCount));
				lineCount++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private CloneLines remapLines(CloneLines cloneLines) {
		int newOrigStartLine = lineMap.get(cloneLines.getOrigStartLine())
				.intValue();
		int newDupStartLine = lineMap.get(cloneLines.getDupStartLine())
				.intValue();
		// int newCurDupLine = linemap.get(cloneLines.curDupLine()).intValue();
		int newCurOrigLine = lineMap.get(cloneLines.curOrigLine()).intValue();

		CloneLines n = new CloneLines(newOrigStartLine, newDupStartLine);
		n.setLength(newCurOrigLine - newOrigStartLine);
		return n;

	}

	public List<CloneLines> remap(List<CloneLines> inputlist) {
		List<CloneLines> list = new ArrayList<CloneLines>();
		for (CloneLines cloneLines : inputlist) {
			CloneLines newCloneLines = remapLines(cloneLines);
			list.add(newCloneLines);
		}
		return list;
	}
}
