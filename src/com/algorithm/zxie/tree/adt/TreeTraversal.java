package com.algorithm.zxie.tree.adt;

public interface TreeTraversal {
	
	/**
	 * visit binary tree pre-order
	 * @param root binary tree root node
	 */
	public String preOrder();

	/**
	 * visit binary tree post-order
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	public String postOrder();

	/**
	 * visit binary tree in-order
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	public String inOrder();

	/**
	 * visit binary tree per level
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	public String levelOrder();
}
