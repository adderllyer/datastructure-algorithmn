package com.algrithm.datastructure.btree;

public interface Tree<K extends Comparable<K>, V extends Object> {
	
	/**
	 * Search the B+ tree with key, and return null or element which key value is key
	 * @param key element's key use for search
	 * @return null or object which key value is key
	 */
	public V get(K key); 
	
	/**
	 * Remove the object from the B+ tree which key value is key
	 * @param key the element's key will be removed
	 */
    public void remove(K key); 
    
    /**
     * insert element or update element
     * @param key  the insert or update object key
     * @param obj  the object to be inserted or updated
     */
    public void insertOrUpdate(K key, V obj);
}
