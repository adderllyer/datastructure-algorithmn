package com.algorithm.zxie.wordbreak;

/**
 * Given a string s and a dictionary of words dict, determine if s can be 
 * segmented into a space-separated sequence of one or more dictionary words.
 * For example, given
 * s = ¡°leetcode¡±,
 * dict = ["leet", "code"].
 * 
 * Return true because ¡°leetcode¡± can be segmented as ¡°leet code¡±.
 */

import java.util.HashSet;
import java.util.Set;

public class WordBreak {

	public static void main(String[] args) {
		Set<String> words = new HashSet<>();
		words.add("programcree");
		words.add("program");
		words.add("creek");

		WordBreak wb = new WordBreak();
		boolean result = wb.wordBreak("programcreek", words);
		System.out.println(result);
	}

	/**
	 * The key to solve this problem by using dynamic programming approach:
	 * <li>Define an array t[] such that t[i]==true => 0-(i-1) can be segmented using dictionary
	 * <li>Initial state t[0] == true
	 * 
	 * @param s string use for word break
	 * @param dict set store the words
	 * @return true if s can split to words which were dict, false else
	 */
	public boolean wordBreak(String s, Set<String> dict) {
		boolean[] t = new boolean[s.length()+1];
		t[0] = true; //set first to be true, why?
		//Because we need initial state

		for(int i=0; i<s.length(); i++){
			//should continue from match position
			if(!t[i]) 
				continue;

			for(String a: dict){
				int len = a.length();
				int end = i + len;
				if(end > s.length())
					continue;

				if(t[end]) continue;

				if(s.substring(i, end).equals(a)){
					t[end] = true;
				}
			}
		}

		for(boolean b:t)
			System.out.println(b);
		return t[s.length()];
	}

	public boolean wordBreak_(String s, Set<String> dict) {
		return wordBreakHelper(s, dict, 0);
	}

	public boolean wordBreakHelper(String s, Set<String> dict, int start){
		if(start == s.length()) 
			return true;

		for(String a: dict){
			int len = a.length();
			int end = start+len;

			//end index should be <= string length
			if(end > s.length()) 
				continue;

			if(s.substring(start, start+len).equals(a))
				if(wordBreakHelper(s, dict, start+len))
					return true;
		}

		return false;
	}


}
