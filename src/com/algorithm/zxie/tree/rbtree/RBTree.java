package com.algorithm.zxie.tree.rbtree;

import java.util.LinkedList;
import java.util.Queue;

import com.algorithm.zxie.tree.adt.BinarySearchTree;
import com.algorithm.zxie.tree.adt.TreeTraversal;


/**
 * RBTree
 * <p/>
 * An implementation of a Red Black Tree with
 * distinct T-typed values
 */

public class RBTree<T extends Comparable<? super T>> implements BinarySearchTree<T>,TreeTraversal{

	/**
	 * public class RBNode
	 * <p/>
	 * If you wish to implement classes other than RBTree
	 * (for example RBNode), do it in this file, not in
	 * another file
	 */
	public class RBNode {
		public RBNode right;
		public RBNode left;
		public RBNode parent;
		public T key;
		public RBNodeColor color = RBTree.RBNodeColor.Red; //default color of node is red.

		public RBNode(T _key) {
			this();
			key = _key;
		}

		public RBNode() {
			right = null;
			left = null;
			parent = null;
		}

		public RBNode(T k, RBNode l, RBNode r, RBNode p) {
			right = r;
			left = l;
			parent = p;
			key = k;
		}

	}

	public enum RBNodeColor {
		Red, Black
	}

	private RBNode _nullNode;  //The Null Node
	private RBNode _root;   // Root of the the tree
	private boolean _isBalanced;
	private int _nodeCount;

	public RBTree() {
		//set null node object
		_nullNode = new RBNode();
		_nullNode.color = RBNodeColor.Black;

		//construct root node
		_root = _nullNode;
		_root.color = RBNodeColor.Black;

		_isBalanced = true;
		_nodeCount = 0;
	}

	public RBTree(boolean isBalanced) {
		this();
		_isBalanced = isBalanced;
	}

	public boolean contains(T x) {
		return (!find(x).equals(_nullNode));
	}

	@Override
	public boolean search(T data) {
		if(find(data).equals(_nullNode))
			return false;
		else
			return true;
	}

	@Override
	public String preOrder() {
		return preOrder(this._root);
	}

	@Override
	public String postOrder() {
		return postOrder(this._root);
	}

	@Override
	public String inOrder() {
		return inOrder(this._root);
	}

	@Override
	public String levelOrder() {
		return levelOrder(this._root);
	}

	/**
	 * public T insert(T x)
	 *<p>
	 * inserts the x into the binary tree; the tree
	 * must remain valid (keep its invariants).
	 * the return value is defined in the exercise
	 * <p>
	 * precondition:  none
	 * postcondition: contains(x) == true (that is, x is in the tree)
	 *
	 * @param x - key to insert
	 * @return - x
	 */
	public void insert(T x) throws Exception{
		RBNode newNode = new RBNode(x, _nullNode, _nullNode, _nullNode);

		if (_root.equals(_nullNode)) {
			//case root is _nullnode
			_root = newNode;
			_nodeCount++;
		} else {
			//Traveling the tree till find a place to add new key
			RBNode iterator = _root;
			while (true) {
				if (iterator.key.compareTo(x) < 0) {
					if (iterator.right.equals(_nullNode)) {
						iterator.right = newNode;
						newNode.parent = iterator;

						_nodeCount++;
						break;
					} else {
						iterator = iterator.right;
					}
				} else if (iterator.key.compareTo(x) > 0) {
					if (iterator.left.equals(_nullNode)) {
						iterator.left = newNode;
						newNode.parent = iterator;
						_nodeCount++;
						break;
					} else {
						iterator = iterator.left;
					}
				} else {
					throw new Exception("attemping to insert dumpliation value");
				}
			}
		}
		if (_isBalanced) {
			//check for Red/Black Rules, if the tree is RB.
			if (!newNode.equals(_root)) {
				//case 0  - parent is not black then fix up tree
				if (newNode.parent.color != RBNodeColor.Black) {
					insertFixUp(newNode);
				}
			}
			_root.color = RBTree.RBNodeColor.Black;
		}
	}

