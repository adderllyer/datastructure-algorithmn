package com.algorithm.zxie.swap;

public class Swap {

	public static void main(String[] args) {
		Swap sp = new Swap();
		int a = 1;
		int b = 2;
		sp.swap(a, b);
		System.out.println("a="+a+",b="+b);
		
		Integer o1 = 1;
		Integer o2 = 2;
		sp.swap(o1, o2);
		System.out.println("o1="+o1+",o2="+o2);
	}
	
	//incorrect swap method
	public void swap(int a, int b){
		int tem = a;
		a = b;
		b = tem;
	}
	
	//incorrect swap method
	public void swap(Integer o1, Integer o2){
		int tem = o1;//ad1
		o1 = o2;//ad2
		o2 = tem;//ad1
	}
	
	//correct swap method
	public void swap(Object[] a, int i, int j){
		Object tem = a[i];
		a[i] = a[j];
		a[j] = tem;
	}

}
