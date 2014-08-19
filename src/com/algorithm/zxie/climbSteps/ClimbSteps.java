package com.algorithm.zxie.climbSteps;

/**
 * there are n steps, you can step 1 to m steps one time, please count out the number of 
 * methods to reach the step top
 * 
 * @author zxie
 *
 */
public class ClimbSteps {

	public long jumpingWithRecursive(int steps){
		if (steps <= 0)
			return 0l;

		if(steps == 1 || steps == 2)
			return (long)steps;
		else
			return jumpingWithRecursive(steps-2)+jumpingWithRecursive(steps-1);
	}

	
	public long jumpingWithDynamicProgramming(int steps){
		long first = 1;
		long second = 2;
		long third = 0;
		if(steps <= 2)
			return steps;
		for(int i=3; i<=steps; i++){
			third = first + second;
			first = second;
			second = third;
		}
		return third;
	}
	
	public long jumpingWithDynamicProgramming(int n, int m){
		long[] f = new long[n+1];
		f[1] = 1;
		f[2] = 2;
		for(int i=3; i<=m; i++){
			for(int j=1; j<i; j++)
				f[i] += f[j];
		}
		
		for(int k=m+1; k<=n; k++){
			for(int h=1; h<=m; h++)
				f[k] += f[k-h];
		}
		return f[n];
	}
	
	public static void main(String[] args){
		ClimbSteps cs = new ClimbSteps();
		long result = cs.jumpingWithDynamicProgramming(10, 2);
		System.out.println(result);
		System.out.println(cs.jumpingWithDynamicProgramming(10));
	}

}
