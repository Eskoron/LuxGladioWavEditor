package datastructure;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MaxStack<T> {

	private int maxlength;
	
	private ConcurrentLinkedQueue<T> list; 
	
	public MaxStack(int maxlength){
		this.maxlength= maxlength;
		list = new ConcurrentLinkedQueue<T>();
	}
	
	public void Add(T obj) {
		list.add(obj);
		if(list.size() > maxlength ) {
			list.poll();
		}
	}
	
	
	public List<T> PollList(){
		LinkedList<T> llist = new LinkedList<T>();
		int i = 0;
		while(!list.isEmpty() || i>maxlength) {
		llist.add(list.remove());
		}
		return llist;
	}
	
	public int length(){
		return list.size();
	}
}
