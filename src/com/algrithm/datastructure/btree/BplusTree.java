package com.algrithm.datastructure.btree;

import java.util.Random;


/**
 * B+树的定义：
 *  1.任意非叶子结点最多有M个子节点；且M>2；
 *  2.除根结点以外的非叶子结点至少有 M/2个子节点；
 *  3.根结点至少有2个子节点；
 *  4.除根节点外每个结点存放至少M/2和至多M个关键字；（至少2个关键字）
 *  5.非叶子结点的子树指针与关键字个数相同；
 *  6.所有结点的关键字：K[1], K[2], …, K[M]；且K[i] < K[i+1]；
 *  7.非叶子结点的子树指针P[i]，指向关键字值属于[K[i], K[i+1])的子树；
 *  8.所有叶子结点位于同一层；
 *  5.为所有叶子结点增加一个链指针；
 *  6.所有关键字都在叶子结点出现；
 * @author zxie
 *
 */
public class BplusTree<K extends Comparable<K>, V extends Object> implements Tree<K,V> {
	
	/**
	 * B+ tree root
	 */
	protected Node<K, V> root;
	
	/** rank, max number of entries */
	protected int order;
	
	/** leaf nodes list head */
	protected Node<K, V> head;
	
	public Node<K,V> getHead() {
		return head;
	}

	public void setHead(Node<K,V> head) {
		this.head = head;
	}

	public Node<K,V> getRoot() {
		return root;
	}

	public void setRoot(Node<K,V> root) {
		this.root = root;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public V get(K key) {
		return root.get(key);
	}

	@Override
	public void remove(K key) {
		root.remove(key, this);
	}

	@Override
	public void insertOrUpdate(K key, V obj) {
		root.insertOrUpdate(key, obj, this);
	}
	
	public BplusTree(int order){
		if (order < 3) {
			System.out.print("order must be greater than 2");
			System.exit(0);
		}
		this.order = order;
		root = new Node<K,V>(true, true);
		head = root;
	}
	
	//testing
	public static void main(String[] args) {
		BplusTree<Integer, Integer> tree = new BplusTree<>(6);
		Random random = new Random();
		long current = System.currentTimeMillis();
		for (int j = 0; j < 10000; j++) {
			for (int i = 0; i < 100; i++) {
				int randomNumber = random.nextInt(1000);
				tree.insertOrUpdate(randomNumber, randomNumber);
			}

			for (int i = 0; i < 100; i++) {
				int randomNumber = random.nextInt(1000);
				tree.remove(randomNumber);
			}
		}

		long duration = System.currentTimeMillis() - current;
		System.out.println("time elpsed for duration: " + duration);
		int search = 80;
		System.out.print(tree.get(search));
	}

}
