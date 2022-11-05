import java.util.ArrayList;

public class QuestionTwo {
	public static void main(String[] args) {
		Graph graph = new Graph(6);
		Vertex a = new Vertex("Person_A");
		Vertex b = new Vertex("Person_B");
		Vertex c = new Vertex("Person_C");
		Vertex d = new Vertex("Person_D");
		Vertex e = new Vertex("Person_E");
		Vertex f = new Vertex("Person_F");
		
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		
		graph.addEdges(a,b);
		graph.addEdges(a,c);
		graph.addEdges(a,d);
		graph.addEdges(b,d);
		graph.addEdges(e,f);
		graph.addEdges(f,d);
		
		graph.isConnected();
		graph.displayNeighbors(a);
		graph.displayNeighbors(e);
		graph.displaySocialCommunities();
		graph.fuzzyMembers();
		graph.getPath(a, f);
	}
	
}

class Stack{
	private final int MAX_SIZE; //Same as MAX_VERTICES
	private int[] myStack;
	private int top;
	//constructor
	public Stack(int size) {
		MAX_SIZE = size;
		myStack = new int [MAX_SIZE];
		top = -1;
	}
	//defining methods: push, pop, peek
	public void push(int i) {
		myStack[++top] = i;
	}
	
	public int pop() {
		return myStack[top--];
	}
	
	public int peek() {
		return myStack[top];
	}
	//check if empty:
	public boolean isEmpty() {
		return (top == -1);//if top reaches bottom, then empty
	}
}


class Vertex{
	public String name;
	public char label;
	public boolean wasVisited;
	public int friends_club_number;
	//constructor
	public Vertex(String name) {
		//e.g. input form "Person_A" fixed, record 'A' at 7th position
		this.name = name;
		label = name.charAt(7);
		wasVisited = false;
		friends_club_number = -1;
	}
	
}


class Graph{
	private final int MAX_VERTICES; // same as MAX_SIZE
	private Vertex vertList[];
	private int adjMatrix[][];
	private int vertNumber;
	private Stack ourStack;
	private boolean connected;
	
	
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	//constructor 
	public Graph(int size){
		MAX_VERTICES = size;
		vertList = new Vertex[MAX_VERTICES];
		adjMatrix = new int[MAX_VERTICES][MAX_VERTICES];
		vertNumber = 0;//start with 0 vertices
		for (int i = 0; i < MAX_VERTICES; i++) {
			for (int j = 0; j < MAX_VERTICES; j++) {
				adjMatrix[i][j] = 0; //set all adjacency to 0
			}
		}
		ourStack = new Stack(size);
		connected = false;
	}
	
	//1. Create Adjacency Matrix/add vertex&edges
	public void addVertex(Vertex v) {
		vertList[vertNumber++] = v;
	}
	
	public void addEdges(Vertex start, Vertex end) {
		int from = -1;
		int to = -1;
		for (int i = 0; i < MAX_VERTICES; i++) {
			if (vertList[i].label == start.label) {
				from = i;
			}else if (vertList[i].label == end.label) {
				to = i;
			}
		}
		if (from != -1 && to != -1) {
			adjMatrix[from][to] = 1; //Symmetric
			adjMatrix[to][from] = 1;
			return;
		}else {
			System.out.println("Edge "+start+","+end+" is out of boundary.");
		}
	}//end of addVertex(Vertex v)
	
	
	//Display Vertex 
	public void displayVertex(int v){
		System.out.print(vertList[v].name);
    }
	
	//2.A Display the neighbors of a given node
	public void displayNeighbors(Vertex v) {
		char key = v.label;
		int find = -1;
		for (int i = 0;i < MAX_VERTICES; i++) {
			if (key == vertList[i].label) {
				find  = i;
				break;
			}
		}
		if (find == -1) {System.out.println("No such person."); return;}
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		for (int j = 0; j< MAX_VERTICES; j++) {
			if (adjMatrix[find][j] != 0) {
				neighbors.add(j);
			}
		}
		if (neighbors.size() != 0) {
			System.out.print("Neighbors of "+v.name+" are:");
			for (int k: neighbors) {
				System.out.print(" "+vertList[k].name+" ");
			}
			System.out.println();
		}else {
			System.out.println(v.name+" has no neighbors.");
		}
	}//end of displayNeighbors(Vertex v)
	
	//2.B Check if the graph is connected or Not 
	public boolean isConnected() {
		dfs(vertList[0]);
		if (connected) {
			System.out.println("The graph is connected.");
		}else {
			System.out.println("The graph is not connected.");
		}
		return connected;
	}//end of isConnected()
	
	//Depth-first search
	public void dfs(Vertex a) { 
		int start = -1;
		for (int i = 0; i < MAX_VERTICES; i++) {
			if (a.label == vertList[i].label) {
				start = i;
				break;
			}
		}
		if (start == -1) {
			System.out.println("No such person.");
			return;
		}
		vertList[start].wasVisited = true; // mark it
		vertList[start].friends_club_number = start;
		ourStack.push(start); // push it
		while (!ourStack.isEmpty()) {
			int v = getAdjUnvisitedVertex(ourStack.peek()); // get an unvisited vertex of this peeked vertex
			if (v == -1) {
				ourStack.pop();// if has no more unvisited vertex, begin pop
			} else {
				vertList[v].wasVisited = true; // mark it
				vertList[v].friends_club_number = start;
				ourStack.push(v); // push it
			}
		}// finish once our stack is empty
		
		for (int i = 0; i < vertNumber; i++) { // check connectivity 
			if (vertList[i].wasVisited == false) {
				setConnected(false);
				break;
			}else {
				setConnected(true);
			}
		}
		
		for (int j = 0; j < vertNumber; j++) { // reset to unvisited
			vertList[j].wasVisited = false;
		}
		
	}//end of DFS(Vertex a)
	
	
	//get unvisited vertex linked to a vertex in our adjacency matrix 
	public int getAdjUnvisitedVertex(int v) {
		for (int j = 0; j < vertNumber; j++) {
			if (adjMatrix[v][j] == 1 && vertList[j].wasVisited == false) {
				return j;
			}
		}
		return -1;
	}//end of getAdjUnvisitedVertex(int v)
	//end of 2B: Connectivity
	