	/**
	 * public void delete(T x)
	 * <p/>
	 * deletes x from the binary tree, if it is there;
	 * the tree must remain valid (keep its invariants).
	 * the return value is defined in the exercise
	 * <p/>
	 * precondition:  none
	 * postcondition: contains(i) == false (that is, x is not in the tree)
	 *
	 * @param x - key to delete
	 * @return x
	 */
	public void delete(T x) throws Exception{
		RBNode nodeToDelete = find(x);
		if(nodeToDelete.equals(_nullNode))
			throw new Exception("attemping to delete not exists value");

		RBNode node1, node2;

		if (nodeToDelete.left.equals(_nullNode) || nodeToDelete.right.equals(_nullNode)) { // one child or no children
			node1 = nodeToDelete;
		} else {//two children, find the most left node of right child tree
			node1 = findMostLeft(nodeToDelete.right);
//			node1 = successor(nodeToDelete);
		}

		if (!node1.left.equals(_nullNode)) {
			node2 = node1.left;
		} else {
			node2 = node1.right;
		}

		node2.parent = node1.parent;

		if (!node1.parent.equals(_nullNode)) {
			if (node1.equals(node1.parent.left)) {
				node1.parent.left = node2;
			} else {
				node1.parent.right = node2;
			}
		} else {
			_root = node2;
			_root.parent = _nullNode;
		}

		if (!node1.equals(nodeToDelete)) {
			nodeToDelete.key = node1.key;
		}

		if (_isBalanced) {
			if (node1.color == RBNodeColor.Black && !node2.equals(_nullNode)) {
				deleteFixUP(node2);
				_root.color = RBTree.RBNodeColor.Black;
			}
		}
		_nodeCount--;
	}

	/**
	 * justify tree
	 * @param current 
	 */
	private void insertFixUp(RBNode current) {
		while (current.parent.color == RBNodeColor.Red && !current.equals(_root)) {
			if (current.parent.equals(current.parent.parent.left)) { //LLr,LRr,LLb,LRb		
				RBNode uncle = current.parent.parent.right;
				if (uncle.color == RBNodeColor.Red) { // LLr, LRr: cases for right uncle is red
					current.parent.color = RBNodeColor.Black; //father
					uncle.color = RBNodeColor.Black; //uncle
					current.parent.parent.color = RBNodeColor.Red;//grandfather
					current = current.parent.parent;
				} else {//LLb, LRb: case for right uncle is black
					if (current.equals(current.parent.right)) {// LRb
						current = current.parent;
						leftRotate(current);
					}
					//LLb or LRb
					current.parent.color = RBNodeColor.Black;
					current.parent.parent.color = RBNodeColor.Red;
					rightRotate(current.parent.parent);
				}
			} else { //RRr,RLr, RRb, RLb
				RBNode uncle = current.parent.parent.left;
				if (uncle.color == RBNodeColor.Red) {//RRr, RLr
					//case 1 - uncle is red
					current.parent.color = RBNodeColor.Black; //father
					uncle.color = RBNodeColor.Black; //uncle
					current.parent.parent.color = RBNodeColor.Red;//grandfather
					current = current.parent.parent;
				} else {//RRb, RLb case for left uncle is black
					if (current.equals(current.parent.left)) {//RLb
						current = current.parent;
						rightRotate(current);
					}
					//RRb or RLb
					current.parent.color = RBNodeColor.Black;
					current.parent.parent.color = RBNodeColor.Red;
					leftRotate(current.parent.parent);
				}
			}
		}
	}

