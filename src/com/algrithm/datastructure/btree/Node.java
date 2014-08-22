package com.algrithm.datastructure.btree;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * B+ tree node
 * @author zxie
 *
 */
public class Node {

	/** mark if a leaf node */
	protected boolean isLeaf;
	
	/** mark if a root node */
	protected boolean isRoot;
	
	/** parent node */
	protected Node parent;
	
	/** leaf node's previous node */
	protected Node previous;
	
	/** leaf node's next node */
	protected Node next;	

	/** entry list */
	protected List<Entry<Comparable, Object>> entries;

	/** children list */
	protected List<Node> children;

	/**
	 * Construct
	 * @param isLeaf  true, node is leaf node; false node is non-leaf node
	 */
	public Node(boolean isLeaf) {
		this.isLeaf = isLeaf;
		entries = new ArrayList<Entry<Comparable, Object>>();

		if (!isLeaf) {
			children = new ArrayList<Node>();
		}
	}

	/**
	 * Constructor
	 * @param isLeaf true, node is leaf node; false, node is non-leaf node
	 * @param isRoot true, node is root node; false, node is non-root node
	 */
	public Node(boolean isLeaf, boolean isRoot) {
		this(isLeaf);
		this.isRoot = isRoot;
	}

	/**
	 * Search the value from B+ tree with the key
	 * @param key  key for search
	 * @return value related to key or null
	 */
	public Object get(Comparable key) {

		if(isLeaf){
			for(Entry<Comparable, Object> entry : entries){
				if(entry.getKey().compareTo(key) == 0)
					return entry.getValue();
			}
			return null;
		}
		else{
			int entrySize = entries.size();
			int i =  entrySize - 1;
			while(i >= 0 && entries.get(i).getKey().compareTo(key) >= 0){
				if(entries.get(i).getKey().compareTo(key) == 0)
					break;
				i--;
			}
			if(i == entrySize -1)
				return children.get(entrySize).get(key);
			else if(i == -1)
				return children.get(0).get(key);
			else
				return children.get(i).get(key);
		}
	}

	/**
	 * Insert or update an entry into/of a B+ tree, it include following steps:
	 * <li> insert an entry
	 * <li> update the B+ tree
	 * 
	 * @param key  element's key
	 * @param obj  element's value
	 * @param tree B+ tree
	 */
	public void insertOrUpdate(Comparable key, Object obj, BplusTree tree){

		//insert into leaf node
		if (isLeaf){

			insertOrUpdate(key, obj);

			if ((contains(key) || entries.size() < tree.getOrder())){// insert into a non-full leaf node
				if(isRoot == false)
					validate(parent, tree);			
			}
			else {// insert into a full leaf node
				Node left = new Node(true);
				Node right = new Node(true);                
				if (previous != null){
					previous.setNext(left);
					left.setPrevious(previous);
				}
				if (next != null) {
					next.setPrevious(right);
					right.setNext(next);
				}
				if (previous == null){
					tree.setHead(left);
				}
				left.setNext(right);
				right.setPrevious(left);
				previous = null;
				next = null;

				int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2; 
				int rightSize = (tree.getOrder() + 1) / 2;
				for (int i = 0; i < leftSize; i++){
					left.getEntries().add(entries.get(i));
				}
				for (int i = 0; i < rightSize; i++){
					right.getEntries().add(entries.get(leftSize + i));
				}

				if (parent != null) {
					int index = parent.getChildren().indexOf(this);
					parent.getChildren().remove(this);
					left.setParent(parent);
					right.setParent(parent);
					parent.getChildren().add(index,left);
					parent.getChildren().add(index + 1, right);
					setEntries(null);
					setChildren(null);

					parent.updateInsert(tree);
					setParent(null);
				}
				else {
					isRoot = false;
					Node parent = new Node(false, true);
					tree.setRoot(parent);
					left.setParent(parent);
					right.setParent(parent);
					parent.getChildren().add(left);
					parent.getChildren().add(right);
					setEntries(null);
					setChildren(null);

					validate(parent, tree);
				}
			}
		}
		else {
			int entrySize = entries.size();
			int i =  entrySize - 1;
			while(i >= 0 && entries.get(i).getKey().compareTo(key) >= 0){
				if(entries.get(i).getKey().compareTo(key) == 0)
					break;
				i--;
			}
			if(i < 0)
				children.get(0).insertOrUpdate(key, obj, tree);
			else if(i == entrySize - 1)
				children.get(i).insertOrUpdate(key, obj, tree);
			else
				children.get(i).insertOrUpdate(key, obj, tree);

		}
	}


