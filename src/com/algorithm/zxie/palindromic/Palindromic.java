package com.algorithm.zxie.palindromic;

public class Palindromic {

	public static void main(String[] args) throws Exception {
		Palindromic pd = new Palindromic();
		pd.longestPalindrome("dabbadeedbacefecab");
		pd.longestPalindrome("s");
		pd.longestPalindrome(null);
	}
	
	public  void longestPalindrome(String s) throws Exception{
		if(s == null) throw new Exception("String is null");
		printTable(longestPalindrome(s, longestPalindrome(s, s.length())));
	}
	
	private String[][] longestPalindrome(String s, int[][] idxTab){
		int length = idxTab.length;
		String[][] palindromeStr = new String[length][length];
		for(int i=0; i<length; i++){
			for(int j=i; j<length; j++){
				if(idxTab[i][j] != 0)
					palindromeStr[i][j] = s.substring(i,j+1);
			}
		}
		return palindromeStr;
	}

	private int[][] longestPalindrome(String s, int length) {
		
		int[][] table = new int[length][length];

		//every single letter is palindrome
		for (int i = 0; i < length; i++) {
			table[i][i] = 1;
		}
		//		printTable(table);

		//e.g. bcba
		//two consecutive same letters are palindrome
		for (int i = 0; i <= length - 2; i++) {
			if (s.charAt(i) == s.charAt(i + 1)){
				table[i][i + 1] = 1;
			}	
		}
		printTable(table);
		//condition for calculate whole table
		for (int l = 3; l <= length; l++) {
			for (int i = 0; i <= length-l; i++) {
				int j = i + l - 1;
				if (s.charAt(i) == s.charAt(j)) {
					table[i][j] = table[i + 1][j - 1];
				} else {
					table[i][j] = 0;
				}
				printTable(table);
			}
		}

		return table;
	}
	private void printTable(int[][] x){
		for(int [] y : x){
			for(int z: y){
				System.out.print(z + " ");
			}
			System.out.println();
		}
		System.out.println("------");
	}
	
	private void printTable(String[][] x){
		for(String [] y : x){
			for(String z: y){
				System.out.print(z + " ");
			}
			System.out.println();
		}
		System.out.println("------");
	}
}
