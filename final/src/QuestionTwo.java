
public class QuestionTwo {
	public static void main(String[] args) {
		StudentList list = new StudentList();
		
		list.addLast(666, "Hua", "Wei");
		list.addLast(123, "Sam", "Sun");
		list.addLast(8849, "Shi", "Wang");
		list.addLast(8848, "Shi", "Wang");
		list.addLast(756, "Jane", "Doe");
		System.out.println("Input List:");
		list.displayList();
		System.out.println("Reversed List:");
		list.displayList();
		//merge sort
		System.out.println("After Merge Sort:");
		list.setFirst(list.mergeSort(list.getFirst()));
//		System.out.println("After Quick Sort:");
//		list.quickSort(list.getFirst(), list.getLast());
		list.displayList();
		list.search(666);
		list.count();
		list.displayList();
		list.delete(8848);
		list.displayList();
	}
}

class Student {
	private int ID;
	private String firstName;
	private String lastName;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}

// Begin Node Class--------------------------------------
class Link {
	public Student sData;
	public Link next;

	public Link(int id, String fn, String ln) {
		Student newE = new Student();
		newE.setID(id);
		newE.setFirstName(fn);
		newE.setLastName(ln);
		this.sData = newE;
	}

	public void displayNode() {
		System.out.println("{" + sData.getID() + "," + sData.getFirstName() + "," + sData.getLastName() + "}");
	}
}

// End Node Class-----------------------------------------
class StudentList {
	private Link first;
	private Link last;

	

	// constructor
	public StudentList() {
		first = null;
	}
	
	public Link getFirst() {
        return first;
    }

    public void setFirst(Link first) {
        this.first = first;
    }
    
    public Link getLast() {
    	Link last = first;
    	if (last == null || last.next == null) {
    		return first;
    	}
    	while (last.next != null) {
    		last = last.next;
    	}
    	return last;
    }
    
    public void setLast(Link last) {
		this.last = last;
	}
    //Display the list
	public void displayList() {
		Link current = first;
		while (current != null) {
			current.displayNode();
			current = current.next;
		}
	}
	
	
	// A -- Add a Student to the end of list
	// O(n)
	public void addLast(int id, String fn, String ln) {
		if (last == null) {//so first is also null
			first = new Link(id, fn, ln);
			setLast(first);
			return;
		}
		Link addLast = new Link(id, fn, ln);
		this.getLast().next = addLast;
		last = addLast;
	}
	

	// B -- Edit a Student's ID
	// O(n)
	public Link editID(int oldID, int newID) {
		// find the student with given oldID first
		Link current = first;
		while (current.sData.getID() != oldID) {
			// reached the end of the List
			if (current.next == null) {
				System.out.println("Not found, no change.");
				return null;
			} else {
				current = current.next;
			}
		}
		current.sData.setID(newID);
		current.displayNode();
		return current;
	}

	// C -- Delete a specific students(& all duplicates)
	// O(n)
	public Link delete(int key) { // (assumes non-empty list)
		Link previous = first;
		Link current = first;
		boolean end = false;
		while (!end) {
			while (current.sData.getID() != key) {
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
		System.out.println("Delete {"+current.sData.getID()+","
		+current.sData.getFirstName()+","+current.sData.getLastName()+"}");
		return current;
	}

	// D -- Search for a student by ID
	// O(n)
	public Link search(int key) {
		Link current = first;
		while (current.sData.getID() != key) {
			// reached the end of the List
			if (current.next == null) {
				System.out.println("Not found");
				return null;
			} else {
				current = current.next;
			}
		}
		System.out.println("Found the student with ID "+ key+":");
		current.displayNode();
		return current;
	}

	// E -- Display the current number of Students
	// O(n)
	public int count() {
		int count = 1;
		Link current = first;
		if (current == null)
			return 0;
		while (current.next != null) {
			current = current.next;
			count++;
		}
		System.out.println("There are " + count + " students in total");
		return count;
	}
    
    //F -- Sort and Display
	//O(n*log(n)),Merge Sort
	public Link mergeSort(Link head) {
        if (head == null || head.next == null) { return head; }

        Link mid = getMid(head);//obtain the middle Link

        Link second = mid.next;
        mid.next = null;

        //merge two sorted arrays
        Link left = mergeSort(head);
        Link right = mergeSort(second);

        return merge(right, left);
    }
	
	//G -- Merge 2 sorted Linked list
    //O(n)
    private Link merge(Link l1, Link l2) {
        Link dummy = new Link(0, "", "");
        Link tail = dummy;
        while (l1 != null && l2 != null) {
            if (l1.sData.getID() <= l2.sData.getID()) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next; 
        }

        if (l1 != null) {
            tail.next = l1;
        } else {
            tail.next = l2;
        }

        return dummy.next;

    }

    private Link getMid(Link head) {
        if (head == null || head.next == null) {
            return head;
        }

        Link slow = head;
        Link faster = head.next;
        while (faster != null && faster.next != null) {
            slow = slow.next;
            faster = faster.next.next;
        }
        return slow;
    }
    //end of Part G


}

	