	private void deleteFixUP(RBNode current) {
		while ((!current.equals(_root)) && current.color == RBNodeColor.Black) {
			if (current.equals(current.parent.left)) {
				RBNode brother = current.parent.right;
				if (brother.color == RBTree.RBNodeColor.Red) {
					//case1 - right brother is red
					brother.color = RBNodeColor.Black;
					current.parent.color = RBNodeColor.Red;
					leftRotate(current.parent);
					brother = current.parent.right;
				}
				if (brother.left.color == RBNodeColor.Black &&
						brother.right.color == RBNodeColor.Black) {
					//case2 - right brother is black and both of his children black
					brother.color = RBNodeColor.Red;
					current = current.parent;
				} else {
					//one child of right brother is red
					if (brother.right.color == RBNodeColor.Black) {
						//case3 - right brother\'s right child is black
						brother.left.color = RBNodeColor.Black;
						rightRotate(brother);
						brother = current.parent.right;
					}
					//case4 - right brother\'s left child is black
					brother.color = current.parent.color;
					current.parent.color = RBNodeColor.Black;
					brother.right.color = RBNodeColor.Black;
					leftRotate(current.parent);
					current = _root;
				}
			} else {
				//symetric cases as above
				RBNode brother = current.parent.left;
				if (brother.color == RBTree.RBNodeColor.Red) {//Rr0
					//case1 - left brother is red
					brother.color = RBNodeColor.Black;
					current.parent.color = RBNodeColor.Red;
					rightRotate(current.parent);
					brother = current.parent.left;
				}
				if (brother.left.color == RBNodeColor.Black &&
						brother.right.color == RBNodeColor.Black) {//Rb0
					//case2 - left brother is black and both of his children black
					brother.color = RBNodeColor.Red;
					current = current.parent;
				} else {
					//one child of left brother is red
					if (brother.left.color == RBNodeColor.Black) {//rb(i)
						//case3 - left brother\'s left child is black
						brother.right.color = RBNodeColor.Black;
						leftRotate(brother);
						brother = current.parent.left;
					}
					//case4 - left brother\'s right child is black
					brother.color = current.parent.color;
					current.parent.color = RBNodeColor.Black;
					brother.left.color = RBNodeColor.Black;
					rightRotate(current.parent);
					current = _root;
				}
			}
		}
		current.color = RBNodeColor.Black; //make sure curren is black

	}

	/**
	 * The method makes newLeft to be the left child of current
	 *
	 * @param current - node to set the left child
	 * @param newLeft - a new node to be the left child of current
	 */
	private void setLeftNode(RBNode current, RBNode newLeft) {
		current.left = newLeft;
		newLeft.parent = current;
	}

	/**
	 * The method makes newRight to be the right child of current
	 *
	 * @param current  - node to set the right child
	 * @param newRight - a new node to be the right child of current
	 */
	private void setRightNode(RBNode current, RBNode newRight) {
		current.right = newRight;
		newRight.parent = current;
	}

	/**
	 * The method replace oldNode with newNode
	 *
	 * @param oldNode - node to be replaced.
	 * @param newNode - new node to replace.
	 */
	private void transplant(RBNode oldNode, RBNode newNode) {
		if (!oldNode.parent.equals(_nullNode)) {
			if (oldNode.equals(oldNode.parent.left)) {
				setLeftNode(oldNode.parent, newNode);
			} else {
				setRightNode(oldNode.parent, newNode);
			}
		} else {
			_root = newNode;
			_root.parent = _nullNode;
		}

	}

	/**
	 * The method makes left rotate a round the give node.
	 *
	 * @param current - the node to rotate around.
	 */
	private void leftRotate(RBNode current) {
		RBNode tmp = current.right;
		transplant(current, tmp);
		setRightNode(current, tmp.left);
		setLeftNode(tmp, current);
	}

	/**
	 * The method makes right rotate a round the give node.
	 *
	 * @param current -  the node to rotate around.
	 */
	private void rightRotate(RBNode current) {
		RBNode tmp = current.left;
		transplant(current, tmp);
		setLeftNode(current, tmp.right);
		setRightNode(tmp, current);

	}