	//2.C Display potential social communities
	
	public void displaySocialCommunities() {
		 ArrayList<Vertex> visited = new ArrayList<Vertex>();
		 for (int i = 0; i < MAX_VERTICES;i++ ) {
			 if (visited.contains(vertList[i])) {
				 continue;
			 }
			 dfs(vertList[i]);
			 System.out.print("Community:");
			 for (Vertex v: vertList) {
				 if (v.friends_club_number == i) {
					 visited.add(v);
					 System.out.print(v.name+" ");
				 }
			 }
			 System.out.println();
			 
		 }
	}//end of displaySocialCommunities()
	
	//2.D Fuzzy social numbers
	public void fuzzyMembers() {
		if (vertNumber <= 2) {
			System.out.print("Too few Vertices, no Fuzzy social members.");
			return;
		}
		dfs(vertList[0]);
		if (!connected) {
			System.out.println("There's no Fuzzy social members as the graph is disconnected.");
			return;
		}
		ArrayList<Vertex> fuzzies = new ArrayList<Vertex>();
		if (dfs(vertList[1], vertList[0])) {
			fuzzies.add(vertList[0]);
		}
		for (int i = 1; i < MAX_VERTICES; i++) {
			if (dfs(vertList[0], vertList[i])) {
				fuzzies.add(vertList[i]);
			}
		}
		if (fuzzies.size() != 0) {
			System.out.print("Fuzzy Social Members:");
			for (Vertex v : fuzzies) {
				System.out.print(v.name + " ");
			}
			System.out.println();
		} else {
			System.out.println("No Fuzzy social memebers.");
		}

	}//end of fuzzyMembers()/find articulation point
	
	//DFS with omission of a vertex
	public boolean dfs(Vertex a, Vertex b) { 
		boolean isFuzzy = false;
		int start = -1;
		int omit = -1;
		b.wasVisited = true;
		for (int i = 0; i < MAX_VERTICES; i++) {
			if (a.label == vertList[i].label) {
				start = i;
			}
			if (b.label == vertList[i].label) {
				omit = i;
			}
		}
		if (start == -1 || omit == -1) {
			return false;
		}
		vertList[start].wasVisited = true; // mark it
		ourStack.push(start); // push it
		while (!ourStack.isEmpty()) {
			int v = getAdjOmitVertex(ourStack.peek(),omit); // get an unvisited vertex of this peeked vertex
			if (v == -1) {
				ourStack.pop();// if has no more unvisited vertex, begin pop
			} else {
				vertList[v].wasVisited = true; // mark it
				ourStack.push(v); // push it
			}
		}// finish once our stack is empty
		
		for (int i = 0; i < vertNumber; i++) { // check connectivity 
			if (vertList[i].wasVisited == false) {
				isFuzzy = true;
				break;
			}
		}
		for (int j = 0; j < vertNumber; j++) { // reset to unvisited
			vertList[j].wasVisited = false;
		}
		return isFuzzy;
	}
	
	private int getAdjOmitVertex(int v,int omit) {
		for (int j = 0; j < vertNumber; j++) {
			if (j!= omit && adjMatrix[v][j] == 1 && vertList[j].wasVisited == false) {
				return j;
			}
		}
		return -1;
	}
	
	//2.E Get Path
	public void getPath(Vertex start, Vertex end) {
		int from = -1;
		int to = -1;
		for (int i = 0; i < MAX_VERTICES; i++) {
			if (start.label == vertList[i].label) {
				from = i;
			}
			if (end.label == vertList[i].label) {
				to = i;
			}
		}
		if (from == -1 || to == -1) {
			System.out.println();
			return;
		}
		vertList[from].wasVisited = true; // mark it
		ourStack.push(from); // push it
		while (!ourStack.isEmpty()) {
			int v = getAdjUnvisitedVertex(ourStack.peek()); // get an unvisited vertex of this peeked vertex
			if (v == to) {
				System.out.print("Path found:");
				ArrayList<String> path = new ArrayList<String>(); 
				path.add(end.name+" ");
				while (!ourStack.isEmpty()) {
					int temp = ourStack.pop();
					for (int k = 0; k < MAX_VERTICES; k++) {
						if (temp == k) {
							path.add(vertList[k].name+" ");
						}
					}
				}
				
				for (String s: path) {
					System.out.print(s);
				}
				for (int j = 0; j < vertNumber; j++) { // reset to unvisited
					vertList[j].wasVisited = false;
				}
				System.out.println();
				return;
			}
			if (v == -1) {
				ourStack.pop();// if has no more unvisited vertex, begin pop
			} else {
				vertList[v].wasVisited = true; // mark it
				ourStack.push(v); // push it
			}
		}// finish once our stack is empty
		
		for (int j = 0; j < vertNumber; j++) { // reset to unvisited
			vertList[j].wasVisited = false;
		}
		System.out.println("No path found.");
	}
	//2.F Get Cycle
	
}








