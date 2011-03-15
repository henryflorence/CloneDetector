package com.github.saniul.clonedetector;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
		try {
			MainAlgorithm.CloneLines[] lines = new MainAlgorithm().check(args[0]);
			
			for(MainAlgorithm.CloneLines line : lines)
	    		System.out.print(line.displayMatch());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
