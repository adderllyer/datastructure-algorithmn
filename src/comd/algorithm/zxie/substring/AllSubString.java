package comd.algorithm.zxie.substring;

import java.util.Iterator;
import java.util.LinkedList;

public class AllSubString {

	public LinkedList<String> allSubString(String str){
		LinkedList<String> ll = new LinkedList<>();
		for(int i = 1; i<=str.length(); i++){
			for(int j=0; j<str.length()&&i+j<=str.length(); j++){
				ll.add(str.substring(j, j+i));
			}
		}
		return ll;
	}

	public void allSubString1(String str, LinkedList<String> ll){

		if(str == null)
			return ;
		if(str.length() == 1){
			ll.add(str);
			return;
		}

		ll.add(str);
		String sub;
		
	}

	public static void main(String[] args) {
		AllSubString as = new AllSubString();
		LinkedList<String> ll = new LinkedList<>();
		ll = as.allSubString("abcdefgh");
		Iterator<String> itor = ll.iterator();
		while(itor.hasNext()){
			System.out.println(itor.next());
		}
	}

}