	/**
	 * Update the B+ tree after insert a entry
	 * @param tree B+ tree
	 */
	protected void updateInsert(BplusTree tree){

		validate(this, tree);

		//If the number of key great than the number of order, split the node into two nodes	
		if (children.size() > tree.getOrder()) {

			Node left = new Node(false);
			Node right = new Node(false);
			int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2;
			int rightSize = (tree.getOrder() + 1) / 2;

			//copy children nodes and key to two new nodes
			for (int i = 0; i < leftSize; i++){
				left.getChildren().add(children.get(i));
				left.getEntries().add(new SimpleEntry(children.get(i).getEntries().get(0).getKey(), null));
				children.get(i).setParent(left);
			}
			for (int i = 0; i < rightSize; i++){
				right.getChildren().add(children.get(leftSize + i));
				right.getEntries().add(new SimpleEntry(children.get(leftSize + i).getEntries().get(0).getKey(), null));
				children.get(leftSize + i).setParent(right);
			}

			if (parent != null) {//non-root node
				int index = parent.getChildren().indexOf(this);
				parent.getChildren().remove(this);
				left.setParent(parent);
				right.setParent(parent);
				parent.getChildren().add(index, left);
				parent.getChildren().add(index + 1, right);
				setEntries(null);
				setChildren(null);

				//update parent node
				parent.updateInsert(tree);
				setParent(null);					
			}
			else {//root node
				isRoot = false;
				Node parent = new Node(false, true);
				tree.setRoot(parent);
				left.setParent(parent);
				right.setParent(parent);
				parent.getChildren().add(left);
				parent.getChildren().add(right);
				setEntries(null);
				setChildren(null);

				validate(parent, tree);
			}
		}
	}

	/**
	 * Update node entries
	 * @param node  the node to be updated
	 * @param tree  B+ tree
	 */
	protected static void validate(Node node, BplusTree tree) {
		List<Node> children = node.getChildren();
		List<Entry<Comparable,Object>> entries = node.getEntries();
		entries.clear();
		for(int i = 0; i < children.size(); i++){
			Comparable key = children.get(i).getEntries().get(0).getKey();
			entries.add(new SimpleEntry(key, null));
			if(!node.isRoot)
				validate(node.getParent(), tree);
		}
	}

	/**
	 * Update B+ tree non-leaf nodes
	 * @param tree B+ tree
	 */
	protected void updateRemove(BplusTree tree) {

		validate(this, tree);

		if(children.size() < tree.getOrder() / 2){
			if(isRoot){
				if(children.size() >= 2)
					return;
				else{
					Node root = children.get(0);
					tree.setRoot(root);
					root.setParent(null);
					root.setRoot(true);
					this.setChildren(null);
					this.setEntries(null);
				}
			}
			else{
				List<Node> pChildren = this.getParent().getChildren();
				int currIdx = pChildren.indexOf(this);
				int prevIdx = currIdx - 1;
				int nextIdx = currIdx + 1;

				Node previous = null, next = null;
				if(prevIdx >= 0)
					previous = pChildren.get(prevIdx);
				if(nextIdx < pChildren.size()-1)
					next = pChildren.get(nextIdx);
				if(previous != null 
						&& previous.getChildren().size() + children.size() > tree.getOrder()
						&& previous.getParent() == parent){//get one child from left brother
					Node node =  previous.getChildren().get(previous.getChildren().size() - 1);
					children.add(0, node);
					previous.getChildren().remove(node);
				}
				else if(next != null
						&& next.getChildren().size() + children.size() > tree.getOrder()
						&& next.getParent() == parent){ //get one child from right brother
					Node node = next.getChildren().get(0);
					children.add(node);
					next.getChildren().remove(node);
				}
				else{
					if(previous != null
							&& previous.getChildren().size() + children.size() == tree.getOrder()
							&& previous.getParent() == parent){
						Node node = null;
						for(int i = previous.getChildren().size() - 1; i >= 0; i--){
							node = previous.getChildren().get(i);
							node.setParent(this);
							children.add(0, node);				
						}
						this.getParent().getChildren().remove(prevIdx);
						previous.setChildren(null);
						previous.setEntries(null);
						previous.setParent(null);
						previous = null;
					}
					else if(next != null
							&& next.getChildren().size() + children.size() == tree.getOrder()
							&& next.getParent() == parent){
						Node node = null;
						for(int i = 0; i < next.getChildren().size(); i--){
							node = next.getChildren().get(i);
							node.setParent(this);
							children.add(node);							
						}
						this.getParent().getChildren().remove(nextIdx);
						next.setChildren(null);
						next.setEntries(null);
						next.setParent(null);
						next = null;
					}
				}
				if(previous != null)
					validate(previous, tree);
				if(next != null)
					validate(next, tree);
				validate(this, tree);
				this.getParent().updateRemove(tree);
			}
		}
	}

