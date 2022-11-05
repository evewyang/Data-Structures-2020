
public class QuestionOne {
	public static void main(String[] args) {
		BookList bookStore = new BookList();
		
		bookStore.addFirst(101002,"Intro to Java",100.00);
		bookStore.addFirst(105102, "Data Structure",102.00);
		bookStore.addFirst(201008, "CSO",30.00);
		bookStore.addFirst(110310, "Basic Algorithms",85.00);
		bookStore.addFirst(134480, "Artificial Intelligence",98.00);
		
		bookStore.displayBooks();
		bookStore.findCheapest();
		bookStore.delete("CSO");
		bookStore.displayBooks();
	}
	/*Advantages: Adding the book is really fast. O(1) is always the time. 
	 * it is also really straight forward for deleting because searching at best 
	 * can be O(1)(if at the beginning) or at most O(n); Linked List donesn't 
	 * restrict size, so it can be used for unknown number of data points, and 
	 * insertion is made very flexible,fast and easy. 
	 * Especially when the data points are in relative small numbers, it is very useful.
	 */
	
	/*Disadvantages: comparing to hash table, it doesn't give you direct positioning 
	 * when searching. It always needs to be started at the beginning(or the end) 
	 * so the random access is not-so-efficient. Also, we can't use for-each loop 
	 * and indexing like arrays to locate, rather, we need to use iterator. 
	 */
}
class Book {
	//Assume that each book has an ISBN number i.e.ID
	private int ID;
	private String name;
	private double price;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}

class Node {
	public Book bData;
	public Node next;
	
	public Node(int id, String n, double p) {
		Book book = new Book();
		book.setID(id);
		book.setName(n);
		book.setPrice(p);
		this.bData = book;
	}

	public void displayNode() {
		System.out.println("{" + bData.getID() + "," + bData.getName() + "," + bData.getPrice() + "}");
	}
}

class BookList{
	private Node first;
	
	public BookList() {
		first = null;
	}
	
	//1.add a book
	public void addFirst(int id, String n, double p) {
		if (first == null) {
			first = new Node(id, n, p);
			return;
		}
		Node addFirst = new Node(id, n, p);
		addFirst.next = first;
		first = addFirst;
	}
	
	//2.delete a book
	public Node delete(String name) { 
		Node previous = first;
		Node current = first;
		boolean end = false;
		if (first == null) {
			return null;// empty list
		}
		while (!end) {
			while (!current.bData.getName().equals(name)) {
				if (current.next == null) {
					return null;
				} // not found, no deletion :(
				else {
					previous = current; // keep track of the previous for removing
					current = current.next; // go to next link
				}
			}
			if (current == first) {
				first = current.next; // set first to 2nd Link
			} else {
				previous.next = current.next; // connect the previous Link to 2 Links after
			}
			if (current.next != null) {
				current = current.next;
				continue;
			} else {
				end = true;
			}
		}
		System.out.println("Delete {"+current.bData.getID()+","
		+current.bData.getName()+","+current.bData.getPrice()+"}");
		return current;
	}
	//3. Find the cheapest
	public Node findCheapest() {
		Node current = this.first;
		Node cheap = this.first;
		double lowestPrice = first.bData.getPrice();
		while (current.next != null && current != null) {
			if (current.bData.getPrice()< lowestPrice) {
				lowestPrice = current.bData.getPrice();
				cheap = current;
			}
			current = current.next;
		}
		System.out.println("Book with lowest price: {"+cheap.bData.getID()+","
				+cheap.bData.getName()+","+cheap.bData.getPrice()+"}");
		return cheap;
	}
	
	public void displayBooks() {
		Node current = first;
		while (current != null) {
			current.displayNode();
			current = current.next;
		}
	}
	
}