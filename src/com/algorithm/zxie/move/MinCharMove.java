package com.algorithm.zxie.move;

public class MinCharMove {

	public int test(String a, String b){
		int num = 0;
		num = maxsubStringF(a.toLowerCase(), b.toLowerCase());
		return a.length()-num;
	}

	/**
	 * 1. all characters are different
	 * 2. include same characters
	 */
	private int maxsubString(String a, String b){
		int len = a.length();
		if(a.equals(b)) return len;
		int oldIdx = len;
		int newIdx = 0;
		int sum = 0;
		for(int i = len-1; i>=0; i--){
			char c = b.charAt(i);
			System.out.println(c);
			newIdx = a.indexOf(c);
			System.out.println(newIdx);
			if(newIdx == 0){
				return ++sum;
			}
			else if(newIdx > oldIdx)
				return sum;
			else{
				sum++;
				oldIdx = newIdx;
			}
		}
		return sum;
	}

	private int maxsubStringF(String a, String b){
		int len = a.length()-1;
		if(a.equals(b)) return len;
		int oldIdx = len;
		int sum = 0;
		for(int i = len; i>=0; i--){
			char c = b.charAt(i);
			System.out.println(c);
			for(int j=oldIdx; j>=0; j--){
				if(a.charAt(j)==c){
					if(j==0)
						return ++sum;
					else{
						oldIdx = j;
						sum++;
						break;
					}
				}
				else{
					if(j==0)
						return sum;
				}
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// a= "abcdef", b="defabc"
		String a = "abbcdca";
		String b = "abcdabc";
		MinCharMove mcm = new MinCharMove();
		System.out.println("move char number is: "+mcm.test(a, b));

	}

}
