package com.github.saniul.clonedetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.github.saniul.clonedetector.preprocessor.FileProcessor;
import com.github.saniul.clonedetector.preprocessor.Normalizer;
import com.github.saniul.clonedetector.preprocessor.EmptyLineRemover;

/**
 * Main class that strings together the preprocessor and main algorithm
 * 
 */
public class Main {
	private String[] inFiles;
	private FileProcessor emptyLineRemover;
	private FileProcessor normalizer;
	private MainAlgorithm mainAlgorithm;
	
	public Main() {
		emptyLineRemover = new EmptyLineRemover();
		normalizer = new Normalizer();
		mainAlgorithm = new MainAlgorithm();
	}
	public void run() throws IOException {
		File original = new File("./testFiles/Test.java");
		emptyLineRemover.setFile(original);
		
		normalizer.setFile(emptyLineRemover.process());
		File processed = normalizer.process();
		
		displayFile(processed);
		List<CloneLines> cloneLines = mainAlgorithm.check(processed);
		
		//cloneLines = normalizer.remap(cloneLines);
		//cloneLines = emptyLineRemover.remap(cloneLines);
		for(CloneLines cl:cloneLines)
			if(cl.dupGroupLength > 6) System.out.println(cl);
		
	}
	public void processArguments(String[] args) {
		
	}
	public static void main(String[] args) throws IOException {
		try {
			Main instance = new Main();
			instance.processArguments(args);
			instance.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void displayFile(File processed) {
		String line;
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(processed));
			while((line = bufferedReader.readLine()) != null)
				System.out.println(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
