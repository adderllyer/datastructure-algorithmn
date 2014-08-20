package com.algorithm.zxie.tree.heap;

/**
 * The BinaryHeap is an -generic- implementation of the PriorityQueue interface.  
 * This is a binary min-heap implementation of the priority queue ADT.
 */
import java.util.Arrays;

public class BinaryHeap<T extends Comparable<T>> implements PriorityQueue<T> {
	private static final int DEFAULT_CAPACITY = 10;
	protected T[] array;
	protected int size;

	/**
	 * Constructs a new BinaryHeap.
	 */
	@SuppressWarnings("unchecked")
	public BinaryHeap () {
		// Java doesn't allow construction of arrays of placeholder data types 
		array = (T[])new Comparable[DEFAULT_CAPACITY];  
		size = 0;
	}

	@SuppressWarnings("unchecked")
	public BinaryHeap (T[] heap){
		T[] tmp = (T[])new Comparable[heap.length+1];
		for(int i=0; i<heap.length; i++)
			tmp[i+1] = heap[i];
		array = tmp;
		buildHeap(array);
		size = heap.length;
	}

	/**
	 * build up a heap from array heap
	 * @param heap a not null array for heap building
	 */
	protected void buildHeap(T[] heap) {
		if(heap == null || heap.length == 0 || heap.length <= 1)
			return;
		int length = heap.length/2;
		int minChild = 1;
		for(int i=1; i<=length; i++){
			if(2*i+1<=heap.length-1)
				minChild = (heap[2*i].compareTo(heap[2*i+1]) >= 0)?2*i+1:2*i;
			else
				minChild = 2*i;
			if(heap[i].compareTo(heap[minChild])>0)
				swap(i, minChild);
		}
	}

	/**
	 * Adds a value to the min-heap.
	 * <i>add the value to the end of array
	 * <i>justify the heap
	 */
	public void add(T value) {
		// grow array if needed
		if (size >= array.length - 1) {
			array = this.resize();
		}        

		// place element into heap at bottom
		size++;
		int index = size;
		array[index] = value;

		bubbleUp();
	}


	/**
	 * Returns true if the heap has no elements; false otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}


	/**
	 * Returns (but does not remove) the minimum element in the heap.
	 */
	public T peek() {
		if (this.isEmpty()) {
			throw new IllegalStateException();
		}

		return array[1];
	}


	/**
	 * Removes and returns the minimum element in the heap.
	 */
	public T remove() {
		// what do want return?
		T result = peek();

		// get rid of the last leaf/decrement
		array[1] = array[size];
		array[size] = null;
		size--;

		bubbleDown();

		return result;
	}


	/**
	 * Returns a String representation of BinaryHeap with values stored with 
	 * heap structure and order properties.
	 */
	public String toString() {
		return Arrays.toString(array);
	}


	/**
	 * Performs the "bubble down" operation to place the element that is at the 
	 * root of the heap in its correct place so that the heap maintains the 
	 * min-heap order property.
	 */
	protected void bubbleDown() {
		int index = 1;

		while(hasLeftChild(index)){
			int minIndex = leftIndex(index);

			//get the min child index
			if(hasRightChild(index) && array[rightIndex(index)].compareTo(array[leftIndex(index)]) < 0)
				minIndex = rightIndex(index);

			// if current node value great to one of its child node, swap value
			if(array[index].compareTo(array[minIndex]) > 0)
				swap(index, minIndex);
			else
				break;

			index = minIndex;
		}
	}


	/**
	 * Performs the "bubble up" operation to place a newly inserted element 
	 * (i.e. the element that is at the size index) in its correct place so 
	 * that the heap maintains the min-heap order property.
	 */
	protected void bubbleUp() {
		int index = this.size;
		while(hasParent(index) && parent(index).compareTo(array[index]) > 0){
			swap(index,parentIndex(index));
			index = parentIndex(index);
		}    
	}


	/**
	 * check if node has parent node
	 * 
	 * @param i node index
	 * @return true if has parent node, else return false
	 */
	protected boolean hasParent(int i) {
		return i > 1;
	}


	/**
	 * get the left child index
	 * 
	 * @param i node index
	 * @return left child index
	 */
	protected int leftIndex(int i) {
		return i * 2;
	}


	/**
	 * get the right child index
	 * 
	 * @param i node index
	 * @return right child index
	 */
	protected int rightIndex(int i) {
		return i * 2 + 1;
	}


	/**
	 * check if node i has a left child
	 * 
	 * @param i node index
	 * @return true if has left child, else return false
	 */
	protected boolean hasLeftChild(int i) {
		return leftIndex(i) <= size;
	}


	/**
	 * check if node i has a right child
	 * 
	 * @param i
	 * @return true if has right child, else return false
	 */
	protected boolean hasRightChild(int i) {
		return rightIndex(i) <= size;
	}


	/**
	 * get node i's parent value
	 * 
	 * @param i node index
	 * @return parent node value
	 */
	protected T parent(int i) {
		return array[parentIndex(i)];
	}


	/**
	 * get parent index
	 * @param i node index
	 * @return parent index
	 */
	protected int parentIndex(int i) {
		return i / 2;
	}


	/**
	 * justify the size of array, extend the size to size*2
	 * 
	 * @return the new array with all copy datas
	 */
	protected T[] resize() {
		return Arrays.copyOf(array, array.length * 2);
	}


	/**
	 * exchange the two elements' value
	 * 
	 * @param index1 element index
	 * @param index2 element index
	 */
	protected void swap(int index1, int index2) {
		T tmp = array[index1];
		array[index1] = array[index2];
		array[index2] = tmp;        
	}
}