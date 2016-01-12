package net.shared;

import java.io.Serializable;

public class Pair<K, V> implements Serializable{
	private static final long serialVersionUID = 561470688889968330L;

	public K k;
	public V v;
	
	public Pair(K k, V v) {
		this.k = k;
		this.v = v;
	}
	
	public Pair(){}

}
