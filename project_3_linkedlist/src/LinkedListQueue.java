
public class LinkedListQueue<T> {
	
	public Node<T> first;
	public Node<T> last;
	public int size;
	public class Node<T>{
		public T data;
		public Node<T> next;
	}
	public void enqueue(T t){
		Node<T> addLast = new Node<T>();
		addLast.data = t;
		if (last != null) {
			last.next = addLast;
			last = addLast;
		}else {
			first = addLast;
			last = addLast;
		}
		size++;
	}
	
	public T dequeue(){
		if (first == null/*if first is null, then last is null*/) {
			return null;
		}else {
			T t = first.data;
			first = first.next;
			size--;
			return t;
		}
		
	}
}
