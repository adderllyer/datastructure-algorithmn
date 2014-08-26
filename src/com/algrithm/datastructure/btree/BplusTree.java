package com.algrithm.datastructure.btree;

import java.util.Random;


/**
 * B+���Ķ��壺
 *  1.�����Ҷ�ӽ�������M���ӽڵ㣻��M>2��
 *  2.�����������ķ�Ҷ�ӽ�������� M/2���ӽڵ㣻
 *  3.�����������2���ӽڵ㣻
 *  4.�����ڵ���ÿ�����������M/2������M���ؼ��֣�������2���ؼ��֣�
 *  5.��Ҷ�ӽ�������ָ����ؼ��ָ�����ͬ��
 *  6.���н��Ĺؼ��֣�K[1], K[2], ��, K[M]����K[i] < K[i+1]��
 *  7.��Ҷ�ӽ�������ָ��P[i]��ָ��ؼ���ֵ����[K[i], K[i+1])��������
 *  8.����Ҷ�ӽ��λ��ͬһ�㣻
 *  5.Ϊ����Ҷ�ӽ������һ����ָ�룻
 *  6.���йؼ��ֶ���Ҷ�ӽ����֣�
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
