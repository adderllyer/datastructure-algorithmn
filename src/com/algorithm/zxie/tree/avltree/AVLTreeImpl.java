package com.algorithm.zxie.tree.avltree;

import java.util.LinkedList;
import java.util.Queue;

import com.algorithm.zxie.tree.adt.BinarySearchTree;
import com.algorithm.zxie.tree.adt.TreeTraversal;

public class AVLTreeImpl<T extends Comparable<? super T>> implements BinarySearchTree<T>,TreeTraversal {

	public class AVLTreeNode{

		T data;
		int height;
		AVLTreeNode leftChild;
		AVLTreeNode rightChild;

		public AVLTreeNode(T data){
			this(data,0);
		}

		public AVLTreeNode(T data, int h){
			this(data,h,null,null);
		}

		public AVLTreeNode(T data, int h, AVLTreeNode l, AVLTreeNode r){
			this.data = data;
			this.height = h;
			this.leftChild = l;
			this.rightChild = r;
		}

	}

	public AVLTreeNode root;

	public AVLTreeImpl (){
		root = null;	
	}

	@Override
	public boolean search(T data) {
		return search(this.root, data);
	}

	@Override
	public void insert(T data) throws Exception {
		this.root = insert(this.root, data);
	}

	@Override
	public void delete(T data) throws Exception {
		this.root = delete(this.root, data);
	}

	/**
	 * find the data in the tree which root node is root
	 * @param root tree root node
	 * @param data value for search
	 * @return true if find the result, else false
	 */
	private boolean search(AVLTreeNode root, T data) {
		if(root == null) return false;
		if(root.data.compareTo(data) == 0) return true;
		else if(root.data.compareTo(data) > 0)
			return search(root.leftChild,data);
		else 
			return search(root.rightChild,data);
	}

	/**
	 * Internal method to perform an actual insertion.
	 * 
	 * @param x data to add
	 * @param t Root of the tree
	 * @return New root of the tree
	 * @throws Exception 
	 */
	private AVLTreeNode insert(AVLTreeNode t,T x) throws Exception{
		if (t == null)
			t = new AVLTreeNode(x);
		else if (x.compareTo(t.data) < 0){
			t.leftChild = insert(t.leftChild, x);

			if (height(t.leftChild) - height(t.rightChild) == 2){
				if (x.compareTo(t.leftChild.data) < 0){
					t = rotateWithleftChild(t);
				}
				else {
					t = doubleWithleftChild(t);
				}
			}
		}
		else if (x.compareTo (t.data) > 0){
			t.rightChild = insert (t.rightChild, x);

			if ( height(t.rightChild) - height(t.leftChild) == 2)
				if (x.compareTo (t.rightChild.data) > 0){
					t = rotateWithrightChild (t);
				}
				else{
					t = doubleWithrightChild (t);
				}
		}
		else {
			throw new Exception("Attempting to insert duplicate value");
		}

		t.height = max(height(t.leftChild), height(t.rightChild)) + 1;
		return t;
	}

