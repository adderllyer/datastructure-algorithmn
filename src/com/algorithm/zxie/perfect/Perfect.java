package com.algorithm.zxie.perfect;

import java.util.Arrays;

public class Perfect {

	public static int perfect(String s) {
		int result = 0;
		int[] cn = new int[26];
		String ls = s.toLowerCase();
		for(int i=0; i<ls.length(); i++){
			cn[ls.charAt(i)-97]++;
		}
			
		Arrays.sort(cn);
		for(int i = cn.length-1; i>=0; i--){
			if(cn[i]==0) break;
			result += cn[i]*(i+1);
		}
			

		return result;
    }

    
    //start ��ʾ���Զ��ľ���ʼΨһ��ʶ������ɾ�������ӡ� 
    public static void main(String args[]) 
    { 
    	System.out.println(Perfect.perfect("bab"));
    } 
    //end //��ʾ���Զ��ľ����Ψһ��ʶ������ɾ�������ӡ�

}
