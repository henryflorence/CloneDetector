package com.github.saniul.clonedetector;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;

public class ChainedHashMap<K,V> implements Map<K,V> {
	private Map<K,List<V>> map = new HashMap<K,List<V>>();
	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V get(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<V> getChain(K arg0) {
		return map.get(arg0);
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
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
		if(map.containsKey(arg0))
			map.get(arg0).addAll(list);
		map.put(arg0,list);
	}
	@Override
	public void putAll(Map<? extends K, ? extends V> arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public V remove(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

}