	/**
	 * delete one node from AVL Tree
	 * @param t AVL Tree root node
	 * @param x node to be deleted
	 * @return AVL Tree after one node delete
	 */
	private AVLTreeNode delete(AVLTreeNode t, T x) throws Exception{
		if(t == null) return t;
		else if(x.compareTo(t.data) < 0){// delete node in left child tree
			t.leftChild = delete(t.leftChild, x);

			if(height(t.leftChild) - height(t.rightChild) == -2){
				if((height(t.rightChild.leftChild) - height(t.rightChild.rightChild)) <= 0)
					t = rotateWithrightChild(t);
				else
					t = doubleWithrightChild(t);
			}
		}
		else if(x.compareTo(t.data) > 0){// delete node in right child tree
			t.rightChild = delete(t.rightChild, x);

			if(height(t.leftChild) - height(t.rightChild) == 2){
				if((height(t.leftChild.leftChild) - height(t.leftChild.rightChild)) >= 0)
					t = rotateWithleftChild(t);	
				else
					t = doubleWithleftChild(t);
			}
		}
		else if(x.compareTo(t.data) == 0){
			if(t.leftChild == null)
				t = t.rightChild;
			else if(t.rightChild == null)
				t = t.leftChild;
			else{
				t.data = findMin(t.rightChild);
				t.rightChild = delete(t.rightChild, t.data);
				if(height(t.leftChild) - height(t.rightChild) == 2){
					if((height(t.rightChild.leftChild) - height(t.rightChild.rightChild)) <= 0)
						t = rotateWithrightChild(t);
					else
						t = doubleWithrightChild(t);
				}
			}
		}
		else
			throw new Exception("Attempting to delete not existing value");
		if(t != null)
			t.height = max(height(t.leftChild), height(t.rightChild)) + 1;

		return t;
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

	/**
	 * Rotate binary tree node with leftChild child.
	 * For AVL trees, this is a single rotation for case 1.
	 * Update heights, then return new root.
	 * 
	 * @param k2 Root of tree we are rotating
	 * @return New root
	 */
	private AVLTreeNode rotateWithleftChild (AVLTreeNode k2){
		AVLTreeNode k1 = k2.leftChild;

		k2.leftChild = k1.rightChild;
		k1.rightChild = k2;

		k2.height = max (height(k2.leftChild), height(k2.rightChild)) + 1;
		k1.height = max (height(k1.leftChild), k2.height) + 1;

		return (k1);
	}

	/**
	 * Double rotate binary tree node: first leftChild child
	 * with its rightChild child; then node k3 with new leftChild child.
	 * For AVL trees, this is a double rotation for case 2.
	 * Update heights, then return new root.
	 * 
	 * @param k3 Root of tree we are rotating
	 * @return New root
	 */
	private AVLTreeNode doubleWithleftChild (AVLTreeNode k3){
		k3.leftChild = rotateWithrightChild (k3.leftChild);
		return rotateWithleftChild (k3);
	}

	/**
	 * Rotate binary tree node with rightChild child.
	 * For AVL trees, this is a single rotation for case 4.
	 * Update heights, then return new root.
	 * 
	 * @param k1 Root of tree we are rotating.
	 * @return New root
	 */
	private AVLTreeNode rotateWithrightChild (AVLTreeNode k1){
		AVLTreeNode k2 = k1.rightChild;

		k1.rightChild = k2.leftChild;
		k2.leftChild = k1;

		k1.height = max(height(k1.leftChild), height(k1.rightChild)) + 1;
		k2.height = max(height(k2.rightChild), k1.height) + 1;

		return (k2);
	}

	/**
	 * Double rotate binary tree node: first rightChild child
	 * with its leftChild child; then node k1 with new rightChild child.
	 * For AVL trees, this is a double rotation for case 3.
	 * Update heights, then return new root.
	 * 
	 * @param k1 Root of tree we are rotating
	 * @return New root
	 */
	private AVLTreeNode doubleWithrightChild(AVLTreeNode k1){
		k1.rightChild = rotateWithleftChild(k1.rightChild);
		return rotateWithrightChild (k1);
	}



	/**
	 * visit binary tree pre-order
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	private String preOrder(AVLTreeNode root){
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
	private String postOrder(AVLTreeNode root){
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
	private String inOrder(AVLTreeNode root){
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
	private String levelOrder(AVLTreeNode root){
		if(root == null) return null;
		String result = "";
		Queue<AVLTreeNode> queue = new LinkedList<>();
		AVLTreeNode node = root;
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
	 * Determine the height of the given node.
	 * 
	 * @param t Node
	 * @return Height of the given node.
	 */
	private int height(AVLTreeNode t){
		return t == null ? -1 : t.height;
	}

	/**
	 * Find the maximum value among the given numbers.
	 * 
	 * @param a First number
	 * @param b Second number
	 * @return Maximum value
	 */	
	private int max(int a, int b){
		if (a > b)
			return a;
		return b;
	}

	/**
	 * find the min value of a AVLTree
	 * @param t root node
	 * @return min value 
	 */
	private T findMin(AVLTreeNode t){
		if(t == null) return null;
		while(t.leftChild != null)
			t = t.leftChild;
		return t.data;
	}


	/**
	 * visit one AVLTreeNode and print its element value
	 * @param node tree node to be visit
	 */
	private String visit(AVLTreeNode node){
		return "("+node.data.toString()+","+node.height+") ";
	}

	/**
	 * Main entry point; contains test code for the tree.
	 */
	public static void main(String[] args){
		AVLTreeImpl<Integer> t = new AVLTreeImpl<Integer>();

		try {
			t.insert (new Integer(2));

			t.insert (new Integer(1));
			t.insert (new Integer(4));
			t.insert (new Integer(5));
			t.insert (new Integer(9));
			t.insert (new Integer(3));
			t.insert (new Integer(6));
			t.insert (new Integer(7));

			System.out.println ("Infix Traversal:");

			System.out.println(t.inOrder());

			t.delete(new Integer(4));
			System.out.println ("Infix Traversal after remove 4:");
			System.out.println(t.inOrder());

			t.insert(new Integer(4));
			System.out.println ("Infix Traversal after insert 4:");
			System.out.println(t.inOrder());

			t.delete(new Integer(7));
			System.out.println ("Infix Traversal after remove 7:");
			System.out.println(t.inOrder());

			t.delete(new Integer(6));
			System.out.println ("Infix Traversal after remove 6:");
			System.out.println(t.inOrder());

			System.out.println ("Prefix Traversal:");
			System.out.println(t.preOrder());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
