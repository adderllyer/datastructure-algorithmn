package com.algorithm.zxie.tree.rbtree;

import java.util.LinkedList;
import java.util.Queue;

import com.algorithm.zxie.tree.adt.BinarySearchTree;
import com.algorithm.zxie.tree.adt.TreeTraversal;

public class RBTreeImpl<T extends Comparable<? super T>> implements BinarySearchTree<T>, TreeTraversal {

	class RBTreeNode{
		T data;
		boolean isRed;
		RBTreeNode leftChild;
		RBTreeNode rightChild;

		public RBTreeNode(T data){
			this(data, true);
		}

		public RBTreeNode(T data, boolean ir){
			this(data,ir,null,null);
		}

		public RBTreeNode(T data, boolean ir, RBTreeNode l, RBTreeNode r){
			this.data = data;
			this.isRed = ir;
			this.leftChild = l;
			this.rightChild = r;
		}
	}

	RBTreeNode root;

	public RBTreeImpl(T data){
		this.root = new RBTreeNode(data, false);
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
	public boolean search(T data) {
		return search(this.root, data);
	}

	@Override
	public void insert(T data) throws Exception {
		this.root = insert(this.root, data);
	}

	@Override
	public void delete(T data) throws Exception {
		RBTreeNode node = getDelNode(this.root, data);
		System.out.println(node.data.toString());
		if(node.isRed) 
			deleteRNode(this.root, data);
		else
			deleteBNode(this.root, data);
	}



	/**
	 * Internal method to perform a actual search,
	 * find the data in the tree which root node is root
	 * @param root tree root node
	 * @param data value for search
	 * @return true if find the result, else false
	 */
	private boolean search(RBTreeNode root, T data) {
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
	private RBTreeNode insert(RBTreeNode root, T data) throws Exception{
		if(root == null) 
			root = new RBTreeNode(data);
		else if(data.compareTo(root.data) < 0){
			root.leftChild = insert(root.leftChild, data);

			//justify tree
			if(!root.isRed){
				if(isRed(root.rightChild) && isRed(root.leftChild)){//LLr or LRr justify node color
					if(isRed(root.leftChild.leftChild) || isRed(root.leftChild.rightChild))
					{// remember to change the right node's color to black
						root.isRed = true;
						root.leftChild.isRed = false;
						root.rightChild.isRed = false;
					}
				}
				else if(!isRed(root.rightChild) && isRed(root.leftChild)){//LLb or LRb rotate tree and justify node color
					if(isRed(root.leftChild.leftChild)){
						root.isRed = true;
						root.leftChild.isRed = false;
						root = rotateWithleftChild(root);
					}
					else if(isRed(root.leftChild.rightChild)){
						root.isRed = true;
						root.leftChild.rightChild.isRed = false;
						root = doubleWithleftChild(root);
					}					
				}
			}
		}
		else if(data.compareTo(root.data) > 0){
			root.rightChild = insert(root.rightChild, data);

			//justify tree
			if(!root.isRed){
				if(isRed(root.leftChild) && isRed(root.rightChild)){//RRr or RLr justify node color
					if(isRed(root.rightChild.rightChild) || isRed(root.rightChild.leftChild)){
						root.isRed = true;
						root.rightChild.isRed = false;
						// remember to change the left node's color to black
						root.leftChild.isRed = false;

					}
				}
				else if(!isRed(root.leftChild) && isRed(root.rightChild)){//RRb or RLb rotate tree and justify node color
					if(isRed(root.rightChild.rightChild)){
						root.isRed = true;
						root.rightChild.isRed = false;
						root = rotateWithrightChild(root);
					}
					else if(isRed(root.rightChild.leftChild)){
						root.isRed = true;
						root.rightChild.leftChild.isRed = false;
						root = doubleWithrightChild(root);
					}
				}
			}
		}
		else 
			throw new Exception("Attemping to insert duplicate value");
		if(this.root == root && isRed(root))//change tree root node color to black if root node color is red
			root.isRed = false;
		return root;		
	}

	/**
	 * delete one node from AVL Tree
	 * @param root AVL Tree root node
	 * @param data node to be deleted
	 * @return AVL Tree after one node delete
	 */
	private RBTreeNode deleteBNode(RBTreeNode root, T data) throws Exception{
		if(root == null)
			;
		else if(data.compareTo(root.data) < 0){
			root.leftChild = deleteBNode(root.leftChild, data);
			root = justifyWithLeft(root);
		}
		else if(data.compareTo(root.data) > 0){
			root.rightChild = deleteBNode(root.rightChild, data);
			root = justifyWithRight(root);
		}
		else if(data.compareTo(root.data) == 0){
			RBTreeNode node = null;
			if(root.rightChild != null){
				node = findMinNode(root.rightChild);
				root.data = node.data;
				root.rightChild = deleteBNode(root.rightChild, root.data);
				root = justifyWithRight(root);
			}
			else if(root.leftChild != null){
				node = findMaxNode(root.leftChild);
				root.data = node.data;
				root.leftChild = deleteBNode(root.leftChild, root.data);
				root = justifyWithLeft(root);
			}
			else
				root = null;
		}
		else
			throw new Exception("Attemping to delete not existing value");

		if(this.root == root && isRed(root))//change tree root node color to black if root node color is red
			root.isRed = false;
		return root;
	}

	/**
	 * delete one red node, no need to justify tree
	 * @param root tree root node
	 * @param data data value will be delete
	 * @return tree root node 
	 */
	private RBTreeNode deleteRNode(RBTreeNode root, T data){
		if(root == null);
		if(data.compareTo(root.data) > 0)
			root.rightChild = deleteRNode(root.rightChild, data);
		else if(data.compareTo(root.data) < 0)
			root.leftChild = deleteRNode(root.leftChild, data);
		else{
			RBTreeNode node;
			if(root.rightChild != null){
				node = findMinNode(root.rightChild);
				root.data = node.data;
				root.rightChild = deleteRNode(root.rightChild, root.data);
			}
			else if(root.leftChild != null){
				node = findMaxNode(root.leftChild);
				root.data = node.data;
				root.leftChild = deleteRNode(root.leftChild, root.data);
			}
			else
				root = null;
		}

		return root;
	}

	/**
	 * find the really node will be delete
	 * @param root tree root node
	 * @param data delete value
	 * @return node will be deleted 
	 */
	private RBTreeNode getDelNode(RBTreeNode root, T data) throws Exception{
		RBTreeNode node = null;
		if(root == null)
			;
		else if(data.compareTo(root.data) > 0){
			node = getDelNode(root.rightChild, data);
		}
		else if(data.compareTo(root.data) < 0){
			node = getDelNode(root.leftChild, data);
		}
		else if(data.compareTo(root.data) == 0){
			if(root.rightChild != null)
				node = findMinNode(root.rightChild);
			else if(root.leftChild != null)
				node = findMaxNode(root.leftChild);
			else
				node = root;
		}
		else
			throw new Exception("Attemping to delete not exist value");
		return node;
	}

	/**
	 * justify RB tree which one node was deleted from left child tree
	 * @param root tree root node
	 * @return tree after justified
	 */
	private RBTreeNode justifyWithLeft(RBTreeNode root){
		// justify tree
		if(!isRed(root.rightChild)){//Lb0, Lb1, Lb2
			if(!isRed(root.rightChild.leftChild) && !isRed(root.rightChild.rightChild)){//Lb0, change nodes' color
				root.isRed = false;
				root.rightChild.isRed = true;
			}
			else if(!isRed(root.rightChild.leftChild) && isRed(root.rightChild.rightChild)){//Lb1(i)
				root.rightChild.rightChild.isRed = false;
				root.rightChild.isRed = root.isRed;
				root.isRed = false;
				root = rotateWithrightChild(root);
			}
			else if(isRed(root.rightChild.leftChild)){// Lb1(ii), Lb2
				root.rightChild.leftChild.isRed = root.isRed;
				root.isRed = false;
				root = doubleWithrightChild(root);
			}
		}
		else if(isRed(root.rightChild)){//Lr0, Lr1, Lr2
			if(!isRed(root.rightChild.leftChild) && !isRed(root.rightChild.rightChild)){//Lr0
				this.root.rightChild.isRed = false;
				this.root.rightChild.leftChild.isRed = true;
				root = rotateWithrightChild(root);
			}
			else if(isRed(root.rightChild.leftChild.rightChild) && !isRed(root.rightChild.leftChild.leftChild)){//Lr1(i)
				root.rightChild.leftChild.rightChild.isRed = false;
				root = doubleWithrightChild(root);
			}
			else if(isRed(root.rightChild.leftChild.leftChild)){ // Lr1(ii), Lr2
				root.rightChild.leftChild.leftChild.isRed = false;
				root.rightChild.leftChild = rotateWithleftChild(root.rightChild.leftChild);
				root = doubleWithrightChild(root);
			}
		}
		return root;
	}

	/**
	 * justify RB tree which one node was deleted from right tree
	 * @param root tree root node
	 * @return RB tree after justified 
	 */
	private RBTreeNode justifyWithRight(RBTreeNode root)
	{
		// justify tree
		if(isRed(root.rightChild)){
			
		}
		else if(!isRed(root.leftChild)){//Rb0,Rb1,Rb2
			if(!isRed(root.leftChild.leftChild) && !isRed(root.leftChild.rightChild)){//Rb0, change nodes' color
				root.isRed = false;
				root.leftChild.isRed = true;
			}
			else if(isRed(root.leftChild.leftChild) && !isRed(root.leftChild.rightChild)){//Rb1(i),
				root.leftChild.leftChild.isRed = false;
				root.leftChild.isRed = root.isRed;
				root.isRed = false;
				root = rotateWithleftChild(root);
			}
			else if(isRed(root.leftChild.rightChild)){//Rb1(ii), Rb2
				root.leftChild.rightChild.isRed = root.isRed;
				root.isRed = false;
				root = doubleWithleftChild(root);
			}
		}
		else if(isRed(root.leftChild)){//Rr0, Rr1, Rr2
			if(!isRed(root.leftChild.leftChild) && !isRed(root.leftChild.rightChild)){// Rr0
				root.leftChild.isRed = false;
				root.leftChild.rightChild.isRed = true;
				root = rotateWithleftChild(root);
			}
			else if(isRed(root.leftChild.rightChild.leftChild) && !isRed(root.leftChild.rightChild.rightChild)){//Rr1(i)
				root.leftChild.rightChild.leftChild.isRed = false;
				root = doubleWithleftChild(root);
			}
			else if(isRed(root.leftChild.rightChild.rightChild)){//Rr1(ii), Rr2
				root.leftChild.rightChild.rightChild.isRed = false;
				root.leftChild.rightChild = rotateWithrightChild(root.leftChild.rightChild);
				root = doubleWithleftChild(root);
			}
		}
		return root;
	}

	/**
	 * Rotate binary tree node with leftChild child.
	 * For AVL trees, this is a single rotation for case 1.
	 * Update heights, then return new root.
	 * 
	 * @param k2 Root of tree we are rotating
	 * @return New root
	 */
	private RBTreeNode rotateWithleftChild (RBTreeNode k2){
		RBTreeNode k1 = k2.leftChild;
		k2.leftChild = k1.rightChild;
		k1.rightChild = k2;
		return (k1);
	}

	/**
	 * Rotate binary tree node with rightChild child.
	 * For AVL trees, this is a single rotation for case 4.
	 * Update heights, then return new root.
	 * 
	 * @param k1 Root of tree we are rotating.
	 * @return New root
	 */
	private RBTreeNode rotateWithrightChild (RBTreeNode k1){
		RBTreeNode k2 = k1.rightChild;
		k1.rightChild = k2.leftChild;
		k2.leftChild = k1;
		return (k2);
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
	private RBTreeNode doubleWithleftChild (RBTreeNode k3){
		k3.leftChild = rotateWithrightChild (k3.leftChild);
		return rotateWithleftChild (k3);
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
	private RBTreeNode doubleWithrightChild(RBTreeNode k1){
		k1.rightChild = rotateWithleftChild(k1.rightChild);
		return rotateWithrightChild (k1);
	}

	/**
	 * visit binary tree pre-order
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	private String preOrder(RBTreeNode root){
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
	private String postOrder(RBTreeNode root){
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
	private String inOrder(RBTreeNode root){
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
	private String levelOrder(RBTreeNode root){
		if(root == null) return null;
		String result = "";
		Queue<RBTreeNode> queue = new LinkedList<>();
		RBTreeNode node = root;
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
	 * check node if it is a red node
	 * @param node RBTreeNode to be check
	 * @return true if it is a red node, or return false. if node is null, return false 
	 */
	private boolean isRed(RBTreeNode node){
		return (node == null)? false : node.isRed;
	}


	/**
	 * find the max value node
	 * @param node tree root node
	 * @return the node with the max value 
	 */
	private RBTreeNode findMaxNode(RBTreeNode node){
		if(node == null) return null;
		while(node.rightChild != null)
			node = node.rightChild;
		return node;
	}

	/**
	 * find the min value of a AVLTree
	 * @param node root node
	 * @return min value 
	 */
	private RBTreeNode findMinNode(RBTreeNode node){
		if(node == null) return null;
		while(node.leftChild != null)
			node = node.leftChild;
		return node;
	}

	/**
	 * visit one RBTreeNode and print its element value
	 * @param node tree node to be visit
	 */
	private String visit(RBTreeNode node){
		return "("+node.data.toString()+","+node.isRed+") ";
	}	

	public static void main(String[] args){
		RBTreeImpl<Integer> t = new RBTreeImpl<>(8);
		try {
			t.insert (new Integer(3));
			t.insert (new Integer(4));
			t.insert (new Integer(2));
			t.insert (new Integer(10));
			t.insert (new Integer(5));
			t.insert (new Integer(6));
			t.insert (new Integer(7));
			t.insert (new Integer(9));
			t.insert (new Integer(0));
			t.insert (new Integer(11));
			t.insert (new Integer(12));
			/*
			t.insert (new Integer(13));
			t.insert (new Integer(14));
			t.insert (new Integer(15));
			t.insert (new Integer(16));
			t.insert (new Integer(17));
			t.insert (new Integer(18));
			 */

			System.out.println ("Infix Traversal:");

			System.out.println(t.preOrder());


			t.delete(new Integer(11));
			System.out.println ("Infix Traversal after remove 4:");
			System.out.println(t.preOrder());
			t.delete(new Integer(12));
			System.out.println ("Infix Traversal after remove 4:");
			System.out.println(t.preOrder());

			/*
			t.insert(new Integer(4));
			System.out.println ("Infix Traversal after insert 4:");
			System.out.println(t.inOrder());

			t.delete(new Integer(7));
			System.out.println ("Infix Traversal after remove 7:");
			System.out.println(t.inOrder());

			t.delete(new Integer(6));
			System.out.println ("Infix Traversal after remove 6:");
			System.out.println(t.inOrder());
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
