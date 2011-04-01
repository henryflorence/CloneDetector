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
	private List<MutableInt> lineMap = new ArrayList<MutableInt>();
	
	public void buildLineMap(File file) throws IOException {
		 buildLineMap(new BufferedReader(new FileReader(file)));
	}
	public void buildLineMap(BufferedReader bufferedReader) throws IOException {
		lineMap = new ArrayList<MutableInt>();
		int lineCount = 0;
		while (bufferedReader.readLine() != null)
			lineMap.add(new MutableInt(lineCount++));
		
		//extra line for eof
		lineMap.add(new MutableInt(lineCount));
	}

	public void insertBlankLine(int lineNo) {
		while(lineNo < lineMap.size())
			lineMap.get(lineNo++).increment();
	}

	public void addAdditionalLine(int lineNo) {
		if(++lineNo < lineMap.size())
			while(lineNo < lineMap.size())
				lineMap.get(lineNo++).decrement();
		
		int lastLine = lineMap.get(lineMap.size() - 1).intValue() + 1;
		lineMap.add(new MutableInt(lastLine));
	}
	public void remap(List<CloneLines> inputList) {
		for (CloneLines cloneLines : inputList)
			cloneLines.remapLines(this);
	}
	public int translate(int processedLine) {
		return lineMap.get(processedLine).intValue();
	}
}