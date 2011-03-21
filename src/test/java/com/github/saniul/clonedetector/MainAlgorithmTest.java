package com.github.saniul.clonedetector;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MainAlgorithmTest {
	private MainAlgorithm algo;
	private List<MainAlgorithm.CloneLines> groups;
	private ChainedHashMap<Integer, Integer> dupLines;
	private Map<String, Integer> fileLines;
	private List<String> lines;

	@Before
	public void setUp() {
		algo = new MainAlgorithm();
		groups = new ArrayList<MainAlgorithm.CloneLines>();
		algo.setCloneGroups(groups);
		dupLines = new ChainedHashMap<Integer, Integer>();
		fileLines = new HashMap<String, Integer>();
		lines = new LinkedList<String>();
	}

	@Test
	public void testCloneLines() {
		MainAlgorithm.CloneLines cloneLines = algo.new CloneLines(1, 2);
		assertEquals(groups.size(), 1);
		assertEquals(cloneLines.curOrigLine(), 2);
		assertEquals(cloneLines.curDupLine(), 3);
		assertEquals(cloneLines.getLength(), 1);
		assertEquals(cloneLines.toString(), "3-3:2-2");

		cloneLines.increment();
		assertEquals(cloneLines.curOrigLine(), 3);
		assertEquals(cloneLines.curDupLine(), 4);
		assertEquals(cloneLines.getLength(), 2);

		assertEquals(cloneLines.toString(), "3-4:2-3");
	}

	@Test
	public void testNoGroups() {
		List<MainAlgorithm.CloneLines> out = algo.findGroups(dupLines);
		assertEquals(out.size(), 0);

		List<Integer> chain = new LinkedList<Integer>();
		chain.add(1);
		dupLines.putChain(1, chain);
		out = algo.findGroups(dupLines);
		assertEquals(out.size(), 0);
	}

	@Ignore
	@Test
	public void testOneGroups() {
		dupLines.put(2, 10);
		dupLines.put(3, 11);

		List<MainAlgorithm.CloneLines> cl = algo.findGroups(dupLines);
		assertEquals(cl.size(), 1);
	}

	@Ignore
	@Test
	public void testTwoGroups() {
		dupLines.put(2, 10);
		dupLines.put(3, 11);
		dupLines.put(4, 10);
		dupLines.put(5, 11);

		List<MainAlgorithm.CloneLines> cl = algo.findGroups(dupLines);
		assertEquals(cl.size(), 2);
	}

	@Test
	public void testCollateLines() throws IOException {
		lines.add("one");
		lines.add("two");

		algo.collateLines(new TestReader(lines), fileLines);

		assertEquals(fileLines.size(), 2);
		assertEquals((int) fileLines.get("one"), 0);
		assertEquals((int) fileLines.get("two"), 1);
	}

	public class TestReader extends BufferedReader {
		Iterator<String> iterator;

		public TestReader(List<String> vals) {
			super(new StringReader("sausage"));
			this.iterator = vals.iterator();
		}

		public String readLine() {
			if (iterator.hasNext())
				return iterator.next();
			return null;
		}
	}
}
