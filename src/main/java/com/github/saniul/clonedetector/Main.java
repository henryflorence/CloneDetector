package com.github.saniul.clonedetector;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.github.saniul.clonedetector.preprocessor.Normalizer;
import com.github.saniul.clonedetector.preprocessor.EmptyLineRemover;

/**
 * Main class that strings together the preprocessor and main algorithm
 * 
 */
public class Main {
	public static void main(String[] args) {
		File original = new File("./testFiles/Test.java");
		EmptyLineRemover emptyLineRemover = new EmptyLineRemover(original);
		Normalizer normalizer = new Normalizer(emptyLineRemover.process());
		File processed = normalizer.process();
		
		MainAlgorithm algo = new MainAlgorithm();
		List<CloneLines> cloneLines = null;
		try {
			cloneLines = algo.check(processed);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cloneLines = normalizer.remap(cloneLines);
		cloneLines = emptyLineRemover.remap(cloneLines);
		for(CloneLines cl:cloneLines){
			System.out.println(cl);
		}
		// try {
		// List<MainAlgorithm.CloneLines> lines = new
		// MainAlgorithm().check(args[0]);
		//
		// for(MainAlgorithm.CloneLines line : lines)
		// System.out.println(line);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
