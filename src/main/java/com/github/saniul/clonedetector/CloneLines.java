package com.github.saniul.clonedetector;

import com.github.saniul.clonedetector.preprocessor.LineMap;

public class CloneLines {
	public int origStartLine;
	public int dupStartLine;
	public int origEndLine;
	public int dupEndLine;
	private int dupLength; // before remapping
	
	public CloneLines(int origStartLine, int dupStartLine, 
					int origEndLine, int dupEndLine, int dupLength) {
		this.origStartLine = origStartLine; 
		this.dupStartLine = dupStartLine;
		this.origEndLine = origEndLine;
		this.dupEndLine = dupEndLine;
		this.dupLength = dupLength;
	}
	
	public int getOrigStartLine(){
		return origStartLine;
	}
	public int getDupStartLine(){
		return dupStartLine;
	}
	public int getOrigEndLine() {
		return origEndLine;
	}
	public int getDupEndLine() {
		return dupEndLine;
	}
	public String toString() { 
		return (dupStartLine+1)+"-"+(dupEndLine+1)+":"
				+ (origStartLine+1)+"-"+(origEndLine+1);
	}

	public int getLength() {
		return dupLength;
	}
	public void remapLines(LineMap lineMap) {
		origStartLine = lineMap.translate(getOrigStartLine());
		dupStartLine = lineMap.translate(getDupStartLine());
		dupEndLine = lineMap.translate(getDupEndLine());
		origEndLine = lineMap.translate(getOrigEndLine());
	}
}