package com.algorithm.zxie.sort;

public class InsertSort {
	
	public static int[] sort(int[] arrs) throws Exception{
		if(arrs == null || arrs.length == 0)
			throw new Exception("null array!");
		if(arrs.length == 1)
			return arrs;
		for(int i=1; i<arrs.length; i++){
			int j = i-1;
			int k = i;
			while(j >= 0 && arrs[k] < arrs[j]){
				int tmp = arrs[j];
				arrs[j] = arrs[k];
				arrs[k] = tmp;
				k = j;
				j--;
			}
		}
		return arrs;
	}

	public static void main(String[] args) throws Exception {
		int[] arrs = {9,1,3,5,2,8,7,4};
		arrs =  InsertSort.sort(arrs);
		for(int a : arrs)
			System.out.println(a);
	}

}
