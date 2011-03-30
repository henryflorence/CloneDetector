package com.github.saniul.clonedetector;

import java.util.List;
import java.util.Map;

public interface ChainedMap<K, V> extends Map<K,V>{
	
	List<V> getChain(K arg0);

	void putChain(K arg0, List<V> list);

	void addChain(K arg0, List<V> list);

}