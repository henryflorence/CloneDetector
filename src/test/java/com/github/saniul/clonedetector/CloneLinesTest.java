package com.github.saniul.clonedetector;

import org.junit.Before;
import org.junit.Test;

import com.github.saniul.clonedetector.preprocessor.LineMap;

import static org.junit.Assert.*;


public class CloneLinesTest {
	
	private CloneLines cl;
	
	@Before
	public void setUp() {
		cl = new CloneLines(3,1,4,2,2);
	}
	
	@Test
	public void testCloneLines() {
		assertEquals( cl.getOrigStartLine(),3 );
		assertEquals( cl.getOrigEndLine(),4 );
		assertEquals( cl.getDupStartLine(),1 );
		assertEquals( cl.getDupEndLine(),2 );
		assertEquals( cl.getLength(),2 );
	}
	
	@Test
	public void testRemapLines() {
		cl.remapLines(new LineMapTest());
		
		assertEquals( cl.getOrigStartLine(),3 );
		assertEquals( cl.getOrigEndLine(),4 );
		assertEquals( cl.getDupStartLine(),1 );
		assertEquals( cl.getDupEndLine(),2 );
		assertEquals( cl.getLength(),2 );
	}
	private class LineMapTest extends LineMap {
		public int translate(int i) {
			return i;
		}
	}
}
