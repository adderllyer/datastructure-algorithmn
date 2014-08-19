package com.algorithm.zxie.binomial;

public class Binomial {
	
	//Recursion
	public static long calcBinomial(int n, int k){
		if(k == 0 || n == k) return 1;
		return calcBinomial(n-1,k-1) + calcBinomial(n-1, k);
	}

	//space in time
	public static long calcBinomialDP(int n, int k){
		long[][] table =  new long[n+1][k+1];
		int max = 0;
		for(int i = 0; i <= n; i++){
			max = (i>k)?k:i;
			for(int j = 0; j <= max; j++){
				if(j == 0 || i == j)table[i][j] = 1;
				else
					table[i][j] = table[i-1][j-1] + table[i-1][j];
			}
		}
		return table[n][k];
	}

	public static void main(String[] args) {
		System.out.println(Binomial.calcBinomialDP(100, 50));
		System.out.println(Binomial.calcBinomial(100, 50));
	}

}
