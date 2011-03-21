package com.github.saniul.clonedetector;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sun.reflect.generics.reflectiveObjects.*;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.github.saniul.clonedetector.ChainedHashMap;

public class ChainedHashMapTest {
	ChainedHashMap<Integer,String> map;
  	     
    @Before
    public void setUp() {
    	map = new ChainedHashMap<Integer,String>();
    }


	@Test
	public void testEmpty() {
		assertEquals(map.size(), 0);
		assertEquals(map.keySet().size(), 0);
	}
	
	@Test
	public void testOneEntry() {
		map.put(1, "one");
		assertEquals(map.size(), 1);
		assertEquals(map.keySet().size(), 1);
		assertEquals(map.getChain(1).get(0), "one");
		map.clear();
		assertEquals(map.size(),0);
		assertEquals(map.keySet().size(), 0);
	}
	
	@Test
	public void testTwoEntries() {
		map.put(1, "one");
		assertEquals(map.size(), 1);
		assertEquals(map.keySet().size(), 1);
		assertEquals(map.getChain(1).get(0), "one");
		
		map.put(2, "two");
		assertEquals(map.size(), 2);
		assertEquals(map.keySet().size(), 2);
		assertEquals(map.getChain(2).get(0), "two");
		
		map.clear();
		assertEquals(map.size(),0);
	}
	
	@Test
	public void testTwoEntriesSameChain() {
		map.put(1, "one");
		assertEquals(map.size(), 1);
		
		map.put(1, "two");
		assertEquals(map.size(), 1);
		assertEquals(map.keySet().size(), 1);
		assertEquals(map.getChain(1).get(0), "one");
		assertEquals(map.getChain(1).get(1), "two");
		
		map.clear();
		assertEquals(map.size(),0);
	}
	
	@Test
	public void testCopyInsertChain() {
		List<String> chain = new LinkedList<String>();
		chain.add("one");
		chain.add("two");
		
		map.putChain(1,chain);
		assertEquals(map.size(),1);
		
		List<String> chain2 = map.getChain(1);
		assertEquals(chain2.get(0),"one");
		assertEquals(chain2.get(1),"two");
	}
	
	@Test(expected=NotImplementedException.class)
    public void testStub1() {
        map.containsKey(null);
    }
	
	@Test(expected=NotImplementedException.class)
    public void testStub2() {
        map.containsValue(null);
    }
	
	@Test(expected=NotImplementedException.class)
    public void testStub3() {
        map.get(null);
    }

	@Test(expected=NotImplementedException.class)
    public void testStub4() {
        map.isEmpty();
    }
	
	@Test(expected=NotImplementedException.class)
    public void testStub5() {
        map.putAll(null);
    }
	
	@Test(expected=NotImplementedException.class)
    public void testStub6() {
        map.remove(null);
    }
	
	@Test(expected=NotImplementedException.class)
    public void testStub7() {
        map.values();
    }
	
	@Test(expected=NotImplementedException.class)
    public void testStub8() {
        map.entrySet();
    }
}
