package com.algorithm.zxie.sort;

public class ExchangeSort {

	public static void main(String[] args) {
		int[] a = {3,1,2,4,7,8,5,6,9};
		System.out.println(ExchangeSort.run(a));
		int[] b = {3,1,2};
		System.out.println(ExchangeSort.run(b));
		int[] c = {1,3,2};
		System.out.println(ExchangeSort.run(c));
	}
	
	public static int run(int[] a){
		return run(a,  0, a.length-1);
	}

	public static int run(int [] a, int start, int end){
		if(end-start>=2){
			int max_idx=end;
			int min_idx=start;
			int max = a[max_idx];
			int min = a[min_idx];
			// round 1: find the min value
			for(int i=start; i<=end; i++){
				if(a[i]<min){
					min = a[i];
					min_idx = i;
				}
			}
			int min_num=exchange_min(a,start,min_idx);
			
			// round 2: find the max value
			for(int i=start; i<=end; i++){
				if(a[i]>max){
					max = a[i];
					max_idx = i;
				}
			}
			int max_num= exchange_max(a,end,max_idx);
			return min_num+max_num+run(a,start+1,end-1);
		}
		else if(end -start == 1){
			if(a[start]<a[end])
				return 0;
			else if(a[start]>a[end]){
				return exchange_min(a,start,end);
			}
		}
		return 0;
	}

	public static int exchange_min(int[] a, int idx_1, int idx_2){
		if (a[idx_1]>a[idx_2]){
			int tem = a[idx_1];
			a[idx_1] = a[idx_2];
			a[idx_2]= tem;
			return 1;
		}
		else return 0;
	}
	
	public static int exchange_max(int[] a, int idx_1, int idx_2){
		if (a[idx_1]<a[idx_2]){
			int tem = a[idx_1];
			a[idx_1] = a[idx_2];
			a[idx_2]= tem;
			return 1;
		}
		else return 0;
	}

}
