package com.algorithm.zxie.kmp;

public class KMPSearch {

	
	public static int kmpSearch(String s, String p){
		
		int[] next = next(p);
		int i = 0;  
		int j = 0; 
		int sLen = s.length();  
		int pLen = p.length();  
		while (i < sLen && j < pLen)  
		{  
			System.out.println(i);
			//①如果j = -1，或者当前字符匹配成功（即S[i] == P[j]），都令i++，j++      
			if (j == -1 || s.charAt(i) == p.charAt(j))  
			{  
				i++;  
				j++;  
			}  
			else  
			{  
				//②如果j != -1，且当前字符匹配失败（即S[i] != P[j]），则令 i 不变，j = next[j]      
				//next[j]即为j所对应的next值        
				j = next[j];  
			}  
		}  
		if (j == pLen)  
			return i - j;  
		else  
			return -1;
	}
	
	private static int[] next(String p){
		int len = p.length();
		int[] next = new int[len];
		next[0] = -1;
		int k = -1;  
	    int j = 0;  
	    while (j < len - 1)  
	    {  
	        //p[k]表示前缀，p[j]表示后缀    
	        if (k == -1 || p.charAt(j) == p.charAt(k))  
	        {  
	            ++j;  
	            ++k;  
	            //较之前next数组求法，改动在下面4行  
	            if (p.charAt(j) != p.charAt(k))  
	                next[j] = k;   //之前只有这一行  
	            else  
	                //因为不能出现p[j] = p[ next[j ]]，所以当出现时需要继续递归，k = next[k] = next[next[k]]  
	                next[j] = next[k];  
	        }  
	        else  
	        {  
	            k = next[k];  
	        }  
	    }  
		
		return next;
	}

	public static void main(String[] args) {
		System.out.println(KMPSearch.kmpSearch("BBC ABCDAB ABCDABCDABDE", "ABCDABD"));
	}

}
