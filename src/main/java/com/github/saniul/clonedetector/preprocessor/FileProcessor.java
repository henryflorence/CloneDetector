package com.github.saniul.clonedetector.preprocessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

import org.apache.commons.lang.mutable.MutableInt;

import com.github.saniul.clonedetector.Main.CommandArgs;


public abstract class FileProcessor {
	protected LineMap lineMap;
	protected File originalFile;
	protected File processedFile;
	protected BufferedReader reader;
	protected BufferedWriter writer;
	protected CommandArgs cmdArgs;

	public void setFile (File originalFile) throws IOException {
		this.originalFile = originalFile;
		processedFile = File.createTempFile("clonedetector", ".tmp");
		processedFile.deleteOnExit();
	}

	public void setLineMap(LineMap lineMap) {
		this.lineMap = lineMap;
	}
	public File getProcessedFile() {
		return this.processedFile;
	}

	abstract void doProcess() throws IOException;
	
	public void process() throws IOException {
		reader = new BufferedReader(new FileReader(
				originalFile));

		writer = new BufferedWriter(new FileWriter(
				processedFile));
		
		doProcess();
		
		reader.close();
		writer.flush();
		writer.close();
	}

	public void setCommandArgs(CommandArgs cmdArgs) {
		this.cmdArgs = cmdArgs;
	}
}
