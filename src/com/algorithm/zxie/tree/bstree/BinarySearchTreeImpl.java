package com.algorithm.zxie.tree.bstree;

import com.algorithm.zxie.tree.adt.BinarySearchTree;

public class BinarySearchTreeImpl<T extends Comparable<? super T>> implements BinarySearchTree<T> {

	BinaryTreeNode root;

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
	public boolean search(T data) {
		return search(this.root, data);
	}

	@Override
	public void delete(T data) throws Exception{
		this.root = delete(this.root, data);

	}

	@Override
	public void insert(T data) throws Exception{
		this.root = insert(this.root, data);
	}

	/**
	 * delete a node which value is data
	 * @param root tree root node
	 * @param data node value, this node will be deleted
	 */
	private BinaryTreeNode delete(BinaryTreeNode root, T data) throws Exception{
		if (root != null) {
			if (data.compareTo(root.data) < 0)
				root.leftChild = delete(root.leftChild, data);
			else if (data.compareTo(root.data) > 0)
				root.rightChild = delete(root.rightChild, data);
			else if(data.compareTo(root.data) == 0)
			{
				if (root.leftChild == null)//data equal root.data and leftchild is null
					root = root.rightChild;
				else if (root.rightChild == null)//data equal root.data and rightchild is null
					root = root.leftChild;
				else {//data equal root.data and leftchild and rightchild are not null
					root.data = findMin(root.rightChild);
					root.rightChild = delete(root.rightChild, root.data);
				}
			}
			else
				throw new Exception("Attempting to delete not existing value");
		}
		return root;
	}

	/**
	 * find the min value
	 * @param root tree root node
	 * @return min node value 
	 */
	private T findMin(BinaryTreeNode root){
		if(root != null){
			while(root.leftChild != null)
				root = root.leftChild;
			return root.data;
		}
		return null;
	}

	/**
	 * insert data to binary search tree
	 * @param root tree root node
	 * @param data node data
	 * @return tree root node
	 */
	private BinaryTreeNode insert(BinaryTreeNode root, T data) throws Exception{
		if (root == null)
			root = new BinaryTreeNode(data);
		else if (data.compareTo(root.data) < 0)
			root.leftChild = insert(root.leftChild, data);
		else if (data.compareTo(root.data) > 0)
			root.rightChild = insert(root.rightChild, data);
		else
			throw new Exception("Attempting to insert duplicate value");
		return root;
	}

	/**
	 * find the data in the tree which root node is root
	 * @param root tree root node
	 * @param data value for search
	 * @return true if find the result, else false
	 */
	private boolean search(BinaryTreeNode root, T data) {
		if(root == null) return false;
		if(root.data.compareTo(data) == 0) return true;
		else if(root.data.compareTo(data) > 0)
			return search(root.leftChild,data);
		else 
			return search(root.rightChild,data);
	}

	/**
	 * visit binary tree pre-order
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	public String preOrder(BinaryTreeNode root){
		if(root == null) return null;
		String result = "";
		result += visit(root);
		if(root.leftChild != null) 
			result += preOrder(root.leftChild);
		if(root.rightChild != null)
			result += preOrder(root.rightChild);
		return result;
	}

	private String visit(BinaryTreeNode node){
		return node.data.toString()+" ";
	}


	public static void main(String[] args){
		BinarySearchTreeImpl<String> bst = new BinarySearchTreeImpl<>();
		String[] arr = {"7","5","3","6","9","8","11"};
		for(int i=0; i< arr.length; i++){
			try {
				bst.insert(arr[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(bst.preOrder(bst.root));
	}

}
