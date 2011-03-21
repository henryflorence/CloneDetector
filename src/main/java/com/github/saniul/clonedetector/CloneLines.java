package com.github.saniul.clonedetector;

public class CloneLines {
	int origStartLine;
	int dupStartLine;
	int origGroupLength = 1;
	int dupGroupLength = 1;
	
	public CloneLines(int origStartLine, int dupStartLine) {
		this.origStartLine = origStartLine; 
		this.dupStartLine = dupStartLine;
	}
	public CloneLines(int origStartLine, int dupStartLine, int origGroupLength, int dupGroupLength) {
		this.origStartLine = origStartLine; 
		this.dupStartLine = dupStartLine;
		this.origGroupLength = origGroupLength;
		this.dupGroupLength = dupGroupLength;
	}
	public void setLength(int length){
		origGroupLength=length;
		dupGroupLength=length;
	}
	
	public int getOrigStartLine(){
		return origStartLine;
	}
	
	public int getDupStartLine(){
		return dupStartLine;
	}
	public int curOrigLine() {
		return origStartLine + origGroupLength;
	}
	public int curDupLine() {
		return dupStartLine + dupGroupLength;
	}
	public String toString() { 
		return (dupStartLine+1)+"-"+(dupStartLine+dupGroupLength) + ":"
				+ (origStartLine+1)+"-"+(origStartLine+origGroupLength);
	}

	public int getLength() {
		return origGroupLength;
	}
}