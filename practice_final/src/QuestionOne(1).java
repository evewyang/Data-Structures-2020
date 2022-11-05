public class QuestionOne {
    // This program will implement Linked List
    // Goal: manage employees(user ID(int), first name, last name)
    public static void main(String[] args) {
        System.out.println("Welcome!");
        EmployeeList list = new EmployeeList();

        list.addFirst(666, "Hua", "Wei");
        list.addFirst(123, "Sam", "Sun");
        list.addFirst(8848, "Shi", "Wang");
        list.displayList();
        System.out.println("-------");
        list.setFirst(list.mergeSort(list.getFirst()));
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
    private int    ID;
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
class Node {
    public Employee eData;
    public Node     next;

    public Node(int id, String fn, String ln) {
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

// End Node
class EmployeeList {
    private Node first;

    // constructor
    public EmployeeList() {
        first = null;
    }

    // A -- Add an Employee
    // O(1)
    public void addFirst(int id, String fn, String ln) {
        if (first == null) {
            first = new Node(id, fn, ln);
            return;
        }
        Node addFirst = new Node(id, fn, ln);
        addFirst.next = first;
        first = addFirst;
    }

    // B -- Edit an Empoyee's ID
    // O(n)
    public Node editID(int oldID, int newID) {
        // find the employee with given oldID first
        Node current = first;
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
    public Node delete(int key) { // (assumes non-empty list)
        Node previous = first;
        Node current = first;
        boolean end = false;
        while (!end) {
            while (current.eData.getID() != key) {
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
        return current;
    }

    // D -- Search for an employee
    // O(n)
    public Node search(int key) {
        Node current = first;
        while (current.eData.getID() != key) {
            // reached the end of the List
            if (current.next == null) {
                System.out.println("Not found");
                return null;
            } else {
                current = current.next;
            }
        }
        current.displayNode();
        return current;
    }

    // E -- Display the current number of employees
    // O(n)
    public int count() {
        int count = 1;
        Node current = first;
        if (current == null) { return 0; }
        while (current.next != null) {
            current = current.next;
            count++;
        }
        System.out.println("There are " + count + " employees in total");
        return count;
    }


    public Node mergeSort(Node head) {
        if (head == null || head.next == null) { return head; }

        Node mid = getMid(head);//获取链表中间节点

        Node second = mid.next;
        mid.next = null;

        //对两个子链表排序
        Node left = mergeSort(head);
        Node right = mergeSort(second);

        return merge(right, left);
    }

    //两个有序链表的归并
    private Node merge(Node l1, Node l2) {
        //辅助节点，排好序的节点将会链接到dummy后面
        Node dummy = new Node(0, "", "");

        Node tail = dummy;//tail指向最后一个排好序的节点
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

    //返回链表之间节点
    private Node getMid(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node slow = head;
        Node faster = head.next;
        while (faster != null && faster.next != null) {
            slow = slow.next;
            faster = faster.next.next;
        }
        return slow;
    }

    public void displayList() {
        Node current = first;
        while (current != null) {
            current.displayNode();
            current = current.next;
        }
    }

    // G -- Delete Employees that have the same first & last names
    // O(n)
    public void deleteDuplicates() {
        Node search = first;
        Node current = first;
        Node previous = first;
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
                if (current == search) {
                    previous.next = current.next;
                }
            }
            search = search.next;
        }
    }

    // H -- Find two employee whose IDs sums to a number x
    // O(n)
    public void findSum(int x) {
        Node search = first;
        Node current = first;
        int existSum = 0;
        // size = 0 or size = 1, cannot form a "sum"
        if (search == null || search.next == null) {
            System.out.println("Not enough employees.");
            return;
        }
        while (search != null) {
            while (current != null) {
                if (search.eData.getID() + current.eData.getID() == x) {
                    System.out.println(search.eData.getFirstName() + " " + search.eData.getLastName() + ","
                            + current.eData.getFirstName() + " " + current.eData.getLastName());
                    existSum++;
                    search = search.next;// As ID is unique, one can only pair with at most one to sum to x
                    current = search.next;
                }
                current = current.next;
            }
        }
        if (existSum == 0) {
            System.out.println("No such pairs whose ID's sums to X");
        }
    }

    public Node getFirst() {
        return first;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

}
