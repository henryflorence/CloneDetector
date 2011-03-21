package com.github.saniul.clonedetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class MainAlgorithm {
	private List<CloneLines> groups; 
	
	public void setCloneGroups(List<CloneLines> groups) {
		this.groups = groups;
	}
	public void add (CloneLines lines){
		groups.add(lines);
	}
	
	public List<CloneLines> check(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		ChainedHashMap<String,Integer> fileLines = new ChainedHashMap<String,Integer>();
		collateLines(bufferedReader, fileLines);
		
		ChainedHashMap<Integer,Integer> duplicateLines = new ChainedHashMap<Integer,Integer>();
		findDuplicates(fileLines, duplicateLines);
			
		List<CloneLines> lines = findGroups(duplicateLines);
		
		return lines;
	}
	public List<CloneLines> findGroups(ChainedHashMap<Integer,Integer> duplicateLines) {
		if(duplicateLines.keySet().size() < 2) return new LinkedList<CloneLines>();

		SortedSet<Integer> sortedDuplicates = new TreeSet<Integer>(duplicateLines.keySet());
		groups = new LinkedList<CloneLines>();
		
		for(int line : sortedDuplicates) {
			for(int origStart : duplicateLines.getChain(line)) {
				CloneLines clone = new CloneLines(line,origStart);
				groups.add(clone);
				
				for(int nextLine : sortedDuplicates.tailSet(line+1)) {
					if(nextLine == clone.curDupLine() && duplicateLines.getChain(nextLine).contains(clone.curOrigLine()))
						clone.increment();
					else
						break;
				}
			}
		}
		/*Iterator<Integer> iterator = sortedDuplicates.iterator();
		int line = iterator.next();
		CloneLines clone = new CloneLines(duplicateLines.getChain(line).get(0),line);

		while(iterator.hasNext()) {
			line = iterator.next();

			if(line == clone.curDupLine() && duplicateLines.getChain(line).get(0) == clone.curOrigLine())
				clone.increment();
			else
				clone = new CloneLines(duplicateLines.getChain(line).get(0),line);
		}*/

		return groups;
	}
	private void findDuplicates(ChainedHashMap<String,Integer> fileLines, ChainedHashMap<Integer,Integer> duplicateLines) {
		for(String line : fileLines.keySet()) {
			List<Integer> dups = fileLines.getChain(line);
			if(dups.size() < 2) continue;
			
			for(int i=1; i < dups.size(); i++) {
				List<Integer> others = dups.subList(0,i);
				others.addAll(dups.subList(i+1,dups.size()));
			    duplicateLines.putChain(dups.get(i),others);
			}
		}
	}
	public void collateLines(BufferedReader bufferedReader,Map<String, Integer> fileLines) throws IOException {
		String line;
		int lineNo = 0;
		while((line = bufferedReader.readLine()) != null)
			fileLines.put(line, lineNo++);
	}
}
