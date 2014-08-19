package com.algorithm.zxie.wordLadder;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

/**
 * The problem:
 * 
 * Given two words (start and end), and a dictionary, find the length of shortest transformation sequence from start to end, such that:
 * <li>Only one letter can be changed at a time 
 * <li>Each intermediate word must exist in the dictionary
 * <p>
 * <p>
 * <b>For example</b>,
 * <p>
 * <p>
 * Given: start = "hit", end = "cog", dict = ["hot","dot","dog","lot","log"]
 * <p>
 * <P>As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog", return its length 5.
 * <p><b>Note:</b>
 * Return 0 if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * @author zxie
 *
 */

public class WordLadder {

	public static void main(String[] args) {
		HashSet<String> dict = new HashSet<>();
		dict.add("hot");
		dict.add("dot");
		dict.add("dog");
		dict.add("log");
		dict.add("lot");

		WordLadder wl = new WordLadder();
		System.out.println(wl.wordLadderWithDepth("hit", "cog", dict));
		
		dict.add("hot");
		dict.add("dot");
		dict.add("dog");
		dict.add("log");
		dict.add("lot");
		System.out.println(wl.wordLadderWithBreath("hit", "cog", dict));

	}

	class Item {
		String word;
		int length;

		public Item(String word, int length){
			this.word = word;
			this.length = length;
		}

		public void setWord(String word){
			this.word = word;
		}

		public String getWord(){
			return this.word;
		}

		public void setLength(int length){
			this.length = length;
		}

		public int getLength(){
			return this.length;
		}
	}

	/**
	 * implement word ladder with depth search, can't make sure the shortest distance
	 * 
	 * @param start start word
	 * @param end object word to be
	 * @param dict words dictionary
	 * @return the distance from start to end
	 */
	public int wordLadderWithDepth(String start, String end, HashSet<String> dict){
		if(dict.size() == 0)
			return 0;

		Stack<Item> words = new Stack<>();

		Item item = new Item(start,1);
		words.push(item);

		while(!words.isEmpty()){
			item = words.pop();
			String currWord = item.getWord().trim();
			int currDistance = item.getLength();

			if(currWord.equals(end))
				return currDistance;
			
			currDistance = currDistance + 1;
			for(int i=0; i<currWord.length(); i++){
				char[] currCharArr = currWord.toCharArray();
				for(char c='a'; c<='z'; c++){
					currCharArr[i] = c;
					String newWord = new String(currCharArr);
					if(newWord.equals(end))
						return currDistance;
					else if(dict.contains(newWord)){
						words.push(new Item(newWord, currDistance));
						dict.remove(newWord);
					}
				}
			}
		}
		return 0;
	}

	/**
	 * implement word ladder with breath search, can make sure the shortest distance
	 * 
	 * @param start start word
	 * @param end object word to be
	 * @param dict words dictionary
	 * @return the distance from start to end
	 */
	public int wordLadderWithBreath(String start, String end, HashSet<String> dict){
		if(dict.size() == 0)
			return 0;

		LinkedList<Item> words = new LinkedList<>();

		Item item = new Item(start,1);
		words.add(item);

		while(!words.isEmpty()){
			item = words.pop();
			String currWord = item.getWord().trim();
			int currDistance = item.getLength();

			if(currWord.equals(end))
				return currDistance;

			currDistance = currDistance+1;
			for(int i=0; i<currWord.length(); i++){
				char[] currCharArr = currWord.toCharArray();
				for(char c='a'; c<='z'; c++){
					currCharArr[i] = c;
					String newWord = new String(currCharArr);
					if(newWord.equals(end))
						return currDistance;
					else if(dict.contains(newWord)){
						words.push(new Item(newWord, currDistance));
						dict.remove(newWord);
					}
				}
			}
		}

		return 0;
	}
}
