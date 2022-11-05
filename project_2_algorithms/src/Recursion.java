
public class Recursion {
	public static double binaryMax(double[] arr, int start, int end) {
		//base case
		if (start == end) {
			return arr[start];
		}
		int mid = (start + end) / 2 ;
		//recursive call
		return Math.max(binaryMax(arr,start,mid), binaryMax(arr,mid+1,end));
	}
	
	public static int zeroInBinary(int N, int ones, int digits) {
		//base case
		if (N == 0) {
			return digits + 1 - ones; // take into consideration of the place of (2^0)
		}
		
		int i = 0; //the power of 2
		
		while (N - Math.pow(2, i+1) >= 0) { 
			i++;
		}
		int remainder = (int) (N - Math.pow(2, i));
		
		// digits counts the place in binary of N where power (of 2) is not zero
		//recursive call
		return zeroInBinary( remainder , ones + 1, Math.max(digits, i));
	}
	public static boolean isPalindrome (String word, int forward, int backward) {
		//Palindrome can only be digits, including letters and numbers, we ignore punctuation and spaces
		while (!Character.isDigit(word.charAt(forward)) && !Character.isLetter(word.charAt(forward))
				&& forward < word.length()/2) {
			forward ++;
		}
		while (!Character.isDigit(word.charAt(backward)) && !Character.isLetter(word.charAt(backward)) 
				&& backward > word.length()/2) {
			backward --;
		}
		//base case: all digits are checked = no return before this step
		if (forward == backward /*odd length*/|| forward - 1 == backward /*even length*/) {
			return true;
		}
		if (forward > backward) {
			return false;
		}
		//recursive call: check if front equals back 
		if (word.toLowerCase().charAt(forward) == word.toLowerCase().charAt(backward)) {
			return isPalindrome( word , forward + 1, backward - 1);
		}
		return false;
		
	}
	
}
