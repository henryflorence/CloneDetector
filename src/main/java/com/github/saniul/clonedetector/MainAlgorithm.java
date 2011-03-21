package com.github.saniul.clonedetector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Iterator;

public class MainAlgorithm {
	private List<CloneLines> groups;

	public void setCloneGroups(List<CloneLines> groups) {
		this.groups = groups;
	}

	public List<CloneLines> check(String file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		ChainedHashMap<String, Integer> fileLines = new ChainedHashMap<String, Integer>();
		collateLines(bufferedReader, fileLines);

		Map<Integer, Integer> duplicateLines = new HashMap<Integer, Integer>();
		findDuplicates(fileLines, duplicateLines);

		List<CloneLines> lines = findGroups(duplicateLines);

		return lines;
	}

	public List<CloneLines> findGroups(Map<Integer, Integer> duplicateLines) {
		if (duplicateLines.keySet().size() < 2)
			return new LinkedList<CloneLines>();

		SortedSet<Integer> sortedDuplicates = new TreeSet<Integer>(
				duplicateLines.keySet());
		groups = new LinkedList<CloneLines>();

		Iterator<Integer> iterator = sortedDuplicates.iterator();
		int line = iterator.next();
		CloneLines clone = new CloneLines(duplicateLines.get(line), line);

		while (iterator.hasNext()) {
			line = iterator.next();

			if (line == clone.curDupLine()
					&& duplicateLines.get(line) == clone.curOrigLine())
				clone.increment();
			else
				clone = new CloneLines(duplicateLines.get(line), line);
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

	public class CloneLines {
		int origStartLine;
		int dupStartLine;
		int origGroupLength = 1;
		int dupGroupLength = 1;

		public CloneLines(int origStartLine, int dupStartLine) {
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

		public String toString() {
			return (dupStartLine + 1) + "-" + (dupStartLine + dupGroupLength)
					+ ":" + (origStartLine + 1) + "-"
					+ (origStartLine + origGroupLength);
		}

		public int getLength() {
			return origGroupLength;
		}
	}
}
