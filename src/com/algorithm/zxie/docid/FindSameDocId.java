package com.algorithm.zxie.docid;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class FindSameDocId {
	
	public ArrayList<String> getIds(String file) throws IOException{
		ArrayList<String> result = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		int idx = -1;
		while((line = br.readLine()) != null){
			idx = line.indexOf("DocID");
			if(idx != -1)
			result.add(line.substring(idx+6).trim());
		}
		br.close();
		return result;
	}
	
	public Vector<String> existSameID(ArrayList<String> al1, ArrayList<String> al2){
		Vector<String> vec = new Vector<String>();
		Iterator<String> iter = al1.iterator();
		String docId;
		while(iter.hasNext()){
			docId = iter.next();
			if(al2.contains(docId)){
				vec.add(docId);
			}
		}
		return vec;
	}

	public static void main(String[] args) throws IOException {
		FindSameDocId fdi = new FindSameDocId();
		ArrayList<String> al1 = fdi.getIds(args[0]);
		ArrayList<String> al2 = fdi.getIds(args[1]);
		Vector<String> vec = fdi.existSameID(al1, al2);
		if(!vec.isEmpty()){
			System.out.println("two query results include same docId");
			StringBuilder docIds = new StringBuilder();
			Iterator<String> iter = vec.iterator();
			while(iter.hasNext()){
				docIds.append(iter.next()+",");
			}
			System.out.println(docIds.toString());
		}
		else{
			System.out.println("two query results didn't include same docId");
		}
	}

}
