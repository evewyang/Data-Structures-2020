
public class QuestionOne {
	// This program will implement Linked List
	// Goal: manage employees(user ID(integer), first name, last name)
	public static void main(String[] args) {
		EmployeeList list = new EmployeeList();
		
		list.addFirst(666, "Hua", "Wei");
		list.addFirst(123, "Sam", "Sun");
		list.addFirst(8849, "Shi", "Wang");
		list.addFirst(8848, "Shi", "Wang");
		System.out.println("Input List:");
		list.displayList();
		list.setFirst(list.reverseList(list.getFirst()));//setFirst to change the entire List
		System.out.println("Reversed List:");
		list.displayList();
		//merge sort
//		System.out.println("After Merge Sort:");
//		list.setFirst(list.mergeSort(list.getFirst()));
		System.out.println("After Quick Sort:");
		list.quickSort(list.getFirst(), list.getLast());
		list.displayList();
		list.search(666);
		list.count();
		list.deleteDuplicates();
		list.displayList();
		list.findSum(789);
		list.delete(8848);
		list.displayList();
//		Scanner input = new Scanner(System.in);
//		menu();
//		String choice = input.next().toUpperCase();
//		if (choice.equals("Q")) {
//			input.close();
//			return;
//		}
//		while (!choice.equals("Q")) {
//			if (choice.equals("A")) {
//				System.out.print("Enter Employee's First Name");
//				String fn = input.next();
//				System.out.print("Enter Employee's Last Name");
//				String ln = input.next();
//				System.out.print("Enter Employee's ID");
//				int id = input.nextInt();
//				list.addFirst(id, fn, ln);
//			} else if (choice.equals("B")) {
//				System.out.print("Enter Employee's ID");
//				int key = input.nextInt();
//				System.out.print("Enter Employee's newID");
//				int id = input.nextInt();
//				list.editID(key, id);
//			} else if (choice.equals("C")) {
//				System.out.print("Enter Employee's ID");
//				int key = input.nextInt();
//				list.delete(key);
//			} else if (choice.equals("D")) {
//				System.out.print("Enter Employee's ID");
//				int key = input.nextInt();
//				list.search(key);
//			} else if (choice.equals("E")) {
//				list.count();
//			} else if (choice.equals("F")) {
//				list.sort();
//			} else if (choice.equals("G")) {
//				list.deleteDuplicates();
//			} else if (choice.equals("H")) {
//				System.out.print("Enter a sum:");
//				int x = input.nextInt();
//				list.findSum(x);
//			} else {
//				System.out.print("Invalid choice.");
//			}
//			menu();
//			choice = input.next().toUpperCase();
//		}
//		input.close();
	}

	public static void menu() {
		System.out.println("Type in the Letter to take action:");
		System.out.println("A -- Add an Employee");
		System.out.println("B -- Edit an Empoyee's ID");
		System.out.println("C -- Delete a specific employee");
		System.out.println("D -- Search for an employee");
		System.out.println("E -- Display the current number of employees");
		System.out.println("F -- Sort and Display");
		System.out.println("G -- Delete Employees that have the same first & last names");
		System.out.println("H -- Find two employee whose IDs sums to a number x");
		System.out.println("Q -- Quit");
	}
}

class Employee {
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
	public Employee eData;
	public Link next;

	public Link(int id, String fn, String ln) {
		Employee newE = new Employee();
		newE.setID(id);
		newE.setFirstName(fn);
		newE.setLastName(ln);
		this.eData = newE;
	}

	public void displayNode() {
		System.out.println("{" + eData.getID() + "," + eData.getFirstName() + "," + eData.getLastName() + "}");
	}
}

// End Node Class-----------------------------------------
class EmployeeList {
	private Link first;

	// constructor
	public EmployeeList() {
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
    	while (last.next != null) {
    		last = last.next;
    	}
    	return last;
    }
    //Display the list
	public void displayList() {
		Link current = first;
		while (current != null) {
			current.displayNode();
			current = current.next;
		}
	}
	//Reverse the List(with any given start)
	public Link reverseList(Link start){ 
        Link prev = null; 
        Link current = start; 
        Link next = null; 
        while (current != null) { 
            next = current.next; 
            current.next = prev; 
            prev = current; 
            current = next; 
        } 
        start = prev; 
        return start; 
    } 
  
	
	// A -- Add an Employee
	// O(1)
	public void addFirst(int id, String fn, String ln) {
		if (first == null) {
			first = new Link(id, fn, ln);
			return;
		}
		Link addFirst = new Link(id, fn, ln);
		addFirst.next = first;
		first = addFirst;
	}

	// B -- Edit an Empoyee's ID
	// O(n)
	public Link editID(int oldID, int newID) {
		// find the employee with given oldID first
		Link current = first;
		while (current.eData.getID() != oldID) {
			// reached the end of the List
			if (current.next == null) {
				System.out.println("Not found, no change.");
				return null;
			} else {
				current = current.next;
			}
		}
		current.eData.setID(newID);
		current.displayNode();
		return current;
	}

