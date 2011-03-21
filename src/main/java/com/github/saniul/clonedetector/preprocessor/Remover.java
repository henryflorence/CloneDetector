package com.github.saniul.clonedetector.preprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.mutable.MutableInt;

import com.github.saniul.clonedetector.CloneLines;

public class Remover {
	TreeMap<Integer, MutableInt> linemap = new TreeMap<Integer, MutableInt>();
	File file;

	public Remover(String file) {
		this.file = new File(file);
	}

	public void buildLineMap() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					file));
			String line = null;
			int lineCount = 0;
			while ((line = bufferedReader.readLine()) != null) {
				linemap.put(lineCount, new MutableInt(lineCount));
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

	public void removeEmptyLines() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					file));
			String line = null;
			int curLineCount = 0;
			int originalLineCount = 0;
			while ((line = bufferedReader.readLine()) != null) {
				if (StringUtils.trimToNull(line) == null) {
					for (int l : linemap
							.subMap(curLineCount, linemap.lastKey()).keySet()) {
						linemap.get(l).increment();
					}
				} else {
					System.out.println(curLineCount + ":"
							+ linemap.get(curLineCount) + "\t" + line);
					curLineCount++;
				}
				originalLineCount++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CloneLines remapLines(CloneLines cloneLines) {
		int newOrigStartLine = linemap.get(cloneLines.getOrigStartLine())
				.intValue();
		int newDupStartLine = linemap.get(cloneLines.getDupStartLine()).intValue();
//		int newCurDupLine = linemap.get(cloneLines.curDupLine()).intValue();
		int newCurOrigLine = linemap.get(cloneLines.curOrigLine()).intValue();

		CloneLines n = new CloneLines(newOrigStartLine, newDupStartLine);
		n.setLength(newCurOrigLine-newOrigStartLine);
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
