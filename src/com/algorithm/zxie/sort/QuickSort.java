package com.algorithm.zxie.sort;

public class QuickSort {

	public static void sort(int[] arrs, int l, int r){
		if(arrs == null || arrs.length == 0 || arrs.length == 1) return;
		if(l >= r) return;
		int pivotpos = partition(arrs, l, r);
		sort(arrs, l, pivotpos - 1);
		sort(arrs, pivotpos + 1, r);
	}

	private static int partition(int[] arrs, int l, int r){
		int pivot = arrs[l];
		while(l < r){
			while(l < r && arrs[r] >= pivot) r--;
			if(l < r)
				arrs[l++] = arrs[r];
			while(l < r && arrs[l] <= pivot) l++;
			if(l < r)
				arrs[r--] = arrs[l];
		}

		arrs[l] = pivot;
		return l;
	}

	public static void main(String[] args) {

		int[] arrs = {9,1,3,5,2,8,7,4};
		QuickSort.sort(arrs, 0, arrs.length-1);
		for(int a : arrs)
			System.out.println(a);
	}

}
