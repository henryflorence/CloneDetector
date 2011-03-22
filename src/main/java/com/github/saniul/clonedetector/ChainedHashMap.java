package com.github.saniul.clonedetector;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;

public class ChainedHashMap<K,V> implements Map<K,V> {
	private Map<K,List<V>> map = new HashMap<K,List<V>>();
	//@Override
	public void clear() {
		map.clear();
	}

	//@Override
	public boolean containsKey(Object arg0) {
		throw new NotImplementedException();
	}

	//@Override
	public boolean containsValue(Object arg0) {
		throw new NotImplementedException();
	}

	//@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new NotImplementedException();
	}

	//@Override
	public V get(Object arg0) {
		throw new NotImplementedException();
	}
	public List<V> getChain(K arg0) {
		return map.get(arg0);
	}
	//@Override
	public boolean isEmpty() {
		throw new NotImplementedException();
	}

	//@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	//@Override
	public V put(K arg0, V arg1) {
		if(map.containsKey(arg0)) {
			map.get(arg0).add(arg1);
			return null;
		}
		List<V> list = new LinkedList<V>();
		list.add(arg1);
		map.put(arg0,list);
		return null;
	}
	public void putChain(K arg0, List<V> list) {
		map.put(arg0,list);
	}
	//@Override
	public void putAll(Map<? extends K, ? extends V> arg0) {
		throw new NotImplementedException();
	}

	//@Override
	public V remove(Object arg0) {
		throw new NotImplementedException();
	}

	//@Override
	public int size() {
		return map.size();
	}

	//@Override
	public Collection<V> values() {
		throw new NotImplementedException();
	}
}
