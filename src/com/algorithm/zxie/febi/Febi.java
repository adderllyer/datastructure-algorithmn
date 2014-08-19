package com.algorithm.zxie.febi;

public class Febi {
	
	//recursion
	public static long calcFebi(int n){
		if(n <= 1) return n;
		return calcFebi(n-1)+calcFebi(n-2);
	}
	
	//dynamic programming
	public static long calcFebiDP(int n){
		if(n <= 1) return n;
		long[] tem = new long[2];
		tem[0] = 0;
		tem[1] = 1;
		long tmp = 0;
		for(int i=2; i<=n; i++){
			tmp = tem[0] + tem[1];
			tem[0] = tem[1];
			tem[1] = tmp;
		}
		return tem[1];
	}

	public static void main(String[] args) {
		int n = 100;
		long t1 = System.currentTimeMillis();
		long res0 = Febi.calcFebiDP(n);
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
		t1 = System.currentTimeMillis();
		long res1 = Febi.calcFebi(n);
		t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
		System.out.println("Re:"+res0+"="+"DP:"+res1);
	}

}
