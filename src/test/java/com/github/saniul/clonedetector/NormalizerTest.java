package com.github.saniul.clonedetector;


import com.github.saniul.clonedetector.preprocessor.Normalizer;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NormalizerTest {

	private Normalizer normal;

	@Before
	public void setUp() {

		normal = new Normalizer();

	}

	@Test
	public void normalizeKwrdsTest() {

		assertEquals(normal.normalizeLine("abstract"),"KEYWRD");
		assertEquals(normal.normalizeLine("assert"),"KEYWRD");
		assertEquals(normal.normalizeLine("boolean"),"KEYWRD");
		assertEquals(normal.normalizeLine("break"),"KEYWRD");
		assertEquals(normal.normalizeLine("byte"),"KEYWRD");
		assertEquals(normal.normalizeLine("case"),"KEYWRD");
		assertEquals(normal.normalizeLine("catch"),"KEYWRD");
		assertEquals(normal.normalizeLine("char"),"KEYWRD");
		assertEquals(normal.normalizeLine("class"),"KEYWRD");
		assertEquals(normal.normalizeLine("const"),"KEYWRD");
		assertEquals(normal.normalizeLine("continue"),"KEYWRD");
		assertEquals(normal.normalizeLine("default"),"KEYWRD");
		assertEquals(normal.normalizeLine("do"),"KEYWRD");
		assertEquals(normal.normalizeLine("double"),"KEYWRD");
		assertEquals(normal.normalizeLine("else"),"KEYWRD");
		assertEquals(normal.normalizeLine("enum"),"KEYWRD");
		assertEquals(normal.normalizeLine("extends"),"KEYWRD");
		assertEquals(normal.normalizeLine("final"),"KEYWRD");
		assertEquals(normal.normalizeLine("finally"),"KEYWRD");
		assertEquals(normal.normalizeLine("float"),"KEYWRD");
		assertEquals(normal.normalizeLine("for"),"KEYWRD");
		assertEquals(normal.normalizeLine("goto"),"KEYWRD");
		assertEquals(normal.normalizeLine("if"),"KEYWRD");
		assertEquals(normal.normalizeLine("implements"),"KEYWRD");
		assertEquals(normal.normalizeLine("import"),"KEYWRD");
		assertEquals(normal.normalizeLine("instanceof"),"KEYWRD");
		assertEquals(normal.normalizeLine("int"),"KEYWRD");
		assertEquals(normal.normalizeLine("interface"),"KEYWRD");
		assertEquals(normal.normalizeLine("long"),"KEYWRD");
		assertEquals(normal.normalizeLine("native"),"KEYWRD");
		assertEquals(normal.normalizeLine("new"),"KEYWRD");
		assertEquals(normal.normalizeLine("package"),"KEYWRD");
		assertEquals(normal.normalizeLine("private"),"KEYWRD");
		assertEquals(normal.normalizeLine("protected"),"KEYWRD");
		assertEquals(normal.normalizeLine("public"),"KEYWRD");
		assertEquals(normal.normalizeLine("return"),"KEYWRD");
		assertEquals(normal.normalizeLine("short"),"KEYWRD");
		assertEquals(normal.normalizeLine("static"),"KEYWRD");
		assertEquals(normal.normalizeLine("strictfp"),"KEYWRD");
		assertEquals(normal.normalizeLine("super"),"KEYWRD");
		assertEquals(normal.normalizeLine("switch"),"KEYWRD");
		assertEquals(normal.normalizeLine("synchronized"),"KEYWRD");
		assertEquals(normal.normalizeLine("this"),"KEYWRD");
		assertEquals(normal.normalizeLine("throw"),"KEYWRD");
		assertEquals(normal.normalizeLine("throws"),"KEYWRD");
		assertEquals(normal.normalizeLine("transient"),"KEYWRD");
		assertEquals(normal.normalizeLine("try"),"KEYWRD");
		assertEquals(normal.normalizeLine("void"),"KEYWRD");
		assertEquals(normal.normalizeLine("volatile"),"KEYWRD");
		assertEquals(normal.normalizeLine("while"),"KEYWRD");
	}
	
	@Test
	public void normalizeIDTest() {

		assertEquals(normal.normalizeLine("i"),"ID");
		assertEquals(normal.normalizeLine("result"),"ID");
	}
	
	@Test
	public void normalizeOPTest() {

		assertEquals(normal.normalizeLine("|"),"OP");
		assertEquals(normal.normalizeLine("||"),"OP");
		assertEquals(normal.normalizeLine("&&"),"OP");
		assertEquals(normal.normalizeLine("<<"),"OP");
		assertEquals(normal.normalizeLine(">>"),"OP");
		assertEquals(normal.normalizeLine("+="),"OP");
		assertEquals(normal.normalizeLine("-="),"OP");
		assertEquals(normal.normalizeLine("="),"OP");
		assertEquals(normal.normalizeLine("*="),"OP");
		assertEquals(normal.normalizeLine("++"),"OP");
		assertEquals(normal.normalizeLine("--"),"OP");
		assertEquals(normal.normalizeLine("/="),"OP");
		assertEquals(normal.normalizeLine("+"),"OP");
		assertEquals(normal.normalizeLine("-"),"OP");
		assertEquals(normal.normalizeLine("/"),"OP");
		assertEquals(normal.normalizeLine("*"),"OP");
		assertEquals(normal.normalizeLine(">"),"OP");
		assertEquals(normal.normalizeLine("<"),"OP");
		assertEquals(normal.normalizeLine("<="),"OP");
		assertEquals(normal.normalizeLine(">="),"OP");
		assertEquals(normal.normalizeLine("&"),"OP");
		assertEquals(normal.normalizeLine(">>>"),"OP");
		assertEquals(normal.normalizeLine("^"),"OP");
		assertEquals(normal.normalizeLine(":"),"OP");
		assertEquals(normal.normalizeLine("?"),"OP");
		assertEquals(normal.normalizeLine("!="),"OP");
		assertEquals(normal.normalizeLine("!"),"OP");
		assertEquals(normal.normalizeLine("%"),"OP");
	}
	
	@Test
	public void normalizeNUMLITTest() {

		assertEquals(normal.normalizeLine("213"),"NUMLIT");
	}
	
	@Test
	public void normalizeNULLTest() {
		
		assertEquals(normal.normalizeLine("null"),"NULLLIT");
	}
	
	@Test
	public void normalizeBOOLLITTest() {

		assertEquals(normal.normalizeLine("false"),"BOOLLIT");
		assertEquals(normal.normalizeLine("true"),"BOOLLIT");
	}
	
	/*@Test
	public void normalizeDOTTest() {

		assertEquals(normal.normalizeLine("."),"DOT");
	}*/


}
