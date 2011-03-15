package com.github.saniul.clonedetector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class MainAlgorithm {
	public CloneLines[] check(String file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		ChainedHashMap<String,Integer> fileLines = new ChainedHashMap<String,Integer>();
		collateLines(bufferedReader, fileLines);
		
		Map<Integer,Integer> duplicateLines = new HashMap<Integer,Integer>();
		findDuplicates(fileLines, duplicateLines);
			
		ArrayList<CloneLines> lines = findGroups(duplicateLines);
		
		return lines.toArray(new CloneLines[0]);
	}
	public ArrayList<CloneLines> findGroups(Map<Integer,Integer> duplicateLines) {
		ArrayList<CloneLines> groups = new ArrayList<CloneLines>();
		
		for(int line : new TreeSet<Integer>(duplicateLines.keySet())) {
			
			
		}
			
		return groups;
	}
	private void findDuplicates(ChainedHashMap<String,Integer> fileLines, Map<Integer,Integer> duplicateLines) {
		for(String line : fileLines.keySet()) {
			List<Integer> dups = fileLines.getChain(line);
			if(dups.size() > 1) 
				for(int i=1; i < dups.size(); i++)
						duplicateLines.put(dups.get(i),dups.get(0));
			
		}
	}
	public void collateLines(BufferedReader bufferedReader,
			ChainedHashMap<String, Integer> fileLines) throws IOException {
		String line;
		int lineNo = 0;
		while((line = bufferedReader.readLine()) != null)
			fileLines.put(line, lineNo++);
	}
	public class CloneLines {
		int origStartLine;
		int dupStartLine;
		int origGroupLength = 0;
		int dupGroupLength = 0;
		
		public CloneLines(int origStartLine, int dupStartLine) {
			this.origStartLine = origStartLine; 
			this.dupStartLine = dupStartLine; 
		}
		public void incrOrig() {
			origGroupLength++;
		}
		public void incrDup() {
			dupGroupLength++;	
		}
		public String displayMatch() { return null; } 
	}
}
