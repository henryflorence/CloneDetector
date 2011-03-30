package com.github.saniul.clonedetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.github.saniul.clonedetector.preprocessor.Normalizer;
import com.github.saniul.clonedetector.preprocessor.EmptyLineRemover;

/**
 * Main class that strings together the preprocessor and main algorithm
 * 
 */
public class Main {
	public static void main(String[] args) throws IOException {
		File original = new File("./testFiles/Test.java");
		EmptyLineRemover emptyLineRemover = new EmptyLineRemover(original);
		Normalizer normalizer = new Normalizer(emptyLineRemover.process());
		File processed = normalizer.process();
		String line;
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(processed));
		//while((line = bufferedReader.readLine()) != null)
		//	System.out.println(line);
		
		List<CloneLines> cloneLines = null;
		try {
			cloneLines = new MainAlgorithm().check(processed);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//cloneLines = normalizer.remap(cloneLines);
		//cloneLines = emptyLineRemover.remap(cloneLines);
		for(CloneLines cl:cloneLines)
			if(cl.dupGroupLength > 6) System.out.println(cl);
		
	}
}
