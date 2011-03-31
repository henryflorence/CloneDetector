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
	private boolean minHash;
	
	public void setCloneGroups(List<DupLines> groups) {
		this.groups = groups;
	}

	public List<CloneLines> check(File file, boolean minHash) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		this.minHash = minHash;
		
		ChainedHashMap<String, Integer> fileLines = new ChainedHashMap<String, Integer>();
		ChainedHashMap<String, Integer> minHashLines = new ChainedHashMap<String, Integer>();
		ChainedHashMap<Integer, Integer> duplicateLines = new ChainedHashMap<Integer, Integer>();
		
		if (minHash) {
			// running with minHash option
			collateLines(bufferedReader, fileLines, minHashLines);
			findDuplicates(fileLines, duplicateLines, minHashLines);
		} else {
			collateLines(bufferedReader, fileLines);
			findDuplicates(fileLines, duplicateLines);
		}

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
			ChainedMap<Integer, Integer> duplicateLines) {
		for (String line : fileLines.keySet()) {
			List<Integer> dups = fileLines.getChain(line);
			if (dups.size() == 1) continue;

			for (int i = 0; i < dups.size(); i++) {
				List<Integer> items = new LinkedList<Integer>();
				if(i > 0) items.addAll(dups.subList(0, i));
				if(i < dups.size() - 1) items.addAll(dups.subList(i + 1, dups.size()));
				
				duplicateLines.addChain(dups.get(i), items);
			}
		}
		/* for(int i : new TreeSet<Integer>(duplicateLines.keySet())) {
			System.out.print(i+" >");
			for(int j : duplicateLines.getChain(i)) System.out.print(" "+j);
			System.out.println("");
		} */
	}

	private void findDuplicates(ChainedHashMap<String, Integer> fileLines,
			ChainedMap<Integer, Integer> duplicateLines, 
			ChainedHashMap<String, Integer> minHashLines) {		
		String minHashCodeLine;
		List<Integer> dups = new LinkedList<Integer>();
		for (String line : fileLines.keySet()) {
			dups.clear();
			minHashCodeLine = computeMinHashCode(line);
			
			for (String minHashCode : minHashLines.keySet()){
				// if Hamming Distance <= 1, consider as clone.
				if (getHammingDistance(minHashCodeLine,minHashCode) <= 1) {
					dups.addAll(minHashLines.getChain(minHashCode));
				}
			}
			
			if (dups.size() == 1) continue;
			
			for (int i = 0; i < dups.size(); i++) {
				List<Integer> items = new LinkedList<Integer>();
				if(i > 0) items.addAll(dups.subList(0, i));
				if(i < dups.size() - 1) items.addAll(dups.subList(i + 1, dups.size()));
				
				duplicateLines.addChain(dups.get(i), items);
			}
		}
		/* for(int i : new TreeSet<Integer>(duplicateLines.keySet())) {
			System.out.print(i+" >");
			for(int j : duplicateLines.getChain(i)) System.out.print(" "+j);
			System.out.println("");
		} */
	}
	
	public void collateLines(BufferedReader bufferedReader,
			Map<String, Integer> fileLines) throws IOException {
		String line;
		int lineNo = 0;
		while ((line = bufferedReader.readLine()) != null)
			fileLines.put(line, lineNo++);
	}
	
	public void collateLines(BufferedReader bufferedReader,
			Map<String, Integer> fileLines, Map<String, Integer> minHashLines)
			throws IOException {
		String line;
		String minHashCode;
		int lineNo = 0;
		while ((line = bufferedReader.readLine()) != null) {
			fileLines.put(line, lineNo);
			/* A minHash code is string made of 36 binary characters.
			 * Each binary code represents a normalized token term.
			 * If a odd number of X token term appeared in the line,
			 * its binary code will be represented by 1.
			 * Otherwise, its binary code will be represented by 0. 
			 */
			minHashCode = computeMinHashCode(line);
			/* minHashLine is a ChainedHashedMap consisting of
			 * minHashCode(key), lineNo(value) pairs 
			 */
			minHashLines.put(minHashCode, lineNo++);
		}
	}

	private String computeMinHashCode(String line) {
		String[] terms = {"ID","KEYWRD","OR","AND","XOR","LSHIFT",
						"RSHIFT","USHIFT","INCREMENT","DECREMENT",
						"PLUS","MINUS","DIV","TIMES","MOD","LT",
						"GT","LEQ","GEQ","COND-THEN","COND-ELSE",
						"EQUAL","NOT-EQUAL","NOT","ASSIGN","NUMLIT",
						"BOOLLIT","NULLLIT","DOT","[","]","{","}",
						"(",")",";"};
		String minHashCode = "";
		for (String term: terms) {
			minHashCode = minHashCode + countAppearance(line,term);
		}
		return minHashCode;
	}
	
	private String countAppearance(String line, String term) {
		String testLine = line;
		int index = testLine.indexOf(term);
		int count = 0;
		while (index != -1) {
		    count++;
		    testLine = testLine.substring(index + 1);
		    index = testLine.indexOf(term);
		}
		if ((count % 2) == 0) {
			// even count
			return "0";
		} else {
			// odd count
			return "1";
		}
	}
	
    private int getHammingDistance(String minHashCodeLine, String minHashCode) {
        int counter = 0;
        
        for (int i = 0; i < minHashCodeLine.length(); i++) {
        	if (minHashCodeLine.charAt(i) != minHashCode.charAt(i)) counter++;
        }
        return counter;
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
			return new CloneLines(origStartLine, dupStartLine, 
									curOrigLine()-1, curDupLine()-1, getLength());
		}
	}
}
