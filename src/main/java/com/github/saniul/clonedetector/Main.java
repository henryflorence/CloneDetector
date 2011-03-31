package com.github.saniul.clonedetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.saniul.clonedetector.preprocessor.FileProcessor;
import com.github.saniul.clonedetector.preprocessor.LineMap;
import com.github.saniul.clonedetector.preprocessor.MultiLineNormalizer;
import com.github.saniul.clonedetector.preprocessor.Normalizer;
import com.github.saniul.clonedetector.preprocessor.EmptyLineRemover;

/**
 * Main class that strings together the preprocessor and main algorithm
 * 
 */
public class Main {
	private List<String> inFiles = new ArrayList<String>();
	private FileProcessor multiLineNormalizer;
	private FileProcessor emptyLineRemover;
	private FileProcessor normalizer;
	private MainAlgorithm mainAlgorithm;
	private LineMap lineMap;
	private CommandArgs cmdArgs = new CommandArgs();

	public Main() {
		multiLineNormalizer = new MultiLineNormalizer();
		emptyLineRemover = new EmptyLineRemover();
		normalizer = new Normalizer();
		mainAlgorithm = new MainAlgorithm();
		
		lineMap = new LineMap();
		multiLineNormalizer.setLineMap(lineMap);
		emptyLineRemover.setLineMap(lineMap);
		normalizer.setLineMap(lineMap);
		
		multiLineNormalizer.setCommandArgs(cmdArgs);
		emptyLineRemover.setCommandArgs(cmdArgs);
		normalizer.setCommandArgs(cmdArgs);
	}
	public void run() throws IOException {
		File current = new File(inFiles.get(0));
		lineMap.buildLineMap(current);
		
		if(cmdArgs.codeMode) {
			multiLineNormalizer.setFile(current);
			multiLineNormalizer.process();
			current = multiLineNormalizer.getProcessedFile();
		}
		
		if(cmdArgs.javaMode) {
			normalizer.setFile(current);
			normalizer.process();
			current = normalizer.getProcessedFile();
		} /*else if(cmdArgs.codeMode) {
			basicNormalizer.setFile(current);
			basicNormalizer.process();
			current = normalizer.getProcessedFile();
		}*/
		
		if(cmdArgs.codeMode) {
			emptyLineRemover.setFile(current);
			emptyLineRemover.process();
			current = emptyLineRemover.getProcessedFile();
		}
		
		if(cmdArgs.displayProcessed) displayFile(current);
		
		mainAlgorithm.setMinHash(cmdArgs.minHash);
		List<CloneLines> cloneLines = mainAlgorithm.check(current);
		lineMap.remap(cloneLines);
		
		for(CloneLines cl:cloneLines)
			if(cl.getLength() > cmdArgs.minLines) System.out.println(cl);
		
	}
	private void printUsage() {
		System.out.println("basic usage: java -jar clonedetector.jar file.java");
		System.out.println("-h -> display this message and exit");
		System.out.println("-d -> display output from preprocessed stage");
		System.out.println("-m -> use min hash fingerprinting");
		System.out.println("-j -> java mode : default mode");
		System.out.println("-c -> generic code mode");
		System.out.println("-t -> plain text mode");
		System.out.println("-l5 -> match min 5 duplicate lines, default: 6");
		System.exit(0);
	}
	public void processArguments(String[] args) {
		for(String arg : args) {
			if("-d".equals(arg)) cmdArgs.displayProcessed = true;
			else if("-j".equals(arg)) cmdArgs.javaMode = true;
			else if("-c".equals(arg)) cmdArgs.codeMode = true;
			else if("-t".equals(arg)) cmdArgs.textMode = true;
			else if("-m".equals(arg)) cmdArgs.minHash = true;
			else if("-h".equals(arg)) printUsage();
			else if(arg.matches("-l\\d+")) 
				cmdArgs.minLines = Integer.parseInt(arg.substring(2));
			else inFiles.add(arg);
		}
		
		if(inFiles.size() == 0) inFiles.add("./testFiles/Test.java");
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
	
	public static class CommandArgs {
		public boolean minHash = false;
		public boolean displayProcessed = false;
		public boolean javaMode = true;
		public boolean codeMode = true;
		public boolean textMode = true;
		public int minLines = 6;
	}
}
