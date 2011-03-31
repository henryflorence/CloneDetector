package com.github.saniul.clonedetector.preprocessor;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.saniul.clonedetector.CloneLines;

public class LineMapTest {
	private LineMap lineMap;
	private List<String> lines;
	
	@Before
	public void setUp() throws IOException {
		lines = new ArrayList<String>();
		lines.add("one");
		lines.add("two");

		lineMap = new LineMap();
		lineMap.buildLineMap(new TestReader(lines));
	}
	@Test
	public void testCloneLines() {
		assertEquals(lineMap.translate(0),0);
		assertEquals(lineMap.translate(1),1);
		assertEquals(lineMap.translate(2),2);
	}
	@Test
	public void testRemapLines() {
		List<CloneLines> lines = new ArrayList<CloneLines>();
		lines.add(new TestCloneLines());
		lines.add(new TestCloneLines());
		assertEquals(((TestCloneLines)lines.get(0)).tested, false);
		assertEquals(((TestCloneLines)lines.get(1)).tested, false);
		
		lineMap.remap(lines);
		
		assertEquals(((TestCloneLines)lines.get(0)).tested, true);
		assertEquals(((TestCloneLines)lines.get(1)).tested, true);
	}
	@Test
	public void testInsertBlankLine() {
		lineMap.insertBlankLine(1);
		assertEquals(lineMap.translate(0),0);
		assertEquals(lineMap.translate(1),2);
		assertEquals(lineMap.translate(2),3);
	}
	@Test
	public void testAddAdditionalLine() {
		lineMap.addAdditionalLine(1);
		assertEquals(lineMap.translate(0),0);
		assertEquals(lineMap.translate(1),1);
		assertEquals(lineMap.translate(2),1);
		assertEquals(lineMap.translate(3),2);
	}
	private class TestCloneLines extends CloneLines {
		boolean tested = false;
		
		public TestCloneLines() {
			super(1,2,3,4,5);
		}
		public void remapLines(LineMap lineMap) {
			tested = true;
		}
	}
	public class TestReader extends BufferedReader {
		Iterator<String> iterator;

		public TestReader(List<String> vals) {
			super(new StringReader("sausage"));
			this.iterator = vals.iterator();
		}

		public String readLine() {
			if(iterator.hasNext()) return iterator.next();
			return null;
		}
	}
}
