package com.github.saniul.clonedetector.preprocessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class Normalizer {

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
			+ "=|" + "\\+|" + "\\-|" + "\\/|" + "\\*|" + "<|" + ">";

	private final static String identifiers = "\\b(?!(?:" + kwrds
			+ ")\\b)[_a-zA-Z][_a-zA-Z0-9]*\\b";
	private final static String keywords = "\\b(" + kwrds + ")\\b";
	private final static String operators = "(" + ops + ")";

	private final static String numliterals = "\\b\\d+\\.?\\d*[f|d]?\\b";

	private final static String boolliterals = "\\btrue|false\\b";
	private final static String nullliterals = "\\bnull\\b";

	private final static String comments = "\\/\\/(.*)";

	public void normalizeFile(String file) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					file));
			String line = null;
			int lineNo=0;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println((lineNo++)+"\t"+normalizeLine(line));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String normalizeLine(String line) {
		String result = line;
		result = result.replaceAll(comments, " ");
		result = result.replaceAll(identifiers, " ID ");
		result = result.replaceAll(keywords, " KEYWRD ");
		result = result.replaceAll(operators, " OP ");
		result = result.replaceAll(numliterals, " NUMLIT ");
		result = result.replaceAll(boolliterals, " BOOLLIT ");
		result = result.replaceAll(nullliterals, " NULLLIT ");
		result = result.replaceAll("\\[", " LSQUARE ");
		result = result.replaceAll("\\]", " RSQUARE ");
		result = result.replaceAll("\\{", " LBRACE ");
		result = result.replaceAll("\\}", " RBRACE ");
		result = result.replaceAll("\\(", " LPAREN ");
		result = result.replaceAll("\\)", " RPAREN ");
		result = result.replaceAll("\\s\\.\\s", " DOT ");
		result = result.replaceAll(";", " SEMI ");
		result = StringUtils.join(StringUtils.split(result), " ");
		return result;
	}

}
