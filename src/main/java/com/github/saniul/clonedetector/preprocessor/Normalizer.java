package com.github.saniul.clonedetector.preprocessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class Normalizer extends FileProcessor {

	public Normalizer(File file) {
		super(file);
	}

	private final static String kwrds = "abstract|" + "assert|" + "boolean|"
			+ "break|" + "byte|" + "case|" + "catch|" + "char|" + "class|"
			+ "const|" + "continue|" + "default|" + "do|" + "double|" + "else|"
			+ "enum|" + "extends|" + "final|" + "finally|" + "float|" + "for|"
			+ "goto|" + "if|" + "implements|" + "import|" + "instanceof|"
			+ "int|" + "interface|" + "long|" + "native|" + "new|" + "package|"
			+ "private|" + "protected|" + "public|" + "return|" + "short|"
			+ "static|" + "strictfp|" + "super|" + "switch|" + "synchronized|"
			+ "this|" + "throw|" + "throws|" + "transient|" + "try|" + "void|"
			+ "volatile|" + "while";
	private final static String ops = "\\|\\||" + "&&|" + "<<|" + ">>|"
			+ "\\+=|" + "\\-=|" + "\\/=|" + "\\*=|" + "\\+\\+|" + "\\-\\-|"
			+ "=|" + "\\+|" + "\\-|" + "\\/|" + "\\*|" + "<|" + ">|" 
			+ ">=|" + "<=|" + "&|" + ">>>|" + "^|" + "?:|" + "!=|" + "!|" + "%" ; //Added missing operators

	private final static String identifiers = "\\b(?!(?:" + kwrds
			+ ")\\b)[_a-zA-Z][_a-zA-Z0-9]*\\b";
	private final static String keywords = "\\b(" + kwrds + ")\\b";
	private final static String operators = "(" + ops + ")";

	private final static String numliterals = "\\b\\d+\\.?\\d*[f|d]?\\b";

	private final static String boolliterals = "\\btrue|false\\b";
	private final static String nullliterals = "\\bnull\\b";

	private final static String comments = "\\/\\/(.*)";

	public String normalizeLine(String line) {
		String result = line;
		result = result.replaceAll(comments, " ");
		result = result.replaceAll(identifiers, " ID ");
		result = result.replaceAll(keywords, " KEYWRD ");
		result = result.replaceAll(operators, " OP ");
		result = result.replaceAll(numliterals, " NUMLIT ");
		result = result.replaceAll(boolliterals, " BOOLLIT ");
		result = result.replaceAll(nullliterals, " NULLLIT ");
//		result = result.replaceAll("\\[", " LSQUARE ");
//		result = result.replaceAll("\\]", " RSQUARE ");
//		result = result.replaceAll("\\{", " LBRACE ");
//		result = result.replaceAll("\\}", " RBRACE ");
//		result = result.replaceAll("\\(", " LPAREN ");
//		result = result.replaceAll("\\)", " RPAREN ");
		result = result.replaceAll("\\s\\.\\s", " DOT ");
		result = result.replaceAll(";", " SEMI ");
		result = StringUtils.join(StringUtils.split(result), " ");
		return result;
	}

	@Override
	public File process() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					originalFile));

			BufferedWriter writer = new BufferedWriter(new FileWriter(
					processedFile));
			String line = null;
			while ((line = reader.readLine()) != null) {
				writer.write(normalizeLine(line));
				writer.newLine();
			}
			reader.close();
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return processedFile;
	}

}
