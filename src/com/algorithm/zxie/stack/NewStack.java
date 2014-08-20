package com.algorithm.zxie.stack;

/**
 * A new Stack which can push, pop and get the max value with O(1);<p>
 * 
 * use two arrays:
 * <li>1. stack array use for store stack elements
 * <li>2. maxIds array use for store index value of previous max element value, that's to say,<p> 
 *        if current element great than the max element, store the previous max element index in maxIds array,
 *        else store -1
 * 
 * @author zxie
 *
 */

public class NewStack {

	public final static int MAX=100;
	
	public int[] stack = new int[MAX];
	public int[] maxIdxs= new int[MAX];
	
	public int maxIdx = -1;
	public int stackTop = -1;
	
	public int size(){
		return stackTop+1;
	}
	
	public void push(int val) throws Exception{		
		++stackTop;
		if(size()>MAX) 
			throw new Exception("stack overflow.");
		else{
			stack[stackTop] = val;
			if(max() < val){
				maxIdxs[stackTop] = maxIdx;
				maxIdx = stackTop;
			}
			else
				maxIdxs[stackTop] = -1;
		}
	}
	
	public int pop() throws Exception{
		int res;
		if(size() == 0)
			throw new Exception("empty stack.");
		else{
			res = stack[stackTop];
			if(stackTop == maxIdx)
				maxIdx = maxIdxs[stackTop];
			stackTop--;
			return res;
		}
	}
	
	public int max() {
		if(maxIdx >= 0)
			return stack[maxIdx];
		else
			return Integer.MIN_VALUE;
	}
	
	public static void main(String[] args) throws Exception{
		NewStack ns = new NewStack();
		int[] init = {1,3,2,8,5,7,6};
		for(int val:init){
			ns.push(val);
		}
		System.out.println(ns.maxIdx);
		for(int idx:ns.maxIdxs)
			System.out.print(idx+",");
		System.out.println();
		ns.pop();
		System.out.println(ns.max());
		ns.push(10);
		ns.push(4);
		ns.push(9);
		System.out.println(ns.max());
		for(int idx:ns.maxIdxs)
			System.out.print(idx+",");
		System.out.println();
		ns.pop();
		ns.pop();
		ns.pop();
		ns.pop();
		for(int idx:ns.maxIdxs)
			System.out.print(idx+",");
		System.out.println();
		System.out.println(ns.max());
	}
	
}
