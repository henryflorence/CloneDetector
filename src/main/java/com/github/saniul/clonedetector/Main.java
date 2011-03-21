package com.github.saniul.clonedetector;

import java.util.List;

import com.github.saniul.clonedetector.preprocessor.Normalizer;
import com.github.saniul.clonedetector.preprocessor.Remover;


/**
 * Main class that strings together the preprocessor and main algorithm
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
    	Remover remover = new Remover("./testFiles/Test.java");
    	remover.buildLineMap();
    	remover.removeEmptyLines();
    	System.out.println();
    	Normalizer normalizer = new Normalizer();
    	normalizer.normalizeFile("./testFiles/Test.java");
    	
//		try {
//			List<MainAlgorithm.CloneLines> lines = new MainAlgorithm().check(args[0]);
//			
//			for(MainAlgorithm.CloneLines line : lines)
//	    		System.out.println(line);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
}
