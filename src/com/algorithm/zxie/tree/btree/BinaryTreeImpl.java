package com.algorithm.zxie.tree.btree;

import java.util.LinkedList;
import java.util.Queue;

import com.algorithm.zxie.tree.adt.BinaryTree;
import com.algorithm.zxie.tree.adt.TreeTraversal;

public class BinaryTreeImpl<T  extends Comparable<? super T>> implements BinaryTree,TreeTraversal{

	public BinaryTreeNode root;
	
	class BinaryTreeNode{
		T data;
		BinaryTreeNode leftChild;
		BinaryTreeNode rightChild;
		
		public BinaryTreeNode(){
			
		}
		
		public BinaryTreeNode(T data){
			this.data = data;
		}
		
		public BinaryTreeNode(T data, BinaryTreeNode l, BinaryTreeNode r){
			this.data = data;
			this.leftChild = l;
			this.rightChild = r;
		}
	}

	@Override
	public String preOrder() {
		return preOrder(this.root);
	}

	@Override
	public String postOrder() {
		return postOrder(this.root);
	}

	@Override
	public String inOrder() {
		return inOrder(this.root);
	}

	@Override
	public String levelOrder() {
		return levelOrder(this.root);
	}

	@Override
	public int height() {
		return height(this.root);
	}

	@Override
	public int count() {
		return count(this.root);
	}

	/**
	 * visit binary tree pre-order
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	private String preOrder(BinaryTreeNode root){
		if(root == null) return null;
		String result = "";
		result += visit(root);
		if(root.leftChild != null) 
			result += preOrder(root.leftChild);
		if(root.rightChild != null)
			result += preOrder(root.rightChild);
		return result;
	}

	/**
	 * visit binary tree post-order
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	private String postOrder(BinaryTreeNode root){
		if(root == null)return null;
		String result = "";
		if(root.leftChild != null) 
			result += postOrder(root.leftChild);
		if(root.rightChild != null)
			result += postOrder(root.rightChild);
		result += visit(root);
		return result;
	}

	/**
	 * visit binary tree in-order
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	private String inOrder(BinaryTreeNode root){
		if(root == null)return null;
		String result = "";
		if(root.leftChild != null) 
			result += inOrder(root.leftChild);
		result += visit(root);
		if(root.rightChild != null)
			result += inOrder(root.rightChild);
		return result;
	}

	/**
	 * visit binary tree per level
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	private String levelOrder(BinaryTreeNode root){
		if(root == null) return null;
		String result = "";
		Queue<BinaryTreeNode> queue = new LinkedList<>();
		BinaryTreeNode node = root;
		queue.add(node);
		while(!queue.isEmpty()){
			node = queue.remove();
			result += visit(node);
			if(node.leftChild != null)
				queue.add(node.leftChild);
			if(node.rightChild != null)
				queue.add(node.rightChild);
		}
		return result;
	}

	/**
	 * build a binary tree
	 * @param data node value
	 * @param left left sub-tree
	 * @param right right sub-tree 
	 */
	public final void buildTree(T data, BinaryTreeImpl<T> left, BinaryTreeImpl<T> right){
		this.root = new BinaryTreeNode(data,left.root,right.root);
		left.root = right.root = null;
	}

	/**
	 * delete the whole binary tree
	 * @param tree BinaryTreeNode, root node of a binary tree
	 */
	public final void deleteTree(BinaryTreeNode root) {
		this.deleteAllNodes(root);
		this.root = null;
	}

	/**
	 * count the binary tree height
	 * @param root tree root node
	 * @return tree height
	 */
	private int height(BinaryTreeNode root){
		if(root == null) return 0;
		int hl = height(root.leftChild);
		int hr = height(root.rightChild);
		if(hl>=hr)
			return hl+1;
		else
			return hr+1;
	}

	/**
	 * count the number of tree nodes
	 * @param root tree root node
	 * @return tree node number
	 */
	private int count(BinaryTreeNode root){
		if(root == null) return 0;
		int num = 0;
		num += count(root.leftChild);
		num += count(root.rightChild);
		return num+1;
	}

	/**
	 * delete the whole binary tree
	 * @param root BinaryTreeNode, root node of a binary tree
	 */
	private void deleteAllNodes(BinaryTreeNode root){
		if(root == null)return;
		if(root.leftChild != null) 
			deleteAllNodes(root.leftChild);		
		if(root.rightChild != null)
			deleteAllNodes(root.rightChild);
		deleteBinaryTreeNode(root);
	}

	/**
	 * get the root node
	 * @return root node
	 */
	public BinaryTreeNode getRoot(){
		return this.root;
	}

	/**
	 * delete a BinaryTreeNode (why node is not null?)
	 * @param node tree node to be deleted
	 */
	private void deleteBinaryTreeNode( BinaryTreeNode node){
		node.data = null;
		node.leftChild = null;
		node.rightChild = null;
	}

	/**
	 * visit one BinaryTreeNode and print its element value
	 * @param node tree node to be visit
	 */
	private String visit(BinaryTreeNode node){
		return node.data.toString()+" ";
	}

}
