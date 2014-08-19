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
			//�����j = -1�����ߵ�ǰ�ַ�ƥ��ɹ�����S[i] == P[j]��������i++��j++      
			if (j == -1 || s.charAt(i) == p.charAt(j))  
			{  
				i++;  
				j++;  
			}  
			else  
			{  
				//�����j != -1���ҵ�ǰ�ַ�ƥ��ʧ�ܣ���S[i] != P[j]�������� i ���䣬j = next[j]      
				//next[j]��Ϊj����Ӧ��nextֵ        
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
	        //p[k]��ʾǰ׺��p[j]��ʾ��׺    
	        if (k == -1 || p.charAt(j) == p.charAt(k))  
	        {  
	            ++j;  
	            ++k;  
	            //��֮ǰnext�����󷨣��Ķ�������4��  
	            if (p.charAt(j) != p.charAt(k))  
	                next[j] = k;   //֮ǰֻ����һ��  
	            else  
	                //��Ϊ���ܳ���p[j] = p[ next[j ]]�����Ե�����ʱ��Ҫ�����ݹ飬k = next[k] = next[next[k]]  
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
