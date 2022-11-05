import java.util.ArrayList;

public class QuestionThree {
	public static void main(String[] args) {
		BinaryTree theTree = new BinaryTree();
		theTree.insert(50, 1.5);
		theTree.insert(25, 1.2);
		theTree.insert(75, 1.7);
		theTree.insert(12, 1.5);
		theTree.insert(37, 1.2);
		theTree.insert(43, 1.7);
		theTree.insert(30, 1.5);
		theTree.insert(33, 1.2);
		theTree.insert(87, 1.7);
		theTree.insert(93, 1.5);
		theTree.insert(97, 1.5);
		
		theTree.traverse(1, 5);
		theTree.traverse(2, 5);
		theTree.traverse(3, 5);
		theTree.traverse(4, 5);
		theTree.traverse(5, 5);
	}
}

class Node{
	public int iData;              //Data key
	public double dData;           
	public Node leftChild;         // this node's left child
	public Node rightChild;        // this node's right child
	//display
	public void displayNode() {
		System.out.println("{" + iData + "," + dData  + "}");
	}
}

class BinaryTree{
	private Node root; //Define root
	
//constructor
	public BinaryTree(){
		root = null;
	}
	
//Insert
	public void insert(int id, double dd) {//O(log(n)) Worst:O(n)if really unbalanced
		Node newNode = new Node();
		newNode.iData = id;
		newNode.dData = dd;
		if (root == null) {//if empty tree, set the root
 			root = newNode;
		}else {
			Node current = root;
			Node parent;
			while (true) {
				parent = current; 
				if (id < current.iData) {//Go left?
					current = current.leftChild;
					if (current == null) {//end of the branch & empty branch
						parent.leftChild = newNode;//insert as the left child
						return; //finish inserting
					}
				}//end of go left
				else {//Go right? (>= root)
					current = current.rightChild;
					if (current == null) {//end of the branch & empty branch
						parent.rightChild = newNode;//insert as the right child
						return; //finish inserting
					}
				}//end of go right
			}//end of while loop
		}//end of else 
	}//end of insert()
	
//Find
	public Node find(int key) {//O(log(n)) Worst:O(n)if really unbalanced
		Node current = root;
		while (current.iData != key) {//while no match
			if (key < current.iData) {//go left?
				current = current.leftChild;
			}else {//go right?
				current = current.rightChild;
			}
			if (current == null) {//if no child
				return null;//find nothing
			}
		}
		return current;//found it
	}//end of find()
	
//Delete
	public boolean delete(int key) { // delete node with given key
										// (assumes non-empty list)
		Node current = root;
		Node parent = root;

		boolean isLeftChild = true;

		while (current.iData != key) // search for node
		{
			parent = current;
			if (key < current.iData) // go left?
			{
				isLeftChild = true;
				current = current.leftChild;
			} else // or go right?
			{
				isLeftChild = false;
				current = current.rightChild;
			}
			if (current == null) // end of the line,
				return false; // didn't find it
		} // end while
		// found node to delete

		// if no children, simply delete it
		if (current.leftChild == null && current.rightChild == null) {
			if (current == root) // if root,
				root = null; // tree is empty
			else if (isLeftChild)
				parent.leftChild = null; // disconnect
			else // from parent
				parent.rightChild = null;
		}

		// if no right child, replace with left subtree
		else if (current.rightChild == null)
			if (current == root)
				root = current.leftChild;
			else if (isLeftChild)
				parent.leftChild = current.leftChild;
			else
				parent.rightChild = current.leftChild;

		// if no left child, replace with right subtree
		else if (current.leftChild == null)
			if (current == root)
				root = current.rightChild;
			else if (isLeftChild)
				parent.leftChild = current.rightChild;
			else
				parent.rightChild = current.rightChild;

		else // two children, so replace with inorder successor
		{
			// get successor of node to delete (current)
			Node successor = getSuccessor(current);

			// connect parent of current to successor instead
			if (current == root)
				root = successor;
			else if (isLeftChild)
				parent.leftChild = successor;
			else
				parent.rightChild = successor;

			// connect successor to current's left child
			successor.leftChild = current.leftChild;
		} // end else two children
		// (successor cannot have a left child)
		return true; // success
	} // end delete()

	private Node getSuccessor(Node delNode) {
		Node successorParent = delNode;
		Node successor = delNode;
		Node current = delNode.rightChild; // go to right child
		while (current != null) // until no more
		{ // left children,
			successorParent = successor;
			successor = current;
			current = current.leftChild; // go to left child
		}
		// if successor not
		if (successor != delNode.rightChild) // right child,
		{ // make connections
			successorParent.leftChild = successor.rightChild;
			successor.rightChild = delNode.rightChild;
		}
		return successor;
	}// end getSuccessor
//end Delete	
	
//Traversal:preOrder,inOrder,postOrder
	public void traverse(int traverseType,int k) {
		switch (traverseType) {
		case 1:
			System.out.print("\nPreorder traversal: ");
			preOrder(root);
			break;
		case 2:
			System.out.print("\nInorder traversal:  ");
			inOrder(root);
			break;
		case 3:
			System.out.print("\nPostorder traversal: ");
			postOrder(root);
			break;
		
		case 4:
			kthInPostOrder(root,k);
			break;
		
		case 5:
			kthSmallest(root,k);
			break;
		}
		System.out.println();	
	}
	
	private void preOrder(Node localRoot) {
		if (localRoot != null) {
			System.out.print(localRoot.iData + " ");
			preOrder(localRoot.leftChild);
			preOrder(localRoot.rightChild);
		}
	}

	private void inOrder(Node localRoot) {
		if (localRoot != null) {
			inOrder(localRoot.leftChild);
			System.out.print(localRoot.iData + " ");
			inOrder(localRoot.rightChild);
		}
	}
	
	static int flag;
	private Node findKthSmallest(Node localRoot, int k) {
		if (localRoot == null) {
			return null;
		}
		Node left = findKthSmallest(localRoot.leftChild, k);
		if (left != null) 
            return left; 
        flag++; 
        if (flag == k) 
            return localRoot; 
        // else search in right subtree 
        return findKthSmallest(localRoot.rightChild, k); 
	}
	
	private void kthSmallest(Node root, int k) {
		flag = 0;
	    Node res = findKthSmallest(root, k); 
	    if (res == null) 
	        System.out.println("There are less than k nodes in the BST"); 
	    else
	        System.out.println("K-th Smallest Element is " + res.iData); 
	}

	private void postOrder(Node localRoot) {
		if (localRoot != null) {
			postOrder(localRoot.leftChild);
			postOrder(localRoot.rightChild);
			System.out.print(localRoot.iData + " ");
		}
	}
	
	private void kthInPostOrder(Node localRoot, int k) {
		
	}
	
	
//end Traversals
	
}