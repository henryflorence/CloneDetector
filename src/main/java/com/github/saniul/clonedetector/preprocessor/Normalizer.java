package com.github.saniul.clonedetector.preprocessor;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class Normalizer extends FileProcessor {

	private final static String kwrds = "abstract|" + "assert|" + "boolean|"
			+ "break|" + "byte|" + "case|" + "catch|" + "char|" + "class|"
			+ "const|" + "continue|" + "default|" + "do|" + "double|" + "else|"
			+ "enum|" + "extends|" + "final|" + "finally|" + "float|" + "for|"
			+ "goto|" + "if|" + "implements|" + "import|" + "instanceof|"
			+ "int|" + "interface|" + "long|" + "native|" + "new|" + "package|"
			+ "private|" + "protected|" + "public|" + "return|" + "short|"
			+ "static|" + "strictfp|" + "super|" + "switch|" + "synchronized|"
			+ "this|" + "throw|" + "throws|" + "transient|" + "try|" + "void|"
			+ "volatile|" + "while|" + "true|" + "false|" + "null";

	private final static String ops = "\\|\\||" + "\\||" + "&&|" + ">>>|"
			+ "<<|" + ">>|" + "\\+=|" + "\\-=|" + "\\/=|" + "\\*=|" + "\\+\\+|"
			+ "\\-\\-|" + "=|" + "\\+|" + "\\-|" + "\\/|" + "\\*|" + ">=|"
			+ "<=|" + "<|" + ">|" + "&|" + "\\^|" + ":|" + "\\?|" + "\\!=|"
			+ "\\!|" + "%";

	private final static String identifiers = "\\b(?!(?:" + kwrds
			+ ")\\b)[_a-zA-Z][_a-zA-Z0-9]*\\b";

	private final static String keywords = "\\b(" + kwrds + ")\\b";
	private final static String operators = "(" + ops + ")";

	private final static String numliterals = "\\b\\d+\\.?\\d*[f|d]?\\b";

	private final static String comments = "\\/\\/(.*)";

	public String normalizeLine(String line) {

		String result = line;
		result = result.replaceAll(comments, " ");
		result = result.replaceAll(identifiers, " ID ");
		result = result.replaceAll("true|false", " BOOLLIT ");
		result = result.replaceAll("null", " NULLLIT ");
		result = result.replaceAll(keywords, " KEYWRD ");
		//result = result.replaceAll(operators, " OP ");
		//Extended list of OPs
		result = result.replaceAll("(\\|\\||\\|)", " OR ");
		result = result.replaceAll("(&&|&)", " AND ");
		result = result.replaceAll("(\\^)", " XOR ");
		result = result.replaceAll("(<<)", " LSHIFT ");
		result = result.replaceAll("(>>)", " RSHIFT ");
		result = result.replaceAll("(>>>)", " USHIFT ");
		result = result.replaceAll("(\\+\\+)", " INCREMENT ");
		result = result.replaceAll("(\\-\\-)", " DECREMENT ");
		result = result.replaceAll("(\\+)", " PLUS ");
		result = result.replaceAll("(\\-)", " MINUS ");
		result = result.replaceAll("(\\/)", " DIV ");
		result = result.replaceAll("(\\*)", " TIMES ");
		result = result.replaceAll("(%)", " MOD ");
		result = result.replaceAll("(<)", " LT ");
		result = result.replaceAll("(>)", " GT ");
		result = result.replaceAll("(<=)", " LEQ ");
		result = result.replaceAll("(>=)", " GEQ ");
		result = result.replaceAll("(:)", " COND-THEN ");
		result = result.replaceAll("(\\?)", " COND-ELSE ");
		result = result.replaceAll("(==)", " EQUAL ");
		result = result.replaceAll("(\\!=)", " NOT-EQUAL ");
		result = result.replaceAll("(\\!)", " NOT ");
		result = result.replaceAll("(=)", " ASSIGN ");
		//End of extended list
		result = result.replaceAll(numliterals, " NUMLIT ");
		result = result.replaceAll("\\.", " DOT ");
		result = StringUtils.join(StringUtils.split(result), " ");
		return result;
	}

	@Override
	public void doProcess() throws IOException {
		String line = null;
		while ((line = reader.readLine()) != null) {
			writer.write(normalizeLine(line));
			writer.newLine();
		}
	}

}
