package datastructure;

import java.util.LinkedList;

public class MaxStack<T> {

	private int maxlength;
	
	private LinkedList<T> list; 
	
	public MaxStack(int maxlength){
		this.maxlength= maxlength;
		list = new LinkedList<T>();
	}
	
	public void Add(T obj) {
		
		list.addLast(obj);
		if(list.size() > maxlength ) {
			list.removeFirst();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public T[] toArray() {
		return (T[])list.toArray();
	}
	
	public int length(){
		return list.size();
	}
}
