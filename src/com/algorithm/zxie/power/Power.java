package com.algorithm.zxie.power;

public class Power {
	
	//binary method
	public static long power(int x, int n){
		long y = 1l;
		while(true){
			int t = n % 2;
			n = (int) Math.floor(n/2);
			
			if(t == 1)
				y = y * x;
			
			if(n == 0)
				break;
			x = x * x;
		}
		return y;
	}

	public static void main(String[] args) {
		System.out.println(Power.power(2, 9));
	}

}
