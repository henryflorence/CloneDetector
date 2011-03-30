package com.github.saniul.clonedetector.preprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.mutable.MutableInt;

import com.github.saniul.clonedetector.CloneLines;

public class LineMap {
	private SortedMap<Integer, MutableInt> lineMap = new TreeMap<Integer, MutableInt>();
	
	public void buildLineMap(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		int lineCount = 0;
		while (bufferedReader.readLine() != null) {
			lineMap.put(lineCount, new MutableInt(lineCount));
			lineCount++;
		}
	}

	public void insertBlankLine(EmptyLineRemover emptyLineRemover, int lineNo) {
		for (int l : lineMap.subMap(lineNo, lineMap.lastKey()).keySet())
			lineMap.get(l).increment();
	}

	public CloneLines remapLines(FileProcessor fileProcessor, CloneLines cloneLines) {
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

	public List<CloneLines> remap(FileProcessor fileProcessor, List<CloneLines> inputlist) {
		List<CloneLines> list = new ArrayList<CloneLines>();
		for (CloneLines cloneLines : inputlist) {
			CloneLines newCloneLines = remapLines(fileProcessor, cloneLines);
			list.add(newCloneLines);
		}
		return list;
	}
}