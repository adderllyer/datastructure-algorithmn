package com.algorithm.zxie.quicksort;

public class QuickSort {
	
	long[] array;
	int lo, hi;
	
	public QuickSort(long[] array, int lo, int hi){
		this.array = array;
		this.lo = lo;
		this.hi = hi;
	}
	
	public void sort(long[] array, int lo, int hi){
		int pivot;
		if(lo < hi){
			pivot = partition(array, lo, hi);
			sort(array, lo, pivot-1);
			sort(array, pivot+1, hi);
		}
	}
	
	private int partition(long[] array, int lo, int hi){
		Long x = array[lo];
		while(lo<hi){
			while(lo<hi && array[hi]>=x)
				hi--;
			if(lo<hi)
				array[lo++] = array[hi];
			while(lo<hi && array[lo]<=x)
				lo++;
			if(lo<hi)
				array[hi--] = array[lo];
		}
		array[lo] = x;
		return lo;
	}
	
}
