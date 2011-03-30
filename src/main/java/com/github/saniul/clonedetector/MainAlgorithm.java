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

		ChainedHashMap<Integer, Integer> duplicateLines = new ChainedHashMap<Integer, Integer>();
		findDuplicates(fileLines, duplicateLines);

		List<DupLines> lines = findGroups(duplicateLines);
		
		List<CloneLines> cloneLines = new LinkedList<CloneLines>();
		for(DupLines line : lines) cloneLines.add(line.toCloneLines());
		
		return cloneLines;
	}

	public List<DupLines> findGroups(ChainedHashMap<Integer, Integer> duplicateLines) {
		if (duplicateLines.keySet().size() < 2)
			return new LinkedList<DupLines>();

		SortedSet<Integer> sortedDuplicates = new TreeSet<Integer>(
				duplicateLines.keySet());
		groups = new LinkedList<DupLines>();
		
		for(int line : sortedDuplicates) {
			// iterate forward creating groups for all duplicates at this line
			for(int lineHit : duplicateLines.getChain(line)) {
				DupLines clone = new DupLines(lineHit, line);
				//System.out.println("line: "+line+", lineHit: "+lineHit);
				
				for (int posLine : sortedDuplicates.tailSet(line+1)) {
					//System.out.println("-->"+posLine+", "+clone.curDupLine());
					if (posLine == clone.curDupLine()
							&& duplicateLines.getChain(posLine).contains(clone.curOrigLine())) {
						duplicateLines.getChain(posLine).remove((Integer)clone.curOrigLine());
						clone.increment();
						//System.out.println("oh yeah");
					} else
						break;
				}
			}
		}
		//for(DupLines dup : groups) System.out.println(dup.toCloneLines());
		
		return groups;
	}

	private void findDuplicates(ChainedHashMap<String, Integer> fileLines,
			ChainedHashMap<Integer, Integer> duplicateLines) {
		for (String line : fileLines.keySet()) {
			List<Integer> dups = fileLines.getChain(line);
			if (dups.size() == 1) continue;
			
			for (int i = 0; i < dups.size(); i++) {
				List<Integer> items = new LinkedList<Integer>();
				if(i > 0) items.addAll(dups.subList(0, i));
				if(i < dups.size() - 2) items.addAll(dups.subList(i + 1, dups.size()));
				
				duplicateLines.addChain(dups.get(i), items);
			}
		}
		/*for(int i : new TreeSet<Integer>(duplicateLines.keySet())) {
			System.out.print(i+" >");
			for(int j : duplicateLines.getChain(i)) System.out.print(" "+j);
			System.out.println("");
		}*/
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
