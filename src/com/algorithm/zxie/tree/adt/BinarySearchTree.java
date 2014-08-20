package com.algorithm.zxie.tree.adt;


public interface BinarySearchTree<T extends Comparable<? super T>> {
	/**
	 * search the tree and find the specific data if exist
	 * @param root tree root node
	 * @param data element data to search
	 * @return true if data exist, or return false
	 */
	public boolean search(T data);
	
	/**
	 * insert one tree node with value data to the tree, 
	 * if data greater than root value, insert to right child,
	 * if data less than root value, insert to left child,
	 * if data equal to root value, doesn't insert and return
	 * @param data node value
	 * @return the binary search tree
	 */
	public void insert(T data) throws Exception;
	
	/**
	 * delete one tree node which value is data 
	 * @param root tree root node
	 * @param data node value
	 * @return the binary search tree
	 */
	public void delete(T data) throws Exception;

}