	/**
	 * Remove a entry from B+ Tree
	 * @param key  entry key
	 * @param tree B+ tree
	 */
	public void remove(Comparable key, BplusTree tree){

		if(isLeaf){// remove the entry from leaf node
			if (!contains(key)){
				return;
			}

			remove(key);

			if(entries.size() < tree.getOrder() / 2){
				//get an entry from previous or next node which have the same parent
				if(previous != null 
						&& previous.getParent() == parent 
						&& previous.getEntries().size() + entries.size() > tree.getOrder()){
					Entry<Comparable,Object> entry = previous.getEntries().get(previous.getEntries().size() - 1);
					entries.add(0, entry);
					previous.entries.remove(entry);
				}
				else if(next != null
						&& next.getParent() == parent
						&& next.getEntries().size() + entries.size() > tree.getOrder()){
					Entry<Comparable, Object> entry = next.getEntries().get(0);
					entries.add(entry);
					next.entries.remove(entry);
				}
				// merger previous or next node
				else if(previous != null
						&& previous.getParent() == parent
						&& previous.getEntries().size() + entries.size() == tree.getOrder()){
					Node tmp = previous;
					for(int i = 0; i < previous.getEntries().size(); i++)
						entries.add(i, previous.getEntries().get(i));
					if(previous.previous != null){
						previous.previous.next = this;
						this.previous = previous.previous;
					}
					else{
						tree.setHead(this);
					}
					parent.getChildren().remove(tmp);
					tmp.setParent(null);
					tmp.setPrevious(null);
					tmp.setNext(null);
					tmp.getEntries().clear();
				}
				else if(next != null
						&& next.getParent() == parent
						&& next.getEntries().size() + entries.size() == tree.getOrder()){
					Node tmp = next;
					for(int i = 0; i < next.getEntries().size(); i++)
						entries.add(next.getEntries().get(i));
					if(next.next != null){
						next.next.previous = this;
						next = next.next;
					}
					parent.getChildren().remove(tmp);
					tmp.setParent(null);
					tmp.setPrevious(null);
					tmp.setNext(null);
					tmp.getEntries().clear();
				}
			}

			if(!isRoot)
				parent.updateRemove(tree);
		}// non leaf node
		else {
			if (key.compareTo(entries.get(0).getKey()) <= 0) {
				children.get(0).remove(key, tree);
			}
			else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) {
				children.get(children.size()-1).remove(key, tree);
			}
			else {
				for (int i = 0; i < entries.size(); i++) {
					if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i+1).getKey().compareTo(key) > 0) {
						children.get(i).remove(key, tree);
						break;
					}
				}	
			}
		}
	}

	/**
	 * Check if current node contains an entry with key
	 * @param key  entry key
	 * @return true if contains, or false
	 */
	protected boolean contains(Comparable key) {
		for (Entry<Comparable, Object> entry : entries) 
			if (entry.getKey().compareTo(key) == 0) 
				return true;
		return false;
	}


	/**
	 * Insert a element to a leaf node, or update the element's value
	 * @param key  element key
	 * @param obj  element value
	 */
	protected void insertOrUpdate(Comparable key, Object obj){
		Entry<Comparable, Object> entry = new SimpleEntry<Comparable, Object>(key, obj);

		int entrySize = entries.size();
		if (entrySize == 0) {
			entries.add(entry);
			return;
		}

		int i = entrySize - 1;
		for(; i >= 0 && key.compareTo(entries.get(i).getKey()) <= 0; i--){
			if(key.compareTo(entries.get(i).getKey()) == 0){
				entries.get(i).setValue(obj);
				return;
			}
		}

		if(i == -1)
			entries.add(0, entry);
		else if(i == entrySize - 1)
			entries.add(entry);
		else
			entries.add(i, entry);
	}

	/**
	 * Remove entry from a node
	 * @param key  entry key
	 */
	protected void remove(Comparable key){
		int index = -1;
		for (int i = 0; i < entries.size(); i++) {
			if (entries.get(i).getKey().compareTo(key) == 0) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			entries.remove(index);
		}
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Entry<Comparable, Object>> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry<Comparable, Object>> entries) {
		this.entries = entries;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("isRoot: ");
		sb.append(isRoot);
		sb.append(", ");
		sb.append("isLeaf: ");
		sb.append(isLeaf);
		sb.append(", ");
		sb.append("keys: ");
		for (Entry entry : entries){
			sb.append(entry.getKey());
			sb.append(", ");
		}
		sb.append(", ");
		return sb.toString();

	}

}