	/**
	 * The method returns the node to the successor of the given node
	 *
	 * @param x - node
	 * @return the node holding the key next to x.key
	 */
	private RBNode successor(RBNode x) {
		RBNode baseNode = x;

		if (!baseNode.equals(_nullNode)) {
			if (!baseNode.right.equals(_nullNode)) {
				//return the minimum of right child
				return findMostLeft(baseNode.right);
			} else {
				RBNode baseParentNode = baseNode.parent;
				while (!baseParentNode.equals(_nullNode) && baseNode.equals(baseParentNode.right)) {
					if (!baseParentNode.equals(_root)) {
						baseNode = baseParentNode;
						baseParentNode = baseNode.parent;
					} else {
						return _nullNode;
					}

				}
				return baseParentNode;
			}
		} else {
			return _nullNode;
		}
	}

	public boolean empty() {
		//if root is _nullnode
		return (_root.equals(_nullNode));
	}

	/**
	 * @param x - key to find
	 * @return pointer to the nodeResult else _nullnode
	 */
	private RBNode find(T x) {
		RBNode iterator = _root;
		//Start travelling the tree from the root.
		while (true) {
			if (!(iterator.equals(_nullNode))) {
				if (iterator.key.compareTo(x) == 0) {
					return iterator;
				} else if (iterator.key.compareTo(x) < 0) {
					iterator = iterator.right;
				} else {
					iterator = iterator.left;
				}
			} else {
				return _nullNode;
			}
		}
	}

	/**
	 * The method returns the most left node of a given node.
	 *
	 * @param current - node to start from
	 * @return Most left node
	 */
	private RBNode findMostLeft(RBNode current) {
		RBNode iterator = current;
		while (!iterator.left.equals(_nullNode)) {
			iterator = iterator.left;
		}
		return iterator;
	}

	/**
	 * The method returns the most right node of a given node.
	 *
	 * @param current - node to start from
	 * @return Most Right node
	 */
	private RBNode findMostRight(RBNode current) {
		RBNode iterator = current;
		while (!iterator.right.equals(_nullNode)) {
			iterator = iterator.right;
		}
		return iterator;
	}

	/**
	 * public T min()
	 * <p/>
	 * Returns the smallest key in the tree. If the tree
	 * is empty, returns -1;
	 * <p/>
	 * precondition: none
	 * postcondition: none
	 *
	 * @return - Minimum key of the tree
	 */
	public T min() {
		if (!_root.equals(_nullNode)) {
			return findMostLeft(_root).key;
		} else {
			return null;
		}

	}

	/**
	 * public T max()
	 * <p/>
	 * Returns the largest key in the tree. If the tree
	 * is empty, returns -1;
	 * <p/>
	 * precondition: none
	 * postcondition: none
	 *
	 * @return - Maximum key of the tree
	 */
	public T max() {
		if (!_root.equals(_nullNode)) {
			return findMostRight(_root).key;
		} else {
			return null;
		}
	}

	/**
	 * public int getHeight()
	 * <p/>
	 * Returns the height of the tree.
	 * <p/>
	 * precondition: none
	 * postcondition:
	 */
	public int getHeight() {
		return treeHeight(_root, 0);

	}

	/**
	 * The method returns the height of the tree from a given node.
	 *
	 * @param current - node to start from
	 * @return height of the tree
	 */
	private int treeHeight(RBNode current, int i) {
		int iLeft;
		int iRight;

		if (!(current.left.equals(_nullNode))) {
			iLeft = treeHeight(current.left, i + 1);
		} else {
			iLeft = i + 1;
		}
		if (!(current.right.equals(_nullNode))) {
			iRight = treeHeight(current.right, i + 1);
		} else {
			iRight = i + 1;
		}

		if (iLeft > iRight) {
			i = iLeft;
		} else {
			i = iRight;
		}
		return i;
	}

	/**
	 * public T getAverageDepth()
	 * <p/>
	 * Returns the average depth of a node in the tree.
	 * <p/>
	 * precondition: none
	 * postcondition:
	 */
	public double getAverageDepth() {
		return sumHeight(_root, 0) / (numOfNulls(_root, 0) + _nodeCount);

	}

