package com.algorithm.zxie.queue;

import com.algorithm.zxie.stack.NewStack;

public class NewQueue {

	public NewStack stackIn;
	public NewStack stackOut;

	public NewQueue(){
		stackIn  = new NewStack();
		stackOut = new NewStack();
	}

	public void enqueue(int val) throws Exception{
		if(stackIn.size() >= NewStack.MAX)
			throw new Exception("Stack overflow.");
		stackIn.push(val);
	}

	public int dequeue() throws Exception{
		if(stackOut.size() == 0 && stackIn.size() == 0)
			throw new Exception("Stack empty");
		else if(stackOut.size() == 0 && stackIn.size() != 0){
			while(stackIn.size() != 0){
				stackOut.push(stackIn.pop());
			}
		}
		return stackOut.pop();
	}

	public int max() {
		return stackOut.max() > stackIn.max()? stackOut.max() : stackIn.max();
	}

	public int size(){
		return stackOut.size() + stackIn.size();
	}

	public static void main(String[] args) throws Exception {
		int[] init = {1,3,2,8,5,7,6};

		NewQueue nq = new NewQueue();
		for(int val:init){
			nq.enqueue(val);
		}
		System.out.println(nq.max());
		nq.enqueue(10);
		nq.enqueue(9);
		nq.enqueue(4);
		System.out.println(nq.max());
		System.out.println("***********"+nq.size()+"***********");
		int size = nq.size();
		for(int i=0; i<size-2; i++)
			System.out.println(i+":"+nq.dequeue());
		System.out.println(nq.max());
		nq.dequeue();
		System.out.println(nq.max());
	}

}
