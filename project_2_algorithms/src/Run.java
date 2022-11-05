
public class Run {
	public static void main(String[] args) {
		//1.Arrays
		String a1 = "abc ";
		String a2 = "c ba";
		System.out.println(ArrayAlgorithms.checkReversed(a1, a2));
		String b = "Other entries include a historic district in Charlottesville Virginia cut-flower greenhouse complex";
		System.out.println(ArrayAlgorithms.leaveShortest(b));
		
		//2.Recursion
		double [] c = {2,9.5,93.2,88,0,-1,Math.PI};
		System.out.println(Recursion.binaryMax(c, 0, c.length-1));
		System.out.println(Recursion.zeroInBinary(121, 0, 0));
		String d1 = "r...a.d..d...a..r..";
		String d2 = "kayak";
		System.out.println(d1+" is Palindrome: "+Recursion.isPalindrome(d1, 0, d1.length()-1));
		System.out.println(d2+" is Palindrome: "+Recursion.isPalindrome(d2, 0, d2.length()-1));
		
		//3.Sorting
		int[] e = {4,77,98,30,20,50,77,22,49,2};
		Sort.bubbleSortNR(e);
		print(e);
		
		int[] f = {4,77,98,30,20,50,77,22,49,2};
		Sort.bubbleSort(f,f.length-1);
		print(f);
		
		int [] g = {4,77,98,30,20,50,77,22,49,2};	
		Sort.selectSort(g);
		print(g);
		
		int [] h = {4,77,98,30,20,50,77,22,49,2};
		Sort.insertionSort(h);
		print(h);
		
		int [] i = {4,77,98,30,20,50,77,22,49,2};
		Sort.mergeSort(i, i, 0, i.length-1);
		print(i);
		
	}
	
	public static void print(int[] some) {
		for (int i : some) {
			System.out.print(i+" ");
		}
		System.out.println();
	}
	
}