	/**
	 * The method returns the sum height of a sub tree from a given node.
	 *
	 * @param current - node to start from
	 * @return sum of nodes heights.
	 */

	private int sumHeight(RBNode current, int i) {
		int sumLeft;
		int sumRight;

		if (current.equals(_nullNode)) {
			return i;
		}
		sumLeft = sumHeight(current.left, i + 1);
		sumRight = sumHeight(current.right, i + 1);

		i = i + sumLeft + sumRight;


		return i;
	}


	/**
	 * The method returns the number of null nodes in a tree.
	 *
	 * @param current - node to start from
	 * @return number of null nodes in a tree
	 */

	private double numOfNulls(RBNode current, double count) {
		if (current.equals(_nullNode)) {

			return count + 1;
		}

		count = numOfNulls(current.left, count) + numOfNulls(current.right, count);
		return count;
	}


	/**
	 * public int size()
	 * <p/>
	 * Returns the number of nodes in the tree.
	 * <p/>
	 * <p/>
	 * precondition: none
	 * postcondition:
	 *
	 * @return how many nodes in the tree,
	 */
	public int getSize() {
		return _nodeCount;
	}

	/**
	 * visit binary tree pre-order
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	private String preOrder(RBNode root){
		if(root.equals(_nullNode)) return null;
		String result = "";
		result += visit(root);
		if(!root.left.equals(_nullNode)) 
			result += preOrder(root.left);
		if(!root.right.equals(_nullNode))
			result += preOrder(root.right);
		return result;
	}

	/**
	 * visit binary tree post-order
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	private String postOrder(RBNode root){
		if(root.equals(_nullNode))return null;
		String result = "";
		if(!root.left.equals(_nullNode)) 
			result += postOrder(root.left);
		if(!root.right.equals(_nullNode))
			result += postOrder(root.right);
		result += visit(root);
		return result;
	}

	/**
	 * visit binary tree in-order
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	private String inOrder(RBNode root){
		if(root.equals(_nullNode))return null;
		String result = "";
		if(!root.left.equals(_nullNode)) 
			result += inOrder(root.left);
		result += visit(root);
		if(!root.right.equals(_nullNode))
			result += inOrder(root.right);
		return result;
	}

	/**
	 * visit binary tree per level
	 * @param root binary tree root node
	 * @return String nodes' value list
	 */
	private String levelOrder(RBNode root){
		if(root.equals(_nullNode)) return null;
		String result = "";
		Queue<RBNode> queue = new LinkedList<>();
		RBNode node = root;
		queue.add(node);
		while(!queue.isEmpty()){
			node = queue.remove();
			result += visit(node);
			if(!node.left.equals(_nullNode))
				queue.add(node.left);
			if(!node.right.equals(_nullNode))
				queue.add(node.right);
		}
		return result;
	}

	/**
	 * visit one RBTreeNode and print its element value
	 * @param node tree node to be visit
	 */
	private String visit(RBNode node){
		return "("+node.key.toString()+","+node.color+") ";
	}	

	public static void main(String[] args){
		RBTree<Integer> t = new RBTree<>();
		try {
			t.insert (new Integer(8));
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

			t.insert (new Integer(13));
			t.insert (new Integer(14));
			t.insert (new Integer(15));
			t.insert (new Integer(16));
			t.insert (new Integer(17));
			t.insert (new Integer(18));
			/*
			 */

			System.out.println ("prefix Traversal:");
			System.out.println(t.preOrder());

			t.delete(new Integer(17));
			System.out.println ("prefix Traversal after remove 11:");
			System.out.println(t.preOrder());
			t.delete(new Integer(12));
			System.out.println ("prefix Traversal after remove 12:");
			System.out.println(t.preOrder());
			t.delete(new Integer(8));
			System.out.println ("prefix Traversal after remove 8:");
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