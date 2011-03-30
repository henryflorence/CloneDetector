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
	public SortedMap<Integer, MutableInt> lineMap = new TreeMap<Integer, MutableInt>();
	
	public void buildLineMap(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		int lineCount = 0;
		while (bufferedReader.readLine() != null) {
			lineMap.put(lineCount, new MutableInt(lineCount));
			lineCount++;
		}
		
		//extra line for eof
		lineMap.put(lineCount, new MutableInt(lineCount));
	}

	public void insertBlankLine(int lineNo) {
		for (int l : lineMap.subMap(lineNo, lineMap.lastKey()).keySet())
			lineMap.get(l).increment();
	}

	public void addAdditionalLine(int lineNo) {
		
	}
	public void remap(List<CloneLines> inputList) {
		for (CloneLines cloneLines : inputList)
			cloneLines.remapLines(this);
	}
}