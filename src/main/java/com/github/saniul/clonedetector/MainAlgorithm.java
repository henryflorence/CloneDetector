package com.github.saniul.clonedetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Iterator;

public class MainAlgorithm {
	private List<DupLines> groups;

	public void setCloneGroups(List<DupLines> groups) {
		this.groups = groups;
	}

	public List<CloneLines> check(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		ChainedHashMap<String, Integer> fileLines = new ChainedHashMap<String, Integer>();
		collateLines(bufferedReader, fileLines);

		Map<Integer, Integer> duplicateLines = new HashMap<Integer, Integer>();
		findDuplicates(fileLines, duplicateLines);

		List<DupLines> lines = findGroups(duplicateLines);
		List<CloneLines> cloneLines= new LinkedList<CloneLines>();
		
		for(DupLines line : lines) cloneLines.add(line.toCloneLines());
		return cloneLines;
	}

	public List<DupLines> findGroups(Map<Integer, Integer> duplicateLines) {
		if (duplicateLines.keySet().size() < 2)
			return new LinkedList<DupLines>();

		SortedSet<Integer> sortedDuplicates = new TreeSet<Integer>(
				duplicateLines.keySet());
		groups = new LinkedList<DupLines>();

		Iterator<Integer> iterator = sortedDuplicates.iterator();
		int line = iterator.next();
		DupLines clone = new DupLines(duplicateLines.get(line), line);

		while (iterator.hasNext()) {
			line = iterator.next();

			if (line == clone.curDupLine()
					&& duplicateLines.get(line) == clone.curOrigLine())
				clone.increment();
			else
				clone = new DupLines(duplicateLines.get(line), line);
		}

		return groups;
	}

	private void findDuplicates(ChainedHashMap<String, Integer> fileLines,
			Map<Integer, Integer> duplicateLines) {
		for (String line : fileLines.keySet()) {
			List<Integer> dups = fileLines.getChain(line);
			if (dups.size() > 1)
				for (int i = 1; i < dups.size(); i++)
					duplicateLines.put(dups.get(i), dups.get(0));
		}
	}

	public void collateLines(BufferedReader bufferedReader,
			Map<String, Integer> fileLines) throws IOException {
		String line;
		int lineNo = 0;
		while ((line = bufferedReader.readLine()) != null)
			fileLines.put(line, lineNo++);
	}

	public class DupLines {
		int origStartLine;
		int dupStartLine;
		int origGroupLength = 1;
		int dupGroupLength = 1;

		public DupLines(int origStartLine, int dupStartLine) {
			this.origStartLine = origStartLine;
			this.dupStartLine = dupStartLine;
			groups.add(this);
		}

		public void increment() {
			origGroupLength++;
			dupGroupLength++;
		}

		public int curOrigLine() {
			return origStartLine + origGroupLength;
		}

		public int curDupLine() {
			return dupStartLine + dupGroupLength;
		}

		public int getLength() {
			return origGroupLength;
		}
		
		public CloneLines toCloneLines() {
			return new CloneLines(origStartLine, dupStartLine, origGroupLength, dupGroupLength);
		}
	}
}
