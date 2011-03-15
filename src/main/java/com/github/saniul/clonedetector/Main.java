package com.github.saniul.clonedetector;

import java.io.IOException;

import java.util.List;

/**
 * Main class that strings together the preprocessor and main algorithm
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
		try {
			List<MainAlgorithm.CloneLines> lines = new MainAlgorithm().check(args[0]);
			
			for(MainAlgorithm.CloneLines line : lines)
	    		System.out.println(line.displayMatch());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