	// C -- Delete a specific employee(&all duplicates)
	// O(n)
	public Link delete(int key) { // (assumes non-empty list)
		Link previous = first;
		Link current = first;
		boolean end = false;
		while (!end) {
			while (current.eData.getID() != key) {
				if (current.next == null) {
					System.out.println("Not Found.");
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
		System.out.println("Delete {"+current.eData.getID()+","
		+current.eData.getFirstName()+","+current.eData.getLastName()+"}");
		return current;
	}

	// D -- Search for an employee
	// O(n)
	public Link search(int key) {
		Link current = first;
		while (current.eData.getID() != key) {
			// reached the end of the List
			if (current.next == null) {
				System.out.println("Not found");
				return null;
			} else {
				current = current.next;
			}
		}
		System.out.println("Found the employee with ID "+ key+":");
		current.displayNode();
		return current;
	}

	// E -- Display the current number of employees
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
		System.out.println("There are " + count + " employees in total");
		return count;
	}

	// F -- Sort and Display
	//O(n*log(n)),Merge Sort
	public Link mergeSort(Link head) {
        if (head == null || head.next == null) { return head; }

        Link mid = getMid(head);//获取链表中间节点

        Link second = mid.next;
        mid.next = null;

        //对两个子链表排序
        Link left = mergeSort(head);
        Link right = mergeSort(second);

        return merge(right, left);
    }

    //两个有序链表的归并
    private Link merge(Link l1, Link l2) {
        //辅助节点，排好序的节点将会链接到dummy后面
        Link dummy = new Link(0, "", "");

        Link tail = dummy;//tail指向最后一个排好序的节点
        while (l1 != null && l2 != null) {
            if (l1.eData.getID() <= l2.eData.getID()) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next; //移动tail指针
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
    //Quick Sort
    private Link paritionLast(Link start, Link end) 
    { 
        if(start == end || start == null || end == null) 
            return start; 
      
        Link previous = start; 
        Link curr = start;  
        Employee pivot = end.eData;  
          
        // iterate till one before the end,  
        // no need to iterate till the end  
        // because end is pivot 
        while(start != end ) { 
            if(start.eData.getID() < pivot.getID()) {  
                // keep tracks of last modified item 
                previous = curr;  
                Employee temp = curr.eData;  
                curr.eData = start.eData;  
                start.eData = temp;  
                curr = curr.next;  
            } 
            start = start.next;  
        } 
          
        Employee temp = curr.eData;  
        curr.eData = pivot;  
        end.eData = temp;  
      
        // return one previous to current 
        // because current is now pointing to pivot 
        return previous; 
    } 
    
    public void quickSort(Link start, Link end) { 
        if(start == end ) 
            return; 
              
        // split list and partition recurse 
        Link previous = paritionLast(start, end); 
        quickSort(start, previous); 
          
        // if pivot is picked and moved to the start, 
        // that means start and pivot is same  
        // so pick from next of pivot 
        if( previous != null && previous == start ) { 
            quickSort(previous.next, end); 
        }  
        
        // if pivot is in between of the list, 
        // start from next of pivot,  
        // since we have pivot_prev, so we move two nodes 
        else if(previous != null && previous.next != null) { 
            quickSort(previous.next.next, end); 
        }
    } 
    

	// G -- Delete Employees that have the same first & last names
	// O(n)
	public void deleteDuplicates() {
		Link search = first;
		Link current = first;
		Link previous = first;
		// size = 0 or size = 1, no duplicates
		if (search == null || search.next == null) {
			return;
		}
		while (search != null) {
			while (current != null) {
				if (current.next == null) {
					break;
				}
				previous = current;
				current = current.next;
				if ((current.eData.getFirstName() == search.eData.getFirstName())
						&&(current.eData.getLastName() == search.eData.getLastName())) {
					previous.next = current.next;
				}
			}
			search = search.next;
			current = search;
		}
	}

	// H -- Find two employee whose IDs sums to a number x
	// O(n^2)
	public void findSum(int x) {
		Link search = first;
		Link current = first;
		int existSum = 0;
		// size = 0 or size = 1, cannot form a "sum"
		if (search == null || search.next == null) {
			System.out.println("Not enough employees.");
			return;
		}
		while (search != null) {
			while (current != null) {
				if (search.eData.getID() + current.eData.getID() == x) {
					System.out.println("Pair that sums to "+x+":"+search.eData.getFirstName() + " " + search.eData.getLastName() + ","
							+ current.eData.getFirstName() + " " + current.eData.getLastName());
					existSum++;
					search = search.next;// As ID is unique, one can only pair with at most one to sum to x
					current = search.next;
				}
				current = current.next;
			}
			search = search.next;
			current = search;
		}
		if (existSum == 0) {
			System.out.println("No such pairs whose ID's sums to X");
		}
	}

}
